package br.com.microservices.main.reactive.elastic.query.service.business;

import br.com.microservices.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {
    Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);
}
