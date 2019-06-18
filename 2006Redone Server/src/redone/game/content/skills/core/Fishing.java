package redone.game.content.skills.core;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.randomevents.RiverTroll;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.game.players.antimacro.AntiBotting;
import redone.util.Misc;


public class Fishing extends SkillHandler {
	
	public static void randomEvents(Client client) {
		if (Misc.random(350) == 5) {
			RiverTroll.spawnRiverTroll(client);
		}
		if (Misc.random(350) == 0 && RiverTroll.hasRiverTroll == false) {
			AntiBotting.botCheckInterface(client);
		}
		if (RiverTroll.hasRiverTroll == false) {
			RandomEventHandler.addRandom(client);
		}
	}

	/**
	 * Resets the Event according to the ID. id = 10 (According to skill.)
	 */
	protected static int eventId = 10;

	public static int[][] data = {
			// dataid, levelreq, item needed, bait, item recieved, exp,
			// animation,
			// secondfish, levelreq, secondexp
			{ 1, 1, 303, -1, 317, 10, 621, 321, 15, 40 }, // SHRIMP + ANCHOVIES
			{ 2, 5, 307, 313, 327, 20, 622, 345, 10, 30 }, // SARDINE + HERRING
			{ 3, 16, 305, -1, 353, 20, 620, -1, -1, -1 }, // MACKEREL
			{ 4, 20, 309, 314, 335, 50, 622, 331, 30, 70 }, // TROUT
			{ 5, 23, 305, -1, 341, 45, 619, 363, 46, 100 }, // BASS + COD
			{ 6, 25, 307, 313, 349, 60, 622, -1, -1, -1 }, // PIKE
			{ 7, 35, 311, -1, 359, 80, 618, 371, 50, 100 }, // TUNA + SWORDIE
			{ 8, 40, 301, -1, 377, 90, 619, -1, -1, -1 }, // LOBSTER
			{ 9, 62, 303, -1, 7944, 120, 621, -1, -1, -1 }, // Monkfish
			{ 10, 76, 311, -1, 383, 110, 618, -1, -1, -1 } // Shark
	};

	private static String[][] messages = { { "You cast out your net." }, // SHRIMP
																			// +
																			// ANCHOVIES
			{ "You cast out your line." }, // SARDINE + HERRING
			{ "You start harpooning fish." }, // TUNA + SWORDIE
			{ "You attempt to catch a lobster." }, // LOBSTER
	};

	private static void attemptdata(final Client c, final int npcId) {
		if (!FISHING) {
			c.getActionSender().sendMessage(c.disabled());
			return;
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't fish right now!");
			return;
		}
		if (c.playerSkillProp[10][4] > 0) {
			c.playerSkilling[10] = false;
			return;
		}
		if (!noInventorySpace(c, "fishing")) {
			return;
		}
		resetFishing(c);
		for (int i = 0; i < data.length; i++) {
			if (npcId == data[i][0]) {
				if (c.playerLevel[c.playerFishing] < data[i][1]) {
					c.getDialogueHandler().sendStatement(
							"You need a fishing level of at least "
									+ data[i][1]
									+ " in order to fish at this spot.");
					return;
				}
				if (!hasFishingEquipment(c, data[i][2])) {
					return;
				}
				if (data[i][3] > 0) {
					if (!c.getItemAssistant().playerHasItem(data[i][3])) {
						c.getDialogueHandler().sendStatement(
								"You need more "
										+ ItemAssistant.getItemName(data[i][3])
												.toLowerCase().toLowerCase()
										+ " in order to fish at this spot.");
						return;
					}
				}
				c.playerSkillProp[10][0] = data[i][6]; // ANIM
				c.playerSkillProp[10][1] = data[i][4]; // FISH
				c.playerSkillProp[10][2] = data[i][5]; // XP
				c.playerSkillProp[10][3] = data[i][3]; // BAIT
				c.playerSkillProp[10][4] = data[i][2]; // EQUIP
				c.playerSkillProp[10][5] = data[i][7]; // sFish
				c.playerSkillProp[10][6] = data[i][8]; // sLvl
				c.playerSkillProp[10][7] = data[i][4]; // FISH
				c.playerSkillProp[10][8] = data[i][9]; // sXP
				c.playerSkillProp[10][9] = Misc.random(1) == 0 ? 7 : 5;
				c.playerSkillProp[10][10] = data[i][0]; // INDEX

				if (c.playerSkilling[10]) {
					return;
				}
				c.playerSkilling[10] = true;
				if (c.tutorialProgress == 6) { // if tutorial prog = 6
					c.startAnimation(c.playerSkillProp[10][0]);
					c.stopPlayerSkill = true;
					c.getActionSender().drawHeadicon(0, 0, 0, 0); // deletes
																		// headicon
					c.getActionSender().chatbox(6180);
					c.getDialogueHandler()
							.chatboxText(
									c,
									"This should only take a few seconds.",
									"As you gain Fishing experience you'll find that there are many",
									"types of fish and many ways to catch them.",
									"", "Please wait");
					c.getActionSender().chatbox(6179);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
								@Override
								public void execute(CycleEventContainer container) {

									if (c.playerSkillProp[10][5] > 0) {
										if (c.playerLevel[c.playerFishing] >= c.playerSkillProp[10][6]) {
											c.playerSkillProp[10][1] = c.playerSkillProp[10][Misc
													.random(1) == 0 ? 7 : 5];
										}
									}

									if (!c.stopPlayerSkill) {
										container.stop();
									}
									if (!c.playerSkilling[10]) {
										container.stop();
									}

									if (c.playerSkillProp[10][1] > 0) {
										c.startAnimation(c.playerSkillProp[10][0]);
									}

								}

								@Override
								public void stop() {
									resetFishing(c);
								}
							}, 5);
					CycleEventHandler.getSingleton().addEvent(c,
							new CycleEvent() {

								@Override
								public void execute(
										CycleEventContainer container) {
									if (c.playerSkillProp[10][5] > 0) {
										if (c.playerLevel[c.playerFishing] >= c.playerSkillProp[10][6]) {
											c.playerSkillProp[10][1] = c.playerSkillProp[10][Misc
													.random(1) == 0 ? 7 : 5];
										}
									}
									if (c.playerSkillProp[10][2] > 0) {
										c.getPlayerAssistant().addSkillXP(
												c.playerSkillProp[10][2],
												c.playerFishing);
									}
									if (c.playerSkillProp[10][1] > 0) {
										c.getItemAssistant().addItem(c.playerSkillProp[10][1], 1);
										c.startAnimation(c.playerSkillProp[10][0]);
										c.getDialogueHandler().sendDialogues(3019, -1);
										container.stop();
									}
									if (!noInventorySpace(c, "fishing")) {
										container.stop();
									}
									if (!c.stopPlayerSkill) {
										container.stop();
									}
									if (!c.playerSkilling[10]) {
										container.stop();
									}
								}

								@Override
								public void stop() {
									resetFishing(c);
								}
							}, getTimer(c, npcId) + 5 + playerFishingLevel(c));
					return;
				}

				// end of tutorial island fishing

				c.getActionSender().sendMessage("" + messages(c));
				// c.getPA().sendSound(379, 100, 1); // fishing
				c.startAnimation(c.playerSkillProp[10][0]);
				c.stopPlayerSkill = true;
				CycleEventHandler.getSingleton().addEvent(eventId, c,
						new CycleEvent() {

							@Override
							public void execute(CycleEventContainer container) {
								if (c.playerSkillProp[10][3] > 0) {

									if (!c.getItemAssistant().playerHasItem(
											c.playerSkillProp[10][3])) {
										c.getActionSender()
												.sendMessage(
														"You don't have any "
																+ ItemAssistant
																		.getItemName(c.playerSkillProp[10][3])
																+ " left!");
										c.getActionSender()
												.sendMessage(
														"You need "
																+ ItemAssistant
																		.getItemName(c.playerSkillProp[10][3])
																+ " to fish here.");
										resetFishing(c);
										container.stop();
									}
								}
								if (c.playerSkillProp[10][5] > 0) {
									if (c.playerLevel[c.playerFishing] >= c.playerSkillProp[10][6]) {
										c.playerSkillProp[10][1] = c.playerSkillProp[10][Misc
												.random(1) == 0 ? 7 : 5];
									}
								}
								if (!hasFishingEquipment(c,
										c.playerSkillProp[10][4])) {
									resetFishing(c);
									container.stop();
								}
								if (!noInventorySpace(c, "fishing")) {
									resetFishing(c);
									container.stop();
								}
								if (!c.stopPlayerSkill) {
									container.stop();
								}
								if (!c.playerSkilling[10]) {
									resetFishing(c);
									container.stop();
								}
								if (c.playerSkillProp[10][1] > 0) {
									c.startAnimation(c.playerSkillProp[10][0]);
									// c.getPA().sendSound(379, 100, 1); //
									// fishing
								}

							}

							@Override
							public void stop() {
							}
						}, 5);
				CycleEventHandler.getSingleton().addEvent(eventId, c,
						new CycleEvent() {

							@Override
							public void execute(CycleEventContainer container) {
								if (c.playerSkillProp[10][5] > 0) {
									if (c.playerLevel[c.playerFishing] >= c.playerSkillProp[10][6]) {
										c.playerSkillProp[10][1] = c.playerSkillProp[10][Misc
												.random(1) == 0 ? 7 : 5];
									}
								}
								if (c.playerSkillProp[10][1] > 0) {
									c.getActionSender()
											.sendMessage(
													"You catch "
															+ (c.playerSkillProp[10][1] == 321
																	|| c.playerSkillProp[10][1] == 317
																	|| c.playerSkillProp[10][1] == 7944 ? "some "
																	: "a ")
															+ ItemAssistant
																	.getItemName(
																			c.playerSkillProp[10][1])
																	.toLowerCase()
																	.replace(
																			"raw ",
																			"")
															+ ".");
								}
								if (c.playerSkillProp[10][1] > 0) {
									randomEvents(c);
								}
								if (c.isBotting == true) {
									c.getActionSender().sendMessage("You can't get any items, until you confirm you are not a bot.");
									c.getActionSender().sendMessage("If you need to relog you can do so.");
									container.stop();
								}
								if (c.playerSkillProp[10][1] > 0) {
									c.getItemAssistant().deleteItem(c.playerSkillProp[10][3], c.getItemAssistant().getItemSlot(c.playerSkillProp[10][3]), 1);
									c.getItemAssistant().addItem(c.playerSkillProp[10][1], 1);
									c.startAnimation(c.playerSkillProp[10][0]);
								}
								if (c.playerSkillProp[10][5] > 0
										&& c.playerLevel[c.playerFishing] >= c.playerSkillProp[10][6]) {
									c.getPlayerAssistant().addSkillXP(
											c.playerSkillProp[10][8],
											c.playerFishing);
								} else if (c.playerSkillProp[10][7] > 0) {
									c.getPlayerAssistant().addSkillXP(
											c.playerSkillProp[10][2],
											c.playerFishing);
								}
								if (c.playerSkillProp[10][3] > 0) {
									if (!c.getItemAssistant().playerHasItem(
											c.playerSkillProp[10][3])) {
										c.getDialogueHandler()
												.sendStatement(
														"You have run out of "
																+ ItemAssistant
																		.getItemName(
																				c.playerSkillProp[10][3])
																		.toLowerCase()
																		.toLowerCase()
																+ ".");
										// c.getPacketDispatcher().sendMessage("You don't have any "+
										// ItemAssistant.getItemName(c.playerSkillProp[10][3])
										// +" left!");
										// c.getPacketDispatcher().sendMessage("You need "+
										// ItemAssistant.getItemName(c.playerSkillProp[10][3])
										// +" to fish here.");
										container.stop();
									}
								}
								if (!hasFishingEquipment(c,
										c.playerSkillProp[10][4])) {
									resetFishing(c);
									container.stop();
								}
								if (!noInventorySpace(c, "fishing")) {
									resetFishing(c);
									container.stop();
								}
								if (!c.stopPlayerSkill) {
									container.stop();
								}
								if (!c.playerSkilling[10]) {
									resetFishing(c);
									container.stop();
								}
							}

							@Override
							public void stop() {
							}
						}, getTimer(c, npcId) + 5 + playerFishingLevel(c));
			}
		}
	}

	public static boolean hasFishingEquipment(Client c, int equipment) {
		if (!c.getItemAssistant().playerHasItem(equipment)) {
			if (equipment == 311) {
				if (!c.getItemAssistant().playerHasItem(311)
						&& !c.getItemAssistant().playerHasItem(10129)
						&& c.playerEquipment[3] != 10129) {
					c.getActionSender().sendMessage(
							"You need a "
									+ ItemAssistant.getItemName(equipment)
											.toLowerCase() + " to fish here.");
					resetFishing(c);
					return false;
				}
			} else {
				resetFishing(c);
				c.getActionSender().sendMessage(
						"You need a " + ItemAssistant.getItemName(equipment)
								+ " to fish here.");
				return false;
			}
		}
		return true;
	}

	public static void resetFishing(Client c) {
		c.startAnimation(65535);
		c.stopPlayerSkill = false;
		c.playerSkilling[10] = false;
		c.fishingWhirlPool = false;
		stopEvents(c, eventId);
		for (int i = 0; i < 11; i++) {
			c.playerSkillProp[10][i] = -1;
		}
	}

	public static String messages(Client c) {
		if (c.playerSkillProp[10][10] == 1 || c.playerSkillProp[10][10] == 9) {
			// etc
			return messages[0][0];
		}

		if (c.playerSkillProp[10][10] == 2 || c.playerSkillProp[10][10] == 3
				|| c.playerSkillProp[10][10] == 4
				|| c.playerSkillProp[10][10] == 5
				|| c.playerSkillProp[10][10] == 6) {
			return messages[1][0];
		}

		if (c.playerSkillProp[10][10] == 7 || c.playerSkillProp[10][10] == 10) {
			return messages[2][0];
		}

		if (c.playerSkillProp[10][10] == 8) {
			return messages[3][0];
		}

		return null;
	}

	private static int playerFishingLevel(Client c) {
		return 10 - (int) Math.floor(c.playerLevel[c.playerFishing] / 10);
	}

	private final static int getTimer(Client c, int npcId) {
		switch (npcId) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		case 4:
			return 4;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 5;
		case 8:
			return 5;
		case 9:
			return 8;
		case 10:
			return 12;
		case 11:
			return 13;
		case 12:
			return 13;
		default:
			return -1;
		}
	}

	public static void fishingNPC(Client c, int i, int l) {
		switch (i) {
		case 1:
			switch (l) {
			case 319:
			case 329:
			case 323:
			case 325:
			case 326:
			case 327:
			case 330:
			case 332:
			case 404:
			case 316: // NET + BAIT
				Fishing.attemptdata(c, 1);
				break;
			case 334:
			case 313: // NET + HARPOON
				Fishing.attemptdata(c, 3);
				break;
			case 322: // NET + HARPOON
				Fishing.attemptdata(c, 5);
				break;

			case 309: // LURE
			case 310:
			case 403:
			case 311:
			case 314:
			case 315:
			case 317:
			case 318:
			case 328:
			case 331:
				Fishing.attemptdata(c, 4);
				break;
			case 1191:
				Fishing.attemptdata(c, 9);
				break;
			case 312:
			case 321:
			case 405:
			case 324: // CAGE + HARPOON
				Fishing.attemptdata(c, 8);
				break;
			}
			break;
		case 2:
			switch (l) {
			case 326:
			case 327:
			case 330:
			case 332:
			case 404:
			case 316: // BAIT + NET
				Fishing.attemptdata(c, 2);
				break;
			case 319:
			case 323:
			case 325: // BAIT + NET
				Fishing.attemptdata(c, 9);
				break;
			case 310:
			case 311:
			case 314:
			case 315:
			case 317:
			case 318:
			case 328:
			case 329:
			case 331:
			case 403:
			case 309: // BAIT + LURE
				Fishing.attemptdata(c, 6);
				break;
			case 312:
			case 321:
			case 405:
			case 1191:
			case 324:// SWORDIES+TUNA-CAGE+HARPOON
				Fishing.attemptdata(c, 7);
				break;
			case 313:
			case 322:
			case 334: // NET+HARPOON
				Fishing.attemptdata(c, 10);
				break;
			}
			break;
		}
	}

	public static boolean fishingNPC(Client c, int npc) {
		for (int i = 308; i < 335; i++) {
			if (npc == i) {
				return true;
			}
			if (npc == 1191) {
				return true;
			}
		}
		return false;
	}
}
