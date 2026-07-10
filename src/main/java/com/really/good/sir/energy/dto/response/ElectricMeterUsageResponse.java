package com.really.good.sir.energy.dto.response;

import java.time.LocalDateTime;

public class ElectricMeterUsageResponse {

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private Double consumption;

    public ElectricMeterUsageResponse(LocalDateTime periodStart, LocalDateTime periodEnd, Double consumption) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.consumption = consumption;
    }

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public Double getConsumption() {
        return consumption;
    }
}