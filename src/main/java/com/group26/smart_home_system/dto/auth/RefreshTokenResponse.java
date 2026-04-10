package com.group26.smart_home_system.dto.auth;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

  private String token;
  LocalDateTime expiresAt;

}
