package com.group26.smart_home_system.dto.sensor;

import com.group26.smart_home_system.enums.SensorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorResponse {

  private Long id;
  private Long deviceId;
  private String name;
  private SensorType type;

}
