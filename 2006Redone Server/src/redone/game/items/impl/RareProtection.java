package redone.game.items.impl;

import redone.game.players.Client;

/**
 * Rare Protection
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class RareProtection {

	public static final boolean RARES = false, CRACKERS = false;
	private static final int[] RARE_ITEMS = { 1037, 1038, 1039, 1040, 1041,
			1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 962, 963, 1959, 1961, 1989 };
	private static final int[] EATABLE_RARES = { 1959, 1961, 1989 };

	public static boolean equipItem(Client c) {// check when wearing, removing
		for (int element : RARE_ITEMS) {
			if (!RARES && c.wearId == element && c.playerRights < 3) {
				c.getActionSender().sendMessage(
						"You shouldn't have that item!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean removeItem(Client c, int itemId) {// check when
															// wearing, removing
		for (int element : RARE_ITEMS) {
			if (!RARES && itemId == element && c.playerRights < 3) {
				c.getActionSender().sendMessage(
						"You shouldn't have that item!");
				c.getItemAssistant().deleteEquipment(element, 0);
				return false;
			}
		}
		return true;
	}

	public static boolean hasDupedItem(Client c) {// check on login
		for (int element : RARE_ITEMS) {
			if (!RARES && c.getItemAssistant().playerHasItem(element)
					&& c.playerRights < 3) {
				c.getActionSender().sendMessage(
						"You can't have these items!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean eatDupedItem(Client c, int itemId) {// check when
																// eating
		for (int element : EATABLE_RARES) {
			if (!RARES && itemId == element && c.playerRights < 3) {
				c.getActionSender().sendMessage("You can't eat that item!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}

	public static boolean doOtherDupe(Client c, int itemId) {// check when
																// dropping,
																// trading,
																// staking,
																// dropping
		for (int element : RARE_ITEMS) {
			if (!RARES && c.getItemAssistant().playerHasItem(element)
					&& c.playerRights < 3) {
				c.getActionSender().sendMessage(
						"You shouldnt have that item!");
				int amountToDelete = c.getItemAssistant().getItemCount(element);
				c.getItemAssistant().deleteItem(element, amountToDelete);
				return false;
			}
		}
		return true;
	}
}
