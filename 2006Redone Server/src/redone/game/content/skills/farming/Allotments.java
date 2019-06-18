package redone.game.content.skills.farming;

import redone.game.players.Client;

/**
 * Allotments
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class Allotments {
	
	
	private static final int[] ALLOTMENT = {8550, 8551, 8552, 8553};
	
	public static boolean isAllotment(Client player, int objectType) {
		for (int i = 0; i < ALLOTMENT.length; i++) {
			if (objectType == ALLOTMENT[i]) {
				return true;
			}
		}
		return false;
		
	}

}
