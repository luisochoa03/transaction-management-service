package com.banco.transactionupdater.service;

import com.banco.transactionupdater.exception.JsonParsingException;
import com.banco.transactionupdater.model.TransactionElastic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionGetElasticService {

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public TransactionGetElasticService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public List<TransactionElastic> getTransactionsWhereBatchUpdateFlagIsFalse(int pageNumber, int pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest("transactions");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("batchUpdateFlag", false));
        searchSourceBuilder.from(pageNumber * pageSize);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return Arrays.stream(searchResponse.getHits().getHits())
                .map(this::convertHitToTransaction)
                .collect(Collectors.toList());
    }

    private TransactionElastic convertHitToTransaction(SearchHit hit) {
        try {
            return objectMapper.readValue(hit.getSourceAsString(), TransactionElastic.class);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("Error processing JSON", e);
        }
    }
}