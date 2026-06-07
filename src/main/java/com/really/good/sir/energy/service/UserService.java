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
import com.really.good.sir.energy.mapper.UserMapper;
import com.really.good.sir.energy.repository.RoleRepository;
import com.really.good.sir.energy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    public SignupResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        UserEntity userEntity = userMapper.toEntity(request);

        RoleEntity consumerRole = roleRepository.findByName("CONSUMER")
                .orElseThrow(() ->
                        new RuntimeException("CONSUMER role not found"));

        userEntity.getRoles().add(consumerRole);

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.toSignupResponse(savedUserEntity);
    }

    public LoginResponse login(LoginRequest request) {

        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!userEntity.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return userMapper.toLoginResponse(userEntity);
    }
}