package com.group26.smart_home_system.dto.auth;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

  private String token;
  private Instant expiresAt;
  private UserResponse user;

}
