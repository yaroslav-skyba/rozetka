package com.gitlab.yaroslavskyba.exception;

public class UserServiceException extends ServiceException {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Exception ex) {
        super(message, ex);
    }

    public UserServiceException(String message, int errorCode) {
        super(message, errorCode);
    }
}
