package com.nataliia.klymenko.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.nataliia.klymenko.petstore")
public class PetStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }
}
