package com.company.projekt.service.exception;

public class RateCalculateException extends RuntimeException {

    public RateCalculateException() {
        super("Rate calculate case not supported.");
    }

    public RateCalculateException(String message) {
        super(message);
    }
}
