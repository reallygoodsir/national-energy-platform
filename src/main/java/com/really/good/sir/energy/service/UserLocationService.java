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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLocationService.class);

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserLocationMapper mapper;

    public UserLocationService(
            final UserRepository userRepository,
            final CityRepository cityRepository,
            final StreetRepository streetRepository,
            final ApartmentRepository apartmentRepository,
            final UserLocationMapper mapper
    ) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
        this.apartmentRepository = apartmentRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void assignLocation(final Long userId, final UserLocationRequest request) {

        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

        final CityEntity city = cityRepository.findByName(request.getCity())
                .orElseGet(() -> cityRepository.save(mapper.toCity(request)));

        final StreetEntity street = streetRepository
                .findByCityIdAndName(city.getId(), request.getStreet())
                .orElseGet(() -> streetRepository.save(mapper.toStreet(request, city)));

        final ApartmentEntity apartment = apartmentRepository
                .findByStreetIdAndBuildingNumberAndApartmentNumber(
                        street.getId(),
                        request.getBuildingNumber(),
                        request.getApartmentNumber()
                )
                .orElseGet(() -> mapper.toApartment(request, street));

        apartment.setUser(user);

        apartmentRepository.save(apartment);

        LOGGER.info("Location assigned, userId={}, city={}, street={}, building={}, apartment={}",
                userId, request.getCity(), request.getStreet(),
                request.getBuildingNumber(), request.getApartmentNumber());
    }
}