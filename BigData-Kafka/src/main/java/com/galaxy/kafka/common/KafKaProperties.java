package com.galaxy.kafka.common;

import java.util.Properties;

public class KafKaProperties {
    private static String stringserializer = "org.apache.kafka.common.serialization.StringSerializer";
    private static String stringdeserializer = "org.apache.kafka.common.serialization.StringDeserializer";


    private static String zookeepers = "192.8.0.10:2181,192.8.0.12:2181,192.8.0.13:2181";
    private static String bootstraps = "192.8.0.10:9092,192.8.0.12:9092,192.8.0.13:9092";
    private static String partitioner = "com.galaxy.kafka.Partition.BizPatitionner";

    public static Properties producerProp() {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstraps);
        props.put("key.serializer", stringserializer);
        props.put("value.serializer", stringserializer);

        //acks  0 发送后不关心到达, 1 发送后等待应答(不关心同步到其他broker), all 发送后等待同步成功并得到应答
        //props.put("acks", "1");

        //总的消息缓存大小, 缓存内放着多个batch
        //props.put("buffer.memory", "1024000");

        props.put("compression.type", "snappy");
        props.put("retries", "3");

        //消息batch大小, 满一batch就通过Sender发到leader, 发出后未拿到相应的称为in-fight
        props.put("batch.size", "1024");
        props.put("linger.ms", "2000");

        //用于服务端识别哪个客户端
        props.put("client.id", "test_client0");

        //最大未应答消息数,设置为1时保证发送顺序
        props.put("max.in.flight.requests.per.connection", "1");

//        props.put("partitioner.class", partitioner);


        return props;
    }

    public static Properties comsumerProp(){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstraps);
        props.put("key.deserializer", stringdeserializer);
        props.put("value.deserializer", stringdeserializer);
        props.put("group.id", "118");
        props.put("client.id", "test_client8");
        props.put("auto.commit.interval.ms", "10");

        //fetch两配置达到一个条件就返回响应给consumer
//        props.put("fetch.min.bytes", "1024");
//        props.put("fetch.max.wait.ms", "200");
//        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest"); //latest, earliest




        return props;
    }
}
