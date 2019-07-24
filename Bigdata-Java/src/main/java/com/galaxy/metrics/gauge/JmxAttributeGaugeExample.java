package com.galaxy.metrics.gauge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxAttributeGauge;
import com.codahale.metrics.MetricRegistry;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.concurrent.TimeUnit;

public class JmxAttributeGaugeExample {

    private static final MetricRegistry registry = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws MalformedObjectNameException, InterruptedException {
        reporter.start(10, TimeUnit.SECONDS);
        registry.register(MetricRegistry.name(JmxAttributeGauge.class, "HeapMemory"), new JmxAttributeGauge(
                new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage"
        ));
        registry.register(MetricRegistry.name(JmxAttributeGauge.class, "NonHeapMemoryUsage"), new JmxAttributeGauge(
                new ObjectName("java.lang:type=Memory"), "NonHeapMemoryUsage"
        ));

        //程序一直等待唤醒
        Thread.currentThread().join();
    }
}
