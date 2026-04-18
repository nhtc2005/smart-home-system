package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorMode;
import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ActuatorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActuatorResponse {

  private Long id;
  private Long deviceId;
  private String name;
  private ActuatorType type;
  private ActuatorState state;
  private ActuatorMode mode;

}
