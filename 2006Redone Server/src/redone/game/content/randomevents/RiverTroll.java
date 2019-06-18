package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class RiverTroll {
	
	public static boolean hasRiverTroll = false;

	private static int[][] riverTroll = { { 3, 10, 391, 19, 1 },
			{ 11, 20, 392, 40, 1 }, { 21, 40, 393, 80, 3 },
			{ 61, 90, 394, 105, 4 }, { 91, 110, 395, 120, 5 },
			{ 111, 138, 396, 150, 7 }, };

	public static void spawnRiverTroll(Client c) {
		for (int[] element : riverTroll) {
			if (hasRiverTroll == false) {
			if (c.combatLevel >= element[0] && c.combatLevel <= element[1] && hasRiverTroll == false) {
				NpcHandler.spawnNpc(c, element[2], c.absX + Misc.random(1), c.absY + Misc.random(1), c.heightLevel, 0, element[3], element[4], c.playerLevel[c.playerAttack] * 2, c.playerLevel[c.playerDefence] * 2, true, false);
					c.randomActions = 0;
					hasRiverTroll = true;
					NpcHandler.npcs[element[2]].forceChat("Fishies be mine! Leave dem fishies!");
				}
			}
		}
	}
}