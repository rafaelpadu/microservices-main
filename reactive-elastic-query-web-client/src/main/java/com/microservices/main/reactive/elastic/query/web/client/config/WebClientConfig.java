package com.microservices.main.reactive.elastic.query.web.client.config;

import com.microservices.main.config.ElasticQueryWebClientConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    private final ElasticQueryWebClientConfigData.WebClient webClientConfig;

    public WebClientConfig(ElasticQueryWebClientConfigData.WebClient webClientConfig) {
        this.webClientConfig = webClientConfig;
    }
    @Bean("webClient")
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(webClientConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, webClientConfig.getContentType())
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(webClientConfig.getMaxInMemorySize()))
                .build();
    }

    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConfig.getConnectTimeoutMs())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(webClientConfig.getReadTimeoutMs(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(webClientConfig.getWriteTimeoutMs(),
                            TimeUnit.MILLISECONDS));
                });
    }
}
