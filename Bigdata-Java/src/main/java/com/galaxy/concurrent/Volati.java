package com.galaxy.concurrent;

import java.util.concurrent.TimeUnit;

public class Volati {

    private volatile static int INIT = 0;
    private final static int MAX = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start");
            delay(100);

            int local = INIT;
            while (local < MAX) {
                if(local != INIT) {
                    System.out.println(Thread.currentThread().getName() + "INIT been updata to " + INIT);
                    local = INIT;
                }
            }
        }, "read-1--   ").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start");
            delay(100);

            int local = INIT;
            while (local < MAX) {
                if(local != INIT) {
                    System.out.println(Thread.currentThread().getName() + "INIT been updata to " + INIT);
                    local = INIT;
                }
            }
        }, "read-2--   ").start();



        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start");
            delay(100);

            int local = INIT;
            while (INIT < MAX) {
                System.out.println(Thread.currentThread().getName() + "updata INIT to " + ++local);
                INIT = local;
                delay(1);
            }
        }, "write---  ").start();


    }

    private static void delay(int time) {
        try {
            TimeUnit.MICROSECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


}
