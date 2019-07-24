package com.galaxy.jvm;

public class JVMMemorey {
    private int stackDepth = 0;

    public static void main(String[] args)
            throws Throwable {

        JVMMemorey jv = new JVMMemorey();
        jv.stackLeadByThread();


    }


    /**
     * VM Args: -Xss2M
     * 测试多线程导致内存溢出
     */
    public void stackLeadByThread(){
        while (true){
            new Thread(new Runnable() {
                public void run() {
                    dontstop();
                }
            }).start();
        }
    }

    private void dontstop() {
        while (true) {

        }
    }
}
