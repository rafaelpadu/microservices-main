package com.microservices.main.elastic.query.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.main")
public class ElasticQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticQueryServiceApplication.class, args);
    }
}
