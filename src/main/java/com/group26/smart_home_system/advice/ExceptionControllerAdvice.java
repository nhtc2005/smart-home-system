package com.group26.smart_home_system.advice;

import com.group26.smart_home_system.dto.common.ErrorResponse;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .message(exception.getMessage())
                                                   .error(HttpStatus.CONFLICT)
                                                   .status(HttpStatus.CONFLICT.value())
                                                   .timestamp(LocalDateTime.now())
                                                   .build();
        return ResponseEntity.status(errorResponse.getError())
                             .body(errorResponse);
    }

}
