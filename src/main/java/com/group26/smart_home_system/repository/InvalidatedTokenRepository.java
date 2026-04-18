package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.InvalidatedToken;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Long> {

  boolean existsByJti(String jti);

  void deleteByExpiredAtBefore(Instant time);

}
