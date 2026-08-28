package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.LoginRequest;
import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.LoginResponse;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private AuthService authService;
    @Mock private HttpServletRequest httpServletRequest;
    @Mock private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthController authController;

    @Test
    void signup_delegatesToServiceAndReturnsResponse() {

        SignupRequest request = new SignupRequest();
        SignupResponse expected = new SignupResponse(1L, "Test User", "test@example.com");

        when(authService.signup(request)).thenReturn(expected);

        SignupResponse response = authController.signup(request);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void login_delegatesToServiceWithRequestAndHttpRequest() {

        LoginRequest request = new LoginRequest();
        LoginResponse expected = new LoginResponse(1L, "Test User", "test@example.com");

        when(authService.login(request, httpServletRequest)).thenReturn(expected);

        LoginResponse response = authController.login(request, httpServletRequest);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void logout_doesNotThrow() {
        authController.logout(httpServletRequest, httpServletResponse);
    }
}