package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.LoginRequest;
import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.SignupResponse;
import com.really.good.sir.energy.entity.RoleEntity;
import com.really.good.sir.energy.entity.UserEntity;
import com.really.good.sir.energy.exception.EmailAlreadyExistsException;
import com.really.good.sir.energy.exception.InvalidCredentialsException;
import com.really.good.sir.energy.exception.PhoneNumberAlreadyExistsException;
import com.really.good.sir.energy.mapper.AuthMapper;
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import com.really.good.sir.energy.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private AuthMapper authMapper;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private HttpServletRequest httpServletRequest;
    @Mock private HttpSession httpSession;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;
    private UserEntity userEntity;
    private RoleEntity consumerRole;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("new@example.com");
        signupRequest.setPhoneNumber("123456");
        signupRequest.setPassword("rawPassword");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("new@example.com");
        userEntity.setPassword("rawPassword");
        userEntity.setRoles(new HashSet<>());

        consumerRole = new RoleEntity();
        consumerRole.setId(3L);
        consumerRole.setName("CONSUMER");
    }

    @Test
    void signup_throwsEmailAlreadyExistsException_whenEmailTaken() {

        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(signupRequest))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void signup_throwsPhoneNumberAlreadyExistsException_whenPhoneTaken() {

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.existsByPhoneNumber("123456")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(signupRequest))
                .isInstanceOf(PhoneNumberAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void signup_encodesPasswordBeforeSaving_whenValid() {

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.existsByPhoneNumber("123456")).thenReturn(false);
        when(userMapper.toEntity(signupRequest)).thenReturn(userEntity);
        when(passwordEncoder.encode("rawPassword")).thenReturn("hashedPassword");
        when(roleRepository.findByName("CONSUMER")).thenReturn(Optional.of(consumerRole));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(authMapper.toSignupResponse(userEntity)).thenReturn(new SignupResponse(1L, null, null));

        SignupResponse response = authService.signup(signupRequest);

        assertThat(userEntity.getPassword()).isEqualTo("hashedPassword");
        assertThat(userEntity.getRoles()).contains(consumerRole);
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void login_throwsInvalidCredentialsException_whenEmailNotFound() {

        LoginRequest request = new LoginRequest();
        request.setEmail("ghost@example.com");
        request.setPassword("anything");

        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request, httpServletRequest))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void login_throwsInvalidCredentialsException_whenPasswordDoesNotMatch() {

        LoginRequest request = new LoginRequest();
        request.setEmail("new@example.com");
        request.setPassword("wrongPassword");

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("wrongPassword", userEntity.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request, httpServletRequest))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(httpServletRequest, never()).getSession(true);
    }

    @Test
    void login_createsSession_whenCredentialsValid() {

        LoginRequest request = new LoginRequest();
        request.setEmail("new@example.com");
        request.setPassword("rawPassword");

        userEntity.getRoles().add(consumerRole);

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("rawPassword", userEntity.getPassword())).thenReturn(true);
        when(httpServletRequest.getSession(true)).thenReturn(httpSession);
        when(authMapper.toLoginResponse(userEntity)).thenReturn(null);

        authService.login(request, httpServletRequest);

        verify(httpSession).setAttribute(eq("SPRING_SECURITY_CONTEXT"), any());
    }
}