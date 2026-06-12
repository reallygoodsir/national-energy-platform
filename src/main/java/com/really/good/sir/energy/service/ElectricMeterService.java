package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import com.really.good.sir.energy.repository.ElectricMeterRepository;
import com.really.good.sir.energy.repository.ElectricMeterTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectricMeterService {

    private final ElectricMeterRepository meterRepository;
    private final ElectricMeterTypeRepository typeRepository;

    public ElectricMeterService(
            ElectricMeterRepository meterRepository,
            ElectricMeterTypeRepository typeRepository
    ) {
        this.meterRepository = meterRepository;
        this.typeRepository = typeRepository;
    }

    public ElectricMeterResponse create(ElectricMeterRequest request) {

        if (meterRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new RuntimeException("Serial number already exists");
        }

        ElectricMeterTypeEntity type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("Meter type not found"));

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setSerialNumber(request.getSerialNumber());
        meter.setPhaseCount(request.getPhaseCount());
        meter.setType(type);

        ElectricMeterEntity saved = meterRepository.save(meter);

        return new ElectricMeterResponse(
                saved.getId(),
                saved.getSerialNumber(),
                saved.getPhaseCount(),
                saved.getType().getName()
        );
    }

    public List<ElectricMeterTypeResponse> getAllTypes() {
        return typeRepository.findAll()
                .stream()
                .map(t -> new ElectricMeterTypeResponse(t.getId(), t.getName()))
                .toList();
    }
}