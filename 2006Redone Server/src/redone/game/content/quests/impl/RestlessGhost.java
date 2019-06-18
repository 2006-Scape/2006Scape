package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Restless Ghost 
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class RestlessGhost {

	Client client;

	public RestlessGhost(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Restless Ghost", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.restGhost == 0) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Father Aereck in",
					8147);
			client.getPlayerAssistant().sendFrame126("Lumbridge", 8148);
			client.getPlayerAssistant()
					.sendFrame126("Minimum Requirments:", 8149);
			client.getPlayerAssistant().sendFrame126("None.", 8150);
		} else if (client.restGhost == 1) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Father Aereck", 8147);
			client.getPlayerAssistant().sendFrame126(
					"I should speak to Father Urhey", 8148);
		} else if (client.restGhost == 2) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked Father Urhey", 8147);
			client.getPlayerAssistant().sendFrame126("@str@He gave me a amulet",
					8148);
			client.getPlayerAssistant().sendFrame126(
					"I should speak to the ghost", 8149);
		} else if (client.restGhost == 3) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the Ghost", 8147);
			client.getPlayerAssistant()
					.sendFrame126(
							"I should travel to the wizards tower and kill the skeleton",
							8148);
			client.getPlayerAssistant().sendFrame126(
					"I should find the ghosts skull", 8149);
		} else if (client.restGhost == 4) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've found the skull",
					8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I killed the skeleton", 8148);
			client.getPlayerAssistant().sendFrame126(
					"I should travel back to the ghost", 8149);
		} else if (client.restGhost == 5) {
			client.getPlayerAssistant().sendFrame126("Restless Ghost", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've set the skull in the coffin", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've freed the ghost.", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 125 Prayer Exp.", 8151);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point", 8152);
			client.getPlayerAssistant().sendFrame126("", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
