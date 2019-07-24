package com.galaxy.java8;

import com.galaxy.bean.Apple;
import com.galaxy.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangwenjun on 2016/10/12.
 */
public class FilterApple {

    //当接口只有一个方法时,可以将该接口用@FunctionalInterface标注,之后就可以用lambda表达式使用
    //不加注释也可以是lambda表达式,但是建议最好加上以使代码严谨
    @FunctionalInterface
    private interface AppleFilter {

        boolean filter(Apple apple);

    }

    //3.需求上升为按照任意条件筛选苹果,因此传入条件可封装为实现过滤规则接口的对象
    private static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (appleFilter.filter(apple))
                list.add(apple);
        }
        return list;
    }

    private static class GreenAnd160WeightFilter implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return (apple.getColor().equals("green") && apple.getWeight() >= 160);
        }
    }

    private static class YellowLess150WeightFilter implements AppleFilter {

        @Override
        public boolean filter(Apple apple) {
            return (apple.getColor().equals("yellow") && apple.getWeight() < 150);
        }
    }

    //1.原始需求筛选出绿色苹果,提取条件绿色
    private static List<Apple> findGreenApple(List<Apple> apples) {

        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                list.add(apple);
            }
        }

        return list;
    }

    //2.需求提升为筛选出指定颜色苹果,提取条件颜色,此时条件仍然为基本类型
    private static List<Apple> findApple(List<Apple> apples, String color) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {
                list.add(apple);
            }
        }

        return list;
    }

    public static void main(String[] args) throws InterruptedException {

        List<Apple> list = Arrays.asList(new Apple("green", 150),
                new Apple("yellow", 120),
                new Apple("green", 170));

        /* List<Apple> greenApples = findGreenApple(list);
        StringUtil.println(greenApples);

        List<Apple> greensApples = findApple(list, "green");
        StringUtil.println(greensApples);

        List<Apple> redApples = findApple(list, "red");
        StringUtil.println(redApples); */

        List<Apple> result = findApple(list, new GreenAnd160WeightFilter());
        StringUtil.println(result);

        //4.根据需求三得新增大量实现类,为了代码优化则可以使用匿名函数的方式优化
        /*List<Apple> yellowList = findApple(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor());
            }
        });
        StringUtil.println(yellowList);*/

        //lambda表达式会推导出入参和结果的类型
        List<Apple> lambdaResult = findApple(list, apple ->
                "green".equals(apple.getColor()));
        StringUtil.println(lambdaResult);

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        //当lambda表达式待实现的方法发无参时使用()占位
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        Thread.currentThread().join();
    }


}
