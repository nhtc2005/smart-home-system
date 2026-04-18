package com.group26.smart_home_system.dto.websocket;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketMessage<T> {

  private String type;
  private Long deviceId;
  private Long timestamp;
  private T payload;

}
