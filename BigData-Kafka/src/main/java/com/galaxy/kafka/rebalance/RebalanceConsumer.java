package com.galaxy.kafka.rebalance;

import com.galaxy.kafka.common.KafKaProperties;
import com.galaxy.kafka.interceptor.MsgComsumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG;

/**
 * 测试Kafka的Rebalance时可以使用在有consumer消费消息时增删同组consumer
 * 同组增删consumer时要求GroupId一致,但是ClientId不一致.
 *
 * */
public class RebalanceConsumer {
    private static Logger log = LoggerFactory.getLogger(MsgComsumer.class);

    public static void main(String[] args) {

        Properties properties = KafKaProperties.comsumerProp();
        properties.put("group.id", "G1");
        properties.put("client.id", "C1");
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(INTERCEPTOR_CLASSES_CONFIG, "com.galaxy.kafka.interceptor.MsgConsumerInterceptor");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("test"), new ConsumerRebalanceListener() {
            @Override   //回收已分配的partitions
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.info("onPartitionsRevoked => {}", partitions);
            }

            @Override   //重新分配partitions
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                log.info("onPartitionsAssigned => {}", partitions);
            }
        });
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> {

                //business process
                log.info("partition {}, offset {}, key {}, value {}.", record.partition(), record.offset(),
                        record.key(), record.value());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            consumer.commitSync();
        }
    }

    private static class MyConsumerRebalanceListener implements ConsumerRebalanceListener{

        private final KafkaConsumer<String, String> consumer;

        private MyConsumerRebalanceListener(KafkaConsumer<String, String> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            for (TopicPartition partition : partitions){
                long nextOffst = consumer.position(partition);
                //将此consumer消费的partition映射出下一个读取位置后,将此映射关系存入磁盘或数据库
            }
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            for (TopicPartition partition : partitions){

                //seek第二个参数是读取onPartitionsRevoked中保存的partition的下个一读取位置
                consumer.seek(partition, 25);
            }
        }
    }
}

