package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import org.springframework.stereotype.Service;

@Service
public class ElectricMeterMapper {

    public ElectricMeterResponse toResponse(ElectricMeterEntity meter) {
        return new ElectricMeterResponse(
                meter.getId(),
                meter.getSerialNumber(),
                meter.getPhaseCount(),
                meter.getType().getName()
        );
    }

    public ElectricMeterTypeResponse toTypeResponse(ElectricMeterTypeEntity type) {
        return new ElectricMeterTypeResponse(
                type.getId(),
                type.getName()
        );
    }
}