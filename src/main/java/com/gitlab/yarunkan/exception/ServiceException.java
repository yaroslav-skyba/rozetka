package com.gitlab.yarunkan.exception;

import javax.validation.constraints.NotNull;

public abstract class ServiceException extends RozetkaException {
    @NotNull
    private String error;
    @NotNull
    private int errorCode;

    protected ServiceException(String message) {
        super(message);
    }

    protected ServiceException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    protected ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
