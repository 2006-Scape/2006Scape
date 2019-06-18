package redone.game.content.skills;

import redone.event.CycleEventHandler;
import redone.game.content.skills.cooking.Cooking;
import redone.game.content.skills.core.Fishing;
import redone.game.content.skills.core.Mining;
import redone.game.content.skills.core.Woodcutting;
import redone.game.content.skills.herblore.Herblore;
import redone.game.content.skills.smithing.Smelting;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

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

	public static final int WOODCUTTING_EXPERIENCE = SKILLING_EXP,
			MINING_EXPERIENCE = SKILLING_EXP,
			SMITHING_EXPERIENCE = SKILLING_EXP,
			FARMING_EXPERIENCE = SKILLING_EXP,
			FIREMAKING_EXPERIENCE = SKILLING_EXP,
			HERBLORE_EXPERIENCE = SKILLING_EXP,
			FISHING_EXPERIENCE = SKILLING_EXP,
			AGILITY_EXPERIENCE = SKILLING_EXP,
			PRAYER_EXPERIENCE = SKILLING_EXP,
			RUNECRAFTING_EXPERIENCE = SKILLING_EXP,
			CRAFTING_EXPERIENCE = SKILLING_EXP,
			THIEVING_EXPERIENCE = SKILLING_EXP,
			SLAYER_EXPERIENCE = SKILLING_EXP,
			COOKING_EXPERIENCE = SKILLING_EXP,
			FLETCHING_EXPERIENCE = SKILLING_EXP;

	public static boolean isSkilling(Client c) {
		if (c.playerSkilling[10] || c.playerStun || c.playerSkilling[12]
				|| c.playerIsFletching || c.isFletching || c.playerIsCooking
				|| c.isMining || c.isWoodcutting || c.isSmithing
				|| c.isSmelting || c.isSpinning || c.isPotionMaking
				|| c.isPotCrafting || c.isFiremaking
				|| c.playerSkilling[c.playerHerblore] == true
				|| c.playerSkilling[13]) {
			return true;
		}
		return false;
	}
	

	public static void resetItemOnNpc(Client player) {
		if (player.isMining) {// mining
			Mining.resetMining(player);
		} else if (player.playerIsFletching == true) {// fletching
			player.playerIsFletching = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		} else if (player.isSmithing) {// smithing
			player.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (player.isSmelting) {
			player.isSmelting = false;
		} else if (player.isCrafting == true) {
			player.isCrafting = false;
		} else if (player.isPotionMaking) {// herblore
			Herblore.resetHerblore(player);
		} else if (player.isWoodcutting) {// woodcutting
			Woodcutting.stopWoodcutting(player);
		} else if (player.isSpinning) {// spinning
			player.isSpinning = false;
		} else if (player.isPotCrafting == true) {// pot crafting
			player.isPotCrafting = false;
		} else if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		} else if (player.playerSkilling[13]) {// smelting
			Smelting.resetSmelting(player);
		}
	}

	public static void resetSkills(Client c) {// call when walking, dropping,
												// picking up, leveling up
		if (c.playerSkilling[10]) {// fishing
			Fishing.resetFishing(c);
		} else if (c.isMining) {// mining
			Mining.resetMining(c);
		} else if (c.playerIsFletching == true) {// fletching
			c.playerIsFletching = false;
		} else if (c.playerIsCooking) {// cooking
			Cooking.resetCooking(c);
		} else if (c.isSmithing) {// smithing
			c.isSmithing = false;
		} else if (isSkilling[12]) {// crafting
			isSkilling[12] = false;
		} else if (c.isSmelting) {
			c.isSmelting = false;
		} else if (c.isCrafting == true) {
			c.isCrafting = false;
		} else if (c.isPotionMaking) {// herblore
			Herblore.resetHerblore(c);
		} else if (c.isWoodcutting) {// woodcutting
			Woodcutting.stopWoodcutting(c);
		} else if (c.isSpinning) {// spinning
			c.isSpinning = false;
		} else if (c.isPotCrafting == true) {// pot crafting
			c.isPotCrafting = false;
		}
	}

	public static boolean canDoAction(int timer) {
		if (System.currentTimeMillis() >= lastAction) {
			lastAction = System.currentTimeMillis() + timer;
			return true;
		}
		return false;
	}

	public static boolean membersOnly(Client c) {
		if (c.membership == false) {
			c.getActionSender()
					.sendMessage("This is a members only skill.");
			return false;
		}
		return true;
	}

	public static boolean noInventorySpace(Client c, String skill) {
		if (c.getItemAssistant().freeSlots() == 0) {
			c.getActionSender().sendMessage(
					"You don't have enough inventory space to continue "
							+ skill + "!");
			return false;
		}
		return true;
	}
	
	public static void deleteTime(Client c) {
		c.doAmount--;
	}
	
	public static void stopEvents(Client c, int eventId) {
		CycleEventHandler.getSingleton().stopEvents(c, eventId);
	}

	public static void send1Item(Client c, int itemId) {
		c.getPlayerAssistant().sendFrame246(1746, view190 ? 190 : 150, itemId);
		c.getPlayerAssistant().sendFrame126(
				getLine(c) + "" + ItemAssistant.getItemName(itemId) + "", 2799);
		c.getPlayerAssistant().sendChatInterface(4429);
	}

	public static boolean playerHasItem(Client c, String itemName,
			String skill, int itemID) {
		if (!c.getItemAssistant().playerHasItem(itemID, 1)) {
			c.getActionSender().sendMessage(
					"You dont have any " + itemName + " to continue " + skill
							+ "!");
			c.getDialogueHandler().sendStatement(
					"You dont have any " + itemID + " to continue " + skill
							+ "!");
			return false;
		}
		return true;
	}

	public static void resetPlayerSkillVariables(Client c) {
		for (int i = 0; i < 20; i++) {
			if (c.playerSkilling[i]) {
				for (int l = 0; l < 15; l++) {
					c.playerSkillProp[i][l] = -1;
				}
			}
		}
	}

	public static boolean hasRequiredLevel(final Client c, int id, int lvlReq,
			String skill, String event) {
		if (c.playerLevel[id] < lvlReq) {
			c.getActionSender().sendMessage(
					"You don't have a high enough " + skill + " level to "
							+ event + ".");
			return false;
		}
		return true;
	}

	public static String getLine(Client c) {
		return c.below459 ? "\\n\\n\\n\\n" : "\\n\\n\\n\\n\\n";
	}


}
