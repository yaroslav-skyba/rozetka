package com.gitlab.yaroslavskyba.exception;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
