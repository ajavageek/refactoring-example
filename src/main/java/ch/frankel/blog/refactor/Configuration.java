package ch.frankel.blog.refactor;

public class Configuration {

    public static String getCustomersUrl() {
        // Read from local file-system or database
        return "http://localhost:8080/customers";
    }
}
