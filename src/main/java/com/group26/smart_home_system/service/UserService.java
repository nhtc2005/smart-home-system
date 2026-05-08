package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.user.ChangePasswordRequest;
import com.group26.smart_home_system.dto.user.UpdateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.exception.InvalidPasswordException;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.exception.UserNotFoundException;

public interface UserService {

  UserResponse updateUser(UpdateUserRequest updateUserRequest)
      throws UserNotFoundException, UserAlreadyExistsException;

  void changePassword(ChangePasswordRequest changePasswordRequest)
      throws UserNotFoundException, InvalidPasswordException;
}
