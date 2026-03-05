package com.bhagavan.bankingapi.service;

import com.bhagavan.bankingapi.dto.AmountRequest;
import com.bhagavan.bankingapi.dto.CreateAccountRequest;
import com.bhagavan.bankingapi.entity.Account;
import com.bhagavan.bankingapi.entity.Transaction;
import com.bhagavan.bankingapi.repository.AccountRepository;
import com.bhagavan.bankingapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import com.bhagavan.bankingapi.dto.TransferRequest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.bhagavan.bankingapi.dto.AccountResponse;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    private com.bhagavan.bankingapi.dto.AccountResponse toResponse(Account acc) {
        return new com.bhagavan.bankingapi.dto.AccountResponse(
                acc.getId(),
                acc.getHolderName(),
                acc.getBalance()
        );
    }
    public AccountService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    private Account getAccount(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    public AccountResponse createAccount(CreateAccountRequest req) {
        Account saved = accountRepo.save(new Account(req.getHolderName(), req.getInitialBalance(), req.getPin()));
        if (req.getInitialBalance() > 0) {
            txRepo.save(new Transaction(null, saved.getId(), req.getInitialBalance(), "DEPOSIT"));
        }
        return toResponse(saved);
    }

    public AccountResponse getAccountResponse(Long id) {
        Account acc = getAccount(id);
        return toResponse(acc);
    }

    private void verifyPin(Account acc, String pin) {
        if (!acc.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }
    }

    public AccountResponse deposit(Long id, AmountRequest req) {
        Account acc = getAccount(id);
        verifyPin(acc, req.getPin());

        acc.setBalance(acc.getBalance() + req.getAmount());
        Account saved = accountRepo.save(acc);

        txRepo.save(new Transaction(null, id, req.getAmount(), "DEPOSIT"));
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

        txRepo.save(new Transaction(id, null, req.getAmount(), "WITHDRAW"));
        return toResponse(saved);
    }

    public Page<Transaction> getTransactions(Long accountId, String pin, int page, int size) {
        Account acc = getAccount(accountId);
        verifyPin(acc, pin);

        return txRepo.findByFromAccountIdOrToAccountId(
                accountId,
                accountId,
                PageRequest.of(page, size)
        );
    }

    @Transactional
    public void transfer(TransferRequest req) {

        if (req.getFromAccountId().equals(req.getToAccountId())) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        Account from = getAccount(req.getFromAccountId());
        Account to = getAccount(req.getToAccountId());

        verifyPin(from, req.getPin());

        if (from.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance. Current: " + from.getBalance());
        }

        // debit & credit
        from.setBalance(from.getBalance() - req.getAmount());
        to.setBalance(to.getBalance() + req.getAmount());

        // save both
        accountRepo.save(from);
        accountRepo.save(to);

        // record transaction
        txRepo.save(new Transaction(req.getFromAccountId(), req.getToAccountId(), req.getAmount(), "TRANSFER"));
    }
}