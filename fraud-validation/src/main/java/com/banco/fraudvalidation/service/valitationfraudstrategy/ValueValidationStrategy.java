package com.banco.fraudvalidation.service.valitationfraudstrategy;

import com.banco.commonmodels.dto.TransactionKafkaDTO;

import java.math.BigDecimal;

public class ValueValidationStrategy implements ValidationStrategy {

    private static final BigDecimal MAX_VALUE = new BigDecimal("1000");

    @Override
    public boolean validate(TransactionKafkaDTO transaction) {
        return transaction.getValue().compareTo(MAX_VALUE) <= 0;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}