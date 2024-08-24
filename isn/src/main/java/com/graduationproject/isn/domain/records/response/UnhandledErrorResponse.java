package com.graduationproject.isn.domain.records.response;

public record UnhandledErrorResponse(String exceptionName, String stackTrace) implements ErrorResponse {
}
