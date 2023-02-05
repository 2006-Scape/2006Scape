package com.rs2.game.content.skills.fletching;

import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

/**
 * @author Tom
 */

public class LogCuttingInterface {

	public int log;

	public static void handleLog(Player c, int item1, int item2) {
		if (item1 == 946) {
			fletchInterface(c, item2);
		} else {
			fletchInterface(c, item1);
		}
	}

	public static void fletchInterface(Player c, int item) {
		if (c.playerIsFletching) {
			c.playerIsFletching = false;
			return;
		}
		if (item < 1510 || item > 1521) {
			c.playerIsFletching = false;
			c.getPacketSender().sendMessage("Nothing interesting happens.");
			return;
		}
		c.getFletching().log = item;
		if (item == 1511) {
			c.getPacketSender().sendChatInterface(8880);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8883, 180, 52); // left
			c.getPacketSender().sendFrame246(8884, 180, 50); // middle
			c.getPacketSender().sendFrame246(8885, 180, 48); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(52), 8889);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(50), 8893);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(48), 8897);
		} else if (item == 1521) {
			c.getPacketSender().sendChatInterface(8866);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8869, 180, 54); // left
			c.getPacketSender().sendFrame246(8870, 180, 56); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(54), 8874);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(56), 8878);
		} else if (item == 1519) {
			c.getPacketSender().sendChatInterface(8866);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8869, 180, 60); // left
			c.getPacketSender().sendFrame246(8870, 180, 58); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(60), 8874);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(58), 8878);
		} else if (item == 1517) {
			c.getPacketSender().sendChatInterface(8866);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8869, 180, 64); // left
			c.getPacketSender().sendFrame246(8870, 180, 62); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(64), 8874);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(62), 8878);
		} else if (item == 1515) {
			c.getPacketSender().sendChatInterface(8866);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8869, 180, 68); // left
			c.getPacketSender().sendFrame246(8870, 180, 66); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(68), 8874);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(66), 8878);
		} else if (item == 1513) {
			c.getPacketSender().sendChatInterface(8866);
			c.getPacketSender().sendString("What would you like to make?", 8879);
			c.getPacketSender().sendFrame246(8869, 180, 72); // left
			c.getPacketSender().sendFrame246(8870, 180, 70); // right
			c.getPacketSender().sendString(DeprecatedItems.getItemName(72), 8874);
			c.getPacketSender().sendString(DeprecatedItems.getItemName(70), 8878);
		}
	}

	public static void handleItemOnItem(Player player, int itemUsed, int useWith) {
		if (itemUsed == 946 || useWith == 946) {
			handleLog(player, itemUsed, useWith);
		}
	}
}
