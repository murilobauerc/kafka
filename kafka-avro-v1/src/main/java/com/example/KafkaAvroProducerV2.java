package com.example;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerV2 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        KafkaProducer<String, Customer2> kafkaProducer = new KafkaProducer<String, Customer2>(properties);
        String topic = "customer-avro";

        Customer2 customer2 = Customer2.newBuilder()
                .setFirstName("Mark")
                .setLastName("Johnson")
                .setAge(50)
                .setHeight(90.2f)
                .setWeight(85.6f)
                .setPhoneNumber("123-456-789")
                .setEmail("john.doe@gmail.com")
                .build();

        ProducerRecord<String, Customer2> producerRecord = new ProducerRecord<String, Customer2>(topic, customer2);

        kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                if(exception == null) {
                    System.out.println("Success!");
                    System.out.println(recordMetadata.toString());
                }else {
                    exception.printStackTrace();
                }
            }
        });

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
