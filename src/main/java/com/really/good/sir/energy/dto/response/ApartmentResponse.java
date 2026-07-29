package com.really.good.sir.energy.dto.response;

public class ApartmentResponse {

    private final Long id;
    private final String street;
    private final String buildingNumber;
    private final String apartmentNumber;

    public ApartmentResponse(final Long id, final String street,
                             final String buildingNumber, final String apartmentNumber) {
        this.id = id;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }
}