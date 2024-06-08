package com.banco.transactionupdater.service;

import com.banco.transactionupdater.model.TransactionElastic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionListingServiceTest {

    @Mock
    private TransactionGetElasticService transactionGetElasticService;

    @Mock
    private CassandraTemplate cassandraTemplate;

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private TransactionListingService transactionListingService;

    @BeforeEach
    public void setUp() {
        CassandraBatchOperations batchOpsMock = Mockito.mock(CassandraBatchOperations.class);
        Mockito.when(cassandraTemplate.batchOps()).thenReturn(batchOpsMock);
    }

    @Test
    void testProcessTransactions() throws IOException {
        // Given
        TransactionElastic transactionElastic = new TransactionElastic();
        List<TransactionElastic> transactions = Arrays.asList(transactionElastic);
        CassandraBatchOperations batchOpsMock = Mockito.mock(CassandraBatchOperations.class);
        Mockito.when(cassandraTemplate.batchOps()).thenReturn(batchOpsMock);

        Mockito.when(transactionGetElasticService.getTransactionsWhereBatchUpdateFlagIsFalse(any(Integer.class), any(Integer.class)))
                .thenReturn(transactions)
                .thenReturn(Collections.emptyList()); // return an empty list on the second call

        // When
        transactionListingService.processTransactions();

        // Then
        verify(transactionGetElasticService, times(2)).getTransactionsWhereBatchUpdateFlagIsFalse(any(Integer.class), any(Integer.class));
        verify(cassandraTemplate, times(1)).batchOps();
        verify(batchOpsMock, times(1)).update(anyList());
        verify(elasticsearchOperations, times(1)).bulkUpdate(anyList(), eq(IndexCoordinates.of("transactions")));
    }
}