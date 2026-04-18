package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.enums.SensorType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Sensor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SensorType type;

  @Column(name = "mqtt_topic")
  private String mqttTopic;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "device_id", nullable = false)
  private Device device;

  @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SensorData> data = new ArrayList<>();

}
