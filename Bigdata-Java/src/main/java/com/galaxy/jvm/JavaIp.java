package com.galaxy.jvm;

import java.lang.reflect.Array;
import java.net.InetAddress;

public class JavaIp {
    public static void main(String[] args){
        System.out.println("getLocalHost " + System.getProperty("java.class.path"));
    }

    public static String getIp()
            throws Exception {
        return InetAddress.getLocalHost().toString();
    }
}
