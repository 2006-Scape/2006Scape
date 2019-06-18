package redone.game.content.random;

import java.awt.Point;
import java.util.Random;

import redone.Server;
import redone.game.objects.Objects;
import redone.game.players.Client;

public class Balloons extends Objects {

	static Random r = new Random();
	public static int item, amount;
	public static int x, y;

	@SuppressWarnings("static-access")
	public Balloons(int id, int x, int y, int height, int face, int type,
			int ticks, int item, int amount) {
		super(id, x, y, height, face, type, ticks);
		this.x = x;
		this.y = y;
		this.item = item;
		this.amount = amount;
	}

	public static void popBalloon(Client c, int x, int y) {
		PartyRoom.coords.remove(getCoords());
		Balloons empty = remove(x, y);
		Server.itemHandler.createGroundItem(c, item, x, y, amount, c.playerId);
		item = 0;
		amount = 0;
		Server.objectHandler.addObject(empty);
		Server.objectHandler.placeObject(empty);
		c.startAnimation(794);
	}

	public static Point getCoords() {
		return new Point(x, y);
	}

	public static Balloons getBalloon(int item, int amount) {
		return new Balloons(115 + r.nextInt(5), 2730 + r.nextInt(13),
				3462 + r.nextInt(13), 0, 0, 10, 0, item, amount);
	}

	public static Balloons getEmpty() {
		return new Balloons(115 + r.nextInt(5), 2730 + r.nextInt(13),
				3462 + r.nextInt(13), 0, 0, 10, 0, 0, 0);
	}

	public static Balloons remove(int x, int y) {
		return new Balloons(-1, x, y, 0, 0, 10, 0, 0, 0);
	}
}
