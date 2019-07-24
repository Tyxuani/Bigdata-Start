package com.galaxy.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Map;

public class PresultSerializer implements Serializer<Presult> {

    Logger log = LoggerFactory.getLogger(PresultSerializer.class);

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Presult presult) {
        if (topic == null || topic.length() == 0) {
            log.error("There is no topic, please provide the topic!");
            return null;
        }

        int id = presult.getProcessId();
        String rat = presult.getRat();
        String status = presult.getStatus();

        byte[] ratBytes;
        if(rat != null || rat.length() != 0 )
        {
            ratBytes = rat.getBytes();
        }else {
            ratBytes = "null".getBytes();
        }

        byte[] statBytes;
        if(status != null || status.length() != 0 )
        {
            statBytes = status.getBytes();
        }else {
            statBytes = "null".getBytes();
        }


        //开辟字节数组缓存, 4byte存id, 4byte存rat长度, rat长度位存rat值, 以此类推
        ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + ratBytes.length + 4 + statBytes.length);

        buffer.putInt(id);
        buffer.putInt(ratBytes.length);
        buffer.put(ratBytes);
        buffer.putInt(statBytes.length);
        buffer.put(statBytes);

        return buffer.array();
    }

    @Override
    public void close() {

    }
}
