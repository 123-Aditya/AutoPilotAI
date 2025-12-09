package com.autopilot.agent.service.recovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.autopilot.agent.util.ProcessUtil;

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
            LOG.info("Attempting Docker build...");

            boolean buildSuccess = ProcessUtil.run(
                    new ProcessBuilder("docker", "build", "-t", "autopilot-ai-app", "."),
                    120  // build timeout ~2 minutes
            );

            if (!buildSuccess) {
                LOG.warn("Docker build failed.");
                return false;
            }

            LOG.info("Attempting Docker run...");

            boolean runSuccess = ProcessUtil.run(
                    new ProcessBuilder(
                        "docker", "run",
                        "-d", "-p", "8080:8080",
                        "--name", "autopilot-ai-app",
                        "autopilot-ai-app"
                    ),
                    30 // timeout
            );

            return runSuccess;

        } catch (Exception e) {
            LOG.error("Docker rebuild failed", e);
            return false;
        }
    }
}


