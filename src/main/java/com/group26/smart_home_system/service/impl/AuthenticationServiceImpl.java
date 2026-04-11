package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.auth.*;
import com.group26.smart_home_system.entity.InvalidatedToken;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.enums.Role;
import com.group26.smart_home_system.exception.UnauthorizedException;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;
import com.group26.smart_home_system.mapper.AuthenticationMapper;
import com.group26.smart_home_system.repository.InvalidatedTokenRepository;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.security.JwtTokenProvider;
import com.group26.smart_home_system.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final InvalidatedTokenRepository invalidatedTokenRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final CurrentUser currentUser;
  private final AuthenticationMapper authenticationMapper;

  @Value("${auth.login.failed}")
  private String loginFailed;
  @Value("${auth.logout.success}")
  private String logoutSuccess;
  @Value("${account.register.success}")
  private String registerSuccess;
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

    User newUser = authenticationMapper.registerRequestToUser(registerRequest);
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setRole(Role.USER);
    userRepository.save(newUser);

    return RegisterResponse.builder()
        .message(registerSuccess)
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
    LocalDateTime expiresAt = jwtTokenProvider.extractExpiration(token).toInstant()
        .atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
    UserResponse userResponse = authenticationMapper.userToUserResponse(user);
    return LoginResponse.builder().token(token).expiresAt(expiresAt).user(userResponse).build();
  }

  @Override
  public LogoutResponse logout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtAuthenticationToken jwtAUthenticationToken = (JwtAuthenticationToken) authentication;
    Jwt token = jwtAUthenticationToken.getToken();
    LocalDateTime expiresAt = LocalDateTime.ofInstant(token.getExpiresAt(), ZoneId.systemDefault());

    InvalidatedToken invalidatedToken = new InvalidatedToken();
    invalidatedToken.setJti(token.getId());
    invalidatedToken.setExpiredAt(expiresAt);
    invalidatedTokenRepository.save(invalidatedToken);

    return LogoutResponse.builder().message(logoutSuccess).build();
  }

  @Override
  public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String newToken = jwtTokenProvider.refreshToken(refreshTokenRequest.getToken());
    LocalDateTime expiresAt = jwtTokenProvider.extractExpiration(newToken).toInstant()
        .atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
    return RefreshTokenResponse.builder().token(newToken).expiresAt(expiresAt).build();
  }

  @Override
  public UserResponse getInfo() throws UnauthorizedException {
    Long userId = currentUser.getUserId();

    System.out.println(userId);

    User user = userRepository.findById(userId).orElseThrow(() -> new UnauthorizedException(userNotFound));
    return authenticationMapper.userToUserResponse(user);
  }

}
