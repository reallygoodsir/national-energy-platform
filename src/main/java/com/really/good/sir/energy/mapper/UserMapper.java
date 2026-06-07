package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.LoginResponse;
import com.really.good.sir.energy.dto.response.RoleDto;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMapper {

    public UserEntity toEntity(SignupRequest request) {

        UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    public SignupResponse toSignupResponse(UserEntity userEntity) {

        return new SignupResponse(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail()
        );
    }

    public LoginResponse toLoginResponse(UserEntity userEntity) {

        return new LoginResponse(
                userEntity.getId(),
                userEntity.getFullName(),
                userEntity.getEmail()
        );
    }

    public RoleDto toRoleDto(RoleEntity role) {

        return new RoleDto(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }

    public SearchUserResponse toSearchUserResponse(
            UserEntity user,
            List<RoleDto> assignedRoles,
            List<RoleDto> availableRoles) {

        return new SearchUserResponse(
                user.getId(),
                user.getFullName(),
                assignedRoles,
                availableRoles
        );
    }
}