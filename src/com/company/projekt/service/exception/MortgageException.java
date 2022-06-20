package com.company.projekt.service.exception;

public class MortgageException extends RuntimeException {
    public MortgageException() {
        super("Overpayment case not supported.");
    }
}
