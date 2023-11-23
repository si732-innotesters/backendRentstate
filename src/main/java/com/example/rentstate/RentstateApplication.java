package com.example.rentstate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RentstateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentstateApplication.class, args);
    }

}
