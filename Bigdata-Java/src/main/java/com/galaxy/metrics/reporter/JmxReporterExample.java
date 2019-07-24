package com.galaxy.metrics.reporter;


import com.codahale.metrics.*;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 该报告存在于jvm内, 可以通过jconsole查看
 *
 * success rate     成功率
 * total business   总的任务数           --->counter
 * total success    总的成功数
 * total failure    总的失败次数
 * timer            时间维度的趋势统计报表
 * volume bytes     吞吐量
 *
 * */
public class JmxReporterExample {

    private final static Counter totalBusiness = new Counter();
    private final static Counter successBusiness = new Counter();
    private final static Counter failBusiness = new Counter();

    //用于趋势统计
    private final static Timer timer = new Timer();

    //用于预测统计
    private final static Histogram volumeHisto = new Histogram(new ExponentiallyDecayingReservoir());

    //成功率统计
    private final static RatioGauge successGauge = new RatioGauge() {
        @Override
        protected Ratio getRatio() {

            //概率计算规则: (分子, 分母)
            return Ratio.of(successBusiness.getCount(), totalBusiness.getCount());
        }
    };

    private final static MetricRegistry registry = new MetricRegistry();

    //JVM内属性, 通过jconsole查看
    private final static JmxReporter report = JmxReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    //控制台报告, 可以通过控制台查看
    private static ConsoleReporter console = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    //控制台报告, 可以通过控制台查看
    private static CsvReporter csv = CsvReporter.forRegistry(registry)
            .formatFor(Locale.CHINA)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build(new File("I:\\StudyProject\\BigdataSpark\\target"));

    static {

        //监控云盘上传功能指标
        registry.register("cloud-disk-upload-total", totalBusiness);
        registry.register("cloud-disk-upload-success", successBusiness);
        registry.register("cloud-disk-upload-failure", failBusiness);
        registry.register("cloud-disk-upload-frequency", timer);
        registry.register("cloud-disk-upload-volume", volumeHisto);
        registry.register("cloud-disk-upload-sucGauge", successGauge);
    }

    public static void main(String[] args){
        report.start();
        console.start(10, TimeUnit.SECONDS);
        csv.start(5, TimeUnit.SECONDS);

        while (true){
            upload(new byte[ThreadLocalRandom.current().nextInt(10000)]);
        }
    }

    private static void upload(byte[] buffer){

        //每次业务进入就对总任务数+1
        totalBusiness.inc();

        //业务一开始就标记趋势统计开始
        Timer.Context context = timer.time();

        try{

            //除以随机数(分母可能为0)制造异常错误进入异常分支
            int x = 1 / ThreadLocalRandom.current().nextInt(10);

            //监控吞吐量
            volumeHisto.update(buffer.length);

            //模拟业务逻辑耗时
            TimeUnit.MILLISECONDS.sleep(200);

            //业务处理成功时对总成功数+1
            successBusiness.inc();
        } catch (Exception e) {

            //业务处理失败时对总失败数+1
            failBusiness.inc();
        }finally {

            //业务结束标记趋势统计结束, stop可以取到每次业务处理时长, 如果不需要业务处理时长可以直接close
            context.close();
        }
    }
}
