package com.group26.smart_home_system.dto.websocket;

import com.group26.smart_home_system.enums.WebSocketEventType;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketMessage<T> {

  private WebSocketEventType type;
  private Instant timestamp;
  private T payload;

}
