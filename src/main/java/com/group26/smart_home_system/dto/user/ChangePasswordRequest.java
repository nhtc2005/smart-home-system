package com.group26.smart_home_system.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

  @NotBlank(message = "{user.old-password.required}")
  private String oldPassword;

  @NotBlank(message = "{user.new-password.required}")
  @Size(min = 6, max = 100, message = "{user.new-password.size}")
  private String newPassword;
}
