package com.really.good.sir.energy.exception;

public class ApartmentNotFoundException extends RuntimeException {

    public ApartmentNotFoundException(final Long apartmentId) {
        super("Apartment with ID " + apartmentId + " was not found.");
    }
}