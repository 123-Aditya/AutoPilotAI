package com.autopilot.agent.service.recovery;

public interface RecoveryStrategy {
	
	String name();
	
    boolean attemptRecovery();

}
