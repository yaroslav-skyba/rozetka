package com.gitlab.yaroslavskyba;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status, @NotNull WebRequest request) {
        String body;

        final Class<? extends Throwable> exClass = ex.getCause().getClass();

        if (exClass == InvalidFormatException.class) {
            body = "An incorrect field value";
        } else if (exClass == JsonMappingException.class ) {
            body = "A field value is too large";
        } else {
            body = ex.getMessage();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(body);
    }
}
