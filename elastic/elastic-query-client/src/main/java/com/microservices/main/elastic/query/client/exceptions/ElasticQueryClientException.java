package com.microservices.main.elastic.query.client.exceptions;

public class ElasticQueryClientException extends RuntimeException{
    public ElasticQueryClientException() {
    }

    public ElasticQueryClientException(String message) {
        super(message);
    }

    public ElasticQueryClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
