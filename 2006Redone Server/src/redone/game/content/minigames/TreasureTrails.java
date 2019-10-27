package redone.game.content.minigames;

import redone.game.players.Client;
import redone.util.Misc;

public class TreasureTrails {

	public static int lowLevelReward[] = {
			1077,
			1125,
			1165,
			1195,
			1297,
			1367,
			853,
			7390,
			7392,
			7394,
			7396,
			7386,
			7388,
			1099,
			1135,
			1065,
			851,
	};
	public static int mediumLevelReward[] = {
			1073,
			1123,
			1161,
			1199,
			1301,
			1371,
			857,
			2577,
			2579,
			2487,
			2493,
			2499,
			2631,
			855,
	};
	public static int highLevelReward[] = {
			1079,
			1093,
			1113,
			1127,
			1147,
			1163,
			1185,
			1201,
			1275,
			1303,
			1319,
			1333,
			1359,
			1373,
			2491,
			2497,
			2503,
			861,
			859,
			2581,
			2651,
			1079,
			1093,
			1113,
			1127,
			1147,
			1163,
			1185,
			1201,
			1275,
			1303,
			1319,
			1333,
			1359,
			1373,
			2491,
			2497,
			2503,
			861,
			859,
			2581,
			2651,
	};

	public static int lowLevelStacks[] = {
			995,
			380,
			561,
			886,
	};
	public static int mediumLevelStacks[] = {
			995,
			374,
			561,
			563,
			890,
	};
	public static int highLevelStacks[] = {
			995,
			386,
			561,
			563,
			560,
			892,
	};
	public static int allStacks[] = {
			995,
			380,
			561,
			886,
			374,
			561,
			563,
			890,
			386,
			561,
			563,
			560,
			892,
	};


	public static void addClueReward(Client c, int clueLevel) {
		int chanceReward = Misc.random(2);
		if (clueLevel == 0) {
			switch (chanceReward) {
			case 0:
				displayReward(c,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(c,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(c, Misc.randomArrayItem(lowLevelReward), 1);
				break;
			}
		} else if (clueLevel == 1) {
			switch (chanceReward) {
			case 0:
				displayReward(c,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(c,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(c, Misc.randomArrayItem(mediumLevelReward), 1);
				break;
			}
		} else if (clueLevel == 2) {
			switch (chanceReward) {
			case 0:
				displayReward(c,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(c,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(c, Misc.randomArrayItem(highLevelReward), 1);
				break;
			}
		}
	}
	public static void displayReward(Client c, int item, int amount) {
		displayReward(c, item, amount, -1, 1);
	}

	public static void displayReward(Client c, int item, int amount, int item2, int amount2) {
		displayReward(c, item, amount, item2, amount2, -1, 1);
	}

	public static void displayReward(Client c, int item, int amount, int item2, int amount2, int item3, int amount3) {
		int[] items = { item, item2, item3 };
		int[] amounts = { amount, amount2, amount3 };
		c.outStream.createFrameVarSizeWord(53);
		c.outStream.writeWord(6963);
		c.outStream.writeWord(items.length);
		for (int i = 0; i < items.length; i++) {
			if (c.playerItemsN[i] > 254) {
				c.outStream.writeByte(255);
				c.outStream.writeDWord_v2(amounts[i]);
			} else {
				c.outStream.writeByte(amounts[i]);
			}
			if (items[i] > 0) {
				c.outStream.writeWordBigEndianA(items[i] + 1);
			} else {
				c.outStream.writeWordBigEndianA(0);
			}
		}
		c.outStream.endFrameVarSizeWord();
		c.flushOutStream();
		c.getItemAssistant().addOrDropItem(item, amount);
		c.getItemAssistant().addOrDropItem(item2, amount2);
		c.getItemAssistant().addOrDropItem(item3, amount3);
		c.getPlayerAssistant().showInterface(6960);
	}

}
