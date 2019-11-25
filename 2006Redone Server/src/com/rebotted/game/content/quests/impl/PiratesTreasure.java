package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Pirates Treasure
 * @author Andrew (Mr Extremez)
 */

public class PiratesTreasure {

	Player client;

	public PiratesTreasure(Player player) {
		this.client = player;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPacketSender().sendFrame126("", i);
		}
		client.getPacketSender().sendFrame126("@dre@Pirate's Treasure", 8144);
		client.getPacketSender().sendFrame126("", 8145);
		if (client.pirateTreasure == 0) {
			client.getPacketSender().sendFrame126("Pirate's Treasure", 8144);
			client.getPacketSender().sendFrame126(
					"I can start this quest by speaking to Redbeard Frank in",
					8147);
			client.getPacketSender().sendFrame126("Port Sarim", 8148);
			client.getPacketSender().sendFrame126("", 8149);
			client.getPacketSender().sendFrame126(
					"There are no minimum requirments.", 8150);
		} else if (client.pirateTreasure == 1) {
			client.getPacketSender().sendFrame126("Pirate's Treasure", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"He wants me to get him some rum", 8148);
		} else if (client.pirateTreasure == 2) {
			client.getPacketSender().sendFrame126("Pirate's Treasure", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendFrame126(
					"I should get the rum and return to Redbeard.", 8149);
		} else if (client.pirateTreasure == 3) {
			client.getPacketSender().sendFrame126("Pirate's Treasure", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendFrame126(
					"He told me I need to look at the chest in", 8149);
			client.getPacketSender().sendFrame126("The blue moon inn", 8150);
		} else if (client.pirateTreasure == 4) {
			client.getPacketSender().sendFrame126("Pirate's Treasure", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendFrame126(
					"I need to go to falador and kill the gardener", 8150);
		} else if (client.pirateTreasure == 5) {
			client.getPacketSender().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendFrame126(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPacketSender().sendFrame126(
					"I should find the casket now", 8151);
		} else if (client.pirateTreasure == 6) {
			client.getPacketSender().sendFrame126(
					"@str@I talked to Redbeard.", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I found a way to get the rum", 8148);
			client.getPacketSender().sendFrame126("@str@I gave him the rum",
					8149);
			client.getPacketSender().sendFrame126(
					"@str@I looked in the chest", 8149);
			client.getPacketSender().sendFrame126(
					"@str@I went to falador and killed the gardener", 8150);
			client.getPacketSender().sendFrame126("@str@I found the casket ",
					8181);
			client.getPacketSender().sendFrame126("@red@QUEST COMPLETE",
					8152);
			client.getPacketSender().sendFrame126(
					"As a reward, I gained a casket.", 8153);
			client.getPacketSender().sendFrame126("2 Quest Points.", 8153);
		}
		client.getPacketSender().showInterface(8134);
	}

}
