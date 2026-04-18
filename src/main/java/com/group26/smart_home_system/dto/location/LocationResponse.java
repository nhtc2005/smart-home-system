package com.group26.smart_home_system.dto.location;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponse {

  private Long id;
  private Long userId;
  private String name;
  private Instant createdAt;

}
