package com.really.good.sir.energy.exception;

public class MeterReadingNotDetectedException extends RuntimeException {
    public MeterReadingNotDetectedException() {
        super("Could not detect a meter reading in the uploaded photo");
    }
}