package redone.game.objects;

import redone.game.players.Client;
import redone.world.clip.ObjectDef;

/**
 * ObjectDefaults
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class ObjectDefaults {
	
	private static int 
		SOUTH = 0,
		NORTH = 1,
		EAST = 2,
		WEST = 3;
	
	public static int getObjectType(Client player, int objectType) {
		String objectName = ObjectDef.getObjectDef(objectType).name;
		if (objectName.contains("Wardrobe") || objectName.contains("chest") || objectName.contains("Cupboard") || objectName.contains("Coffin")) {
			return 10;
		}
		return 0;
	}
	
	public static int getObjectFace(Client player, int objectType) {
		switch (objectType) {
		case 388:
		case 389:
			if (player.objectX == 3112 || player.objectX == 3104 || player.objectX == 3096) {
				return EAST;
			} else {
				return SOUTH;
			}
		case 1528:
		case 1529:
			return player.objectX == 3172 ? WEST : EAST;
		case 14879:
			return 1;
		case 1568:
			return player.objectX == 3405 ? EAST : SOUTH;
		case 375:
		case 376:
		case 377:
			return player.objectX == 3096 ? NORTH : SOUTH;
		case 399:
			return player.objectX == 3096 && player.objectY == 3469 ? WEST : player.objectX == 3096 ? SOUTH : EAST;
		case 398:
			return player.objectX == 3096 && player.objectY == 3469 ? WEST : player.objectX == 3096 ? SOUTH : EAST;
		case 3193:
			return player.objectX == 3381 ? EAST : NORTH;
		case 378:
			return player.objectX == 3096 ? NORTH : SOUTH;
		case 3194:
			return player.objectX == 3381 ? EAST : NORTH;
		}
		return SOUTH;
	}

}
