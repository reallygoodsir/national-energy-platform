package com.really.good.sir.energy.dto.response;

public class ApartmentResponse {

    private Long id;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;

    public ApartmentResponse(Long id, String street, String buildingNumber, String apartmentNumber) {
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