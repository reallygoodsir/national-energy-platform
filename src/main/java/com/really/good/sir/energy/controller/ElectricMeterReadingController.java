package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.ElectricMeterReadingRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterReadingResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterUsageResponse;
import com.really.good.sir.energy.service.ElectricMeterReadingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ElectricMeterReadingController {

    private final ElectricMeterReadingService readingService;

    public ElectricMeterReadingController(ElectricMeterReadingService readingService) {
        this.readingService = readingService;
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
}