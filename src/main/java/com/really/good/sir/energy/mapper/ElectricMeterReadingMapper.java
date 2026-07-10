package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.entity.ElectricMeterReadingEntity;
import org.springframework.stereotype.Service;

@Service
public class ElectricMeterReadingMapper {

    public ElectricMeterReadingResponse toResponse(ElectricMeterReadingEntity reading) {
        return new ElectricMeterReadingResponse(
                reading.getId(),
                reading.getValue(),
                reading.getReadingDate()
        );
    }

    public ElectricMeterUsageResponse toUsageResponse(
            ElectricMeterReadingEntity older,
            ElectricMeterReadingEntity newer) {

        return new ElectricMeterUsageResponse(
                older.getReadingDate(),
                newer.getReadingDate(),
                newer.getValue() - older.getValue()
        );
    }
}