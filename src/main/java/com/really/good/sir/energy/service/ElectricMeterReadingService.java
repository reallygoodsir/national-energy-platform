package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.ElectricMeterReadingRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterReadingEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.ApartmentNotFoundException;
import com.really.good.sir.energy.exception.ElectricMeterNotFoundException;
import com.really.good.sir.energy.exception.InvalidReadingValueException;
import com.really.good.sir.energy.mapper.ElectricMeterReadingMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.ElectricMeterReadingRepository;
import com.really.good.sir.energy.repository.ElectricMeterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElectricMeterReadingService {

    private static final Logger log = LoggerFactory.getLogger(ElectricMeterReadingService.class);

    private final ElectricMeterReadingRepository readingRepository;
    private final ElectricMeterRepository meterRepository;
    private final ApartmentRepository apartmentRepository;
    private final ElectricMeterReadingMapper readingMapper;

    public ElectricMeterReadingService(
            ElectricMeterReadingRepository readingRepository,
            ElectricMeterRepository meterRepository,
            ApartmentRepository apartmentRepository,
            ElectricMeterReadingMapper readingMapper
    ) {
        this.readingRepository = readingRepository;
        this.meterRepository = meterRepository;
        this.apartmentRepository = apartmentRepository;
        this.readingMapper = readingMapper;
    }

    public List<ElectricMeterReadingResponse> getReadings(Long apartmentId, String requesterEmail) {

        ElectricMeterEntity meter = resolveOwnedMeter(apartmentId, requesterEmail);

        return readingRepository.findByMeterIdOrderByReadingDateDesc(meter.getId())
                .stream()
                .map(readingMapper::toResponse)
                .toList();
    }

    public ElectricMeterReadingResponse submitReading(
            Long apartmentId,
            ElectricMeterReadingRequest request,
            String requesterEmail) {

        log.info("Reading submission attempt, apartmentId={}, value={}, user={}",
                apartmentId, request.getValue(), requesterEmail);

        ElectricMeterEntity meter = resolveOwnedMeter(apartmentId, requesterEmail);

        readingRepository.findTopByMeterIdOrderByReadingDateDesc(meter.getId())
                .ifPresent(lastReading -> {
                    if (request.getValue() < lastReading.getValue()) {
                        log.warn("Reading rejected, new value={} lower than last value={}, meterId={}",
                                request.getValue(), lastReading.getValue(), meter.getId());
                        throw new InvalidReadingValueException(request.getValue(), lastReading.getValue());
                    }
                });

        ElectricMeterReadingEntity reading = new ElectricMeterReadingEntity();
        reading.setMeter(meter);
        reading.setValue(request.getValue());
        reading.setReadingDate(LocalDateTime.now());

        ElectricMeterReadingEntity saved = readingRepository.save(reading);

        log.info("Reading saved, readingId={}, meterId={}, value={}", saved.getId(), meter.getId(), saved.getValue());

        return readingMapper.toResponse(saved);
    }

    public List<ElectricMeterUsageResponse> getUsage(Long apartmentId, String requesterEmail) {

        ElectricMeterEntity meter = resolveOwnedMeter(apartmentId, requesterEmail);

        List<ElectricMeterReadingEntity> readings =
                readingRepository.findByMeterIdOrderByReadingDateAsc(meter.getId());

        List<ElectricMeterUsageResponse> usage = new ArrayList<>();

        for (int i = 1; i < readings.size(); i++) {
            ElectricMeterReadingEntity older = readings.get(i - 1);
            ElectricMeterReadingEntity newer = readings.get(i);
            usage.add(readingMapper.toUsageResponse(older, newer));
        }

        log.info("Computed {} usage period(s) for meterId={}", usage.size(), meter.getId());

        return usage;
    }

    private ElectricMeterEntity resolveOwnedMeter(Long apartmentId, String requesterEmail) {

        ApartmentEntity apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException(apartmentId));

        if (!apartment.getUser().getEmail().equals(requesterEmail)) {
            log.warn("User={} attempted to access apartmentId={} they do not own", requesterEmail, apartmentId);
            throw new ApartmentAccessDeniedException(apartmentId);
        }

        return meterRepository.findByApartmentId(apartmentId)
                .orElseThrow(() ->
                        new ElectricMeterNotFoundException(
                                "No electric meter is assigned to apartment with ID " + apartmentId
                        ));
    }
}