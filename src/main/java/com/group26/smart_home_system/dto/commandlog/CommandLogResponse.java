package com.group26.smart_home_system.dto.commandlog;

import com.group26.smart_home_system.enums.CommandSource;
import com.group26.smart_home_system.enums.CommandStatus;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandLogResponse {

  private Long id;
  private Long actuatorId;
  private CommandSource source;
  private String command;
  private CommandStatus status;
  private Instant timestamp;

}
