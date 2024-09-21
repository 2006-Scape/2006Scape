package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Gertrudes Cat
 * @author Andrew (Mr Extremez)
 */

public class GertrudesCat {

	public static void showInformation(Player client) {
		for (int i = 8144; i < 8196; i++) {
			client.getPacketSender().sendString("", i);
		}
		client.getPacketSender().sendString("@dre@Gertrudes Cat", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.gertCat == 0) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to Gertrude in", 8147);
			client.getPacketSender().sendString("Varrock.", 8148);
			client.getPacketSender().sendString("Minimum Requirments:", 8149);
			client.getPacketSender().sendString("5 Fishing.", 8150);
		} else if (client.gertCat == 1) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Gertrude", 8147);
			client.getPacketSender().sendString(
					"I should speak to Wilough and Shilop.", 8148);
			client.getPacketSender().sendString("They are in the varrock square.", 8149);
		} else if (client.gertCat == 2) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Wilough and Shilop", 8147);
			client.getPacketSender().sendString("@str@I gave them 100 coins",
					8148);
			client.getPacketSender().sendString(
					"I should try to find Gertrudes Cat.", 8149);
		} else if (client.gertCat == 3) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the Gertrudes Cat", 8147);
			client.getPacketSender().sendString(
					"@str@She seemed mad so I gave her", 8148);
			client.getPacketSender().sendString("@str@a bucket of milk",
					8149);
			client.getPacketSender().sendString("The cat still seems mad you should give her some,", 8150);
			client.getPacketSender().sendString("seasoned salmon.", 8151);
		} else if (client.gertCat == 4) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString("@str@The cat is still mad",
					8147);
			client.getPacketSender().sendString("@str@So i gave her some",
					8148);
			client.getPacketSender().sendString("@str@seasoned samon.", 8149);
			client.getPacketSender().sendString("I should talk to Gertrudes cat to see how she feels.", 8150);
		} else if (client.gertCat == 5) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"@str@The cat seems to be mad", 8147);
			client.getPacketSender().sendString(
					"@str@because she can't find her kittens.", 8148);
			client.getPacketSender().sendString("I should go in the lumberyard and", 8149);
			client.getPacketSender().sendString("check it out.", 8150);
		} else if (client.gertCat == 6) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString("@str@I gave gertrudes cat",
					8147);
			client.getPacketSender().sendString("@str@her kittens", 8148);
			client.getPacketSender().sendString(
					"@str@and she now seems happy.", 8149);
			client.getPacketSender().sendString("I should head back to Gertrude.", 8150);
		} else if (client.gertCat == 7) {
			client.getPacketSender().sendString("Gertrudes Cat", 8144);
			client.getPacketSender().sendString(
					"@str@I helped gertrude with her", 8147);
			client.getPacketSender().sendString(
					"@str@Cat so she awarded me.", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8149);
			client.getPacketSender().sendString(
					"As a reward, I gained 1525 Cooking exp.", 8150);
			client.getPacketSender().sendString(
					"A kitten! And the ability to raise cats.", 8150);
			client.getPacketSender().sendString("And 1 Quest Point", 8151);
			client.getPacketSender().sendString("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
