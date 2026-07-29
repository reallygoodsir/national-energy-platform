package com.really.good.sir.energy.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ElectricMeterReadingRequest {

    @NotNull(message = "Reading value is required")
    @PositiveOrZero(message = "Reading value must be zero or positive")
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }
}