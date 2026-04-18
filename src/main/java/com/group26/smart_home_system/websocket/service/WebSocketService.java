package com.group26.smart_home_system.websocket.service;

import com.group26.smart_home_system.dto.websocket.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public <T> void pushToUser(Long userId, WebSocketMessage<T> webSocketMessage) {
    try {
      simpMessagingTemplate.convertAndSend(
          "/topic/users/" + userId,
          webSocketMessage
      );

      log.debug("Sent WebSocket message to userId={}", userId);

    } catch (Exception exception) {
      log.error("Failed to send WebSocket message to userId={}", userId, exception);
    }
  }

}
