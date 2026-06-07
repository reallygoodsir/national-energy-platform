package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.request.AssignRoleRequest;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.service.RoleManagementService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleManagementController {

    private final RoleManagementService roleManagementService;

    public RoleManagementController(
            RoleManagementService roleManagementService) {

        this.roleManagementService = roleManagementService;
    }

    @GetMapping("/users/search")
    public SearchUserResponse searchUser(
            @RequestParam String value) {

        return roleManagementService.searchUser(value);
    }

    @PostMapping("/assign")
    public void assignRole(
            @Valid
            @RequestBody AssignRoleRequest request) {

        roleManagementService.assignRole(request);
    }
}