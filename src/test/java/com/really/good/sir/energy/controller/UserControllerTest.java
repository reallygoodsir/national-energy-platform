package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.UserResponse;
import com.really.good.sir.energy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock private UserService userService;
    @Mock private Authentication authentication;

    @InjectMocks
    private UserController userController;

    @Test
    void search_delegatesToServiceWithValue() {

        SearchUserResponse expected = new SearchUserResponse(1L, "Test User", List.of(), List.of());
        when(userService.search("owner@example.com")).thenReturn(expected);

        SearchUserResponse response = userController.search("owner@example.com");

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void assignRole_delegatesToServiceWithRequest() {

        RoleRequest request = new RoleRequest();
        request.setUserId(1L);
        request.setRoleId(2L);

        userController.assignRole(request);

        verify(userService).assignRole(request);
    }

    @Test
    void removeRole_delegatesToServiceWithRequest() {

        RoleRequest request = new RoleRequest();
        request.setUserId(1L);
        request.setRoleId(2L);

        userController.removeRole(request);

        verify(userService).removeRole(request);
    }

    @Test
    void getCurrentUser_delegatesToServiceWithAuthenticatedEmail() {

        when(authentication.getName()).thenReturn("owner@example.com");

        UserResponse expected = new UserResponse(1L, "owner@example.com", List.of("CONSUMER"));
        when(userService.getCurrentUser("owner@example.com")).thenReturn(expected);

        UserResponse response = userController.getCurrentUser(authentication);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void getUserApartments_delegatesToServiceWithUserId() {

        List<ApartmentResponse> expected = List.of(new ApartmentResponse(1L, "Main St", "12", "4"));
        when(userService.getUserApartments(1L)).thenReturn(expected);

        List<ApartmentResponse> response = userController.getUserApartments(1L);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void getCurrentUserApartments_delegatesToServiceWithAuthenticatedEmail() {

        when(authentication.getName()).thenReturn("owner@example.com");

        List<ApartmentResponse> expected = List.of(new ApartmentResponse(1L, "Main St", "12", "4"));
        when(userService.getCurrentUserApartmentsWithMeter("owner@example.com")).thenReturn(expected);

        List<ApartmentResponse> response = userController.getCurrentUserApartments(authentication);

        assertThat(response).isEqualTo(expected);
    }
}