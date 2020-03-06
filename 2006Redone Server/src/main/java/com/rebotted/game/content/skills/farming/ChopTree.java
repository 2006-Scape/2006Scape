/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rebotted.game.content.skills.farming;

import com.rebotted.game.content.skills.SkillConstants;
import com.rebotted.game.items.ItemConstants;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

public class ChopTree {

	private static final Axe[] axes = new Axe[8];
	private static final Tree[] trees = new Tree[11];

	public static final int[] COMMON_SEEDS = { 5312, 5283, 5284, 5285, 5286,
			5313 };
	public static final int[] UNCOMMON_SEEDS = { 5314, 5288, 5287, 5315, 5289 };
	public static final int[] RARE_SEEDS = { 5316, 5290 };
	public static final int[] VERY_RARE_SEEDS = { 5317 };

	public static final int[] COMMON_RING = { 1635, 1637 };
	public static final int[] UNCOMMON_RING = { 1639 };
	public static final int[] RARE_RING = { 1641 };
	public static final int[] VERY_RARE_RING = { 1643 };

	static {
		// Initialise axes
		axes[0] = new Axe(1351, 1, 508, 879, 0);
		axes[1] = new Axe(1349, 1, 510, 877, 1);
		axes[2] = new Axe(1353, 6, 512, 875, 2);
		axes[3] = new Axe(1361, 6, 514, 873, 3);
		axes[4] = new Axe(1355, 21, 516, 871, 4);
		axes[5] = new Axe(1357, 31, 518, 869, 5);
		axes[6] = new Axe(1359, 41, 520, 867, 6);
		axes[7] = new Axe(6739, 61, 6743, 2846, 7);
		// Initialise trees
		trees[0] = new Tree(new int[] { 2023 }, 1, 25, 2862, 3371, 75, 100);
		trees[1] = new Tree(new int[] { 1276, 1277, 1278, 1279, 1280, 1282,
				1283, 1284, 1285, 1286, 1289, 1290, 1291, 1315, 1316, 1318,
				1319, 1330, 1331, 1332, 1333, 1365, 1383, 1384, 2409, 3033,
				3034, 3035, 3036, 3881, 3882, 3883, 5902, 5903, 5904 }, 1, 25,
				1511, 1342, 75, 100);
		trees[2] = new Tree(new int[] { 1281, 2037 }, 15, 37.5, 1521, 1356, 14,
				25);
		trees[3] = new Tree(new int[] { 1308, 5551, 5552, 5553 }, 30, 67.5,
				1519, 7399, 14, 5);
		trees[4] = new Tree(new int[] { 9036 }, 35, 85, 6333, 9037, 14, 20);
		trees[5] = new Tree(new int[] { 1307, 4677 }, 45, 100, 1517, 1343, 59,
				15);
		trees[6] = new Tree(new int[] { 2289, 4060 }, 45, 83, 3239, 2310, 59,
				15);
		trees[7] = new Tree(new int[] { 9034 }, 50, 125, 6332, 9035, 80, 10);
		trees[8] = new Tree(new int[] { 1309 }, 60, 175, 1515, 7402, 100, 5);
		trees[9] = new Tree(new int[] { 1306 }, 75, 250, 1513, 7401, 200, 3);
		trees[10] = new Tree(new int[] { 1292 }, 36, 0, 771, 1513, 59, 100);

	}

	public static class Axe {

		private int id;
		private int level;
		private int head;
		private int animation;
		private int bonus;

		public Axe(int id, int level, int head, int animation, int bonus) {
			this.id = id;
			this.level = level;
			this.head = head;
			this.animation = animation;
			this.bonus = bonus;
		}

		public int getId() {
			return id;
		}

		public int getLevel() {
			return level;
		}

		public int getHead() {
			return head;
		}

		public int getAnimation() {
			return animation;
		}

		public int getBonus() {
			return bonus;
		}

	}

	public static class Tree {
		private int[] id;
		private int level;
		private double xp;
		private int log;
		private int stump;
		private int respawnTime;
		private int decayChance;

		public Tree(int[] id, int level, double xp, int log, int stump,
				int respawnTime, int decayChance) {
			this.id = id;
			this.level = level;
			this.xp = xp;
			this.log = log;
			this.stump = stump;
			this.respawnTime = respawnTime;
			this.decayChance = decayChance;
		}

		public int[] getId() {
			return id;
		}

		public int getLevel() {
			return level;
		}

		public double getXP() {
			return xp;
		}

		public int getLog() {
			return log;
		}

		public int getStump() {
			return stump;
		}

		public int getRespawnTime() {
			return respawnTime;
		}

		public int getDecayChance() {
			return decayChance;
		}
	}

	public static boolean handleNest(Player player, int itemId) {
		int[] commonItems, uncommonItems, rareItems, veryRareItems;
		switch (itemId) {
		case 5070:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(5075, 1);
			player.getItemAssistant().addItem(5076, 1);
			return true;
		case 5071:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(5075, 1);
			player.getItemAssistant().addItem(5078, 1);
			return true;
		case 5072:
			player.getItemAssistant().deleteItem(itemId, 1);
			player.getItemAssistant().addItem(5075, 1);
			player.getItemAssistant().addItem(5077, 1);
			return true;
		case 5073:
			commonItems = COMMON_SEEDS;
			uncommonItems = UNCOMMON_SEEDS;
			rareItems = RARE_SEEDS;
			veryRareItems = VERY_RARE_SEEDS;
			break;
		case 5074:
			commonItems = COMMON_RING;
			uncommonItems = UNCOMMON_RING;
			rareItems = RARE_RING;
			veryRareItems = VERY_RARE_RING;
			break;
		default:
			return false;
		}
		int randomNumber = Misc.random(100), finalItem;
		if (randomNumber <= 60)
			finalItem = commonItems[Misc.random(commonItems.length - 1)];
		else if (randomNumber <= 80)
			finalItem = uncommonItems[Misc.random(uncommonItems.length - 1)];
		else if (randomNumber <= 95)
			finalItem = rareItems[Misc.random(rareItems.length - 1)];
		else
			finalItem = veryRareItems[Misc.random(veryRareItems.length - 1)];

		player.getPacketSender().sendMessage(
				"You search the nest...and find something in it!");
		player.getItemAssistant().deleteItem(itemId, 1);
		player.getItemAssistant().addItem(5075, 1);
		player.getItemAssistant().addItem(finalItem, 1);
		return true;
	}

	public static Axe getAxe(Player player) {
		final int axeIndex = getAxeIndex(player);
		if (axeIndex == -1) {
			return null;
		}
		return axes[axeIndex];
	}

	public static Tree getTree(int objectId) {
		final int treeIndex = getTreeIndex(objectId);
		if (treeIndex == -1) {
			return null;
		}
		return trees[treeIndex];
	}

	public static int getAxeIndex(Player player) {
		for (int i = 0; i < axes.length; i++) {
			if (player.playerEquipment[ItemConstants.WEAPON] == (axes[i].getId())) {
				return i;
			}
		}
		for (int i = axes.length - 1; i >= 0; i--) {
			if (player.getItemAssistant().playerHasItem(axes[i].getId())) {
				if (player.playerLevel[SkillConstants.WOODCUTTING.ordinal()] >= axes[i].getLevel()) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int getTreeIndex(final int objectId) {
		for (int i = 0; i < trees.length; i++) {
			int[] ids = trees[i].getId();
			for (int id : ids) {
				if (objectId == id) {
					return i;
				}
			}
		}
		return -1;
	}

}