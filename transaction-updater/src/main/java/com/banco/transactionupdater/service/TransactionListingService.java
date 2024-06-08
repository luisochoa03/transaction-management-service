package com.banco.transactionupdater.service;

import com.banco.transactionupdater.model.TransactionCassandra;
import com.banco.transactionupdater.model.TransactionElastic;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.banco.transactionupdater.util.Util.convertToTransactionCassandra;

@Service
public class TransactionListingService {

    private final TransactionGetElasticService transactionGetElasticService;
    private final CassandraTemplate cassandraTemplate;
    private final ElasticsearchOperations elasticsearchOperations;


    public TransactionListingService(TransactionGetElasticService transactionGetElasticService, CassandraTemplate cassandraTemplate, ElasticsearchOperations elasticsearchOperations) {
        this.transactionGetElasticService = transactionGetElasticService;
        this.cassandraTemplate = cassandraTemplate;
        this.elasticsearchOperations = elasticsearchOperations;

    }

    @Scheduled(cron = "0 * * * * ?")
    public void processTransactions() throws IOException {
        int pageNumber = 0;
        int pageSize = 2;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TransactionElastic> transactions;

        do {
            transactions = transactionGetElasticService.getTransactionsWhereBatchUpdateFlagIsFalse(pageable.getPageNumber(), pageable.getPageSize());

            if (!transactions.isEmpty()) {
                updateCassandraDatabase(convertToTransactionCassandra(transactions));
                setBatchUpdateFlagToTrue(transactions);
            }

            pageable = pageable.next();
        } while (!transactions.isEmpty());
    }

    private void updateCassandraDatabase(List<TransactionCassandra> transactions) {
        CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
        batchOps.update(transactions);
        batchOps.execute();
    }

    private void setBatchUpdateFlagToTrue(List<TransactionElastic> transactions) {
        List<UpdateQuery> queries = transactions.stream()
                .map(transaction -> {
                    transaction.setBatchUpdateFlag(true);

                    String id = String.valueOf(transaction.getTransactionExternalId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("batchUpdateFlag", true);

                    return UpdateQuery.builder(id)
                            .withDocument(Document.from(map))
                            .build();
                })
                .collect(Collectors.toList());

        elasticsearchOperations.bulkUpdate(queries, IndexCoordinates.of("transactions"));
    }
}