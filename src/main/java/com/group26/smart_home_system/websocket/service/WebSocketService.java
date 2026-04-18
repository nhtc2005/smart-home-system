package com.group26.smart_home_system.integration.websocket.service;

import com.group26.smart_home_system.dto.websocket.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public <T> void pushToUser(Long userId, WebSocketMessage<T> message) {
    simpMessagingTemplate.convertAndSend(
        "/topic/users/" + userId,
        message
    );
  }

}
