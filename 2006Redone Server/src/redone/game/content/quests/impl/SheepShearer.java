package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Sheep Shearer
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class SheepShearer {

	Client client;

	public SheepShearer(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Sheep Shearer", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.sheepShear == 0) {
			client.getPlayerAssistant().sendFrame126("Sheep Shearer", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Fred in", 8147);
			client.getPlayerAssistant().sendFrame126("Lumbridge.", 8148);
			client.getPlayerAssistant()
					.sendFrame126("Minimum Requirments:", 8149);
			client.getPlayerAssistant().sendFrame126("None.", 8150);
		} else if (client.sheepShear == 1) {
			client.getPlayerAssistant().sendFrame126("Sheep Shearer", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've talked to fred",
					8147);
			client.getPlayerAssistant().sendFrame126(
					"I've agreed to get him some wool.", 8148);
			if (client.getItemAssistant().playerHasItem(1759, 20)) {
				client.getPlayerAssistant()
						.sendFrame126("@str@Bal of Wool", 8149);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@Ball of Wool",
						8149);
			}
		} else if (client.sheepShear == 2) {
			client.getPlayerAssistant().sendFrame126("Sheep Shearer", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I gave fred his wool",
					8147);
			client.getPlayerAssistant().sendFrame126("@str@So he awarded me.",
					8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8149);
			client.getPlayerAssistant().sendFrame126("As a reward, 60 coins.",
					8150);
			client.getPlayerAssistant().sendFrame126("150 crating exp", 8150);
			client.getPlayerAssistant().sendFrame126("And 1 Quest Point", 8151);
			client.getPlayerAssistant().sendFrame126("", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
