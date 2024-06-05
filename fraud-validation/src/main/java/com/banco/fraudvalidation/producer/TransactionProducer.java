package com.banco.fraudvalidation.producer;

import com.banco.fraudvalidation.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private static final String TOPIC = "validatedTransactions";

    public TransactionProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(Transaction transaction) {
        kafkaTemplate.send(TOPIC, transaction);
    }
}