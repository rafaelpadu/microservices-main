package com.microservices.main.kafka.streams.service;

import com.microservices.main.kafka.streams.service.init.StreamsInitializer;
import com.microservices.main.kafka.streams.service.runner.StreamsRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.main")
public class KafkaStreamsServiceApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(KafkaStreamsServiceApplication.class);
    private final StreamsRunner<String, Long> streamsRunner;
    private final StreamsInitializer streamsInitializer;

    public KafkaStreamsServiceApplication(StreamsRunner<String, Long> streamsRunner, StreamsInitializer streamsInitializer) {
        this.streamsRunner = streamsRunner;
        this.streamsInitializer = streamsInitializer;
    }

    public static void main(String... args) throws Exception {
        SpringApplication.run(KafkaStreamsServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Kafka Stream initialized");
        streamsInitializer.init();
        streamsRunner.start();
    }
}
