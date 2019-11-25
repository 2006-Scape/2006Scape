package com.rebotted.game.objects.impl;

import com.rebotted.GameEngine;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

/**
 * @author Andrew (Mr Extremez)
 */

public class Webs {

	public static int[] CLICKING_OBJECTS = { 733 };

	public static boolean webs(Player player, int object) {
		for (int element : CLICKING_OBJECTS) {
			if (object == element) {
				return true;
			}
		}
		return false;
	}

	public static void slashWeb(Player player, final int objectClickId, final int objectX, final int objectY) {
		if (System.currentTimeMillis() - player.webSlashDelay > 1800) {
			if (Misc.random(3) > 0) {
				GameEngine.objectHandler.createAnObject(-1, objectX, objectY);
				// c.startAnimation(451);
				player.startAnimation(player.getCombatAssistant().getWepAnim());
				player.webSlashDelay = System.currentTimeMillis();
				player.getPacketSender().sendSound(SoundList.SLASH_WEB, 100, 0);
				player.getPacketSender().sendMessage("You successfully slash open the web.");
			} else {
				player.getPacketSender().sendMessage("You fail to slash through the web.");
				return;
			}
		}
	}
}
