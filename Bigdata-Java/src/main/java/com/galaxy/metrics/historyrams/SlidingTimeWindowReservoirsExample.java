package com.galaxy.metrics.historyrams;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.galaxy.metrics.common.CommonUtil.costTime;

public class SlidingTimeWindowReservoirsExample {
    private static MetricRegistry registry = new MetricRegistry();
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    //默认统计最近1028次, 按照0.015D进行指数衰减
    private static Histogram histogram = new Histogram(new SlidingTimeWindowReservoir(1000, TimeUnit.SECONDS));

    public static void main(String[] args) {

        reporter.start(5, TimeUnit.SECONDS);
        registry.register("SlidingWindowReservoir-Example", histogram);

        while (true) {
            doSearch();
            costTime();
        }
    }

    private static void doSearch() {
        histogram.update(ThreadLocalRandom.current().nextInt(10));
    }
}
