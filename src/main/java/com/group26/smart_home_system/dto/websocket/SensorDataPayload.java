package com.group26.smart_home_system.dto.websocket;

import com.group26.smart_home_system.enums.SensorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDataPayload {

  private Long deviceId;
  private Long sensorId;
  private String name;
  private SensorType sensorType;
  private Double value;
  private String unit;

}
