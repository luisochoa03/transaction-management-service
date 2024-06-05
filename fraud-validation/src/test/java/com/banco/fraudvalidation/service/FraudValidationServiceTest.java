package com.banco.fraudvalidation.service;

import com.banco.fraudvalidation.model.Transaction;
import com.banco.fraudvalidation.service.valitationfraudstrategy.ValueValidationStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FraudValidationServiceTest {

    private final ValueValidationStrategy strategy = new ValueValidationStrategy();

    @Test
    void validate_shouldReturnTrue_whenValueIsLessThanMax() {
        Transaction transaction = new Transaction();
        transaction.setValue(new BigDecimal("500"));

        boolean result = strategy.validate(transaction);

        assertTrue(result);
    }

    @Test
    void validate_shouldReturnFalse_whenValueIsGreaterThanMax() {
        Transaction transaction = new Transaction();
        transaction.setValue(new BigDecimal("1500"));

        boolean result = strategy.validate(transaction);

        assertFalse(result);
    }

    @Test
    void isRequired_shouldAlwaysReturnTrue() {
        assertTrue(strategy.isRequired());
    }
}