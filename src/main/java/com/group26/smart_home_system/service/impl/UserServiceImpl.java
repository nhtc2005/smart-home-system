package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.user.CreateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.mapper.UserMapper;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${user.already.exists}")
    private String userAlreadyExistsMessage;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new UserAlreadyExistsException(userAlreadyExistsMessage);
        }

        User newUser = userMapper.createUserRequestToUser(createUserRequest);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        return userMapper.userToCreateUserResponse(userRepository.save(newUser));
    }

}
