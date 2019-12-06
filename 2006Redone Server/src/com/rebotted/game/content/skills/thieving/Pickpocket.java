package com.rebotted.game.content.skills.thieving;

import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.content.randomevents.RandomEventHandler;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.items.ItemList;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Player;
import com.rebotted.util.Misc;

public class Pickpocket extends SkillHandler {

	/**
	 * Pickpocket.java
	 **/

	public static enum npcData {

		MAN(new int[] { 1, 2, 3, 3222 }, 1, 8.0, 1, 5, new int[][] {
				{995, 3},
		}),
		WOMEN(new int[] { 4, 5, 6 }, 1, 8.0, 1, 5, new int[][] {
				{995, 3},
		}),
		FARMER(new int[] { 7, 1757 }, 10, 14.5, 1, 5, new int[][]{
				{995, 9},
				{5318, 4},
		}),
		HAM_FEMALE(new int[] { 1714, 1715 }, 15, 18.5, 2, 4, new int[][]{
				{995, 2, 19},
				{4302, 1},
				{4304, 1},
				{4298, 1},
				{4308, 1},
				{4300, 1},
				{4310, 1},
				{4306, 1},
		}),
		HAM_MALE(new int[] { 1714 }, 20, 22.5, 2, 4, new int[][]{
				{995, 2, 19},
				{4302, 1},
				{4304, 1},
				{4298, 1},
				{4308, 1},
				{4300, 1},
				{4310, 1},
				{4306, 1},
		}),
		WARRIOR(new int[] { 15, 18 }, 25, 26.0, 2, 5, new int[][] {
				{995, 18},
		}),
		ROGUE(new int[] { 187 }, 32, 35.5, 2, 5, new int[][]{
				{995, 25},
				{995, 40},
				{7919, 1},
				{556, 6},
				{5686, 1},
				{1523, 1},
				{1944, 1},
		}),
		MASTER_FARMER(new int[] { 2234, 2235 }, 38, 43.0, 2, 5, new int[][] {
				{i("potato seed"), 1, 2},
				{i("onion seed"), 1, 2},
				{i("cabbage seed"), 1, 2},
				{i("tomato seed"), 1, 1},
				{i("sweetcorn seedseed"), 1, 1},
				{i("strawberry seed"), 1},
				{i("watermelon seed"), 1},
				{i("barely seed"), 1, 3},
				{i("hammerstone seed"), 1, 2},
				{i("asgarnian seed"), 1, 1},
				{i("jute seed"), 1, 2},
				{i("yanillian seed"), 1, 1},
				{i("krandorian seed"), 1},
				{i("wildblood seed"), 1},
				{i("redberry seed"), 1},
				{i("cadavaberry seed"), 1},
				{i("dwellberry seed"), 1},
				{i("jangerberry seed"), 1},
				{i("whiteberry seed"), 1},
				{i("poison ivy seed"), 1},
				{i("marigold seed"), 1},
				{i("rosemarry seed"), 1},
				{i("nasturtium seed"), 1},
				{i("woad seed"), 1},
				{i("limpwurt seed"), 1},
				{i("guam seed"), 1},
				{i("marentill seed"), 1},
				{i("tarromin seed"), 1},
				{i("harralander seed"), 1},
				{i("ranarr seed"), 1},
				{i("toadflax seed"), 1},
				{i("irit seed"), 1},
				{i("avantoe seed"), 1},
				{i("kwuarm seed"), 1},
				{i("snapdragon seed"), 1},
				{i("cadantine seed"), 1},
				{i("lantadyme seed"), 1},
				{i("dwarf weed seed"), 1},
				{i("torstol seed"), 1},
				{i("bittercap mushroom spore"), 1},
				{i("belladonna seed"), 1},
				{i("cactus seed"), 1},
		}),
		GUARD(new int[] { 9, 32 }, 40, 46.8, 2, 5, new int[][] {
				{995, 30},
		}),
		KNIGHT(new int[] { 26 }, 55, 84.3, 3, 5, new int[][] {
				{995, 50},
		}),
		MENAPHITE_THUG(new int[] { 1904 }, 65, 137.5, 5, 5, new int[][] {
				{995, 60},
		}),
		WATCHMAN(new int[] { 431 }, 65, 137.5, 3, 5, new int[][] {
				{995, 60},
				{4593, 1},
		}),
		PALADIN(new int[] { 20 }, 70, 151.8, 5, 4, new int[][]{
				{995, 80},
				{562, 2},
		}),
		GNOME(new int[] { 66 }, 75, 198.3, 1, 6, new int[][] {
				{995, 300},
				{557, 1},
				{444, 1},
				{569, 1},
				{2150, 1},
				{2162, 1},
		}),
		HERO(new int[] { 21 }, 80, 273.3, 4, 6, new int[][]{
				{995, 300},
				{560, 2},
				{565, 1},
				{569, 1},
				{1617, 1},
				{444, 1},
				{1993, 1},
		});

		private final int levelReq, damage, stun;
		private final int[] npcId;
		private final int[][] pickpockets;
		private final double xp;

		private npcData(final int[] npcId, final int levelReq, final double xp,
				final int damage, final int stun, final int[][] pickpockets) {
			this.npcId = npcId;
			this.levelReq = levelReq;
			this.xp = xp;
			this.pickpockets = pickpockets;
			this.damage = damage;
			this.stun = stun;
		}

		public int getNpc(final int npc) {
			for (int element : npcId) {
				if (npc == element) {
					return element;
				}
			}
			return -1;
		}

		public int getLevel() {
			return levelReq;
		}

		public double getXp() {
			return xp;
		}

		public int[][] getPickPockets() {
			return pickpockets;
		}

		public int getDamage() {
			return damage;
		}

		public int getStun() {
			return stun;
		}
	}

	public static int r(int random) {
		return Misc.random(random);
	}

	public static int i(String ItemName) {
		return getItemId(ItemName);
	}

	public static int getItemId(String itemName) {
		for (ItemList i : GameEngine.itemHandler.ItemList) {
			if (i != null) {
				if (i.itemName.equalsIgnoreCase(itemName)) {
					return i.itemId;
				}
			}
		}
		return -1;
	}

	public static boolean isNPC(Player c, int npc) {
		for (final npcData n : npcData.values()) {
			if (npc == n.getNpc(npc)) {
				return true;
			}
		}
		return false;
	}

	public static void attemptPickpocket(final Player c, final int npcId) {
		if (System.currentTimeMillis() - c.lastThieve < 2000 || c.playerStun) {
			return;
		}
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.getPacketSender().sendMessage("You can't pickpocket while in combat!");
			return;
		}
		if (!THIEVING) {
			c.getPacketSender().sendMessage("This skill is currently disabled.");
			return;
		}
		// membersOnly();
		for (final npcData n : npcData.values()) {
			if (npcId == n.getNpc(npcId)) {
				if (c.playerLevel[c.playerThieving] < n.getLevel()) {
					c.getDialogueHandler().sendStatement("You need a Thieving level of " + n.getLevel() + " to pickpocket the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + ".");
					return;
				}
				c.getPacketSender().sendMessage("You attempt to pick the  " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
				c.startAnimation(881);
				if (Misc.random(c.playerLevel[17] + 5) < Misc.random(n.getLevel())) {
					RandomEventHandler.addRandom(c);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (c.disconnected) {
								container.stop();
								return;
							}
							c.playerStun = true;
							c.setHitDiff(n.getDamage());
							c.setHitUpdateRequired(true);
							c.playerLevel[3] -= n.getDamage();
							c.getPlayerAssistant().refreshSkill(3);
							c.gfx100(80);
							c.startAnimation(404);
							c.getPacketSender().sendSound(SoundList.STUNNED, 100, 0);
							for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
								if (NpcHandler.npcs[i] != null) {
									if (NpcHandler.npcs[i].npcType == npcId) {
										if (c.goodDistance(c.absX, c.absY, NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY, 1) && c.heightLevel == NpcHandler.npcs[i].heightLevel) {
												if (!NpcHandler.npcs[i].underAttack) {
													NpcHandler.npcs[i].forceChat("What do you think you're doing?");
													NpcHandler.npcs[i].facePlayer(c.playerId);
												}
											}
										}
								}
							}
							c.lastThieve = System.currentTimeMillis() + 5000;
							c.getPacketSender().sendMessage("You fail to pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
							container.stop();
						}
						@Override
						public void stop() {
							
						}
					}, 2);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (c.disconnected) {
								container.stop();
								return;
							}
							c.playerStun = false;
							container.stop();
						}

						@Override
						public void stop() {
							
						}
					}, n.getStun());
				} else {
					String message = "You pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.";
					final String message2 = message;
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							c.getPacketSender().sendMessage(message2);
							c.getPlayerAssistant().addSkillXP((int) n.getXp(),
									c.playerThieving);
							int[] random = n.getPickPockets()[Misc.random(n.getPickPockets().length - 1)];
							c.getItemAssistant().addItem(random[0], random[1] + (random.length > 2 ? Misc.random(random[2]) : 0));
							container.stop();
						}
						@Override
						public void stop() {
							
						}
					}, 2);
				}
				c.lastThieve = System.currentTimeMillis();
			}
		}
	}
}
