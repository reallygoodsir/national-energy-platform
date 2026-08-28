package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.CityEntity;
import com.really.good.sir.energy.entity.StreetEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserLocationMapperTest {

    private final UserLocationMapper mapper = new UserLocationMapper();

    @Test
    void toCity_mapsNameFromRequest() {

        UserLocationRequest request = new UserLocationRequest();
        request.setCity("Lviv");

        CityEntity city = mapper.toCity(request);

        assertThat(city.getName()).isEqualTo("Lviv");
    }

    @Test
    void toStreet_mapsNameAndAttachesGivenCity() {

        UserLocationRequest request = new UserLocationRequest();
        request.setStreet("Main St");

        CityEntity city = new CityEntity();
        city.setId(1L);

        StreetEntity street = mapper.toStreet(request, city);

        assertThat(street.getName()).isEqualTo("Main St");
        assertThat(street.getCity()).isEqualTo(city);
    }

    @Test
    void toApartment_mapsBuildingApartmentNumberAndAttachesGivenStreet() {

        UserLocationRequest request = new UserLocationRequest();
        request.setBuildingNumber("12");
        request.setApartmentNumber("4");

        StreetEntity street = new StreetEntity();
        street.setId(2L);

        ApartmentEntity apartment = mapper.toApartment(request, street);

        assertThat(apartment.getBuildingNumber()).isEqualTo("12");
        assertThat(apartment.getApartmentNumber()).isEqualTo("4");
        assertThat(apartment.getStreet()).isEqualTo(street);
    }
}