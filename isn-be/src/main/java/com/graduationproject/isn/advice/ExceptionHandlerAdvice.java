package com.graduationproject.isn.advice;

import com.graduationproject.isn.domain.records.response.ErrorResponse;
import com.graduationproject.isn.domain.records.response.HandledErrorResponse;
import com.graduationproject.isn.exceptions.APIException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
        DataIntegrityViolationException dataIntegrityViolationException) {
        HandledErrorResponse errorResponse = new HandledErrorResponse(
            dataIntegrityViolationException.getMostSpecificCause().toString(),
            dataIntegrityViolationException.getMessage());
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return buildJsonErrorResponse(responseStatus, errorResponse);
    }

    @ExceptionHandler(SQLServerException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
        SQLServerException sqlServerException) {
        HandledErrorResponse errorResponse = new HandledErrorResponse(
            sqlServerException.getSQLServerError().toSqlExceptionOrSqlWarning().toString(),
            sqlServerException.getSQLServerError().getErrorMessage());
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return buildJsonErrorResponse(responseStatus, errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleApiException(AccessDeniedException accessDeniedException) {
        HandledErrorResponse errorResponse = new HandledErrorResponse(
            HttpStatus.FORBIDDEN.name(),
            accessDeniedException.getMessage());
        HttpStatus responseStatus = HttpStatus.FORBIDDEN;

        return buildJsonErrorResponse(responseStatus, errorResponse);
    }

    private <T extends ErrorResponse> ResponseEntity<T> buildJsonErrorResponse(
        HttpStatus httpStatus, T errorResponse) {
        return ResponseEntity
            .status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
}

