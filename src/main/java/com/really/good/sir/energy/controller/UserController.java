package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}