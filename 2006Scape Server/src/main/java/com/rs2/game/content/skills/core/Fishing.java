package com.rs2.game.content.skills.core;

import com.rs2.GameConstants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.randomevents.RiverTroll;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.ItemAssistant;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;


public class Fishing extends SkillHandler {
	
	public static void randomEvents(Player client) {
		if (Misc.random(350) == 5) {
			RiverTroll.spawnRiverTroll(client);
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

	private static void attemptdata(final Player c, final int npcId) {
		if (!FISHING) {
			c.getPacketSender().sendMessage(c.disabled());
			return;
		}
		if (c.playerSkillProp[GameConstants.FISHING][4] > 0) {
			c.playerSkilling[GameConstants.FISHING] = false;
			return;
		}
		if (!noInventorySpace(c, "fishing")) {
			return;
		}
		resetFishing(c);
		for (int i = 0; i < data.length; i++) {
			if (npcId == data[i][0]) {
				if (c.playerLevel[GameConstants.FISHING] < data[i][1]) {
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
				c.playerSkillProp[GameConstants.FISHING][0] = data[i][6]; // ANIM
				c.playerSkillProp[GameConstants.FISHING][1] = data[i][4]; // FISH
				c.playerSkillProp[GameConstants.FISHING][2] = data[i][5]; // XP
				c.playerSkillProp[GameConstants.FISHING][3] = data[i][3]; // BAIT
				c.playerSkillProp[GameConstants.FISHING][4] = data[i][2]; // EQUIP
				c.playerSkillProp[GameConstants.FISHING][5] = data[i][7]; // sFish
				c.playerSkillProp[GameConstants.FISHING][6] = data[i][8]; // sLvl
				c.playerSkillProp[GameConstants.FISHING][7] = data[i][4]; // FISH
				c.playerSkillProp[GameConstants.FISHING][8] = data[i][9]; // sXP
				c.playerSkillProp[GameConstants.FISHING][9] = Misc.random(1) == 0 ? 7 : 5;
				c.playerSkillProp[GameConstants.FISHING][10] = data[i][0]; // INDEX

				if (c.playerSkilling[GameConstants.FISHING]) {
					return;
				}
				c.playerSkilling[GameConstants.FISHING] = true;
				if (c.tutorialProgress == 6) { // if tutorial prog = 6
					c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
					c.stopPlayerSkill = true;
					c.getPacketSender().drawHeadicon(0, 0, 0, 0); // deletes
																		// headicon
					c.getPacketSender().chatbox(6180);
					c.getDialogueHandler()
							.chatboxText(
									"This should only take a few seconds.",
									"As you gain Fishing experience you'll find that there are many",
									"types of fish and many ways to catch them.",
									"", "Please wait");
					c.getPacketSender().chatbox(6179);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
								@Override
								public void execute(CycleEventContainer container) {

									if (c.playerSkillProp[GameConstants.FISHING][5] > 0) {
										if (c.playerLevel[GameConstants.FISHING] >= c.playerSkillProp[GameConstants.FISHING][6]) {
											c.playerSkillProp[GameConstants.FISHING][1] = c.playerSkillProp[GameConstants.FISHING][Misc
													.random(1) == 0 ? 7 : 5];
										}
									}

									if (!c.stopPlayerSkill) {
										container.stop();
									}
									if (!c.playerSkilling[GameConstants.FISHING]) {
										container.stop();
									}

									if (c.playerSkillProp[GameConstants.FISHING][1] > 0) {
										c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
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
									if (c.playerSkillProp[GameConstants.FISHING][5] > 0) {
										if (c.playerLevel[GameConstants.FISHING] >= c.playerSkillProp[GameConstants.FISHING][6]) {
											c.playerSkillProp[GameConstants.FISHING][1] = c.playerSkillProp[GameConstants.FISHING][Misc
													.random(1) == 0 ? 7 : 5];
										}
									}
									if (c.playerSkillProp[GameConstants.FISHING][2] > 0) {
										c.getPlayerAssistant().addSkillXP(
												c.playerSkillProp[GameConstants.FISHING][2],
												GameConstants.FISHING);
									}
									if (c.playerSkillProp[GameConstants.FISHING][1] > 0) {
										c.getItemAssistant().addItem(c.playerSkillProp[GameConstants.FISHING][1], 1);
										c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
										c.getDialogueHandler().sendDialogues(3019, -1);
										container.stop();
									}
									if (!noInventorySpace(c, "fishing")) {
										container.stop();
									}
									if (!c.stopPlayerSkill) {
										container.stop();
									}
									if (!c.playerSkilling[GameConstants.FISHING]) {
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

				c.getPacketSender().sendMessage("" + messages(c));
				// c.getPA().sendSound(379, 100, 1); // fishing
				c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
				c.stopPlayerSkill = true;
				CycleEventHandler.getSingleton().addEvent(eventId, c,
						new CycleEvent() {

							@Override
							public void execute(CycleEventContainer container) {
								if (c.playerSkillProp[GameConstants.FISHING][3] > 0) {

									if (!c.getItemAssistant().playerHasItem(
											c.playerSkillProp[GameConstants.FISHING][3])) {
										c.getPacketSender()
												.sendMessage(
														"You don't have any "
																+ ItemAssistant
																		.getItemName(c.playerSkillProp[GameConstants.FISHING][3])
																+ " left!");
										c.getPacketSender()
												.sendMessage(
														"You need "
																+ ItemAssistant
																		.getItemName(c.playerSkillProp[GameConstants.FISHING][3])
																+ " to fish here.");
										resetFishing(c);
										container.stop();
									}
								}
								if (c.playerSkillProp[GameConstants.FISHING][5] > 0) {
									if (c.playerLevel[GameConstants.FISHING] >= c.playerSkillProp[GameConstants.FISHING][6]) {
										c.playerSkillProp[GameConstants.FISHING][1] = c.playerSkillProp[GameConstants.FISHING][Misc
												.random(1) == 0 ? 7 : 5];
									}
								}
								if (!hasFishingEquipment(c,
										c.playerSkillProp[GameConstants.FISHING][4])) {
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
								if (!c.playerSkilling[GameConstants.FISHING]) {
									resetFishing(c);
									container.stop();
								}
								if (c.playerSkillProp[GameConstants.FISHING][1] > 0) {
									c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
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
								if (c.playerSkillProp[GameConstants.FISHING][5] > 0) {
									if (c.playerLevel[GameConstants.FISHING] >= c.playerSkillProp[GameConstants.FISHING][6]) {
										c.playerSkillProp[GameConstants.FISHING][1] = c.playerSkillProp[GameConstants.FISHING][Misc
												.random(1) == 0 ? 7 : 5];
									}
								}
								if (c.playerSkillProp[GameConstants.FISHING][1] > 0) {
									c.getPacketSender()
											.sendMessage(
													"You catch "
															+ (c.playerSkillProp[GameConstants.FISHING][1] == 321
																	|| c.playerSkillProp[GameConstants.FISHING][1] == 317
																	|| c.playerSkillProp[GameConstants.FISHING][1] == 7944 ? "some "
																	: "a ")
															+ ItemAssistant
																	.getItemName(
																			c.playerSkillProp[GameConstants.FISHING][1])
																	.toLowerCase()
																	.replace(
																			"raw ",
																			"")
															+ ".");
								}
								if (c.playerSkillProp[GameConstants.FISHING][1] > 0 && c.randomEventsEnabled) {
									randomEvents(c);
								}
								if (c.playerSkillProp[GameConstants.FISHING][1] > 0) {
									c.getItemAssistant().deleteItem(c.playerSkillProp[GameConstants.FISHING][3], c.getItemAssistant().getItemSlot(c.playerSkillProp[GameConstants.FISHING][3]), 1);
									c.getItemAssistant().addItem(c.playerSkillProp[GameConstants.FISHING][1], 1);
									c.startAnimation(c.playerSkillProp[GameConstants.FISHING][0]);
								}
								if (c.playerSkillProp[GameConstants.FISHING][5] > 0
										&& c.playerLevel[GameConstants.FISHING] >= c.playerSkillProp[GameConstants.FISHING][6]) {
									c.getPlayerAssistant().addSkillXP(
											c.playerSkillProp[GameConstants.FISHING][8],
											GameConstants.FISHING);
								} else if (c.playerSkillProp[GameConstants.FISHING][7] > 0) {
									c.getPlayerAssistant().addSkillXP(
											c.playerSkillProp[GameConstants.FISHING][2],
											GameConstants.FISHING);
								}
								if (c.playerSkillProp[GameConstants.FISHING][3] > 0) {
									if (!c.getItemAssistant().playerHasItem(
											c.playerSkillProp[GameConstants.FISHING][3])) {
										c.getDialogueHandler()
												.sendStatement(
														"You have run out of "
																+ ItemAssistant
																		.getItemName(
																				c.playerSkillProp[GameConstants.FISHING][3])
																		.toLowerCase()
																		.toLowerCase()
																+ ".");
										// c.getPacketDispatcher().sendMessage("You don't have any "+
										// ItemAssistant.getItemName(c.playerSkillProp[GameConstants.FISHING][3])
										// +" left!");
										// c.getPacketDispatcher().sendMessage("You need "+
										// ItemAssistant.getItemName(c.playerSkillProp[GameConstants.FISHING][3])
										// +" to fish here.");
										container.stop();
									}
								}
								if (!hasFishingEquipment(c,
										c.playerSkillProp[GameConstants.FISHING][4])) {
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
								if (!c.playerSkilling[GameConstants.FISHING]) {
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

	public static boolean hasFishingEquipment(Player c, int equipment) {
		if (!c.getItemAssistant().playerHasItem(equipment)) {
			if (equipment == 311) {
				if (!c.getItemAssistant().playerHasItem(311)
						&& !c.getItemAssistant().playerHasItem(10129)
						&& c.playerEquipment[3] != 10129) {
					c.getPacketSender().sendMessage(
							"You need a "
									+ ItemAssistant.getItemName(equipment)
											.toLowerCase() + " to fish here.");
					resetFishing(c);
					return false;
				}
			} else {
				resetFishing(c);
				c.getPacketSender().sendMessage(
						"You need a " + ItemAssistant.getItemName(equipment)
								+ " to fish here.");
				return false;
			}
		}
		return true;
	}

	public static void resetFishing(Player player) {
		player.startAnimation(65535);
		player.stopPlayerSkill = false;
		player.playerSkilling[GameConstants.FISHING] = false;
		player.fishingWhirlPool = false;
		stopEvents(player, eventId);
		for (int i = 0; i < 11; i++) {
			player.playerSkillProp[GameConstants.FISHING][i] = -1;
		}
	}

	public static String messages(Player c) {
		if (c.playerSkillProp[GameConstants.FISHING][10] == 1 || c.playerSkillProp[GameConstants.FISHING][10] == 9) {
			// etc
			return messages[0][0];
		}

		if (c.playerSkillProp[GameConstants.FISHING][10] == 2 || c.playerSkillProp[GameConstants.FISHING][10] == 3
				|| c.playerSkillProp[GameConstants.FISHING][10] == 4
				|| c.playerSkillProp[GameConstants.FISHING][10] == 5
				|| c.playerSkillProp[GameConstants.FISHING][10] == 6) {
			return messages[1][0];
		}

		if (c.playerSkillProp[GameConstants.FISHING][10] == 7 || c.playerSkillProp[GameConstants.FISHING][10] == 10) {
			return messages[2][0];
		}

		if (c.playerSkillProp[GameConstants.FISHING][10] == 8) {
			return messages[3][0];
		}

		return null;
	}

	private static int playerFishingLevel(Player c) {
		return 10 - (int) Math.floor(c.playerLevel[GameConstants.FISHING] / 10);
	}

	private final static int getTimer(Player c, int npcId) {
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

	public static void fishingNPC(Player c, int i, int l) {
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

	public static boolean fishingNPC(Player c, int npc) {
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
