package main.java.com.coderscampus.assignment;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AssignmentApplication {
    public static void main(String[] args) {
        Assignment8 assignmentApp = new Assignment8();
        List<Integer> dataList = new CopyOnWriteArrayList<>();
        ExecutorService pool = Executors.newCachedThreadPool();

        List<CompletableFuture<Void>> tasks = Stream.generate(() -> CompletableFuture.supplyAsync(assignmentApp::getNumbers, pool)
                        .thenAcceptAsync(dataList::addAll, pool)
                        .exceptionally(ex -> {
                            ex.printStackTrace();
                            return null;
                        }))
                .limit(1000)
                .toList();

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        pool.shutdown();

        Map<Integer, Integer> newDataList = dataList.stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum, TreeMap::new));

        System.out.println(newDataList);
    }
}