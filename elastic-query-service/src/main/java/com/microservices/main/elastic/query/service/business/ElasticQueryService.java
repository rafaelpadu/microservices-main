package com.microservices.main.elastic.query.service.business;

import br.com.microservices.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.microservices.main.elastic.query.service.model.ElasticQueryServiceAnalyticsResponseModel;

import java.util.List;

public interface ElasticQueryService {
    ElasticQueryServiceResponseModel getDocumentById(String id);
   ElasticQueryServiceAnalyticsResponseModel getDocumentByText(String text, String accessToken);
    List<ElasticQueryServiceResponseModel> getAllDocuments();
}
