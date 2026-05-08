package com.group26.smart_home_system.dto.sensor;

import com.group26.smart_home_system.enums.SensorType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorFilterRequest {

  private SensorType type;

  @Positive(message = "{sensor.device-id.invalid}")
  private Long deviceId;

  @Size(max = 100, message = "{sensor.keyword.size}")
  private String keyword;
}
