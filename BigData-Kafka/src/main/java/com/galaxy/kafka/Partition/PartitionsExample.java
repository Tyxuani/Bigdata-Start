package com.galaxy.kafka.Partition;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PartitionsExample {

    private static Logger log = LoggerFactory.getLogger(PartitionsExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(KafKaProperties.producerProp());

//        nullKeyProducre(producer);
//        keyProducre(producer);

        useBizPartitioner(producer);
    }

    private static void useBizPartitioner(KafkaProducer<String, String> producer) throws InterruptedException, ExecutionException {
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "login", "Hello Kafka!");
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata meteData = future.get();
        log.info("Msg no key partition is {}", meteData.partition());

        record = new ProducerRecord<>("test", "order", "Hello Kafka!");
        future = producer.send(record);
        meteData = future.get();
        log.info("Msg no key partition is {}", meteData.partition());
    }

    private static void nullKeyProducre(KafkaProducer<String, String> producer) throws InterruptedException, ExecutionException {
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "Hello Kafka!");
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata meteData = future.get();
        log.info("Msg no key partition is {}", meteData.partition());

        record = new ProducerRecord<>("test", "Hello Kafka!");
        future = producer.send(record);
        meteData = future.get();
        log.info("Msg no key partition is {}", meteData.partition());
    }

    private static void keyProducre(KafkaProducer<String, String> producer) throws InterruptedException, ExecutionException {

        ProducerRecord<String, String> record = new ProducerRecord<>("test", "msg", "Hello Kafka!");
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata meteData = future.get();
        log.info("Msg with key partition is {}", meteData.partition());

        record = new ProducerRecord<>("test", "msg", "Hello Kafka!");
        future = producer.send(record);
        meteData = future.get();
        log.info("Msg with key partition is {}", meteData.partition());
    }

}
