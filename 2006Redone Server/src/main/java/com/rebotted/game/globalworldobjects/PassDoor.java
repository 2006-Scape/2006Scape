package com.rebotted.game.globalworldobjects;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.Position;

/**
 * Passdoor
 * @author Andrew (Mr Extremez)
 */

public class PassDoor {
	
	private static long doorDelay;
	
	public static boolean passThroughDoor(final Player player, final int objectType, int face1, final int face2, final int type, int x, int y, final int height) {
		if (System.currentTimeMillis() - doorDelay < 800) {
			return false;
		}

		final int objX = player.objectX;
		final int objY = player.objectY;

		player.getPacketSender().object(objectType, objX, objY, height, face1, type);
		player.getPlayerAssistant().walkTo(x, y);
		player.stopPlayer = true;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				player.getPacketSender().object(objectType, objX, objY, height, face2, type);
				container.stop();
			}

			@Override
			public void stop() {
				player.stopPlayer = false;
				doorDelay = System.currentTimeMillis();
			}
		}, 2);
		return true;
	}
	
	public static void processDoor(Player player, int objectType) {
		//player, object, face1, face2, type, x, y, height
		switch (objectType) {
		case 2550:
		if (Position.checkPosition(player, 2674, 3306, 0)) {
			passThroughDoor(player, objectType, 0, 1, 0, 0, -1, 0);
		}
		break;	
		case 2551:
		if (Position.checkPosition(player, 2674, 3303, 0)) {
			passThroughDoor(player, objectType, 0, 3, 0, 0, 1, 0);
		}
		break;
		case 1530:
			if (Position.checkPosition(player, 2715, 3472, 0)) {
				passThroughDoor(player, objectType, 1, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(player, 2716, 3472, 0)) {
				passThroughDoor(player, objectType, 1, 0, 0, -1, 0, 0);
			} else if (Position.checkPosition(player, 3246, 9892, 0)) {
				passThroughDoor(player, objectType, 1, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(player, 3247, 9892, 0)) {
				passThroughDoor(player, objectType, 1, 0, 0, -1, 0, 0);
			} else if (Position.checkPosition(player, 3110, 9559, 0)) {
				passThroughDoor(player, objectType, 3, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(player, 3111, 9559, 0)) {
				passThroughDoor(player, objectType, 3, 0, 0, -1, 0, 0);
			}
			break;
		case 11993:
			if (Position.checkPosition(player, 3107, 3162, 2)) {
				passThroughDoor(player, objectType, 1, 2, 0, 1, 0, 2);
			} else if (Position.checkPosition(player, 3108, 3162, 2)) {
				passThroughDoor(player, objectType, 1, 2, 0, -1, 0, 2);
			} else if (Position.checkPosition(player, 3109, 3162, 1)) {
				passThroughDoor(player, objectType, 1, 2, 0, 1, 0, 1);
			} else if (Position.checkPosition(player, 3110, 3162, 1)) {
				passThroughDoor(player, objectType, 1, 2, 0, -1, 0, 1);
			} else if (Position.checkPosition(player, 3109, 3159, 1)) {
				passThroughDoor(player, objectType, 1, 2, 0, 1, 0, 1);
			} else if (Position.checkPosition(player, 3110, 3159, 1)) {
				passThroughDoor(player, objectType, 1, 2, 0, -1, 0, 1);
			} else if (Position.checkPosition(player, 3108, 3159, 1)) {
				passThroughDoor(player, objectType, 2, 1, 0, 0, -1, 1);
			} else if (Position.checkPosition(player, 3108, 3158, 1)) {
				passThroughDoor(player, objectType, 2, 1, 0, 0, 1, 1);
			} else if (Position.checkObject(player, 3107, 3162, 0)) {
				if (Position.checkPosition(player, 3108, 3162, 0)) {
					passThroughDoor(player, objectType, 2, 1, 9, -1, -1, 0);
				} else if (Position.checkPosition(player, 3107, 3163, 0)) {
					// wizz tower
					passThroughDoor(player, objectType, 2, 1, 9, -1, -1, 0);
				} else {
					if (player.heightLevel == 0) {
						passThroughDoor(player, objectType, 2, 1, 9, 1, 1, 0);
					}
				}
			}
			break;
		}
	}
}
