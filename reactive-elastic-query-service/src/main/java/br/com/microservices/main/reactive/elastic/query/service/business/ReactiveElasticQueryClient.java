package br.com.microservices.main.reactive.elastic.query.service.business;

import com.microservices.main.elastic.model.index.IndexModel;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient<T extends IndexModel> {
    Flux<TwitterIndexModel> getIndexModelByText(String text);
}
