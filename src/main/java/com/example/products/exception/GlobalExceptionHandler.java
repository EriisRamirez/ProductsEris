package com.example.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler 
    public ResponseEntity<String> handleNotFound(
        ResourceNotFoundException ex
    ) {
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> stockExcption(
            StockException ex
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }



}
