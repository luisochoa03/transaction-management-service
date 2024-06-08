package com.banco.fraudvalidation.service;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.fraudvalidation.service.valitationfraudstrategy.ValueValidationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class FraudValidationServiceTest {

    private final ValueValidationStrategy strategy = new ValueValidationStrategy();

    @Test
    void validate_shouldReturnTrue_whenValueIsLessThanMax() {
        TransactionKafkaDTO transaction = new TransactionKafkaDTO();
        transaction.setValue(new BigDecimal("500"));

        boolean result = strategy.validate(transaction);

        assertTrue(result);
    }

    @Test
    void validate_shouldReturnFalse_whenValueIsGreaterThanMax() {
        TransactionKafkaDTO transaction = new TransactionKafkaDTO();
        transaction.setValue(new BigDecimal("1500"));

        boolean result = strategy.validate(transaction);

        assertFalse(result);
    }

    @Test
    void isRequired_shouldAlwaysReturnTrue() {
        assertTrue(strategy.isRequired());
    }
}