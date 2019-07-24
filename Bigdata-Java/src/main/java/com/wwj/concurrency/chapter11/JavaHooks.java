package com.wwj.concurrency.chapter11;

public class JavaHooks {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("application ready to exit");
            notifyAndRealease();
        }));

        int i = 0;
        while (true){
            try{
                Thread.sleep(1_000L);
                System.out.println("working ... " + i);
            } catch (InterruptedException e) {
            }
            i++;
            if(i > 20) throw new RuntimeException("error");
        }
    }

    private static void notifyAndRealease() {
        System.out.println("notified and release lock");
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {

        }
        System.out.println("releaase OK");
    }
}
