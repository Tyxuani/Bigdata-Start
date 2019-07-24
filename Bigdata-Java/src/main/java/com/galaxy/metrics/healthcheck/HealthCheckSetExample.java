package com.galaxy.metrics.healthcheck;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;

import java.util.concurrent.TimeUnit;

public class HealthCheckSetExample
{
    public static void main(String[] args) throws InterruptedException
    {
        final HealthCheckRegistry hcRegistry = new HealthCheckRegistry();
        hcRegistry.register("restful-hc", new RESTfulServiceHealthCheck());
        hcRegistry.register("thread-dead-lock-hc", new ThreadDeadlockHealthCheck());

        final MetricRegistry registry = new MetricRegistry();
        final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .build();
        registry.gauge("restful-hc", () -> hcRegistry::runHealthChecks);
        reporter.start(2, TimeUnit.SECONDS);
        Thread.currentThread().join();
    }
}
