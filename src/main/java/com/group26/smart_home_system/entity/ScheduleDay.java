package com.group26.smart_home_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule_days",
    uniqueConstraints = @UniqueConstraint(columnNames = {"schedule_id", "day_of_week"}))
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ScheduleDay {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "day_of_week", nullable = false)
  private Integer dayOfWeek;

  @ManyToOne
  @JoinColumn(name = "schedule_id", nullable = false)
  private Schedule schedule;

}
