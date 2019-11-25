package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Sheep Shearer
 * @author Andrew (Mr Extremez)
 */

public class SheepShearer {

	Player client;

	public SheepShearer(Player player) {
		this.client = player;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPacketSender().sendFrame126("", i);
		}
		client.getPacketSender().sendFrame126("@dre@Sheep Shearer", 8144);
		client.getPacketSender().sendFrame126("", 8145);
		if (client.sheepShear == 0) {
			client.getPacketSender().sendFrame126("Sheep Shearer", 8144);
			client.getPacketSender().sendFrame126(
					"I can start this quest by speaking to Fred in", 8147);
			client.getPacketSender().sendFrame126("Lumbridge.", 8148);
			client.getPacketSender().sendFrame126("Minimum Requirments:", 8149);
			client.getPacketSender().sendFrame126("None.", 8150);
		} else if (client.sheepShear == 1) {
			client.getPacketSender().sendFrame126("Sheep Shearer", 8144);
			client.getPacketSender().sendFrame126("@str@I've talked to fred",
					8147);
			client.getPacketSender().sendFrame126(
					"I've agreed to get him some wool.", 8148);
			if (client.getItemAssistant().playerHasItem(1759, 20)) {
				client.getPacketSender().sendFrame126("@str@Ball of Wool", 8149);
			} else {
				client.getPacketSender().sendFrame126("@red@Ball of Wool",
						8149);
			}
		} else if (client.sheepShear == 2) {
			client.getPacketSender().sendFrame126("Sheep Shearer", 8144);
			client.getPacketSender().sendFrame126("@str@I gave fred his wool",
					8147);
			client.getPacketSender().sendFrame126("@str@So he awarded me.",
					8148);
			client.getPacketSender().sendFrame126("@red@     QUEST COMPLETE",
					8149);
			client.getPacketSender().sendFrame126("As a reward, 60 coins.",
					8150);
			client.getPacketSender().sendFrame126("150 crating exp", 8150);
			client.getPacketSender().sendFrame126("And 1 Quest Point", 8151);
			client.getPacketSender().sendFrame126("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
