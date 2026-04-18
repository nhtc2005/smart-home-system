package com.group26.smart_home_system.config;

import com.group26.smart_home_system.websocket.interceptor.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
    stompEndpointRegistry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .addInterceptors(jwtHandshakeInterceptor)
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
    messageBrokerRegistry.enableSimpleBroker("/topic", "/queue");
    messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
    messageBrokerRegistry.setUserDestinationPrefix("/user");
  }

}
