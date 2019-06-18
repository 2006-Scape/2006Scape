package redone.game.content.quests.impl;

import redone.game.players.Client;

/**
 * Rune Mysteries
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class RuneMysteries {

	Client client;

	public RuneMysteries(Client client) {
		this.client = client;
	}

	public void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			client.getPlayerAssistant().sendFrame126("", i);
		}
		client.getPlayerAssistant().sendFrame126("@dre@Rune Mysteries", 8144);
		client.getPlayerAssistant().sendFrame126("", 8145);
		if (client.runeMist == 0) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 8144);
			client.getPlayerAssistant().sendFrame126(
					"I can start this quest by speaking to Duke Horiaco", 8147);
			client.getPlayerAssistant().sendFrame126(
					"who is located on the 2nd floor of the Lumbridge Castle.",
					8148);
			client.getPlayerAssistant().sendFrame126("", 8149);
			client.getPlayerAssistant().sendFrame126(
					"There are no minimum requirments.", 8150);
		} else if (client.runeMist == 1) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to the duke", 8147);
			client.getPlayerAssistant().sendFrame126(
					"I should take the talisman to the Head Wizard.", 8148);
		} else if (client.runeMist == 2) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Sedridor", 8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I gave him the talisman", 8148);
			client.getPlayerAssistant().sendFrame126(
					"I should bring the notes to Aubury.", 8149);
		} else if (client.runeMist == 3) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 8144);
			client.getPlayerAssistant().sendFrame126(
					"@str@I've talked to Aubury.", 8147);
			client.getPlayerAssistant().sendFrame126("@str@I gave him the notes",
					8148);
			client.getPlayerAssistant().sendFrame126(
					"I should go back to the wizard tower", 8149);
		} else if (client.runeMist == 4) {
			client.getPlayerAssistant().sendFrame126("Rune Mysteries", 8144);
			client.getPlayerAssistant().sendFrame126("@str@I talked to Sedridor",
					8147);
			client.getPlayerAssistant().sendFrame126(
					"@str@I gave him his items.", 8148);
			client.getPlayerAssistant().sendFrame126("@red@     QUEST COMPLETE",
					8150);
			client.getPlayerAssistant().sendFrame126(
					"As a reward, I gained 1 Quest point", 8151);
			client.getPlayerAssistant()
					.sendFrame126("And an air talisman.", 8152);
		}
		client.getPlayerAssistant().showInterface(8134);
	}

}
