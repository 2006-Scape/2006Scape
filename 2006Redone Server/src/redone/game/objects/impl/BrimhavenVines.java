package redone.game.objects.impl;

import redone.game.content.skills.core.Woodcutting;
import redone.game.players.Client;

/**
 * @author Genesis
 */
public class BrimhavenVines {

	public static void handleBrimhavenVines(Client c, int objectType) {
		if (!Woodcutting.hasAxe(c)) {
			c.getActionSender().sendMessage("You will need an axe to chop through these!");
			return;
		}
		c.getActionSender().sendMessage("You chop your way through the vines.");
		switch (objectType) {
		case 12987:
		case 12986:
			BrimhavenVines.moveThroughVinesX(c, 3213, -2, 0, 2, 0);
			break;
		case 5103:
			BrimhavenVines.moveThroughVinesX(c, 2689, 2, 0, -2, 0);
			break;
		case 5104:
			BrimhavenVines.moveThroughVinesY(c, 9568, 0, 2, 0, -2);
			break;
		case 5105:
			BrimhavenVines.moveThroughVinesX(c, 2672, 2, 0, -2, 0);
			break;
		case 5106:
			BrimhavenVines.moveThroughVinesX(c, 2675, 2, 0, -2, 0);
			break;
		case 5107:
			BrimhavenVines.moveThroughVinesX(c, 2694, 2, 0, -2, 0);
			break;
		}
	}

	public static void moveThroughVinesX(Client c, int originX, int x1, int y1, int x2, int y2) {
		int x = c.getX();
		int y = c.getY();
		if (c.absX <= originX) {
			c.getPlayerAssistant().movePlayer(x + x1, y + y1, 0);
		} else {
			c.getPlayerAssistant().movePlayer(x + x2, y + y2, 0);
		}
	}

	public static void moveThroughVinesY(Client c, int originY, int x1, int y1, int x2, int y2) {
		int x = c.getX();
		int y = c.getY();
		if (c.absY <= originY) {
			c.getPlayerAssistant().movePlayer(x + x1, y + y1, 0);
		} else {
			c.getPlayerAssistant().movePlayer(x + x2, y + y2, 0);
		}
	}

}
