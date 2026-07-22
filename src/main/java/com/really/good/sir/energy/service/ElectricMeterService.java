package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.request.MeterRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import com.really.good.sir.energy.exception.ApartmentNotFoundException;
import com.really.good.sir.energy.exception.ElectricMeterNotFoundException;
import com.really.good.sir.energy.exception.MeterTypeNotFoundException;
import com.really.good.sir.energy.exception.SerialNumberAlreadyExistsException;
import com.really.good.sir.energy.mapper.ElectricMeterMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.ElectricMeterRepository;
import com.really.good.sir.energy.repository.ElectricMeterTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectricMeterService {

    private static final Logger log = LoggerFactory.getLogger(ElectricMeterService.class);

    private final ElectricMeterRepository meterRepository;
    private final ElectricMeterTypeRepository typeRepository;
    private final ElectricMeterMapper electricMeterMapper;
    private final ApartmentRepository apartmentRepository;

    public ElectricMeterService(
            ElectricMeterRepository meterRepository,
            ElectricMeterTypeRepository typeRepository,
            ElectricMeterMapper electricMeterMapper,
            ApartmentRepository apartmentRepository
    ) {
        this.meterRepository = meterRepository;
        this.typeRepository = typeRepository;
        this.electricMeterMapper = electricMeterMapper;
        this.apartmentRepository = apartmentRepository;
    }

    public ElectricMeterResponse create(ElectricMeterRequest request) {

        if (meterRepository.existsBySerialNumber(request.getSerialNumber())) {
            log.warn("Meter creation rejected, duplicate serialNumber={}", request.getSerialNumber());
            throw new SerialNumberAlreadyExistsException(request.getSerialNumber());
        }

        ElectricMeterTypeEntity type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new MeterTypeNotFoundException(request.getTypeId()));

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setSerialNumber(request.getSerialNumber());
        meter.setPhaseCount(request.getPhaseCount());
        meter.setType(type);

        ElectricMeterEntity saved = meterRepository.save(meter);

        log.info("Meter created, meterId={}, serialNumber={}", saved.getId(), saved.getSerialNumber());

        return electricMeterMapper.toResponse(saved);
    }

    public List<ElectricMeterTypeResponse> getAllTypes() {
        return typeRepository.findAll()
                .stream()
                .map(electricMeterMapper::toTypeResponse)
                .toList();
    }

    public void assignMeter(Long apartmentId, MeterRequest request) {

        ApartmentEntity apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException(apartmentId));

        ElectricMeterEntity meter = meterRepository.findById(request.getMeterId())
                .orElseThrow(() -> new ElectricMeterNotFoundException(request.getMeterId()));

        meter.setApartment(apartment);
        meterRepository.save(meter);

        log.info("Meter assigned, meterId={}, apartmentId={}", meter.getId(), apartmentId);
    }

    public void removeMeter(Long apartmentId) {

        ElectricMeterEntity meter = meterRepository.findByApartmentId(apartmentId)
                .orElseThrow(() ->
                        new ElectricMeterNotFoundException(
                                "No electric meter is assigned to apartment with ID " + apartmentId
                        ));

        meter.setApartment(null);
        meterRepository.save(meter);

        log.info("Meter unassigned from apartmentId={}, meterId={}", apartmentId, meter.getId());
    }

    public List<ElectricMeterResponse> getAvailableMeters() {
        return meterRepository.findByApartmentIsNull()
                .stream()
                .map(electricMeterMapper::toResponse)
                .toList();
    }

    public ElectricMeterResponse getAssignedMeter(Long apartmentId) {
        ElectricMeterEntity meter = meterRepository.findByApartmentId(apartmentId)
                .orElseThrow(() -> new ElectricMeterNotFoundException(apartmentId));

        return electricMeterMapper.toResponse(meter);
    }
}