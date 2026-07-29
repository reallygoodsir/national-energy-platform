package com.really.good.sir.energy.exception;

public class InvalidReadingValueException extends RuntimeException {
    public InvalidReadingValueException(final Double newValue, final Double lastValue) {
        super("New reading (" + newValue + ") cannot be lower than the last recorded reading (" + lastValue + ")");
    }
}