package com.really.good.sir.energy.dto.response;

public class ElectricMeterTypeResponse {

    private final Long id;
    private final String name;

    public ElectricMeterTypeResponse(final Long id, final String name) {
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