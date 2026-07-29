package com.really.good.sir.energy.exception;

public class RoleAlreadyAssignedException extends RuntimeException {

    public RoleAlreadyAssignedException(final String message) {
        super(message);
    }
}