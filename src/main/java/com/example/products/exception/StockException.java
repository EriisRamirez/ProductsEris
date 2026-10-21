package com.example.products.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockException extends RuntimeException{

    public StockException(String message) {
        super(message);
    }
}
