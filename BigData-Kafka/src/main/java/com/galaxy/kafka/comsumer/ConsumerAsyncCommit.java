package com.galaxy.kafka.comsumer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

//异步提交
public class ConsumerAsyncCommit {
    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(KafKaProperties.comsumerProp());
        consumer.subscribe(Collections.singleton("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record ->
            {
                log.info("offset is {}", record.offset());
                log.info("key is {}", record.key());
                log.info("value is {}", record.value());
            });

            //测试时设置props.put("enable.auto.commit", "false");
            //如果提交消费记录失败不会进行重试,也不会阻塞
            consumer.commitAsync((map, e) ->
            {
                if(e != null){
                    log.error(e.getStackTrace().toString());
                }
                log.info("key: " + map.keySet() + ", value: " + map.values());
            });
        }
    }
}
