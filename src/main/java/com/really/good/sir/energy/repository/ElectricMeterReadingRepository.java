package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ElectricMeterReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElectricMeterReadingRepository extends JpaRepository<ElectricMeterReadingEntity, Long> {
    Optional<ElectricMeterReadingEntity> findTopByMeterIdOrderByReadingDateDesc(Long meterId);
    List<ElectricMeterReadingEntity> findByMeterIdOrderByReadingDateDesc(Long meterId);
    List<ElectricMeterReadingEntity> findByMeterIdOrderByReadingDateAsc(Long meterId);
}