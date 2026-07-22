package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "apartments",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames =
                                {
                                        "user_id",
                                        "street_id",
                                        "building_number",
                                        "apartment_number"
                                })
        }
)
public class ApartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "apartment_number", nullable = false)
    private String apartmentNumber;

    @ManyToOne
    @JoinColumn(name = "street_id", nullable = false)
    private StreetEntity street;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public ApartmentEntity() {
    }

    public ApartmentEntity(Long id, String buildingNumber, String apartmentNumber,
                           StreetEntity street, UserEntity user) {
        this.id = id;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.street = street;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public StreetEntity getStreet() {
        return street;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void setStreet(StreetEntity street) {
        this.street = street;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}