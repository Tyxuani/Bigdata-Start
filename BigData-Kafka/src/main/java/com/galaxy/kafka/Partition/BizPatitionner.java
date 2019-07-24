package com.galaxy.kafka.Partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class BizPatitionner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if(key == null){
            throw new IllegalArgumentException(" key is required!");
        }
        switch (key.toString().toLowerCase()){
            case "login": return 0;
            case "logout": return 1;
            case "order": return 2;
            default: throw new IllegalArgumentException(" key is required!");
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
