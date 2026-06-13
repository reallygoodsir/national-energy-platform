package com.really.good.sir.energy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SerialNumberValidator implements ConstraintValidator<ValidSerialNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        if (value.startsWith("-") || value.endsWith("-")
                || value.startsWith(".") || value.endsWith(".")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Serial number cannot start or end with '-' or '.'")
                    .addConstraintViolation();
            return false;
        }

        long dashCount = value.chars().filter(c -> c == '-').count();
        if (dashCount > 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Serial number can contain at most one dash")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}