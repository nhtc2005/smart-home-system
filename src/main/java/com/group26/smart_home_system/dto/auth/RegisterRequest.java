package com.group26.smart_home_system.dto.auth;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  private String lastName;
  private String firstName;
  private String email;
  private String phoneNumber;
  private String password;

}
