package com.really.good.sir.energy.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(final String message) {
        super(message);
    }
}