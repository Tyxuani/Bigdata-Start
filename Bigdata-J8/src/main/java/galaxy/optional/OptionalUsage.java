package galaxy.optional;

import galaxy.bean.Apple;

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalUsage {

    public static void main(String[] args) {
        other();
    }

    private static Optional<Apple> createOptional() {
        Optional<Apple> apple1 = Optional.empty();
        Optional<Apple> apple2 = Optional.<Apple>empty();
        Optional<Apple> apple3 = Optional.of(new Apple(15, "yellow"));
        Optional<Apple> apple4 = Optional.ofNullable(null);
        Optional<Apple> apple5 = Optional.ofNullable(new Apple(15, "yellow"));

        return apple5;
    }

    private static void getValue() {
        System.out.println(createOptional().get());
        System.out.println(createOptional().orElse(new Apple(12, "green")));
        System.out.println(createOptional().orElseGet(Apple::new));
        System.out.println(createOptional().orElseThrow(RuntimeException::new));
    }

    private static void other(){
        System.out.println(createOptional().filter(apple -> apple.getWeight() > 10).get());
        System.out.println(createOptional().map(a -> a.getColor()).get());
        System.out.println(createOptional().map(Apple::getColor).isPresent());
    }
}
