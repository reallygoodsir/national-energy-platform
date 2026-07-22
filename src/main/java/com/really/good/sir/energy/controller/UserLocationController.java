package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.service.UserLocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class UserLocationController {

    private final UserLocationService userLocationService;

    public UserLocationController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @PostMapping("/{userId}")
    public void assignLocation(
            @PathVariable Long userId,
            @Valid @RequestBody UserLocationRequest request
    ) {
        userLocationService.assignLocation(userId, request);
    }
}