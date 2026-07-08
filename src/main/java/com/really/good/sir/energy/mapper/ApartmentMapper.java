package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import org.springframework.stereotype.Service;

@Service
public class ApartmentMapper {

    public ApartmentResponse toResponse(ApartmentEntity apartment) {
        return new ApartmentResponse(
                apartment.getId(),
                apartment.getStreet().getName(),
                apartment.getBuildingNumber(),
                apartment.getApartmentNumber()
        );
    }
}
