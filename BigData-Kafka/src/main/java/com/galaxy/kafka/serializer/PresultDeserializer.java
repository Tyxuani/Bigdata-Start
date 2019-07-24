package com.galaxy.kafka.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Map;

public class PresultDeserializer implements Deserializer<Presult> {
    Logger log = LoggerFactory.getLogger(PresultDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Presult deserialize(String topic, byte[] data) {
        if (topic == null || topic.length() == 0) {
            log.error("There is no topic, please provide the topic!");
            return null;
        }

        if (data.length < 12)
        {
            log.error("receive invalid data, it's not a Presult object");
            return null;
        }

        ByteBuffer wrap = ByteBuffer.wrap(data);
        int processId = wrap.getInt();

        int ratLength = wrap.getInt();
        byte[] ratBytes = new byte[ratLength];
        wrap = wrap.get(ratBytes);
        String rat = new String(ratBytes);

        int statLength = wrap.getInt();
        byte[] statBytes = new byte[statLength];
        wrap = wrap.get(statBytes);
        String status = new String(statBytes);

        return new Presult(processId, rat, status);
    }

    @Override
    public void close() {

    }
}
