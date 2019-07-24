package com.galaxy.metrics.metricset;

import com.codahale.metrics.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BusinessService extends Thread implements MetricSet {


    private final Map<String, Metric> metrics = new HashMap<>();

    private final Counter totalBusiness = new Counter();
    private final Counter successBusiness = new Counter();
    private final Counter failBusiness = new Counter();
    private final Timer timer = new Timer();
    private final Histogram volumeHisto = new Histogram(new ExponentiallyDecayingReservoir());

    public BusinessService() {
        metrics.put("cloud-disk-upload-total", totalBusiness);
        metrics.put("cloud-disk-upload-success", successBusiness);
        metrics.put("cloud-disk-upload-failure", failBusiness);
        metrics.put("cloud-disk-upload-frequency", timer);
        metrics.put("cloud-disk-upload-volume", volumeHisto);
    }

    @Override
    public void run() {
        while (true) {
            upload(new byte[ThreadLocalRandom.current().nextInt(10_000)]);
        }
    }

    private void upload(byte[] bytes) {
        //每次业务进入就对总任务数+1
        totalBusiness.inc();

        //业务一开始就标记趋势统计开始
        Timer.Context context = timer.time();

        try{
            int x = 1 / ThreadLocalRandom.current().nextInt(10);
            TimeUnit.MILLISECONDS.sleep(200);
            successBusiness.inc();
        } catch (Exception e) {
            failBusiness.inc();
        }finally {
            context.close();
        }
    }

    @Override
    public Map<String, Metric> getMetrics() {
        return metrics;
    }
}
