package com.galaxy.designPatterns;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SingletonObject {

    /**
     * 此单例模式4优点:
     * 1.懒加载
     * 2.多线程安全
     * 3.相对于方法上加synchronized性能提高
     * 4.volatile保证在读取该字段时, 写已经完成
     */

    //volatile不保证原子性, 但是保证可见性(在读取该字段时, 保证写已经完成)
    private static SingletonObject instance;
    public long a;

    private SingletonObject() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        a = 55L;
    }

    public static SingletonObject getInstance() {
        //性能优化, 多线程取到null实例就才去抢锁, 抢到锁再次确认为null才new
        if (null == instance) {
            synchronized (SingletonObject.class) {
                if (null == instance) {
                    instance = new SingletonObject();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).map(i -> {
            new Thread(() -> {
                SingletonObject instance = SingletonObject.getInstance();
                System.out.println(Thread.currentThread().getName() + "  ins: " + instance + " value: " + instance.a);
            }).start();

            return 1;
        }).sum();
    }
}
