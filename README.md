# transaction-management-service
Este repositorio alberga un sistema de gestión de transacciones integral. El sistema es capaz de crear transacciones, validarlas a través de un servicio de detección de fraude, actualizar las transacciones y finalmente realizar consultas sobre ellas.


levantar cassandra

docker cp create_table.cql transaction-management-service-cassandra-1:/tmp/create_table.cql
docker exec -it transaction-management-service-cassandra-1 cqlsh -f /tmp/create_table.cql

levantar elastic
docker cp index_config.json transaction-management-service-elasticsearch-1:/tmp/index_config.json
docker exec -it transaction-management-service-elasticsearch-1 bash -c 'curl -X PUT "localhost:9200/transactions" -H "Content-Type: application/json" -d "$(cat /tmp/index_config.json)"'