package com.group26.smart_home_system.security.impl;

import com.group26.smart_home_system.entity.InvalidatedToken;
import com.group26.smart_home_system.repository.InvalidatedTokenRepository;
import com.group26.smart_home_system.security.TokenValidationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {

  private final InvalidatedTokenRepository invalidatedTokenRepository;

  @Override
  public boolean isBlacklisted(String jti) {
    return invalidatedTokenRepository.existsByJti(jti);
  }

  @Override
  public void blacklist(String jti, LocalDateTime expirationTime) {
    InvalidatedToken invalidatedToken = new InvalidatedToken();
    invalidatedToken.setJti(jti);
    invalidatedToken.setExpiredAt(expirationTime);
    invalidatedTokenRepository.save(invalidatedToken);
  }

}
