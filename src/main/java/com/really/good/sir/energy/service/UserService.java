package com.really.good.sir.energy.service;

import com.really.good.sir.energy.constants.AppConstants;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    public UserService(
            final UserRepository userRepository,
            final RoleRepository roleRepository,
            final UserMapper userMapper,
            final RoleMapper roleMapper,
            final ApartmentMapper apartmentMapper,
            final ApartmentRepository apartmentRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.apartmentMapper = apartmentMapper;
        this.apartmentRepository = apartmentRepository;
    }

    public SearchUserResponse search(final String searchValue) {

        LOGGER.info("Searching user by value={}", searchValue);

        final UserEntity user = userRepository.findByEmail(searchValue)
                .or(() -> userRepository.findByPhoneNumber(searchValue))
                .orElseThrow(() -> {
                    LOGGER.warn("User search found no match for value={}", searchValue);
                    return new UserNotFoundException(AppConstants.USER_NOT_FOUND);
                });

        final List<RoleResponse> assignedRoles = user.getRoles()
                .stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());

        final List<RoleResponse> availableRoles = roleRepository.findAll()
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

    public void assignRole(final RoleRequest request) {

        final UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

        final RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (user.getRoles().contains(role)) {
            LOGGER.warn("Role already assigned, userId={}, roleId={}", request.getUserId(), request.getRoleId());
            throw new RoleAlreadyAssignedException("Role already assigned");
        }

        user.getRoles().add(role);
        userRepository.save(user);

        LOGGER.info("Role assigned, userId={}, roleId={}", request.getUserId(), request.getRoleId());
    }

    public void removeRole(final RoleRequest request) {

        final UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

        final RoleEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        if (!user.getRoles().contains(role)) {
            LOGGER.warn("Role not assigned, cannot remove, userId={}, roleId={}",
                    request.getUserId(), request.getRoleId());
            throw new RoleNotAssignedException("Role is not assigned to user");
        }

        user.getRoles().remove(role);
        userRepository.save(user);

        LOGGER.info("Role removed, userId={}, roleId={}", request.getUserId(), request.getRoleId());
    }

    public UserEntity findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
    }

    public List<ApartmentResponse> getUserApartments(final Long userId) {

        final UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

        final List<ApartmentEntity> apartments =
                apartmentRepository.findAllByUserId(user.getId());

        LOGGER.info("Loaded {} apartment(s) for userId={}", apartments.size(), userId);

        return apartments.stream()
                .map(apartmentMapper::toResponse)
                .toList();
    }

    public UserResponse getCurrentUser(final String email) {
        final UserEntity user = findByEmail(email);
        return userMapper.toUserResponse(user);
    }

    public List<ApartmentResponse> getCurrentUserApartmentsWithMeter(final String email) {

        final UserEntity user = findByEmail(email);

        final List<ApartmentEntity> apartments =
                apartmentRepository.findAllByUserIdWithMeterAssigned(user.getId());

        LOGGER.info("Loaded {} metered apartment(s) for email={}", apartments.size(), email);

        return apartments.stream()
                .map(apartmentMapper::toResponse)
                .toList();
    }
}