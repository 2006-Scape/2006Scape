package com.rs2.game.content.combat.npcs;

import com.rs2.GameEngine;
import com.rs2.game.content.combat.AttackType;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;

/**
 * Npc Emotes
 * @author Andrew (Mr Extremez)
 */

public enum NpcEmotes {
		MAN(new int[] {1, 2, 3, 4, 5, 6}, 422, 1834, 836),
		AL_KHARID_WARRIOR(new int[] {18}, 451, 1156, 836),
		GUARD(new int[] {9}, 412, 1156, 836),
		GUARD_NO_SHIELD(new int[] {10}, 412, 404, 836),
		GARGOYLE(new int[] {1610, 1611}, 1517, 1519, 1518),
		SKELETAL_WYVERN(new int[] {3068}, 2989, 2988, 2987),
		BAT(new int[] {412, 78}, 30, 31, 36),
		BEAR(new int[] {105, 106}, 41, 42, 44),
		HOB_GOBLIN(new int[] {122, 123}, 164, 165, 167),
		AHRIM(new int[] {2025}, 729, 404, 2304),
		DHAROK(new int[] {2026}, 2067, 404, 2304),
		GUTHAN(new int[] {2027}, 2080, 404, 2304),
		KARIL(new int[] {2028}, 2075, 404, 2304),
		TORAG(new int[] {2029}, 2068, 404, 2304),
		VERAC(new int[] {2030}, 2062, 404, 2304),
		BABY_DRAGON(new int[] {51, 52, 1589, 3376}, 25, 26, 28),
		CHICKEN(new int[] {41}, 55, 56, 57),
		KBD_METAL_DRAGON(new int [] {50, 1590, 1591, 1592}, 80, 89, 92),
		DRAGON(new int[] {53, 54, 55, 941}, 91, 89, 92),
		BASILISK(new int[] {1616, 1617, 4228}, 1546, 1547, 1548),
		BLOOD_WORM(new int[] {2031}, 2070, 2072, 2073),
		TREE_SPIRIT(new int[] {438, 439, 440, 441, 442, 443}, 94, 95, 97),
		ZOMBIE(new int[] {73, 74, 75, 76, 751}, 299, 300, 302),
		ROCK_GOLEM(new int[] {413, 414, 415, 416, 417, 418}, 153, 154, 156),
		RIVER_TROLL(new int[] {391, 392, 393, 394, 395, 396}, 284, 285, 287),
		GOBLIN(new int[] {100, 101, 102, 1769, 1770, 1771, 1772, 1773, 1774, 1775, 1776}, 309, 312, 313),
		COW(new int[] {81, 397, 1766, 1767, 1768}, 59, 60, 62),
		BLOODVELD(new int[] {1618, 1619}, 1552, 1550, 1553),
		IMP(new int[] {708}, 169, 170, 172),
		DARK_WIZARD(new int[] {172, 13, 174}, 711, 1834, 836),
		DUCK(new int[] {44, 45}, 7, 8, 9),
		SPINOLYP(new int[] {2892, 2894}, 2868, 2864, 2865),
		DWARF(new int[] {118, 119}, 99, 100, 102),
		DEFILER(new int[] {3762, 3763, 3764, 3765, 3766, 3767, 3768, 3769, 3770, 3771}, 3920, 3921, 3922),
		SPINNER(new int[] {3747, 3748, 3749, 3750, 3751}, 3908, 3909, 3910),
		SHIFTER(new int[] {3732, 3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741}, 3901, 3902, 3903),
		RAVAGER(new int[] {3742, 3743, 3744, 3745, 3746}, 3915, 3916, 3917),
		BRAWLER(new int[] {3772, 3773, 3774, 3775, 3776}, 3897, 3895, 3894),
		SPLATTER(new int[] {3727, 3728, 3729, 3730, 3731}, 3891, 3890, 3888),
		TORCHER(new int[] {3752, 3753, 3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761}, 3882, 3880, 3881),
		KALPHITE_WORKER(new int[] {1153, 1154, 1155, 1156, 1157, 1158, 1161}, 1184, 1186, 1187),
		KALPHITE_QUEEN(new int[] {1159}, 1185, 1186, 1187),
		KALPHITE_QUEEN_2(new int[] {1160}, 1177, 1179, 1182),
		DEMON(new int[] {82, 83, 84, 1472}, 64, 65, 67),
		DUST_DEVIL(new int[] {1624}, 1557, 1555, 1558),
		CHAOS_ELEMENTAL(new int[] {3200}, 3146, 3148, 3147),
		GIANT(new int[] {110, 111, 112, 113, 116, 117}, 128, 129, 131),
		DAGGANOTH_PRIME(new int[] {2881}, 2855, 2852, 2856),
		DAGGANOTH_SUPREME(new int[] {2882}, 2854, 2852, 2856),
		DAGGANOTH_REX(new int[] {2883}, 2851, 2852, 2856),
		WHITE_KNIGHT(new int[] {1092, 19}, 406, -1, 843),
		KNIGHT_WARRIOR(new int[] {125, 178, 179}, 451, -1, 843),
		PORTAL(new int[] {3777, 3778, 3779, 3780}, -1, -1, -1),
		DARK_BEAST(new int[] {2783}, 2731, 2732, 2733),
		TZHAAR_NPCS(new int[] {2604, 2598, 2591}, 2609, 2606, 2607),
		TZHAAR_MEJ(new int[] {2591}, 2612, 2606, 2607),
		TZHAAR_KET(new int[] {2610, 2615}, 2612, 2606, 2608),
		TZHAAR_XIL(new int[] {2607}, 2611, 2610, 2607),
		TZ_KIH(new int[] {2627}, 2621, 2622, 2620),
		TZ_KEK(new int[] {2629, 2630, 2736, 2738}, 2625, 2626, 2627),
		TOK_XIL(new int[] {2631, 2632}, 2628, 2629, 2630),
		TZHAAR_YT(new int[] {2741, 2742, 2746}, 2637, 2635, 2638),
		KET_ZEK(new int[] {2743, 2744}, 2644, 2645, 2646),
		COCKATRICE(new int[] {1620, 1621}, 1562, 1560, 1563),
		GNOME_CHILD(new int[] {160, 161}, 191, 194, 196),
		GNOME_GUARD(new int[] {163, 164}, 192, 193, 196),
		GNOME_WOMAN(new int[] {168, 169}, 190, 193, 196),
		TUROTH(new int[] {1626, 1627, 1628, 1629, 1630, 1631, 1632}, 1595, 1596, 1597),
		GHOST(new int[] {103, 104, 491}, 123, 124, 126),
		ROCK_CRAB(new int[] {1265, 1267}, 1312, 1313, 1314),
		DOG_WOLF(new int[] {95, 96, 97, 99, 1593, 1594, 141, 142, 143}, 75, 76, 78),
		SPIDER(new int[] {58, 59, 60, 62, 63, 64, 134, 1009, 2035}, 143, 144, 146),
		UNICORN(new int[] {89, 133, 987}, 289, 290, 292),
		OGRE(new int[] {114, 115, 374}, 359, 360, 361),
		FIEND(new int[] {1633, 1634, 1635, 1636, 3406}, 1582, 1581, 1580),
		BANSHEE(new int[] {1612}, 1523, 1525, 1524),
		EXPERIMENT_25(new int[] {1677}, 1616, 1617, 1618),
		EXPERIMENT_25_2(new int[] {1678}, 1612, 1613, 1614),
		EXPERIMENT_51(new int[] {1676}, 1626, 1627, 1628),
		ABYSSAL_DEMON(new int[] {1615}, 1537, 1539, 1538),
		NECHRYAEL(new int[] {1613}, 1528, 1529, 1530),
		SCORPION(new int[] {144, 107, 108}, 246, 247, 248),
		SMALL_SPIDER(new int[] {61}, 280, 279, 273),
		PIT_SCORPION(new int[] {109}, 270, 271, 273),
		CRAWLING_HAND(new int[] {1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657}, 1592, 1591, 1590),
		ABBERANT_SPECTRE(new int[] {1604, 1605, 1606, 1607}, 1507, 1509, 1508),
		INFERNAL_MAGE(new int[] {1643, 1644, 1645, 1646, 1647}, 429, 430, 2304),
		MONKEY_GUARD(new int[] {1455, 1459, 1460}, 1402, 1403, 1404),
		RAT(new int[] {86, 87, 88, 224, 446, 748, 950, 978, 2033}, 138, 139, 141),
		SMALL_RAT(new int[] {47, 2032}, 2705, 2706, 2707),
		DAGGANOTH(new int[] {1338, 1340, 1341, 1342, 2455, 2456}, 1341, 1340, 1342),
		SKELETON(new int[] {90, 91, 92, 93}, 260, 261, 263);
		
		int[] npcId;
		int attackAnim, blockAnim, deadAnim;
	
		private NpcEmotes(int[] npcId, int attackAnim, int blockAnim, int deadAnim) {
			this.npcId = npcId;
			this.attackAnim = attackAnim;
			this.blockAnim = blockAnim;
			this.deadAnim = deadAnim;
		}
		
		private int[] getNpcId() {
			return npcId;
		}
		
		private int getAttack() {
			return attackAnim;
		}
		
		private int getBlock() {
			return blockAnim;	
		}
		
		private int getDead() {
			return deadAnim;
		}

	public static int getAttackEmote(int i) {
		for (NpcEmotes e : NpcEmotes.values()) {
			for (int f = 0; f < e.getNpcId().length; f++) {
				if (NpcHandler.npcs[i].npcType == e.getNpcId()[f]) {
					return e.getAttack();
				} else {
					switch (NpcHandler.npcs[i].npcType) {
					case 2745:
						if (NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
							return 2656;
						} else if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) {
							return 2652;
						} else if (NpcHandler.npcs[i].attackType == AttackType.MELEE.getValue()) {
							return 2655;
						}
						
					}
				}
			}
		}
		return 0x326;
	}

	public static int getBlockEmote(int i) {
		for (NpcEmotes e : NpcEmotes.values()) {
			for (int f = 0; f < e.getNpcId().length; f++) {
				if (NpcHandler.npcs[i].npcType == e.getNpcId()[f]) {
					return e.getBlock();
				} else {
					switch (NpcHandler.npcs[i].npcType) {
					case 2745:
						return 2653;
					}
				}
			}
		}
		return -1;
	}

	public static int getDeadEmote(Player player, int i) {
		for (NpcEmotes e : NpcEmotes.values()) {
			for (int f = 0; f < e.getNpcId().length; f++) {
				if (NpcHandler.npcs[i].npcType == e.getNpcId()[f]) {
					return e.getDead();
				} else {
					switch (NpcHandler.npcs[i].npcType) {
					case 2745:
						return 2654;
					case 1158:
						GameEngine.npcHandler.spawnSecondForm(player, i);
						return 1187;
					case 1160:
						GameEngine.npcHandler.spawnFirstForm(player, i);
						return 1182;
					}
				}
			}
		}
		return 2304;
	}
}
