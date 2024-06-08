#!/bin/bash
/usr/bin/kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic validatedTransactions
/usr/bin/kafka-topics --describe --bootstrap-server localhost:9092 --topic validatedTransactions

/usr/bin/kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic transactions
/usr/bin/kafka-topics --describe --bootstrap-server localhost:9092 --topic transactions
