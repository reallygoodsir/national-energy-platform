package com.really.good.sir.energy.mapper;

import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
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

    public SearchUserResponse toSearchUserResponse(
            UserEntity user,
            List<RoleResponse> assignedRoles,
            List<RoleResponse> availableRoles) {

        return new SearchUserResponse(
                user.getId(),
                user.getFullName(),
                assignedRoles,
                availableRoles
        );
    }
}