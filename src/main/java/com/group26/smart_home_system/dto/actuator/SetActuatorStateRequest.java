package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorState;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SetActuatorStateRequest {

  private ActuatorState state;

}
