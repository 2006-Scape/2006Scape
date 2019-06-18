package redone.game.items.impl;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * Cape Dye
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class CapeDye {
	
	/**
	 * Capes
	 */
	
	private static final int BLUE_CAPE = 1021, RED_CAPE = 1007;
	private static final int GREEN_CAPE = 1027, PINK_CAPE = 6959;
	private static final int ORANGE_CAPE = 1031, PURPLE_CAPE = 1029;
	private static final int YELLOW_CAPE = 1023, BLACK_CAPE = 1019;
	
	/**
	 * Dyes
	 */
	
	private static final int RED_DYE = 1763, BLUE_DYE = 1767;
	private static final int GREEN_DYE = 1771, PINK_DYE = 6955;
	private static final int ORANGE_DYE = 1769, PURPLE_DYE = 1773;
	private static final int YELLOW_DYE = 1765;
	
	/**
	 * Deleting items used + adding skill xp
	 */
	
	private static void cleanUp(Client c, int itemUsed, int useWith) {
		c.getPlayerAssistant().addSkillXP(2.5, c.playerCrafting);
		c.getItemAssistant().deleteItem2(itemUsed, 1);
		c.getItemAssistant().deleteItem2(useWith, 1);
	}
	
	/**
	 * Cape on Dye Method
	 */
	
	public static void capeOnDye(Client c, int itemUsed, int useWith) {
		if (itemUsed == RED_DYE && useWith == BLUE_CAPE || useWith == PURPLE_CAPE && itemUsed == RED_DYE || useWith == GREEN_CAPE && itemUsed == RED_DYE || useWith == ORANGE_CAPE && itemUsed == RED_DYE || useWith == YELLOW_CAPE && itemUsed == RED_DYE || useWith == PINK_CAPE || useWith == BLACK_CAPE && itemUsed == RED_DYE) {
			c.getItemAssistant().addItem(RED_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(RED_CAPE) + ".");
		} else if (itemUsed == BLUE_DYE && useWith == RED_CAPE || useWith == PURPLE_CAPE && itemUsed == BLUE_DYE || useWith == GREEN_CAPE && itemUsed == BLUE_DYE  || useWith == ORANGE_CAPE && itemUsed == BLUE_DYE  || useWith == YELLOW_CAPE && itemUsed == BLUE_DYE  || useWith == PINK_CAPE || useWith == BLACK_CAPE && itemUsed == BLUE_DYE) {
			c.getItemAssistant().addItem(BLUE_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(BLUE_CAPE) + ".");
		} else if (itemUsed == GREEN_DYE && useWith == RED_CAPE || useWith == PURPLE_CAPE && itemUsed == GREEN_DYE || useWith == BLUE_CAPE && itemUsed == GREEN_DYE || useWith == ORANGE_CAPE && itemUsed == GREEN_DYE || useWith == YELLOW_CAPE && itemUsed == GREEN_DYE || useWith == PINK_CAPE || useWith == BLACK_CAPE && itemUsed == GREEN_DYE) {
			c.getItemAssistant().addItem(GREEN_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(GREEN_CAPE) + ".");
		} else if (itemUsed == PINK_DYE && useWith == RED_CAPE || useWith == PURPLE_CAPE && itemUsed == PINK_DYE || useWith == GREEN_CAPE && itemUsed == PINK_DYE || useWith == ORANGE_CAPE && itemUsed == PINK_DYE || useWith == YELLOW_CAPE && itemUsed == PINK_DYE || useWith == BLUE_CAPE || useWith == BLACK_CAPE && itemUsed == PINK_DYE) {
			c.getItemAssistant().addItem(PINK_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(BLUE_CAPE) + ".");
		} else if (itemUsed == ORANGE_DYE && useWith == RED_CAPE || useWith == PURPLE_CAPE && itemUsed == ORANGE_DYE || useWith == GREEN_CAPE && itemUsed == ORANGE_DYE || useWith == PINK_CAPE && itemUsed == ORANGE_DYE || useWith == YELLOW_CAPE && itemUsed == ORANGE_DYE || useWith == BLUE_CAPE || useWith == BLACK_CAPE && itemUsed == ORANGE_DYE) {
			c.getItemAssistant().addItem(ORANGE_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(ORANGE_CAPE) + ".");
		} else if (itemUsed == PURPLE_DYE && useWith == RED_CAPE || useWith == ORANGE_CAPE && itemUsed == PURPLE_DYE || useWith == GREEN_CAPE && itemUsed == PURPLE_DYE || useWith == PINK_CAPE && itemUsed == PURPLE_DYE || useWith == YELLOW_CAPE && itemUsed == PURPLE_DYE || useWith == BLUE_CAPE || useWith == BLACK_CAPE && itemUsed == PURPLE_DYE) {
			c.getItemAssistant().addItem(PURPLE_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(PURPLE_CAPE) + ".");
		} else if (itemUsed == YELLOW_DYE && useWith == RED_CAPE || useWith == ORANGE_CAPE && itemUsed == YELLOW_DYE || useWith == GREEN_CAPE && itemUsed == YELLOW_DYE || useWith == PINK_CAPE && itemUsed == YELLOW_DYE || useWith == PURPLE_CAPE && itemUsed == YELLOW_DYE || useWith == BLUE_CAPE || useWith == BLACK_CAPE && itemUsed == YELLOW_DYE) {
			c.getItemAssistant().addItem(YELLOW_CAPE, 1);
			cleanUp(c, itemUsed, useWith);
			c.getActionSender().sendMessage("You use your " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + " to make a " + ItemAssistant.getItemName(YELLOW_CAPE) + ".");
		} else if (itemUsed == RED_DYE || itemUsed == BLUE_DYE || itemUsed == GREEN_DYE || itemUsed == PINK_DYE || itemUsed == ORANGE_DYE || itemUsed == PURPLE_DYE || itemUsed == YELLOW_DYE && useWith != BLUE_CAPE || useWith != RED_CAPE || useWith != PURPLE_CAPE || useWith != GREEN_CAPE || useWith != ORANGE_CAPE || useWith != YELLOW_CAPE || useWith != PINK_CAPE || useWith != BLACK_CAPE) {
			if (ItemAssistant.getItemName(useWith).contains("Cape") && ItemAssistant.getItemName(itemUsed).contains("dye")) {
				c.getActionSender().sendMessage("You can't use " + ItemAssistant.getItemName(itemUsed) + " on that " + ItemAssistant.getItemName(useWith) + "!");
			} else if (!ItemAssistant.getItemName(useWith).contains("Cape") && ItemAssistant.getItemName(itemUsed).contains("dye")) {
				c.getActionSender().sendMessage("You can't use " + ItemAssistant.getItemName(itemUsed) + " on " + ItemAssistant.getItemName(useWith) + "!");
			}
		}
	}
}
