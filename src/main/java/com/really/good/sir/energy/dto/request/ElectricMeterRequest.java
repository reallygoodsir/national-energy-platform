package com.really.good.sir.energy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ElectricMeterRequest {

    @NotBlank
    private String serialNumber;

    @NotNull
    private Integer phaseCount;

    @NotNull
    private Long typeId;

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getPhaseCount() {
        return phaseCount;
    }

    public Long getTypeId() {
        return typeId;
    }
}