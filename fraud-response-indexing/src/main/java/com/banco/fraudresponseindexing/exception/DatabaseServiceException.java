package com.banco.fraudresponseindexing.exception;

public class DatabaseServiceException extends RuntimeException {

    private final String errorCode;

    public DatabaseServiceException(String errorCode, Throwable cause) {
        super(getErrorMessage(errorCode), cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    private static String getErrorMessage(String errorCode) {
        if (errorCode.equals("DB_SERVICE_ERROR")) {
            return "Error while performing database operation";
        }
        return "Unknown error occurred";
    }
}