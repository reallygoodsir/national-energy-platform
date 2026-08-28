package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.RoleRequest;
import com.really.good.sir.energy.dto.response.SearchUserResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.RoleAlreadyAssignedException;
import com.really.good.sir.energy.exception.UserNotFoundException;
import com.really.good.sir.energy.mapper.ApartmentMapper;
import com.really.good.sir.energy.mapper.RoleMapper;
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.ApartmentRepository;
import com.really.good.sir.energy.repository.RoleRepository;
import com.really.good.sir.energy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private ApartmentMapper apartmentMapper;

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private RoleEntity adminRole;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRoles(new HashSet<>());

        adminRole = new RoleEntity();
        adminRole.setId(2L);
        adminRole.setName("ADMIN");
    }

    @Test
    void search_returnsUser_whenFoundByEmail() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(roleRepository.findAll()).thenReturn(java.util.List.of());
        when(userMapper.toSearchUserResponse(any(), any(), any()))
                .thenReturn(new SearchUserResponse(1L, "Test User", java.util.List.of(), java.util.List.of()));

        SearchUserResponse response = userService.search("test@example.com");

        assertThat(response.getUserId()).isEqualTo(1L);
        verify(userRepository, never()).findByPhoneNumber(any());
    }

    @Test
    void search_throwsUserNotFoundException_whenNoMatch() {

        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber("missing@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.search("missing@example.com"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void assignRole_addsRole_whenNotAlreadyAssigned() {

        RoleRequest request = new RoleRequest();
        request.setUserId(1L);
        request.setRoleId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(adminRole));

        userService.assignRole(request);

        assertThat(user.getRoles()).contains(adminRole);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void assignRole_throwsRoleAlreadyAssignedException_whenRoleAlreadyPresent() {

        user.setRoles(new HashSet<>(Set.of(adminRole)));

        RoleRequest request = new RoleRequest();
        request.setUserId(1L);
        request.setRoleId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(adminRole));

        assertThatThrownBy(() -> userService.assignRole(request))
                .isInstanceOf(RoleAlreadyAssignedException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void findByEmail_throwsUserNotFoundException_whenUserDoesNotExist() {

        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByEmail("ghost@example.com"))
                .isInstanceOf(UserNotFoundException.class);
    }
}