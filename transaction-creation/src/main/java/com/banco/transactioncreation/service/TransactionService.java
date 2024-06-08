package com.banco.transactioncreation.service;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.transactioncreation.model.Transaction;
import com.banco.transactioncreation.model.TransactionStatus;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.banco.transactioncreation.utils.TransactionMapper.toTransactionKafkaDTO;

@Service
public class TransactionService {

    private final CassandraTemplate cassandraTemplate;
    private final KafkaTemplate<Object, TransactionKafkaDTO> kafkaTemplate;
    public TransactionService(CassandraTemplate cassandraTemplate, KafkaTemplate<Object, TransactionKafkaDTO> kafkaTemplate) {
        this.cassandraTemplate = cassandraTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }


    public Transaction prepareTransaction(Transaction transaction) {
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setTransactionExternalId(UUID.randomUUID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now(ZoneOffset.UTC).format(formatter));
        transaction.setCreatedAt(now);

        return transaction;
    }

    public void saveAndPublishTransaction(Transaction transaction) {
        cassandraTemplate.insert(transaction);
        kafkaTemplate.send("transactions", toTransactionKafkaDTO(transaction));
    }

}