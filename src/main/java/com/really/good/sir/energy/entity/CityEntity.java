package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "city")
    private Set<StreetEntity> streets = new HashSet<>();

    public CityEntity() {
    }

    public CityEntity(final Long id, final String name, final Set<StreetEntity> streets) {
        this.id = id;
        this.name = name;
        this.streets = streets;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<StreetEntity> getStreets() {
        return streets;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStreets(final Set<StreetEntity> streets) {
        this.streets = streets;
    }
}