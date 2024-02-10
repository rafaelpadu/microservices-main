package com.microservices.main.elastic.query.service.model.assembler;

import br.com.microservices.main.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import br.com.microservices.main.elastic.query.service.common.transformer.ElasticToResponseModelTransformer;
import com.microservices.main.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.main.elastic.query.service.api.ElasticDocumentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ElasticQueryServiceResponseModelAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {

    public ElasticQueryServiceResponseModelAssembler() {
        super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
    }

    @Override
    public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel) {
        ElasticQueryServiceResponseModel responseModel = getResponseModel(twitterIndexModel);
        responseModel.add(
                linkTo(methodOn(ElasticDocumentController.class)
                        .getDocumentById((twitterIndexModel.getId())))
                        .withSelfRel());
        responseModel.add(
                linkTo(ElasticDocumentController.class)
                        .withRel("documents"));
        return responseModel;
    }

    public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
    }

    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel twitterIndexModel) {
        return ElasticQueryServiceResponseModel
                .builder()
                .id(twitterIndexModel.getId())
                .text(twitterIndexModel.getText())
                .createdAt(twitterIndexModel.getCreatedAt())
                .build();
    }
}
