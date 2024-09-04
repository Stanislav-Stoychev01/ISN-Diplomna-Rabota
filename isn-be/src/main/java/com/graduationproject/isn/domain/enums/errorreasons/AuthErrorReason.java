package com.graduationproject.isn.domain.enums.errorreasons;

public enum AuthErrorReason implements ErrorReason {

    INVALID_CREDENTIALS("User credentials does not match"),

    INVALID_JWT_CLAIMS("Missing access permissions"),

    INVALID_LOGIN_CREDENTIALS("Incorrect username or password."),

    INVALID_AUTHENTICATION("An error occurred during authentication.");
    
    private final String errorMessage;

    AuthErrorReason(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
