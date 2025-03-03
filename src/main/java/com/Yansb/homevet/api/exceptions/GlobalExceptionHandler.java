package com.Yansb.homevet.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CreateUserException.class)
  public ResponseEntity<ErrorResponse> handleCreateUserException(CreateUserException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).build()
      );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build()
    );
  }

}
