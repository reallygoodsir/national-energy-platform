package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.annotation.AdminOnly;
import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @AdminOnly
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }
}