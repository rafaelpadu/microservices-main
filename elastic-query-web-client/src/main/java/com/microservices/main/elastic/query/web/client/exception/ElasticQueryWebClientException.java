package com.microservices.main.elastic.query.web.client.exception;

public class ElasticQueryWebClientException extends RuntimeException {
    public ElasticQueryWebClientException() {
        super();
    }

    public ElasticQueryWebClientException(String message) {
        super(message);
    }

    public ElasticQueryWebClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
