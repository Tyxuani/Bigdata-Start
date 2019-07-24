package com.galaxy.concurrent;

public class AccessStaticFiled {

    /**
     * 对字段加static,多线程操作同一字段
     *
     * 字段不加static在多个线程对其操作时,互不影响, 字段在各自线程内私有
     * 字段加static后在内存里共享
     * */
    private static int num = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                new AccessStaticFiled().printNum("a");
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                new AccessStaticFiled().printNum("b");
            }
        }).start();
    }

    /**
     *  对static的方法加synchronized,多线程使用同一锁.class访问同一块代码
     *
     * 对static的方法加synchronized会让方法获得该类.class为锁,同时只能有一个线程能进入该方法
     * 多线程竞争同一个类锁
     * */
    public static synchronized void printNum(String tag) {
        try {
            if (tag.equals("a")) {
                num = 100;
                println("tag a, set num over!");
                Thread.sleep(1000);
            } else {
                num = 200;
                println("tag b, set num over!");
            }
            println("tag " + tag + ", num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void println(String s) {
        System.out.println(s);
    }
}
