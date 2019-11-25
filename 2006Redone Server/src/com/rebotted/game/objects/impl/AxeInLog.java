package com.rebotted.game.objects.impl;

import com.rebotted.game.objects.Object;
import com.rebotted.game.players.Client;

public class AxeInLog {

	public static void pullAxeFromLog(Client client, int x, int y) {
		if (client.getItemAssistant().freeSlots() <= 0) {
			client.getPacketSender().sendMessage("Not enough space in your inventory.");
			return;
		}
		client.startAnimation(832);
		client.getItemAssistant().addItem(1351, 1);
		client.getPacketSender().sendMessage("You take the axe from the log.");
		new Object(5582, x, y, client.heightLevel, 2, 10, 5581, 100);
	}

}
