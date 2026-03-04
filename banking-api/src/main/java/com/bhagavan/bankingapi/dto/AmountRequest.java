package com.bhagavan.bankingapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class AmountRequest {

    @Min(value = 1, message = "Amount must be >= 1")
    private double amount;

    @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String pin;

    public double getAmount() { return amount; }
    public String getPin() { return pin; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setPin(String pin) { this.pin = pin; }
}