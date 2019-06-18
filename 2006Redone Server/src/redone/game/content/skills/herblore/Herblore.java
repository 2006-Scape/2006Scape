package redone.game.content.skills.herblore;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

public class Herblore extends SkillHandler {

	private static int itemToAdd = -1, itemToDelete = -1, itemToDelete2 = -1,
			potExp = -1;
	private static final int ANIM = 363;

	private static final int[][] CLEAN_DATA = { { 199, 249, 1, 3 },
			{ 201, 251, 5, 4 }, { 203, 253, 11, 5 }, { 205, 255, 20, 6 },
			{ 207, 257, 25, 8 }, { 3049, 2998, 30, 8 },
			{ 12174, 12172, 35, 8 }, { 209, 259, 40, 9 },
			{ 14836, 14854, 30, 8 }, { 211, 261, 48, 10 },
			{ 213, 263, 54, 11 }, { 3051, 3000, 59, 12 }, { 215, 265, 65, 13 },
			{ 2485, 2481, 67, 13 }, { 217, 267, 70, 14 }, { 219, 269, 75, 15 },

	};

	public static void handleHerbCleaning(final Client c, final int itemId,
			final int itemSlot) {
		for (int[] element : CLEAN_DATA) {
			if (itemId == element[0]) {
				if (c.playerLevel[15] < element[2]) {
					c.getActionSender().sendMessage("You cannot clean this herb.");
					c.getActionSender().sendMessage("You need a higher Herblore level.");
					return;
				}
				c.getItemAssistant().deleteItem(itemId, itemSlot, 1);
				c.getItemAssistant().addItem(element[1], 1);
				c.getActionSender().sendMessage("The herb is a " + ItemAssistant.getItemName(element[1]) + ".");
				c.getPlayerAssistant().addSkillXP(element[3], 15);
			}
		}
	}

	//potion, item, finished, level req, exp
	private static final int[][] POTION_DATA = { { 91, 221, 121, 1, 25 },
			{ 93, 235, 175, 5, 38 }, { 95, 225, 115, 12, 50 },
			{ 97, 223, 127, 22, 63 }, { 99, 239, 133, 30, 75 },
			{ 97, 9736, 9741, 36, 84 }, { 99, 231, 139, 38, 88 },
			{ 101, 221, 145, 45, 100 }, { 101, 235, 181, 48, 106 },
			{ 103, 231, 151, 50, 112 }, { 105, 225, 157, 55, 125 },
			{ 107, 239, 163, 66, 150 }, { 2483, 241, 2454, 69, 158 },
			{ 109, 245, 169, 72, 163 }, { 2483, 3138, 3042, 76, 173 },
			{ 111, 247, 189, 78, 175 }, { 3002, 6693, 6687, 81, 180 },
			{ 5935, 6016, 5936, 73, 0}, { 5936, 223, 5937, 73, 165},
			{ 5935, 2398, 5939, 82, 0}, { 5939, 6018, 5940, 82, 190},
			{ 227, 263, 105, 55, 0}, { 105, 241, 187, 60, 137 },
			{ 227, 249, 91, 1, 0 }, { 227, 251, 93, 5, 0 },
			{ 227, 253, 95, 12, 0 }, { 227, 255, 97, 22, 0 },
			{ 227, 257, 99, 30, 0 }, { 227, 259, 101, 34, 0 },
			{ 227, 261, 103, 45, 0 }, { 3004, 223, 3026, 63, 142 },
			{ 227, 265, 107, 66, 0 }, { 227, 267, 109, 72, 0 },
			{ 227, 269, 111, 75, 0 }, { 227, 2481, 2483, 67, 0 },
			{ 227, 3000, 3004, 59, 0 }, { 227, 2998, 3002, 30, 0 },
			{ 1975, 97, 3010, 26, 67 }, };

	public static void setupPotion(final Client c, int useItem, int itemUsed) {
		for (int[] element : POTION_DATA) {
			if (useItem == element[0] && itemUsed == element[1]
					|| useItem == element[1] && itemUsed == element[0]) {
				if (c.playerLevel[c.playerHerblore] < element[3]) {
					c.getActionSender().sendMessage(
							"You need an herblore level of " + element[3]
									+ " to mix this potion.");
					return;
				}
				send1Item(c, element[2]);
				itemToDelete = element[1];
				itemToDelete2 = element[0];
				potExp = element[4];
				itemToAdd = element[2];
				c.isPotionMaking = true;
			}
		}
	}

	public static void makePotion(final Client c, int amount) {
		if (c.playerSkilling[c.playerHerblore]) {
			return;
		}
		if (itemToDelete <= 0 || itemToDelete2 <= 0) {
			return;
		}
		c.doAmount = amount;
		c.playerSkilling[c.playerHerblore] = true;
		c.getPlayerAssistant().removeAllWindows();
		c.startAnimation(ANIM);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				c.getItemAssistant().deleteItem(itemToDelete,
						c.getItemAssistant().getItemSlot(itemToDelete), 1);
				c.getItemAssistant().deleteItem(itemToDelete2,
						c.getItemAssistant().getItemSlot(itemToDelete2), 1);
				c.getItemAssistant().addItem(itemToAdd, 1);
				c.getActionSender().sendMessage(
						"You make a "
								+ ItemAssistant.getItemName(itemToAdd)
										.toLowerCase() + ".");
				c.getPlayerAssistant().addSkillXP(potExp, c.playerHerblore);
				deleteTime(c);
				if (!c.getItemAssistant().playerHasItem(itemToDelete2, 1)
						|| !c.getItemAssistant().playerHasItem(itemToDelete, 1)
						|| c.doAmount <= 0) {
					container.stop();
				}
				if (!c.isPotionMaking) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				resetHerblore(c);
			}
		}, 1);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				c.startAnimation(ANIM);
				if (!c.playerSkilling[c.playerHerblore] || !c.isPotionMaking) {
					container.stop();
				}
			}

			@Override
			public void stop() {
			}
		}, 1);
	}

	public static void resetHerblore(Client c) {
		itemToAdd = -1;
		itemToDelete = -1;
		itemToDelete2 = -1;
		potExp = -1;
		c.isGrinding = false;
		c.isPotionMaking = false;
		c.playerSkilling[c.playerHerblore] = false;
	}

	public static boolean isHerb(int item) {
		for (int[] element : CLEAN_DATA) {
			if (item == element[0]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isIngredient(int item) {
		for (int[] element : POTION_DATA) {
			if (item == element[0] || item == element[1]) {
				return true;
			}
		}
		return false;
	}

	public static void handleHerbloreButtons(Client c, int actionButtonId) {
		switch (actionButtonId) {
		case 10239:
			if (c.isPotionMaking) {
				makePotion(c, 1);
			}
			break;
		case 10238:
			if (c.isPotionMaking) {
				makePotion(c, 5);
			}
			break;
		case 6212:
			if (c.isPotionMaking) {
				makePotion(c, 10);
			}
			break;
		case 6211:
			if (c.isPotionMaking) {
				makePotion(c, 28);
			}
			break;
		}
	}
}
