package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.ElectricMeterRequest;
import com.really.good.sir.energy.dto.response.ElectricMeterResponse;
import com.really.good.sir.energy.dto.response.ElectricMeterTypeResponse;
import com.really.good.sir.energy.service.ElectricMeterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/electric-meters")
public class ElectricMeterController {

    private final ElectricMeterService electricMeterService;

    public ElectricMeterController(ElectricMeterService electricMeterService) {
        this.electricMeterService = electricMeterService;
    }

    @PostMapping
    public ElectricMeterResponse create(@Valid @RequestBody ElectricMeterRequest request) {
        return electricMeterService.create(request);
    }

    @GetMapping("/types")
    public List<ElectricMeterTypeResponse> getTypes() {
        return electricMeterService.getAllTypes();
    }
}