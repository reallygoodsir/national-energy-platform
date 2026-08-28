package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.CityEntity;
import com.really.good.sir.energy.entity.StreetEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.UserNotFoundException;
import com.really.good.sir.energy.mapper.UserLocationMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.CityRepository;
import com.really.good.sir.energy.repository.StreetRepository;
import com.really.good.sir.energy.repository.UserRepository;
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
class UserLocationServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private CityRepository cityRepository;
    @Mock private StreetRepository streetRepository;
    @Mock private ApartmentRepository apartmentRepository;
    @Mock private UserLocationMapper mapper;

    @InjectMocks
    private UserLocationService userLocationService;

    private UserLocationRequest request;
    private UserEntity user;
    private CityEntity city;
    private StreetEntity street;

    @BeforeEach
    void setUp() {
        request = new UserLocationRequest();
        request.setCity("Lviv");
        request.setStreet("Main St");
        request.setBuildingNumber("12");
        request.setApartmentNumber("4");

        user = new UserEntity();
        user.setId(1L);

        city = new CityEntity();
        city.setId(2L);
        city.setName("Lviv");

        street = new StreetEntity();
        street.setId(3L);
        street.setName("Main St");
    }

    @Test
    void assignLocation_throwsUserNotFoundException_whenUserMissing() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userLocationService.assignLocation(1L, request))
                .isInstanceOf(UserNotFoundException.class);

        verify(cityRepository, never()).findByName(any());
    }

    @Test
    void assignLocation_reusesExistingCityAndStreet_whenAlreadyPresent() {

        ApartmentEntity apartment = new ApartmentEntity();
        apartment.setId(4L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cityRepository.findByName("Lviv")).thenReturn(Optional.of(city));
        when(streetRepository.findByCityIdAndName(2L, "Main St")).thenReturn(Optional.of(street));
        when(apartmentRepository.findByStreetIdAndBuildingNumberAndApartmentNumber(3L, "12", "4"))
                .thenReturn(Optional.of(apartment));

        userLocationService.assignLocation(1L, request);

        assertThat(apartment.getUser()).isEqualTo(user);
        verify(cityRepository, never()).save(any());
        verify(streetRepository, never()).save(any());
        verify(apartmentRepository).save(apartment);
    }

    @Test
    void assignLocation_createsNewCityStreetAndApartment_whenNoneExist() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cityRepository.findByName("Lviv")).thenReturn(Optional.empty());
        when(mapper.toCity(request)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);

        when(streetRepository.findByCityIdAndName(2L, "Main St")).thenReturn(Optional.empty());
        when(mapper.toStreet(request, city)).thenReturn(street);
        when(streetRepository.save(street)).thenReturn(street);

        ApartmentEntity newApartment = new ApartmentEntity();
        when(apartmentRepository.findByStreetIdAndBuildingNumberAndApartmentNumber(3L, "12", "4"))
                .thenReturn(Optional.empty());
        when(mapper.toApartment(request, street)).thenReturn(newApartment);

        userLocationService.assignLocation(1L, request);

        assertThat(newApartment.getUser()).isEqualTo(user);
        verify(cityRepository).save(city);
        verify(streetRepository).save(street);
        verify(apartmentRepository).save(newApartment);
    }
}