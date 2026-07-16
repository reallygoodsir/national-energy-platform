package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.entity.*;
import com.really.good.sir.energy.exception.UserNotFoundException;
import com.really.good.sir.energy.mapper.UserLocationMapper;
import com.really.good.sir.energy.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLocationService {

    private static final Logger log = LoggerFactory.getLogger(UserLocationService.class);

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserLocationMapper mapper;

    public UserLocationService(
            UserRepository userRepository,
            CityRepository cityRepository,
            StreetRepository streetRepository,
            ApartmentRepository apartmentRepository,
            UserLocationMapper mapper
    ) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
        this.apartmentRepository = apartmentRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void assignLocation(Long userId, UserLocationRequest request) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

        CityEntity city = cityRepository.findByName(request.getCity())
                .orElseGet(() -> cityRepository.save(mapper.toCity(request)));

        StreetEntity street = streetRepository
                .findByCityIdAndName(city.getId(), request.getStreet())
                .orElseGet(() -> streetRepository.save(mapper.toStreet(request, city)));

        ApartmentEntity apartment = apartmentRepository
                .findByStreetIdAndBuildingNumberAndApartmentNumber(
                        street.getId(),
                        request.getBuildingNumber(),
                        request.getApartmentNumber()
                )
                .orElseGet(() -> mapper.toApartment(request, street));

        apartment.setUser(user);

        apartmentRepository.save(apartment);

        log.info("Location assigned, userId={}, city={}, street={}, building={}, apartment={}",
                userId, request.getCity(), request.getStreet(),
                request.getBuildingNumber(), request.getApartmentNumber());
    }
}