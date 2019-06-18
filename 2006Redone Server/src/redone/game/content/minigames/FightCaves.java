package redone.game.content.minigames;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

public class FightCaves {

	public static final int TZ_KIH = 2627, TZ_KEK_SPAWN = 2738, TZ_KEK = 2630,
			TOK_XIL = 2631, YT_MEJKOT = 2741, KET_ZEK = 2743, TZTOK_JAD = 2745, YT_HURKOT = 2746;

	/**
	 * Holds the data for the 63 waves fight cave.
	 */
	private final int[][] WAVES = { { TZ_KIH }, { TZ_KIH, TZ_KIH }, { TZ_KEK },
			{ TZ_KEK, TZ_KIH }, { TZ_KEK, TZ_KIH, TZ_KIH }, { TZ_KEK, TZ_KEK },
			{ TOK_XIL }, { TOK_XIL, TZ_KIH }, { TOK_XIL, TZ_KIH, TZ_KIH },
			{ TOK_XIL, TZ_KEK }, { TOK_XIL, TZ_KEK, TZ_KIH },
			{ TOK_XIL, TZ_KEK, TZ_KIH, TZ_KIH }, { TOK_XIL, TZ_KEK, TZ_KEK },
			{ TOK_XIL, TOK_XIL }, { YT_MEJKOT }, { YT_MEJKOT, TZ_KIH },
			{ YT_MEJKOT, TZ_KIH, TZ_KIH }, { YT_MEJKOT, TZ_KEK },
			{ YT_MEJKOT, TZ_KEK, TZ_KIH },
			{ YT_MEJKOT, TZ_KEK, TZ_KIH, TZ_KIH },
			{ YT_MEJKOT, TZ_KEK, TZ_KEK }, { YT_MEJKOT, TOK_XIL },
			{ YT_MEJKOT, TOK_XIL, TZ_KIH },
			{ YT_MEJKOT, TOK_XIL, TZ_KIH, TZ_KIH },
			{ YT_MEJKOT, TOK_XIL, TZ_KEK },
			{ YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KIH },
			{ YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KIH, TZ_KIH },
			{ YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KEK },
			{ YT_MEJKOT, TOK_XIL, TOK_XIL }, { YT_MEJKOT, YT_MEJKOT },
			{ KET_ZEK }, { KET_ZEK, TZ_KIH }, { KET_ZEK, TZ_KIH, TZ_KIH },
			{ KET_ZEK, TZ_KEK }, { KET_ZEK, TZ_KEK, TZ_KIH },
			{ KET_ZEK, TZ_KEK, TZ_KIH, TZ_KIH }, { KET_ZEK, TZ_KEK, TZ_KEK },
			{ KET_ZEK, TOK_XIL }, { KET_ZEK, TOK_XIL, TZ_KIH },
			{ KET_ZEK, TOK_XIL, TZ_KIH, TZ_KIH }, { KET_ZEK, TOK_XIL, TZ_KEK },
			{ KET_ZEK, TOK_XIL, TZ_KEK, TZ_KIH },
			{ KET_ZEK, TOK_XIL, TZ_KEK, TZ_KIH, TZ_KIH },
			{ KET_ZEK, TOK_XIL, TZ_KEK, TZ_KEK },
			{ KET_ZEK, TOK_XIL, TOK_XIL }, { KET_ZEK, YT_MEJKOT },
			{ KET_ZEK, YT_MEJKOT, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TZ_KIH, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TZ_KEK },
			{ KET_ZEK, YT_MEJKOT, TZ_KEK, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TZ_KEK, TZ_KIH, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TZ_KEK, TZ_KEK },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KIH, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KEK },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KIH, TZ_KIH },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TZ_KEK, TZ_KEK },
			{ KET_ZEK, YT_MEJKOT, TOK_XIL, TOK_XIL },
			{ KET_ZEK, YT_MEJKOT, YT_MEJKOT }, { KET_ZEK, KET_ZEK },
			{ TZTOK_JAD } };
	
	private static final int[][] JAD_SPAWNS = {{2400, 5090},{2419, 5080}};
	private static final int[][] HEALER_COORDS = {{2390, 5101}, {2391, 5077}, {2411, 5084}, {2398, 5091}};
	private final static int[][] COORDINATES = {{2403, 5094}, {2390, 5096}, {2392, 5077}, {2408, 5080}, {2413, 5108}, {2381, 5106}, {2379, 5072}, {2420, 5082}};

	/**
	 * Handles spawning the next fightcave wave.
	 * 
	 * @param player
	 *            The player.
	 */
	public void spawnNextWave(Client player) {
		if (player != null) {
			if (player.waveId >= WAVES.length) {
				player.waveId = 0;
				return;
			}
			if (player.waveId < 0) {
				player.waveId = 0;
				return;
			}
			int npcAmount = WAVES[player.waveId].length;
			int wave = player.waveId +1;
			if (player.waveId < 62 && player.waveId > -1) {
				for (int j = 0; j < npcAmount; j++) {
						int npc = WAVES[player.waveId][j];
						int X = COORDINATES[j][0];
						int Y = COORDINATES[j][1];
						int H = player.heightLevel;
						int hp = getHp(npc);
						int max = getMax(npc);
						int atk = getAtk(npc);
						int def = getDef(npc);
						NpcHandler.spawnNpc(player, npc, X, Y, H, 0, hp, max, atk, def, true, false);	
					}
				player.getActionSender().sendMessage("You are now on wave @red@" + wave + "@bla@.");
				} else if (player.waveId == 62) {
					player.getDialogueHandler().sendDialogues(102, 2617);
					int a = Misc.random(1);
					int npc = WAVES[62][0];
					int X = JAD_SPAWNS[a][0];
					int Y = JAD_SPAWNS[a][1];
					int H = player.heightLevel;
					int hp = getHp(npc);
					int max = getMax(npc);
					int atk = getAtk(npc);
					int def = getDef(npc);
					NpcHandler.spawnNpc(player, npc, X, Y, H, 0, hp, max, atk, def, true, false);
					player.getActionSender().sendMessage("You are now on wave @red@63@bla@.");
				}
			player.tzhaarToKill = npcAmount;
			player.tzhaarKilled = 0;
		}
	}
	
	public static void ytMejKotEffect(Client player, int i) {
		if(NpcHandler.npcs[i].npcType == YT_MEJKOT) {
			if (NpcHandler.npcs[i].hitsToHeal < 2) {
			NpcHandler.npcs[i].hitsToHeal += 1;
			if (NpcHandler.npcs[i].hitsToHeal == 2) {
				NpcHandler.npcs[i].hitsToHeal = 0;
				NpcHandler.npcs[i].gfx0(444);
				NpcHandler.npcs[i].startAnimation(2639, i);
				NpcHandler.npcs[i].HP += 1+Misc.random(7);
				if (NpcHandler.npcs[i].HP > getHp(YT_MEJKOT))
					NpcHandler.npcs[i].HP = getHp(YT_MEJKOT);
				}
			}
		}
	}
	
	public static void spawnHealers(Client player, int i, int amount) {
		if (player.spawnedHealers < 4) {
		int hp = getHp(YT_HURKOT);
		int max = getMax(YT_HURKOT);
		int atk = getAtk(YT_HURKOT);
		int def = getDef(YT_HURKOT);
			for (int i1 = 0; i1 < amount; i1++) {
				NpcHandler.spawnNpc(player, YT_HURKOT, HEALER_COORDS[i1][0], HEALER_COORDS[i1][1], player.heightLevel, 0, hp, max, atk, def, false, false);
			}
		player.spawnedHealers = amount;
		player.canHealersRespawn = false;
		}
	}

	/**
	 * Handles the correct tz-kih effect; prayer is drained by the formula:
	 * drain = damage + 1
	 * 
	 * @param player
	 *            The player
	 * @param i
	 *            The npcId
	 * @param damage
	 *            What the npchit
	 */
	public static void tzKihEffect(Client player, int i, int damage) {
		if (NpcHandler.npcs[i].npcType == TZ_KIH) {
			if (player != null) {
				if (player.playerLevel[5] > 0) {
					player.playerLevel[5] -= 1 + damage;
					player.getPlayerAssistant().refreshSkill(5);
				}
			}
		}
	}

	public static void tzKekEffect(Client player, int i) {
		if (NpcHandler.npcs[i].npcType == TZ_KEK) {

			int x = NpcHandler.npcs[i].absX + 2;
			int y = NpcHandler.npcs[i].absY + 2;
			int x1 = NpcHandler.npcs[i].absX - 2;
			int y1 = NpcHandler.npcs[i].absY - 2;

			int hp = getHp(TZ_KEK_SPAWN);
			int max = getMax(TZ_KEK_SPAWN);
			int atk = getAtk(TZ_KEK_SPAWN);
			int def = getDef(TZ_KEK_SPAWN);

			if (player != null) {
				if (player.tzKekTimer == 0) {
					if (NpcHandler.npcs[i].isDead) {
						NpcHandler.spawnNpc(player, TZ_KEK_SPAWN, x, y,
								player.heightLevel, 0, hp, max, atk, def, true,
								false);
						NpcHandler.spawnNpc(player, TZ_KEK_SPAWN, x1, y1,
								player.heightLevel, 0, hp, max, atk, def, true,
								false);
					}
				}
			}
		}
	}

	public static int getHp(int npc) {
		switch (npc) {
		case TZ_KIH:
		case TZ_KEK_SPAWN:
			return 10;
		case TZ_KEK:
			return 20;
		case TOK_XIL:
			return 40;
		case YT_MEJKOT:
			return 80;
		case KET_ZEK:
			return 150;
		case YT_HURKOT:
			return 80;
		case TZTOK_JAD:
			return 250;
		}
		return 100;
	}

	public static int getMax(int npc) {
		switch (npc) {
		case TZ_KIH:
		case TZ_KEK_SPAWN:
			return 4;
		case TZ_KEK:
			return 7;
		case TOK_XIL:
			return 13;
		case YT_MEJKOT:
			return 28;
		case KET_ZEK:
			return 54;
		case TZTOK_JAD:
			return 97;
		case YT_HURKOT:
			return 16;
		}
		return 5;
	}

	public static int getAtk(int npc) {
		switch (npc) {
		case TZ_KIH:
		case TZ_KEK_SPAWN:
			return 30;
		case TZ_KEK:
			return 50;
		case TOK_XIL:
			return 100;
		case YT_MEJKOT:
			return 150;
		case KET_ZEK:
			return 450;
		case TZTOK_JAD:
			return 650;
		case YT_HURKOT:
			return 120;
		}
		return 100;
	}

	public static int getDef(int npc) {
		switch (npc) {
		case TZ_KIH:
		case TZ_KEK_SPAWN:
			return 30;
		case TZ_KEK:
			return 50;
		case TOK_XIL:
			return 100;
		case YT_MEJKOT:
			return 150;
		case KET_ZEK:
			return 300;
		case TZTOK_JAD:
			return 500;
		case YT_HURKOT:
			return 125;
		}
		return 100;
	}

	private static void killedTzhaar(int i) {
		final Client c2 = (Client) PlayerHandler.players[NpcHandler.npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			c2.waveId++;
			   CycleEventHandler.getSingleton().addEvent(c2, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c2 != null) {
						Server.fightCaves.spawnNextWave(c2);
					}
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, 15);

		}
	}

	public static void handleJadDeath(int i) {
		Client c = (Client) PlayerHandler.players[NpcHandler.npcs[i].spawnedBy];
		if (c.inFightCaves()) {
			c.getItemAssistant().addItem(6570, 1);
			c.getItemAssistant().addItem(6529, 8032);
			c.getDialogueHandler().sendDialogues(103, 2617);
			c.getActionSender().sendMessage("You were victorious!");
			c.getPlayerAssistant().resetTzhaar();
			c.killedJad = true;
			c.waveId = 300;
			c.setSpecialTarget(null);
		} else {
			c.getActionSender().sendMessage("You must be in the fight caves to do this.");
		}
	}
	
	public static void healJad(Client player, int i) {
		if (NpcHandler.npcs[i].npcType == YT_HURKOT && !NpcHandler.npcs[i].isDead) {
			if (player.getSpecialTarget() != null) {
				if (player.getSpecialTarget().npcType == TZTOK_JAD) {
					Npc jad = player.getSpecialTarget();
					NpcHandler.npcs[i].gfx0(444);
					NpcHandler.npcs[i].startAnimation(2639, i);
					jad.HP += 1+Misc.random(8);
					if (jad.HP >= getHp(TZTOK_JAD)) {
						jad.HP = getHp(TZTOK_JAD);
						if (player.spawnedHealers < 4)
							spawnHealers(player, i, 4-player.spawnedHealers);
					}
				}
			}
		}
	}

	/**
	 * Checks if a tzhaar npc has been killed, if so then it checks if it needs
	 * to do the tz-kek effect. If tzKek spawn has been killed twice or didn't
	 * need to be killed it calls killedTzhaar.
	 * 
	 * @param i
	 *            The npc.
	 */
	public static void tzhaarDeathHandler(int i) {
		if (isFightCaveNpc(i) && NpcHandler.npcs[i].npcType != FightCaves.TZ_KEK && NpcHandler.npcs[i].npcType != FightCaves.YT_HURKOT) {
			killedTzhaar(i);
		}
		if (NpcHandler.npcs[i].npcType == FightCaves.TZ_KEK_SPAWN) {
			int p = NpcHandler.npcs[i].killerId;
			if (PlayerHandler.players[p] != null) {
				Client c = (Client) PlayerHandler.players[p];
				c.tzKekSpawn += 1;
				if (c.tzKekSpawn == 2) {
					killedTzhaar(i);
					c.tzKekSpawn = 0;
				}
			}
		}
		if (NpcHandler.npcs[i].npcType == FightCaves.TZ_KEK) {
			int p = NpcHandler.npcs[i].killerId;
			if (PlayerHandler.players[p] != null) {
				Client c = (Client) PlayerHandler.players[p];
				FightCaves.tzKekEffect(c, i);
			}
		}
	}

	/**
	 * Checks if something is a fight cave npc.
	 * 
	 * @param i
	 *            The npc.
	 * @return Whether or not it is a fight caves npc.
	 */
	public static boolean isFightCaveNpc(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case FightCaves.TZ_KIH:
		case FightCaves.TZ_KEK:
		case FightCaves.TZ_KEK_SPAWN:
		case FightCaves.TOK_XIL:
		case FightCaves.YT_MEJKOT:
		case FightCaves.KET_ZEK:
		case FightCaves.TZTOK_JAD:
		case FightCaves.YT_HURKOT:
			return true;
		}
		return false;
	}

}
