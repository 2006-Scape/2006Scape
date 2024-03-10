package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Witchs Potion
 * @author Andrew (Mr Extremez)
 */

public class WitchsPotion {

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
		client.getPacketSender().sendString("@dre@Witch's Potion", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.witchspot == 0) {
			client.getPacketSender().sendString("Witch's Potion", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to Hetty", 8147);
			client.getPacketSender().sendString("Rimmington.", 8148);
			client.getPacketSender().sendString("Minimum Requirments:", 8149);
			client.getPacketSender().sendString("None.", 8150);
		} else if (client.witchspot == 1) {
			client.getPacketSender().sendString("Witch's Potion", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the Hetty", 8147);
			client.getPacketSender().sendString(
					"I've agreed to get her the ingredients", 8148);
		} else if (client.witchspot == 2) {
			client.getPacketSender().sendString("Witch's Potion", 8144);
			client.getPacketSender().sendString(
					"@str@I have all the ingredients", 8147);
			client.getPacketSender().sendString("I should talk to hetty.",
					8148);
			client.getPacketSender().sendString("", 8149);
		} else if (client.witchspot == 3) {
			client.getPacketSender().sendString("Witch's Potion", 8144);
			client.getPacketSender().sendString("@str@I've Talked to Hetty",
					8147);
			client.getPacketSender().sendString(
					"@str@I drank from the Cauldron", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendString(
					"As a reward, I gained 325 Magic Exp.", 8151);
			client.getPacketSender().sendString("And 1 Quest Point", 8152);
			client.getPacketSender().sendString("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
