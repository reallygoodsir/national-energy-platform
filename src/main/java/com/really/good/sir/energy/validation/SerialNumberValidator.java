package com.really.good.sir.energy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SerialNumberValidator implements ConstraintValidator<ValidSerialNumber, String> {

    private static final int MAX_DASH_COUNT = 1;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null && !value.isBlank()) {

            if (value.startsWith("-") || value.endsWith("-")
                    || value.startsWith(".") || value.endsWith(".")) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Serial number cannot start or end with '-' or '.'")
                        .addConstraintViolation();
                valid = false;

            } else {
                final long dashCount = value.chars().filter(c -> c == '-').count();

                if (dashCount > MAX_DASH_COUNT) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(
                                    "Serial number can contain at most one dash")
                            .addConstraintViolation();
                    valid = false;
                }
            }
        }

        return valid;
    }
}