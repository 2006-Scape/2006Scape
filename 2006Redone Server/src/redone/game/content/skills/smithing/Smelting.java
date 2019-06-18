package redone.game.content.skills.smithing;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * Made by
 * 
 * @author abysspartyy
 */

public class Smelting extends SkillHandler {

	private static int COPPER = 436, TIN = 438, IRON = 440, COAL = 453,
			MITH = 447, ADDY = 449, RUNE = 451, GOLD = 444, SILVER = 442;
	private final static int[] SMELT_FRAME = { 2405, 2406, 2407, 2409, 2410,
			2411, 2412, 2413 };
	private final static int[] SMELT_BARS = { 2349, 2351, 2355, 2353, 2357,
			2359, 2361, 2363 };

	/**
	 * Handles Smelting data.
	 */
	public static int[][] data = {
			// index ,lvl required, XP, item required, item2, COAL AMOUNT,
			// Final item(BAR)
			{ 1, 1, 6, COPPER, TIN, -1, 2349 }, // Bronze
			{ 2, 15, 12, IRON, -1, -1, 2351 }, // iron
			{ 3, 20, 17, IRON, COAL, 2, 2353 }, // Steel
			{ 4, 50, 30, MITH, COAL, 4, 2359 }, // Mith
			{ 5, 70, 37, ADDY, COAL, 6, 2361 }, // Addy
			{ 6, 85, 50, RUNE, COAL, 8, 2363 }, // Rune
			{ 7, 20, 13, SILVER, -1, -1, 2355 }, // Silver
			{ 8, 40, 22, GOLD, -1, -1, 2357 } // GOLD
	};

	/**
	 * Sends the interface
	 * 
	 * @param c
	 */
	public static void startSmelting(Client c, int object) {
		for (int j = 0; j < SMELT_FRAME.length; j++) {
			c.getPlayerAssistant().sendFrame246(SMELT_FRAME[j], 150,
					SMELT_BARS[j]);
		}
		c.getPlayerAssistant().sendChatInterface(2400);
		c.isSmelting = true;
	}

	/**
	 * Sets the amount of bars that can be smelted. (EG. 5,10,28 times)
	 * 
	 * @param c
	 * @param amount
	 */
	public static void doAmount(Client c, int amount, int bartype) {
		c.doAmount = amount;
		smeltBar(c, bartype);
	}

	/**
	 * Main method. Smelting
	 * 
	 * @param c
	 */
	private static void smeltBar(final Client c, int bartype) {
		for (int i = 0; i < data.length; i++) {
			if (bartype == data[i][0]) {
				if (c.playerLevel[c.playerSmithing] < data[i][1]) { // Smithing
																	// level
					c.getDialogueHandler().sendStatement(
							"You need a smithing level of at least "
									+ data[i][1] + " in order smelt this bar.");
					return;
				}

				if (data[i][3] > 0 && data[i][4] > 0) { // All OTHER bars
					if (!c.getItemAssistant().playerHasItem(data[i][3])
							|| !c.getItemAssistant().playerHasItem(data[i][4])) {
						c.getActionSender().sendMessage(
								"You need an "
										+ ItemAssistant.getItemName(data[i][3])
												.toLowerCase()
										+ " and "
										+ data[i][5]
										+ " "
										+ ItemAssistant.getItemName(data[i][4])
												.toLowerCase()
										+ " to make this bar.");

						c.getPlayerAssistant().removeAllWindows();
						return;
					}
				}

				if (data[i][4] < 0) { // Iron bar, Gold & Silver requirements
					if (!c.getItemAssistant().playerHasItem(data[i][3])) {
						c.getActionSender().sendMessage(
								"You need an "
										+ ItemAssistant.getItemName(data[i][3])
												.toLowerCase()
										+ " to make this bar.");
						c.getPlayerAssistant().removeAllWindows();
						return;
					}
				}

				if (data[i][5] > 0) { // Bars with more than 1 coal requirement
					if (!c.getItemAssistant().playerHasItem(data[i][4],
							data[i][5])) {
						c.getActionSender().sendMessage(
								"You need an "
										+ ItemAssistant.getItemName(data[i][3])
												.toLowerCase() + " and "
										+ data[i][5]
										+ " coal ores to make this bar.");
						c.getPlayerAssistant().removeAllWindows();
						return;
					}
				}

				if (c.playerSkilling[13]) {
					return;
				}

				c.playerSkilling[13] = true;
				c.stopPlayerSkill = true;

				c.playerSkillProp[13][0] = data[i][0];// index
				c.playerSkillProp[13][1] = data[i][1];// Level required
				c.playerSkillProp[13][2] = data[i][2];// XP
				c.playerSkillProp[13][3] = data[i][3];// item required
				c.playerSkillProp[13][4] = data[i][4];// item required 2
				c.playerSkillProp[13][5] = data[i][5];// coal amount
				c.playerSkillProp[13][6] = data[i][6];// Final Item

				c.getPlayerAssistant().removeAllWindows();
				c.startAnimation(899);
				c.getActionSender().sendSound(352, 100, 1);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						deleteTime(c);
						c.getItemAssistant().deleteItem(
								c.playerSkillProp[13][3], 1);
						if (c.playerSkillProp[13][5] == -1) {
							c.getItemAssistant().deleteItem(
									c.playerSkillProp[13][4], 1);
						}
						if (c.playerSkillProp[13][5] > 0) {// if coal amount is
															// >0
							c.getItemAssistant().deleteItem2(
									c.playerSkillProp[13][4],
									c.playerSkillProp[13][5]);
						}

						c.getActionSender().sendMessage(
								"You receive an " // Message
										+ ItemAssistant.getItemName(
												c.playerSkillProp[13][6])
												.toLowerCase() + ".");
						if (c.playerSkillProp[13][3] == GOLD
								&& c.playerSkillProp[13][4] == -1
								&& c.playerEquipment[c.playerHands] == 776) {
							c.getPlayerAssistant().addSkillXP(56.2,
									c.playerSmithing);
						} else {
							c.getPlayerAssistant().addSkillXP(
									c.playerSkillProp[13][2], c.playerSmithing);
						}
						c.getItemAssistant().addItem(c.playerSkillProp[13][6],
								1);// item

						// ///////////////////////////////CHECKING//////////////////////
						if (!c.getItemAssistant().playerHasItem(
								c.playerSkillProp[13][3], 1)) {
							c.getActionSender()
									.sendMessage(
											"You don't have enough ores to continue smelting!");
							resetSmelting(c);
							container.stop();
						}
						if (c.playerSkillProp[13][4] > 0) {
							if (!c.getItemAssistant().playerHasItem(
									c.playerSkillProp[13][4], 1)) {
								c.getActionSender()
										.sendMessage(
												"You don't have enough ores to continue smelting!");
								resetSmelting(c);
								container.stop();
							}
						}
						if (c.doAmount <= 0) {
							resetSmelting(c);
							container.stop();
						}
						if (!c.playerSkilling[13]) {
							resetSmelting(c);
							container.stop();
						}
						if (!c.stopPlayerSkill) {
							resetSmelting(c);
							container.stop();
						}

					}

					@Override
					public void stop() {

					}
				}, (int) 5.9);

				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {// animation

							@Override
							public void execute(CycleEventContainer container) {
								if (!c.playerSkilling[13]) {
									resetSmelting(c);
									container.stop();
									return;
								}
								c.startAnimation(899);
								c.getActionSender().sendSound(352, 100, 1);
								if (!c.stopPlayerSkill) {
									container.stop();
								}
							}

							@Override
							public void stop() {

							}
						}, (int) 5.8);

			}
		}
	}

	/**
	 * Gets the index from DATA for which bar to smelt
	 */
	public static void getBar(Client c, int i) {
		switch (i) {
		case 15147: // bronze (1)
			doAmount(c, 1, 1);// (c,amount,Index in data)
			break;
		case 15146: // bronze (5)
			doAmount(c, 5, 1);
			break;
		case 10247: // bronze (10)
			doAmount(c, 10, 1);
			break;
		case 9110:// bronze (X)
			doAmount(c, 28, 1);
			break;

		case 15151: // iron (1)
			doAmount(c, 1, 2);
			break;
		case 15150: // iron (5)
			doAmount(c, 5, 2);
			break;
		case 15149: // iron (10)
			doAmount(c, 10, 2);
			break;
		case 15148:// Iron (X)
			doAmount(c, 28, 2);
			break;

		case 15159: // Steel (1)
			doAmount(c, 1, 3);
			break;
		case 15158: // Steel (5)
			doAmount(c, 5, 3);
			break;
		case 15157: // Steel (10)
			doAmount(c, 10, 3);
			break;
		case 15156:// Steel (X)
			doAmount(c, 28, 3);
			break;

		case 29017: // mith (1)
			doAmount(c, 1, 4);
			break;
		case 29016: // mith (5)
			doAmount(c, 5, 4);
			break;
		case 24253: // mith (10)
			doAmount(c, 10, 4);
			break;
		case 16062:// Mith (X)
			doAmount(c, 28, 4);
			break;

		case 29022: // Addy (1)
			doAmount(c, 1, 5);
			break;
		case 29020: // Addy (5)
			doAmount(c, 5, 5);
			break;
		case 29019: // Addy (10)
			doAmount(c, 10, 5);
			break;
		case 29018:// Addy (X)
			doAmount(c, 28, 5);
			break;

		case 29026: // RUNE (1)
			doAmount(c, 1, 6);
			break;
		case 29025: // RUNE (5)
			doAmount(c, 5, 6);
			break;
		case 29024: // RUNE (10)
			doAmount(c, 10, 6);
			break;
		case 29023:// Rune (X)
			doAmount(c, 28, 6);
			break;

		case 15155:// SILVER (1)
			doAmount(c, 1, 7);
			break;
		case 15154:// SILVER (5)
			doAmount(c, 5, 7);
			break;
		case 15153:// SILVER (10)
			doAmount(c, 10, 7);
			break;
		case 15152:// SILVER (28)
			doAmount(c, 28, 7);
			break;

		case 15163:// Gold (1)
			doAmount(c, 1, 8);
			break;
		case 15162:// Gold (5)
			doAmount(c, 5, 8);
			break;
		case 15161:// Gold (10)
			doAmount(c, 10, 8);
			break;
		case 15160:// Gold (28)
			doAmount(c, 28, 8);
			break;
		}

	}

	/**
	 * Resets Smelting
	 */
	public static void resetSmelting(Client c) {
		c.playerSkilling[13] = false;
		c.stopPlayerSkill = false;
		c.isSmelting = false;
		for (int i = 0; i < 7; i++) {
			c.playerSkillProp[13][i] = -1;
		}
	}

}
