package com.autopilot.autopilot_agent.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.autopilot.autopilot_agent.service.recovery.RecoveryOrchestrator;
import com.autopilot.autopilot_agent.service.recovery.RecoveryResult;

import org.springframework.http.ResponseEntity;

@Service
public class HealthMonitorService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthMonitorService.class);
    
    @Autowired
    private AiAnalysisService aiAnalysisService;
    
    @Autowired
    private RecoveryOrchestrator orchestrator;

    @Value("${agent.target.url}")
    private String targetUrl;

    @Value("${agent.poll-interval-ms}")
    private long pollInterval;
    
    @Value("${agent.reports.dir}")
    private String reportsDir;

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
            
            // Save to daily report file
            saveAnalysisToFile(aiInsight);
        } catch (Exception e) {
            LOG.error("Target app not reachable: {}", e.getMessage());
            
            RecoveryResult result = null;
			try {
				result = orchestrator.attemptRecovery();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            LOG.info("🛠️ Recovery summary:\n{}", result.summary());
            saveAnalysisToFile(result.summary());
        }
    }
    
    private void saveAnalysisToFile(String content) {
        try {
            Path reportsPath = Path.of(reportsDir);
            if (!Files.exists(reportsPath)) {
                Files.createDirectories(reportsPath);
            }

            String fileName = "ai-analysis-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log";
            Path reportFile = reportsPath.resolve(fileName);

            try (FileWriter writer = new FileWriter(reportFile.toFile(), true)) {
                writer.write("\n[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]\n");
                writer.write(content + "\n");
                writer.write("----------------------------------------------------\n");
            }
        } catch (IOException e) {
            LOG.error("Error writing AI analysis report: {}", e.getMessage());
        }
    }

}
