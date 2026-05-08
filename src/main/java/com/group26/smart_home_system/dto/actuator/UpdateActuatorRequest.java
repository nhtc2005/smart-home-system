package com.group26.smart_home_system.dto.actuator;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActuatorRequest {

  @Positive(message = "{actuator.device-id.invalid}")
  private Long deviceId;

  @NotBlank(message = "{actuator.name.required}")
  @Size(min = 2, max = 100, message = "{actuator.name.size}")
  private String name;
}
