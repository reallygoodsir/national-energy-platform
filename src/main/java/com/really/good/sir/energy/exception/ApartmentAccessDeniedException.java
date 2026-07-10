package com.really.good.sir.energy.exception;

public class ApartmentAccessDeniedException extends RuntimeException {
    public ApartmentAccessDeniedException(Long apartmentId) {
        super("You do not have access to apartment with ID " + apartmentId);
    }
}