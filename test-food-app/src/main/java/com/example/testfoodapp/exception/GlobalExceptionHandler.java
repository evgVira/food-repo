package com.example.testfoodapp.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorDto> handleServerException(ServerException exception) {
        logError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        logError(exception, HttpStatus.NOT_FOUND);
        return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRequestException.class)
    public ResponseEntity<ErrorDto> UserRequestException(UserRequestException exception) {
        logError(exception, HttpStatus.BAD_REQUEST);
        return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private void logError(Exception exception, HttpStatus status) {
        LOGGER.error("Ошибка: {} - {}: {}", status.value(), status.getReasonPhrase(), exception.getMessage(), exception);
    }

    private ResponseEntity<ErrorDto> buildErrorResponse(String message, HttpStatus status) {
        ErrorDto errorDto = new ErrorDto(status.value(), status.getReasonPhrase(), message);
        return new ResponseEntity<>(errorDto, status);
    }
}
