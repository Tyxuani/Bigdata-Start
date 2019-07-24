package com.galaxy.jvm;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SignalProcess {
    public static void main(String[] args) throws InterruptedException {
        // 信号处理实例
        MySignalHandler mySignalHandler = new MySignalHandler();

        // 注册对指定信号的处理
        Signal.handle(new Signal("TERM") ,mySignalHandler);    // kill or kill -15
        Signal.handle(new Signal("INT"), mySignalHandler);     // kill -2

        System.out.println("[Thread:"+Thread.currentThread().getName() + "] is sleep" );
        while(true) Thread.sleep(1000);
    }
}

class MySignalHandler implements SignalHandler {

    public void handle(Signal signal) {

        // 信号量名称
        String name = signal.getName();
        // 信号量数值
        int number = signal.getNumber();

        // 当前进程名
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("[Thread:"+currentThreadName + "] receved signal: " + name + " == kill -" + number);
        if(name.equals("TERM")){
            System.exit(0);
        }
    }

}