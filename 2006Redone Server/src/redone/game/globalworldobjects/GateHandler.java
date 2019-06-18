package redone.game.globalworldobjects;

import redone.Server;
import redone.game.objects.Objects;
import redone.game.players.Client;
import redone.world.ObjectManager;
import redone.world.clip.ObjectDef;
import redone.world.clip.Region;

/**
 * GateHandler
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */
public class GateHandler {
	
	public static int gateAmount = 0, gateTicks = 100;
	
	public static boolean isGate(int objectId) {
		String objectName = ObjectDef.getObjectDef(objectId).name;
		return objectName.equalsIgnoreCase("gate") || objectName.equalsIgnoreCase("Gate");
	}
	
	public static void spawnGate(int objectId, int x, int y, int h, int face) {
		Server.objectHandler.placeObject(new Objects(objectId, x, y, h, face, 0, 0));
	}
	
	public static void openSingleGate(Client player, int objectId, int x1, int y1, int x2, int y2, int walkX, int walkY, int face1, int face2) {
		if (isGate(objectId) && gateAmount == 0) {
			spawnGate(-1, x2, y2, player.heightLevel, 0);
			spawnGate(objectId, x1, y1, player.heightLevel, face1);
			gateAmount = 1;
			player.getPlayerAssistant().walkTo(walkX, walkY);
			ObjectManager.singleGateTicks(player, objectId, x2, y2, x1, y1, player.heightLevel, face2, 2);
		}
	}
	
	private static void openDoubleGate(Client player, int objectId, int objectId2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int face1, int face2) {
		if (isGate(objectId) && isGate(objectId2) && gateAmount == 0) {
			spawnGate(-1, x3, y3, player.heightLevel, 0);
			spawnGate(-1, x4, y4, player.heightLevel, 0);
			spawnGate(objectId, x1, y1, player.heightLevel, face1);
			Region.addObject(objectId, x1, y1, player.heightLevel, 0, face1, false);
			gateAmount = 1;
			spawnGate(objectId2, x2, y2, player.heightLevel, face1);
			Region.addObject(objectId2, x2, y2, player.heightLevel, 0, face1, false);
			gateAmount = 2;
			ObjectManager.doubleGateTicks(player, objectId, x3, y3, x1, y1, x2, y2, player.heightLevel, face2, gateTicks);
			ObjectManager.doubleGateTicks(player, objectId2, x4, y4, x1, y1, x2, y2, player.heightLevel, face2, gateTicks);
		} else if (isGate(objectId) && isGate(objectId2) && gateAmount == 2) {
			ObjectManager.doubleGateTicks(player, objectId, x3, y3, x1, y1, x2, y2, player.heightLevel, face2, 0);
			ObjectManager.doubleGateTicks(player, objectId2, x4, y4, x1, y1, x2, y2, player.heightLevel, face2, 0);
		}
	}
	
	private static void openSpecialGate(Client player, int objectId, int objectId2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int face1, int face2, int face3) {
		if (isGate(objectId) && isGate(objectId2) && gateAmount == 0) {
			spawnGate(-1, x3, y3, player.heightLevel, 0);
			spawnGate(-1, x4, y4, player.heightLevel, 0);
			spawnGate(objectId, x1, y1, player.heightLevel, face1);
			Region.addObject(objectId, x1, y1, player.heightLevel, 0, face1, false);
			gateAmount = 1;
			spawnGate(objectId2, x2, y2, player.heightLevel, face2);
			Region.addObject(objectId2, x2, y2, player.heightLevel, 0, face2, false);
			gateAmount = 2;
			ObjectManager.doubleGateTicks(player, objectId, x3, y3, x1, y1, x2, y2, player.heightLevel, face3, gateTicks);
			ObjectManager.doubleGateTicks(player, objectId2, x4, y4, x1, y1, x2, y2, player.heightLevel, face3, gateTicks);
		} else if (isGate(objectId) && isGate(objectId2) && gateAmount == 2) {
			ObjectManager.doubleGateTicks(player, objectId, x3, y3, x1, y1, x2, y2, player.heightLevel, face3, 0);
			ObjectManager.doubleGateTicks(player, objectId2, x4, y4, x1, y1, x2, y2, player.heightLevel, face3, 0);
		}
	}
	
	public static void openSpecialWalkGate(Client player, int objectId, int objectId2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int walkX, int walkY, int face1, int face2, int face3) {
		if (isGate(objectId) && isGate(objectId2) && gateAmount == 0) {
			spawnGate(-1, x3, y3, player.heightLevel, 0);
			spawnGate(-1, x4, y4, player.heightLevel, 0);
			spawnGate(objectId, x1, y1, player.heightLevel, face1);
			gateAmount = 1;
			spawnGate(objectId2, x2, y2, player.heightLevel, face2);
			gateAmount = 2;
			player.getPlayerAssistant().walkTo(walkX, walkY);
			ObjectManager.doubleGateTicks(player, objectId, x3, y3, x1, y1, x2, y2, player.heightLevel, face3, 2);
			ObjectManager.doubleGateTicks(player, objectId2, x4, y4, x1, y1, x2, y2, player.heightLevel, face3, 2);
		}
	}
	
	public static void handleGate(Client player, int objectId, int objectId2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int type) {
		switch (type) {
			/**
			 * X Gate 1
			 */
			case 0:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 2, 3);
			break;
			/**
			 * Y Gate 1
			 */ 
			case 1:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 3, 0);
			break;
			/**
			 * X Gate 2
			 */
			case 2:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 0, 1);
			break;
			/**
			* Y Gate 2
			*/ 
			case 3:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 1, 0);
			break;
			/**
			 * X Gate 3
			 */
			case 4:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 0, 3);
			break;
			/**
			* Y Gate 3
			*/ 
			case 5:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 1, 2);
			break;
			/**
			 * X Gate 4
			 */
			case 6:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 2, 1);
			break;
			/**
			* Y Gate 4
			*/ 
			case 7:
				openDoubleGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 3, 2);
			break;
		}
	}
	
	public static void handleSpecialGate(Client player, int objectId, int objectId2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int type) {
		switch (type) {
			/**
			 * X Gate 1
			 */
			case 0:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 2, 0, 3);
			break;
			/**
			 * Y Gate 1
			 */
			case 1:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 1, 3, 0);
			break;
			/**
			 * X Gate 2
			 */
			case 2:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 0, 2, 3);
			break;
			/**
			 * Y Gate 2
			 */
			case 3:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 3, 1, 0);
			break;
			/**
			 * X Gate 3
			 */
			case 4:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 2, 0, 1);
			break;
			/**
			 * Y Gate 3
			 */
			case 5:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 3, 1, 2);
			break;
			/**
			 * X Gate 4
			 */
			case 6:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 0, 2, 1);
			break;
			/**
			 * Y Gate 4
			 */
			case 7:
				openSpecialGate(player, objectId, objectId2, x1, y1, x2, y2, x3, y3, x4, y4, 1, 3, 2);
			break;
		}
	}

}
