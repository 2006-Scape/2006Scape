package com.rs2.game.content.skills.thieving;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

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
			{5318, 1, 2},
			{5319, 1, 2},
			{5324, 1, 2},
			{5322, 1, 1},
			{5320, 1, 1},
			{5323, 1},
			{5321, 1},
			{5305, 1, 3},
			{5307, 1, 2},
			{5308, 1, 1},
			{5306, 1, 2},
			{5309, 1, 1},
			{5310, 1},
			{5311, 1},
			{5101, 1},
			{5102, 1},
			{5103, 1},
			{5104, 1},
			{5105, 1},
			{5106, 1},
			{5096, 1},
			{5097, 1},
			{5098, 1},
			{5099, 1},
			{5100, 1},
			{5291, 1},
			{5292, 1},
			{5293, 1},
			{5294, 1},
			{5295, 1},
			{5296, 1},
			{5297, 1},
			{5298, 1},
			{5299, 1},
			{5300, 1},
			{5301, 1},
			{5302, 1},
			{5303, 1},
			{5304, 1},
			{5282, 1},
			{5281, 1},
			{5280, 1},
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

	public static boolean isNPC(Player c, int npc) {
		for (final npcData n : npcData.values()) {
			if (npc == n.getNpc(npc)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean canSteal(Player player, int npcId) {
		if (System.currentTimeMillis() - player.lastThieve < 2000 || player.playerStun) {
			return false;
		}
		if (player.underAttackBy > 0 || player.underAttackBy2 > 0) {
			player.getPacketSender().sendMessage("You can't pickpocket while in combat!");
			return false;
		}
		if (System.currentTimeMillis() - player.logoutDelay < 4000) {
			return false;
		}
		if (!THIEVING) {
			player.getPacketSender().sendMessage("This skill is currently disabled.");
			return false;
		}
		return true;
	}

	public static void attemptPickpocket(final Player player, final int npcId) {
		if (!canSteal(player, npcId)) {
			return;
		}
		for (final npcData n : npcData.values()) {
			if (npcId == n.getNpc(npcId)) {
				if (player.playerLevel[Constants.THIEVING] < n.getLevel()) {
					player.getDialogueHandler().sendStatement("You need a Thieving level of " + n.getLevel() + " to pickpocket the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + ".");
					return;
				}
				player.getPacketSender().sendMessage("You attempt to pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
				player.startAnimation(881);
				if (Misc.random(player.playerLevel[Constants.THIEVING] + 5) < Misc.random(n.getLevel())) {
					RandomEventHandler.addRandom(player);
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (player.disconnected) {
								container.stop();
								return;
							}
							player.playerStun = true;
							player.setHitDiff(n.getDamage());
							player.setHitUpdateRequired(true);
							player.playerLevel[Constants.HITPOINTS] -= n.getDamage();
							player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
							player.gfx100(80);
							player.startAnimation(404);
							player.getPacketSender().sendSound(SoundList.STUNNED, 100, 0);
							for (int i = 0; i < NpcHandler.MAX_NPCS; i++) {
								if (NpcHandler.npcs[i] != null) {
									if (NpcHandler.npcs[i].npcType == npcId) {
										if (player.goodDistance(player.absX, player.absY, NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY, 1) && player.heightLevel == NpcHandler.npcs[i].heightLevel) {
											if (!NpcHandler.npcs[i].underAttack) {
												NpcHandler.npcs[i].forceChat("What do you think you're doing?");
												NpcHandler.npcs[i].facePlayer(player);
											}
										}
									}
								}
							}
							player.lastThieve = System.currentTimeMillis() + 5000;
							player.getPacketSender().sendMessage("You fail to pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
							container.stop();
						}
						@Override
						public void stop() {
							
						}
					}, 2);
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (player.disconnected) {
								container.stop();
								return;
							}
							player.playerStun = false;
							container.stop();
						}

						@Override
						public void stop() {
							
						}
					}, n.getStun());
				} else {
					String message = "You pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.";
					final String message2 = message;
					CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							player.getPacketSender().sendMessage(message2);
							player.getPlayerAssistant().addSkillXP((int) n.getXp(),
									Constants.THIEVING);
							int[] random = n.getPickPockets()[Misc.random(n.getPickPockets().length - 1)];
							player.getItemAssistant().addItem(random[0], random[1] + (random.length > 2 ? Misc.random(random[2]) : 0));
							container.stop();
						}
						@Override
						public void stop() {
							
						}
					}, 2);
				}
				player.lastThieve = System.currentTimeMillis();
			}
		}
	}
	
}
