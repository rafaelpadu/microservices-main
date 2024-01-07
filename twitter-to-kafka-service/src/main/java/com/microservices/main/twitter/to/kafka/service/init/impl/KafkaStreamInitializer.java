package com.microservices.main.twitter.to.kafka.service.init.impl;

import com.microservices.main.config.KafkaConfigData;
import com.microservices.main.kafka.admin.client.KafkaAdminClient;
import com.microservices.main.twitter.to.kafka.service.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamInitializer.class);
    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        LOGGER.info("Topicos com o nome {} estão prontos para operações!", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
