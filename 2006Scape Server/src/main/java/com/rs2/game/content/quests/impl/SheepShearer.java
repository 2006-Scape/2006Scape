package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Sheep Shearer
 * @author Andrew (Mr Extremez)
 */

public class SheepShearer {

	public static void showInformation(Player client) {
		for (int i = 8144; i < 8196; i++) {
			client.getPacketSender().sendString("", i);
		}
		for (int i = 12174; i < (12174 + 50); i++) {
			client.getPacketSender().sendString( "", i);
		}
		for (int i = 14945; i < (14945 + 100); i++) {
			client.getPacketSender().sendString("", i);
		}
		client.getPacketSender().sendString("@dre@Sheep Shearer", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.sheepShear == 0) {
			client.getPacketSender().sendString("Sheep Shearer", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to Fred in", 8147);
			client.getPacketSender().sendString("Lumbridge.", 8148);
			client.getPacketSender().sendString("Minimum Requirments:", 8149);
			client.getPacketSender().sendString("None.", 8150);
		} else if (client.sheepShear == 1) {
			client.getPacketSender().sendString("Sheep Shearer", 8144);
			client.getPacketSender().sendString("@str@I've talked to fred",
					8147);
			client.getPacketSender().sendString(
					"I've agreed to get him some wool.", 8148);
			if (client.getItemAssistant().playerHasItem(1759, 20)) {
				client.getPacketSender().sendString("@str@Ball of Wool", 8149);
			} else {
				client.getPacketSender().sendString("@red@Ball of Wool",
						8149);
			}
		} else if (client.sheepShear == 2) {
			client.getPacketSender().sendString("Sheep Shearer", 8144);
			client.getPacketSender().sendString("@str@I gave fred his wool",
					8147);
			client.getPacketSender().sendString("@str@So he awarded me.",
					8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8149);
			client.getPacketSender().sendString("As a reward, 60 coins.",
					8150);
			client.getPacketSender().sendString("150 crating exp", 8150);
			client.getPacketSender().sendString("And 1 Quest Point", 8151);
			client.getPacketSender().sendString("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
