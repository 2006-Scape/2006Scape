package redone.game.content.randomevents;

import redone.game.players.Client;
import redone.util.Misc;

public class SandwhichLady {
	
	private static int itemType;
	
	public static void handleOptions(Client player, int actionbuttonId) {
		if (player.hasSandwhichLady) {
		switch (actionbuttonId) {
		case 63013:
			if (itemType == 0) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(2323, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63014:
			if (itemType == 1) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(1971, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63015:
			if (itemType == 2) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(1973, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63009:
			if (itemType == 3) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(6961, 10);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63010:
			if (itemType == 4) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(6962, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63011:
			if (itemType == 5) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(6965, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage(
						"You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
			break;

		case 63012:
			if (itemType == 6) {
				player.getPlayerAssistant().closeAllWindows();
				player.getItemAssistant().addItem(2309, 1);
				player.getActionSender()
						.sendMessage(
								"Congratulations, you have completed the random event!");
			} else {
				player.getActionSender().sendMessage("You have chosen the wrong item!");
				RandomEventHandler.failEvent(player);
			}
			player.hasSandwhichLady = false;
		break;
		}
	} else if (player.hasSandwhichLady == false && actionbuttonId > 63008 && actionbuttonId < 63116) {
			player.getActionSender().sendMessage("You have improperly opened the sandwhich lady interface.");
		}
	}

	public static void openSandwhichLady(Client player) {
		player.hasSandwhichLady = true;
		player.getPlayerAssistant().sendFrame126(" ", 16131);
		player.getPlayerAssistant().showInterface(16135);
		int randomMessage = Misc.random(6);
		switch (randomMessage) {
		case 0:
			player.getPlayerAssistant().sendFrame126("Please select the pie.",
					16145);
			itemType = 0;
			break;
		case 1:
			player.getPlayerAssistant().sendFrame126("Please select the kebab.",
					16145);
			itemType = 1;
			break;
		case 2:
			player.getPlayerAssistant().sendFrame126(
					"Please select the chocolate.", 16145);
			itemType = 2;
			break;
		case 3:
			player.getPlayerAssistant().sendFrame126("Please select the bagel.",
					16145);
			itemType = 3;
			break;
		case 4:
			player.getPlayerAssistant().sendFrame126(
					"Please select the triangle sandwich.", 16145);
			itemType = 4;
			break;
		case 5:
			player.getPlayerAssistant().sendFrame126(
					"Please select the square sandwich.", 16145);
			itemType = 5;
			break;
		case 6:
			player.getPlayerAssistant().sendFrame126("Please select the bread.",
					16145);
			itemType = 6;
			break;
		}
	}
}
