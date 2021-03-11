package com.rs2.game.content.randomevents;

import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class Shade {

	private static int[][] shade = { { 3, 10, 425, 19, 1 },
			{ 11, 20, 426, 40, 3 }, { 21, 40, 427, 60, 5 },
			{ 41, 60, 428, 80, 8 }, { 61, 90, 429, 105, 11 },
			{ 91, 138, 430, 120, 13 }, };

	public static void spawnShade(Player c) {
		for (int[] element : shade) {
			if (c.shadeSpawned == false) {
			if (c.combatLevel >= element[0]
					&& c.combatLevel <= element[1]) {
				NpcHandler
						.spawnNpc(c, element[2],
								c.absX + Misc.random(1), c.absY
										+ Misc.random(1), c.heightLevel,
								0,
								element[3], // HP
								element[4], // maxhit
								(int) (NpcHandler.getNpcListCombat(element[3]) * 1.5), // defence
								(int) (NpcHandler.getNpcListCombat(element[3]) * 1.5),
								true, false); // attack
				c.randomActions = 0;
				c.shadeSpawned = true;
				}
			}
		}
	}
}