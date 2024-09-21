package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Pirates Treasure
 * @author Andrew (Mr Extremez)
 */

public class PiratesTreasure {


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
		client.getPacketSender().sendString("@dre@Pirate's Treasure", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.pirateTreasure == 0) {
			client.getPacketSender().sendString("Pirate's Treasure", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to Redbeard Frank in",
					8147);
			client.getPacketSender().sendString("Port Sarim", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString(
					"There are no minimum requirements.", 8150);
		} else if (client.pirateTreasure == 1) {
			client.getPacketSender().sendString("Pirate's Treasure", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"He wants me to get him some rum", 8148);
		} else if (client.pirateTreasure == 2) {
			client.getPacketSender().sendString("Pirate's Treasure", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendString(
					"I should get the rum and return to Redbeard.", 8149);
		} else if (client.pirateTreasure == 3) {
			client.getPacketSender().sendString("Pirate's Treasure", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendString("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendString(
					"He told me I need to look at the chest in", 8149);
			client.getPacketSender().sendString("The blue moon inn", 8150);
		} else if (client.pirateTreasure == 4) {
			client.getPacketSender().sendString("Pirate's Treasure", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendString("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendString(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendString(
					"I need to go to falador and kill the gardener", 8150);
		} else if (client.pirateTreasure == 5) {
			client.getPacketSender().sendString(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendString("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendString(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendString(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPacketSender().sendString(
					"I should find the casket now", 8151);
		} else if (client.pirateTreasure == 6) {
			client.getPacketSender().sendString(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendString(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendString("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendString(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendString(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPacketSender().sendString("@str@I found the casket ",
					8181);
			client.getPacketSender().sendString("@red@QUEST COMPLETE",
					8152);
			client.getPacketSender().sendString(
					"As a reward, I gained a casket.", 8153);
			client.getPacketSender().sendString("2 Quest Points.", 8153);
		}
		client.getPacketSender().showInterface(8134);
	}

}
