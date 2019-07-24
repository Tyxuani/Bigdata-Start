package com.galaxy.java8;

import com.galaxy.bean.Apple;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by wangwenjun on 2016/10/12.
 */
public class LambdaExpression {

    public static Supplier<Double> get() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return () -> ThreadLocalRandom.current().nextDouble(50);
    }

    public static void main(String[] args) {

        Comparator<Apple> byColor = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        };

        List<Apple> list = Collections.emptyList();

        list.sort(byColor);

        Comparator<Apple> byColor2 = (o1, o2) -> o1.getColor().compareTo(o2.getColor());

        Function<String, Integer> flambda = s -> s.length();

        Predicate<Apple> p = (Apple a) -> a.getColor().equals("green");

        Runnable r = () -> {
        };

        Consumer consumer = o -> {};


        Function<Apple, Boolean> f = (a) -> a.getColor().equals("green");

    }
}
