package com.banco.fraudresponseindexing.service;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.fraudresponseindexing.config.ElasticSearchConfig;
import com.banco.fraudresponseindexing.exception.DatabaseServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DatabaseService {

    private final RestHighLevelClient client;
    private final String elasticsearchIndex;
    private final ObjectMapper objectMapper;

    @Autowired
    public DatabaseService(ElasticSearchConfig elasticSearchConfig, ObjectMapper objectMapper) {
        this.client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchConfig.getHost(), elasticSearchConfig.getPort(), "http")));
        this.elasticsearchIndex = elasticSearchConfig.getIndex();
        this.objectMapper = objectMapper;
    }

    public RestStatus insertFraudResponse(TransactionKafkaDTO transaction)  {
        Map<String, Object> jsonMap = objectMapper.convertValue(transaction, new TypeReference<Map<String, Object>>() {});
        jsonMap.put("batchUpdateFlag", false);


        IndexRequest request = new IndexRequest(elasticsearchIndex);
        request.id(jsonMap.get("transactionExternalId").toString());

        request.source(jsonMap, XContentType.JSON);

        try {
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            return index.status();
        } catch (IOException e) {
            throw new DatabaseServiceException("DB_SERVICE_ERROR", e);
        }
    }

}