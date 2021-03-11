package com.rs2.game.content.randomevents;

import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class EvilChicken {

	// min cmb, maxcmb, npcid, maxhit, attack, defence
	private static int[][] chicken = { { 3, 10, 2463, 19, 1, 10, 10 },
			{ 11, 20, 2464, 40, 1, 30, 30 }, { 21, 40, 2465, 60, 2, 60, 60 },
			{ 41, 60, 2466, 80, 3, 80, 80 },
			{ 61, 90, 2467, 105, 4, 100, 100 },
			{ 91, 138, 2468, 120, 5, 120, 120 }, };

	public static void spawnChicken(Player c) {
		for (int[] aChicken : chicken) {
			if (c.chickenSpawned == false) {
				if (c.combatLevel >= aChicken[0] && c.combatLevel <= aChicken[1]) {
					NpcHandler.spawnNpc(c, aChicken[2], c.absX + Misc.random(1), c.absY + Misc.random(1), c.heightLevel, 0, aChicken[3], aChicken[4], aChicken[5], aChicken[6], true, false);
					c.chickenSpawned = true;
					c.randomActions = 0;
				}
			}
		}
	}
}