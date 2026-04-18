package com.group26.smart_home_system.dto.websocket;

import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ActuatorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActuatorStatePayload {

  private Long deviceId;
  private Long actuatorId;
  private String name;
  private ActuatorType actuatorType;
  private ActuatorState actuatorState;

}
