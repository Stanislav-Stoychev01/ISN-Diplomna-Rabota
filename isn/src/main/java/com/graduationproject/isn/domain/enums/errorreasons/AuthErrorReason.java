package com.graduationproject.isn.domain.enums.errorreasons;

public enum AuthErrorReason implements ErrorReason {

    INVALID_CREDENTIALS("Provided user is already exits.");
    
    private final String errorMessage;

    AuthErrorReason(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
