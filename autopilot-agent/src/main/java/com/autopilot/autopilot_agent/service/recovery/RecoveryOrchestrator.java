package com.autopilot.autopilot_agent.service.recovery;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecoveryOrchestrator {

    private final List<RecoveryStrategy> strategies;

    public RecoveryOrchestrator(List<RecoveryStrategy> strategies) {
        this.strategies = strategies;
    }

    public RecoveryResult attemptRecovery() {
        StringBuilder summary = new StringBuilder();
        int attempt = 1;

        for (RecoveryStrategy strategy : strategies) {
            summary.append("Attempt ")
                   .append(attempt)
                   .append(": ")
                   .append(strategy.name())
                   .append(" → ");

            boolean success = strategy.attemptRecovery();
            summary.append(success ? "SUCCESS\n" : "FAILED\n");

            if (success) {
                return new RecoveryResult(true, summary.toString());
            }
            attempt++;
        }

        return new RecoveryResult(false, summary.toString());
    }
}

