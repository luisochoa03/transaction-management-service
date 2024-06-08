package com.banco.transactioncreation.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServerWebInputException(ServerWebInputException ex) {
        return ex.getReason();
    }

    @ExceptionHandler(TransactionNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTransactionNullException(TransactionNullException ex) {
        return ex.getMessage();
    }
}