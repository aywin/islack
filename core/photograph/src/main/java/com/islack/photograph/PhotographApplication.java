package com.islack.photograph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotographApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotographApplication.class, args);
    }
}
