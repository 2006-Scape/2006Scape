package redone.game.content.skills.fletching;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Tom
 */

public class LogCuttingInterface {

	public static int log;

	public static void handleLog(Client c, int item1, int item2) {
		if (item1 == 946) {
			Interface(c, item2);
		} else {
			Interface(c, item1);
		}
	}

	public static void Interface(Client c, int item) {
		if (c.playerIsFletching == true && item > 1510 && item < 1522) {
			c.getActionSender().sendMessage("You are already fletching!");
			c.getPlayerAssistant().closeAllWindows();
			return;
		} else if (item < 1510 && item > 1521) {
			c.playerIsFletching = false;
			c.getActionSender().sendMessage("Nothing interesting happens.");
		}
		log = item;
		if (item == 1511) {
			c.getPlayerAssistant().sendChatInterface(8880);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8883, 180, 52); // left
			c.getPlayerAssistant().sendFrame246(8884, 180, 50); // middle
			c.getPlayerAssistant().sendFrame246(8885, 180, 48); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(52),
					8889);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(50),
					8893);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(48),
					8897);
		} else if (item == 1521) {
			c.getPlayerAssistant().sendChatInterface(8866);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8869, 180, 54); // left
			c.getPlayerAssistant().sendFrame246(8870, 180, 56); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(54),
					8874);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(56),
					8878);
		} else if (item == 1519) {
			c.getPlayerAssistant().sendChatInterface(8866);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8869, 180, 60); // left
			c.getPlayerAssistant().sendFrame246(8870, 180, 58); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(60),
					8874);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(58),
					8878);
		} else if (item == 1517) {
			c.getPlayerAssistant().sendChatInterface(8866);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8869, 180, 64); // left
			c.getPlayerAssistant().sendFrame246(8870, 180, 62); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(64),
					8874);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(62),
					8878);
		} else if (item == 1515) {
			c.getPlayerAssistant().sendChatInterface(8866);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8869, 180, 68); // left
			c.getPlayerAssistant().sendFrame246(8870, 180, 66); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(68),
					8874);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(66),
					8878);
		} else if (item == 1513) {
			c.getPlayerAssistant().sendChatInterface(8866);
			c.getPlayerAssistant().sendFrame126("What would you like to make?",
					8879);
			c.getPlayerAssistant().sendFrame246(8869, 180, 72); // left
			c.getPlayerAssistant().sendFrame246(8870, 180, 70); // right
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(72),
					8874);
			c.getPlayerAssistant().sendFrame126(ItemAssistant.getItemName(70),
					8878);
		}
		c.playerIsFletching = true;
	}

	public static void handleItemOnItem(Client c, int itemUsed, int useWith) {

		if (itemUsed == 946 || useWith == 946) {
			handleLog(c, itemUsed, useWith);
		}
	}
}
