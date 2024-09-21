package com.rs2.game.npcs;

import com.rs2.game.content.combat.AttackType;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.clip.Region;
import org.apollo.cache.def.NpcDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NpcData {

	public static final int[] npcsOnlyMage = { 907, 908, 909, 910, 911, 912,
			913, 914 };// done
	public static final int[][] transformNpc = { { 3223, 6006 },
			{ 3224, 6007 }, { 3225, 6008 }, { 3226, 6009 } };// done
	public static final int[] npcsCantKillYou = { 41, 951, 1017, 1401, 1402,
			1692, 2313, 2314, 2315 };// done
	public static final int[] npcCantAttack = { 1532, 1533, 1534, 1535 };
	public static final int[] npcDontGiveXp = { 2459, 2460, 2461, 2462 };

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
		case 2025:
		case 2028:
			return 7;

		case 2745:
			return 8;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2550:
			return 6;
			// saradomin gw boss
		case 2562:
			return 2;

		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 **/
	public static int getHitDelay(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
		case 2892:
		case 2894:
			return 3;

		case 2743:
		case 2631:
		case 2558:
		case 2559:
		case 2560:
			return 3;

		case 2745:
			if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()
					|| NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
				return 5;
			} else {
				return 2;
			}

		case 2025:
			return 4;
		case 2028:
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
		case 1158:
		case 1160:
		return -1;
		case 2881:
		case 2882:
		case 2883:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2550:
		case 2551:
		case 2552:
		case 2553:
			return 100;
		case 3777:
		case 3778:
		case 3779:
		case 3780:
			return 500;
		case 1532:
		case 1534:
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
		//if (NpcHandler.npcs[i].npcType == 81)
		//	System.out.println("npcHandler distanceRequired npc size " + NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize());
		if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) {
			return 8;
		} else if (NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
			return 10;
		} else if (NpcHandler.npcs[i].attackType > AttackType.MAGIC.getValue()) {
			return 5;
		}
		switch (NpcHandler.npcs[i].npcType) {
			case 2562:
				return 2;
			case 2881:// dag kings
			case 2882:
			case 3200:// chaos ele
				return 8;
			case 2552:
			case 2553:
			case 2556:
			case 2557:
			case 2558:
			case 2559:
			case 2560:
			case 2564:
			case 2565:
				return 9;
			// things around dags
			case 2892:
			case 2894:
				return 10;
			case 907 : // Kolodian
			case 908 :
			case 909 :
			case 910 :
			case 911 :
			case 912 : // Zammy battlemage
			case 913 : // Sara battlemage
			case 914 : // Guthix battlemage
			case 2591 : // TzHaar-Mej (Tzhaar mage guy)
			case 2743 : // Ket-Zek (Tzhaar mage guy)
			case 2745 : // TzTok-Jad
			case 1158 : // Kalphite queen form 1
			case 1160 : // Kalphite queen form 2
			case 2025 : // Ahrim
				return 10;
			case 2028 : // Karil
			case 2631 : // Tok-Xil (Tzhaar ranging guy)
			case 1183 : // Elf ranger
			case 172: // dark wizards
			case 174:
				return 8;
			case 941 : // Green drag
			case 50 : // Kbd
				return 6;
		}
		return NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize();
	}


	public static boolean goodDistanceNpc(int i, int x2, int y2, int distance) {
		for (int x = NpcHandler.npcs[i].getX(); x <= NpcHandler.npcs[i].getX() + NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize(); x++) {
			for (int y = NpcHandler.npcs[i].getY(); y <= NpcHandler.npcs[i].getY() + NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize(); y++) {
				int dir = Misc.direction(x, y, x2, y2);
				Set<Integer> nearbyDirections = new HashSet<>(Arrays.asList(6, 8, 9, 10, 12, 13));
				boolean nearBy = nearbyDirections.contains(dir);
				if (Misc.goodDistance(x, y, x2, y2, nearBy && NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize() > 1 ? distance - 1 : distance)) {
					//System.out.println("distance is good! x " + x + " y " + y + " " + " x2 " + x2 + " y2 " + y2 + " vs distance " + (nearBy ? distance - 1 : distance) + " with direction " + dir);
					return true;
				} else {
					//System.out.println("distance is not good! x " + x + " y " + y + " " + " x2 " + x2 + " y2 " + y2 + " vs distance " + distance + " with direction " + dir);
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
		final int dis = distanceRequired(n.npcId);
		int dis2 = 0;
		final boolean melee = distanceRequired(n.npcId) == NPCDefinition.forId(n.npcType).getSize();
		if (NPCDefinition.forId(n.npcType).getSize() < 1 && x != x2 && y != y2) {
			//System.err.println("checkClip exit early! return false");
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
				//System.err.println("checkClip early exit, dis2 > dis " + dis2 + " > " + dis );
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
					if (!Region.getClipping(x, y, n.heightLevel, x3 - x, y3 - y) && !Region.getClipping(x, y, n.heightLevel, x - x3, y - y3)) {
						//System.err.println("checkClip Region.getClipping exit early 1");
						return false;
					}
				}
				if (x == x2 && y == y2) {
					break;
				}
				firstCheck = true;
			}
			if (melee) {
				if (!Region.getClipping(x, y, n.heightLevel, x3 - x, y3 - y) && !Region.getClipping(x, y, n.heightLevel, x - x3, y - y3)) {
					//System.err.println("checkClip Region.getClipping exit early 2");
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
				//System.out.println("in npc 1");
				return true;
			}
		} else {
			for (int x = NpcHandler.npcs[i].getX(); x <= NpcHandler.npcs[i].getX() + NpcHandler.npcs[i].size; x++) {
				for (int y = NpcHandler.npcs[i].getY(); y <= NpcHandler.npcs[i].getY() + NpcHandler.npcs[i].size; y++) {
					if (x2 == x && y2 == y) {
						//System.out.println("in npc 2");
						return true;
					}
				}
			}
		}
		//System.out.println("not in npc");
		return false;
	}


}
