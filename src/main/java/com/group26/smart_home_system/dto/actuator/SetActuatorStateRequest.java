package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorState;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SetActuatorStateRequest {

  @NotNull(message = "{actuator.state.required}")
  private ActuatorState state;
}
