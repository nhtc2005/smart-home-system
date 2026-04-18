package com.group26.smart_home_system.dto.sensor;

import com.group26.smart_home_system.enums.SensorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorFilterRequest {

  private SensorType type;
  private Long deviceId;
  private String keyword;

}
