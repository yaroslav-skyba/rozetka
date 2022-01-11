package com.gitlab.yaroslavskyba.exception;

public class OrderServiceException extends ServiceException{
    public OrderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
