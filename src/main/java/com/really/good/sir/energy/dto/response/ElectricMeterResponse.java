package com.really.good.sir.energy.dto.response;

public class ElectricMeterResponse {

    private final Long id;
    private final String serialNumber;
    private final Integer phaseCount;
    private final String typeName;

    public ElectricMeterResponse(final Long id, final String serialNumber,
                                 final Integer phaseCount, final String typeName) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.phaseCount = phaseCount;
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getPhaseCount() {
        return phaseCount;
    }

    public String getTypeName() {
        return typeName;
    }
}