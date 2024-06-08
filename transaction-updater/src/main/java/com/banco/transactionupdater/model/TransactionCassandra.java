package com.banco.transactionupdater.model;

import org.springframework.data.cassandra.core.mapping.Table;

@Table("Transactions")
public class TransactionCassandra extends TransactionBase {

}