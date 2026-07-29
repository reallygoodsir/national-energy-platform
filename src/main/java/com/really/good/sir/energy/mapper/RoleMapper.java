package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMapper {

    public RoleResponse toRoleResponse(final RoleEntity role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }

    public List<RoleResponse> toRolesResponse(final List<RoleEntity> entities) {
        return entities.stream()
                .map(this::toRoleResponse)
                .toList();
    }
}
