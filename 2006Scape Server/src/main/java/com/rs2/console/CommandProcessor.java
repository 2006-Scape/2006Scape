package com.rs2.console;

/**
 * 
 * @author RS-Emulators
 *
 */
public interface CommandProcessor {
	
	public boolean command(String[] cmd);
	
	public String help();
}
