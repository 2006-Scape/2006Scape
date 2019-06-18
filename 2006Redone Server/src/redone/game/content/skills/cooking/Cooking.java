package redone.game.content.skills.cooking;

import java.security.SecureRandom;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

public class Cooking extends SkillHandler {

	private static SecureRandom cookingRandom = new SecureRandom(); // The
																	// random
																	// factor

	private static enum CookingItems {
		//raw, cooked, burnt, levelreq, exp, stopburn, stopburn w/gloves, name
		SHRIMP(317, 315, 7954, 1, 30, 34, 30, "shrimp"), SARDINE(327, 325, 369,
				1, 40, 38, 38, "sardine"), HERRING(345, 347, 357, 5, 50, 41,
				41, "herring"), TROUT(335, 333, 343, 15, 70, 50, 50, "trout"), TUNA(
				359, 361, 367, 30, 100, 64, 63, "tuna"), ANCHOVIES(321, 319,
				323, 5, 45, 34, 34, "anchovies"), RAW_BEEF(2132, 2142, 2146, 1,
				30, 33, 33, "raw beef"), 
				RAW_RAT(2134, 2142, 2146, 1, 30, 33, 33, "raw rat meat"), 
				BURNT_MEAT(2142, 2146, 2146, 1, 1, 100, 100, "cooked meat"),
				RAW_CHICKEN(2138, 2140, 2144, 1, 30, 33, 33, "raw chicken"), 
				RAW_BEAR_MEAT(2136, 2142, 2146, 1, 30, 33, 33, "raw bear meat"), 
				MACKERAL(353, 355, 357, 10, 60, 45, 45, "mackeral"), 
				SALMON(331, 329, 343, 25, 90, 58, 55, "salmon"),
				UNCOOKED_BERRY_PIE(2321, 2325, 2329, 10, 78, 50, 50, "uncooked pie"),
				PIKE(
				349, 351, 343, 20, 80, 59, 59, "pike"), KARAMBWAN(3142, 3144,
				3146, 1, 80, 20, 20, "karambwan"), LOBSTER(377, 379, 381, 40,
				120, 74, 68, "lobster"), SWORDFISH(371, 373, 375, 50, 140, 86,
				81, "swordfish"), MONKFISH(7944, 7946, 7948, 62, 150, 92, 90,
				"monkfish"), SHARK(383, 385, 387, 76, 210, 100, 94, "shark"), MANTA_RAY(
				389, 391, 393, 91, 169, 100, 100, "manta ray"),
				SEAWEED(401, 1781, 1781, 1, 1, 1, 1, "sea weed"),
				CURRY(2009, 2011, 2013, 60, 280, 74, 74, "curry");

		int rawItem, cookedItem, burntItem, levelReq, xp, stopBurn,
				stopBurnGloves;
		String name;

		private CookingItems(int rawItem, int cookedItem, int burntItem,
				int levelReq, int xp, int stopBurn, int stopBurnGloves,
				String name) {
			this.rawItem = rawItem;
			this.cookedItem = cookedItem;
			this.burntItem = burntItem;
			this.levelReq = levelReq;
			this.xp = xp;
			this.stopBurn = stopBurn;
			this.name = name;
		}

		private int getRawItem() {
			return rawItem;
		}

		private int getCookedItem() {
			return cookedItem;
		}

		private int getBurntItem() {
			return burntItem;
		}

		private int getLevelReq() {
			return levelReq;
		}

		private int getXp() {
			return xp;
		}

		private int getStopBurn() {
			return stopBurn;
		}

		@SuppressWarnings("unused")
		// causes bugs
		private int getStopBurnGloves() {
			return stopBurnGloves;
		}

		private String getName() {
			return name;
		}
	}

	public static CookingItems forId(int itemId) {
		for (CookingItems item : CookingItems.values()) {
			if (itemId == item.getRawItem()) {
				return item;
			}
		}
		return null;
	}

	public static void makeBreadOptions(Client c, int item) {
		if (c.getItemAssistant().playerHasItem(1929)
				&& c.getItemAssistant().playerHasItem(1933)
				&& item == c.breadID) {
			c.getItemAssistant().deleteItem(1929, 1);
			c.getItemAssistant().deleteItem(1933, 1);
			c.getItemAssistant().addItem(1925, 1);
			c.getItemAssistant().addItem(1931, 1);
			c.getItemAssistant().addItem(item, 1);
			c.getActionSender().sendMessage(
					"You make the water and flour to make some "
							+ ItemAssistant.getItemName(item) + ".");
		}
		c.getPlayerAssistant().closeAllWindows();
	}

	public static void pastryCreation(Client c, int itemID1, int itemID2,
			int giveItem, String message) {
		if (c.getItemAssistant().playerHasItem(itemID1)
				&& c.getItemAssistant().playerHasItem(itemID2)) {
			c.getItemAssistant().deleteItem(itemID1, 1);
			c.getItemAssistant().deleteItem(itemID2, 1);
			c.getItemAssistant().addItem(giveItem, 1);
			if (message.equalsIgnoreCase("")) {
				c.getActionSender().sendMessage(
						"You mix the two ingredients and get an "
								+ ItemAssistant.getItemName(giveItem) + ".");
			} else {
				c.getActionSender().sendMessage(message);
			}
		}
	}

	public static void cookingAddon(Client c, int itemID1, int itemID2,
			int giveItem, int requiredLevel, int expGained) {
		if (c.playerLevel[7] >= requiredLevel) {
			if (c.getItemAssistant().playerHasItem(itemID1)
					&& c.getItemAssistant().playerHasItem(itemID2)) {
				c.getItemAssistant().deleteItem(itemID1, 1);
				c.getItemAssistant().deleteItem(itemID2, 1);
				c.getItemAssistant().addItem(giveItem, 1);
				c.getPlayerAssistant().addSkillXP(expGained, 7);
				c.getActionSender().sendMessage(
						"You create a " + ItemAssistant.getItemName(giveItem)
								+ ".");
			}
		} else {
			c.getActionSender().sendMessage(
					"You don't have the required level to make an "
							+ ItemAssistant.getItemName(giveItem));
		}
	}

	private static void setCooking(Client c) {
		c.playerIsCooking = true;
		c.stopPlayerSkill = true;
	}

	public static void resetCooking(Client c) {
		c.playerIsCooking = false;
		c.stopPlayerSkill = false;
	}

	private static void viewCookInterface(Client c, int item) {
		c.getPlayerAssistant().sendChatInterface(1743);
		c.getPlayerAssistant().sendFrame246(13716, view190 ? 190 : 170, item);
		c.getPlayerAssistant().sendFrame126(getLine(c) + "" + ItemAssistant.getItemName(item) + "", 13717);
	}

	public static String getLine(Client c) {
		return c.below459 ? "\\n\\n\\n\\n" : "\\n\\n\\n\\n\\n";
	}

	public static boolean startCooking(Client c, int itemId, int objectId) {
		CookingItems item = forId(itemId);
		if (item != null) {
			if (c.playerLevel[c.playerCooking] < item.getLevelReq()) {
				c.getPlayerAssistant().removeAllWindows();
				c.getDialogueHandler().sendStatement(
						"You need a Cooking level of " + item.getLevelReq()
								+ " to cook this.");
				c.nextChat = 0;
				return false;
			}
			if (c.playerIsCooking) {
				c.getPlayerAssistant().removeAllWindows();
				return false;
			}
			if (!COOKING) {
				c.getActionSender().sendMessage(
						"This skill is currently disabled.");
				return false;
			}
			// save the id of the item and object for the cooking interface.
			c.cookingItem = itemId;
			c.cookingObject = objectId;
			viewCookInterface(c, item.getRawItem());
			return true;
		}
		return false;
	}

	private static boolean getSuccess(Client c, int burnBonus, int levelReq,
			int stopBurn) {
		if (c.playerLevel[c.playerCooking] >= stopBurn) {
			return true;
		}
		double burn_chance = 55.0 - burnBonus;
		double cook_level = c.playerLevel[c.playerCooking];
		double lev_needed = levelReq;
		double burn_stop = stopBurn;
		double multi_a = burn_stop - lev_needed;
		double burn_dec = burn_chance / multi_a;
		double multi_b = cook_level - lev_needed;
		burn_chance -= multi_b * burn_dec;
		double randNum = cookingRandom.nextDouble() * 100.0;
		return burn_chance <= randNum;
	}

	public static void cookItem(final Client c, final int itemId,
			final int amount, final int objectId) {
		final CookingItems item = forId(itemId);

		if (item != null) {
			setCooking(c);
			RandomEventHandler.addRandom(c);
			c.getPlayerAssistant().removeAllWindows();
			c.doAmount = amount;
			if (c.doAmount > c.getItemAssistant().getItemAmount(itemId)) {
				c.doAmount = c.getItemAssistant().getItemAmount(itemId);
			}
			if (objectId > 0) {
				c.startAnimation(objectId == 2732 ? 897 : 896);
			}
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (!c.playerIsCooking) {
						resetCooking(c);
						container.stop();
						return;
					}
					if (!c.getItemAssistant().playerHasItem(item.getRawItem(),
							1)) {
						c.getActionSender().sendMessage(
								"You have run out of " + item.getName()
										+ " to cook.");
						resetCooking(c);
						container.stop();
						return;
					}

					// if (c.playerEquipment[c.playerHands] != 775)
					boolean burn = !getSuccess(c, 3, item.getLevelReq(),
							item.getStopBurn());
					/*
					 * else burn = !getSuccess(c, 3, item.getLevelReq(), item
					 * .getStopBurnGloves());
					 */
					c.getItemAssistant().deleteItem(item.getRawItem(),
							c.getItemAssistant().getItemSlot(itemId), 1);
					if (!burn) {
						c.getActionSender().sendMessage(
								"You successfully cook the "
										+ item.getName().toLowerCase() + ".");
						if (Constants.SOUND) {
							c.getActionSender().sendSound(
									SoundList.COOK_ITEM, 100, 0);
						}
						c.getPlayerAssistant().addSkillXP(item.getXp(),
								c.playerCooking);
						c.getItemAssistant().addItem(item.getCookedItem(), 1);
					} else {
						c.getActionSender().sendMessage(
								"Oops! You accidentally burnt the "
										+ item.getName().toLowerCase() + "!");
						c.getItemAssistant().addItem(item.getBurntItem(), 1);
					}
					c.doAmount--;
					if (c.disconnected) {
						container.stop();
						return;
					}
					if (objectId < 0) {
						container.stop();
						return;
					}
					if (c.doAmount > 0) {
						if (objectId > 0) {
							c.startAnimation(objectId == 2732 ? 897 : 896);
						}
					} else if (c.doAmount == 0) {
						resetCooking(c);
						container.stop();
					}
				}

				@Override
				public void stop() {
					
				}
			}, 4);
		}
	}
}
