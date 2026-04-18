package com.group26.smart_home_system.dto.device;

import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDetailedResponse {

  private Long id;
  private String locationName;
  private String deviceCode;
  private String name;
  private Instant createdAt;
  private Instant lastSeen;
  private List<ActuatorResponse> actuators;
  private List<SensorResponse> sensors;

}
