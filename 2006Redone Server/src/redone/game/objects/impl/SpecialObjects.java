package redone.game.objects.impl;

import redone.Server;
import redone.game.content.traveling.Desert;
import redone.game.players.Client;
import redone.world.clip.Region;

public class SpecialObjects {

	private final static int[] AL_KHARID_GATES = { 2882, 2883 };
	private final static int[] SHANTAY_GATES = { 4031, 4033 };

	public static void openLumbridgePipe(Client c, int objectType) {
		c.isRunning = false;
		c.getPlayerAssistant().sendConfig(173, 0);
		c.playerWalkIndex = 819;
		c.getPlayerAssistant().requestUpdates();
		c.getPlayerAssistant().walkTo(0, -1);
	}

	public static boolean openKharid(Client c, int objectId) {
		for (int element : AL_KHARID_GATES) {
			if (objectId == element) {
				return true;
			}
		}
		return false;
	}

	public static boolean openShantay(Client c, int objectId) {
		for (int element : SHANTAY_GATES) {
			if (objectId == element) {
				return true;
			}
		}
		return false;
	}

	public static void movePlayer(Client c) {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPlayerAssistant().movePlayer(c.absX + 1, c.absY, 0);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1,
				0)) {
			c.getPlayerAssistant().movePlayer(c.absX - 1, c.absY, 0);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0,
				-1)) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0,
				1)) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
		}
	}

	public static void openShantayChest(Client c, int objectId, int obX,
			int obY, String type) {
		if (type == "open") {
			Server.objectHandler.createAnObject(c, 104, obX, obY, -1);
		} else if (type == "shut") {
			Server.objectHandler.createAnObject(c, 2693, obX, obY, -1);
		}
	}

	public static void initKharid(Client c, int objectId) {
		if (!c.getItemAssistant().playerHasItem(995, 10)) {
			c.getDialogueHandler().itemMessage(c,
					"You need 10 coins to pass through this gate.", 995, 200);
			c.nextChat = 0;
			return;
		}
		Server.objectHandler.createAnObject(c, -1, c.objectX, c.objectY, -1);
		final int[] coords = new int[2];
		openKharid(c, objectId);
		if (c.absX == 3267) {
			c.getPlayerAssistant().movePlayer(c.absX + 1, c.absY, 0);
		} else if (c.absX == 3268) {
			c.getPlayerAssistant().movePlayer(c.absX - 1, c.absY, 0);
		}
		c.turnPlayerTo(c.objectX, c.objectY);
		coords[0] = c.objectX;
		coords[1] = c.objectY;
		c.getItemAssistant().deleteItem(995,
				c.getItemAssistant().getItemSlot(995), 10);
	}

	private static boolean movePlayer2(Client c) {
		if (c.absY == 3117) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY - 2, 0);
			return true;
		} else if (c.absY == 3115) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY + 2, 0);
			return true;
		}
		c.getActionSender().sendMessage(
				"Move closer so you can use the gate.");
		return false;
	}

	public static void initShantay(Client c, int objectId) {
		if (!c.getItemAssistant().playerHasItem(1854, 1) && c.absY == 3117) {
			c.getDialogueHandler().sendStatement("You need a Shantay pass to go through.");
			return;
		}
		final int[] coords = new int[2];
		openShantay(c, objectId);
		c.getActionSender().sendMessage("You pass through the gate.");
		movePlayer2(c);
		c.turnPlayerTo(c.objectX, c.objectY);
		coords[0] = c.objectX;
		coords[1] = c.objectY;
		if (c.desertWarning == false && c.absY == 3117) {
			Desert.showWarning(c);
			c.desertWarning = true;
		}
		if (c.absY == 3117) {
			c.getItemAssistant().deleteItem(1854, c.getItemAssistant().getItemSlot(1854), 1);
		}
	}

}
