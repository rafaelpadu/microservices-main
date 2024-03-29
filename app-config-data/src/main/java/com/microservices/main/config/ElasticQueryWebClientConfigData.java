package com.microservices.main.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-web-client")
public class ElasticQueryWebClientConfigData {
    private WebClient webClient;
    private Query queryByText;
    @Data
    public static class WebClient {
        private Integer connectTimeoutMs;
        private Integer readTimeoutMs;
        private Integer writeTimeoutMs;
        private Integer maxInMemorySize;
        private String contentType;
        private String acceptType;
        private String baseUrl;
    }

    @Data
    public static class Query{
        private String method;
        private String accept;
        private String uri;
    }

}
