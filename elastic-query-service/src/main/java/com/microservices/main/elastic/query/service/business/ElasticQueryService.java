package com.microservices.main.elastic.query.service.business;

import br.com.easymarine.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;

import java.util.List;

public interface ElasticQueryService {
    ElasticQueryServiceResponseModel getDocumentById(String id);
    List<ElasticQueryServiceResponseModel> getDocumentByText(String text);
    List<ElasticQueryServiceResponseModel> getAllDocuments();
}
