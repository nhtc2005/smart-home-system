package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.auth.*;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.enums.Role;
import com.group26.smart_home_system.exception.UnauthorizedException;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.mapper.AuthenticationMapper;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.security.JwtTokenProvider;
import com.group26.smart_home_system.security.TokenValidationService;
import com.group26.smart_home_system.service.AuthenticationService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final CurrentUser currentUser;
  private final UserRepository userRepository;
  private final TokenValidationService tokenValidationService;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationMapper authenticationMapper;

  @Value("${auth.login.failed}")
  private String loginFailed;

  @Value("${account.email.exists}")
  private String emailExists;

  @Value("${user.not.found}")
  private String userNotFound;

  @Override
  public RegisterResponse register(RegisterRequest registerRequest)
      throws UserAlreadyExistsException {
    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      throw new UserAlreadyExistsException(emailExists);
    }

    User newUser = authenticationMapper.toEntity(registerRequest);
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setRole(Role.USER);
    userRepository.save(newUser);

    return RegisterResponse.builder()
        .id(newUser.getId())
        .build();
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new BadCredentialsException(loginFailed));

    boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
    if (!passwordMatch) {
      throw new BadCredentialsException(loginFailed);
    }

    String token = jwtTokenProvider.generateToken(user.getId(), user.getRole().name());
    Instant expiresAt = jwtTokenProvider.extractExpiration(token).toInstant();
    UserResponse userResponse = authenticationMapper.toResponse(user);
    return LoginResponse.builder().token(token).expiresAt(expiresAt).user(userResponse).build();
  }

  @Override
  public void logout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtAuthenticationToken jwtAUthenticationToken = (JwtAuthenticationToken) authentication;
    Jwt token = jwtAUthenticationToken.getToken();
    Instant expiresAt = token.getExpiresAt();
    tokenValidationService.blacklist(token.getId(), expiresAt);
  }

  @Override
  public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String newToken = jwtTokenProvider.refreshToken(refreshTokenRequest.getToken());
    Instant expiresAt = jwtTokenProvider.extractExpiration(newToken).toInstant();
    return RefreshTokenResponse.builder().token(newToken).expiresAt(expiresAt).build();
  }

  @Override
  public UserResponse getInfo() throws UnauthorizedException {
    Long userId = currentUser.getUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UnauthorizedException(userNotFound));
    return authenticationMapper.toResponse(user);
  }

}
