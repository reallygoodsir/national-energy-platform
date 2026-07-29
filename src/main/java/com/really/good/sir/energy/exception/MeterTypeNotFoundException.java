package com.really.good.sir.energy.exception;

public class MeterTypeNotFoundException extends RuntimeException {

    public MeterTypeNotFoundException(final Long typeId) {
        super("Electric meter type with id '" + typeId + "' not found");
    }
}