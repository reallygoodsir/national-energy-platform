package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.LoginResponse;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthMapperTest {

    private final AuthMapper authMapper = new AuthMapper();

    @Test
    void toSignupResponse_mapsIdFullNameAndEmail() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        SignupResponse response = authMapper.toSignupResponse(user);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFullName()).isEqualTo("Test User");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void toLoginResponse_mapsIdFullNameAndEmail() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        LoginResponse response = authMapper.toLoginResponse(user);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFullName()).isEqualTo("Test User");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
    }
}