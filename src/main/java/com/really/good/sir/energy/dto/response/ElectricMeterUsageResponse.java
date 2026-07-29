package com.really.good.sir.energy.dto.response;

import java.time.LocalDateTime;

public class ElectricMeterUsageResponse {

    private final LocalDateTime periodStart;
    private final LocalDateTime periodEnd;
    private final Double consumption;

    public ElectricMeterUsageResponse(final LocalDateTime periodStart,
                                      final LocalDateTime periodEnd, final Double consumption) {
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