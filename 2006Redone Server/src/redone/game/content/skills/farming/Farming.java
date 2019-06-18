package redone.game.content.skills.farming;

import redone.game.players.Client;

/**
 * Farming
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class Farming {
	
	public static void openGuide(Client player, int objectType) {
		if (Flowers.isFlower(player, objectType)) {
			player.getSkillInterfaces().farmingComplex(6);
			player.getSkillInterfaces().selected = 20;
		} else if (Herbs.isHerb(player, objectType)) {
			player.getSkillInterfaces().farmingComplex(7);
			player.getSkillInterfaces().selected = 20;
		} else if (Allotments.isAllotment(player, objectType)) {
			player.getSkillInterfaces().farmingComplex(1);
			player.getSkillInterfaces().selected = 20;
		}
	}

}
