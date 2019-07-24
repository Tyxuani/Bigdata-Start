package com.galaxy.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class Tgroup {

    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {

        AtomicReference<Thread> iwork = new AtomicReference<>();

        Thread work = new Thread(() -> {
            System.out.println("Thread A= " + Thread.currentThread().getName() + " start");
            try {
                iwork.set(new Thread(() -> {
                    System.out.println("Thread iA= " + Thread.currentThread().getName() + " start");
                    for (int i = 0; i < 40; i++) {
                        if (i == 30) {
                            System.out.println("JVM exit 0");
                            Runtime.getRuntime().exit(0);
                        }

                        System.out.println("Thread iA= " + Thread.currentThread().getName() + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Thread iA= " + Thread.currentThread().getName() + " end");

                }, "iWork"));

                iwork.get().setDaemon(false);
                iwork.get().start();

                for (int i = 0; i < 15; i++) {
                    System.out.println("Thread A= " + Thread.currentThread().getName() + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Thread A= " + Thread.currentThread().getName() + " end");

        }, "Work");
        work.start();


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread[] threads = new Thread[5];
        int enumerate = work.getThreadGroup().enumerate(threads);
        System.out.println(enumerate);
        IntStream.rangeClosed(0, enumerate - 1).forEach(i -> {
            System.out.println(threads[i].getName() + " is Deamon: " + threads[i].isDaemon());
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadGroup threadGroup = new ThreadGroup("Thread_Stop");
        ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
        System.out.println("1s threadGroup1 name is " + threadGroup1.getName());
        System.out.println("1s threadGroup name is " + threadGroup.getName());

        enumerate = Thread.currentThread().getThreadGroup().enumerate(threads);
        System.out.println(enumerate);
        int bound = enumerate - 1;
        for (int i = 0; i <= bound; i++) {
            System.out.println(threads[i].getName() + " is Deamon: " + threads[i].isDaemon());
            if (threads[i].getName() != "iWork" && threads[i].getName() != "main") {
                Thread t = threads[i];
                new Thread(threadGroup, () -> {
                    try {
                        t.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        System.out.println(t.getName() + "interrupt : " + e.getMessage());
                    }
                }).start();
                t.interrupt();
            }
        }

        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadGroup1 = Thread.currentThread().getThreadGroup();
        System.out.println("10s threadGroup1 name is " + threadGroup1.getName());
        System.out.println("10s threadGroup name is " + threadGroup.getName());
        enumerate = Thread.currentThread().getThreadGroup().enumerate(threads);
        System.out.println(enumerate);
        IntStream.rangeClosed(0, enumerate - 1).forEach(i -> {
            System.out.println(threads[i].getName() + " is Deamon: " + threads[i].isDaemon());
        });
        enumerate = threadGroup.enumerate(threads);
        System.out.println(enumerate);
        IntStream.rangeClosed(0, enumerate - 1).forEach(i -> {
            System.out.println(threads[i].getName() + " is Deamon: " + threads[i].isDaemon());
        });


    }
}
