package com.bhagavan.bankingapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromAccountId; // null for deposit
    private Long toAccountId;   // null for withdraw

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Transaction() {}

    public Transaction(Long fromAccountId, Long toAccountId, double amount, TransactionType type) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}