package com.group26.smart_home_system.dto.sensordata;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDataResponse {

  private Long sensorId;
  private Double value;
  private Instant timestamp;

}
