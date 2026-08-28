package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.ElectricMeterReadingRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.dto.response.MeterScanResponse;
import com.really.good.sir.energy.service.ElectricMeterReadingService;
import com.really.good.sir.energy.service.MeterScanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElectricMeterReadingControllerTest {

    @Mock private ElectricMeterReadingService readingService;
    @Mock private MeterScanService meterScanService;
    @Mock private Authentication authentication;

    @InjectMocks
    private ElectricMeterReadingController readingController;

    @BeforeEach
    void setUp() {
        when(authentication.getName()).thenReturn("owner@example.com");
    }

    @Test
    void submit_delegatesToServiceWithApartmentIdRequestAndEmail() {

        ElectricMeterReadingRequest request = new ElectricMeterReadingRequest();
        ElectricMeterReadingResponse expected = new ElectricMeterReadingResponse(1L, 100.0, null);

        when(readingService.submitReading(10L, request, "owner@example.com")).thenReturn(expected);

        ElectricMeterReadingResponse response = readingController.submit(10L, request, authentication);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void getReadings_returnsListFromService() {

        List<ElectricMeterReadingResponse> expected = List.of(new ElectricMeterReadingResponse(1L, 100.0, null));
        when(readingService.getReadings(10L, "owner@example.com")).thenReturn(expected);

        List<ElectricMeterReadingResponse> response = readingController.getReadings(10L, authentication);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void getUsage_returnsListFromService() {

        List<ElectricMeterUsageResponse> expected = List.of(new ElectricMeterUsageResponse(null, null, 50.0));
        when(readingService.getUsage(10L, "owner@example.com")).thenReturn(expected);

        List<ElectricMeterUsageResponse> response = readingController.getUsage(10L, authentication);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void scan_delegatesToServiceWithApartmentIdFileAndEmail() {

        MultipartFile file = new MockMultipartFile("file", "meter.jpg", "image/jpeg", "bytes".getBytes());
        MeterScanResponse expected = new MeterScanResponse("1465.2");

        when(meterScanService.scan(10L, file, "owner@example.com")).thenReturn(expected);

        MeterScanResponse response = readingController.scan(10L, file, authentication);

        assertThat(response).isEqualTo(expected);
    }
}