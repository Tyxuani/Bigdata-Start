package com.galaxy.metrics.gauge;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.galaxy.util.StringUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 具备缓存能力的度量器,可以缓存度量值,避免影响被监控程序的性能
 * 在某段时间内,只获取一次度量值缓存
 * 该度量值对时间要求不高,但是度量值重要,获取一次可能造成性能影响的可以以此处理
 */

public class CachedGuageExample {

    private static final MetricRegistry registry = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException {
        reporter.start(10, TimeUnit.SECONDS);

        //30内的度量数据在缓存内获取,
        registry.register("cached-db-size", new CachedGauge<Long>(30, TimeUnit.SECONDS) {
            @Override
            protected Long loadValue() {
                return queryFromDB();
            }
        });

        StringUtil.println("====== " + new Date() + "  start ========");
        Thread.currentThread().join();
    }


    private static long queryFromDB() {
        StringUtil.println("====== queryFromDB ==========");
        return System.currentTimeMillis();
    }
}
