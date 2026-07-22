package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.ElectricMeterReadingRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.dto.response.MeterScanResponse;
import com.really.good.sir.energy.service.ElectricMeterReadingService;
import com.really.good.sir.energy.service.MeterScanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ElectricMeterReadingController {

    private final ElectricMeterReadingService readingService;
    private final MeterScanService meterScanService;

    public ElectricMeterReadingController(ElectricMeterReadingService readingService,
                                          MeterScanService meterScanService) {
        this.readingService = readingService;
        this.meterScanService = meterScanService;
    }

    @PostMapping("/{apartmentId}/readings")
    @ResponseStatus(HttpStatus.CREATED)
    public ElectricMeterReadingResponse submit(
            @PathVariable Long apartmentId,
            @Valid @RequestBody ElectricMeterReadingRequest request,
            Authentication authentication) {

        return readingService.submitReading(apartmentId, request, authentication.getName());
    }

    @GetMapping("/{apartmentId}/readings")
    public List<ElectricMeterReadingResponse> getReadings(
            @PathVariable Long apartmentId,
            Authentication authentication) {

        return readingService.getReadings(apartmentId, authentication.getName());
    }

    @GetMapping("/{apartmentId}/readings/usage")
    public List<ElectricMeterUsageResponse> getUsage(
            @PathVariable Long apartmentId,
            Authentication authentication) {

        return readingService.getUsage(apartmentId, authentication.getName());
    }

    @PostMapping(value = "/{apartmentId}/readings/scan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MeterScanResponse scan(
            @PathVariable Long apartmentId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        return meterScanService.scan(apartmentId, file, authentication.getName());
    }
}