package redone.game.content.skills.thieving;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemList;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.antimacro.AntiBotting;
import redone.util.Misc;

public class Pickpocket extends SkillHandler {

	/**
	 * Pickpocket.java
	 **/

	public static enum npcData {

		MAN(new int[] { 1, 2, 3, 3222 }, 1, 8.0, 1, 5, new int[] { 995, 3 }), WOMEN(
				new int[] { 4, 5, 6 }, 1, 8.0, 1, 5, new int[] { 995, 3 }), FARMER(
				new int[] { 7, 1757 }, 10, 14.5, 1, 5, new int[] { 995, 9 },
				new int[] { 5318, 4 }), HAM_FEMALE(new int[] { 1715 }, 15,
				18.5, 2, 4, new int[] { 995, 2 + r(19) },
				new int[] { 4302, 1 }, new int[] { 4304, 1 }, new int[] { 4298,
						1 }, new int[] { 4308, 1 }, new int[] { 4300, 1 },
				new int[] { 4310, 1 }, new int[] { 4306, 1 }), HAM_MALE(
				new int[] { 1714 }, 20, 22.5, 2, 4,
				new int[] { 995, 2 + r(19) }, new int[] { 4302, 1 }, new int[] {
						4304, 1 }, new int[] { 4298, 1 },
				new int[] { 4308, 1 }, new int[] { 4300, 1 }, new int[] { 4310,
						1 }, new int[] { 4306, 1 }), WARRIOR(
				new int[] { 15, 18 }, 25, 26.0, 2, 5, new int[] { 995, 18 }), ROGUE(
				new int[] { 187 }, 32, 35.5, 2, 5, new int[] { 995, 25 },
				new int[] { 995, 40 }, new int[] { 7919, 1 }, new int[] { 556,
						6 }, new int[] { 5686, 1 }, new int[] { 1523, 1 },
				new int[] { 1944, 1 }), MASTER_FARMER(new int[] { 2234, 2235 },
				38, 43.0, 2, 5, new int[] { i("potato seed"), 1 + r(2) },
				new int[] { i("onion seed"), 1 + r(2) }, new int[] {
						i("cabbage seed"), 1 + r(2) }, new int[] {
						i("tomato seed"), 1 + r(1) }, new int[] {
						i("sweetcorn seedseed"), 1 + r(1) }, new int[] {
						i("strawberry seed"), 1 }, new int[] {
						i("watermelon seed"), 1 }, new int[] {
						i("barely seed"), 1 + r(3) }, new int[] {
						i("hammerstone seed"), 1 + r(2) }, new int[] {
						i("asgarnian seed"), 1 + r(1) }, new int[] {
						i("jute seed"), 1 + r(2) }, new int[] {
						i("yanillian seed"), 1 + r(1) }, new int[] {
						i("krandorian seed"), 1 }, new int[] {
						i("wildblood seed"), 1 }, new int[] {
						i("redberry seed"), 1 }, new int[] {
						i("cadavaberry seed"), 1 }, new int[] {
						i("dwellberry seed"), 1 }, new int[] {
						i("jangerberry seed"), 1 }, new int[] {
						i("whiteberry seed"), 1 }, new int[] {
						i("poison ivy seed"), 1 }, new int[] {
						i("marigold seed"), 1 }, new int[] {
						i("rosemarry seed"), 1 }, new int[] {
						i("nasturtium seed"), 1 }, new int[] { i("woad seed"),
						1 }, new int[] { i("limpwurt seed"), 1 }, new int[] {
						i("guam seed"), 1 },
				new int[] { i("marentill seed"), 1 }, new int[] {
						i("tarromin seed"), 1 }, new int[] {
						i("harralander seed"), 1 }, new int[] {
						i("ranarr seed"), 1 }, new int[] { i("toadflax seed"),
						1 }, new int[] { i("irit seed"), 1 }, new int[] {
						i("avantoe seed"), 1 },
				new int[] { i("kwuarm seed"), 1 }, new int[] {
						i("snapdragon seed"), 1 }, new int[] {
						i("cadantine seed"), 1 }, new int[] {
						i("lantadyme seed"), 1 }, new int[] {
						i("dwarf weed seed"), 1 }, new int[] {
						i("torstol seed"), 1 }, new int[] {
						i("bittercap mushroom spore"), 1 }, new int[] {
						i("belladonna seed"), 1 }, new int[] {
						i("cactus seed"), 1 }), GUARD(new int[] { 9, 32 }, 40,
				46.8, 2, 5, new int[] { 995, 30 }), KNIGHT(new int[] { 26 },
				55, 84.3, 3, 5, new int[] { 995, 50 }), MENAPHITE_THUG(
				new int[] { 1904 }, 65, 137.5, 5, 5, new int[] { 995, 60 }), WATCHMAN(
				new int[] { 431 }, 65, 137.5, 3, 5, new int[] { 995, 60 },
				new int[] { 4593, 1 }), PALADIN(new int[] { 20 }, 70, 151.8, 5,
				4, new int[] { 995, 80 }, new int[] { 562, 2 }), GNOME(
				new int[] { 66 }, 75, 198.3, 1, 6, new int[] { 995, 300 },
				new int[] { 557, 1 }, new int[] { 444, 1 },
				new int[] { 569, 1 }, new int[] { 2150 }, new int[] { 2162 }), HERO(
				new int[] { 21 }, 80, 273.3, 4, 6, new int[] { 995, 300 },
				new int[] { 560, 2 }, new int[] { 565, 1 },
				new int[] { 569, 1 }, new int[] { 1617, 1 },
				new int[] { 444, 1 }, new int[] { 1993 });

		private final int levelReq, damage, stun;
		private final int[] npcId;
		private final int[][] pickpockets;
		private final double xp;

		private npcData(final int[] npcId, final int levelReq, final double xp,
				final int damage, final int stun, final int[]... pickpockets) {
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
		for (ItemList i : Server.itemHandler.ItemList) {
			if (i != null) {
				if (i.itemName.equalsIgnoreCase(itemName)) {
					return i.itemId;
				}
			}
		}
		return -1;
	}

	public static boolean isNPC(Client c, int npc) {
		for (final npcData n : npcData.values()) {
			if (npc == n.getNpc(npc)) {
				return true;
			}
		}
		return false;
	}

	public static void attemptPickpocket(final Client c, final int npcId) {
		if (System.currentTimeMillis() - c.lastThieve < 2000 || c.playerStun) {
			return;
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't thieve right now!");
			return;
		}
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.getActionSender().sendMessage("You can't pickpocket while in combat!");
			return;
		}
		if (!THIEVING) {
			c.getActionSender().sendMessage("This skill is currently disabled.");
			return;
		}
		// membersOnly();
		for (final npcData n : npcData.values()) {
			if (npcId == n.getNpc(npcId)) {
				if (c.playerLevel[c.playerThieving] < n.getLevel()) {
					c.getDialogueHandler().sendStatement("You need a Thieving level of " + n.getLevel() + " to pickpocket the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + ".");
					return;
				}
				c.getActionSender().sendMessage("You attempt to pick the  " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
				c.startAnimation(881);
				if (Misc.random(c.playerLevel[17] + 5) < Misc.random(n.getLevel())) {
					if (Misc.random(200) == 0) {
						AntiBotting.botCheckInterface(c);
					}
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
							NpcHandler.npcs[npcId].forceChat("What do you think you're doing?");
							NpcHandler.npcs[npcId].facePlayer(c.playerId);
							c.lastThieve = System.currentTimeMillis() + 5000;
							c.getActionSender().sendMessage("You fail to pick the " + NpcHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() + "'s pocket.");
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
							c.getActionSender().sendMessage(message2);
							c.getPlayerAssistant().addSkillXP((int) n.getXp(),
									c.playerThieving);
							int[] random = n.getPickPockets()[Misc.random(n
									.getPickPockets().length - 1)];
							c.getItemAssistant().addItem(
									random[0],
									random[1]
											+ (random.length > 2 ? Misc
													.random(random[2]) : 0));
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
