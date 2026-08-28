package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.response.MeterScanResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.MeterReadingNotDetectedException;
import com.really.good.sir.energy.integration.google.client.VisionOcrClient;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import com.really.good.sir.energy.repository.ApartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeterScanServiceTest {

    @Mock private VisionOcrClient visionOcrClient;
    @Mock private ApartmentRepository apartmentRepository;

    @InjectMocks
    private MeterScanService meterScanService;

    private ApartmentEntity apartment;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        UserEntity owner = new UserEntity();
        owner.setEmail("owner@example.com");

        apartment = new ApartmentEntity();
        apartment.setId(10L);
        apartment.setUser(owner);

        file = new MockMultipartFile("file", "meter.jpg", "image/jpeg", "fake-bytes".getBytes());
    }

    @Test
    void scan_throwsApartmentAccessDeniedException_whenNotOwner() {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));

        assertThatThrownBy(() -> meterScanService.scan(10L, file, "intruder@example.com"))
                .isInstanceOf(ApartmentAccessDeniedException.class);
    }

    @Test
    void scan_throwsMeterReadingNotDetectedException_whenNoDigitBlocksFound() throws IOException {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(visionOcrClient.detectText(file.getBytes()))
                .thenReturn(List.of(new DetectedTextBlock("SERIAL-ABC", 100.0)));

        assertThatThrownBy(() -> meterScanService.scan(10L, file, "owner@example.com"))
                .isInstanceOf(MeterReadingNotDetectedException.class);
    }

    @Test
    void scan_picksLongestDigitBlock_whenMultipleCandidatesFound() throws Exception {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(visionOcrClient.detectText(file.getBytes())).thenReturn(List.of(
                new DetectedTextBlock("4471", 900.0),
                new DetectedTextBlock("1465.2", 300.0)
        ));

        MeterScanResponse response = meterScanService.scan(10L, file, "owner@example.com");

        assertThat(response.getDetectedValue()).isEqualTo("1465.2");
    }

    @Test
    void scan_usesBoundingBoxAreaAsTiebreaker_whenDigitCountEqual() throws Exception {

        when(apartmentRepository.findById(10L)).thenReturn(Optional.of(apartment));
        when(visionOcrClient.detectText(file.getBytes())).thenReturn(List.of(
                new DetectedTextBlock("1234", 100.0),
                new DetectedTextBlock("5678", 500.0)
        ));

        MeterScanResponse response = meterScanService.scan(10L, file, "owner@example.com");

        assertThat(response.getDetectedValue()).isEqualTo("5678");
    }
}