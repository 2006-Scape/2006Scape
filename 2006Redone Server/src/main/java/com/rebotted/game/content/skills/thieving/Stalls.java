package com.rebotted.game.content.skills.thieving;

import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.randomevents.RandomEventHandler;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.items.ItemList;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

public class Stalls {


	public static enum stallData {
		VEGETABLE_STALL(4706, 2, 10, 0, new int[] { 1965, 1 }),
		BAKER_STALL(2561, 5, 16, 3, new int[] { 2309, 1 }, new int[] { 1891, 1 }, new int[] { 1895, 1 }),
		TEA_STALL(635, 5, 16, 0, new int[] {712, 1 }),
		SILK_STALL(2560, 20, 24, 2, new int[] { 950, 1 }),
		WINE_STALL(14011, 22, 27, 0, new int[] { 1935, 1 }, new int[] {i("jug of water"), 1 }, new int[] { i("jug of wine"), 1 },
				new int[] { i("grapes"), 1 }), SEED_STALL(7053, 27, 10, 0, new int[] { i("potato seed"), 1 }, new int[] {i("onion seed"), 1 },
				new int[] { i("cabbage seed"), 1 }, new int[] {i("tomato seed"), 1 }, new int[] { i("sweetcorn seed"), 1 }, new int[] { i("strawberry seed"), 1 },
				new int[] {i("watermelon seed"), 1 }, new int[] {i("barley seed"), 1 }, new int[] { i("jute seed"), 1 }, new int[] { i("marigold seed"), 1 },
				new int[] {i("rosemary seed"), 1 }, new int[] {i("hammerstone seed"), 1 }, new int[] {i("asgarnain seed"), 1 }, new int[] {i("yanillian seed"), 1 },
				new int[] {i("krandorian seed"), 1 }, new int[] {i("wildblood seed"), 1 }), 
		FUR_STALL(2563, 35, 36, 0, new int[] { 6814, 1 }, new int[] { 958, 1 }),
		FUR_STALL2(4278, 35, 36, 0, new int[] { 6814, 1 }, new int[] { 958, 1 }),
		FISH_STALL(4705, 42, 42, 0, new int[] { 359, 1 }),
		FISH_STALL2(4277, 42, 42, 0, new int[] { 359, 1 }),
		SILVER_STALL(2565, 50, 54, 2, new int[] { 442, 1 }, new int[] { 2355, 1 }),
		SPICE_STALL(2564, 65, 81.3, 0, new int[] { 2007, 1 }, new int[] { 946, 1 }, new int[] { 1550, 1 }), 
		GEM_STALL(2562, 75, 160, 3,
				new int[] { 1617, 1 }, new int[] { 1619, 1 }, new int[] { 1621, 1 }, new int[] { 1623, 1 }),
		MAGIC_STALL(4877, 65, 100, 0, new int[] {i("air rune"), 1}, new int[] {i("water rune"), 1}, new int[] {i("fire rune"), 1}, new int[] {i("law rune"), 1}),
		SCIMITAR_STALL(4878, 65, 100, 0, new int[] {i("iron scimitar"), 1}, new int[] {i("steel scimitar"), 1});

		private int objectId, levelReq, face;
		private int[][] stalls;
		private double xp;
		private long respawnTime;

		private stallData(final int objectId, final int levelReq, final double xp, final int face, final int[]... stalls) {
			this.objectId = objectId;
			this.levelReq = levelReq;
			this.xp = xp;
			this.face = face;
			this.stalls = stalls;
			this.respawnTime = System.currentTimeMillis();
		}

		public int getObject() {
			return objectId;
		}

		public int getLevel() {
			return levelReq;
		}

		public double getXp() {
			return xp;
		}

		public int getFace() {
			return face;
		}

		public int[][] getStalls() {
			return stalls;
		}

	}

	public static int r(int random) {
		return Misc.random(random);
	}

	public static int i(String ItemName) {
		return getItemId(ItemName);
	}

	public static int getItemId(String itemName) {
		for (ItemList i : GameEngine.itemHandler.itemList) {
			if (i != null) {
				if (i.itemName.equalsIgnoreCase(itemName)) {
					return i.itemId;
				}
			}
		}
		return -1;
	}

	public static boolean isObject(int object) {
		for (final stallData s : stallData.values()) {
			if(object == s.getObject()) {
				return true;
			}
		}
		return false;
	}

	public static void attemptStall(final Player p, final int objectId, final int x, final int y) {
		// Skill is disabled on this server
		if (!SkillHandler.THIEVING) {
			p.getPacketSender().sendMessage("This skill is currently disabled.");
			return;
		}
		// In combat
		if(p.underAttackBy > 0 || p.underAttackBy2 > 0) {
			p.getPacketSender().sendMessage("You can't steal from a stall while in combat!");
			return;
		}
		// No inventory space
		if (p.getItemAssistant().freeSlots() == 0) {
			p.getPacketSender().sendMessage("Not enough space in your inventory.");
			return;
		}
		for (final stallData s : stallData.values()) {
			if(objectId == s.getObject()) {
				// Wait for respawn
				if (System.currentTimeMillis() < s.respawnTime) {
					long timeFirstStealFromStall = s.respawnTime - (GameConstants.CYCLE_TIME * getRespawnTime(s.objectId));

					// If stealing from stall at the same tick as another player
					if(p.hasThievedStall() || System.currentTimeMillis() - timeFirstStealFromStall >= GameConstants.CYCLE_TIME) {
						p.getPacketSender().sendMessage("You need to wait longer before you can thieve this stall!");
						return;
					}
				}
				// Thieving level too low
				if (p.playerLevel[p.playerThieving] < s.getLevel()) {
					p.getDialogueHandler().sendStatement("You must have a thieving level of " + s.getLevel() + " to steal from this stall.");
					return;
				}
				// Failed, was caught red handed
				if(Misc.random(4) == 1 && p.playerLevel[p.playerThieving] < 99) {
					failGuards(p);
					return;
				}
				p.startAnimation(832);
				RandomEventHandler.addRandom(p);
				int respawnTime = getRespawnTime(s.getObject());
				GameEngine.objectHandler.createAnObject(p, 634, x, y, 0, getSpecialFace(p, s));
				p.getPlayerAssistant().addSkillXP((int) s.getXp(), p.playerThieving);
				int[] random = s.getStalls()[Misc.random(s.getStalls().length-1)];
				s.respawnTime = System.currentTimeMillis() + (respawnTime * GameConstants.CYCLE_TIME);
				p.getPacketSender().sendMessage("You steal a " + ItemAssistant.getItemName(random[0]) + " from the stall.");
				p.getItemAssistant().addItem(random[0], random[1]);
				p.setHasThievedStall(true);
				CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						GameEngine.objectHandler.createAnObject(p, s.getObject(), x, y, 0, getSpecialFace(p, s));
						p.setHasThievedStall(false);
						container.stop();
					}
					@Override
					public void stop() {
					}
				}, respawnTime);
			}
		}
	}
	
	private static int getSpecialFace(Player player, stallData s) {
		int face;
		if (player.objectX == 3083 && player.objectY == 3251) {
			face = 3;
		} else if (player.objectX == 3075 && player.objectY == 3249) {
			face = 1;
		} else if (player.objectX == 3079 && player.objectY == 3253) {
			face = 2;
		} else {
			face = s.getFace();
		}
		return face;
	}

	private static int getRespawnTime(int i) {
		switch (i) {
			case 4706:
				return 3;// veg
			case 2561:
				return 4;// baker
			case 635:
				return 12;// tea
			case 2560:
				return 13;// silk
			case 14011:
				return 27;// wine
			case 7053:
				return 18;// seed
			case 2563:
				return 25;// fur
			case 4705:
				return 27;// fish
			case 2565:
				return 50;// silver
			case 2564:
			case 4877:
			case 4878:
				return 133;// spice, scimitar, magic
			case 2562:
				return 300;// gem
		}
		return 5;
	}

	private static void failGuards(final Player p) {
		for (int i = 1; i < NpcHandler.MAX_NPCS; i ++) {
			if (NpcHandler.npcs[i] != null) {
				if (NpcHandler.npcs[i].npcType == 32 || NpcHandler.npcs[i].npcType == 1317 || NpcHandler.npcs[i].npcType == 2236 || NpcHandler.npcs[i].npcType == 2571) {
					if (p.goodDistance(p.absX, p.absY, NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY, 7)
							&& p.heightLevel == NpcHandler.npcs[i].heightLevel) {
						if (!NpcHandler.npcs[i].underAttack) {
							NpcHandler.npcs[i].forceChat("What do you think you're doing?!?");
							NpcHandler.npcs[i].underAttack = true;
							NpcHandler.npcs[i].killerId = p.playerId;
							return;
						}
					}
				}
			}
		}
	}
}