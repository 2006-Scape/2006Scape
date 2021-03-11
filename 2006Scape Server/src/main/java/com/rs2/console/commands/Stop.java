package com.rs2.console.commands;

import com.rs2.GameEngine;
import com.rs2.console.CommandProcessor;

/**
 * 
 * @author RS-Emulators
 *
 */
public class Stop implements CommandProcessor {

	@Override
	public boolean command(String[] cmd) {
		if (!cmd[0].equalsIgnoreCase("stop")) {
			return false;
		}
		System.out.println("Setting shutdown flag to true...");
		GameEngine.shutdownServer = true;
		return true;
	}

	@Override
	public String help() {
		return "stop - sets GameEngine.shutdownServer to true.";
	}

}
