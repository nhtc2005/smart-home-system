package com.group26.smart_home_system.event.model;

import com.group26.smart_home_system.enums.ActuatorState;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActuatorStateEvent {

  private Long deviceId;
  private Long actuatorId;
  private ActuatorState actuatorState;
  private Instant timestamp;

}
