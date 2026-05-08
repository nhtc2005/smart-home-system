package com.group26.smart_home_system.advice;

import com.group26.smart_home_system.dto.common.ErrorResponse;
import com.group26.smart_home_system.exception.*;

import java.time.Instant;
import java.util.List;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorResponse>> handleValidation(
      MethodArgumentNotValidException exception) {

    List<ErrorResponse> errors =
        exception.getBindingResult().getFieldErrors().stream()
            .map(
                error ->
                    ErrorResponse.builder()
                        .message(error.getDefaultMessage())
                        .error(HttpStatus.BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(Instant.now())
                        .build())
            .toList();

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> ConstraintViolationExceptionHandler(
      ConstraintViolationException exception) {

    return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> IllegalArgumentExceptionHandler(
      IllegalArgumentException exception) {

    return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> UserAlreadyExistsExceptionHandler(
      UserAlreadyExistsException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> BadCredentialsExceptionHandler(
      BadCredentialsException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> UnauthorizedExceptionHandler(
      UnauthorizedException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> UserNotFoundExceptionHandler(
      UserNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = InvalidPasswordException.class)
  public ResponseEntity<ErrorResponse> InvalidPasswordExceptionHandler(
      InvalidPasswordException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = LocationNotFoundException.class)
  public ResponseEntity<ErrorResponse> LocationNotFoundExceptionHandler(
      LocationNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = DeviceNotFoundException.class)
  public ResponseEntity<ErrorResponse> DeviceNotFoundExceptionHandler(
      DeviceNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = SensorNotFoundException.class)
  public ResponseEntity<ErrorResponse> SensorNotFoundExceptionHandler(
      SensorNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ActuatorNotFoundException.class)
  public ResponseEntity<ErrorResponse> ActuatorNotFoundExceptionHandler(
      ActuatorNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ScheduleNotFoundException.class)
  public ResponseEntity<ErrorResponse> ScheduleNotFoundExceptionHandler(
      ScheduleNotFoundException exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> ExceptionHandler(Exception exception) {
    return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(message)
            .error(status)
            .status(status.value())
            .timestamp(Instant.now())
            .build();

    return ResponseEntity.status(status).body(errorResponse);
  }
}
