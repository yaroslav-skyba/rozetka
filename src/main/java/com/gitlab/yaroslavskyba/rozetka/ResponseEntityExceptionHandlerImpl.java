package com.gitlab.yaroslavskyba.rozetka;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {
    @SuppressWarnings("NullableProblems")
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String body;

        final Throwable exCause = ex.getCause();
        final Class<? extends Throwable> exClass = exCause.getClass();

        if (exClass == InvalidFormatException.class) {
            body = ((InvalidFormatException)exCause).getValue() + " is the incorrect field value";
        } else {
            body = ex.getMessage();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(body);
    }
}
