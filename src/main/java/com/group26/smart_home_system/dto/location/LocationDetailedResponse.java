package com.group26.smart_home_system.dto.location;

import com.group26.smart_home_system.dto.device.DeviceResponse;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDetailedResponse {

  private Long id;
  private Long userId;
  private String name;
  private Instant createdAt;
  private List<DeviceResponse> devices;

}
