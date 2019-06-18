package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Doric's Quest
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class DoricsQuest {

	Client client;

	public DoricsQuest(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Dorics Quest", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.doricQuest == 0) {
			client.getPlayerAssistant().sendFrame126("Dorics Quest", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to doric", 8147);
			client.getPlayerAssistant().sendFrame126("Northwest of falador.",
					8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant().sendFrame126(
					"Recommended Levels: 15 Mining", 8150);
		} else if (client.doricQuest == 1) {
			client.getPlayerAssistant().sendFrame126("Dorics Quest", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the doric.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"He wants me to gather the following materials:", 8148);
			if (client.getItemAssistant().playerHasItem(434, 6)) {
				client.getPlayerAssistant().sendFrame126("@str@6 Clay", 8149);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@6 Clay", 8149);
			}
			if (client.getItemAssistant().playerHasItem(436, 4)) {
				client.getPlayerAssistant().sendFrame126("@str@4 Copper", 8150);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@4 Copper", 8150);
			}
			if (client.getItemAssistant().playerHasItem(440, 2)) {
				client.getPlayerAssistant().sendFrame126("@str@2 Iron ore", 8151);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@2 Iron ore", 8151);
			}
		} else if (client.doricQuest == 2) {
			client.getPlayerAssistant().sendFrame126("Dorics Quest", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to the doric.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I gave the doric his items.", 8148);
			client.getPlayerAssistant().sendFrame126(
					"I should go speak to the doric.", 8149);
		} else if (client.doricQuest == 3) {
			client.getPlayerAssistant().sendFrame126("Dorics Quest", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to the doric.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I gave him his items.", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 26000 Mining Exp", 8151);
			client.getPlayerAssistant().sendFrame126("180 Coins", 8152);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point.", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
