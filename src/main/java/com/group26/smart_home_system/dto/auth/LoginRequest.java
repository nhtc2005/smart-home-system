package com.group26.smart_home_system.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "{auth.email.required}")
  @Email(message = "{auth.email.invalid}")
  private String email;

  @NotBlank(message = "{auth.password.required}")
  @Size(min = 6, max = 100, message = "{auth.password.size}")
  private String password;
}
