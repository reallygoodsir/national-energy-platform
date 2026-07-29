package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "electric_meter_readings")
public class ElectricMeterReadingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private ElectricMeterEntity meter;

    @Column(nullable = false)
    private Double value;

    @Column(name = "reading_date", nullable = false)
    private LocalDateTime readingDate;

    public Long getId() {
        return id;
    }

    public ElectricMeterEntity getMeter() {
        return meter;
    }

    public void setMeter(final ElectricMeterEntity meter) {
        this.meter = meter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    public LocalDateTime getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(final LocalDateTime readingDate) {
        this.readingDate = readingDate;
    }
}