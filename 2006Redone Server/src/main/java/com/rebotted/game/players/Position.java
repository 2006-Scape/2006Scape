package com.rebotted.game.players;

/**
 * Position
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Position {
	
	public static boolean checkPosition(Player player, int x, int y, int h) {
		return player.absX == x && player.absY == y && player.heightLevel == h;
	}
	
	public static boolean checkPlayerX(Player client, int x, int h) {
		return client.absX == x && client.heightLevel == h;
	}
	
	public static boolean checkPlayerY(Player player, int y, int h) {
		return player.absY == y && player.heightLevel == h;
	}
	
	public static boolean checkPlayerH(Player client, int h) {
		return client.heightLevel == h;
	}
	
	public static boolean checkObject(Player client, int x, int y, int h) {
		return client.objectX == x && client.objectY == y && client.heightLevel == h;
	}
	
	public static boolean checkObjectX(Player client, int x, int h) {
		return client.objectX == x && client.heightLevel == h;
	}
	
	public static boolean checkObjectY(Player client, int y, int h) {
		return client.objectY == y && client.heightLevel == h;
	}
}
