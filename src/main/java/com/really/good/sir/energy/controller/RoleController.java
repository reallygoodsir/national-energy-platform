package com.really.good.sir.energy.controller;

import com.really.good.sir.energy.dto.response.RoleDto;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public RoleController(RoleRepository roleRepository, UserMapper userMapper) {
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(userMapper::toRoleDto)
                .collect(Collectors.toList());
    }
}