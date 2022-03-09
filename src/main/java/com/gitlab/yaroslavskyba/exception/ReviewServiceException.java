package com.gitlab.yaroslavskyba.exception;

public class ReviewServiceException extends RuntimeException {
    public ReviewServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
