package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.LoginRequest;
import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.LoginResponse;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody final SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody final LoginRequest request,
            final HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }

    @PostMapping("/logout")
    public void logout(final HttpServletRequest request, final HttpServletResponse response) {
        final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}