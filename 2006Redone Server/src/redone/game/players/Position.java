package redone.game.players;

/**
 * Position
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Position {
	
	public static boolean checkPosition(Client client, int x, int y, int h) {
		return client.absX == x && client.absY == y && client.heightLevel == h;
	}
	
	public static boolean checkPlayerX(Client client, int x, int h) {
		return client.absX == x && client.heightLevel == h;
	}
	
	public static boolean checkPlayerY(Client client, int y, int h) {
		return client.absY == y && client.heightLevel == h;
	}
	
	public static boolean checkPlayerH(Client client, int h) {
		return client.heightLevel == h;
	}
	
	public static boolean checkObject(Client client, int x, int y, int h) {
		return client.objectX == x && client.objectY == y && client.heightLevel == h;
	}
	
	public static boolean checkObjectX(Client client, int x, int h) {
		return client.objectX == x && client.heightLevel == h;
	}
	
	public static boolean checkObjectY(Client client, int y, int h) {
		return client.objectY == y && client.heightLevel == h;
	}
}
