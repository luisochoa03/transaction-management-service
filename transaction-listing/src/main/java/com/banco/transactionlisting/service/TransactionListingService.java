package com.banco.transactionlisting.service;

import com.banco.transactionlisting.configuration.ElasticSearchConfig;
import com.banco.transactionlisting.model.Transaction;
import com.banco.transactionlisting.model.TransactionResponse;
import com.banco.transactionlisting.service.exception.TransactionNotFoundException;
import com.banco.transactionlisting.service.querybuilder.TransactionQueryBuilder;
import com.banco.transactionlisting.util.TransactionValidator;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.banco.transactionlisting.util.Map.convertSearchResponseToTransactionResponse;

@Service
public class TransactionListingService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListingService.class);

    private final RestHighLevelClient client;
    private final String elasticsearchIndex;
    private final TransactionTypeService transactionTypeService;
    private final TransactionValidator transactionValidator;

    @Autowired
    public TransactionListingService(RestHighLevelClient client, ElasticSearchConfig elasticSearchConfig, TransactionTypeService transactionTypeService, TransactionValidator transactionValidator) {
        this.client = client;
        this.elasticsearchIndex = elasticSearchConfig.getIndex();
        this.transactionTypeService = transactionTypeService;
        this.transactionValidator = transactionValidator;
    }

    public List<TransactionResponse> getTransactions(Transaction transaction) throws IOException {

        isEmptyValidate(transaction);
        SearchRequest searchRequest = new SearchRequest(elasticsearchIndex);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        Integer transactionTypes = transactionTypeService.getTransactionTypeIdByName(transaction);

        TransactionQueryBuilder queryBuilder = new TransactionQueryBuilder.Builder()
                .withTransactionExternalId(transaction.getTransactionExternalId())
                .withTransactionTypeId(transactionTypes)
                .withTransactionStatusName(transaction.getTransactionStatus())
                .withValue(transaction.getValue())
                .withCreatedAt(transaction.getCreatedAt())
                .build();

        searchSourceBuilder.query(queryBuilder.getQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] searchHits = response.getHits().getHits();

        if (searchHits.length == 0) {
            logger.error("No se encontraron transacciones, lanzando TransactionNotFoundException");
            throw new TransactionNotFoundException();
        }

        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            TransactionResponse transactionResponse = convertSearchResponseToTransactionResponse(sourceAsString);
            transactionResponses.add(transactionResponse);
        }

        return transactionResponses;
    }




    public void isEmptyValidate(Transaction transaction) {
        transactionValidator.isEmpty(transaction);
    }

}