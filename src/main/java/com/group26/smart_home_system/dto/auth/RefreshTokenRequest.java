package com.group26.smart_home_system.dto.auth;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

  private String token;

}
