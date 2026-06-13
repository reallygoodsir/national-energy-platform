package com.really.good.sir.energy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = SerialNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSerialNumber {

    String message() default "Invalid serial number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}