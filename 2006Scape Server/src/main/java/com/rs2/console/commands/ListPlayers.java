package com.rs2.console.commands;

import com.rs2.console.CommandProcessor;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;

/**
 * 
 * @author RS-Emulators
 *
 */
public class ListPlayers implements CommandProcessor {

	@Override
	public boolean command(String[] cmd) {
		if (!cmd[0].equalsIgnoreCase("list")) {
			return false;
		}
		System.out.println("Currently online: " + PlayerHandler.getPlayerCount());
		for (Player p : PlayerHandler.players) {
			System.out.print(p.playerName + ",");
		}
		return true;
	}

	@Override
	public String help() {
		return "list - lists online players.";
	}

}
