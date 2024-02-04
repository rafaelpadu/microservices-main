package br.com.microservices.main.reactive.elastic.query.service.business.impl;

import br.com.microservices.main.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import br.com.microservices.main.reactive.elastic.query.service.repository.ElasticQueryRepository;
import com.microservices.main.config.ElasticQueryServiceConfigData;
import com.microservices.main.elastic.model.index.IndexModel;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
@Service
public class TwitterReactiveElasticQueryClient implements ReactiveElasticQueryClient<TwitterIndexModel> {
    private static final Logger log = LoggerFactory.getLogger(TwitterReactiveElasticQueryClient.class);
    private final ElasticQueryRepository elasticQueryRepository;
    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    public TwitterReactiveElasticQueryClient(ElasticQueryRepository elasticQueryRepository, ElasticQueryServiceConfigData elasticQueryServiceConfigData) {
        this.elasticQueryRepository = elasticQueryRepository;
        this.elasticQueryServiceConfigData = elasticQueryServiceConfigData;
    }

    @Override
    public Flux<TwitterIndexModel> getIndexModelByText(String text) {
        log.info("Gettind data from elasticsearch for text {}", text);
        return elasticQueryRepository
                .findByText(text)
                .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
    }
}
