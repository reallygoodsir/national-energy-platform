package com.really.good.sir.energy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "electric_meters_types")
public class ElectricMeterTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public ElectricMeterTypeEntity() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}