package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.UserResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email);
        List<String> roles = user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .toList();
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                roles
        );
    }

    @GetMapping("/{userId}/apartments")
    public List<ApartmentResponse> getUserApartments(@PathVariable Long userId) {
        return userService.getUserApartments(userId);
    }
}