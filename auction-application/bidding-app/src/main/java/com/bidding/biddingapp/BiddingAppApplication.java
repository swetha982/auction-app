package com.bidding.biddingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.bidding.biddingapp")
@EnableAutoConfiguration
@EnableScheduling
public class BiddingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiddingAppApplication.class, args);
    }
}

