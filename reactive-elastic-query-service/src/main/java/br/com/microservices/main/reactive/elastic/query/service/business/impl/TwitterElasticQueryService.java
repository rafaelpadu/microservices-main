package br.com.microservices.main.reactive.elastic.query.service.business.impl;

import br.com.microservices.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import br.com.microservices.main.elastic.query.service.common.transformer.ElasticToResponseModelTransformer;
import br.com.microservices.main.reactive.elastic.query.service.business.ElasticQueryService;
import br.com.microservices.main.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
public class TwitterElasticQueryService implements ElasticQueryService {
    private static final Logger log = LoggerFactory.getLogger(TwitterElasticQueryService.class);
    private final ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient;
    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    public TwitterElasticQueryService(ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient, ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
        this.reactiveElasticQueryClient = reactiveElasticQueryClient;
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
    }

    @Override
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying reactive elasticsearch for text {}", text);
        return reactiveElasticQueryClient
                .getIndexModelByText(text)
                .map(elasticToResponseModelTransformer::getResponseModel);
    }
}
