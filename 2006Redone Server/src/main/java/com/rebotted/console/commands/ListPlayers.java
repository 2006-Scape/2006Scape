package com.rebotted.console.commands;

import com.rebotted.console.CommandProcessor;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;

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
