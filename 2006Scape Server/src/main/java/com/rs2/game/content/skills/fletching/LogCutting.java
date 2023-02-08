package com.rs2.game.content.skills.fletching;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * @author Tom
 */

public class LogCutting {

	private static final int KNIFE = 946, CUT_SOUND = 375;
	
	public static void resetFletching(Player player) {
		if (player.playerIsFletching) {
			player.playerIsFletching = false;
			player.startAnimation(65535);
		}
	}

	public static void cutLog(final Player player, final int product, final int level, final double xp, int amount) {
		player.doAmount = amount;
		player.getPacketSender().closeAllWindows();
		if (player.playerLevel[Constants.FLETCHING] < level) {
			player.getPacketSender().sendMessage("You need a fletching level of " + level + " to make this.");
			return;
		}
		if (!player.playerIsFletching) {
			player.playerIsFletching = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (player.doAmount <= 0 || !player.getItemAssistant().playerHasItem(player.getFletching().log) 
							|| !player.getItemAssistant().playerHasItem(KNIFE) || player.isWoodcutting || player.isCrafting 
							|| player.isMoving || player.isMining || player.isBusy || player.isShopping || player.isSmithing 
							|| player.isFiremaking || player.isSpinning || player.isPotionMaking || player.playerIsFishing 
							|| player.isBanking || player.isSmelting || player.isTeleporting || player.isHarvesting 
							|| player.playerIsCooking || player.isPotCrafting) {
						container.stop();
						return;
					} else {
						player.startAnimation(1248);
						player.getItemAssistant().deleteItem(player.getFletching().log, 1);
						if (product == 52) {
							player.getItemAssistant().addItem(product, 15);
							player.getPacketSender().sendMessage("You carefully cut the " + DeprecatedItems.getItemName(player.getFletching().log) + " into 15 " + DeprecatedItems.getItemName(product) + "s.");

						} else {
							player.getItemAssistant().addItem(product, 1);
							player.getPacketSender().sendMessage("You carefully cut the " + DeprecatedItems.getItemName(player.getFletching().log) + " into a " + DeprecatedItems.getItemName(product) + ".");

						}
						player.getPlayerAssistant().addSkillXP(xp, Constants.FLETCHING);
						player.doAmount--;
						player.getPacketSender().sendSound(CUT_SOUND, 100, 0);
					}
				}

				@Override
				public void stop() {
					player.playerIsFletching = false;
				}
			}, 3);
		}
	}

	public static void handleClick(Player player, int buttonId) {
		if (player.doAmount == 28 && player.playerIsFletching) {
			player.getPacketSender().closeAllWindows();
			player.playerIsFletching = false;
			return;
		}
		switch (buttonId) {
		/*
		 * normal log (item on interface 3)
		 */
		case 34185:
		if (player.playerIsFletching) {
			cutLog(player, 52, 1, 5, 1);
			player.playerIsFletching = false;
			return;
		}
		break;
		case 34184:
			cutLog(player, 52, 1, 5, 5);
			return;
		case 34183:
			cutLog(player, 52, 1, 5, 10);
			return;
		case 34182:
			cutLog(player, 52, 1, 5, 28);
			return;
		case 34189:
		if (player.playerIsFletching) {
			cutLog(player, 50, 5, 5, 1);
			player.playerIsFletching = false;
			return;
		}
		break;
		case 34188:
			cutLog(player, 50, 5, 5, 5);
			return;
		case 34187:
			cutLog(player, 50, 5, 5, 10);
			return;
		case 34186:
			cutLog(player, 50, 5, 5, 28);
			return;
		case 34193:
		if (player.playerIsFletching) {
			cutLog(player, 48, 10, 10, 1);
			player.playerIsFletching = false;
			return;
		}
		break;
		case 34192:
			cutLog(player, 48, 10, 10, 5);
			return;
		case 34191:
			cutLog(player, 48, 10, 10, 10);
			return;
		case 34190:
			cutLog(player, 48, 10, 10, 28);
			return;
			/*
			 * rest of the log's (item on interface 2)
			 */
			/*
			 * first item
			 */
		case 34170:
			if (player.getFletching().log == 1521 && player.playerIsFletching) {
				cutLog(player, 54, 20, 16.5, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1519 && player.playerIsFletching) {
				cutLog(player, 60, 35, 33.3, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1517 && player.playerIsFletching) {
				cutLog(player, 64, 50, 50, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1515 && player.playerIsFletching) {
				cutLog(player, 68, 65, 67.5, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1513 && player.playerIsFletching) {
				cutLog(player, 72, 80, 83.25, 1);
				player.playerIsFletching = false;
			}
			return;
		case 34169:
			if (player.getFletching().log == 1521) {
				cutLog(player, 54, 20, 16.5, 5);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 60, 35, 33.3, 5);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 64, 50, 50, 5);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 68, 65, 67.5, 5);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 72, 80, 83.25, 5);
			}
			return;
		case 34168:
			if (player.getFletching().log == 1521) {
				cutLog(player, 54, 20, 16.5, 10);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 60, 35, 33.3, 10);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 64, 50, 50, 10);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 68, 65, 67.5, 10);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 72, 80, 83.25, 10);
			}
			return;
		case 34167:
			if (player.getFletching().log == 1521) {
				cutLog(player, 54, 20, 16.5, 28);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 60, 35, 33.3, 28);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 64, 50, 50, 28);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 68, 65, 67.5, 28);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 72, 80, 83.25, 28);
			}
			return;
			/*
			 * second item
			 */
		case 34174:
			if (player.getFletching().log == 1521 && player.playerIsFletching) {
				cutLog(player, 56, 25, 25, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1519 && player.playerIsFletching) {
				cutLog(player, 58, 40, 41.5, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1517 && player.playerIsFletching) {
				cutLog(player, 62, 55, 58.3, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1515 && player.playerIsFletching) {
				cutLog(player, 66, 70, 70, 1);
				player.playerIsFletching = false;
			}
			if (player.getFletching().log == 1513 && player.playerIsFletching) {
				cutLog(player, 70, 85, 91.5, 1);
				player.playerIsFletching = false;
			}
			return;
		case 34173:
			if (player.getFletching().log == 1521) {
				cutLog(player, 56, 25, 25, 5);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 58, 40, 41.5, 5);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 62, 55, 58.3, 5);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 66, 70, 70, 5);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 70, 85, 91.5, 5);
			}
			return;
		case 34172:
			if (player.getFletching().log == 1521) {
				cutLog(player, 56, 25, 25, 10);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 58, 40, 41.5, 10);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 62, 55, 58.3, 10);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 66, 70, 70, 10);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 70, 85, 91.5, 10);
			}
			return;
		case 34171:
			if (player.getFletching().log == 1521) {
				cutLog(player, 56, 25, 25, 28);
			}
			if (player.getFletching().log == 1519) {
				cutLog(player, 58, 40, 41.5, 28);
			}
			if (player.getFletching().log == 1517) {
				cutLog(player, 62, 55, 58.3, 28);
			}
			if (player.getFletching().log == 1515) {
				cutLog(player, 66, 70, 70, 28);
			}
			if (player.getFletching().log == 1513) {
				cutLog(player, 70, 85, 91.5, 28);
			}
			return;
		}
	}

	public static void wolfBoneArrow(Player c) {
		if (c.getItemAssistant().playerHasItem(2859) && c.getItemAssistant().playerHasItem(1755)) {
			final int amount = c.getItemAssistant().getItemAmount(2859);
			final int makeAmount = c.getItemAssistant().getItemAmount(2859) + c.getItemAssistant().getItemAmount(2859) * Misc.random(4);
			if (!c.getItemAssistant().playerHasItem(2859)) {
				c.getDialogueHandler().sendStatement("You don't have any bones left to chisel.");
				c.nextChat = 0;
				return;
			}
			c.startAnimation(1248);
			c.getItemAssistant().deleteItem(2859, amount);
			c.getItemAssistant().addItem(2861, makeAmount);
			c.getPlayerAssistant().addSkillXP(3 * amount, Constants.FLETCHING);
			c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2859) + " into " + DeprecatedItems.getItemName(2861) + ".");
		}
	}

	public static void flightedArrow(Player c) {// to do
		if (c.playerLevel[Constants.FLETCHING] < 5) {
			c.getDialogueHandler().sendStatement("You need 5 fletching to fletch this.");
			c.nextChat = 0;
			return;
		}
		if (!c.getItemAssistant().playerHasItem(314) || !c.getItemAssistant().playerHasItem(2864)) {
			c.getDialogueHandler().sendStatement("You don't enough materials to make these arrows.");
			c.nextChat = 0;
			return;
		}
		if (c.getItemAssistant().playerHasItem(314) && c.getItemAssistant().playerHasItem(2864)) {
			final int feather = c.getItemAssistant().getItemAmount(314), arrowShaft = c.getItemAssistant().getItemAmount(2864);
			if (feather == arrowShaft * 4) {
				c.startAnimation(1248);
				c.getItemAssistant().deleteItem(314, feather * 4);
				c.getItemAssistant().deleteItem(2864, arrowShaft);
				c.getItemAssistant().addItem(2865, arrowShaft);
				c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2864) + " into " + DeprecatedItems.getItemName(2865) + "(s).");
			} else {
				c.getPacketSender().sendMessage("You need 4 times the amount of feathers as arrow shafts to do this.");
			}
		}
	}

	public static void ogreArrow(Player c) {
		if (c.playerLevel[Constants.FLETCHING] < 5) {
			c.getDialogueHandler().sendStatement("You need 5 fletching to fletch this.");
			c.nextChat = 0;
			return;
		}
		if (!c.getItemAssistant().playerHasItem(2861) || !c.getItemAssistant().playerHasItem(2865)) {
			c.getDialogueHandler().sendStatement("You don't enough materials to make these arrows.");
			c.nextChat = 0;
			return;
		}
		final int wolfBoneArrow = c.getItemAssistant().getItemAmount(2861), flightedArrow = c.getItemAssistant().getItemAmount(2865);
		if (c.getItemAssistant().playerHasItem(2861) && c.getItemAssistant().playerHasItem(2865)) {
			if (wolfBoneArrow == flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, wolfBoneArrow);
				c.getPlayerAssistant().addSkillXP(1 * wolfBoneArrow, Constants.FLETCHING);
				c.getItemAssistant().deleteItem(2861, wolfBoneArrow);
				c.getItemAssistant().deleteItem(2865, wolfBoneArrow);
				c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2865) + " (s) into " + DeprecatedItems.getItemName(2866) + "(s).");
			} else if (wolfBoneArrow > flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, flightedArrow);
				c.getPlayerAssistant().addSkillXP(1 * flightedArrow, Constants.FLETCHING);
				c.getItemAssistant().deleteItem(2861, flightedArrow);
				c.getItemAssistant().deleteItem(2865, flightedArrow);
				c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2865) + " (s) into " + DeprecatedItems.getItemName(2866) + "(s).");
			} else if (wolfBoneArrow < flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, wolfBoneArrow);
				c.getPlayerAssistant().addSkillXP(1 * wolfBoneArrow, Constants.FLETCHING);
				c.getItemAssistant().deleteItem(2861, wolfBoneArrow);
				c.getItemAssistant().deleteItem(2865, wolfBoneArrow);
				c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2865) + " (s) into " + DeprecatedItems.getItemName(2866) + "(s).");
			}
		}
	}

	public static void makeShafts(Player c) {
		if (c.getItemAssistant().playerHasItem(2862) && c.getItemAssistant().playerHasItem(946)) {
			final int amount = c.getItemAssistant().getItemAmount(2862);
			final int makeAmount = c.getItemAssistant().getItemAmount(2862) + c.getItemAssistant().getItemAmount(2862) * Misc.random(4);
			if (!c.getItemAssistant().playerHasItem(2862)) {
				c.getDialogueHandler().sendStatement("You don't have any logs left to fletch.");
				c.nextChat = 0;
				return;
			}
			c.startAnimation(1248);
			c.getItemAssistant().deleteItem(2862, amount);
			c.getItemAssistant().addItem(2864, makeAmount);
			c.getPlayerAssistant().addSkillXP(2 * amount, Constants.FLETCHING);
			c.getPacketSender().sendMessage("You turn your " + DeprecatedItems.getItemName(2862) + " (s) into " + DeprecatedItems.getItemName(2864) + "(s).");
		}
	}
}
