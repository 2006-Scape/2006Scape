package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * Frog Event
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public final class Frog {

	private static final int[][] FROG_DATA = { { 2469, 2470, 2471, 2472, 2473 } };

	public static void startEvent(Client client) {
		spawnFrogs(client);
		spawnBasedOnGender(client);
		client.ignoreFrog = false;
	}

	public static void finishEvent(Client client) {// breaks
		if (client.ignoreFrog) {
			client.getPlayerAssistant().movePlayer(client.lastX, client.lastY, client.lastH);
		}
		giveReward(client);
	}

	public static void spawnBasedOnGender(Client client) {// ternary
		NpcHandler.spawnNpc(client, client.playerAppearance[0] == 1 ? 2474 : 2475, client.absX + Misc.random(1), client.absY + Misc.random(1), 0, 0, 0, 0, 0, 0, false, false);
	}

	public static void ignoreFrogs(Client client) {
		String type = client.playerMagicBook == 0 ? "modern" : "ancient";
		client.getPlayerAssistant().startTeleport(6861, 5834, 0, type);
		spawnFrogs(client);
		spawnBasedOnGender(client);
		client.ignoreFrog = true;
	}

	public static void giveReward(Client client) {
		client.getItemAssistant().addItem(6183, 1);
	}

	public static void spawnFrogs(Client client) {
		for (int[] element : FROG_DATA) {
			NpcHandler.spawnNpc(client, element[0], client.absX + Misc.random(1), client.absY + Misc.random(1), 0, 0, 0, 0, 0, 0, false, false);
		}
	}
}
