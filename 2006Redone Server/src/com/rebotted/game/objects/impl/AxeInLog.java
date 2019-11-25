package com.rebotted.game.objects.impl;

import com.rebotted.game.objects.Object;
import com.rebotted.game.players.Player;

public class AxeInLog {

	public static void pullAxeFromLog(Player player, int x, int y) {
		if (player.getItemAssistant().freeSlots() <= 0) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return;
		}
		player.startAnimation(832);
		player.getItemAssistant().addItem(1351, 1);
		player.getPacketSender().sendMessage("You take the axe from the log.");
		new Object(5582, x, y, player.heightLevel, 2, 10, 5581, 100);
	}

}
