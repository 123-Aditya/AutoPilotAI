package com.autopilot.autopilot_agent.service.recovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DockerRebuildStrategy implements RecoveryStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(DockerRebuildStrategy.class);

    @Override
    public String name() {
        return "Docker rebuild and restart";
    }

    @Override
    public boolean attemptRecovery() {
        try {
            Process build = new ProcessBuilder(
                    "docker", "build", "-t", "autopilot-ai-app", "."
            ).inheritIO().start();

            if (build.waitFor() != 0) return false;

            Process run = new ProcessBuilder(
                    "docker", "run", "-d", "-p", "8080:8080", "autopilot-ai-app"
            ).inheritIO().start();

            return run.waitFor() == 0;
        } catch (Exception e) {
            LOG.error("Docker rebuild failed", e);
            return false;
        }
    }
}


