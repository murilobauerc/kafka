# Docker image to access all the Confluent binaries to run Kafka consumer and producer
docker run -t --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash

# Then
kafka-avro-console-consumer