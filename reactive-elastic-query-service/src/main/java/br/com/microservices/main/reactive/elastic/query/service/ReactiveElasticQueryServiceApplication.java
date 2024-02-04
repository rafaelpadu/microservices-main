package br.com.microservices.main.reactive.elastic.query.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.main")
public class ReactiveElasticQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveElasticQueryServiceApplication.class, args);
    }
}
