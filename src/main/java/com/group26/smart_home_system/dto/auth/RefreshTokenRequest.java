package com.group26.smart_home_system.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

  @NotBlank(message = "{auth.refresh-token.required}")
  private String token;
}
