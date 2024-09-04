package com.graduationproject.isn.domain.records.response;

public record HandledErrorResponse(String errorReason, String errorMessage) implements ErrorResponse {
}
