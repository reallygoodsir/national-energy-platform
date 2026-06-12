package com.really.good.sir.energy.dto.response;

public class ElectricMeterTypeResponse {

    private Long id;
    private String name;

    public ElectricMeterTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}