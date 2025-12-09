package com.autopilot.agent.util;

public class DockerSafetyUtil {

    public static boolean containerExists(String name) {
        try {
            Process p = new ProcessBuilder(
                    "docker", "inspect", name
            ).start();
            return p.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}

