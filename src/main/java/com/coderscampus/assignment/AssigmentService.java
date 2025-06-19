package main.java.com.coderscampus.assignment;

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

public class AssigmentService {

    private final Assignment8 assignmentApp = new Assignment8();
    private final ExecutorService pool = Executors.newCachedThreadPool();


    public List<Integer> fetchNumbers() {
        List<Integer> dataList = new CopyOnWriteArrayList<>();

        List<CompletableFuture<Void>> tasks = Stream.generate(() -> CompletableFuture
                .supplyAsync(assignmentApp::getNumbers, pool)
                .thenAcceptAsync(dataList::addAll, pool)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                })).limit(1000).toList();

        CompletableFuture
                .allOf(tasks.toArray(new CompletableFuture[0]))
                .join();

        return dataList;
    }

    public Map<Integer, Integer> countOccurrences(List<Integer> dataList) {
        return dataList
                .stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum, TreeMap::new));
    }

    public void printResults(Map<Integer, Integer> resultMap) {
        resultMap.forEach((key, value) -> System.out.print(key + "=" + value + ", "));
        System.out.println();
    }

    public void shutdownPool() {
        pool.shutdown();
    }

}
