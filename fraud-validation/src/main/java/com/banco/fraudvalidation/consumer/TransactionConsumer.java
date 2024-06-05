package com.banco.fraudvalidation.consumer;

import com.banco.fraudvalidation.model.Transaction;
import com.banco.fraudvalidation.service.FraudValidationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private final FraudValidationService fraudValidationService;

    public TransactionConsumer(FraudValidationService fraudValidationService) {
        this.fraudValidationService = fraudValidationService;
    }


    @KafkaListener(topics = "transactions", groupId ="validate")
    public void consume(Transaction transaction) {
        fraudValidationService.validateAndPublish(transaction);
    }

}