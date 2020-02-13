package com.rebotted.game.objects.impl;

import com.rebotted.game.content.skills.woodcutting.Woodcutting;
import com.rebotted.game.players.Player;

/**
 * @author Genesis
 */

public class BrimhavenVines {

	public static void handleBrimhavenVines(Player player, int objectType) {
		if (!Woodcutting.hasAxe(player)) {
			player.getPacketSender().sendMessage("You will need an axe to chop through these!");
			return;
		}
		player.getPacketSender().sendMessage("You chop your way through the vines.");
		switch (objectType) {
		case 12987:
		case 12986:
			BrimhavenVines.moveThroughVinesX(player, 3213, -2, 0, 2, 0);
			break;
		case 5103:
			BrimhavenVines.moveThroughVinesX(player, 2689, 2, 0, -2, 0);
			break;
		case 5104:
			BrimhavenVines.moveThroughVinesY(player, 9568, 0, 2, 0, -2);
			break;
		case 5105:
			BrimhavenVines.moveThroughVinesX(player, 2672, 2, 0, -2, 0);
			break;
		case 5106:
			BrimhavenVines.moveThroughVinesX(player, 2675, 2, 0, -2, 0);
			break;
		case 5107:
			BrimhavenVines.moveThroughVinesX(player, 2694, 2, 0, -2, 0);
			break;
		}
	}

	public static void moveThroughVinesX(Player player, int originX, int x1, int y1, int x2, int y2) {
		int x = player.getX();
		int y = player.getY();
		if (player.absX <= originX) {
			player.getPlayerAssistant().movePlayer(x + x1, y + y1, 0);
		} else {
			player.getPlayerAssistant().movePlayer(x + x2, y + y2, 0);
		}
	}

	public static void moveThroughVinesY(Player player, int originY, int x1, int y1, int x2, int y2) {
		int x = player.getX();
		int y = player.getY();
		if (player.absY <= originY) {
			player.getPlayerAssistant().movePlayer(x + x1, y + y1, 0);
		} else {
			player.getPlayerAssistant().movePlayer(x + x2, y + y2, 0);
		}
	}

}
