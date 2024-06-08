package com.banco.transactionupdater.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "transactions")
public class TransactionElastic extends TransactionBase {

    @Field(type = FieldType.Boolean)
    private boolean batchUpdateFlag;

    public boolean isBatchUpdateFlag() {
        return batchUpdateFlag;
    }

    public void setBatchUpdateFlag(boolean batchUpdateFlag) {
        this.batchUpdateFlag = batchUpdateFlag;
    }
}