package galaxy.stream;

import galaxy.bean.Apple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class CreateStream {

    public static void main(String[] args) {
        fromeGenerate().forEach(System.out::println);
    }

    //1. Collection--stream()
    private static Stream<String> collectionToStream() {
        return Arrays.asList("zhouyi", "wangxu", "liyue").stream();
    }

    //2. Stream--of()
    private static Stream<String> fromValue() {
        return Stream.of("zhouyi", "wangxu", "liyue");
    }

    //Arrays--stream()
    private static Stream<String> fromeArrays() {
        String[] strs = new String[]{"zhouyi", "wangxu", "liyue"};
        return Arrays.stream(strs);
    }

    //Files--lines()
    private static Stream<String> fromeFile() {
        Path path = Paths.get("I:\\StudyProject\\Bigdata-J8\\src\\main\\java\\galaxy\\bean\\Apple.java");
        try {
            Stream<String> lines = Files.lines(path);
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Stream--iterate()
    private static Stream<Integer> fromeIterator() {
        return Stream.iterate(0, n -> n + 2).limit(5);
    }

    //Stream--generate()
    private static Stream<Apple> fromeGenerate() {
        Random random = new Random(System.currentTimeMillis());
        return Stream.generate(() -> new Apple(random.nextInt(8), "bule")).limit(5);
    }

}
