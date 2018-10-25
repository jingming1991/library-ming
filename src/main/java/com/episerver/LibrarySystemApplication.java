package com.episerver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrarySystemApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(LibrarySystemApplication.class);
        springApplication.addListeners(new InitializationStartup());
        springApplication.run(args);
    }
}
