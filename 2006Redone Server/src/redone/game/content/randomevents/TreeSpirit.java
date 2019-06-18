package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class TreeSpirit {

	private static int[][] treeSpirit = {
			// combat, combat 2, npcid, hitpoints, levels
			{ 3, 10, 438, 28, 1 }, { 11, 20, 439, 36, 1 },
			{ 21, 40, 440, 57, 3 }, { 61, 90, 441, 90, 4 },
			{ 91, 110, 442, 130, 5 }, { 111, 138, 443, 160, 7 }, };
	
	private static int checkStats(Client client) {
		return client.getPlayerAssistant().getLevelForXP(client.playerXP[client.playerHitpoints]) * 2;
	}
	
	public static void spawnTreeSpirit(Client c) {
		for (int[] element : treeSpirit) {
			if (c.treeSpiritSpawned == false) {
			if (c.combatLevel >= element[0] && c.combatLevel <= element[1]) {
				NpcHandler.spawnNpc(c, element[2], c.absX + Misc.random(1),
						c.absY + Misc.random(1), c.heightLevel, 0, element[3],
						element[4], checkStats(c), c.playerLevel[c.playerDefence] * 2, true,
						false);
				NpcHandler.npcs[element[2]]
						.forceChat("Leave these woods and never return!");
				c.treeSpiritSpawned = true;
				c.randomActions = 0;
				}
			}
		}
	}
}