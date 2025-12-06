package com.autopilot.autopilot_agent.model;

public record RecoveryAttempt(
        int attemptNo,
        String strategy,
        boolean executed,
        boolean healthy
) {}
