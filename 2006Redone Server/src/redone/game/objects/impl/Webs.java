package redone.game.objects.impl;

import redone.Server;
import redone.game.content.music.sound.SoundList;
import redone.game.players.Client;
import redone.util.Misc;

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
				Server.objectHandler.createAnObject(c, -1, objectX, objectY);
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
