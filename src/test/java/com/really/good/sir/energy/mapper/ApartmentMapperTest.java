package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.StreetEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApartmentMapperTest {

    private final ApartmentMapper apartmentMapper = new ApartmentMapper();

    @Test
    void toResponse_mapsAllFieldsIncludingNestedStreetName() {

        StreetEntity street = new StreetEntity();
        street.setName("Main St");

        ApartmentEntity apartment = new ApartmentEntity();
        apartment.setId(1L);
        apartment.setStreet(street);
        apartment.setBuildingNumber("12");
        apartment.setApartmentNumber("4");

        ApartmentResponse response = apartmentMapper.toResponse(apartment);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStreet()).isEqualTo("Main St");
        assertThat(response.getBuildingNumber()).isEqualTo("12");
        assertThat(response.getApartmentNumber()).isEqualTo("4");
    }
}