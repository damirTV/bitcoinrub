package com.example.calcBitcoin.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegrationException extends RuntimeException {

    public IntegrationException(String message) {
        super(message);
        log.error(message);
    }
}
