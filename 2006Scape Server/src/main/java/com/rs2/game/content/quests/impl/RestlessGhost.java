package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Restless Ghost 
 * @author Andrew (Mr Extremez)
 */

public class RestlessGhost {


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
		client.getPacketSender().sendString("@dre@Restless Ghost", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.restGhost == 0) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString("I can start this quest by speaking to Father Aereck in",	8147);
			client.getPacketSender().sendString("Lumbridge", 8148);
			client.getPacketSender().sendString("Minimum Requirements:", 8149);
			client.getPacketSender().sendString("None.", 8150);
		} else if (client.restGhost == 1) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Father Aereck", 8147);
			client.getPacketSender().sendString(
					"I should speak to Father Urhey", 8148);
		} else if (client.restGhost == 2) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked Father Urhey", 8147);
			client.getPacketSender().sendString("@str@He gave me an amulet",
					8148);
			client.getPacketSender().sendString(
					"I should speak to the ghost", 8149);
		} else if (client.restGhost == 3) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the Ghost", 8147);
			client.getPacketSender().sendString("I should travel to the wizards tower and kill the skeleton", 8148);
			client.getPacketSender().sendString(
					"I should find the ghosts skull", 8149);
		} else if (client.restGhost == 4) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString("@str@I've found the skull",
					8147);
			client.getPacketSender().sendString(
					"@str@I killed the skeleton", 8148);
			client.getPacketSender().sendString(
					"I should travel back to the ghost", 8149);
		} else if (client.restGhost == 5) {
			client.getPacketSender().sendString("Restless Ghost", 8144);
			client.getPacketSender().sendString(
					"@str@I've set the skull in the coffin", 8147);
			client.getPacketSender().sendString(
					"@str@I've freed the ghost.", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendString(
					"As a reward, I gained 125 Prayer Exp.", 8151);
			client.getPacketSender().sendString("And 1 Quest Point", 8152);
			client.getPacketSender().sendString("", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
