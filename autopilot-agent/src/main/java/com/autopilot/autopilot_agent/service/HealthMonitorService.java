package com.autopilot.autopilot_agent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class HealthMonitorService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthMonitorService.class);

    private static final String TARGET_APP_HEALTH_URL = "http://localhost:8080/actuator/health";

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void checkAppHealth() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(TARGET_APP_HEALTH_URL, String.class);
            LOG.info("Health status: {}", response.getBody());
        } catch (Exception e) {
            LOG.error("Target app not reachable or unhealthy: {}", e.getMessage());
        }
    }
}
