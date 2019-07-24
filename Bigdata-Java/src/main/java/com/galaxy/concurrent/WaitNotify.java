package com.galaxy.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WaitNotify {

    private  static List list = new ArrayList();

    public void add() {
        list.add("galaxy");
    }

    public  int size() {
        return list.size();
    }

    public static void main(String[] args) {

        final WaitNotify waitNotify = new WaitNotify();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        waitNotify.add();
                        println("当前线程: " + Thread.currentThread().getName() + "添加了一个元素后..size = " );
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "|..T1..| ");

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (waitNotify.size() == 5) {
                        println("当前线程: " + Thread.currentThread().getName() + "收到信号..");
                        throw new RuntimeException();
                    }
                }
            }
        }, "|..T2..| ");

        t1.start();
        t2.start();

    }

    public static void println(String s) {
        System.out.println(new Date().toString() + s);
    }
}
