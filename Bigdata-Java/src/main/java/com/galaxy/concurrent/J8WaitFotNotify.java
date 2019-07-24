package com.galaxy.concurrent;

import com.galaxy.util.StringUtil;

import java.util.Optional;
import java.util.stream.IntStream;

public class J8WaitFotNotify {
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        J8WaitFotNotify.foreach();
    }

    public static void intStream() {
        IntStream.rangeClosed(1, 10).forEach(i ->
                {
                    new Thread(String.valueOf(i)) {

                        public void run() {
                            synchronized (LOCK) {
                                Optional.of(Thread.currentThread().getName() + "  wait ...").ifPresent(System.out::println);
                                try {
                                    LOCK.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Optional.of(Thread.currentThread().getName() + " out ...").ifPresent(System.out::println);
                            }
                        }
                    }.start();
                }
        );

        IntStream.rangeClosed(1, 10).forEach(i ->
                {
                    synchronized (LOCK){
                            LOCK.notify();
                    }

                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        );

    }

    public static void foreach(){
        IntStream.rangeClosed(1, 10).forEach(StringUtil::println);
    }
}
