package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ElectricMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElectricMeterRepository extends JpaRepository<ElectricMeterEntity, Long> {
    boolean existsBySerialNumber(String serialNumber);

    Optional<ElectricMeterEntity> findByApartmentId(Long apartmentId);

    List<ElectricMeterEntity> findByApartmentIsNull();
}