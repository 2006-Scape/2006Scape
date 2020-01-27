package com.rebotted.game.content.skills;

import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.skills.cooking.Cooking;
import com.rebotted.game.content.skills.core.Fishing;
import com.rebotted.game.content.skills.core.Mining;
import com.rebotted.game.content.skills.herblore.Herblore;
import com.rebotted.game.content.skills.smithing.Smelting;
import com.rebotted.game.content.skills.woodcutting.Woodcutting;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Player;

/**
 * Skillhandler
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class SkillHandler {

	public static final int SKILLING_EXP = 1;
	public static boolean view190 = false;
	private static long lastAction = 0;
	public static boolean[] isSkilling = new boolean[25];
	public static long lastSkillingAction;

	public static boolean FISHING = true, AGILITY = true, COOKING = true,
			FIREMAKING = true, HERBLORE = true, MINING = true,
			RUNECRAFTING = true, THIEVING = true, WOODCUTTING = true,
			PRAYER = true, FLETCHING = true, CRAFTING = true, MAGIC = true,
			FARMING = false, SLAYER = true, SMITHING = true;

	public static boolean isSkilling(Player player) {
		if (player.playerSkilling[10] || player.playerStun || player.playerSkilling[12]
				|| player.playerIsFletching || player.isFletching || player.playerIsCooking
				|| player.isMining || player.isWoodcutting || player.isSmithing
				|| player.isSmelting || player.isSpinning || player.isPotionMaking
				|| player.isPotCrafting || player.isFiremaking
				|| player.playerSkilling[player.playerHerblore]
				|| player.playerSkilling[13]) {
			return true;
		}
		return false;
	}
	

	public static void resetItemOnNpc(Player player) {
		if (player.isMining) {// mining
			Mining.resetMining(player);
		} else if (player.playerIsFletching) {// fletching
			player.playerIsFletching = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		} else if (player.isSmithing) {// smithing
			player.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (player.isSmelting) {
			player.isSmelting = false;
		} else if (player.isCrafting) {
			player.isCrafting = false;
		} else if (player.isPotionMaking) {// herblore
			Herblore.resetHerblore(player);
		} else if (player.isWoodcutting) {// woodcutting
			Woodcutting.stopWoodcutting(player);
		} else if (player.isSpinning) {// spinning
			player.isSpinning = false;
		} else if (player.isPotCrafting) {// pot crafting
			player.isPotCrafting = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		} else if (player.playerSkilling[13]) {// smelting
			Smelting.resetSmelting(player);
		}
	}

	public static void resetSkills(Player player) {// call when walking, dropping,
												// picking up, leveling up
		if (player.playerSkilling[10]) {// fishing
			Fishing.resetFishing(player);
		} else if (player.isMining) {// mining
			Mining.resetMining(player);
		} else if (player.playerIsFletching) {// fletching
			player.playerIsFletching = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		} else if (player.isSmithing) {// smithing
			player.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (player.isSmelting) {
			player.isSmelting = false;
		} else if (player.isCrafting) {
			player.isCrafting = false;
		} else if (player.isPotionMaking) {// herblore
			Herblore.resetHerblore(player);
		} else if (player.isWoodcutting) {// woodcutting
			Woodcutting.stopWoodcutting(player);
		} else if (player.isSpinning) {// spinning
			player.isSpinning = false;
		} else if (player.isPotCrafting) {// pot crafting
			player.isPotCrafting = false;
		}
	}

	public static boolean canDoAction(int timer) {
		if (System.currentTimeMillis() >= lastAction) {
			lastAction = System.currentTimeMillis() + timer;
			return true;
		}
		return false;
	}

	public static boolean noInventorySpace(Player c, String skill) {
		if (c.getItemAssistant().freeSlots() == 0) {
			c.getPacketSender().sendMessage(
					"You don't have enough inventory space to continue "
							+ skill + "!");
			return false;
		}
		return true;
	}
	
	public static void deleteTime(Player c) {
		c.doAmount--;
	}
	
	public static void stopEvents(Player player, int eventId) {
		CycleEventHandler.getSingleton().stopEvents(player, eventId);
	}

	public static void send1Item(Player c, int itemId) {
		c.getPacketSender().sendFrame246(1746, view190 ? 190 : 150, itemId);
		c.getPacketSender().sendFrame126(
				getLine(c) + "" + ItemAssistant.getItemName(itemId) + "", 2799);
		c.getPacketSender().sendChatInterface(4429);
	}

	public static boolean playerHasItem(Player c, String itemName,
			String skill, int itemID) {
		if (!c.getItemAssistant().playerHasItem(itemID, 1)) {
			c.getPacketSender().sendMessage(
					"You dont have any " + itemName + " to continue " + skill
							+ "!");
			c.getDialogueHandler().sendStatement(
					"You dont have any " + itemID + " to continue " + skill
							+ "!");
			return false;
		}
		return true;
	}

	public static void resetPlayerSkillVariables(Player c) {
		for (int i = 0; i < 20; i++) {
			if (c.playerSkilling[i]) {
				for (int l = 0; l < 15; l++) {
					c.playerSkillProp[i][l] = -1;
				}
			}
		}
	}

	public static boolean hasRequiredLevel(final Player c, int id, int lvlReq,
			String skill, String event) {
		if (c.playerLevel[id] < lvlReq) {
			c.getPacketSender().sendMessage(
					"You don't have a high enough " + skill + " level to "
							+ event + ".");
			return false;
		}
		return true;
	}

	public static String getLine(Player c) {
		return c.below459 ? "\\n\\n\\n\\n" : "\\n\\n\\n\\n\\n";
	}


}
