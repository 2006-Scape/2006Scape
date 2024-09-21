package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Lost City
 * @author Olivier (JohnsonPhillips / JohnsonMichaels123)
 */

public class LostCity {
		
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
		client.getPacketSender().sendString("@dre@Lost City", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.lostCity == 0) {
			client.getPacketSender().sendString("Lost City", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to the four adventurers ", 8147);
			client.getPacketSender().sendString(
					"west of Lumbridge swamp.",
					8148);
			client.getPacketSender().sendString("Minimum Requirements", 8150);
			client.getPacketSender().sendString(
					"31 Crafting", 8151);
			client.getPacketSender().sendString(
					"36 Woodcutting", 8151);
			client.getPacketSender().sendString(
					"I must be able to defeat a high-level tree spirit", 8153);
			client.getPacketSender().sendString(
					"without the use of weapons or armour.", 8154);
		} else if (client.lostCity == 1) {
			client.getPacketSender().sendString("Lost City", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the adventurers who told me", 8147);
			client.getPacketSender().sendString(
					"that there is a leprechaun hiding in the trees nearby", 8148);
			client.getPacketSender().sendString(
					"who knows how to locate the lost city.", 8149);
		} else if (client.lostCity == 2) {
			client.getPacketSender().sendString("Lost City", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to the leprechaun who told me that", 8147);
			client.getPacketSender().sendString(
					"Zanaris can be accessed through the shed in lumbridge", 8148);
			client.getPacketSender().sendString(
					"Swamp through the use of a Dramen staff, which I can craft", 8149);
			client.getPacketSender().sendString(
					"by obtaining logs from a Spirit Tree.", 8149);
		} else if (client.lostCity == 3) {
			client.getPacketSender().sendString("Lost City", 8144);
			client.getPacketSender().sendString(
					"@str@I defeated the Spirit Tree", 8147);
			client.getPacketSender().sendString("@str@I crafted a dramen staff.",
					8148);
			client.getPacketSender().sendString(
					"I went through the Lumbridge shed and discovered Zanaris!", 8149);
		}
		client.getPacketSender().showInterface(8134);
	}

}
