package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.request.MeterRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.service.ElectricMeterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectricMeterControllerTest {

    @Mock private ElectricMeterService electricMeterService;

    @InjectMocks
    private ElectricMeterController electricMeterController;

    @Test
    void create_delegatesToServiceAndReturnsResponse() {

        ElectricMeterRequest request = new ElectricMeterRequest();
        ElectricMeterResponse expected = new ElectricMeterResponse(1L, "ABC123", 1, "Digital");

        when(electricMeterService.create(request)).thenReturn(expected);

        ElectricMeterResponse response = electricMeterController.create(request);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void assignMeter_delegatesToServiceWithApartmentIdAndRequest() {

        MeterRequest request = new MeterRequest();
        request.setMeterId(5L);

        electricMeterController.assignMeter(10L, request);

        verify(electricMeterService).assignMeter(10L, request);
    }

    @Test
    void removeMeter_delegatesToServiceWithApartmentId() {

        electricMeterController.removeMeter(10L);

        verify(electricMeterService).removeMeter(10L);
    }

    @Test
    void getAvailableMeters_returnsListFromService() {

        List<ElectricMeterResponse> expected = List.of(new ElectricMeterResponse(1L, "ABC123", 1, "Digital"));
        when(electricMeterService.getAvailableMeters()).thenReturn(expected);

        List<ElectricMeterResponse> response = electricMeterController.getAvailableMeters();

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void getAssignedMeter_returnsResponseFromService() {

        ElectricMeterResponse expected = new ElectricMeterResponse(1L, "ABC123", 1, "Digital");
        when(electricMeterService.getAssignedMeter(10L)).thenReturn(expected);

        ElectricMeterResponse response = electricMeterController.getAssignedMeter(10L);

        assertThat(response).isEqualTo(expected);
    }
}