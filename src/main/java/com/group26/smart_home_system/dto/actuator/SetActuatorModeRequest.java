package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorMode;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SetActuatorModeRequest {

  @NotNull(message = "{actuator.mode.required}")
  private ActuatorMode mode;
}
