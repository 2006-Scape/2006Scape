package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Doric's Quest
 * @author Andrew (Mr Extremez)
 */

public class DoricsQuest {

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
		client.getPacketSender().sendString("@dre@Dorics Quest", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.doricQuest == 0) {
			client.getPacketSender().sendString("Dorics Quest", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to doric", 8147);
			client.getPacketSender().sendString("Northwest of falador.",
					8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString(
					"Recommended Levels: 15 Mining", 8150);
		} else if (client.doricQuest == 1) {
			client.getPacketSender().sendString("Dorics Quest", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the doric.", 8147);
			client.getPacketSender().sendString(
					"He wants me to gather the following materials:", 8148);
			if (client.getItemAssistant().playerHasItem(434, 6)) {
				client.getPacketSender().sendString("@str@6 Clay", 8149);
			} else {
				client.getPacketSender().sendString("@red@6 Clay", 8149);
			}
			if (client.getItemAssistant().playerHasItem(436, 4)) {
				client.getPacketSender().sendString("@str@4 Copper", 8150);
			} else {
				client.getPacketSender().sendString("@red@4 Copper", 8150);
			}
			if (client.getItemAssistant().playerHasItem(440, 2)) {
				client.getPacketSender().sendString("@str@2 Iron ore", 8151);
			} else {
				client.getPacketSender().sendString("@red@2 Iron ore", 8151);
			}
		} else if (client.doricQuest == 2) {
			client.getPacketSender().sendString("Dorics Quest", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to the doric.", 8147);
			client.getPacketSender().sendString(
					"@str@I gave the doric his items.", 8148);
			client.getPacketSender().sendString(
					"I should go speak to the doric.", 8149);
		} else if (client.doricQuest == 3) {
			client.getPacketSender().sendString("Dorics Quest", 8144);
			client.getPacketSender().sendString(
					"@str@I talked to the doric.", 8147);
			client.getPacketSender().sendString(
					"@str@I gave him his items.", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendString(
					"As a reward, I gained 26000 Mining Exp", 8151);
			client.getPacketSender().sendString("180 Coins", 8152);
			client.getPacketSender().sendString("And 1 Quest Point.", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
