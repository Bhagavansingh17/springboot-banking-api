package com.bhagavan.bankingapi.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false)
    private double balance;

    @JsonIgnore
    @Column(nullable = false, length = 4)
    private String pin;

    public Account() {}

    public Account(String holderName, double balance, String pin) {
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
    }

    public Long getId() { return id; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public String getPin() { return pin; }

    public void setHolderName(String holderName) { this.holderName = holderName; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setPin(String pin) { this.pin = pin; }
}