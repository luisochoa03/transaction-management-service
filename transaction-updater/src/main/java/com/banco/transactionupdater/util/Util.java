package com.banco.transactionupdater.util;

import com.banco.transactionupdater.model.TransactionCassandra;
import com.banco.transactionupdater.model.TransactionElastic;

import java.util.ArrayList;
import java.util.List;

public class Util {
    private Util() {
    }

    public static List<TransactionCassandra> convertToTransactionCassandra(List<TransactionElastic> transactionsElastic) {
        List<TransactionCassandra> transactionsCassandra = new ArrayList<>();
        for (TransactionElastic transactionElastic : transactionsElastic) {
            TransactionCassandra transactionCassandra = new TransactionCassandra();
            transactionCassandra.setAccountExternalIdDebit(transactionElastic.getAccountExternalIdDebit());
            transactionCassandra.setAccountExternalIdCredit(transactionElastic.getAccountExternalIdCredit());
            transactionCassandra.setTransferTypeId(transactionElastic.getTransferTypeId());
            transactionCassandra.setValue(transactionElastic.getValue());
            transactionCassandra.setTransactionExternalId(transactionElastic.getTransactionExternalId());
            transactionCassandra.setCreatedAt(transactionElastic.getCreatedAt());
            transactionCassandra.setTransactionStatus(transactionElastic.getTransactionStatus());
            transactionsCassandra.add(transactionCassandra);
        }
        return transactionsCassandra;
    }
}