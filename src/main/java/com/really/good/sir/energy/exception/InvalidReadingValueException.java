package com.really.good.sir.energy.exception;

public class InvalidReadingValueException extends RuntimeException {
    public InvalidReadingValueException(Double newValue, Double lastValue) {
        super("New reading (" + newValue + ") cannot be lower than the last recorded reading (" + lastValue + ")");
    }
}