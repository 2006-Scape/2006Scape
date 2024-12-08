package com.rs2.game.npcs;

import com.rs2.game.content.combat.AttackType;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;

import java.util.ArrayList;

import static com.rs2.game.content.StaticNpcList.*;

public class NpcData {

	public static final int[] npcsOnlyMage = { KOLODION_907, KOLODION_908, KOLODION_909, KOLODION_910, KOLODION_911, BATTLE_MAGE,
			BATTLE_MAGE_913, BATTLE_MAGE_914 };// done
	public static final int[][] transformNpc = { { MAN_3223, 6006 },
			{ MAN_3224, 6007 }, { MAN_3225, 6008 }, { WOMAN_3226, 6009 } };// done // this transformNpc array seems to be unused, maybe can be removed? Maybe it's not even accurate? Idk I just fixed the magic numbers.
	public static final int[] npcsCantKillYou = { CHICKEN, CHICKEN_951, CHICKEN_1017, CHICKEN_1401, CHICKEN_1402,
			UNDEAD_CHICKEN, CHICKEN_2313, CHICKEN_2314, CHICKEN_2315 };// done
	public static final int[] npcCantAttack = { BARRICADE, BARRICADE_1533, BARRICADE_1534, BARRICADE_1535 };
	public static final int[] npcDontGiveXp = { PHEASANT, PHEASANT_2460, PHEASANT_2461, PHEASANT_2462 };

	public static boolean cantKillYou(int npcType) {
		for (int n : npcsCantKillYou) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}

	public static boolean onlyMage(int npcType) {
		for (int element : npcsOnlyMage) {
			if (npcType == element) {
				return true;
			}
		}
		return false;
	}

	public static boolean cantAttack(int npcType) {
		for (int n : npcCantAttack) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}

	public static boolean dontGiveXp(int npcType) {
		for (int n : npcDontGiveXp) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}

	public static int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int p = 1; p < PlayerHandler.players.length; p++) {
			if (PlayerHandler.players[p] != null) {
				if (PlayerHandler.players[p].lastNpcAttacked == npcId) {
					if (PlayerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = PlayerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					PlayerHandler.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}

	public static int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (NpcHandler.goodDistance(
						PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY,
						NpcHandler.npcs[i].absX,
						NpcHandler.npcs[i].absY,
						2 + NpcHandler.distanceRequired(i)
								+ NpcHandler.followDistance(i))
						|| FightCaves.isFightCaveNpc(i)) {
					if (PlayerHandler.players[j].underAttackBy <= 0
							&& PlayerHandler.players[j].underAttackBy2 <= 0
							|| Boundary.isIn(PlayerHandler.players[j], Boundary.MULTI)) {
						if (PlayerHandler.players[j].heightLevel == NpcHandler.npcs[i].heightLevel) {
							players.add(j);
						}
					}
				}
			}
		}
		if (players.size() > 0) {
			return players.get(Misc.random(players.size() - 1));
		} else {
			return 0;
		}
	}

	public static void startAnimation(int animId, int i) {
		NpcHandler.npcs[i].animNumber = animId;
		NpcHandler.npcs[i].animUpdateRequired = true;
		NpcHandler.npcs[i].updateRequired = true;
	}

	public static void handleClipping(int i) {
		Npc npc = NpcHandler.npcs[i];
		if (npc.moveX == 1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0) {
					npc.moveY = 1;
				} else {
					npc.moveX = 1;
				}
			}
		} else if (npc.moveX == -1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0) {
					npc.moveY = -1;
				} else {
					npc.moveX = -1;
				}
			}
		} else if (npc.moveX == 1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0) {
					npc.moveY = -1;
				} else {
					npc.moveX = 1;
				}
			}
		} else if (npc.moveX == -1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0) {
					npc.moveY = 1;
				} else {
					npc.moveX = -1;
				}
			}
		} // Checking Diagonal movement.

		if (npc.moveY == -1) {
			if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0) {
				npc.moveY = 0;
			}
		} else if (npc.moveY == 1) {
			if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0) {
				npc.moveY = 0;
			}
		} // Checking Y movement.
		if (npc.moveX == 1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0) {
				npc.moveX = 0;
			}
		} else if (npc.moveX == -1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0) {
				npc.moveX = 0;
			}
		} // Checking X movement.
	}
	

	/**
	 * Attack delays
	 **/
	public static int getNpcDelay(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case AHRIM_THE_BLIGHTED:
		case KARIL_THE_TAINTED:
			return 7;

		case TZTOKJAD:
			return 8;

		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 **/
	public static int getHitDelay(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case DAGANNOTH_SUPREME:
		case DAGANNOTH_PRIME:
		case CHAOS_ELEMENTAL:
		case SPINOLYP:
		case SPINOLYP_2894:
			return 3;

		case KETZEK:
		case TOKXIL:
			return 3;

		case TZTOKJAD:
			if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()
					|| NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
				return 5;
			} else {
				return 2;
			}

		case AHRIM_THE_BLIGHTED:
			return 4;
		case KARIL_THE_TAINTED:
			return 3;

		default:
			return 2;
		}
	}

	/**
	 * Npc respawn time
	 **/
	public static int getRespawnTime(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case KALPHITE_QUEEN:
		case KALPHITE_QUEEN_1160:
			return -1;
		case DAGANNOTH_SUPREME:
		case DAGANNOTH_PRIME:
		case DAGANNOTH_REX:
			return 100;
		case PORTAL:
		case PORTAL_3778:
		case PORTAL_3779:
		case PORTAL_3780:
			return 500;
		case BARRICADE:
		case BARRICADE_1534:
			return -1;
		default:
			return 25;
		}
	}

	/**
	 * Distance required to attack
	 * It's also worth checking {@link NpcHandler#distanceRequired}
	 */
	public static int distanceRequired(int i) {
		if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) {
			return 8;
		} else if (NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
			return 10;
		} else if (NpcHandler.npcs[i].attackType > AttackType.MAGIC.getValue()) {
			return 5;
		}
		switch (NpcHandler.npcs[i].npcType) {
			case COAL:
				return 2;
			case DAGANNOTH_SUPREME:// dag kings
			case DAGANNOTH_PRIME:
			case CHAOS_ELEMENTAL:// chaos ele
				return 8;
			// things around dags
			case SPINOLYP:
			case SPINOLYP_2894:
				return 10;
			case KOLODION_907 : // Kolodian
			case KOLODION_908 :
			case KOLODION_909 :
			case KOLODION_910 :
			case KOLODION_911 :
			case BATTLE_MAGE : // Zammy battlemage
			case BATTLE_MAGE_913 : // Sara battlemage
			case BATTLE_MAGE_914 : // Guthix battlemage
			case TZHAARMEJ : // TzHaar-Mej (Tzhaar mage guy)
			case KETZEK : // Ket-Zek (Tzhaar mage guy)
			case TZTOKJAD : // TzTok-Jad
			case KALPHITE_QUEEN : // Kalphite queen form 1
			case KALPHITE_QUEEN_1160 : // Kalphite queen form 2
			case AHRIM_THE_BLIGHTED : // Ahrim
				return 10;
			case KARIL_THE_TAINTED : // Karil
			case TOKXIL : // Tok-Xil (Tzhaar ranging guy)
			case ELF_WARRIOR : // Elf ranger
			case DARK_WIZARD: // dark wizards
			case DARK_WIZARD_174:
				return 8;
			case GREEN_DRAGON : // Green drag
			case KING_BLACK_DRAGON : // Kbd
				return 6;
		}
		return 1;
	}


	public static boolean goodDistanceNpc(int i, int x2, int y2, int distance) {
		for (int x = NpcHandler.npcs[i].getX(); x <= NpcHandler.npcs[i].getX() + NpcHandler.npcs[i].size; x++) {
			for (int y = NpcHandler.npcs[i].getY(); y <= NpcHandler.npcs[i].getY() + NpcHandler.npcs[i].size; y++) {
				if (Misc.goodDistance(x, y, x2, y2, distance)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean checkClip(Npc n) {
		int x2 = 0, y2 = 0, x3 = 0, y3 = 0;
		if (n.killerId > 0) {
			if (PlayerHandler.players[n.killerId] == null) {
				return false;
			}
			x2 = PlayerHandler.players[n.killerId].getX();
			y2 = PlayerHandler.players[n.killerId].getY();
		} else if (n.masterId > 0) {
			if (PlayerHandler.players[n.masterId] == null) {
				return false;
			}
			x2 = PlayerHandler.players[n.masterId].getX();
			y2 = PlayerHandler.players[n.masterId].getY();
		} else {
			return false;
		}
		int x = n.getX(); // -1
		int y = n.getY(); // 1
		final int dis = distanceRequired(n.npcId) + n.size;
		int dis2 = 0;
		final boolean melee = distanceRequired(n.npcId) < 2;
		if (n.size < 1 && x != x2 && y != y2) {
			return false;
		}
		// Algorithm starts here
		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0) {
			dx1 = -1;
		} else if (w > 0) {
			dx1 = 1;
		}
		if (h < 0) {
			dy1 = -1;
		} else if (h > 0) {
			dy1 = 1;
		}
		if (w < 0) {
			dx2 = -1;
		} else if (w > 0) {
			dx2 = 1;
		}
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0) {
				dy2 = -1;
			} else if (h > 0) {
				dy2 = 1;
			}
			dx2 = 0;
		}
		int numerator = longest >> 1;
		boolean firstCheck = false;
		for (int i = 0; i <= longest; i++) {
			if (dis2 > dis) {
				return false;
			}
			dis2++;
			x3 = x;
			y3 = y;
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
			if (!firstCheck) {
				if (melee) {
					if (!Region.getClipping(x, y, n.heightLevel, x - x3, y - y3)) {
						return false;
					}
				}
				if (x == x2 && y == y2) {
					break;
				}
				firstCheck = true;
			}
			if (melee) {
				if (!Region.getClipping(x, y, n.heightLevel, x - x3, y - y3)) {
					return false;
				}
			}
			if (x == x2 && y == y2) {
				return true;
			}
		}
		return true;
	}

	public static boolean inNpc(int i, int x2, int y2) {
		if (NpcHandler.npcs[i].size < 1) {
			if (x2 == NpcHandler.npcs[i].getX() && y2 == NpcHandler.npcs[i].getY()) {
				return true;
			}
		} else {
			for (int x = NpcHandler.npcs[i].getX(); x <= NpcHandler.npcs[i].getX() + NpcHandler.npcs[i].size; x++) {
				for (int y = NpcHandler.npcs[i].getY(); y <= NpcHandler.npcs[i].getY() + NpcHandler.npcs[i].size; y++) {
					if (x2 == x && y2 == y) {
						return true;
					}
				}
			}
		}
		return false;
	}


}
