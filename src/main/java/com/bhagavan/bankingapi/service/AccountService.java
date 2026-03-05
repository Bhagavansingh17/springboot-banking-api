package com.bhagavan.bankingapi.service;

import com.bhagavan.bankingapi.dto.*;
import com.bhagavan.bankingapi.entity.Account;
import com.bhagavan.bankingapi.entity.Transaction;
import com.bhagavan.bankingapi.entity.TransactionType;
import com.bhagavan.bankingapi.repository.AccountRepository;
import com.bhagavan.bankingapi.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public AccountService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    private AccountResponse toResponse(Account acc) {
        return new AccountResponse(acc.getId(), acc.getHolderName(), acc.getBalance());
    }

    private Account getAccount(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    private void verifyPin(Account acc, String pin) {
        if (!acc.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }
    }

    public AccountResponse createAccount(CreateAccountRequest req) {
        Account saved = accountRepo.save(new Account(req.getHolderName(), req.getInitialBalance(), req.getPin()));

        if (req.getInitialBalance() > 0) {
            txRepo.save(new Transaction(null, saved.getId(), req.getInitialBalance(), TransactionType.DEPOSIT));
        }

        return toResponse(saved);
    }

    public AccountResponse getAccountResponse(Long id) {
        return toResponse(getAccount(id));
    }

    public AccountResponse deposit(Long id, AmountRequest req) {
        Account acc = getAccount(id);
        verifyPin(acc, req.getPin());

        acc.setBalance(acc.getBalance() + req.getAmount());
        Account saved = accountRepo.save(acc);

        txRepo.save(new Transaction(null, id, req.getAmount(), TransactionType.DEPOSIT));
        return toResponse(saved);
    }

    public AccountResponse withdraw(Long id, AmountRequest req) {
        Account acc = getAccount(id);
        verifyPin(acc, req.getPin());

        if (acc.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance. Current: " + acc.getBalance());
        }

        acc.setBalance(acc.getBalance() - req.getAmount());
        Account saved = accountRepo.save(acc);

        txRepo.save(new Transaction(id, null, req.getAmount(), TransactionType.WITHDRAW));
        return toResponse(saved);
    }

    public Page<TransactionResponse> getTransactions(Long accountId, String pin, int page, int size) {
        Account acc = getAccount(accountId);
        verifyPin(acc, pin);

        return txRepo.findByFromAccountIdOrToAccountId(accountId, accountId, PageRequest.of(page, size))
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getFromAccountId(),
                        tx.getToAccountId(),
                        tx.getAmount(),
                        tx.getType().name(),   // DTO expects String; if your DTO expects enum, remove .name()
                        tx.getCreatedAt()
                ));
    }

    @Transactional
    public Transaction transfer(TransferRequest req) {

        if (req.getFromAccountId().equals(req.getToAccountId())) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        Account from = getAccount(req.getFromAccountId());
        Account to = getAccount(req.getToAccountId());

        verifyPin(from, req.getPin());

        if (from.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance. Current: " + from.getBalance());
        }

        from.setBalance(from.getBalance() - req.getAmount());
        to.setBalance(to.getBalance() + req.getAmount());

        accountRepo.save(from);
        accountRepo.save(to);

        Transaction tx = new Transaction(
                from.getId(),
                to.getId(),
                req.getAmount(),
                TransactionType.TRANSFER
        );

        return txRepo.save(tx);
    }
}