package redone.game.npcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import redone.Constants;
import redone.Server;
import redone.game.content.combat.npcs.NpcAggressive;
import redone.game.content.combat.npcs.NpcCombat;
import redone.game.content.combat.npcs.NpcEmotes;
import redone.game.content.minigames.FightCaves;
import redone.game.content.minigames.PestControl;
import redone.game.content.music.sound.CombatSounds;
import redone.game.content.randomevents.FreakyForester;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.randomevents.RiverTroll;
import redone.game.npcs.drops.NPCDropsHandler;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;
import redone.world.clip.Region;

// Facetypes: 1-Walk, 2-North, 3-South, 4-East, 5-West

public class NpcHandler {

	public static int maxNPCs = 5000;
	public static int maxListedNPCs = 5000;
	public static Npc npcs[] = new Npc[maxNPCs];
	public static NpcList NpcList[] = new NpcList[maxListedNPCs];

	public NpcHandler() {
		for (int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
		}
		for (int i = 0; i < maxListedNPCs; i++) {
			NpcList[i] = null;
		}
		loadNPCList("./Data/CFG/npc.cfg");
		loadAutoSpawn("./Data/CFG/spawn-config.cfg");
	}
	
	public static boolean isUndead(int index) {
		String name = getNpcListName(npcs[index].npcType);
		for(String s : Constants.UNDEAD)
			if(s.equalsIgnoreCase(name))
				return true;
		return false;
	}

	public void spawnNpc3(Client c, int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence,
			boolean attackPlayer, boolean headIcon, boolean summonFollow) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			return;
		}
		Npc newNPC = new Npc(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		newNPC.facePlayer(c.playerId);
		if (headIcon) {
			c.getActionSender().drawHeadicon(1, slot, 0, 0);
		}
		if (summonFollow) {
			newNPC.summoner = true;
			newNPC.summonedBy = c.playerId;
			c.summonId = npcType;
			c.hasNpc = true;
		}
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}

	public boolean switchesAttackers(int i) {
		switch (npcs[i].npcType) {
		case 2551:
		case 2552:
		case 2553:
		case 2559:
		case 2560:
		case 2561:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
			return true;
		}
		return false;
	}

	public int getClosePlayer(Client c, int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (j == npcs[i].spawnedBy) {
					return j;
				}
				if (goodDistance(PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || FightCaves.isFightCaveNpc(i)) {
					if (PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0 || PlayerHandler.players[j].inMulti()) {
						if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel) {
							return j;
						}
					}
				}
			}
		}
		return 0;
	}

	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2883:
		case 2882:
		case 2881:
			return 3;
		}
		return 0;
	}

	/**
	 * Summon npc, barrows, etc
	 **/
	public static void spawnNpc(Client c, int npcType, int x, int y,
			int heightLevel, int WalkingType, int HP, int maxHit, int attack,
			int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		Npc newNPC = new Npc(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if (newNPC.npcType == FightCaves.TZTOK_JAD) {
			c.setSpecialTarget(newNPC);
		}
		if (headIcon) {
			c.getActionSender().drawHeadicon(1, slot, 0, 0);
		}
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
				for (int anim = 4277; anim < 4285; anim++) {
					if (npcType == anim) {
						newNPC.forceChat("I'M ALIVE!");
					}
				}

				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}

	public void spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		Npc newNPC = new Npc(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}
	
	private void killedBarrow(int i) {
		Client c = (Client)PlayerHandler.players[npcs[i].killedBy];
			if(c != null) {
				for(int o = 0; o < c.barrowsNpcs.length; o++){
					if(npcs[i].npcType == c.barrowsNpcs[o][0]) {
						c.barrowsNpcs[o][1] = 2; // 2 for dead
						c.barrowsKillCount++;
					}
				}
			}
		}
	
	private void killedCrypt(int i) {
		Client c = (Client)PlayerHandler.players[npcs[i].killedBy];
			if(c != null) {
				for(int o = 0; o < c.barrowCrypt.length; o++){
					if(npcs[i].npcType == c.barrowCrypt[o][0]) {
						c.barrowsKillCount++;
						c.getPlayerAssistant().sendFrame126(""+c.barrowsKillCount, 4536);
					}
				}
			}
		}

	public void newNPC(int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1) {
			return; // no free slot found
		}
		
		Npc newNPC = new Npc(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1) {
			return; // no free slot found
		}

		NpcList newNPCList = new NpcList(npcType);
		newNPCList.npcName = npcName;
		newNPCList.npcCombat = combat;
		newNPCList.npcHealth = HP;
		NpcList[slot] = newNPCList;
	}

	public String[] voidKnightTalk = { "We must not fail!",
			"Take down the portals", "The Void Knights will not fall!",
			"Hail the Void Knights!", "We are beating these scum!",
			"Don't let these creatures leech my health!!",
			"Do not let me die!!!", "Please....help me!",
			"For the knights we shall prevail!" };

	public int getKillerId(int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].killedBy == playerId) {
					if (PlayerHandler.players[i]
							.withinDistance(PlayerHandler.players[playerId])) {
						if (PlayerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = PlayerHandler.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}
					PlayerHandler.players[i].totalPlayerDamageDealt = 0;
					PlayerHandler.players[i].killedBy = 0;
				}
			}
		}
		return killerId;
	}
	
	public void process() {
		for (Npc i : NpcHandler.npcs) {
			if (i == null) {
				continue;
			}
			i.clearUpdateFlags();
		}

		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {

				Client slaveOwner = (Client) PlayerHandler.players[npcs[i].summonedBy];
				if (slaveOwner == null && npcs[i].summoner) {
					npcs[i].absX = 0;
					npcs[i].absY = 0;
				}
				if (slaveOwner != null
						&& slaveOwner.hasNpc
						&& !slaveOwner.goodDistance(npcs[i].getX(),
								npcs[i].getY(), slaveOwner.absX,
								slaveOwner.absY, 15) && npcs[i].summoner) {
					npcs[i].absX = slaveOwner.absX;
					npcs[i].absY = slaveOwner.absY - 1;
				}

				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
				}

				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}

				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}

				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					NpcCombat.registerNpcHit(i);
				}

				if (npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}

				if (npcs[i].npcType == 3782 && PestControl.gameStarted) {
					if (Misc.random(10) == 4) {
						npcs[i].forceChat(voidKnightTalk[Misc
								.random3(voidKnightTalk.length)]);
					}
				}

				if (npcs[i].spawnedBy > 0) { // delete summons npc
					if (PlayerHandler.players[npcs[i].spawnedBy] == null
							|| PlayerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel
							|| PlayerHandler.players[npcs[i].spawnedBy].respawnTimer > 0
							|| !PlayerHandler.players[npcs[i].spawnedBy]
									.goodDistance(
											npcs[i].getX(),
											npcs[i].getY(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getX(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getY(), 20)) {
						
						if (npcs[i].npcType == FightCaves.YT_HURKOT) {
							Client c = ((Client)PlayerHandler.players[npcs[i].spawnedBy]);
							int ranHeal = Misc.random(19);
							if (ranHeal == 19)
								FightCaves.healJad(c, i);
						}

						if (PlayerHandler.players[npcs[i].spawnedBy] != null) {
							for (int o = 0; o < PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++) {
								if (npcs[i].npcType == PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
									if (PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1) {
										PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
									}
								}
							}
						}
						npcs[i] = null;
					}
				}
				if (npcs[i] == null) {
					continue;
				}

				/**
				 * Attacking player
				 **/
				
				if (NpcAggressive.isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && !switchesAttackers(i)) {
					Client client = (Client) PlayerHandler.players[NpcData.getCloseRandomPlayer(i)];
					if (client != null && getNpcListCombat(npcs[i].npcType) * 2 > client.combatLevel || npcs[i].npcType == 1265 || npcs[i].npcType == 1267 || npcs[i].npcType == 96 || npcs[i].npcType == 97 || npcs[i].npcType == 141) {
						npcs[i].killerId = NpcData.getCloseRandomPlayer(i);
					}
				} else if (NpcAggressive.isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && switchesAttackers(i)) {
					Client c = (Client) PlayerHandler.players[NpcData.getCloseRandomPlayer(i)];
					if (c != null && getNpcListCombat(npcs[i].npcType) * 2 > c.combatLevel) {
						npcs[i].killerId = NpcData.getCloseRandomPlayer(i);
					}
				}
				/*
				 * Attacking player
				 */

				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000) {
					npcs[i].underAttackBy = 0;
				}

				if ((npcs[i].killerId > 0 || npcs[i].underAttack)
						&& !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
					if (!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if (PlayerHandler.players[p] != null) {
							Client c = (Client) PlayerHandler.players[p];
							followPlayer(i, c.playerId);
							if (npcs[i] == null) {
								continue;
							}
							stepAway(c,i);
							if (npcs[i].attackTimer == 0) {
								NpcCombat.attackPlayer(c, i);
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}

				/**
				 * Random walking and walking home
				 **/
				if (npcs[i] == null) {
					continue;
				}
				if ((!npcs[i].underAttack || npcs[i].walkingHome)
						&& npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;
					if (npcs[i].spawnedBy == 0) {
						if (npcs[i].absX > npcs[i].makeX
								+ Constants.NPC_RANDOM_WALK_DISTANCE
								|| npcs[i].absX < npcs[i].makeX
										- Constants.NPC_RANDOM_WALK_DISTANCE
								|| npcs[i].absY > npcs[i].makeY
										+ Constants.NPC_RANDOM_WALK_DISTANCE
								|| npcs[i].absY < npcs[i].makeY
										- Constants.NPC_RANDOM_WALK_DISTANCE) {
							npcs[i].walkingHome = true;
						}
					}

					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX
							&& npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if (npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						handleClipping(i);
						npcs[i].getNextNPCMovement(i);
						npcs[i].updateRequired = true;
					}
					if (npcs[i].walkingType == 1) {
						if (Misc.random(3) == 1 && !npcs[i].walkingHome) {
							int MoveX = 0;
							int MoveY = 0;
							int Rnd = Misc.random(9);
							if (Rnd == 1) {
								MoveX = 1;
								MoveY = 1;
							} else if (Rnd == 2) {
								MoveX = -1;
							} else if (Rnd == 3) {
								MoveY = -1;
							} else if (Rnd == 4) {
								MoveX = 1;
							} else if (Rnd == 5) {
								MoveY = 1;
							} else if (Rnd == 6) {
								MoveX = -1;
								MoveY = -1;
							} else if (Rnd == 7) {
								MoveX = -1;
								MoveY = 1;
							} else if (Rnd == 8) {
								MoveX = 1;
								MoveY = -1;
							}

							if (MoveX == 1) {
								if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveX == -1) {
								if (npcs[i].absX - MoveX > npcs[i].makeX - 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveY == 1) {
								if (npcs[i].absY + MoveY < npcs[i].makeY + 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}

							if (MoveY == -1) {
								if (npcs[i].absY - MoveY > npcs[i].makeY - 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}
							handleClipping(i);
							// NpcData.handleClipping(i);
							npcs[i].getNextNPCMovement(i);
							npcs[i].updateRequired = true;
						}
					}
				}

				if (npcs[i].walkingType >= 0) {
					switch (npcs[i].walkingType) {

					case 5:
						npcs[i].turnNpc(npcs[i].absX - 1, npcs[i].absY);
						break;

					case 4:
						npcs[i].turnNpc(npcs[i].absX + 1, npcs[i].absY);
						break;

					case 3:
						npcs[i].turnNpc(npcs[i].absX, npcs[i].absY - 1);
						break;

					case 2:
						npcs[i].turnNpc(npcs[i].absX, npcs[i].absY + 1);
						break;

					default:
						if (npcs[i].walkingType >= 0) {
							npcs[i].turnNpc(npcs[i].absX, npcs[i].absY);
						}
						break;
					}
				}

				if (npcs[i].isDead) {
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false
							&& npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = NpcData.getNpcKillerId(i);
						npcs[i].animNumber = NpcEmotes.getDeadEmote(i); // dead
																		// emote
						Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
						if (c != null) {
							if (Constants.combatSounds
									&& NpcHandler.npcs[i].npcType < 3177
									&& NpcHandler.npcs[i].npcType > 3180) {
								c.getActionSender()
										.sendSound(
												CombatSounds
														.getNpcDeathSounds(npcs[i].npcType),
												100, 0);
							}
						}
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						killedBarrow(i);
						killedCrypt(i);
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].applyDead == true
							&& npcs[i].needRespawn == false) {
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = NpcData.getRespawnTime(i); // respawn
																			// time
						dropItems(i); // npc drops items!
						FightCaves.tzhaarDeathHandler(i);
						if (npcs[i].npcType == 2745) {
							FightCaves.handleJadDeath(i);
							if (PlayerHandler.players[npcs[i].killerId] != null) {
								PlayerHandler.players[npcs[i].killerId].spawnedHealers = 0;
								PlayerHandler.players[npcs[i].killerId].canHealersRespawn = true;
							}
						}
						if (npcs[i].npcType == FightCaves.YT_HURKOT) {
							if (PlayerHandler.players[npcs[i].killerId] != null) {
								if (PlayerHandler.players[npcs[i].killerId].spawnedHealers != 0) {
									PlayerHandler.players[npcs[i].killerId].spawnedHealers -= 1;
									if (PlayerHandler.players[npcs[i].killerId].spawnedHealers <= 0) {
										PlayerHandler.players[npcs[i].killerId].spawnedHealers = 0;
									}
								}
							}
						}
						appendSlayerExperience(i);
						resetEvent(i);
						Client player = (Client) PlayerHandler.players[npcs[i].killedBy];
						if (player != null) {
							if (player.tutorialProgress == 24) {
								handleratdeath(i);
							} else if (player.tutorialProgress == 25
									&& player.ratdied2 == true) {
								handleratdeath2(i);
							}
						}
						if (npcs[i].npcType > 3726 && npcs[i].npcType < 3732) {
							int damage = 10 + Misc.random(10);
							player.playerLevel[player.playerHitpoints] = player.getPlayerAssistant().getLevelForXP(player.playerXP[player.playerHitpoints]) - damage;
							player.getPlayerAssistant().refreshSkill(player.playerHitpoints);
							player.handleHitMask(damage);
						}
						if (npcs[i].npcType == 655) {
							player.spiritTree = true;
							player.getActionSender().sendMessage(
									"You have defeated the tree spirit.");
						}
						if (npcs[i].npcType > 412 && npcs[i].npcType < 419) {
							player.golemSpawned = false;
						}
						if (npcs[i].npcType == 757 && player.vampSlayer == 3) {
							player.vampSlayer = 4;
						}
						if (npcs[i].npcType > 390 && npcs[i].npcType < 397) {
							RiverTroll.hasRiverTroll = false;
						}
						if (npcs[i].npcType > 418 && npcs[i].npcType < 425) {
							player.zombieSpawned = false;
						}
						if (npcs[i].npcType > 424 && npcs[i].npcType < 431) {
							player.shadeSpawned = false;
						}
						if (npcs[i].npcType > 437 && npcs[i].npcType < 444) {
							player.treeSpiritSpawned = false;
						}
						if (npcs[i].npcType > 2462 && npcs[i].npcType < 2469) {
							player.chickenSpawned = false;
						}
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType >= 2440 && npcs[i].npcType <= 2446) {
							Server.objectManager.removeObject(npcs[i].absX,
									npcs[i].absY);
						}
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].needRespawn == true) {
						if (npcs[i].spawnedBy > 0) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;
							int old9 = npcs[i].defence;

							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7,
									old8, old9);
						}
					}
				}
			}
		}
	}

	private void handleratdeath(int i) {
		final Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler()
					.chatboxText(
							c,
							"",
							"Pass through the gate and talk to the Combat Instructor, he",
							"will give you your next task.", "",
							"Well done, you've made your first kill!");
			c.getActionSender().chatbox(6179);
			c.getActionSender().drawHeadicon(1, 6, 0, 0); // draws
																// headicon to
																// combat ude
			c.tutorialProgress = 25;
		}
	}

	private void handleratdeath2(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler()
					.chatboxText(
							c,
							"You have completed the tasks here. To move on, click on the",
							"ladder shown. If you need to go over any of what you learnt",
							"here, just talk to the Combat Instructor and he'll tell you what",
							"he can.", "Moving on");
			c.getActionSender().chatbox(6179);
			c.tutorialProgress = 26;
			c.getActionSender().createArrow(3111, 9525, c.getH(), 2); // send
																			// hint
																			// to
																			// furnace
		}
	}

	public boolean getsPulled(Client c, int i) {
		switch (npcs[i].npcType) {
		case 2550:
			if (npcs[i].firstAttacker > 0) {
				return false;
			}
			break;
		case 87:
			if (c.isInTut() || c.tutorialProgress < 36) {
				return false;
			}
			break;
		}
		return true;
	}

	public static boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
		case 2558:
			return true;
		case 2562:
			if (npcs[i].attackType == 2) {
				return true;
			}
		case 2550:
			if (npcs[i].attackType == 1) {
				return true;
			}
		default:
			return false;
		}

	}
	
	private void stepAway(Client c, int i) {
		int otherX = NpcHandler.npcs[i].getX();
		int otherY = NpcHandler.npcs[i].getY();
		if (otherX == c.getX() && otherY == c.getY()) {
			if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
				npcs[i].moveX = -1;
			} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
				npcs[i].moveX = 1;
			} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
				npcs[i].moveY = -1;
			} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
				npcs[i].moveY = 1;
			}
			npcs[i].getNextNPCMovement(i);
			npcs[i].updateRequired = true;
		}
	}

	public static void handleClipping(int i) {
		Npc npc = npcs[i];
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
	 * Dropping Items!
	 **/

	// [npc][0] = item dropped
	// [npc][1] = amount
	// [npc][2] = rarity
	// [j][2] = rarity
	// [j][1] = amount
	// [j][0] = drop

	public void dropItems(int i) {// ring of wealth to add
		int npc = 0;
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			for (npc = 0; npc < NPCDropsHandler.NPC_DROPS(getNpcListName(npcs[i].npcType).toLowerCase(), npcs[i].npcType).length; npc++) {
				if (Misc.random(NPCDropsHandler.NPC_DROPS(getNpcListName(npcs[i].npcType).toLowerCase(), npcs[i].npcType)[npc][2]) == 0 && npcs[i].npcType != 2627 && npcs[i].npcType != 2638 && npcs[i].npcType != 2630 && npcs[i].npcType != 2631 && npcs[i].npcType != 2641 && npcs[i].npcType != 2643 && npcs[i].npcType != 2645 && npcs[i].npcType != 1532 && npcs[i].npcType != 153 && !PestControl.npcIsPCMonster(npcs[i].npcType)) {
					Server.itemHandler.createGroundItem(c, NPCDropsHandler.NPC_DROPS(getNpcListName(npcs[i].npcType).toLowerCase(), npcs[i].npcType)[npc][0], npcs[i].absX, npcs[i].absY, NPCDropsHandler.NPC_DROPS(getNpcListName(npcs[i].npcType).toLowerCase(), npcs[i].npcType)[npc][1], c.playerId);
					
							}
					}
					switch (npcs[i].npcType) {
					case 2459:
						FreakyForester.killedPheasant(c, 0);
						Server.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
						break;
					case 2460:
						FreakyForester.killedPheasant(c, 1);
						Server.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
						break;
					case 2461:
						FreakyForester.killedPheasant(c, 2);
						Server.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
						break;
					case 2462:
						FreakyForester.killedPheasant(c, 3);
						Server.itemHandler.createGroundItem(c, 6178, npcs[i].absX, npcs[i].absY, 1, c.playerId);
						break;
					case 92:
						if (c.restGhost == 3) {
							Server.itemHandler.createGroundItem(c, 553, npcs[i].absX, npcs[i].absY, 1, c.playerId);
							c.restGhost = 4;
						}
						break;
					case 47:
						if (c.witchspot == 1 || c.romeojuliet > 0 && c.romeojuliet < 9) {
							Server.itemHandler.createGroundItem(c, 300, npcs[i].absX, npcs[i].absY, 1, c.playerId);
						}
						break;
					}
				}
			}
	/**
	 * Slayer Experience
	 **/
	public void appendSlayerExperience(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			// if (c.getSlayer().isSlayerTask(i)) {
			if (c.slayerTask == npcs[i].npcType) {
				c.taskAmount--;
				c.getPlayerAssistant().addSkillXP(c.getSlayer().getTaskExp(c.slayerTask), 18);
				if (c.taskAmount <= 0) {
					int points = c.getSlayer().getDifficulty(c.slayerTask) * 4;
					c.slayerTask = -1;
					c.slayerPoints += points;
					c.getActionSender().sendMessage("You completed your slayer task. You obtain " + points + " slayer points. Please talk to your slayer master.");
				}
			}
		}
	}

	// }

	public void resetEvent(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			RandomEventHandler.addRandom(c);
		}
	}

	/**
	 * Resets players in combat
	 */

	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].underAttackBy2 == i) {
					PlayerHandler.players[j].underAttackBy2 = 0;
				}
			}
		}
	}

	/**
	 * Npc Follow Player
	 **/

	public static int GetMove(int Place1, int Place2) {
		if (Place1 - Place2 == 0) {
			return 0;
		} else if (Place1 - Place2 < 0) {
			return 1;
		} else if (Place1 - Place2 > 0) {
			return -1;
		}
		return 0;
	}

	public static boolean followPlayer(int i) {
		if (NpcHandler.npcs[i].inLesserNpc()) {
			return false;
		}
		switch (npcs[i].npcType) {
		case 1456:
		case 2892:
		case 2894:
		case 1532:
		case 1534:
			return false;
		}
		return true;
	}

	public static void followPlayer(int i, int playerId) {
		if (PlayerHandler.players[playerId] == null) {
			return;
		}
		if (PlayerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}
		
		if (npcs[i].npcType == 1532 || npcs[i].npcType == 1534) {
			return;
		}

		if (!followPlayer(i) && npcs[i].npcType != 1532 && npcs[i].npcType != 1534) {
			npcs[i].facePlayer(playerId);
			return;
		}

		int playerX = PlayerHandler.players[playerId].absX;
		int playerY = PlayerHandler.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY,
				distanceRequired(i))) {
			return;
		}

		Npc npc = npcs[i];
		int x = npc.absX;
		int y = npc.absY;
		Player player = PlayerHandler.players[playerId];
		if (npcs[i].spawnedBy > 0
				|| x < npc.makeX + Constants.NPC_FOLLOW_DISTANCE
				&& x > npc.makeX - Constants.NPC_FOLLOW_DISTANCE
				&& y < npc.makeY + Constants.NPC_FOLLOW_DISTANCE
				&& y > npc.makeY - Constants.NPC_FOLLOW_DISTANCE) {
			if (npc.heightLevel == player.heightLevel) {
				if (player != null && npc != null) {
					if (playerY < y) {
						npc.moveX = GetMove(x, playerX);
						npc.moveY = GetMove(y, playerY);
					} else if (playerY > y) {
						npc.moveX = GetMove(x, playerX);
						npc.moveY = GetMove(y, playerY);
					} else if (playerX < x) {
						npc.moveX = GetMove(x, playerX);
						npc.moveY = GetMove(y, playerY);
					} else if (playerX > x) {
						npc.moveX = GetMove(x, playerX);
						npc.moveY = GetMove(y, playerY);
					}
					npc.facePlayer(playerId);
					handleClipping(i);
					npc.getRandomAndHomeNPCWalking(i);
					npc.updateRequired = true;
				}
			}
		} else {
			npc.facePlayer(0);
			npc.randomWalk = true;
			npc.underAttack = false;
		}
	}

	/**
	 * Distanced required to attack
	 **/
	public static int distanceRequired(int i) {
		switch (npcs[i].npcType) {
		case 2025:
		case 2028:
			return 6;
		case 50:
		case 2562:
			return 2;
		case 2881:// dag kings
		case 2882:
		case 3200:// chaos ele
		case 2743:
		case 2631:
		case 2745:
			return 8;
		case 2883:// rex
			return 1;
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
		default:
			return 1;
		}
	}

	public static int followDistance(int i) {
		switch (npcs[i].npcType) {
		case 2550:
		case 2551:
		case 2562:
		case 2563:
			return 8;
		case 2883:
			return 4;
		case 2881:
		case 2882:
			return 1;
		}
		return 0;
	}

	public static int getProjectileSpeed(int i) {
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
			return 85;

		case 2745:
			return 130;

		case 50:
			return 90;

		case 2025:
			return 85;

		case 2028:
			return 80;

		default:
			return 85;
		}
	}

	public static int offset(int i) {
		switch (npcs[i].npcType) {
		case 50:
		case 110:
		case 941:
		case 1590:
		case 1591:
		case 1592:
		case 2642:
		case 55:
		case 54:
		case 53:
			return 2;
		case 2881:
		case 2882:
			return 1;
		case 2745:
		case 2743:
			return 1;
		}
		return 0;
	}

	public boolean specialCase(Client c, int i) { // responsible for npcs that
													// much
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8)
				&& !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
						c.getY(), distanceRequired(i))) {
			return true;
		}
		return false;
	}

	public boolean retaliates(int npcType) {
		return npcType < 3777 || npcType > 3780
				&& !(npcType >= 2440 && npcType <= 2446);
	}

	public static void handleSpecialEffects(Client c, int i, int damage) {
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPlayerAssistant().refreshSkill(5);
						c.getPlayerAssistant().appendPoison(12);
					}
				}
			}
		}
	}

	public static boolean goodDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		return objectX - playerX <= distance && objectX - playerX >= -distance
				&& objectY - playerY <= distance
				&& objectY - playerY >= -distance;
	}

	public static int getMaxHit(int i) {
		switch (npcs[i].npcType) {
		case 2558:
			if (npcs[i].attackType == 2) {
				return 28;
			} else {
				return 68;
			}
		case 1265:
		case 1267:
			return 2;
		case 2562:
			return 31;
		case 2550:
			return 36;
		}
		return 1;
	}

	public static int getNpcListCombat(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcCombat;
				}
			}
		}
		return 0;
	}

	public boolean loadAutoSpawn(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		// int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			// return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]),// npc
							Integer.parseInt(token3[1]),// x
							Integer.parseInt(token3[2]),// y
							Integer.parseInt(token3[3]),// height
							Integer.parseInt(token3[4]),// walk
							getNpcListHP(Integer.parseInt(token3[0])),// health
							Integer.parseInt(token3[5]),// maxhit
							Integer.parseInt(token3[6]),// attack
							Integer.parseInt(token3[7]));// str

				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					//return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public static int getNpcListHP(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcHealth;
				}
			}
		}
		return 0;
	}

	public static String getNpcListName(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcName;
				}
			}
		}
		return "nothing";
	}

	public boolean loadNPCList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
			// characterfile.close();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			// return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1],
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]));
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					//return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public static boolean checkSpawn(Client player, int i) {
		return npcs[i] != null && npcs[i].spawnedBy != -1
				&& npcs[i].npcType == i;
	}

}
