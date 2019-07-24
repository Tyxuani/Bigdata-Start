package com.galaxy.metrics.timer;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.galaxy.util.StringUtil;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 对Historgrams的抽取, 在时间维度上统计, 使用指数衰变的方式
 *
 * */
public class TimerExample {
    private static MetricRegistry registry = new MetricRegistry();
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private final static Timer timer = registry.timer("request");

    public static void main(String[] args){
        reporter.start(10, TimeUnit.SECONDS);
        while(true){
            business();
        }
    }

    private static void business(){

        //业务开始标记
        Timer.Context context = timer.time();
        try{

            //业务处理耗时
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {

            //业务停止标记
            long stop = context.stop();
            StringUtil.println("=======" + stop);
        }
    }
}
