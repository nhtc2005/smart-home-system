package com.group26.smart_home_system.event.model;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDataEvent {

  private Long deviceId;
  private Long sensorId;
  private Double value;
  private Instant timestamp;

}
