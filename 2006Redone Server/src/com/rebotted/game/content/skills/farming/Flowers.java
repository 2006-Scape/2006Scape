package com.rebotted.game.content.skills.farming;

import com.rebotted.game.players.Player;


/**
 * Flowers
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class Flowers {
	
	private static final int[] FLOWER_PATCH = {7847, 7848};
	
	public static boolean isFlower(Player player, int objectType) {
		for (int i = 0; i < FLOWER_PATCH.length; i++) {
			if (objectType == FLOWER_PATCH[i]) {
				return true;
			}
		}
		return false;
	}

}
