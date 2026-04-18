package com.group26.smart_home_system.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException authenticationException)
      throws IOException {

    Throwable cause = authenticationException.getCause();

    String message;

    if (cause instanceof JwtException jwtException) {
      message = jwtException.getMessage();
    } else {
      message = authenticationException.getMessage();
    }

    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    Map<String, Object> body = Map.of(
        "message", message,
        "error", "401 UNAUTHORIZED",
        "status", 401,
        "timestamp", Instant.now().toString()
    );

    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(body));
  }

}
