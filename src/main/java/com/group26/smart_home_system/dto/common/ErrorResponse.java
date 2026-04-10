package com.group26.smart_home_system.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  private String message;
  private HttpStatus error;
  private Integer status;
  private LocalDateTime timestamp;

}
