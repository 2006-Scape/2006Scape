package com.rs2.game.content.skills;

import java.util.Random;

import com.rs2.Constants;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.skills.cooking.Cooking;
import com.rs2.game.content.skills.core.Fishing;
import com.rs2.game.content.skills.core.Mining;
import com.rs2.game.content.skills.herblore.Herblore;
import com.rs2.game.content.skills.smithing.Smelting;
import com.rs2.game.content.skills.woodcutting.Woodcutting;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

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
	
	public static final String[] skillNames = { "Attack", "Defence",
			"Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking",
			"Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
			"Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
			"Farming", "Runecrafting" };


	public static boolean isSkilling(Player player) {
		if (player.playerSkilling[Constants.FISHING] || player.playerStun || player.playerSkilling[Constants.CRAFTING]
				|| player.playerIsFletching || player.isFletching || player.playerIsCooking
				|| player.isMining || player.isWoodcutting || player.isSmithing
				|| player.isSmelting || player.isSpinning || player.isPotionMaking
				|| player.isPotCrafting || player.isFiremaking
				|| player.playerSkilling[Constants.HERBLORE]
				|| player.playerSkilling[Constants.SMITHING]) {
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
			Cooking.setCooking(player, false);
		} else if (player.isSmithing) {// smithing
			player.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (player.isSmelting || player.playerSkilling[Constants.SMITHING]) {// smelting
			Smelting.resetSmelting(player);
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
			Cooking.setCooking(player, false);
		}
	}

	public static void resetSkills(Player player) {// call when walking, dropping,
												// picking up, leveling up
		if (player.playerSkilling[Constants.FISHING]) {// fishing
			Fishing.resetFishing(player);
		} else if (player.isMining) {// mining
			Mining.resetMining(player);
		} else if (player.playerIsFletching) {// fletching
			player.playerIsFletching = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.setCooking(player, false);
		} else if (player.isSmithing) {// smithing
			player.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (player.isSmelting || player.playerSkilling[Constants.SMITHING]) {// smelting
			Smelting.resetSmelting(player);
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
		c.getPacketSender().sendString(
				getLine(c) + "" + DeprecatedItems.getItemName(itemId) + "", 2799);
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
	
	public static boolean hasRequiredLevel(final Player player, int skillId,
			int lvlReq, String event) {
		if (player.playerLevel[skillId] < lvlReq) {
			player.getPacketSender().sendMessage("You need at least " + lvlReq + " "
					+ skillNames[skillId] + " to " + event + ".");
			return false;
		}
		return true;
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
	
	public static boolean skillCheck(int level, int levelRequired, int itemBonus) {
		double chance = 0.0;
		double baseChance = levelRequired < 11 ? 15 : levelRequired < 51 ? 10
				: 5;// Math.pow(10d-levelRequired/10d, 2d)/2d;
		chance = baseChance + ((level - levelRequired) / 2d)
				+ (itemBonus / 10d);
		return chance >= (new Random().nextDouble() * 100.0);
	}

	public static String getLine(Player c) {
		return c.below459 ? "\\n\\n\\n\\n" : "\\n\\n\\n\\n\\n";
	}


}
