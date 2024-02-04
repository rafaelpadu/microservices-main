package com.microservices.main.elastic.query.service.model.assembler;

import br.com.microservices.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import br.com.microservices.main.elastic.query.service.common.transformer.ElasticToResponseModelTransformer;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.main.elastic.query.service.api.ElasticDocumentController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ElasticQueryServiceResponseModelAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {
    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    public ElasticQueryServiceResponseModelAssembler(ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
        super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
    }

    @Override
    public ElasticQueryServiceResponseModel toModel(TwitterIndexModel entity) {
        ElasticQueryServiceResponseModel responseModel = elasticToResponseModelTransformer.getResponseModel(entity);
        responseModel.add(
                linkTo(
                        methodOn(ElasticDocumentController.class)
                                .getDocumentById((entity.getId())))
                        .withSelfRel());
        responseModel.add(linkTo(ElasticDocumentController.class).withRel("documents"));
        return responseModel;
    }

    public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModelList){
        return twitterIndexModelList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
