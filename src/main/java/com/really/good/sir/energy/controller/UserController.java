package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.AssignRoleRequest;
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

    @PostMapping("/assign")
    public void assignRole(
            @Valid
            @RequestBody AssignRoleRequest request) {

        userService.assignRole(request);
    }
}