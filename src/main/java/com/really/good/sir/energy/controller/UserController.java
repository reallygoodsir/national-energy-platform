package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.UserResponse;
import com.really.good.sir.energy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public SearchUserResponse search(@RequestParam String value) {
        return userService.search(value);
    }

    @PostMapping("/role")
    public void assignRole(@Valid @RequestBody RoleRequest request) {
        userService.assignRole(request);
    }

    @DeleteMapping("/role")
    public void removeRole(@Valid @RequestBody RoleRequest request) {
        userService.removeRole(request);
    }

    @GetMapping("/current")
    public UserResponse getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }

    @GetMapping("/{userId}/apartments")
    public List<ApartmentResponse> getUserApartments(@PathVariable Long userId) {
        return userService.getUserApartments(userId);
    }

    @GetMapping("/current/apartments")
    public List<ApartmentResponse> getCurrentUserApartments(Authentication authentication) {
        return userService.getCurrentUserApartmentsWithMeter(authentication.getName());
    }
}