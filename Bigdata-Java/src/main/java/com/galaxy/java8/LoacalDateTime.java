package com.galaxy.java8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.stream.IntStream;

public class LoacalDateTime {

    public static void main(String[] args) throws InterruptedException {
        LocalTime time = LocalTime.now();
        LocalTime before = time.minusHours(1);
        Duration duration = Duration.between(time, before);
        System.out.println(duration.toHours());
    }

    private static void insDur() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }

    private static void compose() {
        System.out.println(LocalDate.now() + " " + LocalTime.now());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
    }

    private static void localDateBase() {
        LocalDate date = LocalDate.of(2019, 5, 3);
        System.out.println(date.getYear());
        System.out.println(date.getMonth());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfYear());
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getDayOfWeek());
    }

    private static void oldBug() throws InterruptedException {
        Date date = new Date(116, 2, 8);
        System.out.println(date);
        SimpleDateFormat pas = new SimpleDateFormat("yyyyMMdd");

        IntStream.rangeClosed(1, 30).forEach(i -> {
            new Thread(() -> {
                for (int x = 0; x < 100; x++) {
                    Date pa = null;
                    try {
                        pa = pas.parse("20160505");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(pa);
                }
            }).start();
        });
    }
}
