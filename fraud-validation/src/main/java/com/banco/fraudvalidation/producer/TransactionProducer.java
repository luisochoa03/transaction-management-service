package com.banco.fraudvalidation.producer;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<Object, TransactionKafkaDTO> kafkaTemplate;
    private static final String TOPIC = "validatedTransactions";

    public TransactionProducer(KafkaTemplate<Object, TransactionKafkaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(TransactionKafkaDTO transaction) {
        kafkaTemplate.send(TOPIC, transaction);
    }
}