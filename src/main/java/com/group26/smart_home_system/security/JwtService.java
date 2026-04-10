package com.group26.smart_home_system.security;

import java.util.Date;

public interface JwtService {

  String generateToken(Long userId, String role);

  String refreshToken(String token);

  Date extractExpiration(String token);

}
