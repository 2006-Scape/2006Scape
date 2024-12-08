package com.rs2.game.content.quests.impl;

import com.rs2.game.players.Player;

/**
 * @author Andrew (Mr Extremez)
 * Cooks Assistant
 */

public class CooksAssistant {

	private static final int EGG = 1944;
	private static final int MILK = 1927;
	private static final int FLOUR = 1933;

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
		client.getPacketSender().sendString("@dre@Cook's Assistant", 8144);
		client.getPacketSender().sendString("", 8145);
		if (client.cookAss == 0) {
			client.getPacketSender().sendString("Cook's Assistant", 8144);
			client.getPacketSender().sendString("I can start this quest by speaking to the Cook in the", 8147);
			client.getPacketSender().sendString("Lumbridge Castle kitchen.", 8148);
			client.getPacketSender().sendString("", 8149);
			client.getPacketSender().sendString("There are no minimum requirements.", 8150);
		} else if (client.cookAss == 1) {
			client.getPacketSender().sendString("Cook's Assistant", 8144);
			client.getPacketSender().sendString("@str@I've talked to the cook.", 8147);
			client.getPacketSender().sendString("He wants me to gather the following materials:", 8148);
			if (client.getItemAssistant().playerHasItem(EGG, 1)) {
				client.getPacketSender().sendString("@str@1 egg", 8149);
			} else {
				client.getPacketSender().sendString("@red@1 egg", 8149);
			}
			if (client.getItemAssistant().playerHasItem(MILK, 1)) {
				client.getPacketSender().sendString("@str@1 bucket of milk", 8150);
			} else {
				client.getPacketSender().sendString("@red@1 bucket of milk", 8150);
			}
			if (client.getItemAssistant().playerHasItem(FLOUR, 1)) {
				client.getPacketSender().sendString("@str@1 heap of flour", 8151);
			} else {
				client.getPacketSender().sendString("@red@1 pot of flour", 8151);
			}
		} else if (client.cookAss == 2) {
			client.getPacketSender().sendString("Cook's Assistant", 8144);
			client.getPacketSender().sendString("@str@I talked to the cook.", 8147);
			client.getPacketSender().sendString("@str@I gave the cook his items.", 8148);
			client.getPacketSender().sendString("I should go speak to the cook.", 8149);
		} else if (client.cookAss == 3) {
			client.getPacketSender().sendString("Cook's Assistant", 8144);
			client.getPacketSender().sendString("@str@I talked to the cook.", 8147);
			client.getPacketSender().sendString("@str@I gave him his items.", 8148);
			client.getPacketSender().sendString("@red@     QUEST COMPLETE", 8150);
			client.getPacketSender().sendString("As a reward, I gained 150 Cooking Experience.", 8151);
		}
		client.getPacketSender().showInterface(8134);
	}

}
