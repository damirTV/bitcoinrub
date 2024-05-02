package com.example.calcBitcoin.controller;

import com.example.calcBitcoin.exception.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BitcoinRubControllerAdvice {

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<String> handleIntegrationException(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage() + " На сайте наблюдаются проблемы, приходите позже");
    }
}