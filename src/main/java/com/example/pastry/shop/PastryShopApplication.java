package com.example.pastry.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PastryShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastryShopApplication.class, args);
    }
}
