package com.galaxy.kafka.comsumer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class MsgComsumer {

    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(KafKaProperties.comsumerProp());
        consumer.subscribe(Collections.singleton("test"));

        final AtomicInteger counter = new AtomicInteger();
        while (true) {
            Iterator<ConsumerRecord<String, String>> iterator = consumer.poll(1000).iterator();
            if (iterator.hasNext()) {
                {
                    ConsumerRecord<String, String> record = iterator.next();
                    log.info("offset is {}", record.offset());
                    log.info("key is {}", record.key());
                    log.info("value is {}", record.value());
                    int cnt = counter.incrementAndGet();
                    if(cnt >= 3)
                    {
                        //throw new RuntimeException();     //抛出异常来终止程序
                        //改用halt钩子强制立即终止进程, 保证consumer不会在期间发消息
                        Runtime.getRuntime().halt(-1);
                    }
                }
            }
        }
    }
}
