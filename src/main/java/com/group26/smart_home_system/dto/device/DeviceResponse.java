package com.group26.smart_home_system.dto.device;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {

  private Long id;
  private String locationName;
  private String deviceCode;
  private String name;
  private Instant createdAt;
  private Instant lastSeen;

}
