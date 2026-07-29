package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.response.LoginResponse;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthMapper {
    public SignupResponse toSignupResponse(final UserEntity userEntity) {
        return new SignupResponse(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail()
        );
    }

    public LoginResponse toLoginResponse(final UserEntity userEntity) {
        return new LoginResponse(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail()
        );
    }
}
