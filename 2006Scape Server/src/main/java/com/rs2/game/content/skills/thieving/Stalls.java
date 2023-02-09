package com.rs2.game.content.skills.thieving;

import com.rs2.Constants;
import org.apollo.cache.def.ItemDefinition;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class Stalls {


	public static enum stallData {
		VEGETABLE_STALL(4706, 2, 10, 0, new int[] { 1965, 1 }),
		BAKER_STALL(2561, 5, 16, 3, new int[] { 2309, 1 }, new int[] { 1891, 1 }, new int[] { 1895, 1 }),
		TEA_STALL(635, 5, 16, 0, new int[] {712, 1 }),
		SILK_STALL(2560, 20, 24, 2, new int[] { 950, 1 }),
		WINE_STALL(14011, 22, 27, 0, 
				new int[] { 1935, 1 },
				new int[] { 1937, 1 },
				new int[] { 1993, 1 },
				new int[] { 1987, 1 }), 
		
		SEED_STALL(7053, 27, 10, 0, 
				new int[] { 5318, 1 },
				new int[] { 5319, 1 },
				new int[] { 5324, 1 },
				new int[] { 5322, 1 },
				new int[] { 5320, 1 },
				new int[] { 5323, 1 },
				new int[] { 5321, 1 },
				new int[] { 5305, 1 },
				new int[] { 5306, 1 },
				new int[] { 5096, 1 },
				new int[] { 5097, 1 },
				new int[] { 5307, 1 },
				new int[] { 5308, 1 },
				new int[] { 5309, 1 },
				new int[] { 5310, 1 },
				new int[] { 5311, 1 }), 
		
		FUR_STALL(2563, 35, 36, 0, new int[] { 6814, 1 }, new int[] { 958, 1 }),
		FUR_STALL2(4278, 35, 36, 0, new int[] { 6814, 1 }, new int[] { 958, 1 }),
		FISH_STALL(4705, 42, 42, 0, new int[] { 359, 1 }),
		FISH_STALL2(4277, 42, 42, 0, new int[] { 359, 1 }),
		SILVER_STALL(2565, 50, 54, 2, new int[] { 442, 1 }, new int[] { 2355, 1 }),
		SPICE_STALL(2564, 65, 81.3, 0, new int[] { 2007, 1 }, new int[] { 946, 1 }, new int[] { 1550, 1 }), 
		GEM_STALL(2562, 75, 160, 3,
				new int[] { 1617, 1 }, new int[] { 1619, 1 }, new int[] { 1621, 1 }, new int[] { 1623, 1 }),
		MAGIC_STALL(4877, 65, 100, 0, 
				new int[] { 556, 1 },
				new int[] { 555, 1 },
				new int[] { 554, 1 },
				new int[] { 563, 1 }),
		SCIMITAR_STALL(4878, 65, 100, 0, 
				new int[] { 1323, 1 },
				new int[] { 1325, 1 });

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
					long timeFirstStealFromStall = s.respawnTime - (Constants.CYCLE_TIME * getRespawnTime(s.objectId));

					// If stealing from stall at the same tick as another player
					if(p.hasThievedStall() || System.currentTimeMillis() - timeFirstStealFromStall >= Constants.CYCLE_TIME) {
						p.getPacketSender().sendMessage("You need to wait longer before you can thieve this stall!");
						return;
					}
				}
				// Thieving level too low
				if (p.playerLevel[Constants.THIEVING] < s.getLevel()) {
					p.getDialogueHandler().sendStatement("You must have a thieving level of " + s.getLevel() + " to steal from this stall.");
					return;
				}
				// Failed, was caught red handed
				if(Misc.random(4) == 1 && p.playerLevel[Constants.THIEVING] < 99) {
					failGuards(p);
					return;
				}
				p.startAnimation(832);
				RandomEventHandler.addRandom(p);
				int respawnTime = getRespawnTime(s.getObject());
				GameEngine.objectHandler.createAnObject(p, 634, x, y, 0, getSpecialFace(p, s));
				p.getPlayerAssistant().addSkillXP((int) s.getXp(), Constants.THIEVING);
				int[] random = s.getStalls()[Misc.random(s.getStalls().length-1)];
				s.respawnTime = System.currentTimeMillis() + (respawnTime * Constants.CYCLE_TIME);
				p.getPacketSender().sendMessage("You steal a " + ItemDefinition.lookup(random[0]).getName() + " from the stall.");
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