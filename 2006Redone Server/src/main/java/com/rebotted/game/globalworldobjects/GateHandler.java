package com.rebotted.game.globalworldobjects;

import com.rebotted.GameEngine;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.objects.Objects;
import com.rebotted.game.players.Player;
import com.rebotted.world.ObjectManager;
import com.rebotted.world.clip.ObjectDefinition;
import com.rebotted.world.clip.Region;

/**
 * GateHandler
 * (needs to be re written tbh)
 * @author Andrew (Mr Extremez)
 */
public class GateHandler {
	
	public int gateStatus = 0, gateTicks = 50,
		CLOSED = 0, PARTIAL_OPEN = 1, OPEN = 2;
	
	public boolean isGate(int objectId) {
		String objectName = ObjectDefinition.getObjectDef(objectId).name;
		try {
			return objectName.equalsIgnoreCase("gate") || objectName.equalsIgnoreCase("Gate");
		} catch (Exception e) {
			return false;
		}
	} 
	
	public void spawnGate(Player player, int objectId, int newObjectX, int newObjectY, int height, int face) {
		GameEngine.objectHandler.placeObject(new Objects(objectId, newObjectX, newObjectY, height, face, 0, 0));
		player.getPacketSender().sendSound(SoundList.OPEN_GATE, 100, 0);
	}
	
	public void openSingleGate(Player player, int objectId, int newObjectX, int newObjectY, int oldObjectX, int oldObjectY, int walkX, int walkY, int newFace, int oldFace) {
		if (isGate(objectId) && player.getGateHandler().gateStatus == CLOSED) {
			spawnGate(player, -1, oldObjectX, oldObjectY, player.heightLevel, 0);
			spawnGate(player, objectId, newObjectX, newObjectY, player.heightLevel, newFace);
			player.getGateHandler().gateStatus = PARTIAL_OPEN;
			player.getPlayerAssistant().walkTo(walkX, walkY);
			ObjectManager.singleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, player.heightLevel, oldFace, 2);
		}
	}
	
	private void openDoubleGate(Player player, int objectId, int objectId2, int newObjectX, int newObjectY, int newObjectX2, int newObjectY2, int oldObjectX, int oldObjectY, int oldObjectX2, int oldObjectY2, int newFace, int oldFace) {
		if (isGate(objectId) && isGate(objectId2) && player.getGateHandler().gateStatus == CLOSED) {
			spawnGate(player, -1, oldObjectX, oldObjectY, player.heightLevel, 0);
			spawnGate(player, -1, oldObjectX2, oldObjectY2, player.heightLevel, 0);
			spawnGate(player, objectId, newObjectX, newObjectY, player.heightLevel, newFace);
			Region.addObject(objectId, newObjectX, newObjectY, player.heightLevel, 0, newFace, false);
			player.getGateHandler().gateStatus = PARTIAL_OPEN;
			spawnGate(player, objectId2, newObjectX2, newObjectY2, player.heightLevel, newFace);
			Region.addObject(objectId2, newObjectX2, newObjectY2, player.heightLevel, 0, newFace, false);
			player.getGateHandler().gateStatus = OPEN;
			ObjectManager.doubleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, player.getGateHandler().gateTicks);
			ObjectManager.doubleGateTicks(player, objectId2, oldObjectX2, oldObjectY2, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, player.getGateHandler().gateTicks);
		} else if (isGate(objectId) && isGate(objectId2) && player.getGateHandler().gateStatus == OPEN) {
			ObjectManager.doubleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 0);
			ObjectManager.doubleGateTicks(player, objectId2, oldObjectX2, oldObjectY2, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 0);
		}
	}
	
	private void openSpecialGate(Player player, int objectId, int objectId2, int newObjectX, int newObjectY, int newObjectX2, int newObjectY2, int oldObjectX, int oldObjectY, int oldObjectX2, int oldObjectY2, int newFace, int newFace2, int oldFace) {
		if (isGate(objectId) && isGate(objectId2) && player.getGateHandler().gateStatus == CLOSED) {
			spawnGate(player, -1, oldObjectX, oldObjectY, player.heightLevel, 0);
			spawnGate(player, -1, oldObjectX2, oldObjectY2, player.heightLevel, 0);
			spawnGate(player, objectId, newObjectX, newObjectY, player.heightLevel, newFace);
			Region.addObject(objectId, newObjectX, newObjectY, player.heightLevel, 0, newFace, false);
			player.getGateHandler().gateStatus = PARTIAL_OPEN;
			spawnGate(player, objectId2, newObjectX2, newObjectY2, player.heightLevel, newFace2);
			Region.addObject(objectId2, newObjectX2, newObjectY2, player.heightLevel, 0, newFace2, false);
			player.getGateHandler().gateStatus = OPEN;
			ObjectManager.doubleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, player.getGateHandler().gateTicks);
			ObjectManager.doubleGateTicks(player, objectId2, oldObjectX2, oldObjectY2, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, player.getGateHandler().gateTicks);
		} else if (isGate(objectId) && isGate(objectId2) && player.getGateHandler().gateStatus == OPEN) {
			ObjectManager.doubleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 0);
			ObjectManager.doubleGateTicks(player, objectId2, oldObjectX2, oldObjectY2, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 0);
		}
	}
	
	public void openMetalGateWalk(Player player, int objectId, int objectId2, int newObjectX, int newObjectY, int newObjectX2, int newObjectY2, int oldObjectX, int oldObjectY, int oldObjectX2, int oldObjectY2, int walkX, int walkY, int newFace, int newFace2, int oldFace) {
		if (isGate(objectId) && isGate(objectId2) && player.getGateHandler().gateStatus == CLOSED) {
			spawnGate(player, -1, oldObjectX, oldObjectY, player.heightLevel, 0);
			spawnGate(player, -1, oldObjectX2, oldObjectY2, player.heightLevel, 0);
			spawnGate(player, objectId, newObjectX, newObjectY, player.heightLevel, newFace);
			player.getGateHandler().gateStatus = PARTIAL_OPEN;
			spawnGate(player, objectId2, newObjectX2, newObjectY2, player.heightLevel, newFace2);
			player.getGateHandler().gateStatus = OPEN;
			player.getPlayerAssistant().walkTo(walkX, walkY);
			ObjectManager.doubleGateTicks(player, objectId, oldObjectX, oldObjectY, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 2);
			ObjectManager.doubleGateTicks(player, objectId2, oldObjectX2, oldObjectY2, newObjectX, newObjectY, newObjectX2, newObjectY2, player.heightLevel, oldFace, 2);
		}
	}
	
	public void handleWoodenGate(Player player, int objectId, int objectId2, int newObjectX, int newObjectY, int newObjectX2, int newObjectY2, int oldObjectX, int oldObjectY, int oldObjectX2, int oldObjectY2, int type) {
		switch (type) {
			/**
			 * X Gate 1
			 */
			case 0:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 2, 3);
			break;
			/**
			 * Y Gate 1
			 */ 
			case 1:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 3, 0);
			break;
			/**
			 * X Gate 2
			 */
			case 2:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 0, 1);
			break;
			/**
			* Y Gate 2
			*/ 
			case 3:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 1, 0);
			break;
			/**
			 * X Gate 3
			 */
			case 4:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 0, 3);
			break;
			/**
			* Y Gate 3
			*/ 
			case 5:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 1, 2);
			break;
			/**
			 * X Gate 4
			 */
			case 6:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 2, 1);
			break;
			/**
			* Y Gate 4
			*/ 
			case 7:
				openDoubleGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 3, 2);
			break;
		}
	}
	
	public void handleMetalGate(Player player, int objectId, int objectId2, int newObjectX, int newObjectY, int newObjectX2, int newObjectY2, int oldObjectX, int oldObjectY, int oldObjectX2, int oldObjectY2, int type) {
		switch (type) {
			/**
			 * X Gate 1
			 */
			case 0:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 2, 0, 3);
			break;
			/**
			 * Y Gate 1
			 */
			case 1:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 1, 3, 0);
			break;
			/**
			 * X Gate 2
			 */
			case 2:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 0, 2, 3);
			break;
			/**
			 * Y Gate 2
			 */
			case 3:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 3, 1, 0);
			break;
			/**
			 * X Gate 3
			 */
			case 4:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 2, 0, 1);
			break;
			/**
			 * Y Gate 3
			 */
			case 5:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 3, 1, 2);
			break;
			/**
			 * X Gate 4
			 */
			case 6:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 0, 2, 1);
			break;
			/**
			 * Y Gate 4
			 */
			case 7:
				openSpecialGate(player, objectId, objectId2, newObjectX, newObjectY, newObjectX2, newObjectY2, oldObjectX, oldObjectY, oldObjectX2, oldObjectY2, 1, 3, 2);
			break;
		}
	}

}
