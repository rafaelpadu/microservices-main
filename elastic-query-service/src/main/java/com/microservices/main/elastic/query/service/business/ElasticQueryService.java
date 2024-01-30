package com.microservices.main.elastic.query.service.business;

import com.microservices.main.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ElasticQueryService {
    ElasticQueryServiceResponseModel getDocumentById(String id);
    List<ElasticQueryServiceResponseModel> getDocumentByText(String text);
    List<ElasticQueryServiceResponseModel> getAllDocuments();
}
