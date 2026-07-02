package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.CityEntity;
import com.really.good.sir.energy.entity.StreetEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLocationMapper {

    public CityEntity toCity(UserLocationRequest request) {
        CityEntity city = new CityEntity();
        city.setName(request.getCity());
        return city;
    }

    public StreetEntity toStreet(UserLocationRequest request, CityEntity city) {
        StreetEntity street = new StreetEntity();
        street.setName(request.getStreet());
        street.setCity(city);
        return street;
    }

    public ApartmentEntity toApartment(
            UserLocationRequest request,
            StreetEntity street
    ) {
        ApartmentEntity apartment = new ApartmentEntity();
        apartment.setStreet(street);
        apartment.setBuildingNumber(request.getBuildingNumber());
        apartment.setApartmentNumber(request.getApartmentNumber());
        return apartment;
    }
}