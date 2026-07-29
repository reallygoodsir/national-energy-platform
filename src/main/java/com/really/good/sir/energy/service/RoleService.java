package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.mapper.RoleMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(final RoleRepository roleRepository, final RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleResponse> getAllRoles() {
        final List<RoleEntity> rolesEntities = roleRepository.findAll();
        return roleMapper.toRolesResponse(rolesEntities);
    }
}
