package com.microservices.main.elastic.query.client.exceptions;

public class ElasticQueryServiceException extends RuntimeException{
    public ElasticQueryServiceException() {
    }

    public ElasticQueryServiceException(String message) {
        super(message);
    }

    public ElasticQueryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
