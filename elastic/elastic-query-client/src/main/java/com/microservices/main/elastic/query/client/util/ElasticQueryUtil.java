package com.microservices.main.elastic.query.client.util;

import com.microservices.main.elastic.model.index.IndexModel;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ElasticQueryUtil<T extends IndexModel> {
    public Query getSearchQueryById(String id) {
        return new NativeSearchQueryBuilder().withIds(Collections.singleton(id)).build();
    }

    public Query getSearchQueryByFieldText(String field, String text) {
        return new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .must(QueryBuilders.matchQuery(field, text)))
                .build();
    }

    public Query getSearchQueryFoAll(){
        return new NativeSearchQueryBuilder().withQuery(new BoolQueryBuilder().must(QueryBuilders.matchAllQuery())).build();
    }
}
