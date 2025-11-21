package com.autopilot.autopilot_agent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class HealthMonitorService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthMonitorService.class);
    
    @Autowired
    private AiAnalysisService aiAnalysisService;

    @Value("${agent.target.url}")
    private String targetUrl;

    @Value("${agent.poll-interval-ms}")
    private long pollInterval;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * This method checks the target app’s health periodically.
     * The interval is dynamically configured from application.properties.
     */
    @Scheduled(fixedDelayString = "${agent.poll-interval-ms}")
    public void checkAppHealth() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);
            String healthJson = response.getBody();
            LOG.info("Raw Health JSON: {}", healthJson);

            // Ask AI to analyze
            String aiInsight = aiAnalysisService.analyzeHealth(healthJson);
            LOG.info("AI agent analysis:\n{}", aiInsight);

        } catch (Exception e) {
            LOG.error("Target app not reachable: {}", e.getMessage());
        }
    }

}
