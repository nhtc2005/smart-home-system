package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.enums.Role;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private Instant createdAt;

  @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Location> locations = new ArrayList<>();

}
