package com.really.good.sir.energy.dto.response;

import java.time.LocalDateTime;

public class ElectricMeterReadingResponse {

    private final Long id;
    private final Double value;
    private final LocalDateTime readingDate;

    public ElectricMeterReadingResponse(final Long id, final Double value, final LocalDateTime readingDate) {
        this.id = id;
        this.value = value;
        this.readingDate = readingDate;
    }

    public Long getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getReadingDate() {
        return readingDate;
    }
}