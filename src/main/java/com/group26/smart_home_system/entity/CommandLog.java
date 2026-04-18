package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.enums.CommandSource;
import com.group26.smart_home_system.enums.CommandStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "command_logs")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CommandLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CommandSource source;

  @Column(nullable = false)
  private String command;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CommandStatus status;

  @Column(nullable = false)
  @CreationTimestamp
  private Instant timestamp;

  @ManyToOne
  @JoinColumn(name = "actuator_id", nullable = false)
  private Actuator actuator;

}
