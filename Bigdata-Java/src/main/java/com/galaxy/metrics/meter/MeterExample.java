package com.galaxy.metrics.meter;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

import static com.galaxy.metrics.common.CommonUtil.costTime;

public class MeterExample {
    private final static MetricRegistry registry = new MetricRegistry();
    public static Meter rate = registry.meter("requestRate");
    public static Meter size = registry.meter("requestSize");

    public static void main(String[] args) {

        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.MINUTES).build();

        reporter.start(10, TimeUnit.SECONDS);

        while (true) {
            handleRequest(new byte[]{15, 23, 56, 8});
            costTime();
        }
    }

    private static void handleRequest(byte[] bytes) {
        rate.mark();
        size.mark(bytes.length);
        costTime();
    }

}
