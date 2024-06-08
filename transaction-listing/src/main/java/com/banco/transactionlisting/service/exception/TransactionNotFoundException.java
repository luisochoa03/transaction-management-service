package com.banco.transactionlisting.service.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("No se encontraron transacciones para los criterios de búsqueda proporcionados");
    }
}