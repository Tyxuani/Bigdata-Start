package galaxy.reference;

import galaxy.bean.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * Consumer<T>--accept只有一个入参, 没有返回值
 * Function<A, B>--apply传入入参类型A, 返回值类型B
 * BiFunction<A, B, C>--apply传入入参类型A,B, 返回值类型C
 * Predicate<T>--test接收一个入参, 返回boolean类型
 * Supplier<T>--get无参方法返回T类型值
 * */

public class MethodReference {
    public static void main(String[] args) {

        referenceSort();


    }

    private static void consumerTest() {
        Consumer<String> consumer = (s) -> System.out.println(s);
        useConsumer(consumer, "Hello Alesx");

        useConsumer((s) -> System.out.println(s), "Hello Lace");

        useConsumer(System.out::println, "engage");
    }

    private static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
    }

    private static void lambdaSort() {
        List<Apple> apples = Arrays.asList(new Apple(12, "blue"),
                new Apple(25, "green"),
                new Apple(16, "yellow"));
        System.out.println(apples);
        apples.sort((a, b) -> a.getWeight() - b.getWeight());
        System.out.println(apples);
        apples.forEach(System.out::println);
    }

    private static void functionTest() {
        int value = Integer.parseInt("123");

        //推断类的静态方法
        Function<String, Integer> f = Integer::parseInt;
        System.out.println(f.apply("45") == 45);

        //推断入参对象的方法
        BiFunction<String, Integer, Character> f2 = String::charAt;
        System.out.println(f2.apply("Vaporate", 3) == 'o');

        //推断对象的方法
        String s = "Goolmy";
        Function<Integer, Character> f3 = s::charAt;
        System.out.println(f3.apply(3) == 'l');

        //推断构造方法
        BiFunction<Integer, String, Apple> f4 = Apple::new;
        System.out.println(f4.apply(45, "red"));
    }

    private static void referenceSort() {
        List<Apple> apples = Arrays.asList(new Apple(12, "blue"),
                new Apple(25, "green"),
                new Apple(16, "yellow"));
        System.out.println(apples);
        apples.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(apples);
    }
}
