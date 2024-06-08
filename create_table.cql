CREATE KEYSPACE IF NOT EXISTS test WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

USE test;

CREATE TABLE IF NOT EXISTS Transactions (
                                            transactionExternalId uuid PRIMARY KEY,
                                            accountExternalIdDebit uuid,
                                            accountExternalIdCredit uuid,
                                            transactionStatus text,
                                            value decimal,
                                            operationDate timestamp,
                                            transactionType int
);