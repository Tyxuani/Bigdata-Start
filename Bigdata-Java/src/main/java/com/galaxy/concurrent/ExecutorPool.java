package com.galaxy.concurrent;

import java.io.PrintStream;
import java.util.concurrent.*;

public class ExecutorPool {

    static PrintStream log = System.out;

    static ExecutorService jobPool;

    public static void main(String[] args) throws Exception {
        jobPool = new ThreadPoolExecutor(
                3,
                5,
                3L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r, "JobRunner-- " + Thread.activeCount() );
            }
        }
        );
        for (int i = 1; i < 20; i++) {
            log.println("[ " + Thread.currentThread().getName() + " ]"
                    + "submit job start");
            jobPool.execute(new JobRunner(i, log));
            log.println(jobPool.toString());
//            Thread.currentThread().sleep(2000L);
            log.println("[ " + Thread.currentThread().getName() + " ]"
                    + "submit job success");
        }
        jobPool.shutdown();
    }
}


class JobRunner implements Runnable {
    int runnerId;
    PrintStream log;

    JobRunner(int runnerId, PrintStream log) {
        this.runnerId = runnerId;
        this.log = log;
    }

    public void run() {
        long start = System.currentTimeMillis();
        log.println("[ " + Thread.currentThread().getName() + " *** " + runnerId + " ]" + "JobRunner start task.........");
        try {
            Thread.currentThread().sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        log.println("[ " + Thread.currentThread().getName() + " *** " + runnerId + " ]" + "JobRunner end task....." + (finish - start) + "....");
    }
}