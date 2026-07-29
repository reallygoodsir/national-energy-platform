package com.really.good.sir.energy.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserLocationRequest {

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Building number is required")
    private String buildingNumber;

    @NotBlank(message = "Apartment number is required")
    private String apartmentNumber;

    public String getCity() {
        return city;
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

    public void setCity(final String city) {
        this.city = city;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public void setBuildingNumber(final String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setApartmentNumber(final String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}