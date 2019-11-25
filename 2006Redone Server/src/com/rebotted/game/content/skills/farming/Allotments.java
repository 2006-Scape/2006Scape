package com.rebotted.game.content.skills.farming;

import com.rebotted.game.players.Player;

/**
 * Allotments
 * @author Andrew (Mr Extremez)
 */

public class Allotments {
	
	
	private static final int[] ALLOTMENT = {8550, 8551, 8552, 8553};
	
	public static boolean isAllotment(Player player, int objectType) {
		for (int i = 0; i < ALLOTMENT.length; i++) {
			if (objectType == ALLOTMENT[i]) {
				return true;
			}
		}
		return false;
		
	}

}
