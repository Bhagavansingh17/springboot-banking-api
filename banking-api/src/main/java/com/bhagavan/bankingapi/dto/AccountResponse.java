package com.bhagavan.bankingapi.dto;

public class AccountResponse {
    private Long id;
    private String holderName;
    private double balance;

    public AccountResponse(Long id, String holderName, double balance) {
        this.id = id;
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
}