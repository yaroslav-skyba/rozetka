package com.gitlab.yaroslavskyba.rozetka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String body;

        try {
            final JsonMappingException jsonMappingException = (JsonMappingException)ex.getCause();
            body = ((JsonParser)jsonMappingException.getProcessor()).getText()
                   + " is the incorrect "
                   + String.join(" ", StringUtils.splitByCharacterTypeCamelCase(jsonMappingException.getPath().get(0).getFieldName()))
                   + " value";
        } catch (ClassCastException | IOException exception) {
            body = exception.getMessage();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(body);
    }
}
