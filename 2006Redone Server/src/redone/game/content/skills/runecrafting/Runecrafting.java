package redone.game.content.skills.runecrafting;

import redone.game.content.music.sound.SoundList;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.players.Client;

/**
 * @author phl0w
 * @author highly edited by Andrew
 */

public class Runecrafting {

	private final Client c;

	public Runecrafting(Client c) {
		this.c = c;
	}

	public static void locate(Client c, int xPos, int yPos) {
		String X = "";
		String Y = "";
		if (c.absX >= xPos) {
			X = "west";
		}
		if (c.absY > yPos) {
			Y = "South";
		}
		if (c.absX < xPos) {
			X = "east";
		}
		if (c.absY <= yPos) {
			Y = "North";
		}
		c.getActionSender().sendMessage(
				"You need to travel " + Y + "-" + X + ".");
	}

	private enum Altars {
		AIR_ALTAR(2452, new int[] { 1438, 5527 }, new int[] { 2842, 4829 }), MIND_ALTAR(
				2453, new int[] { 1448, 5529 }, new int[] { 2793, 4828 }), WATER_ALTAR(
				2454, new int[] { 1444, 5531 }, new int[] { 2713, 4836 }), EARTH_ALTAR(
				2455, new int[] { 1440, 5535 }, new int[] { 2655, 4831 }), FIRE_ALTAR(
				2456, new int[] { 1442, 5537 }, new int[] { 2577, 4845 }), BODY_ALTAR(
				2457, new int[] { 1446, 5533 }, new int[] { 2521, 4834 }), COSMIC_ALTAR(
				2458, new int[] { 1454, 5539 }, new int[] { 2162, 4833 }), CHAOS_ALTAR(
				2461, new int[] { 1452, 5543 }, new int[] { 2268, 4842 }), 
				NATURE_ALTAR(2460, new int[] { 1462, 5541 }, new int[] { 2400, 4835 }), LAW_ALTAR(
				2459, new int[] { 1458, 5545 }, new int[] { 2464, 4819 }), DEATH_ALTAR(
				2462, new int[] { 1456, 5547 }, new int[] { 2208, 4831 });

		int objId;
		int[] keys, loc;

		private Altars(int objId, int[] keys, int[] loc) {
			this.objId = objId;
			this.loc = loc;
			this.keys = keys;
		}

		private int getObj() {
			return objId;
		}

		private int[] getKeys() {
			return keys;
		}

		private int[] getNewLoc() {
			return loc;
		}
	}

	public void enterAltar(int objId, int itemUse) {
		Altars a = forAltar(objId);
		if (a != null) {
			/*if (c.runeMist < 4) {
				c.getDialogueHandler().sendStatement(
						"You need to beat rune mysteries first to do this.");
				c.nextChat = 0;
				return;
			}*/
			if (a.getKeys()[1] == c.playerEquipment[c.playerHat]
					|| a.getKeys()[0] == itemUse) {
				c.getPlayerAssistant().movePlayer(a.getNewLoc()[0],
						a.getNewLoc()[1], 0);
				c.getActionSender().sendMessage(
						"You enter the mysterious ruins.");
			} else {
				c.getActionSender().sendMessage(
						"Nothing interesting happens.");
			}
		}
	}

	private enum Altar_Data {
		AIR(2478, 1, 6, 556, new int[][] { { 11, 2 }, { 22, 3 }, { 33, 4 },
				{ 44, 5 }, { 55, 6 }, { 66, 7 }, { 77, 8 }, { 88, 9 },
				{ 99, 9 } }), MIND(2479, 5, 6, 558, new int[][] { { 14, 2 },
				{ 28, 3 }, { 42, 4 }, { 56, 5 }, { 70, 6 }, { 84, 7 },
				{ 98, 8 } }), WATER(2480, 9, 7, 555, new int[][] { { 19, 2 },
				{ 38, 3 }, { 57, 4 }, { 76, 5 }, { 95, 6 } }), EARTH(2481, 9,
				7, 557, new int[][] { { 26, 2 }, { 52, 3 }, { 78, 4 } }), FIRE(
				2482, 14, 7, 554,
				new int[][] { { 26, 2 }, { 52, 3 }, { 78, 4 } }), BODY(2483,
				20, 8, 559, new int[][] { { 35, 2 }, { 70, 3 } }), COSMIC(2484,
				27, 9, 564, new int[][] { { 59, 2 } }), CHAOS(2487, 35, 9, 562,
				new int[][] { { 74, 2 } }), // was 2485, 35,
											// 9, 562, new
											// int[][]{{74,
											// 2}}
		NATURE(2486, 44, 10, 561, new int[][] { { 91, 2 } }), LAW(2485, 54, 11,
				563, new int[][] { { 100, 2 } }), // was 2487, 54,
													// 11, 563, new
													// int[][]{{100,
													// 2}})
		DEATH(2488, 65, 13, 560, new int[][] { { 100, 2 } }), BLOOD(2489, 77,
				15, 565, new int[][] { { 100, 2 } }), SOUL(2490, 90, 17, 566,
				new int[][] { { 100, 2 } }), ASTRAL(17010, 40, 10, 9075,
				new int[][] { { 82, 2 } });

		int altarID, levelReq, xp, rewardedRune;
		int[][] multiRunes;

		private Altar_Data(int altarID, int levelReq, int xp, int rewardedRune,
				int[][] multiRunes) {
			this.altarID = altarID;
			this.levelReq = levelReq;
			this.xp = xp;
			this.rewardedRune = rewardedRune;
			this.multiRunes = multiRunes;
		}
	}

	public Altar_Data forObj(int obj) {
		for (Altar_Data ad : Altar_Data.values()) {
			if (ad.altarID == obj) {
				return ad;
			}
		}
		return null;
	}

	int objId;
	int[] keys, loc;

	public Altars forAltar(int id) {
		for (Altars a : Altars.values()) {
			if (a.getObj() == id) {
				return a;
			}
		}
		return null;
	}

	public boolean craftRunes(int obj) {
		Altar_Data ad = forObj(obj);
		if (ad != null) {
			RandomEventHandler.addRandom(c);
			if (!SkillHandler.RUNECRAFTING) {
				c.getActionSender().sendMessage(
						"This skill is currently disabled.");
				return false;
			}
			if (c.playerLevel[c.playerRunecrafting] >= ad.levelReq) {
				getMultiSupport(obj);
				c.startAnimation(791);
				c.gfx100(186);
				c.getActionSender().sendSound(SoundList.RUNECRAFTING, 100,
						0);
			} else {
				c.getActionSender().sendMessage(
						"You need a runecrafting level of at least "
								+ ad.levelReq + " to make runes here.");
			}
		}
		return false;
	}

	public void getMultiSupport(int obj) {
		Altar_Data ad = forObj(obj);
		if (ad != null) {
			int amount = c.getItemAssistant().getItemCount(7936);
			int amount2 = c.getItemAssistant().getItemCount(1436);
			if (amount > 0) {
				c.getItemAssistant().deleteItem(7936,
						c.getItemAssistant().getItemCount(7936));
				c.getItemAssistant().addItem(
						ad.rewardedRune,
						amount
								* (getMultiplier(ad) == 1 ? getMultiplier(ad)
										: getMultiplier(ad) - 1));
				c.getPlayerAssistant().addSkillXP(ad.xp * amount,
						c.playerRunecrafting);
			} else if (amount2 > 0) {
				c.getItemAssistant().deleteItem(1436,
						c.getItemAssistant().getItemCount(1436));
				c.getItemAssistant().addItem(
						ad.rewardedRune,
						amount2
								* (getMultiplier(ad) == 1 ? getMultiplier(ad)
										: getMultiplier(ad) - 1));
				c.getPlayerAssistant().addSkillXP(ad.xp * amount2,
						c.playerRunecrafting);
			} else if (amount2 > 0 && amount > 0) {
				c.getItemAssistant().deleteItem(7936,
						c.getItemAssistant().getItemCount(7936));
				c.getItemAssistant().addItem(
						ad.rewardedRune,
						amount
								* (getMultiplier(ad) == 1 ? getMultiplier(ad)
										: getMultiplier(ad) - 1));
				c.getPlayerAssistant().addSkillXP(ad.xp * amount,
						c.playerRunecrafting);
				c.getItemAssistant().deleteItem(1436,
						c.getItemAssistant().getItemCount(1436));
				c.getItemAssistant().addItem(
						ad.rewardedRune,
						amount2
								* (getMultiplier(ad) == 1 ? getMultiplier(ad)
										: getMultiplier(ad) - 1));
				c.getPlayerAssistant().addSkillXP(ad.xp * amount2,
						c.playerRunecrafting);
			} else {
				c.getActionSender().sendMessage(
						"You don't have any essence left.");
			}
		}
	}

	public int getMultiplier(Altar_Data ad) {
		int temp = 1;
		for (int[] multiRune : ad.multiRunes) {
			for (int j = 0; j < multiRune.length; j++) {
				if (c.playerLevel[c.playerRunecrafting] >= multiRune[0]) {
					temp++;
				}
			}
		}
		return temp;
	}
}
