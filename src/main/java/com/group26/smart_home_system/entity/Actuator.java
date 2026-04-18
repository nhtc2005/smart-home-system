package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.enums.ActuatorMode;
import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ActuatorType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "actuators")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Actuator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ActuatorType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ActuatorState state;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ActuatorMode mode;

  @Column(name = "mqtt_topic")
  private String mqttTopic;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "device_id", nullable = false)
  private Device device;

}
