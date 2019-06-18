package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Witchs Potion
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class WitchsPotion {

	Client client;

	public WitchsPotion(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Witch's Potion", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.witchspot == 0) {
			client.getPlayerAssistant().sendFrame126("Witch's Potion", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Hetty", 8147);
			client.getPlayerAssistant().sendFrame126("Rimmington.", 8148);
			client.getPlayerAssistant()
					.sendFrame126("Minimum Requirments:", 8149);
			client.getPlayerAssistant().sendFrame126("None.", 8150);
		} else if (client.witchspot == 1) {
			client.getPlayerAssistant().sendFrame126("Witch's Potion", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the Hetty", 8147);
			client.getPlayerAssistant().sendFrame126(
					"I've agreed to get her the ingredients", 8148);
		} else if (client.witchspot == 2) {
			client.getPlayerAssistant().sendFrame126("Witch's Potion", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I have all the ingredients", 8147);
			client.getPlayerAssistant().sendFrame126("I should talk to hetty.",
					8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
		} else if (client.witchspot == 3) {
			client.getPlayerAssistant().sendFrame126("Witch's Potion", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've Talked to Hetty",
					8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I drank from the Cauldron", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 325 Magic Exp.", 8151);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point", 8152);
			client.getPlayerAssistant().sendFrame126("", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
