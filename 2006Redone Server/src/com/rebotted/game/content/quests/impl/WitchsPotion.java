package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Witchs Potion
 * @author Andrew (Mr Extremez)
 */

public class WitchsPotion {

	Player client;

	public WitchsPotion(Player player) {
		this.client = player;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPacketSender().sendFrame126("", i);
		}
		client.getPacketSender().sendFrame126("@dre@Witch's Potion", 8144);
		client.getPacketSender().sendFrame126("", 8145);
		if (client.witchspot == 0) {
			client.getPacketSender().sendFrame126("Witch's Potion", 8144);
			client.getPacketSender().sendFrame126(
					"I can start this quest by speaking to Hetty", 8147);
			client.getPacketSender().sendFrame126("Rimmington.", 8148);
			client.getPacketSender().sendFrame126("Minimum Requirments:", 8149);
			client.getPacketSender().sendFrame126("None.", 8150);
		} else if (client.witchspot == 1) {
			client.getPacketSender().sendFrame126("Witch's Potion", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've talked to the Hetty", 8147);
			client.getPacketSender().sendFrame126(
					"I've agreed to get her the ingredients", 8148);
		} else if (client.witchspot == 2) {
			client.getPacketSender().sendFrame126("Witch's Potion", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I have all the ingredients", 8147);
			client.getPacketSender().sendFrame126("I should talk to hetty.",
					8148);
			client.getPacketSender().sendFrame126("", 8149);
		} else if (client.witchspot == 3) {
			client.getPacketSender().sendFrame126("Witch's Potion", 8144);
			client.getPacketSender().sendFrame126("@str@I've Talked to Hetty",
					8147);
			client.getPacketSender().sendFrame126(
					"@str@I drank from the Cauldron", 8148);
			client.getPacketSender().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendFrame126(
					"As a reward, I gained 325 Magic Exp.", 8151);
			client.getPacketSender().sendFrame126("And 1 Quest Point", 8152);
			client.getPacketSender().sendFrame126("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
