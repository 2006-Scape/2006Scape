package com.rs2.game.content.combat.npcs;

import com.rs2.Constants;
import com.rs2.game.content.combat.AttackType;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.melee.MeleeData;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.music.sound.CombatSounds;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.items.impl.Greegree.MonkeyData;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.NpcData;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;

public class NpcCombat {

	public static void multiAttackDamage(int i) {
		int max = NpcHandler.getMaxHit(i);
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				if (c.isDead || c.heightLevel != NpcHandler.npcs[i].heightLevel) {
					continue;
				}
				if (player.goodDistance(c.absX, c.absY,
						NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY, 15)) {
					if (NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) {
						if (!c.getPrayer().prayerActive[16]) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombatAssistant().mageDef())) {
								int dam = Misc.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					} else if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) {
						if (!c.getPrayer().prayerActive[17]) {
							int dam = Misc.random(max);
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombatAssistant()
									.calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					}
					if (NpcHandler.npcs[i].endGfx > 0) {
						c.gfx0(NpcHandler.npcs[i].endGfx);
					}
				}
				c.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			}
		}
	}

	public static void multiAttackGfx(int i, int gfx) {
		if (NpcHandler.npcs[i].projectileId < 0) {
			return;
		}
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client c = (Client) player;
				if (c.heightLevel != NpcHandler.npcs[i].heightLevel) {
					continue;
				}
				if (player.goodDistance(c.absX, c.absY,
						NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY, 15)) {
					int nX = NpcHandler.npcs[i].getX() + NpcHandler.offset(i);
					int nY = NpcHandler.npcs[i].getY() + NpcHandler.offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY) * -1;
					int offY = (nX - pX) * -1;
					c.getPlayerAssistant().createPlayersProjectile(nX, nY,
							offX, offY, 50, NpcHandler.getProjectileSpeed(i),
							NpcHandler.npcs[i].projectileId, 43, 31,
							-c.getId() - 1, 65);
				}
			}
		}
	}

	public static void attackPlayer(Player c, int i) {
		if (NpcHandler.npcs[i] != null) {
			if (NpcHandler.npcs[i].absY == 3228 && c.absY == 3227
				|| NpcHandler.npcs[i].absY == 3224 && c.absY == 3225
				|| NpcHandler.npcs[i].absY == 3226 && c.absY == 3227
				|| Boundary.isIn(c, Boundary.DRAYNOR_BUILDING) && (NpcHandler.npcs[i].npcType == 172 || NpcHandler.npcs[i].npcType == 174)
				|| NpcHandler.npcs[i].inLesserNpc()
				|| !c.npcCanAttack
				|| NpcHandler.npcs[i].isDead) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 1532
					|| NpcHandler.npcs[i].npcType == 1534
					|| NpcHandler.npcs[i].npcType == 6145
					|| NpcHandler.npcs[i].npcType == 6144
					|| NpcHandler.npcs[i].npcType == 6143
					|| NpcHandler.npcs[i].npcType == 6142
					|| NpcHandler.npcs[i].npcType == 752) {
				return;
			}
			if (Boundary.isIn(c, Boundary.APE_ATOLL) && MonkeyData.isWearingGreegree(c)) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 1401 && Boundary.isIn(c, Boundary.TUTORIAL) || c.tutorialProgress < 36) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 9 && c.absX == 3180 && c.absY > 3433 && c.absY < 3447) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 374 && c.absY == 3372 && c.absX > 2522 && c.absX < 2532) {
				return;
			}
			if (NpcHandler.npcs[i].npcType > 2462 && NpcHandler.npcs[i].npcType < 2468) {
				if (Misc.random(5) == 0) {
					NpcHandler.npcs[i].forceChat("Flee from me, " + c.playerName + "!");
				} else if (Misc.random(5) == 1) {
					NpcHandler.npcs[i].forceChat("Begone, " + c.playerName + "!");
				} else if (Misc.random(5) == 2) {
					NpcHandler.npcs[i].forceChat("Bwuk");
				} else if (Misc.random(5) == 3) {
					NpcHandler.npcs[i].forceChat("Bwuk bwuk bwuk");
				} else if (Misc.random(5) == 4) {
					NpcHandler.npcs[i].forceChat("MUAHAHAHAHAAA!");
				} else if (Misc.random(5) == 5) {
					NpcHandler.npcs[i].forceChat("Bwaaaaaaauk bwuk bwuk");
				}
			}
			if (!NpcHandler.npcs[i].inMulti() && NpcHandler.npcs[i].underAttackBy > 0 && NpcHandler.npcs[i].underAttackBy != c.playerId) {
				NpcHandler.npcs[i].killerId = 0;
				return;
			}
			if (!NpcHandler.npcs[i].inMulti() && (c.underAttackBy > 0 || c.underAttackBy2 > 0 && c.underAttackBy2 != i)) {
				NpcHandler.npcs[i].killerId = 0;
				return;
			}
			if (NpcHandler.npcs[i].heightLevel != c.heightLevel) {
				NpcHandler.npcs[i].killerId = 0;
				return;
			}
			//System.out.println("distance is: " + Misc.distance(c.getX(), c.getY(), NpcHandler.npcs[i].getX(),  NpcHandler.npcs[i].getY()));
			if (!NpcData.goodDistanceNpc(NpcHandler.npcs[i].npcId, c.getX(), c.getY(), NpcData.distanceRequired(NpcHandler.npcs[i].npcId)) || NpcData.inNpc(NpcHandler.npcs[i].npcId, c.getX(), c.getY())) {
				return;
			}
			//System.out.println("distance seems good!");
			NpcHandler.npcs[i].facePlayer(c);
			boolean special = false;//specialCase(c,i);
			if (NpcData.checkClip(NpcHandler.npcs[i]) || special) {
				//System.out.println("clip seems good!");
				if (c.respawnTimer <= 0) {
					NpcHandler.npcs[i].facePlayer(c);
					NpcHandler.npcs[i].attackTimer = NpcData.getNpcDelay(i);
					NpcHandler.npcs[i].hitDelayTimer = NpcData.getHitDelay(i);
					NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
					if (CombatConstants.COMBAT_SOUNDS) {
						if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
							return;
						}
						c.getPacketSender().sendSound(CombatSounds.getNpcAttackSounds(NpcHandler.npcs[i].npcType), 100, 0);
					}
					if (special) {
						loadSpell2(i);
					} else {
						loadSpell(c, i);
					}
					if (NpcHandler.npcs[i].attackType == AttackType.FIRE_BREATH.getValue()) {
						NpcHandler.npcs[i].hitDelayTimer += 2;
					}
					if (NpcHandler.multiAttacks(i)) {
						multiAttackGfx(i, NpcHandler.npcs[i].projectileId);
						NpcData.startAnimation(NpcEmotes.getAttackEmote(i), i);
						if (CombatConstants.COMBAT_SOUNDS) {
							if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
								return;
							}
							c.getPacketSender().sendSound(CombatSounds.getNpcAttackSounds(NpcHandler.npcs[i].npcType), 100, 0);
						}
						NpcHandler.npcs[i].oldIndex = c.playerId;
						return;
					}
					if (NpcHandler.npcs[i].projectileId > 0) {
						int nX = NpcHandler.npcs[i].getX() + NpcHandler.offset(i);
						int nY = NpcHandler.npcs[i].getY() + NpcHandler.offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY) * -1;
						int offY = (nX - pX) * -1;
						c.getPlayerAssistant().createPlayersProjectile(nX, nY, offX, offY, 50, NpcHandler.getProjectileSpeed(i), NpcHandler.npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);
					}
					int random = Misc.random(10);
					if (NpcHandler.npcs[i].npcType == 222 && (NpcHandler.npcs[i].killerId > 0 && NpcHandler.npcs[i].underAttack) && !NpcHandler.npcs[i].isDead && (NpcHandler.npcs[i].HP < NpcHandler.npcs[i].MaxHP + 1)) {
						if (random < 3) {
							NpcHandler.npcs[i].HP += 2;
							//NpcHandler.npcs[i].startAnimation(84); 
							NpcHandler.npcs[i].updateRequired = true;
						}
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					NpcHandler.npcs[i].oldIndex = c.playerId;
					NpcData.startAnimation(NpcEmotes.getAttackEmote(i), i);
					if (CombatConstants.COMBAT_SOUNDS) {
						if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
							return;
						}
						c.getPacketSender().sendSound(CombatSounds.getNpcAttackSounds(NpcHandler.npcs[i].npcType), 100, 0);
					}
					c.getPacketSender().closeAllWindows();
				}
			}
		}
	}

	public static void loadSpell2(int i) {
		NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
		int random = Misc.random(3);
		if (random == 0) {
			NpcHandler.npcs[i].projectileId = 393; // red
			NpcHandler.npcs[i].endGfx = 430;
		} else if (random == 1) {
			NpcHandler.npcs[i].projectileId = 394; // green
			NpcHandler.npcs[i].endGfx = 429;
		} else if (random == 2) {
			NpcHandler.npcs[i].projectileId = 395; // white
			NpcHandler.npcs[i].endGfx = 431;
		} else if (random == 3) {
			NpcHandler.npcs[i].projectileId = 396; // blue
			NpcHandler.npcs[i].endGfx = 428;
		}
	}

	public static void loadSpell(Player c, int i) {
		if (NpcHandler.npcs[i].npcType > 2462 && NpcHandler.npcs[i].npcType < 2469 || NpcHandler.npcs[i].npcType > 3751 && NpcHandler.npcs[i].npcType < 3762) {
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
		}
		if (NpcHandler.npcs[i].npcType > 3761 && NpcHandler.npcs[i].npcType < 3772) {
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
		}
		switch (NpcHandler.npcs[i].npcType) {
		case 1158://kq first form
			int kqRandom = Misc.random(3);
				if (kqRandom == 2) {
					NpcHandler.npcs[i].projectileId = 280; //gfx
					NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();	
					NpcHandler.npcs[i].endGfx = 279;
				} else {
					NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();	
				}
				break;
			case 1160://kq secondform
				int kqRandom2 = Misc.random(3);
				if (kqRandom2 == 2) {
					NpcHandler.npcs[i].projectileId = 279; //gfx
					NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue() + Misc.random(1);	
					NpcHandler.npcs[i].endGfx = 278;
				} else {
					NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();	
				}
				break;
		case 2607:
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
		case 2591:
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			break;	
		case 172:
		case 174:
			NpcHandler.npcs[i].gfx100(96); // Dark Wizards use earth strike
			NpcHandler.npcs[i].projectileId = 97; 
			NpcHandler.npcs[i].endGfx = 98;
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			break;
		case 3068:
		if(Misc.random(10) > 7) {
			NpcHandler.npcs[i].projectileId = 393; //red
			NpcHandler.npcs[i].endGfx = 430;
			NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
			NpcData.startAnimation(2989, i);
		} else {
			NpcData.startAnimation(2980, i);
			NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
		}
		break;
		case 2892:
			NpcHandler.npcs[i].projectileId = 94;
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].endGfx = 95;
			break;
		case 2894:
			NpcHandler.npcs[i].projectileId = 298;
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			break;
		/*
		 * Better Dragons
		 */
		case 5363: // Mithril-Dragon
		case 53: // Red Dragon
		case 54: // Black-Dragon
		case 55: // Blue-Dragon
		case 941: // Green-Dragon
		case 4682:
		case 5362:
		case 1590:
		case 1591:
		case 1592:
			int random1 = Misc.random(3);
			switch (random1) {
			case 1:
				NpcHandler.npcs[i].projectileId = 393; // red
				NpcHandler.npcs[i].endGfx = 430;
				NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
				break;
			default:
				NpcHandler.npcs[i].projectileId = -1; // melee
				NpcHandler.npcs[i].endGfx = -1;
				NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
				break;
			}
			break;
		case 134:
			if (c.playerLevel[Constants.PRAYER] > 0) {
				c.playerLevel[Constants.PRAYER]--;
				c.getPlayerAssistant().refreshSkill(Constants.PRAYER);
				c.getPlayerAssistant().appendPoison(5);
				c.getCombatAssistant().resetPlayerAttack();
			}
			break;

		case 3590:
		case 50:
		case 742:
			int random = Misc.random(4);
			switch (random) {
			case 0:
				NpcHandler.npcs[i].projectileId = 393; // red
				NpcHandler.npcs[i].endGfx = 430;
				NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
				break;
			case 1:
				NpcHandler.npcs[i].projectileId = 394; // green
				NpcHandler.npcs[i].endGfx = 429;
				NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
				break;
			case 2:
				NpcHandler.npcs[i].projectileId = 395; // white
				NpcHandler.npcs[i].endGfx = 431;
				NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
				break;
			case 3:
				NpcHandler.npcs[i].projectileId = 396; // blue
				NpcHandler.npcs[i].endGfx = 428;
				NpcHandler.npcs[i].attackType = AttackType.FIRE_BREATH.getValue();
				break;
			case 4:
				NpcHandler.npcs[i].projectileId = -1; // melee
				NpcHandler.npcs[i].endGfx = -1;
				NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
				break;
			}
			break;
		// arma npcs
		case 2561:
			NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
			break;
		case 2560:
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 1190;
			break;
		case 2559:
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].projectileId = 1203;
			break;
		case 2558:
			random = Misc.random(1);
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue() + random;
			if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) {
				NpcHandler.npcs[i].projectileId = 1197;
			} else {
				NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
				NpcHandler.npcs[i].projectileId = 1198;
			}
			break;
		// sara npcs
		case 2562: // sara
			random = Misc.random(1);
			if (random == 0) {
				NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
				NpcHandler.npcs[i].endGfx = 1224;
				NpcHandler.npcs[i].projectileId = -1;
			} else if (random == 1) {
				NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
			}
			break;
		case 2563: // star
			NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
			break;
		case 2564: // growler
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].projectileId = 1203;
			break;
		case 2565: // bree
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 9;
			break;
		// bandos npcs
		case 2550:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
			} else {
				NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
				NpcHandler.npcs[i].endGfx = 1211;
				NpcHandler.npcs[i].projectileId = 288;
			}
			break;
		case 2551:
			NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
			break;
		case 2552:
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].projectileId = 1203;
			break;
		case 2553:
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 1206;
			break;
		case 2025:
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			int r = Misc.random(3);
			if (r == 0) {
				NpcHandler.npcs[i].gfx100(158);
				NpcHandler.npcs[i].projectileId = 159;
				NpcHandler.npcs[i].endGfx = 160;
			}
			if (r == 1) {
				NpcHandler.npcs[i].gfx100(161);
				NpcHandler.npcs[i].projectileId = 162;
				NpcHandler.npcs[i].endGfx = 163;
			}
			if (r == 2) {
				NpcHandler.npcs[i].gfx100(164);
				NpcHandler.npcs[i].projectileId = 165;
				NpcHandler.npcs[i].endGfx = 166;
			}
			if (r == 3) {
				NpcHandler.npcs[i].gfx100(155);
				NpcHandler.npcs[i].projectileId = 156;
			}
			break;
		case 2881:// supreme
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 298;
			break;

		case 2882:// prime
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].projectileId = 162;
			NpcHandler.npcs[i].endGfx = 477;
			break;

		case 2028:
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 27;
			break;

		case 3200:
			int r2 = Misc.random(1);
			if (r2 == 0) {
				NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
				NpcHandler.npcs[i].gfx100(550);
				NpcHandler.npcs[i].projectileId = 551;
				NpcHandler.npcs[i].endGfx = 552;
			} else {
				NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
				NpcHandler.npcs[i].gfx100(553);
				NpcHandler.npcs[i].projectileId = 554;
				NpcHandler.npcs[i].endGfx = 555;
			}
			break;
		case 2745:
			int r3 = 0;
			if (NpcHandler
					.goodDistance(
							NpcHandler.npcs[i].absX,
							NpcHandler.npcs[i].absY,
							PlayerHandler.players[NpcHandler.npcs[i].spawnedBy].absX,
							PlayerHandler.players[NpcHandler.npcs[i].spawnedBy].absY,
							1)) {
				r3 = Misc.random(2);
			} else {
				r3 = Misc.random(1);
			}
			if (r3 == 0) {
				NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
				NpcHandler.npcs[i].endGfx = 157;
				NpcHandler.npcs[i].projectileId = 448;
			} else if (r3 == 1) {
				NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
				NpcHandler.npcs[i].projectileId = 451;
			} else if (r3 == 2) {
				NpcHandler.npcs[i].attackType = AttackType.MELEE.getValue();
				NpcHandler.npcs[i].projectileId = -1;
			}
			break;
		case 2743:
			NpcHandler.npcs[i].attackType = AttackType.MAGIC.getValue();
			NpcHandler.npcs[i].projectileId = 445;
			NpcHandler.npcs[i].endGfx = 446;
			break;

		case 2631:
			NpcHandler.npcs[i].attackType = AttackType.RANGE.getValue();
			NpcHandler.npcs[i].projectileId = 443;
			break;
		}
	}

	public static void registerNpcHit(int i) {
		if (NpcHandler.npcs[i] != null) {
			if (PlayerHandler.players[NpcHandler.npcs[i].oldIndex] == null) {
				return;
			}
			if (NpcHandler.npcs[i].isDead) {
				return;
			}
			Client c = (Client) PlayerHandler.players[NpcHandler.npcs[i].oldIndex];
			if (NpcHandler.multiAttacks(i)) {
				NpcCombat.multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0) {
				if (c.autoRet == 1 && NpcHandler.npcs[i].npcType != 411) {
					c.npcIndex = i;
				}
			}
			if (c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0 && c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombatAssistant().getBlockEmote());
			}
			if (c.respawnTimer <= 0) {
				int damage = 0;
				if (NpcHandler.npcs[i].attackType == AttackType.MELEE.getValue()) {
					damage = Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getMaxHit());
					if (5 + Misc.random(c.getCombatAssistant().calcDef()) > Misc
							.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getAttackBonus())) {
						damage = 0;
					}
					if (NpcData.cantKillYou(NpcHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Constants.HITPOINTS]) {
							damage = c.playerLevel[Constants.HITPOINTS] - 1;
						}
					}
					if (c.getPrayer().prayerActive[18] && !(NpcHandler.npcs[i].npcType == 2030)) { // protect from melee
						damage = 0;
					} else if (c.getPrayer().prayerActive[18] && NpcHandler.npcs[i].npcType == 2030) {
						if (NpcHandler.npcs[i].attackType == AttackType.MELEE.getValue()) {
							damage = Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getMaxHit());
						}
						if (5 + Misc.random(MeleeData.calculateMeleeDefence(c)) > Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getAttackBonus())) {
							 if (NpcHandler.npcs[i].npcType == 1158 || NpcHandler.npcs[i].npcType == 1160) 
								damage = (damage / 2);
							 else
								 damage = 0;
						}
					}
					if (c.playerLevel[Constants.HITPOINTS] - damage < 0) {
						damage = c.playerLevel[Constants.HITPOINTS];
					}
				}

				if (NpcHandler.npcs[i].attackType == AttackType.RANGE.getValue()) { // range
					damage = Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getMaxHit());
					if (5 + Misc.random(c.getCombatAssistant().calculateRangeDefence()) > Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getAttackBonus())) {
						if (NpcHandler.npcs[i].npcType == 1158 || NpcHandler.npcs[i].npcType == 1160) 
							damage = (damage / 2);
						 else
							 damage = 0;
					}
					if (NpcData.cantKillYou(NpcHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Constants.HITPOINTS]) {
							damage = c.playerLevel[Constants.HITPOINTS] - 1;
						}
					}
					if (c.getPrayer().prayerActive[17]) { // protect from range
						damage = 0;
					}
					if (c.playerLevel[Constants.HITPOINTS] - damage < 0) {
						damage = c.playerLevel[Constants.HITPOINTS];
					}
				}

				if (NpcHandler.npcs[i].attackType == AttackType.MAGIC.getValue()) { // magic
					damage = Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getMaxHit());
					boolean magicFailed = false;
					if (5 + Misc.random(c.getCombatAssistant().mageDef()) > Misc.random(NPCDefinition.forId(NpcHandler.npcs[i].npcType).getAttackBonus())) {
						damage = 0;
						magicFailed = true;
					}
					if (NpcData.cantKillYou(NpcHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Constants.HITPOINTS]) {
							damage = c.playerLevel[Constants.HITPOINTS] - 1;
						}
					}
					if(c.getPrayer().prayerActive[16]) { // protect from magic
						
						 if (NpcHandler.npcs[i].npcType == 1158)  {
							 damage = (damage / 2);
						 } else {
							 damage = 0;
						}
						magicFailed = true;			
						if (c.playerLevel[Constants.HITPOINTS] - damage < 0) {
							damage = c.playerLevel[Constants.HITPOINTS];
						}
						if(NpcHandler.npcs[i].endGfx > 0 && (!magicFailed || FightCaves.isFightCaveNpc(i))) {
							c.gfx100(NpcHandler.npcs[i].endGfx);
						} else {
							c.gfx100(85);
							c.getPacketSender().sendSound(SoundList.MAGE_FAIL, 100, 0);
						}
					}
				}

				if (NpcHandler.npcs[i].attackType == AttackType.FIRE_BREATH.getValue()) { // fire breath
					int anti = c.getPlayerAssistant().antiFire();
					switch (anti) {
					case 0:// has no shield
						damage = Misc.random(45) + 10;
						c.getPacketSender().sendMessage("You are badly burnt by the dragon fire!");
						break;
					case 1:// has a shield
						if (c.getItemAssistant().playerHasEquipped(5, 1540)) {
							damage = Misc.random(4) + 1;
							c.getPacketSender().sendMessage("Your shield protects you from the fire.");
						}
						break;
					case 2:// melee
						damage = Misc.random(5);
						break;
					}
				}
				if (damage > 0) {
					c.getCombatAssistant().applyRecoilNPC(c, damage, i);
				}
				if (c.getPlayerAssistant().savePlayer()) {
					c.getPlayerAssistant().handleROL();
				} else {
					int difference = c.playerLevel[Constants.HITPOINTS] - damage;
					if (difference <= c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) / 10 && difference > 0) {
						c.appendRedemption();
					} 
					if (c.playerLevel[Constants.HITPOINTS] - damage < 0) {
						damage = c.playerLevel[Constants.HITPOINTS];
					}
					NpcHandler.handleSpecialEffects(c, i, damage);
					c.logoutDelay = System.currentTimeMillis(); // logout delay
					c.handleHitMask(damage);
					c.playerLevel[Constants.HITPOINTS] -= damage;
					c.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
					FightCaves.tzKihEffect(c, i, damage);
					if (damage > 0)
					{
						c.getPacketSender().sendSound(822 + Misc.random(2), 100, 0);
					}
					c.updateRequired = true;
				}
			}
		}
	}

}
