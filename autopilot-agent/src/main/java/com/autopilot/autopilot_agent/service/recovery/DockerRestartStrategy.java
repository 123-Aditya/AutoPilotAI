package com.autopilot.autopilot_agent.service.recovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DockerRestartStrategy implements RecoveryStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(DockerRestartStrategy.class);

    @Override
    public String name() {
        return "Docker container restart";
    }

    @Override
    public boolean attemptRecovery() {
        try {
            Process process = new ProcessBuilder(
                    "docker", "restart", "autopilot-ai-app"
            ).start();

            int exitCode = process.waitFor();
            LOG.info("Docker restart exit code: {}", exitCode);

            return exitCode == 0;
        } catch (Exception e) {
            LOG.error("Docker restart failed", e);
            return false;
        }
    }
}

