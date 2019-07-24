package com.galaxy.kafka.producer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

//发完就忘
public class MsgProducer {

    private final static Logger log = LoggerFactory.getLogger(MsgProducer.class);

    public static void main(String[] args) {
        syncSend();
    }

    /**
     * We call the send() method with a callback function, which gets triggered when it
     * receives a response from the Kafka broker.
     */
    private static void asyncSend() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafKaProperties.producerProp());
        IntStream.range(0, 10).forEach(i -> {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("test", String.valueOf(i), "hello kafKa ka  " + i);
            Future<RecordMetadata> future = producer.send(record, (r, e) -> {
                if (e == null) {
                    log.info("The message is send done and key is {}, offset is {}", i, r.offset());
                }
            });
            try {
                //get为阻塞式,拿不到应答数据程序会一直等待
                RecordMetadata metadata = future.get();
                log.info("The message is send done and key is {}, offset is {}", i, metadata.offset());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * We send a message, the send() method returns a Future object,and we use get()
     * to wait on the future and see if the send() was successful or not.
     */
    private static void syncSend() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(KafKaProperties.producerProp());
        IntStream.range(0, 10).forEach(i -> {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("test1", String.valueOf(i), "你是谁  " + i);
            Future<RecordMetadata> future = producer.send(record);
            try {
                //get为阻塞式,拿不到应答数据程序会一直等待
                RecordMetadata metadata = future.get();
                log.info("The message is send done and key is {}, offset is {}", i, metadata.offset());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

    //We send a message to the server and don’t really care if it arrives succesfully or not.
    private static void produceMsg() {

        KafkaProducer<String, String> producer = new KafkaProducer<>(KafKaProperties.producerProp());

        IntStream.range(0, 10).forEach(i ->
        {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("test2", String.valueOf(i), "Hello " + i);
            producer.send(record);
            log.info("The message is send done and key is {}", i);
        });
        producer.flush();
        producer.close();
    }


}
