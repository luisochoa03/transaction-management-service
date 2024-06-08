package com.banco.transactionlisting.service;

import com.banco.transactionlisting.model.Transaction;
import com.banco.transactionlisting.model.TransactionTypeCassandra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypeService {

    private final CassandraTemplate cassandraTemplate;

    @Autowired
    public TransactionTypeService(CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    public Integer getTransactionTypeIdByName(Transaction transaction) {
        if (transaction.getTransactionType() == null || transaction.getTransactionType().getName().isEmpty())
            return null;
        Query query = Query.query(Criteria.where("name").is(transaction.getTransactionType().getName()));
        TransactionTypeCassandra transactionType = cassandraTemplate.selectOne(query, TransactionTypeCassandra.class);
        if (transactionType == null) {
            throw new TransactionTypeNotFoundException("No se encontró el tipo de transacción: " + transaction.getTransactionType().getName());
        }
        return transactionType.getId();
    }
}