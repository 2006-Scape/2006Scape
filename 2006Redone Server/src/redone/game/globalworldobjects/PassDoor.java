package redone.game.globalworldobjects;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.game.players.Position;

/**
 * Passdoor
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class PassDoor {
	
	private static long doorDelay;
	
	public static boolean passThroughDoor(final Client client, final int objectType, int face1, final int face2, final int type, int x, int y, final int height) {
		if (System.currentTimeMillis() - doorDelay < 1200) {
			client.getActionSender().sendMessage("You must wait longer to pass this door.");
			return false;
		}
		client.getActionSender().object(objectType, client.objectX, client.objectY, height, face1, type);
		client.getPlayerAssistant().walkTo(x, y);
		client.stopPlayer = true;
		CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				client.getActionSender().object(objectType, client.objectX, client.objectY, height, face2, type);
				container.stop();
			}

			@Override
			public void stop() {
				client.stopPlayer = false;
				doorDelay = System.currentTimeMillis();
			}
		}, 2);
		return true;
	}
	
	public static void processDoor(Client client, int objectType) {
		//player, object, face1, face2, type, x, y, height
		switch (objectType) {
		case 2550:
		if (Position.checkPosition(client, 2674, 3306, 0)) {
			passThroughDoor(client, objectType, 0, 1, 0, 0, -1, 0);
		}
		break;	
		case 2551:
		if (Position.checkPosition(client, 2674, 3303, 0)) {
			passThroughDoor(client, objectType, 0, 3, 0, 0, 1, 0);
		}
		break;
		case 1530:
			if (Position.checkPosition(client, 2715, 3472, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(client, 2716, 3472, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, -1, 0, 0);
			} else if (Position.checkPosition(client, 3246, 9892, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(client, 3247, 9892, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, -1, 0, 0);
			} else if (Position.checkPosition(client, 3108, 9570, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, -1, 0, 0);
			} else if (Position.checkPosition(client, 3107, 9570, 0)) {
				passThroughDoor(client, objectType, 1, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(client, 3110, 9559, 0)) {
				passThroughDoor(client, objectType, 3, 0, 0, 1, 0, 0);
			} else if (Position.checkPosition(client, 3111, 9559, 0)) {
				passThroughDoor(client, objectType, 3, 0, 0, -1, 0, 0);
			}
			break;
		case 11993:
			if (Position.checkPlayerY(client, 3167, 0)) {
				passThroughDoor(client, objectType, 0, 3, 0, 0, -1, 0);
			} else if (Position.checkPlayerY(client, 3166, 0)) {
				passThroughDoor(client, objectType, 0, 3, 0, 0, 1, 0);
			} else if (Position.checkPlayerY(client, 3163, 0) && client.absX != 3107) {
				passThroughDoor(client, objectType, 2, 1, 0, 0, -1, 0);
			} else if (Position.checkPlayerY(client, 3162, 0) && client.absX != 3108 && client.absX != 3106) {
				passThroughDoor(client, objectType, 2, 1, 0, 0, 1, 0);
			} else if (Position.checkPosition(client, 3107, 3162, 2)) {
				passThroughDoor(client, objectType, 1, 2, 0, 1, 0, 2);
			} else if (Position.checkPosition(client, 3108, 3162, 2)) {
				passThroughDoor(client, objectType, 1, 2, 0, -1, 0, 2);
			} else if (Position.checkPosition(client, 3109, 3159, 1)) {
				passThroughDoor(client, objectType, 1, 2, 0, 1, 0, 1);
			} else if (Position.checkPosition(client, 3110, 3159, 1)) {
				passThroughDoor(client, objectType, 1, 2, 0, -1, 0, 1);
			} else if (Position.checkPosition(client, 3108, 3159, 1)) {
				passThroughDoor(client, objectType, 2, 1, 0, 0, -1, 1);
			} else if (Position.checkPosition(client, 3108, 3158, 1)) {
				passThroughDoor(client, objectType, 2, 1, 0, 0, 1, 1);
			} else if (Position.checkObject(client, 3107, 3162, 0)) {
				if (Position.checkPosition(client, 3108, 3162, 0)) {
					passThroughDoor(client, objectType, 2, 1, 9, -1, -1, 0);
				} else if (Position.checkPosition(client, 3107, 3163, 0)) {
					passThroughDoor(client, objectType, 2, 1, 9, -1, -1, 0);
				} else {
					if (client.heightLevel == 0) {
						passThroughDoor(client, objectType, 2, 1, 9, 1, 1, 0);
					}
				}
			}
			break;
		}
	}
}
