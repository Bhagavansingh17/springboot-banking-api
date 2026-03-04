package com.bhagavan.bankingapi.controller;

import com.bhagavan.bankingapi.dto.TransferRequest;
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
    public String transfer(@Valid @RequestBody TransferRequest req) {
        service.transfer(req);
        return "✅ Transfer successful";
    }
}