package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.entity.ElectricMeterReadingEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricMeterReadingMapperTest {

    private final ElectricMeterReadingMapper readingMapper = new ElectricMeterReadingMapper();

    @Test
    void toResponse_mapsIdValueAndReadingDate() {

        LocalDateTime readingDate = LocalDateTime.of(2026, 7, 1, 10, 0);

        ElectricMeterReadingEntity reading = new ElectricMeterReadingEntity();
        reading.setId(1L);
        reading.setValue(150.0);
        reading.setReadingDate(readingDate);

        ElectricMeterReadingResponse response = readingMapper.toResponse(reading);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getValue()).isEqualTo(150.0);
        assertThat(response.getReadingDate()).isEqualTo(readingDate);
    }

    @Test
    void toUsageResponse_computesConsumptionAsDifferenceBetweenReadings() {

        LocalDateTime olderDate = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime newerDate = LocalDateTime.of(2026, 7, 1, 10, 0);

        ElectricMeterReadingEntity older = new ElectricMeterReadingEntity();
        older.setValue(1000.0);
        older.setReadingDate(olderDate);

        ElectricMeterReadingEntity newer = new ElectricMeterReadingEntity();
        newer.setValue(1150.0);
        newer.setReadingDate(newerDate);

        ElectricMeterUsageResponse response = readingMapper.toUsageResponse(older, newer);

        assertThat(response.getPeriodStart()).isEqualTo(olderDate);
        assertThat(response.getPeriodEnd()).isEqualTo(newerDate);
        assertThat(response.getConsumption()).isEqualTo(150.0);
    }

    @Test
    void toUsageResponse_computesZeroConsumption_whenValuesEqual() {

        ElectricMeterReadingEntity older = new ElectricMeterReadingEntity();
        older.setValue(1000.0);
        older.setReadingDate(LocalDateTime.now());

        ElectricMeterReadingEntity newer = new ElectricMeterReadingEntity();
        newer.setValue(1000.0);
        newer.setReadingDate(LocalDateTime.now());

        ElectricMeterUsageResponse response = readingMapper.toUsageResponse(older, newer);

        assertThat(response.getConsumption()).isEqualTo(0.0);
    }
}