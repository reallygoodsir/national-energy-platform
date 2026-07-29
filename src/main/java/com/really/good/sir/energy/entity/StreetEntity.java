package com.really.good.sir.energy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "streets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"city_id", "name"})
        }
)
public class StreetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @OneToMany(mappedBy = "street")
    private Set<ApartmentEntity> apartments = new HashSet<>();

    public StreetEntity() {
    }

    public StreetEntity(
            final Long id,
            final String name,
            final CityEntity city,
            final Set<ApartmentEntity> apartments) {

        this.id = id;
        this.name = name;
        this.city = city;
        this.apartments = apartments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityEntity getCity() {
        return city;
    }

    public Set<ApartmentEntity> getApartments() {
        return apartments;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCity(final CityEntity city) {
        this.city = city;
    }

    public void setApartments(final Set<ApartmentEntity> apartments) {
        this.apartments = apartments;
    }
}