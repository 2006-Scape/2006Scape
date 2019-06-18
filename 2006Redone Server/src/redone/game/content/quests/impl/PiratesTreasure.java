package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Pirates Treasure
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class PiratesTreasure {

	Client client;

	public PiratesTreasure(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Pirate's Treasure", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.pirateTreasure == 0) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Redbeard Frank in",
					8147);
			client.getPlayerAssistant().sendFrame126("Port Sarim", 8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant().sendFrame126(
					"There are no minimum requirments.", 8150);
		} else if (client.pirateTreasure == 1) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"He wants me to get him some rum", 8148);
		} else if (client.pirateTreasure == 2) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPlayerAssistant().sendFrame126(
					"I should get the rum and return to Redbeard.", 8149);
		} else if (client.pirateTreasure == 3) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPlayerAssistant().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPlayerAssistant().sendFrame126(
					"He told me I need to look at the chest in", 8149);
			client.getPlayerAssistant().sendFrame126("The blue moon inn", 8150);
		} else if (client.pirateTreasure == 4) {
			client.getPlayerAssistant().sendFrame126("Pirate's Treasure", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPlayerAssistant().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPlayerAssistant().sendFrame126(
					"I need to go to falador and kill the gardener", 8150);
		} else if (client.pirateTreasure == 5) {
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPlayerAssistant().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPlayerAssistant().sendFrame126(
					"I should find the casket now", 8151);
		} else if (client.pirateTreasure == 6) {
			client.getPlayerAssistant().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPlayerAssistant().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPlayerAssistant().sendFrame126(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPlayerAssistant().sendFrame126("@str@I found the casket ",
					8181);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8152);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained a casket.", 8153);
			client.getPlayerAssistant().sendFrame126("2 Quest Points.", 8153);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
