package main.java.com.coderscampus.assignment;


public class AssignmentApplication {
    public static void main(String[] args) {
        var service = new AssigmentService();
        service.printResults(service.countOccurrences(service.fetchNumbers()));
        service.shutdownPool();
    }
}