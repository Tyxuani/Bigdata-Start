package com.galaxy.metrics.historyrams;


import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;
import static com.galaxy.metrics.common.CommonUtil.costTime;

/**
 * 直方统计图:测量数据流中值的分布
 *
 * 在一个数据集里统计分布: 最小, 最大, 平均, 某时间段内的次数
 */

public class HistorgramsExample {
    private static final MetricRegistry registry = new MetricRegistry();

    //指定度量注册的报表
    private static final ConsoleReporter repoter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();
    private static final Histogram histogram = registry.histogram("search-reuslt");
    private static final Histogram histogram2 = registry.histogram(name("search-reuslt"));


    //创建双向队列BlockingDeque用于多线程操作
    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1_000);

    public static void main(String[] args) {

        repoter.start(5, TimeUnit.SECONDS);

        while (true) {
            doSearch();
            costTime();
        }

    }

    private static void doSearch() {

        //搜索过程忽略
        //直方图统计搜索量(此处给的随机数)
        histogram.update(ThreadLocalRandom.current().nextInt(10));
    }

}
