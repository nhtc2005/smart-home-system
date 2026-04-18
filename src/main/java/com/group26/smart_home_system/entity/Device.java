package com.group26.smart_home_system.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "devices")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "device_code", nullable = false, unique = true)
  private String deviceCode;

  @Column(nullable = false)
  private String name;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "location_id")
  private Location location;

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Sensor> sensors = new HashSet<>();

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Actuator> actuators = new HashSet<>();

}
