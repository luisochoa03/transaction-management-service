package com.banco.fraudresponseindexing.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DatabaseServiceException.class)
    public ResponseEntity<String> handleDatabaseServiceException(DatabaseServiceException e) {
        return new ResponseEntity<>(e.getMessage() + " Error Code: " + e.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
