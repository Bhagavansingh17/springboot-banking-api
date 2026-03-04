package com.bhagavan.bankingapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TransferRequest {

    @NotNull
    private Long fromAccountId;

    @NotNull
    private Long toAccountId;

    @Min(value = 1, message = "Amount must be >= 1")
    private double amount;

    @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String pin;

    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public double getAmount() { return amount; }
    public String getPin() { return pin; }

    public void setFromAccountId(Long fromAccountId) { this.fromAccountId = fromAccountId; }
    public void setToAccountId(Long toAccountId) { this.toAccountId = toAccountId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPin(String pin) { this.pin = pin; }
}