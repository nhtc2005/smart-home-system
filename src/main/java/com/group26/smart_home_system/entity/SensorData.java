package com.group26.smart_home_system.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "sensor_data")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class SensorData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Double value;

  @Column(nullable = false)
  private Instant timestamp;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sensor_id", nullable = false)
  private Sensor sensor;

}
