package redone.game.content.skills.core;

import redone.event.*;
import redone.game.items.ItemAssistant;
import redone.game.objects.Object;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.Misc;
import redone.world.clip.Region;

public class Mining {
	
	public final int[][] Pick_Settings = {
		{1265, 1, 1, 625}, //Bronze
		{1267, 1, 2, 626}, //Iron
		{1269, 6, 3, 627}, //Steel
		{1271, 31, 5, 629}, //Addy
		{1273, 21, 4, 628}, //Mithril
		{1275, 41, 6, 624}, //Rune

	};
	
	public final int[][] Rock_Settings = {
		{2091, 1, 18, 3, 436}, //Copper
		{2095, 1, 18, 3, 438}, //Tin
		{2093, 15, 35, 7, 440}, //Iron
		{2097, 30, 50, 38, 453}, //Coal
		{2103, 55, 80, 155, 447}, //Mithril
		{2105, 70, 95, 315, 449}, //Addy
		{2107, 85, 125, 970, 451}, //Rune
		{2090, 1, 18, 3, 436}, //Copper
		{2094, 1, 18, 3, 438}, //Tin
		{2092, 15, 35, 7, 440}, //Iron
		{2096, 30, 50, 38, 453}, //Coal
		{2102, 55, 80, 155, 447}, //Mithril
		{2104, 70, 95, 315, 449}, //Addy
		{2106, 85, 125, 970, 451}, //Rune
		{2100, 20, 40, 78, 442}, //Silver
		{2101, 20, 40, 78, 442}, //Silver
		{2098, 40, 65, 78, 444}, //Gold
		{2099, 40, 65, 78, 444}, //Gold
		{3042, 1, 18, 3, 436}, //Copper
		{3043, 1, 18, 3, 438}, //Tin
	};
	
	public int getRandomAdd() {
		int random = 0;
		for (int i = 0; i < Rock_Settings.length; i++) {
			if (Rock_Settings[i][3] == 3) {
				random = Misc.random(3);
			} else if (Rock_Settings[i][3] == 7) {
				random = Misc.random(7);
			} else if (Rock_Settings[i][3] == 38) {
				random = Misc.random(38);
			} else if (Rock_Settings[i][3] == 78) {
				random = Misc.random(78);
			} else if (Rock_Settings[i][3] == 155) {
				random = Misc.random(155);
			} else if (Rock_Settings[i][3] == 315) {
				random = Misc.random(315);
			} else if (Rock_Settings[i][3] == 970) {
				random = Misc.random(970);
			}
		}
		return random;
	}
	
	int a = -1;
	
	public void repeatAnimation(final Client c) {
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.isMining) {
					c.startAnimation(Pick_Settings[a][3]);
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
				c.startAnimation(65535);
				c.isMining = false;
			}
		}, 3);
	}
	
	public void startMining(final Client player, final int objectNumber, final int objectX, final int objectY, final int type) {
		if (player.isMining)
			return;
		if (player.miningRock)
			return;
		int miningLevel = player.playerLevel[player.playerMining];
		a = -1;
		player.turnPlayerTo(objectX, objectY);
		if (Rock_Settings[objectNumber][1] > miningLevel) {
			player.getActionSender().sendMessage("You need a Mining level of " + Rock_Settings[objectNumber][1] + " to mine this rock.");
			return;
		}
		for (int i = 0; i < Pick_Settings.length; i++) {
			if (player.getItemAssistant().playerHasItem(Pick_Settings[i][0]) || player.playerEquipment[player.playerWeapon] == Pick_Settings[i][0]) {
				if (Pick_Settings[i][1] <= miningLevel) {
					a = i;
				}
			}
		}
		if (a == -1) {
			player.getActionSender().sendMessage("You need a pickaxe to mine this rock.");
			return;
		}
		if (player.getItemAssistant().freeSlots() < 1) {
			player.getActionSender().sendMessage("You do not have enough inventory slots to do that.");
			return;
		}
		player.startAnimation(Pick_Settings[a][3]);
		player.isMining = true;
		repeatAnimation(player);
		player.rockX = objectX;
		player.rockY = objectY;
		player.miningRock = true;
		if (player.tutorialProgress == 17 || player.tutorialProgress == 18) {
			player.getActionSender().chatbox(6180);
			player.getDialogueHandler().chatboxText(player, "", "Your character is now attempting to mine the rock.", "This should only take a few seconds.", "", "Please wait");
			player.getActionSender().chatbox(6179);
		} else {
			player.getActionSender().sendMessage("You swing your pick at the rock.");
		}
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.isMining) {
					container.stop();
					player.startAnimation(65535);
					return;
				}
				if (player.isMining) {
					player.getItemAssistant().addItem(Rock_Settings[objectNumber][4], 1);
					player.getPlayerAssistant().addSkillXP(Rock_Settings[objectNumber][2], player.playerMining);
					player.getActionSender().sendMessage("You manage to mine some " + ItemAssistant.getItemName(Rock_Settings[objectNumber][4]).toLowerCase() + ".");
				}
				if (player.tutorialProgress == 17) {
					if (objectNumber == 18) {
						player.getDialogueHandler().sendStatement("You should mine tin first.");
						resetMining(player);
						return;
					}
					if (player.getItemAssistant().playerHasItem(438)) {
						player.getActionSender().createArrow(3086, 9501, player.getH(), 2);
						player.getDialogueHandler().chatboxText(player, "Now you have some tin ore you must need some copper ore, then", "you'll have all you need to create a bronze bar. As you did before", "riger click on the copper rock and select 'mine'.", "", "Mining");
						player.tutorialProgress = 18;
					}
				} else if (player.tutorialProgress == 18) {
					if (objectNumber == 19) {
						player.getDialogueHandler().sendStatement("You have already mined this type of ore, now try the other.");
						resetMining(player);
						return;
					}
					if (player.getItemAssistant().playerHasItem(436)) {
						player.getActionSender().createArrow(3078, 9495, 0, 2);
						player.getDialogueHandler().sendDialogues(3061, -1);
					}
				}
				if (player.getItemAssistant().freeSlots() < 1) {
					player.getActionSender().sendMessage("You have ran out of inventory slots.");
					container.stop();
				}
				mineRock(Rock_Settings[objectNumber][3] + getRandomAdd(), objectX, objectY, type, Rock_Settings[objectNumber][0]);
				player.isMining = false;
				container.stop();
			}
			@Override
			public void stop() {
				player.getPlayerAssistant().removeAllWindows();
				player.startAnimation(65535);
				player.isMining = false;
				player.rockX = 0;
				player.rockY = 0;
				player.miningRock = false;
				return;
			}
		}, getTimer(objectNumber, a, miningLevel));
	}
	
	public static void resetMining(Client c) {
		c.getPlayerAssistant().removeAllWindows();
		c.startAnimation(65535);
		c.isMining = false;
		c.rockX = 0;
		c.rockY = 0;
		c.miningRock = false;
	}

	public int getTimer(int b, int c, int level) {
		double timer = (int)((Rock_Settings[b][1]  * 2) + 20 + Misc.random(20))-((Pick_Settings[c][2] * (Pick_Settings[c][2] * 0.75)) + level);
		if (timer < 2.0) {
			return 2;
		} else {
			return (int)timer;
		}
	}

	public void mineRock(int respawnTime, int x, int y, int type, int i) {
		new Object(452, x, y, 0, type, 10, i, respawnTime);
		Region.addObject(452, x, y, 0, 10, type, false);
		for (int t = 0; t < PlayerHandler.players.length; t++) {
			if (PlayerHandler.players[t] != null) {
				if (PlayerHandler.players[t].rockX == x && PlayerHandler.players[t].rockY == y) {
					PlayerHandler.players[t].isMining = false;
					PlayerHandler.players[t].startAnimation(65535);
					PlayerHandler.players[t].rockX = 0;
					PlayerHandler.players[t].rockY = 0;
				}
			}
		}
	}
	
	public static void prospectRock(final Client c, final String itemName) {
		if (c.tutorialProgress == 15 || c.tutorialProgress == 16) {
			c.getPlayerAssistant().removeAllWindows();
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler()
			.chatboxText(
					c,
					"Please wait.",
					"Your character is now attempting to prospect the rock. This should",
					"only take a few seconds.", "", "");
			c.getActionSender().chatbox(6179);
			   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.tutorialProgress == 15) {
						c.getActionSender().sendMessage(
								"This rock contains "
										+ itemName.toLowerCase() + ".");
						c.getActionSender().chatbox(6180);
						c.getDialogueHandler()
						.chatboxText(
								c,
								"",
								"So now you know there's tin in the grey rocks. Try prospecting",
								"the brown ones next.", "",
								"It's tin");
						c.getActionSender().createArrow(3086, 9501,
								c.getH(), 2);
						c.getActionSender().chatbox(6179);
						c.tutorialProgress = 16;
						container.stop();
						return;
					} else if (c.tutorialProgress == 16) {
						c.getActionSender().sendMessage(
								"This rock contains "
										+ itemName.toLowerCase() + ".");
						c.getActionSender().chatbox(6180);
						c.getDialogueHandler()
						.chatboxText(
								c,
								"Talk to the Mining Instructor to find out about these types of",
								"ore and how you can mine them. He'll even give you the",
								"required tools.", "",
								"It's copper");
						c.getActionSender().createArrow(1, 5);
						c.getActionSender().chatbox(6179);
						container.stop();
						return;
					}
					c.getActionSender().sendMessage(
							"This rock contains "
									+ itemName.toLowerCase() + ".");
					stop();
				}

					@Override
					public void stop() {
						
					}

			}, 3);
			return;
		}
		c.getActionSender().sendMessage("You examine the rock for ores...");
		   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				c.getActionSender().sendMessage("This rock contains " + itemName + ".");
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 3);
	}

	public static void prospectNothing(final Client c) {
		c.getActionSender().sendMessage("You examine the rock for ores...");
		   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				c.getActionSender().sendMessage("There is no ore left in this rock.");
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 2);
	}

	public static enum rockData {
		ESSENCE(new int[] { 2491 }, 1, 5, 3, 1, new int[] { 1436 }), 
		CLAY(new int[] { 2108, 2109, 11189, 11190, 11191, 9713, 9711, 14905, 14904 }, 1, 5, 1, 5, new int[] { 434 }), 
		COPPER(new int[] { 3042, 2091, 2090, 9708, 9709, 9710, 11960, 14906, 14907 }, 1, 18, 1, 8, new int[] { 436 }), 
		TIN(new int[] { 2094, 2095, 3043, 9716, 9714, 11958, 11957, 11959, 11933, 11934, 11935, 14903, 14902 }, 1, 18, 1, 8, new int[] { 438 }), 
		BLURITE(new int[] { 10574, 10583, 2110 }, 10, 20, 1, 8, new int[] { 668 }), 
		IRON(new int[] { 2093, 2092, 9717, 9718, 9719, 11962, 11956, 11954, 14856, 14857, 14858, 14914, 14913 }, 15, 35, 2, 5, new int[] { 440 }), 
		SILVER(new int[] { 2101, 11186, 11187, 11188, 2100 }, 20, 40, 3, 20, new int[] { 442 }), 
		COAL(new int[] { 2096, 2097, 11963, 11964, 14850, 14851, 14852, 11930, 11931 }, 30, 50, 4, 25, new int[] { 453 }), 
		GOLD(new int[] { 2099, 2098, 11183, 11184, 11185, 9720, 9722 }, 40,	65, 6, 33, new int[] { 444 }), 
		MITHRIL(new int[] { 2103, 2102, 14853, 14854, 14855 }, 55, 80, 8, 50, new int[] { 447 }), 
		ADAMANT(new int[] { 2104, 2105, 14862, 14863, 14864 }, 70, 95, 10, 83, new int[] { 449 }),
		RUNE(new int[] { 14859, 14860, 2106, 2107 }, 85, 125, 20, 166, new int[] { 451 }), 
		GRANITE(new int[] { 10947 }, 45, 75, 10, 10, new int[] { 6979, 6981, 6983 }), 
		SANDSTONE(new int[] { 10946 }, 35, 60, 5, 5, new int[] { 6971, 6973, 6975, 6977 }), 
		GEM(new int[] {2111}, 40, 65, 6, 120, new int[] {1});

		private final int levelReq, mineTimer, respawnTimer, xp;
		private final int[] oreIds;
		private final int[] objectId;

		private rockData(final int[] objectId, final int levelReq, final int xp, final int mineTimer, final int respawnTimer, final int... oreIds) {
			this.objectId = objectId;
			this.levelReq = levelReq;
			this.xp = xp;
			this.mineTimer = mineTimer;
			this.respawnTimer = respawnTimer;
			this.oreIds = oreIds;
		}

		public int getObject(final int object) {
			for (int element : objectId) {
				if (object == element) {
					return element;
				}
			}
			return -1;
		}

		public int getLevel() {
			return levelReq;
		}

		public int getXp() {
			return xp;
		}

		public int getTimer() {
			return mineTimer;
		}

		public int getResapwn() {
			return respawnTimer;
		}

		public int[] getOreIds() {
			return oreIds;
		}
	}
	
	public static boolean rockExists(Client c, int rockExist) {
		boolean rockExists = false;
		for (final rockData a : rockData.values()) {
			if (rockExist == a.getObject(rockExist)) {
				rockExists = true;
			}
		}
		return rockExists;
	}

}
