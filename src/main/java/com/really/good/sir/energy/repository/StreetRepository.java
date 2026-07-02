package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreetRepository extends JpaRepository<StreetEntity, Long> {
    Optional<StreetEntity> findByCityIdAndName(Long cityId, String name);
}