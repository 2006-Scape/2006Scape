package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Vampyre Slayer
 * @author Andrew (Mr Extremez)
 */

public class VampyreSlayer {
	
	private static String[] lines = new String[]{};


	public static void showInformation(Player client) {
		// Clear all lines
		for (int i = 8144; i < 8196; i++) client.getPacketSender().sendString("", i);
		for (int i = 12174; i < (12174 + 50); i++) {
			client.getPacketSender().sendString( "", i);
		}
		for (int i = 14945; i < (14945 + 100); i++) {
			client.getPacketSender().sendString("", i);
		}
		// Set the title
		client.getPacketSender().sendString("Vampyre Slayer", 8144);
		// Add content
		if (client.vampSlayer == 0) {
			lines = new String[]{
					"I can start this quest by speaking to Morgan in",
					"Draynor Village.",
					"Minimum Requirements:",
					"Be able to kill a level 37 monster.",
			};
		} else if (client.vampSlayer == 1) {
			lines =  new String[]{
					"@str@I've talked to Morgan",
					"I should speak to Doctor Harlow",
			};
		} else if (client.vampSlayer == 2) {
			lines =  new String[]{
					"@str@I've talked to Doctor Harlow",
					"I need to bring him a beer.",
					"I should speak to the bartender",
			};
		} else if (client.vampSlayer == 3) {
			lines =  new String[]{
					"@str@I've talked to the bartender",
					"@str@I gave Doctor Harlow the beer",
					"@str@Doctor Harlow talked to me and",
					"@str@Gave me a stake and hammer.",
					"Get everything you need and go to Draynor Village.",
					"Begin your battle.",
			};
		} else if (client.vampSlayer == 4) {
			lines =  new String[]{
					"@str@I've killed the Vampire",
					"I need to talk to Morgan to complete the quest.",
			};
		} else if (client.vampSlayer == 5) {
			lines =  new String[]{
					"@str@I've killed the Vampire",
					"@str@I've Talked to Morgan",
					"",
					"@red@QUEST COMPLETE",
					"",
					"REWARDS:",
					"4,825 Attack Exp.",
					"1 Quest Point",
			};
		}
		// Send the lines to the client
		int lineNumber = 8147;
		for (String line : lines) client.getPacketSender().sendString(line, lineNumber++);
		client.getPacketSender().showInterface(8134);
	}
}
