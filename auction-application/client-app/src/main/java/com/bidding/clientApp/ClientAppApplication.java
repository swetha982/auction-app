package com.bidding.clientApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bidding")
@EnableAutoConfiguration
public class ClientAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientAppApplication.class, args);


    }

}
