package com.really.good.sir.energy.repository;

import com.really.good.sir.energy.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {

    Optional<ApartmentEntity> findByStreetIdAndBuildingNumberAndApartmentNumber(
            Long streetId,
            String buildingNumber,
            String apartmentNumber
    );

    List<ApartmentEntity> findAllByUserId(Long userId);

    @Query("""
        SELECT a FROM ApartmentEntity a
        WHERE a.user.id = :userId
        AND EXISTS (SELECT m FROM ElectricMeterEntity m WHERE m.apartment = a)
    """)
    List<ApartmentEntity> findAllByUserIdWithMeterAssigned(@Param("userId") Long userId);
}