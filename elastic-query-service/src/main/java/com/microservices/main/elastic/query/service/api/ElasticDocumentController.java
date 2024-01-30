package com.microservices.main.elastic.query.service.api;

import com.microservices.main.elastic.query.service.business.impl.TwitterElasticQueryService;
import com.microservices.main.elastic.query.service.model.ElasticQueryServiceRequestModel;
import com.microservices.main.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticDocumentController.class);
    private final TwitterElasticQueryService twitterElasticQueryService;

    public ElasticDocumentController(TwitterElasticQueryService twitterElasticQueryService) {
        this.twitterElasticQueryService = twitterElasticQueryService;
    }

    @GetMapping("/")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> response = twitterElasticQueryService.getAllDocuments();
        LOGGER.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable String id) {
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = twitterElasticQueryService.getDocumentById(id);
        LOGGER.debug("Elasticsearch returned document with id {}", id);
        return ResponseEntity.ok(elasticQueryServiceResponseModel);
    }

    @PostMapping("/get-document-by-text")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
        List<ElasticQueryServiceResponseModel> responseModels = twitterElasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
        LOGGER.debug("Elasticsearch returned {} of documents", responseModels.size());
        return ResponseEntity.ok(responseModels);
    }
}
