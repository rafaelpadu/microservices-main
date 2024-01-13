package com.microservices.service.consumer.impl;

import com.microservices.main.config.KafkaConfigData;
import com.microservices.main.kafka.admin.client.KafkaAdminClient;
import com.microservices.main.kafka.avro.model.TwitterAvroModel;
import com.microservices.service.consumer.KafkaConsumer;
import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class KafkaConsumerImpl implements KafkaConsumer<Long, TwitterAvroModel> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerImpl.class);
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;

    public KafkaConsumerImpl(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, KafkaAdminClient kafkaAdminClient, KafkaConfigData kafkaConfigData) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaAdminClient = kafkaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> messages, @Header List<Integer> keys, @Header List<Integer> partitions, @Header List<Long> offsets) {
        logger.info("{} number of message received with keys {}, partitions {} and offsets {}. Sending to elastic: Thread id {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString(), Thread.currentThread().getId());
    }
}
