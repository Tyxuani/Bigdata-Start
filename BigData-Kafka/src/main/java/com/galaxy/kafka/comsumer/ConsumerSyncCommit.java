package com.galaxy.kafka.comsumer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

//同步提交消费记录
public class ConsumerSyncCommit {

    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(KafKaProperties.comsumerProp());
        consumer.subscribe(Collections.singleton("test1"));

        final AtomicInteger counter = new AtomicInteger();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record ->
            {
                log.info("partition is " + record.partition() + ", offset is " + record.offset()
                         + ", key is " + record.key() + ", value is " + record.value());
                if (counter.incrementAndGet() == 3) {
                    consumer.commitSync();
//                    counter.set(0);
                }
            });

            //测试时设置props.put("enable.auto.commit", "false");
            //如果提交消费记录失败会进行重试,只有成功提交后才能继续消费后面数据
//            consumer.commitSync();
        }
    }
}