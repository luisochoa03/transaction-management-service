package com.banco.transactionlisting.service.querybuilder;

import com.banco.transactionlisting.model.TransactionStatus;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.math.BigDecimal;
import java.util.Objects;


public class TransactionQueryBuilder {

    private final BoolQueryBuilder query;

    private TransactionQueryBuilder(Builder builder) {
        this.query = builder.query;
    }

    public BoolQueryBuilder getQuery() {
        return this.query;
    }

    public static class Builder {

        private final BoolQueryBuilder query;

        public Builder() {
            this.query = QueryBuilders.boolQuery();
        }

        public Builder withTransactionExternalId(String transactionExternalId) {
            addQuery(transactionExternalId, "transactionExternalId");
            return this;
        }

        public Builder withTransactionTypeId(Integer transactionTypeId) {
            addQueryObject(transactionTypeId, "transferTypeId");
            return this;
        }

        public Builder withTransactionStatusName(TransactionStatus transactionStatusName) {
            if (Objects.nonNull(transactionStatusName)) {
                addQuery(transactionStatusName.getName(), "transactionStatus");
            }
            return this;
        }

        public Builder withValue(BigDecimal value) {
            addQueryObject(value, "value");
            return this;
        }

        public Builder withCreatedAt(String createdAt) {
            addQuery(createdAt, "createdAt");
            return this;
        }

        private void addQuery(String string, String query) {
            if (Objects.nonNull(string) && !string.isEmpty() && !string.equals("null")) {
                this.query.must(QueryBuilders.matchQuery(query, string));
            }
        }

        private void addQueryObject(Number number, String query) {
            if (Objects.nonNull(number)) {
                this.query.must(QueryBuilders.matchQuery(query, number));
            }
        }

        public TransactionQueryBuilder build() {
            return new TransactionQueryBuilder(this);
        }
    }
}



