package com.banco.transactionlisting.service;

import com.banco.transactionlisting.configuration.ElasticSearchConfig;
import com.banco.transactionlisting.model.Transaction;
import com.banco.transactionlisting.model.TransactionResponse;
import com.banco.transactionlisting.model.TransactionStatus;
import com.banco.transactionlisting.util.TransactionValidator;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionListingServiceTest {

    @Mock
    private RestHighLevelClient client;

    @Mock
    private TransactionTypeService transactionTypeService;

    private TransactionListingService transactionListingService;

    @Mock
    TransactionValidator transactionValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ElasticSearchConfig elasticSearchConfig = Mockito.mock(ElasticSearchConfig.class);
        when(elasticSearchConfig.getIndex()).thenReturn("testIndex");
        transactionListingService = new TransactionListingService(client, elasticSearchConfig, transactionTypeService, transactionValidator);
    }

    @Test
    void testGetTransactions() throws IOException {
        // Given
        Transaction transaction = new Transaction();
        TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setName("transferencia");
        transaction.setTransactionStatus(transactionStatus);

        String searchHitJson = "{\"transactionExternalId\":\"123e4567-e89b-12d3-a456-426614174000\",\"transactionTypeId\":1,\"transactionStatus\":\"SUCCESS\",\"value\":100.0,\"createdAt\":\"2022-01-01T00:00:00Z\"}";
        SearchResponse searchResponse = Mockito.mock(SearchResponse.class);
        SearchHits searchHits = Mockito.mock(SearchHits.class);
        SearchHit searchHit = Mockito.mock(SearchHit.class);

        when(searchHit.getSourceAsString()).thenReturn(searchHitJson);
        when(searchHits.getHits()).thenReturn(new SearchHit[]{searchHit});
        when(searchResponse.getHits()).thenReturn(searchHits);
        when(client.search(any(SearchRequest.class), any(RequestOptions.class))).thenReturn(searchResponse);
        when(transactionTypeService.getTransactionTypeIdByName(transaction)).thenReturn(1);

        // When
        List<TransactionResponse> transactionResponses = transactionListingService.getTransactions(transaction);

        // Then
        assertEquals(1, transactionResponses.size());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", transactionResponses.get(0).getTransactionExternalId().toString());
    }

}