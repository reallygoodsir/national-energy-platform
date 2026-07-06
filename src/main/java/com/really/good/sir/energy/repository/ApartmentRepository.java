package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {

    Optional<ApartmentEntity> findByStreetIdAndBuildingNumberAndApartmentNumber(
            Long streetId,
            String buildingNumber,
            String apartmentNumber
    );

    List<ApartmentEntity> findAllByUserId(Long userId);
}