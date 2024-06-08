package com.banco.transactionlisting.util;

import com.banco.transactionlisting.model.Transaction;
import com.banco.transactionlisting.service.exception.EmptyTransactionException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class TransactionValidator {

    public void isEmpty(Transaction transaction) {
        if (Objects.isNull(transaction) || Stream.of(transaction.getTransactionExternalId(), transaction.getTransactionType(),
                        transaction.getTransactionStatus(), transaction.getValue(), transaction.getCreatedAt())
                .allMatch(Objects::isNull)) {
            throw new EmptyTransactionException("No search parameters provided");
        }
    }
}