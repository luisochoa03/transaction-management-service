package com.banco.transactioncreation.service;

import com.banco.transactioncreation.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

import java.util.stream.Collectors;

@Service
public class TransactionValidationService implements ValidationService<Transaction> {

    private final Validator validator;

    public TransactionValidationService(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validate(Transaction transaction) {
        Errors errors = new BeanPropertyBindingResult(transaction, Transaction.class.getName());
        validator.validate(transaction, errors);
        if (errors.hasErrors()) {
            String errorMessage = errors.getFieldErrors().stream()
                    .map(fieldError ->  fieldError.getField() + " is null: " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ServerWebInputException(errorMessage);
        }
    }
}