package com.group26.smart_home_system.dto.mqtt;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedFeed {

  private Long deviceId;
  private String type;
  private Long index;

}
