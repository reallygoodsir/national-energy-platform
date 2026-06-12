package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ElectricMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricMeterRepository extends JpaRepository<ElectricMeterEntity, Long> {
    boolean existsBySerialNumber(String serialNumber);
}