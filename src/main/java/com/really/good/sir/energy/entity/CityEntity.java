package com.really.good.sir.energy.entity;

import jakarta.persistence.*;

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

    public CityEntity(Long id, String name, Set<StreetEntity> streets) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreets(Set<StreetEntity> streets) {
        this.streets = streets;
    }
}