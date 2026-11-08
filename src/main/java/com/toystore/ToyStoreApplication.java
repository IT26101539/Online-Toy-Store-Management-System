// (Admin_Management, User_Management, Toy_Management ...) are inside com.toystore
package com.toystore;

// SpringApplication: class that starts the whole Spring Boot application
import org.springframework.boot.SpringApplication;

// SpringBootApplication: annotation that marks this as the main Spring Boot class
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication = 3 annotations combined:
//   @Configuration           -> this class can define Spring settings
//   @EnableAutoConfiguration -> Spring Boot sets up things automatically (Tomcat, etc.)
//   @ComponentScan           -> finds all @Controller, @Service and @Repository classes
//                               inside com.toystore and its sub-packages

@SpringBootApplication
public class ToyStoreApplication {

    // main method - the starting point of every Java program

    public static void main(String[] args) {
        // Starts the application: creates all Spring objects (beans),
        // starts the built-in Tomcat server, and makes the website available
        // (by default at http://localhost:8080)

        SpringApplication.run(ToyStoreApplication.class, args);
    }

}
