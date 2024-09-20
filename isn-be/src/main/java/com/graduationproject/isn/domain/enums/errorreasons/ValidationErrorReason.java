package com.graduationproject.isn.domain.enums.errorreasons;

public enum ValidationErrorReason implements ErrorReason {

    MALFORMED_URL_ERROR_REASON("Extracted URL is malformed");

    private final String message;

    ValidationErrorReason(String message) {
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

}
