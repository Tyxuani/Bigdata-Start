package com.galaxy.kafka.serializer;

import lombok.Data;

@Data
public class Presult {
    private int processId;
    private String rat;
    private String status;

    public Presult(int processId, String rat, String status) {
        this.processId = processId;
        this.rat = rat;
        this.status = status;
    }

    public Presult() {
    }
}
