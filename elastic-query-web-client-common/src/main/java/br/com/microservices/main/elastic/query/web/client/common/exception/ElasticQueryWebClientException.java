package br.com.microservices.main.elastic.query.web.client.common.exception;

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
