package com.group26.smart_home_system.integration.websocket.interceptor;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final JwtDecoder jwtDecoder;

  @Override
  public boolean beforeHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes) {

    try {
      String query = request.getURI().getQuery();
      String token = null;

      if (query != null && query.contains("token=")) {
        token = query.split("token=")[1];
      }

      if (token != null) {
        Jwt jwt = jwtDecoder.decode(token);
        String userId = jwt.getSubject();
        System.out.println(userId);
        attributes.put("userId", userId);
      }

      return true;

    } catch (Exception e) {
      throw new RuntimeException("Invalid WS token", e);
    }
  }

  @Override
  public void afterHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Exception exception) {
  }

}
