package com.microservices.main.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-streams-service")
public class KafkaStreamsServiceConfigData {
	private String version;
	private String customAudience;
}