package com.autopilot.autopilot_agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutopilotAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutopilotAgentApplication.class, args);
	}

}
