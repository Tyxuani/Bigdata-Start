package com.galaxy.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AppRun {

    public static String xl;

    public static void main(String[] args) throws Exception {
        code();

    }

    public static void code() throws IOException {
        Runtime r = Runtime.getRuntime();
        r.exec("javac -cp /src/main/java/com/galaxy/util/ StringUtil.java");
    }


    public static void allocate() throws UnsupportedEncodingException {
        ByteBuffer by = ByteBuffer.allocate(12);
        by.put("hs".getBytes(StandardCharsets.UTF_8));
        StringUtil.println(15);
    }


    public static void sameObj() {

        List ss = new ArrayList<String>();
        ss.addAll(Arrays.asList("15", "4445", "sa"));
        List ss1 = ss;
        ss1.remove("15");
        StringUtil.println(ss);
        StringUtil.println(ss1);
    }

    public static void run() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
//                    code();
                }
            }).start();
        }
    }


}
