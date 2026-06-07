package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.AssignRoleRequest;
import com.really.good.sir.energy.dto.response.RoleDto;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.RoleAlreadyAssignedException;
import com.really.good.sir.energy.exception.UserNotFoundException;
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import com.really.good.sir.energy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public RoleManagementService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public SearchUserResponse searchUser(String searchValue) {

        UserEntity user = userRepository.findByEmail(searchValue)
                .or(() -> userRepository.findByPhoneNumber(searchValue))
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<RoleDto> assignedRoles = user.getRoles()
                .stream()
                .map(userMapper::toRoleDto)
                .collect(Collectors.toList());

        List<RoleDto> availableRoles = roleRepository.findAll()
                .stream()
                .filter(role -> !user.getRoles().contains(role))
                .map(userMapper::toRoleDto)
                .collect(Collectors.toList());

        return userMapper.toSearchUserResponse(
                user,
                assignedRoles,
                availableRoles
        );
    }

    public void assignRole(AssignRoleRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found"));

        if (user.getRoles().contains(role)) {
            throw new RoleAlreadyAssignedException(
                    "Role already assigned");
        }

        user.getRoles().add(role);

        userRepository.save(user);
    }
}