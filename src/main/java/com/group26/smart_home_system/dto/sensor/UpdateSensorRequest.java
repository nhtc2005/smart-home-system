package com.group26.smart_home_system.dto.sensor;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSensorRequest {

  @Positive(message = "{sensor.device-id.invalid}")
  private Long deviceId;

  @NotBlank(message = "{sensor.name.required}")
  @Size(min = 2, max = 100, message = "{sensor.name.size}")
  private String name;
}
