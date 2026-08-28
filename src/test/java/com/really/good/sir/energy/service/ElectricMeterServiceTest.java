package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.request.MeterRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.MeterTypeNotFoundException;
import com.really.good.sir.energy.exception.SerialNumberAlreadyExistsException;
import com.really.good.sir.energy.mapper.ElectricMeterMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.ElectricMeterRepository;
import com.really.good.sir.energy.repository.ElectricMeterTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectricMeterServiceTest {

    @Mock private ElectricMeterRepository meterRepository;
    @Mock private ElectricMeterTypeRepository typeRepository;
    @Mock private ElectricMeterMapper electricMeterMapper;
    @Mock private ApartmentRepository apartmentRepository;

    @InjectMocks
    private ElectricMeterService meterService;

    private ElectricMeterRequest createRequest;
    private ApartmentEntity apartment;

    @BeforeEach
    void setUp() {
        createRequest = new ElectricMeterRequest();
        createRequest.setSerialNumber("ABC123");
        createRequest.setTypeId(5L);

        UserEntity owner = new UserEntity();
        owner.setEmail("owner@example.com");

        apartment = new ApartmentEntity();
        apartment.setId(10L);
        apartment.setUser(owner);
    }

    @Test
    void create_throwsSerialNumberAlreadyExistsException_whenDuplicate() {

        when(meterRepository.existsBySerialNumber("ABC123")).thenReturn(true);

        assertThatThrownBy(() -> meterService.create(createRequest))
                .isInstanceOf(SerialNumberAlreadyExistsException.class);

        verify(typeRepository, never()).findById(any());
    }

    @Test
    void create_throwsMeterTypeNotFoundException_whenTypeMissing() {

        when(meterRepository.existsBySerialNumber("ABC123")).thenReturn(false);
        when(typeRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> meterService.create(createRequest))
                .isInstanceOf(MeterTypeNotFoundException.class);

        verify(meterRepository, never()).save(any());
    }

    @Test
    void create_savesMeter_whenValid() {

        when(meterRepository.existsBySerialNumber("ABC123")).thenReturn(false);
        when(typeRepository.findById(5L)).thenReturn(Optional.of(new ElectricMeterTypeEntity()));
        when(meterRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        meterService.create(createRequest);

        verify(meterRepository).save(any());
    }

    @Test
    void assignMeter_setsApartmentAndSaves() {

        MeterRequest request = new MeterRequest();
        request.setMeterId(20L);

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setId(20L);

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(meterRepository.findById(20L)).thenReturn(Optional.of(meter));

        meterService.assignMeter(10L, request);

        assertThat(meter.getApartment()).isEqualTo(apartment);
        verify(meterRepository).save(meter);
    }

    @Test
    void removeMeter_clearsApartmentAndSaves() {

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setId(20L);
        meter.setApartment(apartment);

        when(meterRepository.findByApartmentId(10L)).thenReturn(Optional.of(meter));

        meterService.removeMeter(10L);

        assertThat(meter.getApartment()).isNull();
        verify(meterRepository).save(meter);
    }

    @Test
    void getOwnedAssignedMeter_throwsApartmentAccessDeniedException_whenNotOwner() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));

        assertThatThrownBy(() -> meterService.getOwnedAssignedMeter(10L, "intruder@example.com"))
                .isInstanceOf(ApartmentAccessDeniedException.class);

        verify(meterRepository, never()).findByApartmentId(any());
    }

    @Test
    void getOwnedAssignedMeter_returnsMeter_whenOwnerMatches() {

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setId(20L);

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(meterRepository.findByApartmentId(10L)).thenReturn(Optional.of(meter));
        when(electricMeterMapper.toResponse(meter)).thenReturn(null);

        meterService.getOwnedAssignedMeter(10L, "owner@example.com");

        verify(electricMeterMapper).toResponse(meter);
    }
}