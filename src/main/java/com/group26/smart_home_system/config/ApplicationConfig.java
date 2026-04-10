package com.group26.smart_home_system.config;

import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.enums.Role;
import com.group26.smart_home_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final PasswordEncoder passwordEncoder;

  @Bean
  ApplicationRunner applicationRunner(UserRepository userRepository) {
    return args -> {
      if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
      }
    };
  }

}
