package com.rebotted.game.content.skills.farming;

import com.rebotted.game.players.Player;

/**
 * Herbs
 * @author Andrew (Mr Extremez)
 */

public class Herbs {
	
	private static final int[] HERB_PATCH = {8150, 8151};
	
	public static boolean isHerb(Player player, int objectType) {
		for (int i = 0; i < HERB_PATCH.length; i++) {
			if (objectType == HERB_PATCH[i]) {
				return true;
			}
		}
		return false;
		
	}

}
