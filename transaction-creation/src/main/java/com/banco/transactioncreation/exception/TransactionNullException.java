package com.banco.transactioncreation.exception;

public class TransactionNullException extends RuntimeException {
    public TransactionNullException(String message) {
        super(message);
    }
}