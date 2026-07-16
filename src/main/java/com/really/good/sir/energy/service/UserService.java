package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.ApartmentResponse;
import com.really.good.sir.energy.dto.response.RoleResponse;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.dto.response.UserResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.RoleAlreadyAssignedException;
import com.really.good.sir.energy.exception.RoleNotAssignedException;
import com.really.good.sir.energy.exception.RoleNotFoundException;
import com.really.good.sir.energy.exception.UserNotFoundException;
import com.really.good.sir.energy.mapper.ApartmentMapper;
import com.really.good.sir.energy.mapper.RoleMapper;
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.RoleRepository;
import com.really.good.sir.energy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            RoleMapper roleMapper, ApartmentMapper apartmentMapper,
            ApartmentRepository apartmentRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.apartmentMapper = apartmentMapper;
        this.apartmentRepository = apartmentRepository;
    }

    public SearchUserResponse search(String searchValue) {

        log.info("Searching user by value={}", searchValue);

        UserEntity user = userRepository.findByEmail(searchValue)
                .or(() -> userRepository.findByPhoneNumber(searchValue))
                .orElseThrow(() -> {
                    log.warn("User search found no match for value={}", searchValue);
                    return new UserNotFoundException("User not found");
                });

        List<RoleResponse> assignedRoles = user.getRoles()
                .stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());

        List<RoleResponse> availableRoles = roleRepository.findAll()
                .stream()
                .filter(role -> !user.getRoles().contains(role))
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());

        return userMapper.toSearchUserResponse(
                user,
                assignedRoles,
                availableRoles
        );
    }

    public void assignRole(RoleRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (user.getRoles().contains(role)) {
            log.warn("Role already assigned, userId={}, roleId={}", request.getUserId(), request.getRoleId());
            throw new RoleAlreadyAssignedException("Role already assigned");
        }

        user.getRoles().add(role);
        userRepository.save(user);

        log.info("Role assigned, userId={}, roleId={}", request.getUserId(), request.getRoleId());
    }

    public void removeRole(RoleRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        if (!user.getRoles().contains(role)) {
            log.warn("Role not assigned, cannot remove, userId={}, roleId={}", request.getUserId(), request.getRoleId());
            throw new RoleNotAssignedException("Role is not assigned to user");
        }

        user.getRoles().remove(role);
        userRepository.save(user);

        log.info("Role removed, userId={}, roleId={}", request.getUserId(), request.getRoleId());
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<ApartmentResponse> getUserApartments(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<ApartmentEntity> apartments =
                apartmentRepository.findAllByUserId(user.getId());

        log.info("Loaded {} apartment(s) for userId={}", apartments.size(), userId);

        return apartments.stream()
                .map(apartmentMapper::toResponse)
                .toList();
    }

    public UserResponse getCurrentUser(String email) {
        UserEntity user = findByEmail(email);
        return userMapper.toUserResponse(user);
    }

    public List<ApartmentResponse> getCurrentUserApartmentsWithMeter(String email) {

        UserEntity user = findByEmail(email);

        List<ApartmentEntity> apartments =
                apartmentRepository.findAllByUserIdWithMeterAssigned(user.getId());

        log.info("Loaded {} metered apartment(s) for email={}", apartments.size(), email);

        return apartments.stream()
                .map(apartmentMapper::toResponse)
                .toList();
    }
}