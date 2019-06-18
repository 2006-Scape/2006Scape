package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class EvilChicken {

	// min cmb, maxcmb, npcid, maxhit, attack, defence
	private static int[][] chicken = { { 3, 10, 2463, 19, 1, 10, 10 },
			{ 11, 20, 2464, 40, 1, 30, 30 }, { 21, 40, 2465, 60, 2, 60, 60 },
			{ 41, 60, 2466, 80, 3, 80, 80 },
			{ 61, 90, 2467, 105, 4, 100, 100 },
			{ 91, 138, 2468, 120, 5, 120, 120 }, };

	public static void spawnChicken(Client player) {
		for (int[] aChicken : chicken) {
			if (player.chickenSpawned == false) {
				if (player.combatLevel >= aChicken[0] && player.combatLevel <= aChicken[1]) {
					NpcHandler.spawnNpc(player, aChicken[2], player.absX + Misc.random(1), player.absY + Misc.random(1), player.heightLevel, 0, aChicken[3], aChicken[4], aChicken[5], aChicken[6], true, false);
					player.chickenSpawned = true;
					player.randomActions = 0;
				}
			}
		}
	}
}