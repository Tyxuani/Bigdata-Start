package com.galaxy.kafka.interceptor;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.apache.kafka.clients.producer.ProducerConfig.INTERCEPTOR_CLASSES_CONFIG;


//发完就忘
public class MsgProducer {

    private final static Logger log = LoggerFactory.getLogger(MsgProducer.class);

    public static void main(String[] args) {
        syncSend();
    }

    private static void syncSend() {
        Properties properties = KafKaProperties.comsumerProp();
        properties.put(INTERCEPTOR_CLASSES_CONFIG, "com.galaxy.kafka.interceptor.MsgProducerInterceptor");
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafKaProperties.producerProp());
        IntStream.range(0, 10).forEach(i -> {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("test", String.valueOf(i), "你是谁  " + i);
            ProducerRecord<String, String> record1 =
                    new ProducerRecord<>("test", String.valueOf(i), "你是谁  " + i);
            Future<RecordMetadata> future = producer.send(record);
            Future<RecordMetadata> future1 = producer.send(record1);
            try {

                RecordMetadata metadata = future.get();
                RecordMetadata metadata1 = future1.get();
                log.info("The message is send done and key is {}, offset is {}", i, metadata.offset());
                log.info("The message1 is send done and key is {}, offset is {}", i, metadata1.offset());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

}
