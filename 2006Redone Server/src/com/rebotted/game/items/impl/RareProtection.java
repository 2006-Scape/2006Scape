package com.rebotted.game.items.impl;

import com.rebotted.game.players.Player;

/**
 * Rare Protection
 * @author Andrew (Mr Extremez)
 */

public class RareProtection {

	public static final boolean RARES = true, CRACKERS = false;
	private static final int[] RARE_ITEMS = { 1037, 1038, 1039, 1040, 1041,
			1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 962, 963, 1959, 1961, 1989 };
	private static final int[] EDIBLE_RARES = { 1959, 1961, 1989 };

	public static boolean equipItem(Player c) {// check when wearing, removing
		for (int element : RARE_ITEMS) {
			if (c.wearId == element && c.playerRights < 3) {
				c.getPacketSender().sendMessage("You shouldn't have that item!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean removeItem(Player c, int itemId) {
		for (int element : RARE_ITEMS) {
			if (itemId == element && c.playerRights < 3) {
				c.getPacketSender().sendMessage("You shouldn't have that item!");
				c.getItemAssistant().deleteEquipment(element, 0);
				return false;
			}
		}
		return true;
	}

	public static boolean hasDupedItem(Player c) {// check on login
		for (int element : RARE_ITEMS) {
			if (c.getItemAssistant().playerHasItem(element) && c.playerRights < 3) {
				c.getPacketSender().sendMessage("You can't have these items!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean eatDupedItem(Player c, int itemId) {
		for (int element : EDIBLE_RARES) {
			if (itemId == element && c.playerRights < 3) {
				c.getPacketSender().sendMessage("You can't eat that item!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean doOtherDupe(Player player, int itemId) {
		for (int element : RARE_ITEMS) {
			if (player.getItemAssistant().playerHasItem(element) && player.playerRights < 3) {
				player.getPacketSender().sendMessage("You shouldnt have that item!");
				int amountToDelete = player.getItemAssistant().getItemCount(element);
				player.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}
}
