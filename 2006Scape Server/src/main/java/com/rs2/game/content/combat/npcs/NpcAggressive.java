package com.rs2.game.content.combat.npcs;

import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.npcs.NpcHandler;

import static com.rs2.game.content.StaticNpcList.*;

public class NpcAggressive {

	/**
	 * Aggressive monsters
	 */
	private static final int[] AGGRESSIVE_MONSTERS = { KALPHITE_GUARDIAN, KALPHITE_GUARDIAN_1157, KALPHITE_QUEEN,
			KALPHITE_QUEEN_1159, KALPHITE_QUEEN_1160, BIG_WOLF, MONKEY_GUARD_1459, MONKEY_ARCHER, WHITE_WOLF,
			WHITE_WOLF_97, WOLF_142, DWARVEN_MINER_2550, DWARVEN_MINER_2551, DWARVEN_MINER_2552, BLAST_FURNACE_FOREMAN,
			ADAMANTITE_ORE, RUNITE_ORE, SILVER_ORE, GOLD_ORE, COAL, PERFECT_GOLD_ORE, ORDAN, JORZIK, SPINOLYP,
			SPINOLYP_2894, DAGANNOTH_SUPREME, DAGANNOTH_PRIME, DAGANNOTH_REX, WILD_DOG, KING_SCORPION, MOSS_GIANT,
			BLACK_DEMON, SKELETAL_WYVERN, KING_BLACK_DRAGON, BRONZE_DRAGON, IRON_DRAGON, STEEL_DRAGON, RED_DRAGON,
			BLACK_DRAGON, BLUE_DRAGON, BLACK_KNIGHT, HELLHOUND, EGG_2450, EGG_2451, GIANT_ROCK_CRAB, BOULDER_2453,
			DAGANNOTH_SPAWN, DAGANNOTH_2455, DAGANNOTH_2456, LESSER_DEMON, LESSER_DEMON_752, KURASK, KURASK_1609,
			GARGOYLE, WALL_BEAST, DARK_BEAST, BANDIT_1926, BANDIT_1931, WALLASALKI, BAT, ABERRANT_SPECTER, BANSHEE,
			GARGOYLE_1611, GREATER_DEMON, GREEN_DRAGON, HELLHOUND, ICE_GIANT, ICE_WARRIOR, KALPHITE_SOLDIER,
			DAGANNOTH_1342, JAIL_GUARD, JAIL_GUARD_917, ROCK_CRAB_1267 };

	public static boolean isAggressive(int i) {
		try {
			boolean aggressive = NpcHandler.npcs[i].inWild() || PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || FightCaves.isFightCaveNpc(i);
			for (int element : AGGRESSIVE_MONSTERS) {
				if (NpcHandler.npcs[i].npcType == element || aggressive) {
					return true;
				}
			}
			return false;
		} catch (NullPointerException TODO_better_fix) {
			return false;
		}
	}
}
