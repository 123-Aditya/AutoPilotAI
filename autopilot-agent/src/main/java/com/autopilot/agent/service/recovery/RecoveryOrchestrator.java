package com.autopilot.agent.service.recovery;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.autopilot.agent.service.HealthVerifier;

@Service
public class RecoveryOrchestrator {

    private final List<RecoveryStrategy> strategies;
    private final HealthVerifier healthVerifier;

    @Value("${agent.health.check.url}")
    private String healthUrl;

    @Value("${agent.recovery.wait.ms}")
    private long waitMs;

    public RecoveryOrchestrator(
            List<RecoveryStrategy> strategies,
            HealthVerifier healthVerifier) {
        this.strategies = strategies;
        this.healthVerifier = healthVerifier;
    }

    public RecoveryResult attemptRecovery() throws InterruptedException {

        StringBuilder summary = new StringBuilder();
        int attempt = 1;

        for (RecoveryStrategy strategy : strategies) {

            summary.append("Attempt ")
                   .append(attempt)
                   .append(": ")
                   .append(strategy.name());

            boolean executed = strategy.attemptRecovery();

            Thread.sleep(waitMs); // ✅ wait for app to stabilize

            boolean healthy = healthVerifier.isHealthy(healthUrl);

            if (executed && healthy) {
                summary.append(" → SUCCESS (app healthy)\n");
                return new RecoveryResult(true, summary.toString());
            } else {
                summary.append(" → FAILED\n");
            }

            attempt++;
        }

        return new RecoveryResult(false, summary.toString());
    }
}


