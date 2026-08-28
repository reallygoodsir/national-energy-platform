package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.UserResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void toEntity_mapsRequestFieldsAndSetsCreatedAt() {

        SignupRequest request = new SignupRequest();
        request.setFullName("Test User");
        request.setEmail("test@example.com");
        request.setPhoneNumber("123456");
        request.setPassword("rawPassword");

        UserEntity user = userMapper.toEntity(request);

        assertThat(user.getFullName()).isEqualTo("Test User");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPhoneNumber()).isEqualTo("123456");
        assertThat(user.getPassword()).isEqualTo("rawPassword");
        assertThat(user.getCreatedAt()).isNotNull();
    }

    @Test
    void toSearchUserResponse_mapsUserAndBothRoleLists() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFullName("Test User");

        List<RoleResponse> assignedRoles = List.of(new RoleResponse(1L, "ADMIN", null));
        List<RoleResponse> availableRoles = List.of(new RoleResponse(2L, "CONSUMER", null));

        SearchUserResponse response = userMapper.toSearchUserResponse(user, assignedRoles, availableRoles);

        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getFullName()).isEqualTo("Test User");
        assertThat(response.getAssignedRoles()).isEqualTo(assignedRoles);
        assertThat(response.getAvailableRoles()).isEqualTo(availableRoles);
    }

    @Test
    void toUserResponse_mapsIdEmailAndRoleNames() {

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ADMIN");

        RoleEntity consumerRole = new RoleEntity();
        consumerRole.setName("CONSUMER");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRoles(new HashSet<>(Set.of(adminRole, consumerRole)));

        UserResponse response = userMapper.toUserResponse(user);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getRoles()).containsExactlyInAnyOrder("ADMIN", "CONSUMER");
    }
}