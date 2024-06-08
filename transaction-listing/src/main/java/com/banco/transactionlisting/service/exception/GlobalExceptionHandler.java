package com.banco.transactionlisting.service.exception;

import com.banco.transactionlisting.service.TransactionTypeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTransactionNotFoundException(TransactionNotFoundException ex) {
        logger.error("Manejador handleTransactionNotFoundException");

        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex) {
        logger.error("Excepción no manejada: ", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(EmptyTransactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmptyTransactionException(EmptyTransactionException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            return "Error en el campo '" + fieldName + "': " + defaultMessage;
        } else {
            return "Error de validación de la entrada";
        }
    }


    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIOException(IOException ex) {
        logger.error("Manejador handleIOException");
        return ex.getMessage();
    }

    @ExceptionHandler(TransactionTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTransactionTypeNotFoundException(TransactionTypeNotFoundException ex) {
        logger.error("Manejador handleTransactionTypeNotFoundException");
        return ex.getMessage();
    }
}