package com.group26.smart_home_system.dto.device;

import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceResponse {

  private Long id;
  private String name;
  private String deviceCode;
  private String locationName;
  private LocalDateTime createdAt;
  private LocalDateTime lastSeen;

  private List<ActuatorResponse> actuators;
  private List<SensorResponse> sensors;

}
