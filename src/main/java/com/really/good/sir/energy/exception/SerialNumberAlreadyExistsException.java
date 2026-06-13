package com.really.good.sir.energy.exception;

public class SerialNumberAlreadyExistsException extends RuntimeException {

    public SerialNumberAlreadyExistsException(String serialNumber) {
        super("Electric meter with serial number '" + serialNumber + "' already exists");
    }
}