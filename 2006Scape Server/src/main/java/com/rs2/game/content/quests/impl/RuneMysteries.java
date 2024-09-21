package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * Rune Mysteries
 * @author Andrew (Mr Extremez)
 */

public class RuneMysteries {
		
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
		client.getPacketSender().sendString("@dre@Rune Mysteries", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.runeMist == 0) {
			client.getPacketSender().sendString("Rune Mysteries", 8144);
			client.getPacketSender().sendString(
					"I can start this quest by speaking to Duke Horiaco", 8147);
			client.getPacketSender().sendString(
					"who is located on the 2nd floor of the Lumbridge Castle.",
					8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString(
					"There are no minimum requirments.", 8150);
		} else if (client.runeMist == 1) {
			client.getPacketSender().sendString("Rune Mysteries", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to the duke", 8147);
			client.getPacketSender().sendString(
					"I should take the talisman to the Head Wizard.", 8148);
		} else if (client.runeMist == 2) {
			client.getPacketSender().sendString("Rune Mysteries", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Sedridor", 8147);
			client.getPacketSender().sendString(
					"@str@I gave him the talisman", 8148);
			client.getPacketSender().sendString(
					"I should bring the notes to Aubury.", 8149);
		} else if (client.runeMist == 3) {
			client.getPacketSender().sendString("Rune Mysteries", 8144);
			client.getPacketSender().sendString(
					"@str@I've talked to Aubury.", 8147);
			client.getPacketSender().sendString("@str@I gave him the notes",
					8148);
			client.getPacketSender().sendString(
					"I should go back to the wizard tower", 8149);
		} else if (client.runeMist == 4) {
			client.getPacketSender().sendString("Rune Mysteries", 8144);
			client.getPacketSender().sendString("@str@I talked to Sedridor",
					8147);
			client.getPacketSender().sendString(
					"@str@I gave him his items.", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE",
					8150);
			client.getPacketSender().sendString(
					"As a reward, I gained 1 Quest point", 8151);
			client.getPacketSender().sendString("And an air talisman.", 8152);
		}
		client.getPacketSender().showInterface(8134);
	}

}
