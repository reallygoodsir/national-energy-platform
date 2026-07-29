package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.CityEntity;
import com.really.good.sir.energy.entity.StreetEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLocationMapper {

    public CityEntity toCity(final UserLocationRequest request) {
        final CityEntity city = new CityEntity();
        city.setName(request.getCity());
        return city;
    }

    public StreetEntity toStreet(final UserLocationRequest request, final CityEntity city) {
        final StreetEntity street = new StreetEntity();
        street.setName(request.getStreet());
        street.setCity(city);
        return street;
    }

    public ApartmentEntity toApartment(
            final UserLocationRequest request,
            final StreetEntity street
    ) {
        final ApartmentEntity apartment = new ApartmentEntity();
        apartment.setStreet(street);
        apartment.setBuildingNumber(request.getBuildingNumber());
        apartment.setApartmentNumber(request.getApartmentNumber());
        return apartment;
    }
}