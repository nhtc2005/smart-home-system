package com.group26.smart_home_system.dto.websocket;

import com.group26.smart_home_system.enums.SensorType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorData {

  private Long deviceId;
  private Long sensorId;
  private SensorType sensorType;
  private String name;
  private Double value;
  private String unit;

}
