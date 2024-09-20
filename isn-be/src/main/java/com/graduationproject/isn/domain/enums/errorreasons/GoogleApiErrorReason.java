package com.graduationproject.isn.domain.enums.errorreasons;

public enum GoogleApiErrorReason implements ErrorReason {

    CONNECTION_UNAVAILABLE("Current service connection is not available."),

    INTERRUPTED("Current service connection is not available.");

    private final String message;

    GoogleApiErrorReason(String message) {
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
