package com.banco.transactioncreation.service;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.transactioncreation.model.Transaction;
import com.banco.transactioncreation.model.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private CassandraTemplate cassandraTemplate;

    @Mock
    private KafkaTemplate<String, TransactionKafkaDTO> kafkaTemplate;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrepareTransaction() {
        Transaction transaction = new Transaction();

        Transaction preparedTransaction = transactionService.prepareTransaction(transaction);

        assertNotNull(preparedTransaction);
    }

    @Test
    void testSaveAndPublishTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setTransferTypeId(1);

        when(cassandraTemplate.insert(any(Transaction.class))).thenReturn(transaction);
        when(kafkaTemplate.send(anyString(), any(TransactionKafkaDTO.class))).thenReturn(null);

        transactionService.saveAndPublishTransaction(transaction);

        verify(cassandraTemplate, times(1)).insert(any(Transaction.class));
        verify(kafkaTemplate, times(1)).send(anyString(), any(TransactionKafkaDTO.class));
    }
}