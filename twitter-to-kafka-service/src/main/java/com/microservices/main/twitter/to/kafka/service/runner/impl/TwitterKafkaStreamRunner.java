package com.microservices.main.twitter.to.kafka.service.runner.impl;

import com.microservices.main.config.TwitterToKafkaServiceConfigData;
import com.microservices.main.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.main.twitter.to.kafka.service.runner.StreamRunner;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;
@Deprecated
@Component
@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.enable-v2-tweets}")
public class TwitterKafkaStreamRunner implements StreamRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterKafkaStatusListener statusListener) {
        this.twitterToKafkaServiceConfigData = configData;
        this.twitterKafkaStatusListener = statusListener;
    }

    @PreDestroy
    public void shutDown() {
        if(twitterStream != null){
            LOGGER.info("Closing twitter stream!");
            twitterStream.shutdown();
        }
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    private void addFilter() {
        String[] keyWords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keyWords);
        twitterStream.filter(filterQuery);
        LOGGER.info("Started filtering twitter streams for keywords {}", Arrays.toString(keyWords));
    }
}
