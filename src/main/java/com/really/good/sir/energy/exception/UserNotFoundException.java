package com.really.good.sir.energy.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}