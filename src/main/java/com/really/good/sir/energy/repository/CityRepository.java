package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByName(String name);
}