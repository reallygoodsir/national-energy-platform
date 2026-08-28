package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.mapper.RoleMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock private RoleRepository roleRepository;
    @Mock private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getAllRoles_returnsMappedRoles_whenRolesExist() {

        RoleEntity adminRole = new RoleEntity();
        adminRole.setId(1L);
        adminRole.setName("ADMIN");

        List<RoleEntity> entities = List.of(adminRole);
        List<RoleResponse> expectedResponses = List.of(new RoleResponse(1L, "ADMIN", null));

        when(roleRepository.findAll()).thenReturn(entities);
        when(roleMapper.toRolesResponse(entities)).thenReturn(expectedResponses);

        List<RoleResponse> result = roleService.getAllRoles();

        assertThat(result).isEqualTo(expectedResponses);
    }

    @Test
    void getAllRoles_returnsEmptyList_whenNoRolesExist() {

        when(roleRepository.findAll()).thenReturn(List.of());
        when(roleMapper.toRolesResponse(List.of())).thenReturn(List.of());

        List<RoleResponse> result = roleService.getAllRoles();

        assertThat(result).isEmpty();
    }
}