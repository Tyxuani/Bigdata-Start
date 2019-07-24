package com.galaxy.kafka.serializer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

public class PresultProducer {
    static Logger log = LoggerFactory.getLogger(PresultProducer.class);

    public static void main(String[] args) {

        Properties properties = KafKaProperties.producerProp();
        properties.put("value.serializer", "com.galaxy.kafka.serializer.PresultSerializer");

        KafkaProducer<String, Presult> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, Presult> record = new ProducerRecord("test", new Presult(15, "LTE", "Complete"));
        Future<RecordMetadata> future = producer.send(record);
        try {
            RecordMetadata metadata = future.get();
            int partition = metadata.partition();
            long offset = metadata.offset();
            log.info("Presult message send success, partition " + partition + ", offset " + offset);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
