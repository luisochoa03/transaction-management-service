package com.banco.fraudresponseindexing.service;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseServiceIntegrationTest {

    @Mock
    private DatabaseService databaseService;

    @Test
    void insertFraudResponse() {
        TransactionKafkaDTO transaction = new TransactionKafkaDTO();
        transaction.setTransactionExternalId(UUID.randomUUID());

        when(databaseService.insertFraudResponse(transaction)).thenReturn(RestStatus.CREATED);

        RestStatus restStatus = databaseService.insertFraudResponse(transaction);

        Assertions.assertEquals(RestStatus.CREATED, restStatus);
        verify(databaseService, times(1)).insertFraudResponse(transaction);
    }
}