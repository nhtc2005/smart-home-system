package com.group26.smart_home_system.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "{auth.last-name.required}")
  @Size(max = 50, message = "{auth.last-name.size}")
  private String lastName;

  @NotBlank(message = "{auth.first-name.required}")
  @Size(max = 50, message = "{auth.first-name.size}")
  private String firstName;

  @NotBlank(message = "{auth.email.required}")
  @Email(message = "{auth.email.invalid}")
  private String email;

  @NotBlank(message = "{auth.phone.required}")
  @Pattern(regexp = "^[0-9]{10}$", message = "{auth.phone.invalid}")
  private String phoneNumber;

  @NotBlank(message = "{auth.password.required}")
  @Size(min = 6, max = 100, message = "{auth.password.size}")
  private String password;
}
