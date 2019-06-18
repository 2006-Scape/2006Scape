package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class RockGolem {

	public static int[][] rockGolem = {
		{3, 	10, 	413, 	19, 	1},
		{11, 	20, 	414, 	40, 	1},
		{21, 	40, 	415, 	80, 	3},
		{61, 	90, 	416, 	105, 	4},
		{91, 	110, 	417, 	120, 	5},
		{111, 	138, 	418, 	150, 	7},
	};

	public static void spawnRockGolem(Client c) {
		for (int[] aRockGolem : rockGolem) {
			if(!c.golemSpawned) {
				if (c.combatLevel >= aRockGolem[0] && c.combatLevel <= aRockGolem[1]) {
					NpcHandler.spawnNpc(c, aRockGolem[2], c.getX() + Misc.random(1), c.getY() + Misc.random(1), c.heightLevel, 0, aRockGolem[3], aRockGolem[4], aRockGolem[4] * 10, aRockGolem[4] * 10, true, false);
					c.golemSpawned = true;
					NpcHandler.npcs[aRockGolem[2]].forceChat("Raarrrgghh! Flee human!");
				}
			}
		}
	}

}
