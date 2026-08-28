package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.UserLocationRequest;
import com.really.good.sir.energy.service.UserLocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserLocationControllerTest {

    @Mock private UserLocationService userLocationService;

    @InjectMocks
    private UserLocationController userLocationController;

    @Test
    void assignLocation_delegatesToServiceWithUserIdAndRequest() {

        UserLocationRequest request = new UserLocationRequest();
        request.setCity("Lviv");
        request.setStreet("Main St");
        request.setBuildingNumber("12");
        request.setApartmentNumber("4");

        userLocationController.assignLocation(1L, request);

        verify(userLocationService).assignLocation(1L, request);
    }
}