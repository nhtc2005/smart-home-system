package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ScheduleMode;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import lombok.*;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ScheduleMode mode;

  @Column(nullable = false)
  private LocalTime time;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ActuatorState action;

  @Column(name = "cron_expression", nullable = false)
  private String cronExpression;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "last_executed_at")
  private Instant lastExecutedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "actuator_id", nullable = false)
  private Actuator actuator;

  @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ScheduleDay> days = new ArrayList<>();

}
