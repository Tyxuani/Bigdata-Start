package com.galaxy.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgConsumerInterceptor implements ConsumerInterceptor {

    Logger log = LoggerFactory.getLogger(MsgConsumerInterceptor.class);
    @Override
    public ConsumerRecords onConsume(ConsumerRecords records) {

        List<ConsumerRecord<String, String>> recordList = new ArrayList<>();
        Map<TopicPartition, List<ConsumerRecord<String, String>>> recordMap = new HashMap<>();
        records.forEach(obj -> {
            ConsumerRecord record = (ConsumerRecord) obj;
            if(record.value().toString().toLowerCase().contains("hello")){
                recordList.add(record);
            }
        });
        ConsumerRecords re = new ConsumerRecords(recordMap);
        return re;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map offsets) {
        offsets.values().forEach(offset -> {

            log.info("======== commit start ===========");
            log.info("======== commit offset is: " + offset);
            log.info("======== commit end ===========");
        });
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
