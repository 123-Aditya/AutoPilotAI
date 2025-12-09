package com.autopilot.agent.util;

import java.util.concurrent.TimeUnit;

public class ProcessUtil {

    public static boolean run(ProcessBuilder builder, int timeoutSeconds)
            throws Exception {

        Process process = builder.start();
        boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);

        if (!finished) {
            process.destroy();
            return false;
        }
        return process.exitValue() == 0;
    }
}

