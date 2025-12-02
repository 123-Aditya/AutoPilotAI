package com.autopilot.autopilot_agent.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthVerifier {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isHealthy(String healthUrl) {
        try {
            String response = restTemplate.getForObject(healthUrl, String.class);
            return response != null && response.contains("\"status\":\"UP\"");
        } catch (Exception e) {
            return false;
        }
    }
}

