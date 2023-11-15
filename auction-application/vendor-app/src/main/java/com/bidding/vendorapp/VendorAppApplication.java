package com.bidding.vendorapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.bidding.vendorapp.model")
@EnableJpaRepositories(basePackages = "com.bidding.vendorapp.dao")
@ComponentScan(basePackages = "com.bidding")
@EnableAutoConfiguration
public class VendorAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendorAppApplication.class, args);
    }
}
