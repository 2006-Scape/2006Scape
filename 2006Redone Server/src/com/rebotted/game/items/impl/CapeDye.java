package com.rebotted.game.items.impl;

import com.rebotted.game.items.Item;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Client;

/**
 * Dye.java
 * @author Andrew (Mr Extremez)
 */
	
public enum CapeDye {
	
		RED_CAPE(1763, 1007),
		BLUE_CAPE(1767, 1021),
		GREEN_CAPE(1771, 1027),
		PINK_CAPE(6955, 6959),
		ORANGE_CAPE(1769, 1031),
		YELLOW_CAPE(1765, 1023),
		PURPLE_CAPE(1773, 1029);
		
		int reward, itemUsed;
		
		private CapeDye(int itemUsed, int reward) {
			this.itemUsed = itemUsed;
			this.reward = reward;
		}
		
		private int getItemUsed() {
			return itemUsed;
		}
		
		private int getReward() {
			return reward;
		}
		
		//blue+yellow =green
		//red+blue = purple
		
		public static final int[][] MAIL_DATA = {
				{1769, 288, 286},
				{1769, 287, 286},
				{1767, 288, 287},
				{1767, 286, 287},
				{1767, 1765, 1771},
				{1763, 1767, 1773}
		};
	
		public static boolean blockDye(Client player, CapeDye dye, int itemUsed, int useWith) {
			if (itemUsed == dye.getItemUsed() && ItemAssistant.getItemName(useWith).equalsIgnoreCase("Cape") && Item.itemIsNote[useWith]) {
				player.getPacketSender().sendMessage("You can't dye a noted cape.");
				return true;
			} else if (itemUsed == dye.getItemUsed() && ItemAssistant.getItemName(useWith).equalsIgnoreCase("Cape") && useWith == dye.getReward() && !Item.itemIsNote[useWith]) {
				player.getPacketSender().sendMessage("That cape is already that color.");
				return true;
			} else if (itemUsed == dye.getItemUsed() && !ItemAssistant.getItemName(useWith).equalsIgnoreCase("Cape")) {
				return true;
			}
			return false;
		}
		
	public static void dyeItem(Client player, int itemUsed, int useWith) {
		for (CapeDye cape: CapeDye.values()) {
			if (blockDye(player, cape, itemUsed, useWith)) {
				return;
			}
			if (itemUsed == cape.getItemUsed() && ItemAssistant.getItemName(useWith).equalsIgnoreCase("Cape") && !Item.itemIsNote[useWith] && useWith != cape.getReward()) {
				player.getItemAssistant().deleteItem(itemUsed, 1);
				player.getItemAssistant().deleteItem(useWith, 1);
				player.getItemAssistant().addItem(cape.getReward(), 1);
				player.getPlayerAssistant().addSkillXP(2.5, player.playerCrafting);
			}
		}
	}

}