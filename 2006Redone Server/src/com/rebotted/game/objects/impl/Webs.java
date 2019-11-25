package com.rebotted.game.objects.impl;

import com.rebotted.GameEngine;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.players.Client;
import com.rebotted.util.Misc;

/**
 * @author Andrew
 */

public class Webs {

	public static int[] CLICKING_OBJECTS = { 733 };

	public static boolean webs(Client c, int object) {
		for (int element : CLICKING_OBJECTS) {
			if (object == element) {
				return true;
			}
		}
		return false;
	}

	public static void slashWeb(Client c, final int objectClickId,
			final int objectX, final int objectY) {
		if (System.currentTimeMillis() - c.webSlashDelay > 1800) {
			if (Misc.random(3) > 0) {
				GameEngine.objectHandler.createAnObject(c, -1, objectX, objectY);
				// c.startAnimation(451);
				c.startAnimation(c.getCombatAssistant().getWepAnim());
				c.webSlashDelay = System.currentTimeMillis();
				c.getActionSender().sendSound(SoundList.SLASH_WEB, 100, 0);
				c.getActionSender().sendMessage("You successfully slash open the web.");
			} else {
				c.getActionSender().sendMessage("You fail to slash through the web.");
				return;
			}
		}
	}
}
