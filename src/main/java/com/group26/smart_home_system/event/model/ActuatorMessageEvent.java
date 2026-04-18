package com.group26.smart_home_system.event.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActuatorMessageEvent {

  private Long deviceId;
  private Long actuatorId;
  private String message;
  private Instant timestamp;

}
