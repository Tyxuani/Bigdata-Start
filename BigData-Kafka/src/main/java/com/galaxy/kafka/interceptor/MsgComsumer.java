package com.galaxy.kafka.interceptor;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG;


public class MsgComsumer {

    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {

        Properties properties = KafKaProperties.comsumerProp();
        properties.put(INTERCEPTOR_CLASSES_CONFIG, "com.galaxy.kafka.interceptor.MsgConsumerInterceptor");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("test"));
        while (true) {
            Iterator<ConsumerRecord<String, String>> iterator = consumer.poll(1000).iterator();
            if (iterator.hasNext()) {
                {
                    ConsumerRecord<String, String> record = iterator.next();
                    log.info("offset is {}", record.offset());
                    log.info("key is {}", record.key());
                    log.info("value is {}", record.value());
                }
            }
        }
    }
}
