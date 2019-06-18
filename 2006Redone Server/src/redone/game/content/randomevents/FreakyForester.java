package redone.game.content.randomevents;

import redone.game.players.Client;
import redone.util.Misc;

public class FreakyForester {

	public static void teleportToLocation(Client client) {
		client.lastX = client.absX;
		client.lastY = client.absY;
		client.lastH = client.heightLevel;
		client.teleportToX = 2602;
		client.teleportToY = 4775;
		client.heightLevel = 0;
		client.getActionSender().sendMessage("Talk to the freaky forester to get out.");
	}

	private static String[] pheasant = { "one", "two", "three", "four", };

	public static String getPheasant(Client client) {
		if (client.getPheasent < 0) {
			client.getPheasent = Misc.random(3);
		}
		return pheasant[client.getPheasent] + " tailed";
	}

	public static void leaveArea(Client client) {
		if (client.killedPheasant[client.getPheasent]) {
			client.getPlayerAssistant().movePlayer(client.lastX, client.lastY, client.lastH);
			client.canLeaveArea = true;
			client.getActionSender().sendMessage("Congratulations, you've completed the freaky forester event!");
			if (client.recievedReward == false) {
				client.getItemAssistant().addItem(6180, 1);
				client.getItemAssistant().addItem(6181, 1);
				client.getItemAssistant().addItem(6182, 1);
				client.recievedReward = true;
			} else {
				client.getItemAssistant().addItem(995, 500);
				client.getActionSender().sendMessage("You have already beat the freaky forester event so you get 500 coins.");
			}
			int delete = client.getItemAssistant().getItemCount(6178);
			client.getItemAssistant().deleteItem2(6178, delete);
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

	public static void killedPheasant(Client client, int p) {
		for (int i = 0; i < 4; i++) {
			client.killedPheasant[i] = false;
		}
		client.killedPheasant[p] = true;
	}

	public static boolean hasKilledPheasant(Client client) {
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
