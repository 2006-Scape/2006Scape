package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Gertrudes Cat
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class GertrudesCat {

	Client client;

	public GertrudesCat(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Gertrudes Cat", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.gertCat == 0) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Gertrude in", 8147);
			client.getPlayerAssistant().sendFrame126("Varrock.", 8148);
			client.getPlayerAssistant()
					.sendFrame126("Minimum Requirments:", 8149);
			client.getPlayerAssistant().sendFrame126("5 Fishing.", 8150);
		} else if (client.gertCat == 1) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Gertrude", 8147);
			client.getPlayerAssistant().sendFrame126(
					"I should speak to Wilough and Shilop.", 8148);
			client.getPlayerAssistant().sendFrame126("They are in the varrock square.", 8149);
		} else if (client.gertCat == 2) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Wilough and Shilop", 8147);
			client.getPlayerAssistant().sendFrame126("@str@I gave them 100 coins",
					8148);
			client.getPlayerAssistant().sendFrame126(
					"I should try to find Gertrudes Cat.", 8149);
		} else if (client.gertCat == 3) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the Gertrudes Cat", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@She seemed mad so I gave her", 8148);
			client.getPlayerAssistant().sendFrame126("@str@a bucket of milk",
					8149);
			client.getPlayerAssistant().sendFrame126("The cat still seems mad you should give her some,", 8150);
			client.getPlayerAssistant().sendFrame126("seasoned salmon.", 8151);
		} else if (client.gertCat == 4) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126("@str@The cat is still mad",
					8147);
			client.getPlayerAssistant().sendFrame126("@str@So i gave her some",
					8148);
			client.getPlayerAssistant()
					.sendFrame126("@str@seasoned samon.", 8149);
			client.getPlayerAssistant().sendFrame126("I should talk to Gertrudes cat to see how she feels.", 8150);
		} else if (client.gertCat == 5) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@The cat seems to be mad", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@because she can't find her kittens.", 8148);
			client.getPlayerAssistant().sendFrame126("I should go in the lumberyard and", 8149);
			client.getPlayerAssistant().sendFrame126("check it out.", 8150);
		} else if (client.gertCat == 6) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I gave gertrudes cat",
					8147);
			client.getPlayerAssistant().sendFrame126("@str@her kittens", 8148);
			client.getPlayerAssistant().sendFrame126(
					"@str@and she now seems happy.", 8149);
			client.getPlayerAssistant().sendFrame126("I should head back to Gertrude.", 8150);
		} else if (client.gertCat == 7) {
			client.getPlayerAssistant().sendFrame126("Gertrudes Cat", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I helped gertrude with her", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@Cat so she awarded me.", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8149);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 1525 Cooking exp.", 8150);
			client.getPlayerAssistant().sendFrame126(
					"A kitten! And the ability to raise cats.", 8150);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point", 8151);
			client.getPlayerAssistant().sendFrame126("", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
