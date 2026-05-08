package com.group26.smart_home_system.dto.actuator;

import com.group26.smart_home_system.enums.ActuatorType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActuatorFilterRequest {

  @Positive(message = "{actuator.device-id.invalid}")
  private Long deviceId;

  private ActuatorType type;

  @Size(max = 100, message = "{actuator.keyword.size}")
  private String keyword;
}
