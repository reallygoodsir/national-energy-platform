package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectricMeterTypeRepository extends JpaRepository<ElectricMeterTypeEntity, Long> {
    Optional<ElectricMeterTypeEntity> findByName(String name);
}