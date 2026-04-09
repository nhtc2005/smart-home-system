package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.user.CreateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;

public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest) throws UserAlreadyExistsException;

}
