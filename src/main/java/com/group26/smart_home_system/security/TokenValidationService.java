package com.group26.smart_home_system.security;

import java.time.LocalDateTime;

public interface TokenValidationService {

  boolean isBlacklisted(String jti);

  void blacklist(String jti, LocalDateTime expirationTime);

}
