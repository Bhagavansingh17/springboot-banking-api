package com.bhagavan.bankingapi.controller;

import com.bhagavan.bankingapi.dto.TransferRequest;
import com.bhagavan.bankingapi.dto.TransferResponse;
import com.bhagavan.bankingapi.entity.Transaction;
import com.bhagavan.bankingapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final AccountService service;

    public TransferController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public TransferResponse transfer(@Valid @RequestBody TransferRequest req) {

        Transaction tx = service.transfer(req);

        return new TransferResponse(
                tx.getId(),
                "SUCCESS",
                tx.getFromAccountId(),
                tx.getToAccountId(),
                tx.getAmount(),
                tx.getCreatedAt()
        );
    }
}