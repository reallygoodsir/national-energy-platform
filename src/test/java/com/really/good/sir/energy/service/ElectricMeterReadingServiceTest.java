package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.ElectricMeterReadingRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterReadingEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.ApartmentNotFoundException;
import com.really.good.sir.energy.exception.InvalidReadingValueException;
import com.really.good.sir.energy.mapper.ElectricMeterReadingMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.ElectricMeterReadingRepository;
import com.really.good.sir.energy.repository.ElectricMeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectricMeterReadingServiceTest {

    @Mock private ElectricMeterReadingRepository readingRepository;
    @Mock private ElectricMeterRepository meterRepository;
    @Mock private ApartmentRepository apartmentRepository;
    @Mock private ElectricMeterReadingMapper readingMapper;

    @InjectMocks
    private ElectricMeterReadingService readingService;

    private ApartmentEntity apartment;
    private ElectricMeterEntity meter;

    @BeforeEach
    void setUp() {
        UserEntity owner = new UserEntity();
        owner.setEmail("owner@example.com");

        apartment = new ApartmentEntity();
        apartment.setId(10L);
        apartment.setUser(owner);

        meter = new ElectricMeterEntity();
        meter.setId(20L);
    }

    @Test
    void submitReading_throwsApartmentNotFoundException_whenApartmentMissing() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.empty());

        ElectricMeterReadingRequest request = new ElectricMeterReadingRequest();
        request.setValue(100.0);

        assertThatThrownBy(() -> readingService.submitReading(10L, request, "owner@example.com"))
                .isInstanceOf(ApartmentNotFoundException.class);
    }

    @Test
    void submitReading_throwsApartmentAccessDeniedException_whenNotOwner() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));

        ElectricMeterReadingRequest request = new ElectricMeterReadingRequest();
        request.setValue(100.0);

        assertThatThrownBy(() -> readingService.submitReading(10L, request, "intruder@example.com"))
                .isInstanceOf(ApartmentAccessDeniedException.class);

        verify(meterRepository, never()).findByApartmentId(any());
    }

    @Test
    void submitReading_throwsInvalidReadingValueException_whenValueLowerThanLast() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(meterRepository.findByApartmentId(10L)).thenReturn(Optional.of(meter));

        ElectricMeterReadingEntity lastReading = new ElectricMeterReadingEntity();
        lastReading.setValue(500.0);
        when(readingRepository.findTopByMeterIdOrderByReadingDateDesc(20L))
                .thenReturn(Optional.of(lastReading));

        ElectricMeterReadingRequest request = new ElectricMeterReadingRequest();
        request.setValue(100.0);

        assertThatThrownBy(() -> readingService.submitReading(10L, request, "owner@example.com"))
                .isInstanceOf(InvalidReadingValueException.class);

        verify(readingRepository, never()).save(any());
    }

    @Test
    void submitReading_savesReading_whenValueValidAndNoPreviousReading() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(meterRepository.findByApartmentId(10L)).thenReturn(Optional.of(meter));
        when(readingRepository.findTopByMeterIdOrderByReadingDateDesc(20L))
                .thenReturn(Optional.empty());

        ElectricMeterReadingRequest request = new ElectricMeterReadingRequest();
        request.setValue(100.0);

        ElectricMeterReadingEntity savedEntity = new ElectricMeterReadingEntity();
        savedEntity.setId(1L);
        savedEntity.setValue(100.0);
        when(readingRepository.save(any())).thenReturn(savedEntity);

        readingService.submitReading(10L, request, "owner@example.com");

        verify(readingRepository).save(any());
    }
}