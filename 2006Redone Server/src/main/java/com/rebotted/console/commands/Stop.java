package com.rebotted.console.commands;

import com.rebotted.GameEngine;
import com.rebotted.console.CommandProcessor;

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
