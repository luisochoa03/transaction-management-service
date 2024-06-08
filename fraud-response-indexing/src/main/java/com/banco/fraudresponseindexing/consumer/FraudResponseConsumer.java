package com.banco.fraudresponseindexing.consumer;
import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.fraudresponseindexing.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class FraudResponseConsumer {

    private final DatabaseService databaseService;

    @Autowired
    public FraudResponseConsumer(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @KafkaListener(topics = "validatedTransactions", groupId = "result")
    public void consumeFraudResult(TransactionKafkaDTO transaction)  {
        databaseService.insertFraudResponse(transaction);
    }
}