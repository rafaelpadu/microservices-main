package com.microservices.main.elastic.query.client.service;

import com.microservices.main.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticQueryClient<T extends IndexModel> {
    List<T> getIndexModelByText(String text);
    T getIndexModelById(String id);
    List<T> getAllIndexModels();
}
