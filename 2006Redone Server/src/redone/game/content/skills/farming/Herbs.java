package redone.game.content.skills.farming;

import redone.game.players.Client;

/**
 * Herbs
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class Herbs {
	
	private static final int[] HERB_PATCH = {8150, 8151};
	
	public static boolean isHerb(Client player, int objectType) {
		for (int i = 0; i < HERB_PATCH.length; i++) {
			if (objectType == HERB_PATCH[i]) {
				return true;
			}
		}
		return false;
		
	}

}
