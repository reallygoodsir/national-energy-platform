package com.really.good.sir.energy.dto.response;

public class ElectricMeterResponse {

    private Long id;
    private String serialNumber;
    private Integer phaseCount;
    private String typeName;

    public ElectricMeterResponse(Long id, String serialNumber, Integer phaseCount, String typeName) {
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