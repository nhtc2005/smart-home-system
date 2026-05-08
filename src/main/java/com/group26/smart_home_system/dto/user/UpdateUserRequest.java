package com.group26.smart_home_system.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

  @NotBlank(message = "{user.last-name.required}")
  @Size(max = 50, message = "{user.last-name.size}")
  private String lastName;

  @NotBlank(message = "{user.first-name.required}")
  @Size(max = 50, message = "{user.first-name.size}")
  private String firstName;

  @NotBlank(message = "{user.email.required}")
  @Email(message = "{user.email.invalid}")
  private String email;

  @NotBlank(message = "{user.phone.required}")
  @Pattern(regexp = "^[0-9]{10}$", message = "{user.phone.invalid}")
  private String phoneNumber;
}
