package galaxy.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStream {


    public static void main(String[] args) {

        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println(stream.filter(i -> i.intValue() > 3).reduce(0, Integer::sum));

        //使用基本数据类型将对象拆箱可以大幅度提高效率
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println(stream.mapToInt(Integer::intValue).filter(i -> i > 3).sum());

        int a = 9;
        IntStream.rangeClosed(1, 100)
                .boxed()
                .filter(i -> Math.sqrt(i * i + a * a) % 1 == 0)
                .forEach(System.out::println);

    }

}
