package com.autopilot.autopilot_agent.service.recovery;

public interface RecoveryStrategy {
	
	String name();
	
    boolean attemptRecovery();

}
