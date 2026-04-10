package com.group26.smart_home_system.dto.auth;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

  private String token;
  private LocalDateTime expiresAt;
  private UserResponse user;

}
