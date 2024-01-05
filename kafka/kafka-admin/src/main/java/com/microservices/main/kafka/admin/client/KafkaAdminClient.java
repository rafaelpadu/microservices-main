package com.microservices.main.kafka.admin.client;

import com.microservices.main.config.KafkaConfigData;
import com.microservices.main.config.RetryConfigData;
import com.microservices.main.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaAdminClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaAdminClient.class);

    private final KafkaConfigData kafkaConfigData;
    private final RetryConfigData retryConfigData;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;
    private final WebClient webClient;

    public KafkaAdminClient(KafkaConfigData kafkaConfigData, RetryConfigData retryConfigData, AdminClient adminClient, RetryTemplate retryTemplate, WebClient webClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
        this.webClient = webClient;
    }

    public void createTopics(){
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::doCreateTopics);
        }catch (Throwable t){
            throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)");
        }
        checkTopicsCreated();
    }
    private CreateTopicsResult doCreateTopics(RetryContext retryContext){
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        LOGGER.info("Creating {} topics, attempted {} times", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).toList();
        return adminClient.createTopics(kafkaTopics);
    }
    public void checkTopicsCreated(){
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        for(String topic: kafkaConfigData.getTopicNamesToCreate()){
            while (!isTopicCreated(topics, topic)){
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs *= multiplier;
                topics = getTopics();
            }
        }
    }

    private HttpStatusCode getSchemaRegistryStatus(){
        try {
            return webClient
                    .method(HttpMethod.GET)
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .exchange()
                    .map(ClientResponse::statusCode)
                    .block();
        } catch (Exception e) {
            return HttpStatusCode.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value());
        }
    }
    public void checkSchemaRegistry(){
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()){
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs *= multiplier;
        }
    }
    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Erro while sleeping for waiting new created topics");
        }
    }

    private void checkMaxRetry(int retry, Integer maxRetry) {
        if(retry > maxRetry){
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)");
        }
    }

    private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
        if(topics == null){
            return false;
        }
        return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
    }

    private Collection<TopicListing> getTopics(){
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        }catch (Throwable e){
            throw new KafkaClientException("Reached max number of retry for reading kafka topics", e);
        }
        return topics;
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext) throws ExecutionException, InterruptedException {
        LOGGER.info("Reading kafka topic {}, attempt {}", kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
        Collection<TopicListing> topics = adminClient.listTopics().listings().get();
        if(topics != null){
            topics.forEach(topicListing -> LOGGER.debug("Topic with name {}", topicListing.name()));
        }
        return topics;
    }
}
