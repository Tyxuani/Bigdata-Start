package com.galaxy.kafka.serializer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class PresultConsumer {
    static Logger log = LoggerFactory.getLogger(PresultProducer.class);

    public static void main(String[] args) {
        Properties properties = KafKaProperties.comsumerProp();
        properties.put("value.deserializer", "com.galaxy.kafka.serializer.PresultDeserializer");

        KafkaConsumer<String, Presult> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("test"));
        while (true){
            ConsumerRecords<String, Presult> records = consumer.poll(30);
            records.forEach(record -> {
                Presult presult = record.value();
                log.info("get Presult message success: " + presult.toString());
            });
        }
    }
}
