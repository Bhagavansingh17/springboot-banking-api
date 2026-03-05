package com.bhagavan.bankingapi.controller;

import com.bhagavan.bankingapi.dto.AccountResponse;
import com.bhagavan.bankingapi.dto.AmountRequest;
import com.bhagavan.bankingapi.dto.CreateAccountRequest;
import com.bhagavan.bankingapi.entity.Transaction;
import com.bhagavan.bankingapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // ✅ CREATE ACCOUNT (returns AccountResponse, no PIN)
    @PostMapping
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest req) {
        return service.createAccount(req);
    }

    // ✅ GET ACCOUNT (returns AccountResponse, no PIN)
    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable Long id) {
        return service.getAccountResponse(id);
    }

    // ✅ DEPOSIT
    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest req) {
        return service.deposit(id, req);
    }

    // ✅ WITHDRAW
    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest req) {
        return service.withdraw(id, req);
    }

    // ✅ TRANSACTIONS (same as before)
    @GetMapping("/{id}/transactions")
    public org.springframework.data.domain.Page<Transaction> transactions(
            @PathVariable Long id,
            @RequestParam String pin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return service.getTransactions(id, pin, page, size);
    }
}