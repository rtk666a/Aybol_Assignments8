package main.java.com.coderscampus.assignment;



import java.util.List;
import java.util.Map;



public class  AssignmentApplication {
    public static void main(String[] args) {
        var service = new AssigmentService();
        service.printResults(service.countOccurrences(service.fetchNumbers()));
        service.shutdownPool();
    }
}