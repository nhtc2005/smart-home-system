package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.user.ChangePasswordRequest;
import com.group26.smart_home_system.dto.user.UpdateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  @PutMapping("/me")
  public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest request)
      throws UserNotFoundException, UserAlreadyExistsException {
    return ResponseEntity.ok(userService.updateUser(request));
  }

  @PutMapping("/me/change-password")
  public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request)
      throws UserNotFoundException {
    userService.changePassword(request);
    return ResponseEntity.ok().build();
  }
}
