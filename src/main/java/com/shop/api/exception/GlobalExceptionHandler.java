package com.shop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    protected ResponseEntity<Object> invalidInputRequestHandler(InvalidInputDataException inputDataException){
        final ErrorDetails errorDetails = new ErrorDetails("Invalid input request",inputDataException.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
