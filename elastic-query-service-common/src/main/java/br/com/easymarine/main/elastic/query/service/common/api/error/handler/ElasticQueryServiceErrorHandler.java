package br.com.easymarine.main.elastic.query.service.common.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ElasticQueryServiceErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ElasticQueryServiceErrorHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handler(AccessDeniedException e){
        logger.error("Acces denied exception", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to acess this resource");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handler(IllegalArgumentException e){
        logger.error("Illegal argument exception", e);
        return ResponseEntity.badRequest().body("Illegal argument exception " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException e){
        logger.error("Service runtime exception!", e);
        return ResponseEntity.badRequest().body("Service runtime exception " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e){
        logger.error("INternal server error!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occurred!");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handler(MethodArgumentNotValidException e){
        logger.error("Method argument validation exception", e);
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError ->
                errors.put(((FieldError) objectError).getField(), objectError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
