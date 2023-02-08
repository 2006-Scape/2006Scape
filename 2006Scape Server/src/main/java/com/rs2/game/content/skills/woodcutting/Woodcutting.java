package com.rs2.game.content.skills.woodcutting;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.randomevents.TreeSpirit;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.objects.Object;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;

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
	
	private static enum treeData {
		TREE(new int[] {1276, 1278, 1286, 3033, 1282, 1383}, 1342, 1, 25, 1511, 11, 100),
		OAK(new int[] {1281, 3037}, 1356, 15, 38, 1521, 25, 20),
		WILLOW(new int[] {1308, 5552, 5551, 5553}, 7399, 30, 68, 1519, 30, 8),
		MAPLE(new int[] {1307}, 1343, 45, 100, 1517, 48, 8),
		YEW(new int[] {1309}, 7402, 60, 175, 1515, 79, 5),
		MAGIC(new int[] {1306}, 7401, 75, 250, 1513, 150, 3),
		EVERGREEN(new int[] {1319, 1318, 1315, 1316, 1332}, 1341, 1, 25, 1511, 11, 100),
		ACHEY(new int[] {2023}, 3371, 1, 25, 1511, 11, 100),
		DRAMEN(new int[] {1292}, 1341, 36, 0, 771, 45, 100);
		
		private int[] treeId;
		private int stumpId, levelReq, xpRecieved, logRecieved, respawnTime, cutChance;
		
		private treeData(int[] treeId, int stumpId, int levelReq, int xpRecieved, int logRecieved, int respawnTime, int cutChance) {
			this.treeId = treeId;
			this.stumpId = stumpId;
			this.levelReq = levelReq;
			this.xpRecieved = xpRecieved;
			this.logRecieved = logRecieved;
			this.respawnTime = respawnTime;
			this.cutChance = cutChance;
		}
		
		private int getStump() {
			return stumpId;
		}
		
		private int getLevelReq() {
			return levelReq;
		}
		
		private int getXpReceived() {
			return xpRecieved;
		}
		
		private int getLogRecieved() {
			return logRecieved;
		}
		
		private int getRespawnTime() {
			return respawnTime;
		}
		
		private int getChance() {
			return cutChance;
		}
		
		private int getObject(int object) {
			for (int element : treeId) {
				if (object == element) {
					return element;
				}
			}
			return -1;
		}
		
		private static treeData getTree(int objectId) {
			for (treeData tree : treeData.values()) {
				if (objectId == tree.getObject(objectId)) {
					return tree;
				}
			}
			return null;
		}
		
	}

	public static int[][] FIX_AXE = { { 492, 508, 1351 }, { 492, 510, 1349 },
			{ 492, 512, 1353 }, { 492, 514, 1361 }, { 492, 516, 1355 },
			{ 492, 518, 1357 }, { 492, 520, 1359 }, };

	public static void repeatAnimation(final Player p) {
		CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (p.isWoodcutting) {
					if ((p.woodcuttingAxe >= 0) && (p.woodcuttingAxe < Axe_Settings.length))	{
						try {
							p.startAnimation(Axe_Settings[p.woodcuttingAxe][3]);
						} catch (ArrayIndexOutOfBoundsException exception) {
							System.out.println("LOL this happend again: " + exception);
						}
						p.getPacketSender().sendSound(SoundList.TREE_CUTTING, 100, 0);
					}
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
				stopWoodcutting(p);
			}
		}, 3);
	}

	public static void handleCanoe(final Player player, final int objectId) {
		Boolean gotAxe = false;
		if (player.playerLevel[Constants.WOODCUTTING] < 12) {
			player.getPacketSender().sendMessage("You need a woodcutting level of at least 12 to use the canoe station.");
			return;
		}

		for (int axes[] : Axe_Settings) {
			int type = axes[0];
			if ( player.getItemAssistant().playerHasItem(type) || player.playerEquipment[player.playerWeapon] == type) {
				gotAxe = true;
			}
		}
		if (gotAxe) {
			player.getPacketSender().sendMessage("You swing your axe at the station.");
		} else {
			player.getPacketSender().sendMessage("You need an axe to cut the station.");
			return;
		}
		for (int axes[] : Axe_Settings) {
			int type = axes[0];
			int level = axes[1];
			int anim = axes[3];
			if (player.playerLevel[Constants.WOODCUTTING] >= level && player.getItemAssistant().playerHasItem(type) || player.playerLevel[Constants.WOODCUTTING] >= level && player.playerEquipment[player.playerWeapon] == type) {
				player.turnPlayerTo(player.objectX, player.objectY);
				player.startAnimation(anim);
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
						player.getPacketSender().sendMessage("You cut down the canoe. Please wait...");
						container.stop();
					}

					@Override
					public void stop() {
						
					}
				}, 4);
			}
		}
	}

	public void fixAxe(final Player player) {
		for (int fix[] : FIX_AXE) {
			int axeHandle = fix[0];
			int axeHead = fix[1];
			final int fixedAxe = fix[2];
			if (player.getItemAssistant().playerHasItem(axeHandle) && player.getItemAssistant().playerHasItem(axeHead)) {
				player.isWoodcutting = true;
				player.getItemAssistant().deleteItem(axeHandle, 1);
				player.getItemAssistant().deleteItem(axeHead, 1);
				player.getPacketSender().closeAllWindows();
				player.getPacketSender().sendMessage("Your axe handle and axe head have been taken.");
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.getItemAssistant().addItem(fixedAxe, 1);
						player.getPacketSender().sendMessage("Your axe has been fixed.");
						container.stop();
					}
					@Override
					public void stop() {
						
					}
				}, 1);
			}
		}
	}

	public static void addFallenTree(Player player, int canoe) {
		if (canoe == player.objectId) {
			for (Player players : PlayerHandler.players) {
				if (players != null) {
					new Object(1296, player.objectX, player.objectY, 0, 0, 10, canoe, 20 + Misc.random(40));
				}
			}
		}
	}
	
	public static boolean hasAxe(Player player) {
		for (int i = 0; i < Axe_Settings.length; i++) {
			if (player.getItemAssistant().playerHasItem(Axe_Settings[i][0]) || player.playerEquipment[player.playerWeapon] == Axe_Settings[i][0]) {
				return true;
			}
		}
		return false;
	}

	public static void startWoodcutting(final Player p, final int objectId, final int x, final int y, final int type) {
		CycleEventHandler.getSingleton().stopEvents(p, "WoodcuttingEvent".hashCode());
		if (p.isWoodcutting || p.isFletching || p.isFiremaking || p.playerIsFletching) {
			return;
		}
		if (p.absX == 2717 && p.absY == 3461) {
			p.getPacketSender().sendMessage("You can't cut the tree from here!");
			return;
		}
		if (!SkillHandler.WOODCUTTING) {
			p.getPacketSender().sendMessage("This skill is currently disabled.");
			return;
		}
		int wcLevel = p.playerLevel[Constants.WOODCUTTING];
		p.woodcuttingAxe = -1;
		treeData tree = treeData.getTree(objectId);
		p.turnPlayerTo(x, y);
		if (tree.getLevelReq() > wcLevel) {
			p.getPacketSender().sendMessage("You need a Woodcutting level of " + tree.getLevelReq() + " to cut this tree.");
			return;
		}
		for (int i = 0; i < Axe_Settings.length; i++) {
			if (p.getItemAssistant().playerHasItem(Axe_Settings[i][0]) || p.playerEquipment[p.playerWeapon] == Axe_Settings[i][0]) {
				if (Axe_Settings[i][1] <= wcLevel) {
					p.woodcuttingAxe = i;
				}
			}
		}
		if (p.woodcuttingAxe == -1) {
			p.getPacketSender().sendMessage("You need an axe to cut this tree.");
			return;
		}
		if (p.getItemAssistant().freeSlots() < 1) {
			p.getPacketSender().sendMessage("You do not have enough inventory slots to do that.");
			return;
		}
		if (Misc.goodDistance(p.objectX, p.objectY, p.absX, p.absY, 3)) {
			if (p.isWoodcutting) {
				p.getPacketSender().sendMessage("You are already woodcutting!");
				return;
			}
			p.startAnimation(Axe_Settings[p.woodcuttingAxe][3]);
			p.isWoodcutting = true;
			p.getPacketSender().sendSound(SoundList.TREE_CUT_BEGIN, 100, 0);
			repeatAnimation(p);
			p.treeX = x;
			p.treeY = y;
			if (p.tutorialProgress == 3) {
				p.getPacketSender().closeAllWindows();
				p.getPacketSender().chatbox(6180);
				if (p.playerAppearance[0] == 0) {
					p.getDialogueHandler().chatboxText("", "Your character is now attempting to cut down the tree. Sit back", "for a moment while he does all the hard work.", "", "Please wait");
				} else {
					p.getDialogueHandler().chatboxText("", "Your character is now attempting to cut down the tree. Sit back", "for a moment while she does all the hard work.", "", "Please wait");
				}
				p.getPacketSender().chatbox(6179);
			} else {
				p.getPacketSender().sendMessage("You swing your axe at the tree.");
			}
			CycleEventHandler.getSingleton().addEvent("WoodcuttingEvent".hashCode(), p, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (p.woodcuttingAxe <= -1)
					{
						container.stop();
						return;
					}
					if (!p.isWoodcutting) {
						container.stop();
						return;
					}
					if (p.disconnected) {
						container.stop();
						return;
					}
					if (p.isWoodcutting) {
						p.startAnimation(Axe_Settings[p.woodcuttingAxe][3]);
					}
					if (p.getItemAssistant().freeSlots() < 1) {
						p.getPacketSender().sendMessage("You have ran out of inventory slots.");
						container.stop();
					}
					int XP = tree.getXpReceived();
					if (p.isWoodcutting) {
						p.getItemAssistant().addItem(tree.getLogRecieved(), 1);
						p.getPlayerAssistant().addSkillXP(XP, 8);
						p.getPacketSender().sendMessage("You manage to get some " + DeprecatedItems.getItemName(tree.getLogRecieved()).toLowerCase() + " from the tree.");
					}
					if (p.tutorialProgress == 3) {
						p.getDialogueHandler().sendDialogues(3014, 0);
					}
					if (p.isWoodcutting) {
						BirdNest.birdNests(p);
					}
					if (p.isWoodcutting && p.tutorialProgress >= 36 && p.treeSpiritSpawned == false) {
						RandomEventHandler.addRandom(p);
					}
					if (p.isWoodcutting && Misc.random(350) == 69 && p.tutorialProgress >= 36 && p.randomEventsEnabled) {
						TreeSpirit.spawnTreeSpirit(p);
					}
					if (p.playerIsFletching || p.isFiremaking) {
						container.stop();
					}
					if (Misc.random(100) <= tree.getChance()) {
						cutDownTree(tree.getRespawnTime(), x, y, type, tree.getStump(), objectId);
						p.getPacketSender().sendSound(SoundList.TREE_EMPTY, 100, 0);
						container.stop();
					}
				}
				@Override
				public void stop() {
					p.startAnimation(65535);
					p.isWoodcutting = false;
					p.treeX = 0;
					p.treeY = 0;
				}
			}, getTimer(tree, p.woodcuttingAxe, wcLevel));
		}
	}

	public static void stopWoodcutting(Player player) {
		player.startAnimation(65535);
		player.isWoodcutting = false;
		player.treeX = 0;
		player.treeY = 0;
	}
	
	public static int getTimer(treeData tree, int axe, int level) {
		double timer = (int)((tree.getLevelReq()  * 2) + 20 + Misc.random(20))-((Axe_Settings[axe][2] * (Axe_Settings[axe][2] * 0.75)) + level);
		if (timer < 3.0) {
			return 3;
		} else {
			return (int)timer;
		}
	}

	public static boolean playerTrees(Player player, int tree) {
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
					13415, 13423, }, 
			{ // YEW
			1309, 8503, 8504, 8505, 8506, 8507, 8508, 8509, 8510, 8511, 8512,
					8513, 13416, 13422, }, 
			{ // MAGIC
			1306, 8396, 8397, 8398, 8399, 8400, 8401, 8402, 8403, 8404, 8405,
					8406, 8407, 8408, 8409, 13417, 13424, } };

	public static void cutDownTree(int respawnTime, int objectX, int objectY, int type, int stumpID, int treeID) {
		new Object(stumpID, objectX, objectY, 0, type, 10, treeID, respawnTime);
		for (int t = 0; t < PlayerHandler.players.length; t++) {
			if (PlayerHandler.players[t] != null) {
				if (PlayerHandler.players[t].treeX == objectX && PlayerHandler.players[t].treeY == objectY) {
					PlayerHandler.players[t].isWoodcutting = false;
					PlayerHandler.players[t].startAnimation(65535);
					PlayerHandler.players[t].treeX = 0;
					PlayerHandler.players[t].treeY = 0;
				}
			}
		}
	}
}
