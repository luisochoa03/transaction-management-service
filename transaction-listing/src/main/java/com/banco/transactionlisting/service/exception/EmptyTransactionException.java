package com.banco.transactionlisting.service.exception;

public class EmptyTransactionException extends RuntimeException {
    public EmptyTransactionException(String message) {
        super(message);
    }
}