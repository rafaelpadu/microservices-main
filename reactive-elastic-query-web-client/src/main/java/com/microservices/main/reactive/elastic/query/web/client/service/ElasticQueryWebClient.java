package com.microservices.main.reactive.elastic.query.web.client.service;

import br.com.microservices.main.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import br.com.microservices.main.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryWebClient {

    Flux<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel request);
}
