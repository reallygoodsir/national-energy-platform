package com.really.good.sir.energy.exception;

public class ElectricMeterNotFoundException extends RuntimeException {

    public ElectricMeterNotFoundException(final String message) {
        super(message);
    }

    public ElectricMeterNotFoundException(final Long meterId) {
        super("Electric meter with ID " + meterId + " was not found.");
    }
}