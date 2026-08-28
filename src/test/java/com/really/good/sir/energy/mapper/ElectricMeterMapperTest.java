package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.entity.ElectricMeterEntity;
import com.really.good.sir.energy.entity.ElectricMeterTypeEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricMeterMapperTest {

    private final ElectricMeterMapper electricMeterMapper = new ElectricMeterMapper();

    @Test
    void toResponse_mapsAllFieldsIncludingNestedTypeName() {

        ElectricMeterTypeEntity type = new ElectricMeterTypeEntity();
        type.setName("Digital");

        ElectricMeterEntity meter = new ElectricMeterEntity();
        meter.setId(1L);
        meter.setSerialNumber("ABC123");
        meter.setPhaseCount(3);
        meter.setType(type);

        ElectricMeterResponse response = electricMeterMapper.toResponse(meter);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getSerialNumber()).isEqualTo("ABC123");
        assertThat(response.getPhaseCount()).isEqualTo(3);
        assertThat(response.getTypeName()).isEqualTo("Digital");
    }

    @Test
    void toTypeResponse_mapsIdAndName() {

        ElectricMeterTypeEntity type = new ElectricMeterTypeEntity();
        type.setId(2L);
        type.setName("Digital");

        ElectricMeterTypeResponse response = electricMeterMapper.toTypeResponse(type);

        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("Digital");
    }
}