package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "electric_meters")
public class ElectricMeterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "phase_count", nullable = false)
    private Integer phaseCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ElectricMeterTypeEntity type;

    @OneToOne
    @JoinColumn(name = "apartment_id", unique = true)
    private ApartmentEntity apartment;

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getPhaseCount() {
        return phaseCount;
    }

    public void setPhaseCount(final Integer phaseCount) {
        this.phaseCount = phaseCount;
    }

    public ElectricMeterTypeEntity getType() {
        return type;
    }

    public void setType(final ElectricMeterTypeEntity type) {
        this.type = type;
    }

    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(final ApartmentEntity apartment) {
        this.apartment = apartment;
    }
}