package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.auth.*;
import com.group26.smart_home_system.exception.UnauthorizedException;
import com.group26.smart_home_system.exception.UserAlreadyExistsException;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest) throws UserAlreadyExistsException;

    LoginResponse login(LoginRequest loginRequest);

    LogoutResponse logout();

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    UserResponse getInfo() throws UnauthorizedException;

}
