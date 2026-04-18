package com.group26.smart_home_system.dto.notification;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

  private Long id;
  private Integer userId;
  private Integer deviceId;
  private String message;
  private Instant timestamp;

}
