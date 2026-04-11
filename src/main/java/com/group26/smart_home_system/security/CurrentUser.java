package com.group26.smart_home_system.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

  public Long getUserId() {
    Jwt jwt = (Jwt) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return Long.parseLong(jwt.getSubject());
  }
}
