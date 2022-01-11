package com.gitlab.yaroslavskyba.exception;

public class RozetkaException extends RuntimeException {
    public RozetkaException() {
    }

    public RozetkaException(String message) {
        super(message);
    }

    public RozetkaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RozetkaException(Throwable cause) {
        super(cause);
    }
}
