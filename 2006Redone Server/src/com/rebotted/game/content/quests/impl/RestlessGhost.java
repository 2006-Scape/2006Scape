package com.rebotted.game.content.quests.impl;

import com.rebotted.game.players.Player;

/**
 * Restless Ghost 
 * @author Andrew (Mr Extremez)
 */

public class RestlessGhost {


	public void showInformation(Player client) {
		for (int i = 8144; i < 8195; i++) {
			client.getPacketSender().sendFrame126("", i);
		}
		client.getPacketSender().sendFrame126("@dre@Restless Ghost", 8144);
		client.getPacketSender().sendFrame126("", 8145);
		if (client.restGhost == 0) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126("I can start this quest by speaking to Father Aereck in",	8147);
			client.getPacketSender().sendFrame126("Lumbridge", 8148);
			client.getPacketSender().sendFrame126("Minimum Requirments:", 8149);
			client.getPacketSender().sendFrame126("None.", 8150);
		} else if (client.restGhost == 1) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've talked to Father Aereck", 8147);
			client.getPacketSender().sendFrame126(
					"I should speak to Father Urhey", 8148);
		} else if (client.restGhost == 2) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've talked Father Urhey", 8147);
			client.getPacketSender().sendFrame126("@str@He gave me a amulet",
					8148);
			client.getPacketSender().sendFrame126(
					"I should speak to the ghost", 8149);
		} else if (client.restGhost == 3) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've talked to the Ghost", 8147);
			client.getPacketSender().sendFrame126("I should travel to the wizards tower and kill the skeleton", 8148);
			client.getPacketSender().sendFrame126(
					"I should find the ghosts skull", 8149);
		} else if (client.restGhost == 4) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126("@str@I've found the skull",
					8147);
			client.getPacketSender().sendFrame126(
					"@str@I killed the skeleton", 8148);
			client.getPacketSender().sendFrame126(
					"I should travel back to the ghost", 8149);
		} else if (client.restGhost == 5) {
			client.getPacketSender().sendFrame126("Restless Ghost", 8144);
			client.getPacketSender().sendFrame126(
					"@str@I've set the skull in the coffin", 8147);
			client.getPacketSender().sendFrame126(
					"@str@I've freed the ghost.", 8148);
			client.getPacketSender().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendFrame126(
					"As a reward, I gained 125 Prayer Exp.", 8151);
			client.getPacketSender().sendFrame126("And 1 Quest Point", 8152);
			client.getPacketSender().sendFrame126("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
