package redone.game.content.randomevents;

import redone.game.players.Client;
import redone.util.Misc;

/**
 * Eventhandler class
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class RandomEventHandler {

	public static int CALL_RANDOM = 350 + Misc.random(100);

	public static void resetEvent(Client player) {
		player.randomActions = 0;
	}

	private static int[][] FAIL_COORDS = { { 3333, 3333 }, { 3196, 3193 },
			{ 3084, 3549 }, { 2974, 3346 }, { 2781, 3506 }, { 2810, 3508 }, };

	public static void failEvent(Client player) {
		int loc = Misc.random(FAIL_COORDS.length - 1);
		player.teleportToX = FAIL_COORDS[loc][0];
		player.teleportToY = FAIL_COORDS[loc][1];
		player.heightLevel = 0;
		player.getActionSender().sendMessage("You wake up in a strange location...");
		resetEvent(player);
		player.getPlayerAssistant().closeAllWindows();
	}

	public static void callRandom(Client player) { // add all random events here
		if (player.inFightCaves() || player.playerEquipment[player.playerWeapon] == 4024 || player.tutorialProgress < 36) {
			return;
		}
		int randomEvent = Misc.random(3);
		switch (randomEvent) {
		case 0:
			SandwhichLady.openSandwhichLady(player);
			resetEvent(player);
			break;
		case 1:
		if (player.chickenSpawned == false) {
			EvilChicken.spawnChicken(player);
			resetEvent(player);
		}
		break;
		case 2:
			FreakyForester.teleportToLocation(player);
			resetEvent(player);
		break;
		case 3:
			Swarm.spawnSwarm(player);
			resetEvent(player);
			break;

		default:
			System.out.println("Error no random event called for " + player.playerName + ".");
			break;
		}
	}

	public static void addRandom(Client player) {
		if (player.randomActions >= CALL_RANDOM) {
			callRandom(player);
			if (player.playerIsBusy() && !player.hasSandwhichLady) {
				player.getPlayerAssistant().closeAllWindows();
			}
		} else {
			int nextRandom = CALL_RANDOM - player.randomActions;
			if (player.playerRights == 3) {
				player.getActionSender().sendMessage("Next random will be in " + nextRandom + " more random actions.");
			}
			player.randomActions += 1;
		}
	}
}
