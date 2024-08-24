package com.graduationproject.isn.advice;

import com.graduationproject.isn.domain.records.response.ErrorResponse;
import com.graduationproject.isn.domain.records.response.HandledErrorResponse;
import com.graduationproject.isn.exceptions.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleApiException(APIException apiException) {
        HandledErrorResponse errorResponse = new HandledErrorResponse(
            apiException.getErrorReason().name(),
            apiException.getErrorReason().getErrorMessage());
        HttpStatus responseStatus = apiException.getStatus();

        return buildJsonErrorResponse(responseStatus, errorResponse);
    }

    // TODO: Add Handling for these exceptions
    // @ExceptionHandler(DataIntegrityViolationException.class)

    private <T extends ErrorResponse> ResponseEntity<T> buildJsonErrorResponse(
        HttpStatus httpStatus, T errorResponse) {
        return ResponseEntity
            .status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
}

