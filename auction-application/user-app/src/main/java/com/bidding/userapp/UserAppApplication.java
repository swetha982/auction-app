package com.bidding.userapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EntityScan(basePackages = "com.bidding.userapp.model")
//@EnableJpaRepositories(basePackages = "com.bidding.userapp.dao")
@ComponentScan(basePackages = "com.bidding")
@EnableAutoConfiguration
public class UserAppApplication {



    public static void main(String[] args) {
        SpringApplication.run(UserAppApplication.class, args);


    }

}
