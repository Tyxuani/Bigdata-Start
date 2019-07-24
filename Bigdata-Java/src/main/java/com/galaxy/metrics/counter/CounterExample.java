package com.galaxy.metrics.counter;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.galaxy.metrics.common.CommonUtil.costTime;

/**
 * 容器计数器,有增减方法,可以使用此方式替换Meter
 */

public class CounterExample {
    private static final MetricRegistry registry = new MetricRegistry();
    private static final ConsoleReporter repoter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1_000);

    public static void main(String[] args) {
        repoter.start(5, TimeUnit.SECONDS);
        Counter counter = registry.counter("queue-count");

        new Thread(() -> {
            for (; ; ) {
                costTime();
                queue.add(System.nanoTime());
                counter.inc();
            }
        }).start();

        new Thread(() -> {
            for (; ; ) {
                costTime();
                queue.poll();
                counter.dec();
            }
        }).start();

    }


}
