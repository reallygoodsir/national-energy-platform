package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.annotation.AdminOnly;
import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.request.MeterRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.service.ElectricMeterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/electric-meters")
public class ElectricMeterController {

    private final ElectricMeterService electricMeterService;

    public ElectricMeterController(final ElectricMeterService electricMeterService) {
        this.electricMeterService = electricMeterService;
    }

    @PostMapping
    @AdminOnly
    public ElectricMeterResponse create(@Valid @RequestBody final ElectricMeterRequest request) {
        return electricMeterService.create(request);
    }

    @GetMapping("/types")
    public List<ElectricMeterTypeResponse> getTypes() {
        return electricMeterService.getAllTypes();
    }

    @PostMapping("/apartments/{apartmentId}")
    @AdminOnly
    public void assignMeter(
            @PathVariable final Long apartmentId,
            @RequestBody final MeterRequest request
    ) {
        electricMeterService.assignMeter(apartmentId, request);
    }

    @DeleteMapping("/apartments/{apartmentId}")
    @AdminOnly
    public void removeMeter(@PathVariable final Long apartmentId) {
        electricMeterService.removeMeter(apartmentId);
    }

    @GetMapping("/available")
    public List<ElectricMeterResponse> getAvailableMeters() {
        return electricMeterService.getAvailableMeters();
    }

    @GetMapping("/apartment/{apartmentId}")
    public ElectricMeterResponse getAssignedMeter(@PathVariable final Long apartmentId) {
        return electricMeterService.getAssignedMeter(apartmentId);
    }
}