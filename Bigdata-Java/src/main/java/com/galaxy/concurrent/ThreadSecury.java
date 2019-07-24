package com.galaxy.concurrent;

public class ThreadSecury {

    /**
     * 当多个线程访问某一个类(字段或方法)时,这个类始终都能表现出正确的行为,则该类(字段或方法)就是线程安全的
     * 线程安全在于共享区域的操作需要同步操作(同时只能有一个种操作),保证或取前已完成修改.
     *
     * */
    int num = 5;
    public static void main(String[] args){
        final ThreadSecury ts = new ThreadSecury();
        for(int i = 0; i < 1; i++)
        {
            new Thread(new Runnable() {
                public void run() {
                    ts.numDecrease();
                }
            }).start();
        }
    }

    /**
     * 对于synchronized加锁的方法称为互斥区,在多线程中只有拿到锁的线程才能进入此方法
     *
     * */
    public synchronized void numDecrease(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + " === " + num);
        if(num == 4)
        {
            System.out.println(Thread.currentThread().getName() + " === " + num);
            throw new RuntimeException();
        }

    }
}
