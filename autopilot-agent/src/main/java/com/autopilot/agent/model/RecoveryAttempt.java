package com.autopilot.agent.model;

public record RecoveryAttempt(
        int attemptNo,
        String strategy,
        boolean executed,
        boolean healthy
) {}
