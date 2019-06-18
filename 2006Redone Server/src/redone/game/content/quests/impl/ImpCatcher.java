package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Imp Catcher
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class ImpCatcher {

	public Client client;

	public ImpCatcher(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Imp Catcher", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.impsC == 0) {
			client.getPlayerAssistant()
					.sendFrame126(
							"I can start this quest by speaking to Wizard Mizgog who is",
							8147);
			client.getPlayerAssistant().sendFrame126("in the Wizard's Tower.",
					8148);
		} else if (client.impsC == 1) {
			client.getPlayerAssistant()
					.sendFrame126(
							"@str@I can start this quest by speaking to Wizard Mizgog who is",
							8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@in the Wizard's Tower.", 8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant().sendFrame126(
					"Wizard Mizgog have asked you to get the following items:",
					8150);
			client.getPlayerAssistant().sendFrame126("Red bead", 8151);
			client.getPlayerAssistant().sendFrame126("Yellow bead", 8152);
			client.getPlayerAssistant().sendFrame126("Black bead", 8153);
			client.getPlayerAssistant().sendFrame126("White bead", 8154);
		} else if (client.impsC == 2) {
			client.getPlayerAssistant()
					.sendFrame126(
							"@str@I can start this quest by speaking to Wizard Mizgog who is",
							8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@in the Wizard's Tower.", 8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant()
					.sendFrame126(
							"@str@Wizard Mizgog have asked you to get the following items:",
							8150);
			client.getPlayerAssistant().sendFrame126("@str@Red bead", 8151);
			client.getPlayerAssistant().sendFrame126("@str@Yellow bead", 8152);
			client.getPlayerAssistant().sendFrame126("@str@Black bead", 8153);
			client.getPlayerAssistant().sendFrame126("@str@White bead", 8154);
			client.getPlayerAssistant().sendFrame126("", 8155);
			client.getPlayerAssistant().sendFrame126(
					"You have completed this quest!", 8156);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
