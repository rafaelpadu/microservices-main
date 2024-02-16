package com.microservices.main.elastic.query.web.client.service;


import br.com.microservices.main.elastic.query.web.client.common.model.ElasticQueryWebClientAnalyticsResponseModel;
import br.com.microservices.main.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import br.com.microservices.main.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {
    ElasticQueryWebClientAnalyticsResponseModel getDataByText(ElasticQueryWebClientRequestModel requestModel);
}
