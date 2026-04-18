package com.group26.smart_home_system.websocket.interceptor;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final JwtDecoder jwtDecoder;

  @Value("${auth.token.invalid}")
  String invalidToken;

  @Override
  public boolean beforeHandshake(
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse,
      WebSocketHandler webSocketHandler,
      Map<String, Object> attributes) {

    log.info("WebSocket handshake request received: {}", serverHttpRequest.getURI());

    try {
      String query = serverHttpRequest.getURI().getQuery();
      String token = null;

      if (query != null) {
        String[] params = query.split("&");
        for (String param : params) {
          if (param.startsWith("token=")) {
            token = param.substring(6);
            break;
          }
        }
      }

      if (token == null) {
        log.warn("WebSocket handshake rejected: missing token");
        return false;
      }

      Jwt jwt = jwtDecoder.decode(token);
      String userId = jwt.getSubject();

      attributes.put("userId", userId);

      log.info("WebSocket handshake success: userId={}", userId);

      return true;

    } catch (Exception exception) {
      log.error("WebSocket handshake failed", exception);
      throw new RuntimeException(invalidToken);
    }
  }

  @Override
  public void afterHandshake(
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse,
      WebSocketHandler webSocketHandlerwsHandler,
      Exception exception) {

    if (exception != null) {
      log.error("WebSocket handshake error", exception);
    }
  }

}
