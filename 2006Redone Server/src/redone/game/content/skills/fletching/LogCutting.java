package redone.game.content.skills.fletching;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author Tom
 */

public class LogCutting {

	private static final int KNIFE = 946, CUT_SOUND = 375;
	
	public static void resetFletching(Client c) {
		if (c.playerIsFletching == true) {
			c.playerIsFletching = false;
			c.startAnimation(65535);
		}
	}

	public static void cutLog(final Client c, final int product, final int level, final double xp, int amount) {
		if (c.isSpinning) {
			c.isSpinning = false;
		}
		if (c.isWoodcutting) {
			c.isWoodcutting = false;
		}
		c.doAmount = amount;
		c.getPlayerAssistant().removeAllWindows();
		if (c.playerLevel[9] < level) {
			c.getActionSender()
					.sendMessage(
							"You need a fletching level of " + level
									+ " to make this.");
			return;
		}
		c.playerIsFletching = true;
		c.startAnimation(1248);
		c.getActionSender().sendSound(CUT_SOUND, 100, 0);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.getItemAssistant().playerHasItem(KNIFE)) {
					c.getItemAssistant().deleteItem2(LogCuttingInterface.log, 1);
					if (product == 52) {
						c.getItemAssistant().addItem(product, 15);
					} else {
						c.getItemAssistant().addItem(product, 1);
					}
					c.getActionSender().sendMessage("You carefully cut the " + ItemAssistant.getItemName(LogCuttingInterface.log) + " into an " + ItemAssistant.getItemName(product) + ".");
					c.getPlayerAssistant().addSkillXP(xp, c.playerFletching);
					c.doAmount--;
				}

				if (!c.getItemAssistant().playerHasItem(LogCuttingInterface.log)) {
					container.stop();
					return;
				}
				
				if (c.playerIsFletching == false) {
					container.stop();
					return;
				}

				if (c.doAmount <= 0) {
					container.stop();
					return;
				}

			}

			@Override
			public void stop() {
				c.playerIsFletching = false;
				c.startAnimation(65535);
				return;
			}
		}, 3);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.playerIsFletching == false) {
					container.stop();
					return;
				}
				c.getActionSender().sendSound(CUT_SOUND, 100, 0);
				c.startAnimation(1248);

			}

			@Override
			public void stop() {
				c.playerIsFletching = false;
				c.startAnimation(65535);
				return;
			}
		}, 3);
	}

	public static void handleClick(Client c, int buttonId) {
		if (c.doAmount == 28 && c.playerIsFletching == true) {
			c.getPlayerAssistant().closeAllWindows();
			c.playerIsFletching = false;
			return;
		}
		switch (buttonId) {
		/*
		 * normal log (item on interface 3)
		 */
		case 34185:
		if (c.playerIsFletching == true) {
			cutLog(c, 52, 1, 5, 1);
			c.playerIsFletching = false;
			return;
		}
		break;
		case 34184:
			cutLog(c, 52, 1, 5, 5);
			return;
		case 34183:
			cutLog(c, 52, 1, 5, 10);
			return;
		case 34182:
			cutLog(c, 52, 1, 5, 28);
			return;
		case 34189:
		if (c.playerIsFletching == true) {
			cutLog(c, 50, 5, 5, 1);
			c.playerIsFletching = false;
			return;
		}
		break;
		case 34188:
			cutLog(c, 50, 5, 5, 5);
			return;
		case 34187:
			cutLog(c, 50, 5, 5, 10);
			return;
		case 34186:
			cutLog(c, 50, 5, 5, 28);
			return;
		case 34193:
		if (c.playerIsFletching == true) {
			cutLog(c, 48, 10, 10, 1);
			c.playerIsFletching = false;
			return;
		}
		break;
		case 34192:
			cutLog(c, 48, 10, 10, 5);
			return;
		case 34191:
			cutLog(c, 48, 10, 10, 10);
			return;
		case 34190:
			cutLog(c, 48, 10, 10, 28);
			return;
			/*
			 * rest of the log's (item on interface 2)
			 */
			/*
			 * first item
			 */
		case 34170:
			if (LogCuttingInterface.log == 1521 && c.playerIsFletching == true) {
				cutLog(c, 54, 20, 16.5, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1519 && c.playerIsFletching == true) {
				cutLog(c, 60, 35, 33.3, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1517 && c.playerIsFletching == true) {
				cutLog(c, 64, 50, 50, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1515 && c.playerIsFletching == true) {
				cutLog(c, 68, 65, 67.5, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1513 && c.playerIsFletching == true) {
				cutLog(c, 72, 80, 83.25, 1);
				c.playerIsFletching = false;
			}
			return;
		case 34169:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 54, 20, 16.5, 5);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 60, 35, 33.3, 5);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 64, 50, 50, 5);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 68, 65, 67.5, 5);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 72, 80, 83.25, 5);
			}
			return;
		case 34168:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 54, 20, 16.5, 10);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 60, 35, 33.3, 10);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 64, 50, 50, 10);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 68, 65, 67.5, 10);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 72, 80, 83.25, 10);
			}
			return;
		case 34167:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 54, 20, 16.5, 28);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 60, 35, 33.3, 28);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 64, 50, 50, 28);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 68, 65, 67.5, 28);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 72, 80, 83.25, 28);
			}
			return;
			/*
			 * second item
			 */
		case 34174:
			if (LogCuttingInterface.log == 1521 && c.playerIsFletching == true) {
				cutLog(c, 56, 25, 25, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1519 && c.playerIsFletching == true) {
				cutLog(c, 58, 40, 41.5, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1517 && c.playerIsFletching == true) {
				cutLog(c, 62, 55, 58.3, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1515 && c.playerIsFletching == true) {
				cutLog(c, 66, 70, 70, 1);
				c.playerIsFletching = false;
			}
			if (LogCuttingInterface.log == 1513 && c.playerIsFletching == true) {
				cutLog(c, 70, 85, 91.5, 1);
				c.playerIsFletching = false;
			}
			return;
		case 34173:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 56, 25, 25, 5);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 58, 40, 41.5, 5);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 62, 55, 58.3, 5);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 66, 70, 70, 5);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 70, 85, 91.5, 5);
			}
			return;
		case 34172:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 56, 25, 25, 10);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 58, 40, 41.5, 10);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 62, 55, 58.3, 10);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 66, 70, 70, 10);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 70, 85, 91.5, 10);
			}
			return;
		case 34171:
			if (LogCuttingInterface.log == 1521) {
				cutLog(c, 56, 25, 25, 28);
			}
			if (LogCuttingInterface.log == 1519) {
				cutLog(c, 58, 40, 41.5, 28);
			}
			if (LogCuttingInterface.log == 1517) {
				cutLog(c, 62, 55, 58.3, 28);
			}
			if (LogCuttingInterface.log == 1515) {
				cutLog(c, 66, 70, 70, 28);
			}
			if (LogCuttingInterface.log == 1513) {
				cutLog(c, 70, 85, 91.5, 28);
			}
			return;
		}
	}

	public static void wolfBoneArrow(Client c) {
		if (c.getItemAssistant().playerHasItem(2859)
				&& c.getItemAssistant().playerHasItem(1755)) {
			final int amount = c.getItemAssistant().getItemCount(2859);
			final int makeAmount = c.getItemAssistant().getItemCount(2859)
					+ c.getItemAssistant().getItemCount(2859) * Misc.random(4);
			if (!c.getItemAssistant().playerHasItem(2859)) {
				c.getDialogueHandler().sendStatement(
						"You don't have any bones left to chisel.");
				c.nextChat = 0;
				return;
			}
			c.startAnimation(1248);
			c.getItemAssistant().deleteItem2(2859, amount);
			c.getItemAssistant().addItem(2861, makeAmount);
			c.getPlayerAssistant().addSkillXP(3 * amount, c.playerFletching);
			c.getActionSender().sendMessage(
					"You turn your " + ItemAssistant.getItemName(2859)
							+ " into " + ItemAssistant.getItemName(2861) + ".");
		}
	}

	public static void flightedArrow(Client c) {// to do
		if (c.playerLevel[c.playerFletching] < 5) {
			c.getDialogueHandler().sendStatement(
					"You need 5 fletching to fletch this.");
			c.nextChat = 0;
			return;
		}
		if (!c.getItemAssistant().playerHasItem(314)
				|| !c.getItemAssistant().playerHasItem(2864)) {
			c.getDialogueHandler().sendStatement(
					"You don't enough materials to make these arrows.");
			c.nextChat = 0;
			return;
		}
		if (c.getItemAssistant().playerHasItem(314)
				&& c.getItemAssistant().playerHasItem(2864)) {
			final int feather = c.getItemAssistant().getItemCount(314), arrowShaft = c
					.getItemAssistant().getItemCount(2864);
			if (feather == arrowShaft * 4) {
				c.startAnimation(1248);
				c.getItemAssistant().deleteItem2(314, feather * 4);
				c.getItemAssistant().deleteItem2(2864, arrowShaft);
				c.getItemAssistant().addItem(2865, arrowShaft);
				c.getActionSender().sendMessage(
						"You turn your " + ItemAssistant.getItemName(2864)
								+ " into " + ItemAssistant.getItemName(2865)
								+ "(s).");
				/*
				 * } else if (feather > arrowShaft * 4) {//to fix
				 * c.startAnimation(1248); c.getItemAssistant().deleteItem2(314,
				 * feather*4); c.getItemAssistant().deleteItem2(2864,
				 * arrowShaft); c.getItemAssistant().addItem(2865, arrowShaft);
				 * c.getPacketDispatcher().sendMessage("You turn your " +
				 * ItemAssistant.getItemName(2864) + " into " +
				 * ItemAssistant.getItemName(2865) + "(s)."); } else if (feather
				 * < arrowShaft * 4) {//to fix c.startAnimation(1248);
				 * c.getItemAssistant().deleteItem2(314, feather*4);
				 * c.getItemAssistant().deleteItem2(2864, arrowShaft);
				 * c.getItemAssistant().addItem(2865, arrowShaft);
				 * c.getPacketDispatcher().sendMessage("You turn your " +
				 * ItemAssistant.getItemName(2864) + " (s) into " +
				 * ItemAssistant.getItemName(2865) + "(s).");
				 */
			} else {
				c.getActionSender()
						.sendMessage(
								"You need 4 times the amount of feathers as arrow shafts to do this.");
			}
		}
	}

	public static void ogreArrow(Client c) {
		if (c.playerLevel[c.playerFletching] < 5) {
			c.getDialogueHandler().sendStatement(
					"You need 5 fletching to fletch this.");
			c.nextChat = 0;
			return;
		}
		if (!c.getItemAssistant().playerHasItem(2861)
				|| !c.getItemAssistant().playerHasItem(2865)) {
			c.getDialogueHandler().sendStatement(
					"You don't enough materials to make these arrows.");
			c.nextChat = 0;
			return;
		}
		final int wolfBoneArrow = c.getItemAssistant().getItemCount(2861), flightedArrow = c
				.getItemAssistant().getItemCount(2865);
		if (c.getItemAssistant().playerHasItem(2861)
				&& c.getItemAssistant().playerHasItem(2865)) {
			if (wolfBoneArrow == flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, wolfBoneArrow);
				c.getPlayerAssistant().addSkillXP(1 * wolfBoneArrow,
						c.playerFletching);
				c.getItemAssistant().deleteItem2(2861, wolfBoneArrow);
				c.getItemAssistant().deleteItem2(2865, wolfBoneArrow);
				c.getActionSender().sendMessage(
						"You turn your " + ItemAssistant.getItemName(2865)
								+ " (s) into "
								+ ItemAssistant.getItemName(2866) + "(s).");
			} else if (wolfBoneArrow > flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, flightedArrow);
				c.getPlayerAssistant().addSkillXP(1 * flightedArrow,
						c.playerFletching);
				c.getItemAssistant().deleteItem2(2861, flightedArrow);
				c.getItemAssistant().deleteItem2(2865, flightedArrow);
				c.getActionSender().sendMessage(
						"You turn your " + ItemAssistant.getItemName(2865)
								+ " (s) into "
								+ ItemAssistant.getItemName(2866) + "(s).");
			} else if (wolfBoneArrow < flightedArrow) {
				c.startAnimation(1248);
				c.getItemAssistant().addItem(2866, wolfBoneArrow);
				c.getPlayerAssistant().addSkillXP(1 * wolfBoneArrow,
						c.playerFletching);
				c.getItemAssistant().deleteItem2(2861, wolfBoneArrow);
				c.getItemAssistant().deleteItem2(2865, wolfBoneArrow);
				c.getActionSender().sendMessage(
						"You turn your " + ItemAssistant.getItemName(2865)
								+ " (s) into "
								+ ItemAssistant.getItemName(2866) + "(s).");
			}
		}
	}

	public static void makeShafts(Client c) {
		if (c.getItemAssistant().playerHasItem(2862)
				&& c.getItemAssistant().playerHasItem(946)) {
			final int amount = c.getItemAssistant().getItemCount(2862);
			final int makeAmount = c.getItemAssistant().getItemCount(2862)
					+ c.getItemAssistant().getItemCount(2862) * Misc.random(4);
			if (!c.getItemAssistant().playerHasItem(2862)) {
				c.getDialogueHandler().sendStatement(
						"You don't have any logs left to fletch.");
				c.nextChat = 0;
				return;
			}
			c.startAnimation(1248);
			c.getItemAssistant().deleteItem2(2862, amount);
			c.getItemAssistant().addItem(2864, makeAmount);
			c.getPlayerAssistant().addSkillXP(2 * amount, c.playerFletching);
			c.getActionSender().sendMessage(
					"You turn your " + ItemAssistant.getItemName(2862)
							+ " (s) into " + ItemAssistant.getItemName(2864)
							+ "(s).");
		}
	}
}
