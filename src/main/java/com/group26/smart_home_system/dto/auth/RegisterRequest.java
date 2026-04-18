package com.group26.smart_home_system.dto.auth;

import lombok.*;

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
