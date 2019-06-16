package it.bz.opendatahub.alpinebitsserver.application.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class for Spring Boot application.
 */
@SpringBootApplication
@ComponentScan(basePackages = "it.bz.opendatahub.alpinebitsserver")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}