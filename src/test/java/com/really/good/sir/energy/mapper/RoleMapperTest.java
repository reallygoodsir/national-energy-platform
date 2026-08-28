package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleMapperTest {

    private final RoleMapper roleMapper = new RoleMapper();

    @Test
    void toRoleResponse_mapsIdNameAndDescription() {

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");
        role.setDescription("Administrator role");

        RoleResponse response = roleMapper.toRoleResponse(role);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("ADMIN");
        assertThat(response.getDescription()).isEqualTo("Administrator role");
    }

    @Test
    void toRolesResponse_mapsEachEntityInList() {

        RoleEntity adminRole = new RoleEntity();
        adminRole.setId(1L);
        adminRole.setName("ADMIN");

        RoleEntity consumerRole = new RoleEntity();
        consumerRole.setId(2L);
        consumerRole.setName("CONSUMER");

        List<RoleResponse> responses = roleMapper.toRolesResponse(List.of(adminRole, consumerRole));

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("ADMIN");
        assertThat(responses.get(1).getName()).isEqualTo("CONSUMER");
    }
}