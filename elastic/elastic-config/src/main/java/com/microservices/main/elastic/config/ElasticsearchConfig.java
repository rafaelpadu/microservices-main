package com.microservices.main.elastic.config;

import com.microservices.main.config.ElasticConfigData;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.microservices.main.elastic")
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    private final ElasticConfigData elasticConfigData;

    public ElasticsearchConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Bean
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration
                .builder()
                .connectedTo(elasticConfigData.getConnectionUrl())
                .withConnectTimeout(elasticConfigData.getConnectionTimeoutMs())
                .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                .build();
    }

}
