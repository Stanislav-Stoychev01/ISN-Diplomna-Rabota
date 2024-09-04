package com.graduationproject.isn.exceptions;

import com.graduationproject.isn.domain.enums.errorreasons.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class APIException extends RuntimeException {

    private ErrorReason errorReason;

    private HttpStatus status;

}
