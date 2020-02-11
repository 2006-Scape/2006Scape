package com.rebotted.console;

/**
 * 
 * @author RS-Emulators
 *
 */
public interface CommandProcessor {
	
	public boolean command(String[] cmd);
	
	public String help();
}
