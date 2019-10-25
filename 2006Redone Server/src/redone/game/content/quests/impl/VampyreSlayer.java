package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Vampyre Slayer
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class VampyreSlayer {

	Client client;

	public String[] lines = new String[]{};

	public VampyreSlayer(Client client) {
		this.client = client;
	}

	public void showInformation() {
		// Clear all lines
		for (int i = 8144; i < 8195; i++) client.getPlayerAssistant().sendFrame126("", i);
		// Set the title
		client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
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
		for (String line : lines) client.getPlayerAssistant().sendFrame126(line, lineNumber++);
		client.getPlayerAssistant().showInterface(8134);
	}
}
