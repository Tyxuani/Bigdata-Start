package com.galaxy.kafka.comsumer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

//同步和异步同时使用,降低重复数据出现概率和提高吞吐量
public class AsyncAndSyncSubmit {
    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(KafKaProperties.comsumerProp());
        consumer.subscribe(Collections.singleton("test"));
        try {

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record ->
                {
                    log.info("offset is {}", record.offset());
                    log.info("key is {}", record.key());
                    log.info("value is {}", record.value());
                });

                //先异步提交避免阻塞,提高吞吐
                consumer.commitAsync();
            }
        } finally {
            //避免异步提交出错
            consumer.commitSync();
        }
    }

}
