package com.really.good.sir.energy.dto.response;

import java.time.LocalDateTime;

public class ElectricMeterReadingResponse {

    private Long id;
    private Double value;
    private LocalDateTime readingDate;

    public ElectricMeterReadingResponse(Long id, Double value, LocalDateTime readingDate) {
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