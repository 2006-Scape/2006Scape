package com.rs2.game.content.random;

import java.util.Random;
import com.rs2.GameEngine;
import com.rs2.game.objects.Objects;
import com.rs2.game.players.Player;

public class Balloons extends Objects {

	static Random r = new Random();
	public static int minID = 115;
	public static int maxID = 120;
	public int item, amount;

	public Balloons(int x, int y, int height, int face, int type, int ticks, int item, int amount) {
		super(getRandomBalloon(), x, y, height, face, type, ticks);
		GameEngine.objectHandler.addObject(this);
		GameEngine.objectHandler.placeObject(this);
		this.item = item;
		this.amount = amount;
	}

	public static Balloons getBalloon(int x, int y, int height, int item, int amount) {
		return new Balloons(x, y, 0, 0, 10, 0, item, amount);
	}

	public static Balloons getBalloon(int x, int y, int item, int amount) {
		return getBalloon(x, y, 0, item, amount);
	}

	public static Balloons getEmpty(int x, int y, int height) {
		return getBalloon(x, y, height, 0, 0);
	}

	public static Balloons getEmpty(int x, int y) {
		return getEmpty(x, y, 0);
	}

	public void popBalloon(Player player) {
		remove();
		if (item > 0 && amount > 0) {
			GameEngine.itemHandler.createGroundItem(player, item, objectX, objectY, amount, player.playerId);
			item = 0;
			amount = 0;
		}
		player.startAnimation(794);
	}

	public static int getRandomBalloon() {
		// between 115 and 122
		return 115 + r.nextInt(8);
	}

	public int getPoppedBalloon() {
		// between 115 and 122
		return objectId + 8;
	}

	public void remove() {
		// Spawn the popped balloons
		Objects poppedBallons = new Objects(getPoppedBalloon(), objectX, objectY, objectHeight, objectFace, objectType, 0);
		GameEngine.objectHandler.addObject(poppedBallons);
		GameEngine.objectHandler.placeObject(poppedBallons);
		// Remove the popped balloons
		Objects empty = new Objects(-1, objectX, objectY, objectHeight, objectFace, objectType, 4);
		GameEngine.objectHandler.addObject(empty);
	}
}
