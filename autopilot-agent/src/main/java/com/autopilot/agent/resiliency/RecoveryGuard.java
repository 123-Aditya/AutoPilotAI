package com.autopilot.agent.resiliency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecoveryGuard {

    private long lastRecoveryTime = 0;

    @Value("${agent.recovery.cooldown.ms}")
    private long cooldownMs;

    public boolean canRecover() {
        long now = System.currentTimeMillis();
        return (now - lastRecoveryTime) > cooldownMs;
    }

    public void markRecoveryAttempted() {
        lastRecoveryTime = System.currentTimeMillis();
    }
}

