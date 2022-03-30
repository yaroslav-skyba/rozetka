package com.gitlab.yaroslavskyba.rozetka.exception;

public class RoleServiceException extends RuntimeException {
    public RoleServiceException(String message) {
        super(message);
    }

    public RoleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
