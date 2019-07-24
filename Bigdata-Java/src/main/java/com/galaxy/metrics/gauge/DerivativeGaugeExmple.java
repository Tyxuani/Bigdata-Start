package com.galaxy.metrics.gauge;

import com.codahale.metrics.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * 派生gauge,需要从已有gauge派生.
 */

public class DerivativeGaugeExmple {

    //使用CacheBuilder来build一个LoadingCache<String, String>
    private static final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(10)    //最大元素10,当超出10个就cache
            .expireAfterAccess(5, TimeUnit.SECONDS)     //5秒内不使用就超时
            .recordStats()      //获取状态
            .build(new CacheLoader<String, String>() {      //使用懒加载方式build,避免无数据时也加载
                @Override
                public String load(String s) throws Exception {
                    return s.toUpperCase();
                }
            });

    private static final MetricRegistry registry = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();


    public static void main(String[] args) throws InterruptedException {

        reporter.start(10, TimeUnit.SECONDS);
        Gauge cacheStats = registry.register("cache-stats", new CachedGauge<CacheStats>(10, TimeUnit.SECONDS) {
            @Override
            protected CacheStats loadValue() {
                return cache.stats();
            }
        });

        //从已有Gauge里派生新的gauge并注册到注册表里,新的gauge只监控原度量的某个具体值
        registry.register("missCount", new DerivativeGauge<CacheStats, Long>(cacheStats) {
            @Override
            protected Long transform(CacheStats cacheStats) {
                return cacheStats.missCount();
            }
        });

        while (true) {
            business();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void business() {
        //使用cache
        cache.getUnchecked("Galaxy");
    }
}
