package com.really.good.sir.energy.entity;

import jakarta.persistence.*;

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

    public ElectricMeterEntity() {}

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getPhaseCount() {
        return phaseCount;
    }

    public void setPhaseCount(Integer phaseCount) {
        this.phaseCount = phaseCount;
    }

    public ElectricMeterTypeEntity getType() {
        return type;
    }

    public void setType(ElectricMeterTypeEntity type) {
        this.type = type;
    }
}