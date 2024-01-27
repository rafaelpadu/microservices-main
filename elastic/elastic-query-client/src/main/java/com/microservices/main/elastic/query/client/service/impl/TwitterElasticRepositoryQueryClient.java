package com.microservices.main.elastic.query.client.service.impl;

import com.microservices.main.common.util.CollectionsUtil;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.main.elastic.query.client.exceptions.ElasticQueryClientException;
import com.microservices.main.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import com.microservices.main.elastic.query.client.service.ElasticQueryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    private static final Logger logger = LoggerFactory.getLogger(TwitterElasticQueryClient.class);
    private final TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository;

    public TwitterElasticRepositoryQueryClient(TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository) {
        this.twitterElasticsearchQueryRepository = twitterElasticsearchQueryRepository;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> seaarchResult = twitterElasticsearchQueryRepository.findById(id);
        logger.info("Document with id {} retrieved successfully", seaarchResult.orElseThrow(() -> new ElasticQueryClientException("No document found at elasticsearch with id " + id)).getId());
        return seaarchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findByText(text);
        logger.info("{} of document with text {} retrieved successfully", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> all = CollectionsUtil.getInstance().getListFromIterable(twitterElasticsearchQueryRepository.findAll());
        logger.info("{} of document retrieved successfully", all.size());
        return all;
    }
}
