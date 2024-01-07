package com.microservices.main.twitter.to.kafka.service.transfomer;

import com.microservices.main.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {

    public TwitterAvroModel getTwitterAvroModelFromStatus(Status status) {
        return TwitterAvroModel.newBuilder()
                .setId(status.getUser().getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }
}
