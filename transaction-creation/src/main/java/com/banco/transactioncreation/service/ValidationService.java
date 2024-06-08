package com.banco.transactioncreation.service;

public interface ValidationService<T> {
    void validate(T object);
}