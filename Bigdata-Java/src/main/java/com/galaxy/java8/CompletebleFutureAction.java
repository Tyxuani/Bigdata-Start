package com.galaxy.java8;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletebleFutureAction {
    public static void main(String[] args) throws InterruptedException {
        supplyAsync();
    }

    private static void supplyAsync() throws InterruptedException {


//        CompletableFuture.supplyAsync(() -> 1)
//                .thenApply(i -> Integer.sum(i, 10))
//                .whenCompleteAsync((v, e) -> System.out.println(v));

//        CompletableFuture.supplyAsync(() -> 1)
//                .handle((v, e) -> Integer.sum(v, 10))
//                .whenComplete((v, e) -> System.out.println(v))
//                .thenRun(System.out::println);

//        CompletableFuture.supplyAsync(() -> 1)
//                .thenAccept(System.out::println);

//        CompletableFuture.supplyAsync(() -> 1)
//                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10*i))
//                .thenAccept(System.out::println);

//        CompletableFuture.supplyAsync(() -> 1)
//                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (a, b) -> a+b)
//                .thenAccept(System.out::println);

//        CompletableFuture.supplyAsync(() -> 1)
//                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (a, b) -> {
//                    System.out.println(a);
//                    System.out.println(b);
//                    System.out.println(a + b);
//                });

//        CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 1 Rrunning...");
//            return 1;
//        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 2 Rrunning...");
//            return 2.0d;
//            }),() -> System.out.println("Done"));

//        CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 1 Rrunning...");
//            return 1;
//        }).applyToEither(CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 2 Rrunning...");
//            return 2;
//        }),v -> v*10)
//        .thenAccept(System.out::println);

//        CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 1 Rrunning...");
//            try {
//                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(50));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 1;
//        }).acceptEither(CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 2 Rrunning...");
//            try {
//                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(50));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 2;
//        }),System.out::println);

//        CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 1 Rrunning...");
//            try {
//                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(50));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 1;
//        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "is 2 Rrunning...");
//            try {
//                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(50));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 2;
//        }),System.out::println);


        List<CompletableFuture<Supplier<Double>>> collect = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(i -> CompletableFuture.supplyAsync(LambdaExpression::get))
                .collect(Collectors.toList());
//
//        CompletableFuture.allOf(collect.toArray(new CompletableFuture[collect.size()]))
//                .thenRun(() -> {
//                    System.out.println("DONE");
//                    System.exit(0);
//                });

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> {
                    System.out.println("DONE");
                    System.exit(0);
                });

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("========== i am block  ========");
        Thread.currentThread().join();

    }

    private static void productQuery() {
        List<Integer> productionIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        List<Double> collect = productionIds.stream()
                .map(i -> CompletableFuture.supplyAsync(LambdaExpression.get(), exec()))
                .map(future -> future.thenApply(CompletebleFutureAction::multiply))
                .map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(collect);
    }

    private static double multiply(double v) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return v * 10d;
    }


    private static Executor exec() {
        Executor executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        executor.execute(() -> System.out.println("testing"));
        return executor;
    }
}
