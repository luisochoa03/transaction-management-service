package com.banco.transactionupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Bacth {

    public static void main(String[] args) {
        SpringApplication.run(com.banco.transactionupdater.Bacth.class, args);
    }
}