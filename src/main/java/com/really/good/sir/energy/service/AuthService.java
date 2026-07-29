package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.request.LoginRequest;
import com.really.good.sir.energy.dto.request.SignupRequest;
import com.really.good.sir.energy.dto.response.LoginResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    public AuthService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final AuthMapper authMapper,
                       final UserMapper userMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    public SignupResponse signup(final SignupRequest request) {

        LOGGER.info("Signup attempt for email={}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            LOGGER.warn("Signup rejected, email already exists: {}", request.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            LOGGER.warn("Signup rejected, phone number already exists: {}", request.getPhoneNumber());
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        final UserEntity userEntity = userMapper.toEntity(request);

        final RoleEntity consumerRole = roleRepository.findByName("CONSUMER")
                .orElseThrow(() -> {
                    LOGGER.error("CONSUMER role not found in database — check role seed data");
                    return new RuntimeException("CONSUMER role not found");
                });

        userEntity.getRoles().add(consumerRole);

        final UserEntity savedUserEntity = userRepository.save(userEntity);

        LOGGER.info("Signup successful, new userId={}", savedUserEntity.getId());

        return authMapper.toSignupResponse(savedUserEntity);
    }

    public LoginResponse login(final LoginRequest request, final HttpServletRequest httpRequest) {

        LOGGER.info("Login attempt for email={}", request.getEmail());

        final UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    LOGGER.warn("Login failed, no account for email={}", request.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });

        if (!userEntity.getPassword().equals(request.getPassword())) {
            LOGGER.warn("Login failed, incorrect password for email={}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        final var authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        null,
                        authorities
                );

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        httpRequest.getSession(true)
                .setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        context
                );

        LOGGER.info("Login successful for email={}", request.getEmail());

        return authMapper.toLoginResponse(userEntity);
    }
}