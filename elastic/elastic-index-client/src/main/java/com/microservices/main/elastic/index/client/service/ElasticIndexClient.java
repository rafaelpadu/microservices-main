package com.microservices.main.elastic.index.client.service;

import com.microservices.main.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
