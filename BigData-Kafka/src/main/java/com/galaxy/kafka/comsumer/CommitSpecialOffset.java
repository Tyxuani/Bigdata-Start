package com.galaxy.kafka.comsumer;

import com.galaxy.kafka.common.KafKaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//指定Offset提交
public class CommitSpecialOffset {
    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(KafKaProperties.comsumerProp());
        consumer.subscribe(Collections.singleton("test"));
        Map<TopicPartition, OffsetAndMetadata> offset = new HashMap<>();
        TopicPartition partition = new TopicPartition("test", 0);

        //指定下次从partition的那个位置开始读
        OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(16, "no meta data");
        offset.put(partition, offsetAndMetadata);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record ->
            {
                log.info("offset is {}", record.offset());
                log.info("key is {}", record.key());
                log.info("value is {}", record.value());
            });

            //指定offset提交避免数据丢失
            consumer.commitSync(offset);
        }

        /**
         * 重复数据出现的原因
         * 1. poll到数据后,还没来得及执行Commit到Broker程序就中断.
         * 2. poll到数据后,在执行Commit时出错(网络连接失败)
         *
         * */
    }
}
