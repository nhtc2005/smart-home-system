package com.group26.smart_home_system.security;

import com.group26.smart_home_system.entity.InvalidatedToken;
import com.group26.smart_home_system.repository.InvalidatedTokenRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

  private final InvalidatedTokenRepository invalidatedTokenRepository;

  public boolean isBlacklisted(String jti) {
    return invalidatedTokenRepository.existsByJti(jti);
  }

  public void blacklist(String jti, Instant expirationTime) {
    InvalidatedToken invalidatedToken = new InvalidatedToken();
    invalidatedToken.setJti(jti);
    invalidatedToken.setExpiredAt(expirationTime);
    invalidatedTokenRepository.save(invalidatedToken);
  }

  @Scheduled(cron = "0 0 2 * * ?")
  @Transactional
  public void cleanupExpiredTokens() {
    invalidatedTokenRepository.deleteByExpiredAtBefore(Instant.now());
  }

}
