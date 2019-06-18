package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 * Cooks Assistant
 */

public class CooksAssistant {

	Client client;

	public CooksAssistant(Client client) {
		this.client = client;
	}

	private static final int EGG = 1944;
	private static final int MILK = 1927;
	private static final int FLOUR = 1933;

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Cook's Assistant", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.cookAss == 0) {
			client.getPlayerAssistant().sendFrame126("Cook's Assistant", 8144);
			client.getPlayerAssistant().sendFrame126("I can start this quest by speaking to the Cook in the", 8147);
			client.getPlayerAssistant().sendFrame126("Lumbridge Castle kitchen.", 8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant().sendFrame126("There are no minimum requirements.", 8150);
		} else if (client.cookAss == 1) {
			client.getPlayerAssistant().sendFrame126("Cook's Assistant", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I've talked to the cook.", 8147);
			client.getPlayerAssistant().sendFrame126("He wants me to gather the following materials:", 8148);
			if (client.getItemAssistant().playerHasItem(EGG, 1)) {
				client.getPlayerAssistant().sendFrame126("@str@1 egg", 8149);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@1 egg", 8149);
			}
			if (client.getItemAssistant().playerHasItem(MILK, 1)) {
				client.getPlayerAssistant().sendFrame126("@str@1 bucket of milk", 8150);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@1 bucket of milk", 8150);
			}
			if (client.getItemAssistant().playerHasItem(FLOUR, 1)) {
				client.getPlayerAssistant().sendFrame126("@str@1 heap of flour", 8151);
			} else {
				client.getPlayerAssistant().sendFrame126("@red@1 pot of flour", 8151);
			}
		} else if (client.cookAss == 2) {
			client.getPlayerAssistant().sendFrame126("Cook's Assistant", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I talked to the cook.", 8147);
			client.getPlayerAssistant().sendFrame126("@str@I gave the cook his items.", 8148);
			client.getPlayerAssistant().sendFrame126("I should go speak to the cook.", 8149);
		} else if (client.cookAss == 3) {
			client.getPlayerAssistant().sendFrame126("Cook's Assistant", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I talked to the cook.", 8147);
			client.getPlayerAssistant().sendFrame126("@str@I gave him his items.", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE", 8150);
			client.getPlayerAssistant().sendFrame126("As a reward, I gained 150 Cooking Experience.", 8151);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
