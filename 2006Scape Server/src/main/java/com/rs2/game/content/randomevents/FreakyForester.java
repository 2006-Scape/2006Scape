package com.rs2.game.content.randomevents;

import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class FreakyForester {

	public static void teleportToLocation(Player c) {
		c.lastX = c.absX;
		c.lastY = c.absY;
		c.lastH = c.heightLevel;
		c.teleportToX = 2602;
		c.teleportToY = 4775;
		c.heightLevel = 0;
		c.getPacketSender().sendMessage("Talk to the freaky forester to get out.");
	}

	private static String[] pheasant = { "one", "two", "three", "four", };

	public static String getPheasant(Player client) {
		if (client.getPheasent < 0) {
			client.getPheasent = Misc.random(3);
		}
		return pheasant[client.getPheasent] + " tailed";
	}

	public static void leaveArea(Player client) {
		if (client.killedPheasant[client.getPheasent]) {
			client.getPlayerAssistant().movePlayer(client.lastX, client.lastY, client.lastH);
			client.canLeaveArea = true;
			client.getPacketSender().sendMessage("Congratulations, you've completed the freaky forester event!");
			if (client.recievedReward == false) {
				client.getItemAssistant().addItem(6180, 1);
				client.getItemAssistant().addItem(6181, 1);
				client.getItemAssistant().addItem(6182, 1);
				client.recievedReward = true;
			} else {
				client.getItemAssistant().addItem(995, 500);
				client.getPacketSender().sendMessage("You have already beat the freaky forester event so you get 500 coins.");
			}
			int delete = client.getItemAssistant().getItemAmount(6178);
			client.getItemAssistant().deleteItem(6178, delete);
			client.randomActions = 0;
		} else {
			RandomEventHandler.failEvent(client);
		}
		for (int i = 0; i < 4; i++) {
			client.killedPheasant[i] = false;
		}
		client.getPheasent = -1;
		client.canLeaveArea = false;
	}

	public static void killedPheasant(Player c, int p) {
		for (int i = 0; i < 4; i++) {
			c.killedPheasant[i] = false;
		}
		c.killedPheasant[p] = true;
	}

	public static boolean hasKilledPheasant(Player client) {
		for (int i = 0; i < 4; i++) {
			if (client.killedPheasant[i]) {
				client.canLeaveArea = true;
				return true;
			}
		}
		client.canLeaveArea = false;
		return false;
	}
}
