package com.bci.evaluation.controller.exception;

import com.bci.evaluation.dto.ErrorResponse;
import com.bci.evaluation.exception.AlreadyExistsException;
import com.bci.evaluation.exception.BadCredentialsException;
import com.bci.evaluation.exception.BadRequestException;
import com.bci.evaluation.exception.NotFoundException;
import com.bci.evaluation.exception.RepositoryFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(RepositoryFailedException.class)
  public ResponseEntity<ErrorResponse> handleRepositoryFailedException(RepositoryFailedException exception) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException exception) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage()));
  }

}
