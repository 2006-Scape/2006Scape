package com.rebotted.game.objects.impl;

import com.rebotted.GameEngine;
import com.rebotted.game.content.traveling.DesertCactus;
import com.rebotted.game.content.traveling.DesertHeat;
import com.rebotted.game.players.Player;
import com.rebotted.world.clip.Region;

public class SpecialObjects {

	private final static int[] AL_KHARID_GATES = { 2882, 2883 };
	private final static int[] SHANTAY_GATES = { 4031, 4033 };

	public static void openLumbridgePipe(Player c, int objectType) {
		c.isRunning = false;
		c.getPacketSender().sendConfig(173, 0);
		c.playerWalkIndex = 819;
		c.getPlayerAssistant().requestUpdates();
		c.getPlayerAssistant().walkTo(0, -1);
	}

	public static boolean openKharid(Player player, int objectId) {
		for (int element : AL_KHARID_GATES) {
			if (objectId == element) {
				return true;
			}
		}
		return false;
	}

	public static boolean openShantay(Player c, int objectId) {
		for (int element : SHANTAY_GATES) {
			if (objectId == element) {
				return true;
			}
		}
		return false;
	}

	public static void movePlayer(Player c) {
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

	public static void openShantayChest(Player player, int objectId, int obX,
			int obY, String type) {
		if (type == "open") {
			GameEngine.objectHandler.createAnObject(player, 104, obX, obY, player.heightLevel, -1);
		} else if (type == "shut") {
			GameEngine.objectHandler.createAnObject(player, 2693, obX, obY, player.heightLevel, -1);
		}
	}

	public static void initKharid(Player player, int objectId) {
		if (!player.getItemAssistant().playerHasItem(995, 10)) {
			player.getDialogueHandler().itemMessage("You need 10 coins to pass through this gate.", 995, 200);
			player.nextChat = 0;
			return;
		}
		GameEngine.objectHandler.createAnObject(player, -1, player.objectX, player.objectY, player.heightLevel, -1);
		final int[] coords = new int[2];
		openKharid(player, objectId);
		if (player.absX == 3267) {
			player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
		} else if (player.absX == 3268) {
			player.getPlayerAssistant().movePlayer(player.absX - 1, player.absY, 0);
		}
		player.turnPlayerTo(player.objectX, player.objectY);
		coords[0] = player.objectX;
		coords[1] = player.objectY;
		player.getItemAssistant().deleteItem(995,
				player.getItemAssistant().getItemSlot(995), 10);
	}

	private static boolean movePlayer2(Player c) {
		if (c.absY == 3117) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY - 2, 0);
			return true;
		} else if (c.absY == 3115) {
			c.getPlayerAssistant().movePlayer(c.absX, c.absY + 2, 0);
			return true;
		}
		c.getPacketSender().sendMessage(
				"Move closer so you can use the gate.");
		return false;
	}

	public static void initShantay(Player c, int objectId) {
		if (!c.getItemAssistant().playerHasItem(1854, 1) && c.absY == 3117) {
			c.getDialogueHandler().sendStatement("You need a Shantay pass to go through.");
			return;
		}
		final int[] coords = new int[2];
		openShantay(c, objectId);
		c.getPacketSender().sendMessage("You pass through the gate.");
		movePlayer2(c);
		c.turnPlayerTo(c.objectX, c.objectY);
		coords[0] = c.objectX;
		coords[1] = c.objectY;
		if (c.desertWarning == false && c.absY == 3117) {
			DesertHeat.showWarning(c);
			c.desertWarning = true;
		}
		if (c.absY == 3117) {
			c.getItemAssistant().deleteItem(1854, c.getItemAssistant().getItemSlot(1854), 1);
		}
	}

}
