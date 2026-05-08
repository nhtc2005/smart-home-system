package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.user.ChangePasswordRequest;
import com.group26.smart_home_system.dto.user.UpdateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.exception.InvalidPasswordException;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.mapper.UserMapper;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final CurrentUser currentUser;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Value("${user.not.found}")
  private String userNotFound;

  @Value("${user.email.exists}")
  private String emailExists;

  @Value("${user.phone_number.exists}")
  private String phoneNumberExists;

  @Value("${user.password.incorrect}")
  private String passwordIncorrect;

  @Override
  @Transactional
  public UserResponse updateUser(UpdateUserRequest updateUserRequest)
      throws UserNotFoundException, UserAlreadyExistsException {
    User user =
        userRepository
            .findById(currentUser.getUserId())
            .orElseThrow(() -> new UserNotFoundException(userNotFound));

    Long userId = user.getId();

    if (updateUserRequest.getEmail() != null
        && userRepository.existsByEmailAndIdNot(updateUserRequest.getEmail(), userId)) {
      throw new UserAlreadyExistsException(emailExists);
    }

    if (updateUserRequest.getPhoneNumber() != null
        && userRepository.existsByPhoneNumberAndIdNot(updateUserRequest.getPhoneNumber(), userId)) {
      throw new UserAlreadyExistsException(phoneNumberExists);
    }

    userMapper.updateUserFromRequest(updateUserRequest, user);

    return userMapper.toUserResponse(user);
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordRequest changePasswordRequest)
      throws UserNotFoundException, InvalidPasswordException {
    User user =
        userRepository
            .findById(currentUser.getUserId())
            .orElseThrow(() -> new UserNotFoundException(userNotFound));
    if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      throw new InvalidPasswordException(passwordIncorrect);
    }
    user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
  }
}
