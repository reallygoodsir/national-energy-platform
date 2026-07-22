package com.really.good.sir.energy.dto.request;

import com.really.good.sir.energy.validation.ValidSerialNumber;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ElectricMeterRequest {

    @NotBlank(message = "Serial number must not be blank")
    @Size(min = 6, max = 20, message = "Serial number must be 6–20 characters long")
    @Pattern(
            regexp = "^[A-Za-z0-9.-]+$",
            message = "Serial number may only contain letters, numbers, dots, and dashes"
    )
    @ValidSerialNumber
    private String serialNumber;

    @NotNull(message = "Phase count is required")
    @Min(value = 1, message = "Phase count must be at least 1")
    @Max(value = 3, message = "Phase count must be at most 3")
    private Integer phaseCount;

    @NotNull(message = "Meter type is required")
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