package com.galaxy.metrics.metricset;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

public class Application {
    private final static MetricRegistry registry = new MetricRegistry();
    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException
    {
        reporter.start(0, 5, TimeUnit.SECONDS);
        BusinessService businessService = new BusinessService();
        registry.registerAll(businessService);
        businessService.start();
        Thread.currentThread().join();//阻塞线程
    }
}
