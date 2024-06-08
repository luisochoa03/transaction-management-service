package com.banco.transactionlisting.model;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private UUID transactionExternalId;
    @Valid
    private TransactionType transactionType;
    @Valid
    private TransactionStatus transactionStatus;
    private BigDecimal value;
    private String createdAt;

    public String getTransactionExternalId() {
        return String.valueOf(transactionExternalId);
    }

    public void setTransactionExternalId(UUID transactionExternalId) {
        this.transactionExternalId = transactionExternalId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}