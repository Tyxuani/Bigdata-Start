package com.galaxy.metrics.gauge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.galaxy.metrics.common.CommonUtil.costTime;

/**
 * 简式度量表Gauge, 只输出value
 * 相比于meter的mark标记, 简式度量表需要实现Gauge接口去getValue
 */

public class SimpleGauge {
    private static final MetricRegistry registry = new MetricRegistry();

    //指定度量注册的报表
    private static final ConsoleReporter repoter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    //创建双向队列BlockingDeque用于多线程操作
    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1_000);

    public static void main(String[] args) {

        //使用MetricRegistry.name创建meter放入注册表
        //使用lamada表达式(Gauge)queue::size可以替代接口实现new Gauge<Integer>() {}
        registry.register(MetricRegistry.name(SimpleGauge.class, "queue-size"), new Gauge<Integer>() {

            @Override
            public Integer getValue() {
                return queue.size();
            }
        });


        repoter.start(5, TimeUnit.SECONDS);

        //λ表达式实现Runnable接口
        new Thread(() -> {
            for (; ; ) {
                costTime();

                //获取纳秒级系统当前时间nanoTime(), 获取毫秒级系统时间currentTimeMillis()
                queue.add(System.nanoTime());
            }
        }).start();

        new Thread(() -> {
            for (; ; ) {
                costTime();

                //pick只是获得,而poll获得后会remove
                queue.poll();
            }
        }).start();


    }


}
