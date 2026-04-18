package com.group26.smart_home_system;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource("messages.properties")
@EnableScheduling
@RequiredArgsConstructor
public class SmartHomeSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeSystemApplication.class, args);
	}

}
