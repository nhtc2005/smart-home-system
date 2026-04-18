package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateActuatorRequest {

  private Long deviceId;
  private String name;
  private ActuatorType type;

}
