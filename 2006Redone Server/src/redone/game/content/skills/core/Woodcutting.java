package redone.game.content.skills.core;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.randomevents.TreeSpirit;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.objects.Object;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.game.players.antimacro.AntiBotting;
import redone.util.Misc;

/**
 * Woodcutting
 */

public class Woodcutting {
	
	public final static int[][] Axe_Settings = {
			{1351, 1, 1, 879},
			{1349, 1, 2, 877}, 
			{1353, 6, 3, 875}, 
			{1361, 6, 4, 873},
			{1355, 21, 5, 871},
			{1357, 31, 6, 869}, 
			{1359, 41, 7, 867}, 
			{6739, 61, 8, 2846}, 
			{13661, 41, 8, 10251} 
		};

	public final static int[][] Tree_Settings = {
			{ 1276, 1342, 1, 25, 1511, 45, 100 },
			{ 1278, 1342, 1, 25, 1511, 45, 100 }, 
			{ 1286, 1342, 1, 25, 1511, 45, 100 },
			{ 1281, 1356, 15, 38, 1521, 11, 20 },
			{ 1308, 7399, 30, 68, 1519, 11, 8 },
			{ 5552, 7399, 30, 68, 1519, 11, 8 },
			{ 1307, 1343, 45, 100, 1517, 48, 8 },
			{ 1309, 7402, 60, 175, 1515, 79, 5 },
			{ 1306, 7401, 75, 250, 1513, 150, 3 },
			{ 5551, 7399, 30, 68, 1519, 11, 8 },
			{ 5553, 7399, 30, 68, 1519, 11, 8 },
			{ 3033, 1342, 1, 25, 1511, 45, 100 }, 
			{ 3037, 1356, 15, 38, 1521, 11, 20 },
			{ 1282, 1342, 1, 25, 1511, 45, 100 },
			{ 1383, 1342, 1, 25, 1511, 45, 100 },
			{ 2023, 3371, 1, 25, 2862, 45, 100 },
			{ 1319, 1341, 1, 25, 1511, 45, 100 },
			{ 1318, 1341, 1, 25, 1511, 45, 100 }, 
			{ 1315, 1341, 1, 25, 1511, 45, 100 }, 
			{ 1316, 1341, 1, 25, 1511, 45, 100 }, 
			{ 1332, 1341, 1, 25, 1511, 45, 100 },
			{ 1292, 1341, 36, 1, 771, 45, 100 } 
	};

	public static int[][] FIX_AXE = { { 492, 508, 1351 }, { 492, 510, 1349 },
			{ 492, 512, 1353 }, { 492, 514, 1361 }, { 492, 516, 1355 },
			{ 492, 518, 1357 }, { 492, 520, 1359 }, };

	private static int a = -1;

	public static void repeatAnimation(final Client player) {
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (player.isWoodcutting) {
					player.startAnimation(Axe_Settings[a][3]);
					player.getActionSender().sendSound(SoundList.TREE_CUTTING, 100, 0);
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
				stopWoodcutting(player);
			}
		}, 3);
	}

	public static void handleCanoe(final Client player, final int objectId) {
		if (player.playerLevel[player.playerWoodcutting] < 12) {
			player.getActionSender().sendMessage("You need a woodcutting level of at least 12 to use the canoe station.");
			return;
		}
		for (int axes[] : Axe_Settings) {
			int type = axes[0];
			int level = axes[1];
			int anim = axes[3];
			if (player.playerLevel[player.playerWoodcutting] >= level && player.getItemAssistant().playerHasItem(type) || player.playerLevel[player.playerWoodcutting] >= level && player.playerEquipment[player.playerWeapon] == type) {
				player.turnPlayerTo(player.objectX, player.objectY);
				player.startAnimation(anim);
				player.getActionSender().sendMessage("You swing your axe at the station.");
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						addFallenTree(player, objectId);
						CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
							@Override
							public void execute(CycleEventContainer container) {
								player.getPlayerAssistant().handleCanoe();
								container.stop();
							}

							@Override
							public void stop() {
								
							}

						}, 1);
						player.getActionSender().sendMessage("You cut down the canoe. Please wait...");
						container.stop();
					}

					@Override
					public void stop() {
						
					}
				}, 4);
			}
		}
	}

	public void fixAxe(final Client player) {
		for (int fix[] : FIX_AXE) {
			int axeHandle = fix[0];
			int axeHead = fix[1];
			final int fixedAxe = fix[2];
			if (player.getItemAssistant().playerHasItem(axeHandle) && player.getItemAssistant().playerHasItem(axeHead)) {
				player.isWoodcutting = true;
				player.getItemAssistant().deleteItem(axeHandle, 1);
				player.getItemAssistant().deleteItem(axeHead, 1);
				player.getPlayerAssistant().removeAllWindows();
				player.getActionSender().sendMessage("Your axe handle and axe head have been taken.");
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.getItemAssistant().addItem(fixedAxe, 1);
						player.getActionSender().sendMessage("Your axe has been fixed.");
						container.stop();
					}
					@Override
					public void stop() {
						
					}
				}, 1);
			}
		}
	}

	public static void addFallenTree(Client player, int canoe) {
		if (canoe == player.objectId) {
			for (Player players : PlayerHandler.players) {
				if (players != null) {
					new Object(1296, player.objectX, player.objectY, 0, 0, 10, canoe, 20 + Misc.random(40));
				}
			}
		}
	}
	
	public boolean hasAxe(Client player) {
		for (int i = 0; i < Axe_Settings.length; i++) {
			if (player.getItemAssistant().playerHasItem(Axe_Settings[i][0]) || player.playerEquipment[player.playerWeapon] == Axe_Settings[i][0]) {
				return true;
			}
		}
		return false;
	}

	public static void startWoodcutting(final Client player, final int j, final int x, final int y, final int type) {
		if (player.isWoodcutting || player.isFletching || player.isFiremaking || player.playerIsFletching) {
			return;
		}
		if (player.absX == 2717 && player.absY == 3461) {
			player.getActionSender().sendMessage("You can't cut the tree from here!");
			return;
		}
		if (!SkillHandler.WOODCUTTING) {
			player.getActionSender().sendMessage("This skill is currently disabled.");
			return;
		}
		int wcLevel = player.playerLevel[8];
		a = -1;
		player.turnPlayerTo(x, y);
		if (Tree_Settings[j][2] > wcLevel) {
			player.getActionSender().sendMessage("You need a Woodcutting level of " + Tree_Settings[j][2] + " to cut this tree.");
			return;
		}
		for (int i = 0; i < Axe_Settings.length; i++) {
			if (player.getItemAssistant().playerHasItem(Axe_Settings[i][0]) || player.playerEquipment[player.playerWeapon] == Axe_Settings[i][0]) {
				if (Axe_Settings[i][1] <= wcLevel) {
					a = i;
				}
			}
		}
		if (a == -1) {
			player.getActionSender().sendMessage("You need an axe to cut this tree.");
			return;
		}
		if (player.getItemAssistant().freeSlots() < 1) {
			player.getActionSender().sendMessage("You do not have enough inventory slots to do that.");
			return;
		}
		if (Misc.goodDistance(player.objectX, player.objectY, player.absX, player.absY, 3)) {
			if (player.isWoodcutting == true) {
				player.getActionSender().sendMessage("You are already woodcutting!");
				return;
			}
			player.startAnimation(Axe_Settings[a][3]);
			player.isWoodcutting = true;
			player.getActionSender().sendSound(SoundList.TREE_CUT_BEGIN, 100, 0);
			repeatAnimation(player);
			player.treeX = x;
			player.treeY = y;
			if (player.tutorialProgress == 3) {
				player.getPlayerAssistant().removeAllWindows();
				player.getActionSender().chatbox(6180);
				if (player.playerAppearance[0] == 0) {
					player.getDialogueHandler().chatboxText(player, "", "Your character is now attempting to cut down the tree. Sit back", "for a moment while he does all the hard work.", "", "Please wait");
				} else {
					player.getDialogueHandler().chatboxText(player, "", "Your character is now attempting to cut down the tree. Sit back", "for a moment while she does all the hard work.", "", "Please wait");
				}
				player.getActionSender().chatbox(6179);
			} else {
				player.getActionSender().sendMessage("You swing your axe at the tree.");
			}
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (!player.isWoodcutting) {
						container.stop();
						return;
					}
					if (player.disconnected) {
						container.stop();
						return;
					}
					if (player.isWoodcutting) {
						player.startAnimation(Axe_Settings[a][3]);
					}
					if (player.getItemAssistant().freeSlots() < 1) {
						player.getActionSender().sendMessage("You have ran out of inventory slots.");
						container.stop();
					}
					int XP = Tree_Settings[j][3];
					if (player.isWoodcutting) {
						player.getItemAssistant().addItem(Tree_Settings[j][4], 1);
						player.getPlayerAssistant().addSkillXP(XP, 8);
						player.getActionSender().sendMessage("You manage to get some " + ItemAssistant.getItemName(Tree_Settings[j][4]).toLowerCase() + " from the tree.");
					}
					if (player.tutorialProgress == 3) {
						player.getDialogueHandler().sendDialogues(3014, 0);
					}
					if (player.isWoodcutting) {
						birdNests(player);
					}
					if (player.isWoodcutting && player.tutorialProgress >= 36 && player.treeSpiritSpawned == false && Misc.random(300) == 10) {
						AntiBotting.botCheckInterface(player);
					}
					if (player.isWoodcutting && player.tutorialProgress >= 36 && player.treeSpiritSpawned == false) {
						RandomEventHandler.addRandom(player);
					}
					if (player.isWoodcutting && Misc.random(350) == 69 && player.tutorialProgress >= 36) {
						TreeSpirit.spawnTreeSpirit(player);
					}
					if (player.playerIsFletching || player.isFiremaking) {
						container.stop();
					}
					if (Misc.random(100) <= Tree_Settings[j][6]) {
						cutDownTree(Tree_Settings[j][5], x, y, type, Tree_Settings[j][1], Tree_Settings[j][0]);
						player.getActionSender().sendSound(SoundList.TREE_EMPTY, 100, 0);
						container.stop();
					}
				}
				@Override
				public void stop() {
					player.startAnimation(65535);
					player.isWoodcutting = false;
					player.treeX = 0;
					player.treeY = 0;
				}
			}, getTimer(j, a, wcLevel));
		}
	}

	public static void stopWoodcutting(Client player) {
		player.startAnimation(65535);
		player.isWoodcutting = false;
		player.treeX = 0;
		player.treeY = 0;
	}
	
	public static int getTimer(int b, int c, int level) {
		double timer = (int)((Tree_Settings[b][2]  * 2) + 20 + Misc.random(20))-((Axe_Settings[c][2] * (Axe_Settings[c][2] * 0.75)) + level);
		if (timer < 3.0) {
			return 3;
		} else {
			return (int)timer;
		}
	}

	public static void birdNests(Client player) {
		if (Misc.random(200) == 69 && player.tutorialProgress >= 36) {
			player.getActionSender().sendMessage("A birds nest falls from the branches.");
			dropNest(player);
		}
	}

	public static void dropNest(Client player) {
		Server.itemHandler.createGroundItem(player, 5070 + Misc.random(4), player.getX(), player.getY(), 1, player.getId());
	}

	public static boolean playerTrees(Client player, int tree) {
		boolean trees2 = false;
		for (int i = 0; i < trees.length; i++) {
			for (int i1 = 0; i1 < 6; i1++) {
				if (tree == trees[i1][i]) {
					trees2 = true;
				}
			}
		}
		return trees2;
	}

	public static int[][] trees = {
			{ // NORMAL
			1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1287,
					1288, 1289, 1290, 1291, 1301, 1303, 1304, 1305, 1318, 1319,
					1315, 1316, 1330, 1331, 1332, 1333, 1383, 1384, 2409, 2447,
					2448, 3033, 3034, 3035, 3036, 3879, 3881, 3883, 3893, 3885,
					3886, 3887, 3888, 3892, 3889, 3890, 3891, 3928, 3967, 3968,
					4048, 4049, 4050, 4051, 4052, 4053, 4054, 4060, 5004, 5005,
					5045, 5902, 5903, 5904, 8973, 8974, 10041, 10081, 10082,
					10664, 11112, 11510, 12559, 12560, 12732, 12895, 12896,
					13412, 13411, 13419, 13843, 13844, 13845, 13847, 13848,
					13849, 13850, 14308, 14309, 14513, 14514, 14515, 14521,
					14564, 14565, 14566, 14593, 14594, 14595, 14600, 14635,
					14636, 14637, 14642, 14664, 14665, 14666, 14693, 14694,
					14695, 14696, 14701, 14738, 14796, 14797, 14798, 14799,
					14800, 14801, 14802, 14803, 14804, 14805, 14806, 14807,
					15489, 15776, 15777, 16264, 16265, 19165, 19166, 19167,
					23381,

			},
			{ // OAK
			1281, 3037, 8462, 8463, 8464, 8465, 8466, 8467, 10083, 13413,
					13420, },
			{ // WILLOW
			1308, 5551, 5552, 5553, 8481, 8482, 8483, 8484, 8485, 8486, 8487,
					8488, 8496, 8497, 8498, 8499, 8500, 8501, 13414, 13421, },
			{ // MAPLE
			1307, 4674, 8435, 8436, 8437, 8438, 8439, 8440, 8441, 8442, 8443,
					8444, 8454, 8455, 8456, 8457, 8458, 8459, 8460, 8461,
					13415, 13423, }, { // YEW
			1309, 8503, 8504, 8505, 8506, 8507, 8508, 8509, 8510, 8511, 8512,
					8513, 13416, 13422, }, { // MAGIC
			1306, 8396, 8397, 8398, 8399, 8400, 8401, 8402, 8403, 8404, 8405,
					8406, 8407, 8408, 8409, 13417, 13424, } };

	public static void cutDownTree(int respawnTime, int x, int y, int type, int i, int j) {
		new Object(i, x, y, 0, type, 10, j, respawnTime);
		for (int t = 0; t < PlayerHandler.players.length; t++) {
			if (PlayerHandler.players[t] != null) {
				if (PlayerHandler.players[t].treeX == x && PlayerHandler.players[t].treeY == y) {
					PlayerHandler.players[t].isWoodcutting = false;
					PlayerHandler.players[t].startAnimation(65535);
					PlayerHandler.players[t].treeX = 0;
					PlayerHandler.players[t].treeY = 0;
				}
			}
		}
	}
}
