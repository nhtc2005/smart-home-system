package com.group26.smart_home_system.dto.auth;

import com.group26.smart_home_system.enums.Role;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

  private Long id;
  private String lastName;
  private String firstName;
  private String email;
  private String phoneNumber;
  private Role role;
  private Instant createdAt;

}
