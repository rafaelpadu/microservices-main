package com.microservices.main.elastic.query.client.service.impl;

import com.microservices.main.config.ElasticConfigData;
import com.microservices.main.config.ElasticQueryConfigData;
import com.microservices.main.elastic.model.index.IndexModel;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.main.elastic.query.client.exceptions.ElasticQueryClientException;
import com.microservices.main.elastic.query.client.service.ElasticQueryClient;
import com.microservices.main.elastic.query.client.util.ElasticQueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterElasticQueryClient.class);
    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData elasticQueryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    public TwitterElasticQueryClient(ElasticConfigData elasticConfigData, ElasticQueryConfigData elasticQueryConfigData, ElasticsearchOperations elasticsearchOperations, ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil) {
        this.elasticConfigData = elasticConfigData;
        this.elasticQueryConfigData = elasticQueryConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticQueryUtil = elasticQueryUtil;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult = elasticsearchOperations.searchOne(query, TwitterIndexModel.class);
        if(searchResult == null){
            LOGGER.error("No document found at elasticsearch with id {}", id);
            throw new ElasticQueryClientException("No document found at elasticsearch with id " + id);
        }
        return searchResult.getContent();
    }
    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        return search(query, "{} of documents with text {} retrieved successfully", text);
    }
    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryFoAll();
        return search(query, "{} number of documents retrieved successfully");
    }
    private List<TwitterIndexModel> search(Query query, String logMessage, Object... logParams) {
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class);
        LOGGER.info(logMessage, searchResult.getTotalHits(), logParams);
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
