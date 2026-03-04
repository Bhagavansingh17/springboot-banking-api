package com.bhagavan.bankingapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateAccountRequest {

    @NotBlank
    private String holderName;

    @Min(0)
    private double initialBalance;

    @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String pin;

    public String getHolderName() { return holderName; }
    public double getInitialBalance() { return initialBalance; }
    public String getPin() { return pin; }

    public void setHolderName(String holderName) { this.holderName = holderName; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
    public void setPin(String pin) { this.pin = pin; }
}