package com.microservices.main.kafka.producer.config.service.impl;

import com.microservices.main.kafka.avro.model.TwitterAvroModel;
import com.microservices.main.kafka.producer.config.service.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaProducer.class);
    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    public TwitterKafkaProducer(KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        LOGGER.info("Enviando mensagem='{}' para o topico='{}'", message, topicName);
        CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
        checkMessageCallback(topicName, message, kafkaResultFuture);
    }

    private static void checkMessageCallback(String topicName, TwitterAvroModel message, CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture) {
        kafkaResultFuture.whenComplete((result, throwable) -> {
          if(throwable != null){
              LOGGER.error("Erro ao enviar a mensagem {}, para o topico {}", message, topicName, throwable);
          }else{
              final RecordMetadata recordMetadata = result.getRecordMetadata();
              LOGGER.debug("Recebeu uma nova Metadata: Topico: {}; Offset: {}; Timestamp: {}; na Hora: {}",
                      recordMetadata.topic(),
                      recordMetadata.offset(),
                      recordMetadata.timestamp(),
                      System.nanoTime());
          }
        });
    }
}
