package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Vampyre Slayer
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class VampyreSlayer {

	Client client;

	public VampyreSlayer(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Vampyre Slayer", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.vampSlayer == 0) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126("I can start this quest by speaking to Morgan in", 8147);
			client.getPlayerAssistant().sendFrame126("Draynor Village.", 8148);
			client.getPlayerAssistant().sendFrame126("Minimum Requirments:", 8149);
			client.getPlayerAssistant().sendFrame126("Be able to kill a level 37 monster.", 8150);
		} else if (client.vampSlayer == 1) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've talked to the Morgan", 8147);
			client.getPlayerAssistant().sendFrame126("I should speak to Doctor Harlow", 8148);
		} else if (client.vampSlayer == 2) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've talked to Doctor Harlow", 8147);
			client.getPlayerAssistant().sendFrame126("I need to him a beer.", 8148);
			client.getPlayerAssistant().sendFrame126("I should speak to the bartender", 8149);
		} else if (client.vampSlayer == 3) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the bartender", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I gave Doctor Harlow the beer", 8148);
			client.getPlayerAssistant().sendFrame126(
					"@str@Doctor Harlow talked to me and", 8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@Gave me a stake and hammer.", 8150);
			client.getPlayerAssistant().sendFrame126("Get everything you need and go to Draynor Village.", 8151);
			client.getPlayerAssistant().sendFrame126("Begin your battle.", 8152);
		} else if (client.vampSlayer == 4) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've killed the Vampire", 8147);
			client.getPlayerAssistant().sendFrame126("I need to talk to Morgan to complete the quest.", 8148);
		} else if (client.vampSlayer == 5) {
			client.getPlayerAssistant().sendFrame126("Vampyre Slayer", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've Talked to Morgan", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 4825 Attack Exp.", 8151);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point", 8152);
			client.getPlayerAssistant().sendFrame126("", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
