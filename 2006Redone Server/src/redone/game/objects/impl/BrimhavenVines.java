package redone.game.objects.impl;

import redone.game.players.Client;

/**
 * @author Genesis
 */
public class BrimhavenVines {

	public static void handleBrimhavenVines(Client c, int objectType) {
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

	public static void moveThroughVinesX(Client c, int originX, int x1, int y1,
			int x2, int y2) {
		if (c.absX <= originX) {
			c.getPlayerAssistant().walkTo(x1, y1);
		} else {
			c.getPlayerAssistant().walkTo(x2, y2);
		}
	}

	public static void moveThroughVinesY(Client c, int originY, int x1, int y1,
			int x2, int y2) {
		if (c.absY <= originY) {
			c.getPlayerAssistant().walkTo(x1, y1);
		} else {
			c.getPlayerAssistant().walkTo(x2, y2);
		}
	}

}
