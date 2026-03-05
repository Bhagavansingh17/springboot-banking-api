package com.bhagavan.bankingapi.dto;

import java.time.LocalDateTime;

public class TransferResponse {
    private Long transactionId;
    private String status;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private LocalDateTime createdAt;

    public TransferResponse(Long transactionId, String status, Long fromAccountId,
                            Long toAccountId, Double amount, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.status = status;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getTransactionId() { return transactionId; }
    public String getStatus() { return status; }
    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public Double getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}