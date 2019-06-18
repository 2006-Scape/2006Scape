package redone.game.content.combat;

import redone.Constants;
import redone.Server;
import redone.game.content.combat.magic.CastOnOther;
import redone.game.content.combat.magic.MagicData;
import redone.game.content.combat.magic.MagicMaxHit;
import redone.game.content.combat.magic.MagicRequirements;
import redone.game.content.combat.magic.MagicSpells;
import redone.game.content.combat.melee.MeleeData;
import redone.game.content.combat.melee.MeleeMaxHit;
import redone.game.content.combat.npcs.NpcEmotes;
import redone.game.content.combat.prayer.PrayerDrain;
import redone.game.content.combat.range.RangeData;
import redone.game.content.combat.range.RangeMaxHit;
import redone.game.content.minigames.FightCaves;
import redone.game.content.minigames.FightPits;
import redone.game.content.minigames.PestControl;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.content.music.sound.CombatSounds;
import redone.game.content.music.sound.SoundList;
import redone.game.content.skills.slayer.SlayerRequirements;
import redone.game.items.ItemAssistant;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerAssistant;
import redone.game.players.PlayerHandler;
import redone.game.players.antimacro.AntiBotting;
import redone.util.Misc;
import redone.world.clip.PathFinder;

/**
 * Rewritten Combat
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class CombatAssistant {

	private final Client c;

	public CombatAssistant(Client Client) {
		c = Client;
	}

	public static boolean inCombat(Client c) {
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			return true;
		}
		return false;
	}

	public void delayedHit(int i) { // npc hit delay
		if (NpcHandler.npcs[i] != null) {
			if (NpcHandler.npcs[i].isDead) {
				c.npcIndex = 0;
				return;
			}
			if (NpcHandler.npcs[i].attackTimer <= 3 || NpcHandler.npcs[i].attackTimer == 0 && NpcHandler.npcs[i].hitDelayTimer > 0 && !c.castingMagic) { // block animation
				NpcHandler.npcs[i].animNumber = NpcEmotes.getBlockEmote(i); // block emote
				NpcHandler.npcs[i].animUpdateRequired = true;
				NpcHandler.npcs[i].updateRequired = true;
			}
			if (Constants.combatSounds && NpcHandler.npcs[i].npcType < 3177 && NpcHandler.npcs[i].npcType > 3180) {
				c.getActionSender().sendSound(CombatSounds.getNpcBlockSound(NpcHandler.npcs[c.oldNpcIndex].npcType), 100, 0);
			}
			NpcHandler.npcs[i].facePlayer(c.playerId);
			if (NpcHandler.npcs[i].underAttackBy > 0 && Server.npcHandler.getsPulled(c, i)) {
				NpcHandler.npcs[i].killerId = c.playerId;
			} else if (NpcHandler.npcs[i].underAttackBy < 0 && !Server.npcHandler.getsPulled(c, i)) {
				NpcHandler.npcs[i].killerId = c.playerId;
			}
			c.lastNpcAttacked = i;
			if (c.projectileStage == 0) { // melee hit damage
				applyNpcMeleeDamage(i, 1);
				if (c.doubleHit) {
					applyNpcMeleeDamage(i, 2);
				}
			}
			if (!c.castingMagic && c.projectileStage > 0) { // range hit
															// damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					damage2 = Misc.random(rangeMaxHit());
				}
				boolean ignoreDef = false;
				if (Misc.random(5) == 1 && c.lastArrowUsed == 9243) {
					ignoreDef = true;
					NpcHandler.npcs[i].gfx0(758);
				}

				if (Misc.random(NpcHandler.npcs[i].defence) > Misc
						.random(10 + calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				} else if (NpcHandler.npcs[i].npcType == 2881
						|| NpcHandler.npcs[i].npcType == 2883 && !ignoreDef) {
					damage = 0;
				}

				if (Misc.random(4) == 1 && c.lastArrowUsed == 9242 && damage > 0) {
					NpcHandler.npcs[i].gfx0(754);
					damage = NpcHandler.npcs[i].HP / 5;
					c.handleHitMask(c.playerLevel[3] / 10);
					c.dealDamage(c.playerLevel[3] / 10);
					c.gfx0(754);
				}

				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					if (Misc.random(NpcHandler.npcs[i].defence) > Misc.random(10 + calculateRangeAttack())) {
						damage2 = 0;
					}
				}
				if (c.dbowSpec) {
					NpcHandler.npcs[i].gfx100(1100);
					if (damage < 8) {
						damage = 8;
					}
					if (damage2 < 8) {
						damage2 = 8;
					}
					c.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1
						&& c.lastArrowUsed == 9244) {
					damage *= 1.45;
					NpcHandler.npcs[i].gfx0(756);
				}

				if (NpcHandler.npcs[i].HP - damage < 0) {
					damage = NpcHandler.npcs[i].HP;
				}
				if (NpcHandler.npcs[i].HP - damage <= 0 && damage2 > 0) {
					damage2 = 0;
				}
				if (c.fightMode == 3) {//range shared
					c.getPlayerAssistant().addSkillXP(damage * Constants.RANGE_EXP_RATE / 3, 4);
					c.getPlayerAssistant().addSkillXP(damage / 3, 1);
					c.getPlayerAssistant().addSkillXP(damage / 3, 3);
					c.getPlayerAssistant().refreshSkill(1);
					c.getPlayerAssistant().refreshSkill(3);
					c.getPlayerAssistant().refreshSkill(4);
				} else {
					c.getPlayerAssistant().addSkillXP(damage * Constants.RANGE_EXP_RATE, 4);
					c.getPlayerAssistant().addSkillXP(damage * Constants.RANGE_EXP_RATE /3, 3);
					c.getPlayerAssistant().refreshSkill(3);
					c.getPlayerAssistant().refreshSkill(4);
				}
				if (damage > 0) {
					if (NpcHandler.npcs[i].npcType >= 3777 && NpcHandler.npcs[i].npcType <= 3780 || PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType)) {
						c.pcDamage += damage;
					}
				}
				boolean dropArrows = true;

				for (int noArrowId : RangeData.NO_ARROW_DROP) {
					if (c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					c.getItemAssistant().dropArrowNpc();
				}
				if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == c.getId() && ((NpcHandler.npcs[i].HP < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
						(NpcHandler.npcs[i].HP-damage+(damage2 > -1 ? damage2 : 0) < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
							if (c.canHealersRespawn) {
								FightCaves.spawnHealers(c, i, 4-c.spawnedHealers);
							}
						}
				NpcHandler.npcs[i].underAttack = true;
				NpcHandler.npcs[i].hitDiff = damage;
				NpcHandler.npcs[i].HP -= damage;
				if (damage2 > -1) {
					NpcHandler.npcs[i].hitDiff2 = damage2;
					NpcHandler.npcs[i].HP -= damage2;
					c.totalDamageDealt += damage2;
				}
				if (c.killingNpcIndex != c.oldNpcIndex) {
					c.totalDamageDealt = 0;
				}
				c.killingNpcIndex = c.oldNpcIndex;
				c.totalDamageDealt += damage;
				NpcHandler.npcs[i].hitUpdateRequired = true;
				if (damage2 > -1) {
					NpcHandler.npcs[i].hitUpdateRequired2 = true;
				}
				NpcHandler.npcs[i].updateRequired = true;

			} else if (c.projectileStage > 0) { // magic hit damage
				int damage = Misc
						.random(MagicData.MAGIC_SPELLS[c.oldSpellId][6]);
				if (MagicSpells.godSpells(c)) {
					if (System.currentTimeMillis() - c.godSpellDelay < Constants.GOD_SPELL_CHARGE) {
						damage += Misc.random(10);
					}
				}
				boolean magicFailed = false;
				// c.npcIndex = 0;
				int bonusAttack = getBonusAttack(i);
				if (Misc.random(NpcHandler.npcs[i].defence) > 10
						+ Misc.random(mageAtk()) + bonusAttack) {
					damage = 0;
					magicFailed = true;
				} else if (NpcHandler.npcs[i].npcType == 2881
						|| NpcHandler.npcs[i].npcType == 2882) {
					damage = 0;
					magicFailed = true;
				}

				if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == c.getId() && ((NpcHandler.npcs[i].HP > (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
						(NpcHandler.npcs[i].HP-damage < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
							if (c.canHealersRespawn)
								FightCaves.spawnHealers(c, i, 4-c.spawnedHealers);
						}
				
				if (NpcHandler.npcs[i].HP - damage < 0) {
					damage = NpcHandler.npcs[i].HP;
				}
				//magic
				c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[c.oldSpellId][7] + damage * Constants.MAGIC_EXP_RATE, 6);
				if (MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1161 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1153 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1157 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1542 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1543 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1562) {
					c.getPlayerAssistant().addSkillXP(damage * Constants.MAGIC_EXP_RATE / 3, 3);
				}
				c.getPlayerAssistant().refreshSkill(3);
				c.getPlayerAssistant().refreshSkill(6);

				if (damage > 0) {
					if (NpcHandler.npcs[i].npcType >= 3777 && NpcHandler.npcs[i].npcType <= 3780 || PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType)) {
						c.pcDamage += damage;
					}
				}
				if (MagicSpells.getEndGfxHeight(c) == 100 && !magicFailed) { // end GFX
					NpcHandler.npcs[i]
							.gfx100(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!magicFailed) {
					NpcHandler.npcs[i]
							.gfx0(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
				}

				if (magicFailed) {
					NpcHandler.npcs[i].gfx100(85);
				}
				if (!magicFailed) {
					int freezeDelay = MagicSpells.getFreezeTime(c);// freeze
					if (freezeDelay > 0 && NpcHandler.npcs[i].freezeTimer == 0) {
						NpcHandler.npcs[i].freezeTimer = freezeDelay;
					}
					switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = Misc.random(damage / 2);
						if (c.playerLevel[3] + heal >= c.getPlayerAssistant()
								.getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPlayerAssistant()
									.getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPlayerAssistant().refreshSkill(3);
						break;
					}

				}
				NpcHandler.npcs[i].underAttack = true;
				if (MagicData.MAGIC_SPELLS[c.oldSpellId][6] != 0) {
					NpcHandler.npcs[i].hitDiff = damage;
					NpcHandler.npcs[i].HP -= damage;
					NpcHandler.npcs[i].hitUpdateRequired = true;
					c.totalDamageDealt += damage;
				}
				c.killingNpcIndex = c.oldNpcIndex;
				NpcHandler.npcs[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				c.oldSpellId = 0;
			}
		}

		if (c.bowSpecShot <= 0) {
			c.oldNpcIndex = 0;
			c.projectileStage = 0;
			c.doubleHit = false;
			c.lastWeaponUsed = 0;
			c.bowSpecShot = 0;
		}
		if (c.bowSpecShot >= 2) {
			c.bowSpecShot = 0;
			// c.attackTimer =
			// getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		}
		if (c.bowSpecShot == 1) {
			fireProjectileNpc();
			c.hitDelay = 2;
			c.bowSpecShot = 0;
		}
	}

	public void applyNpcMeleeDamage(int i, int damageMask) {
		int damage = Misc.random(meleeMaxHit());
		boolean fullVeracsEffect = c.getPlayerAssistant().fullVeracs()
				&& Misc.random(3) == 1;
		if (NpcHandler.npcs[i].HP - damage < 0) {
			damage = NpcHandler.npcs[i].HP;
		}

		if (!fullVeracsEffect) {
			if (Misc.random(NpcHandler.npcs[i].defence) > 10 + Misc
					.random(calcAtt())) {
				damage = 0;
			} else if (NpcHandler.npcs[i].npcType == 2882
					|| NpcHandler.npcs[i].npcType == 2883) {
				damage = 0;
			}
		}
			if (NpcHandler.npcs[i].HP - damage > 0) {
						if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == c.getId() && ((NpcHandler.npcs[i].HP > (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
							(NpcHandler.npcs[i].HP-damage < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
							if (c.canHealersRespawn)
							FightCaves.spawnHealers(c, i, 4-c.spawnedHealers);
						}
					}
		boolean guthansEffect = false;
		if (c.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(3) == 1) {
				guthansEffect = true;
			}
		}
		if (c.fightMode == 3 && NpcHandler.npcs[i].npcType != 2459 && NpcHandler.npcs[i].npcType != 2460 && NpcHandler.npcs[i].npcType != 2461 && NpcHandler.npcs[i].npcType != 2462) {
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 0);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 1);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 2);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 3);
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(1);
			c.getPlayerAssistant().refreshSkill(2);
			c.getPlayerAssistant().refreshSkill(3);
		} else {
			if (NpcHandler.npcs[i].npcType != 2459 && NpcHandler.npcs[i].npcType != 2460 && NpcHandler.npcs[i].npcType != 2461 && NpcHandler.npcs[i].npcType != 2462) {
				c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE, c.fightMode);
				c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 3);
				c.getPlayerAssistant().refreshSkill(c.fightMode);
				c.getPlayerAssistant().refreshSkill(3);
			}
		}
		if (damage > 0) {
			if (NpcHandler.npcs[i].npcType >= 3777 && NpcHandler.npcs[i].npcType <= 3780 || PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType)) {
				c.pcDamage += damage;
			}
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			}
			c.getPlayerAssistant().refreshSkill(3);
			NpcHandler.npcs[i].gfx0(398);
		}
		NpcHandler.npcs[i].underAttack = true;
		c.killingNpcIndex = c.npcIndex;
		c.lastNpcAttacked = i;
		switch (c.specEffect) {
		case 4:
			if (damage > 0) {
				if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3])) {
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
						;
					} else {
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					}
				} else {
					c.playerLevel[3] += damage;
				}
				c.getPlayerAssistant().refreshSkill(3);
			}
			break;

		}
		switch (damageMask) {
		case 1:
			NpcHandler.npcs[i].hitDiff = damage;
			NpcHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NpcHandler.npcs[i].hitUpdateRequired = true;
			NpcHandler.npcs[i].updateRequired = true;
			break;

		case 2:
			NpcHandler.npcs[i].hitDiff2 = damage;
			NpcHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NpcHandler.npcs[i].hitUpdateRequired2 = true;
			NpcHandler.npcs[i].updateRequired = true;
			c.doubleHit = false;
			break;

		}
	}

	public void fireProjectileNpc() {
		if (c.oldNpcIndex > 0) {
			if (NpcHandler.npcs[c.oldNpcIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int nX = NpcHandler.npcs[c.oldNpcIndex].getX();
				int nY = NpcHandler.npcs[c.oldNpcIndex].getY();
				int offX = (pY - nY) * -1;
				int offY = (pX - nX) * -1;
				c.getPlayerAssistant().createPlayersProjectile(pX, pY, offX,
						offY, 50, RangeData.getProjectileSpeed(c),
						RangeData.getRangeProjectileGFX(c), 43, 31, c.oldNpcIndex + 1,
						MagicSpells.getStartDelay(c));
				if (RangeData.usingDbow(c)) {
					c.getPlayerAssistant().createPlayersProjectile2(pX, pY,
							offX, offY, 50, RangeData.getProjectileSpeed(c),
							RangeData.getRangeProjectileGFX(c), 60, 31, c.oldNpcIndex + 1,
							MagicSpells.getStartDelay(c), 35);
				}
			}
		}
	}

	public void attackNpc(int i) {
		// int equippedWeapon = c.playerEquipment[c.playerWeapon];
		// final int npcId = NPCHandler.npcs[i].npcType;
		if (NpcHandler.npcs[i] != null) {
			if (NpcHandler.npcs[i].isDead || NpcHandler.npcs[i].MaxHP <= 0) {
				c.usingMagic = false;
				c.faceUpdate(0);
				c.npcIndex = 0;
				return;
			}
			if (c.absY == 3228 && NpcHandler.npcs[i].absY == 3227) {
				resetPlayerAttack();
				return;
			}
			if (c.absY == 3224 && NpcHandler.npcs[i].absY == 3225) {
				resetPlayerAttack();
				return;
			}
			if (c.absY == 3226 && NpcHandler.npcs[i].absY == 3227) {
				resetPlayerAttack();
				return;
			}
			if (c.absY == 3228 && NpcHandler.npcs[i].absY == 3227) {
				resetPlayerAttack();
				return;
			}
			if (c.absX == 3252 && c.absY > 3254 && c.absY < 3272 || c.absY == 3254 && c.absX > 3252 && c.absX < 3265) {
				resetPlayerAttack();
				return;
			}
			if (c.usingMagic && MagicData.MAGIC_SPELLS[c.spellId][0] == 1171) {
				if (!NpcHandler.isUndead(i)) {
					c.getActionSender().sendMessage("This spell only affects skeletons, zombies, ghosts and shades.");
					resetPlayerAttack();
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
			}
			if (c.isBotting == true) {
				c.getActionSender().sendMessage("You can't attack npcs, until you confirm you are not botting.");
				c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
				resetPlayerAttack();
				return;
			}
			if (c.respawnTimer > 0) {
				c.npcIndex = 0;
				return;
			}
			if (!SlayerRequirements.itemNeededSlayer(c, i)) {
				return;
			}
			if (!c.getSlayer().canAttackNpc(i)) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 9) {
			if (c.absX == 3225 && c.absY > 3459 && c.absY < 3465 || c.absX > 3222 && c.absX < 3226 && c.absY > 3456 && c.absY < 3460 
			|| c.absX > 3213 && c.absX < 3223 && c.absY == 3457 || c.absX > 3202 && c.absX < 3212 && c.absY == 3457 
			|| c.absX > 3199 && c.absX < 3203 && c.absY > 3456 && c.absY < 3460 || c.absX == 3200 && c.absY > 3459 && c.absY < 3467) {
					resetPlayerAttack();
					return;
				}
			}
			if (c.absX == 3180 && c.absY > 3433 && c.absY < 3447) {
				resetPlayerAttack();
				return;
			}
			if (c.absX > 2837 && c.absX < 2840 && c.absY == 9772) {
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].npcType == 134 || NpcHandler.npcs[i].npcType == 1267 || NpcHandler.npcs[i].npcType == 1265) {
				if (Misc.random(350) == 0) {
					AntiBotting.botCheckInterface(c);
				}
			}
			if (NpcHandler.npcs[i].npcType == 757 && c.vampSlayer > 2) {
				if (!c.getItemAssistant().playerHasItem(1549, 1) || !c.getItemAssistant().playerHasItem(2347, 1)) {
					c.getActionSender().sendMessage("You need a stake and hammer to attack count draynor.");
					resetPlayerAttack();
					return;
				}
			}
			if (c.isWoodcutting == true) {
				c.getActionSender().sendMessage("You can't attack an npc while woodcutting.");
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].npcType == 1676) {
				c.getActionSender().sendMessage("You don't have the heart to kill the poor creature again.");
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].npcType == 411) {
				c.getActionSender().sendMessage("You can't attack a swarm!");
				resetPlayerAttack();
				return;
			}
			if (!c.goodDistance(c.getX(), c.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 1)) {
				
			}
			if (NpcHandler.npcs[i].underAttackBy > 0 && NpcHandler.npcs[i].underAttackBy != c.playerId && !NpcHandler.npcs[i].inMulti()) {
				c.npcIndex = 0;
				c.getActionSender().sendMessage("This monster is already in combat.");
				return;
			}
			if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.underAttackBy2 != i && !c.inMulti()) {
				resetPlayerAttack();
				c.getActionSender().sendMessage("I am already under attack.");
				return;
			}
			if (NpcHandler.npcs[i].spawnedBy != c.playerId && NpcHandler.npcs[i].spawnedBy > 0) {
				resetPlayerAttack();
				c.getActionSender().sendMessage("This monster was not spawned for you.");
				return;
			}
			c.followId2 = i;
			c.followId = 0;
			if (c.attackTimer <= 0) {
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;
				boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
				c.bonusAttack = 0;
				c.rangeItemUsed = 0;
				c.projectileStage = 0;
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				if (c.spellId > 0) {
					c.usingMagic = true;
				}
				c.attackTimer = getAttackDelay();
				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				if (!c.usingMagic) {
					for (int bowId : RangeData.BOWS) {
						if (c.playerEquipment[c.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : RangeData.ARROWS) {
								if (c.playerEquipment[c.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				if (armaNpc(i) && !usingCross && !usingBow && !c.usingMagic
						&& !RangeData.usingCrystalBow(c) && !usingOtherRangeWeapons) {
					resetPlayerAttack();
					return;
				}
				if (usingOtherRangeWeapons || usingBow
						&& Constants.combatSounds
						&& NpcHandler.npcs[i].npcType < 3177
						&& NpcHandler.npcs[i].npcType > 3180) {
					c.getActionSender().sendSound(SoundList.SHOOT_ARROW,
							100, 0);
				}
				if (!c.goodDistance(c.getX(), c.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 2) && RangeData.usingHally(c) && !usingOtherRangeWeapons && !usingBow && !c.usingMagic || !c.goodDistance(c.getX(), c.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 4)
						&& usingOtherRangeWeapons
						&& !usingBow
						&& !c.usingMagic
						|| !c.goodDistance(c.getX(), c.getY(),
								NpcHandler.npcs[i].getX(),
								NpcHandler.npcs[i].getY(), 1)
						&& !usingOtherRangeWeapons
						&& !RangeData.usingHally(c)
						&& !usingBow
						&& !c.usingMagic
						|| !c.goodDistance(c.getX(), c.getY(),
								NpcHandler.npcs[i].getX(),
								NpcHandler.npcs[i].getY(), 8)
						&& (usingBow || c.usingMagic)) {
					c.attackTimer = 2;
					return;
				}

				if (!usingCross
						&& !usingArrows
						&& usingBow
						&& (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223)) {
					c.getActionSender().sendMessage(
							"There is no ammo left in your quiver.");
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
				if (RangeData.correctBowAndArrows(c) < c.playerEquipment[c.playerArrows]
						&& Constants.CORRECT_ARROWS && usingBow
						&& !RangeData.usingCrystalBow(c)
						&& c.playerEquipment[c.playerWeapon] != 9185) {
					c.getItemAssistant();
					c.getItemAssistant();
					c.getActionSender().sendMessage(
							"You can't use "
									+ ItemAssistant.getItemName(
											c.playerEquipment[c.playerArrows])
											.toLowerCase()
									+ "s with a "
									+ ItemAssistant.getItemName(
											c.playerEquipment[c.playerWeapon])
											.toLowerCase() + ".");
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}

				if (c.playerEquipment[c.playerWeapon] == 9185 && !properBolts()) {
					c.getActionSender().sendMessage(
							"You must use bolts with a crossbow.");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}

				if (usingBow
						|| c.usingMagic
						|| usingOtherRangeWeapons
						|| c.goodDistance(c.getX(), c.getY(),
								NpcHandler.npcs[i].getX(),
								NpcHandler.npcs[i].getY(), 2) && RangeData.usingHally(c)) {
					c.stopMovement();
				}
				

				/**
				 * Npc projectiles
				 */
				if (PlayerAssistant.pathBlocked(c, NpcHandler.npcs[i])) {
						if((c.usingBow || c.usingMagic || usingOtherRangeWeapons || c.autocasting)) {
							PathFinder.getPathFinder().findRoute(c, NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), true, 8, 8);
						if(!c.usingBow && !c.usingMagic && !usingOtherRangeWeapons && !c.autocasting) {
							PathFinder.getPathFinder().findRoute(c,NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), true, 1, 1);
							c.attackTimer = 0;
							return;
						}
					}
				}


				if (!checkMagicReqs(c.spellId)) {
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}

				c.faceUpdate(i);
				NpcHandler.npcs[i].underAttackBy = c.playerId;
				NpcHandler.npcs[i].lastDamageTaken = System.currentTimeMillis();
				if (c.usingSpecial && !c.usingMagic) {
					if (c.getCombatAssistant().checkSpecAmount(
							c.playerEquipment[c.playerWeapon])) {
						c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						c.getSpecials().activateSpecial(
								c.playerEquipment[c.playerWeapon], i);
						return;
					} else {
						c.getActionSender()
								.sendMessage(
										"You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItemAssistant().updateSpecialBar();
						if (Constants.combatSounds) {
							c.getActionSender()
									.sendSound(
											CombatSounds
													.specialSounds(c.playerEquipment[c.playerWeapon]),
											100, 0);
						}
						c.npcIndex = 0;
						return;
					}
				}
				c.specMaxHitIncrease = 0;
				if (!c.usingMagic) {
					if (Constants.combatSounds) {
						c.getActionSender().sendSound(
								CombatSounds.getWeaponSounds(c), 100, 0);
					}
					c.startAnimation(getWepAnim());
				} else {
					if (Constants.combatSounds) {
						c.getActionSender().sendSound(
								CombatSounds.getMagicSound(c, c.spellId), 100,
								0);
					}
					c.startAnimation(MagicData.MAGIC_SPELLS[c.spellId][2]);
				}
				c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
				c.lastArrowUsed = c.playerEquipment[c.playerArrows];
				if (!usingBow && !c.usingMagic && !usingOtherRangeWeapons) { // melee
																				// hit
																				// delay
					c.hitDelay = getHitDelay();
					c.projectileStage = 0;
					c.oldNpcIndex = i;
				}

				if (usingBow && !usingOtherRangeWeapons && !c.usingMagic
						|| usingCross) { // range hit delay
					if (usingCross) {
						c.usingBow = true;
					}
					if (c.fightMode == 2) {
						c.attackTimer--;
					}
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.gfx100(RangeData.getRangeStartGFX(c));
					c.hitDelay = getHitDelay();
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					if (c.playerEquipment[c.playerWeapon] >= 4212
							&& c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						c.crystalBowArrowCount++;
						c.lastArrowUsed = 0;
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItemAssistant().deleteArrow();
					}
					fireProjectileNpc();
				}

				if (usingOtherRangeWeapons && !c.usingMagic && !usingBow) { // knives,
																			// darts,
																			// etc
																			// hit
																			// delay
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItemAssistant().deleteEquipment();
					c.gfx100(RangeData.getRangeStartGFX(c));
					c.lastArrowUsed = 0;
					c.hitDelay = getHitDelay();
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					if (c.fightMode == 2) {
						c.attackTimer--;
					}
					fireProjectileNpc();
				}

				if (c.usingMagic) { // magic hit delay
					int pX = c.getX();
					int pY = c.getY();
					int nX = NpcHandler.npcs[i].getX();
					int nY = NpcHandler.npcs[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					if (MagicData.MAGIC_SPELLS[c.spellId][3] > 0) {
						if (MagicSpells.getStartGfxHeight(c) == 100) {
							c.gfx100(MagicData.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(MagicData.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if (MagicData.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPlayerAssistant().createPlayersProjectile(pX, pY,
								offX, offY, 50, 78,
								MagicData.MAGIC_SPELLS[c.spellId][4],
								MagicSpells.getStartHeight(c), MagicSpells.getEndHeight(c), i + 1, 50);
					}
					c.hitDelay = getHitDelay();
					c.oldNpcIndex = i;
					c.oldSpellId = c.spellId;
					c.spellId = 0;
					if (!c.autocasting) {
						c.npcIndex = 0;
					}
				}

				if (usingBow && Constants.CRYSTAL_BOW_DEGRADES) { // crystal
																	// bow
																	// degrading
					if (c.playerEquipment[c.playerWeapon] == 4212) { // new
																		// crystal
																		// bow
																		// becomes
																		// full
																		// bow
																		// on
																		// the
																		// first
																		// shot
						c.getItemAssistant().wearItem(4214, 1, 3);
					}
					if (c.crystalBowArrowCount >= 250) {
						switch (c.playerEquipment[c.playerWeapon]) {

						case 4223: // 1/10 bow
							c.getItemAssistant().wearItem(-1, 1, 3);
							c.getActionSender().sendMessage(
									"Your crystal bow has fully degraded.");
							if (!c.getItemAssistant().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(c, 4207,
										c.getX(), c.getY(), 1, c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;

						default:
							c.getItemAssistant().wearItem(
									++c.playerEquipment[c.playerWeapon], 1, 3);
							c.getActionSender().sendMessage(
									"Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;

						}
					}
				}
			}
		}
	}

	/**
	 * Attack Players, same as npc tbh xD
	 **/

	public void attackPlayer(int i) {
		Client o = (Client) PlayerHandler.players[i];
		/*if (c.connectedFrom.equals(o.connectedFrom)) {
	         c.getActionSender().sendMessage("You cannot attack your self.");
	         resetPlayerAttack();
	         return;
	    }*/
		int equippedWeapon = c.playerEquipment[c.playerWeapon];

		if (PlayerHandler.players[i] != null) {
			
			   if (c.usingMagic && MagicData.MAGIC_SPELLS[c.spellId][0] == 1171) {
					c.getActionSender().sendMessage("This spell only affects skeletons, zombies, ghosts and shades, not humans.");
					resetPlayerAttack();
					c.stopMovement();
					return;
				}

			if (CastleWars.isInCw(PlayerHandler.players[i])
					&& CastleWars.isInCw(c)) {
				if (CastleWars.getTeamNumber(c) == CastleWars.getTeamNumber((Client) PlayerHandler.players[i])) {
					c.getActionSender().sendMessage("You cannot attack your own teammate.");
					resetPlayerAttack();
					return;
				}
			}

			if (!CastleWars.isInCw(PlayerHandler.players[i])
					&& CastleWars.isInCw(c)) {
				c.getActionSender().sendMessage(
						"You cannot attack people outside castle wars.");
				resetPlayerAttack();
				return;
			}

			if (PlayerHandler.players[i].isDead) {
				resetPlayerAttack();
				return;
			}

			if (c.respawnTimer > 0 || PlayerHandler.players[i].respawnTimer > 0) {
				resetPlayerAttack();
				return;
			}

			/*
			 * if( c.getPlayerAssistant().getWearingAmount() < 4 && c.duelStatus
			 * < 1 && ! c.inCw() && c.inPits == false ) {
			 * c.getPacketDispatcher().sendMessage(
			 * "You must be wearing at least 4 items to attack someone." );
			 * resetPlayerAttack(); return; }
			 */
			boolean sameSpot = c.absX == PlayerHandler.players[i].getX()
					&& c.absY == PlayerHandler.players[i].getY();
			if (!c.goodDistance(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), c.getX(), c.getY(), 25) && !sameSpot) {
				resetPlayerAttack();
				return;
			}

			if (PlayerHandler.players[i].respawnTimer > 0) {
				PlayerHandler.players[i].playerIndex = 0;
				resetPlayerAttack();
				return;
			}

			if (PlayerHandler.players[i].heightLevel != c.heightLevel) {
				resetPlayerAttack();
				return;
			}
			// c.getPacketDispatcher().sendMessage("Made it here0.");
			c.followId = i;
			c.followId2 = 0;
			if (c.attackTimer <= 0) {
				c.usingBow = false;
				c.specEffect = 0;
				c.usingRangeWeapon = false;
				c.rangeItemUsed = 0;
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;
				boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
				c.projectileStage = 0;
				if (c.absX == PlayerHandler.players[i].absX
						&& c.absY == PlayerHandler.players[i].absY) {
					if (c.freezeTimer > 0) {
						resetPlayerAttack();
						return;
					}
					c.followId = i;
					c.attackTimer = 0;
					return;
				}

				// c.getPacketDispatcher().sendMessage("Made it here1.");
				if (!c.usingMagic) {
					for (int bowId : RangeData.BOWS) {
						if (c.playerEquipment[c.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : RangeData.ARROWS) {
								if (c.playerEquipment[c.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				// c.getPacketDispatcher().sendMessage("Made it here2.");
				if (c.spellId > 0) {
					c.usingMagic = true;
				}
				c.attackTimer = getAttackDelay();

				if (c.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : Constants.FUN_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						c.getActionSender().sendMessage(
								"You can only use fun weapons in this duel!");
						resetPlayerAttack();
						return;
					}
				}
				// c.getPacketDispatcher().sendMessage("Made it here3.");
				if (c.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					c.getActionSender().sendMessage(
							"Range has been disabled in this duel!");
					return;
				}
				if (c.duelRule[3] && !usingBow && !usingOtherRangeWeapons
						&& !c.usingMagic) {
					c.getActionSender().sendMessage(
							"Melee has been disabled in this duel!");
					return;
				}

				if (c.duelRule[4] && c.usingMagic) {
					c.getActionSender().sendMessage(
							"Magic has been disabled in this duel!");
					resetPlayerAttack();
					return;
				}

				if (!c.goodDistance(c.getX(), c.getY(),
						PlayerHandler.players[i].getX(),
						PlayerHandler.players[i].getY(), 4)
						&& usingOtherRangeWeapons
						&& !usingBow
						&& !c.usingMagic
						|| !c.goodDistance(c.getX(), c.getY(),
								PlayerHandler.players[i].getX(),
								PlayerHandler.players[i].getY(), 2)
						&& !usingOtherRangeWeapons
						&& RangeData.usingHally(c)
						&& !usingBow
						&& !c.usingMagic
						|| !c.goodDistance(c.getX(), c.getY(),
								PlayerHandler.players[i].getX(),
								PlayerHandler.players[i].getY(),
								getRequiredDistance())
						&& !usingOtherRangeWeapons
						&& !RangeData.usingHally(c)
						&& !usingBow
						&& !c.usingMagic
						|| !c.goodDistance(c.getX(), c.getY(),
								PlayerHandler.players[i].getX(),
								PlayerHandler.players[i].getY(), 10)
						&& (usingBow || c.usingMagic)) {
					// c.getPacketDispatcher().sendMessage("Setting attack timer to 1");
					c.attackTimer = 1;
					if (!usingBow && !c.usingMagic && !usingOtherRangeWeapons
							&& c.freezeTimer > 0) {
						resetPlayerAttack();
					}
					return;
				}

				if (!usingCross
						&& !usingArrows
						&& usingBow
						&& (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223)
						&& !c.usingMagic) {
					c.getActionSender().sendMessage(
							"There is no ammo left in your quiver.");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (RangeData.correctBowAndArrows(c) < c.playerEquipment[c.playerArrows]
						&& Constants.CORRECT_ARROWS && usingBow
						&& !RangeData.usingCrystalBow(c)
						&& c.playerEquipment[c.playerWeapon] != 9185
						&& !c.usingMagic) {
					c.getItemAssistant();
					c.getItemAssistant();
					c.getActionSender().sendMessage(
							"You can't use "
									+ ItemAssistant.getItemName(
											c.playerEquipment[c.playerArrows])
											.toLowerCase()
									+ "s with a "
									+ ItemAssistant.getItemName(
											c.playerEquipment[c.playerWeapon])
											.toLowerCase() + ".");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (c.playerEquipment[c.playerWeapon] == 9185 && !properBolts()
						&& !c.usingMagic) {
					c.getActionSender().sendMessage(
							"You must use bolts with a crossbow.");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}

				if (usingBow || c.usingMagic || usingOtherRangeWeapons
						|| RangeData.usingHally(c)) {
					c.stopMovement();
				}

				/**
				 * Player projectiles
				 */
					if(PlayerAssistant.pathBlocked(c, o)) {
						if((c.usingBow || c.usingMagic || usingOtherRangeWeapons || c.autocasting)) {
							PathFinder.getPathFinder().findRoute(c, o.absX, o.absY, true, 8, 8);
						if(!c.usingBow && !c.usingMagic && !usingOtherRangeWeapons && !c.autocasting) {
							PathFinder.getPathFinder().findRoute(c, o.absX, o.absY, true, 1, 1);
							c.attackTimer = 0;
							return;
						}
					}
				}

				if (!checkMagicReqs(c.spellId)) {
					c.stopMovement();
					resetPlayerAttack();
					return;
				}

				c.faceUpdate(i + 32768);

				if (c.duelStatus != 5
						&& !PlayerHandler.players[c.playerIndex].inCwGame()
						&& FightPits.getState(c) == null) {
					if (!c.attackedPlayers.contains(c.playerIndex)
							&& !PlayerHandler.players[c.playerIndex].attackedPlayers
									.contains(c.playerId)) {
						c.attackedPlayers.add(c.playerIndex);
						c.isSkulled = true;
						c.skullTimer = Constants.SKULL_TIMER;
						c.headIconPk = 0;
						c.getPlayerAssistant().requestUpdates();
					}
				}
				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				c.delayedDamage = c.delayedDamage2 = 0;
				if (c.usingSpecial && !c.usingMagic) {
					if (c.duelRule[10] && c.duelStatus == 5) {
						c.getActionSender()
								.sendMessage(
										"Special attacks have been disabled during this duel!");
						c.usingSpecial = false;
						c.getItemAssistant().updateSpecialBar();
						if (Constants.combatSounds) {
							c.getActionSender()
									.sendSound(
											CombatSounds
													.specialSounds(c.playerEquipment[c.playerWeapon]),
											100, 0);
						}
						resetPlayerAttack();
						return;
					}
					if (checkSpecAmount(equippedWeapon)) {
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						c.getSpecials().activateSpecial(
								c.playerEquipment[c.playerWeapon], i);
						c.followId = c.playerIndex;
						return;
					} else {
						c.getActionSender()
								.sendMessage(
										"You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItemAssistant().updateSpecialBar();
						if (Constants.combatSounds) {
							c.getActionSender()
									.sendSound(
											CombatSounds
													.specialSounds(c.playerEquipment[c.playerWeapon]),
											100, 0);
						}
						c.playerIndex = 0;
						return;
					}
				}

				if (!c.usingMagic) {
					if (Constants.combatSounds) {
						c.getActionSender().sendSound(
								CombatSounds.getWeaponSounds(c), 100, 0);
					}
					c.startAnimation(getWepAnim());
					c.mageFollow = false;
				} else {
					c.startAnimation(MagicData.MAGIC_SPELLS[c.spellId][2]);
					if (Constants.combatSounds) {
						c.getActionSender().sendSound(
								CombatSounds.getMagicSound(c, c.spellId), 100,
								0);
					}
					c.mageFollow = true;
					c.followId = c.playerIndex;
				}
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = c.playerId;
				c.lastArrowUsed = 0;
				c.rangeItemUsed = 0;
				if (!usingBow && !c.usingMagic && !usingOtherRangeWeapons) { // melee
																				// hit
																				// delay;
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPlayerAssistant().followPlayer();
					c.hitDelay = getHitDelay();
					c.delayedDamage = Misc.random(meleeMaxHit());
					c.projectileStage = 0;
					c.oldPlayerIndex = i;
				}

				if (usingBow && !usingOtherRangeWeapons && !c.usingMagic
						|| usingCross) { // range hit delay
					if (c.playerEquipment[c.playerWeapon] >= 4212
							&& c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						c.crystalBowArrowCount++;
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItemAssistant().deleteArrow();
					}
					if (c.fightMode == 2) {
						c.attackTimer--;
					}
					if (usingCross) {
						c.usingBow = true;
					}

					c.usingBow = true;
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPlayerAssistant().followPlayer();
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.gfx100(RangeData.getRangeStartGFX(c));
					c.hitDelay = getHitDelay();
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					fireProjectilePlayer();
				}

				if (usingOtherRangeWeapons) { // knives, darts, etc hit delay
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItemAssistant().deleteEquipment();
					c.usingRangeWeapon = true;
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPlayerAssistant().followPlayer();
					c.gfx100(RangeData.getRangeStartGFX(c));
					if (c.fightMode == 2) {
						c.attackTimer--;
					}
					c.hitDelay = getHitDelay();
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					fireProjectilePlayer();
				}

				if (c.usingMagic) { // magic hit delay
					int pX = c.getX();
					int pY = c.getY();
					int nX = PlayerHandler.players[i].getX();
					int nY = PlayerHandler.players[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					if (MagicData.MAGIC_SPELLS[c.spellId][3] > 0) {
						if (MagicSpells.getStartGfxHeight(c) == 100) {
							c.gfx100(MagicData.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(MagicData.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if (MagicData.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPlayerAssistant().createPlayersProjectile(pX, pY,
								offX, offY, 50, 78,
								MagicData.MAGIC_SPELLS[c.spellId][4],
								MagicSpells.getStartHeight(c), MagicSpells.getEndHeight(c), -i - 1,
								MagicSpells.getStartDelay(c));
					}
					if (c.autocastId > 0) {
						c.followId = c.playerIndex;
						c.followDistance = 5;
					}
					c.hitDelay = getHitDelay();
					c.oldPlayerIndex = i;
					c.oldSpellId = c.spellId;
					c.spellId = 0;
					if (MagicData.MAGIC_SPELLS[c.oldSpellId][0] == 12891
							&& o.isMoving) {
						// c.getPacketDispatcher().sendMessage("Barrage projectile..");
						c.getPlayerAssistant().createPlayersProjectile(pX, pY,
								offX, offY, 50, 85, 368, 25, 25, -i - 1,
								MagicSpells.getStartDelay(c));
					}
					if (Misc.random(o.getCombatAssistant().mageDef()) > Misc
							.random(mageAtk())) {
						c.magicFailed = true;
					} else {
						c.magicFailed = false;
					}
					int freezeDelay = MagicSpells.getFreezeTime(c);// freeze time
					if (freezeDelay > 0
							&& PlayerHandler.players[i].freezeTimer <= -3
							&& !c.magicFailed) {
						PlayerHandler.players[i].freezeTimer = freezeDelay;
						o.resetWalkingQueue();
						o.getActionSender().sendMessage(
								"You have been frozen.");
						o.frozenBy = c.playerId;
					}
					if (!checkReqs()) {
						return;
					}
					if (!c.autocasting && c.spellId <= 0) {
						c.playerIndex = 0;
					}
				}

				if (usingBow && Constants.CRYSTAL_BOW_DEGRADES) { // crystal
																	// bow
																	// degrading
					if (c.playerEquipment[c.playerWeapon] == 4212) { // new
																		// crystal
																		// bow
																		// becomes
																		// full
																		// bow
																		// on
																		// the
																		// first
																		// shot
						c.getItemAssistant().wearItem(4214, 1, 3);
					}
					if (c.crystalBowArrowCount >= 250) {
						switch (c.playerEquipment[c.playerWeapon]) {

						case 4223: // 1/10 bow
							c.getItemAssistant().wearItem(-1, 1, 3);
							c.getActionSender().sendMessage(
									"Your crystal bow has fully degraded.");
							if (!c.getItemAssistant().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(c, 4207,
										c.getX(), c.getY(), 1, c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;

						default:
							c.getItemAssistant().wearItem(
									++c.playerEquipment[c.playerWeapon], 1, 3);
							c.getActionSender().sendMessage(
									"Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}

	public void appendVengeance(int otherPlayer, int damage) {
		if (damage <= 0) {
			return;
		}
		Player o = PlayerHandler.players[otherPlayer];
		o.forcedText = "Taste Vengeance!";
		o.forcedChatUpdateRequired = true;
		o.updateRequired = true;
		o.vengOn = false;
		if (o.playerLevel[3] - damage > 0) {
			damage = (int) (damage * 0.75);
			if (damage > c.playerLevel[3]) {
				damage = c.playerLevel[3];
			}
			c.setHitDiff2(damage);
			c.setHitUpdateRequired2(true);
			c.playerLevel[3] -= damage;
			c.getPlayerAssistant().refreshSkill(3);
		}
		c.updateRequired = true;
	}

	public void playerDelayedHit(int i) {
		if (PlayerHandler.players[i] != null) {
			if (PlayerHandler.players[i].isDead || c.isDead
					|| PlayerHandler.players[i].playerLevel[3] <= 0
					|| c.playerLevel[3] <= 0) {
				c.playerIndex = 0;
				return;
			}
			if (PlayerHandler.players[i].respawnTimer > 0) {
				c.faceUpdate(0);
				c.playerIndex = 0;
				return;
			}
			Client o = (Client) PlayerHandler.players[i];
			o.getPlayerAssistant().removeAllWindows();
			if (o.playerIndex <= 0 && o.npcIndex <= 0) {
				if (o.autoRet == 1) {
					o.playerIndex = c.playerId;
				}
			}
			if (o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0
					&& !c.castingMagic) { // block animation
				o.startAnimation(o.getCombatAssistant().getBlockEmote());
				if (Constants.combatSounds) {
					o.getActionSender().sendSound(
							CombatSounds.getPlayerBlockSounds(o), 100, 0);
				}
			}
			if (o.inTrade) {
				o.getTrading().declineTrade();
			}
			if (c.projectileStage == 0) { // melee hit damage
				applyPlayerMeleeDamage(i, 1);
				if (c.doubleHit) {
					applyPlayerMeleeDamage(i, 2);
				}
			}
			if (!c.castingMagic && c.projectileStage > 0) { // range hit
															// damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					damage2 = Misc.random(rangeMaxHit());
				}
				boolean ignoreDef = false;
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9243) {
					ignoreDef = true;
					o.gfx0(758);
				}
				if (Misc.random(10 + o.getCombatAssistant()
						.calculateRangeDefence()) > Misc
						.random(10 + calculateRangeAttack())
						&& !ignoreDef) {
					damage = 0;
				}
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9242
						&& damage > 0) {
					PlayerHandler.players[i].gfx0(754);
					damage = NpcHandler.npcs[i].HP / 5;
					c.handleHitMask(c.playerLevel[3] / 10);
					c.dealDamage(c.playerLevel[3] / 10);
					c.gfx0(754);
				}

				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					if (Misc.random(10 + o.getCombatAssistant()
							.calculateRangeDefence()) > Misc
							.random(10 + calculateRangeAttack())) {
						damage2 = 0;
					}
				}

				if (c.dbowSpec) {
					o.gfx100(1100);
					if (damage < 8) {
						damage = 8;
					}
					if (damage2 < 8) {
						damage2 = 8;
					}
					c.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1
						&& c.lastArrowUsed == 9244) {
					damage *= 1.45;
					o.gfx0(756);
				}
				if (o.getPrayer().prayerActive[17]
						&& System.currentTimeMillis() - o.protRangeDelay > 1500) { // if
																					// prayer
																					// active
																					// reduce
																					// damage
																					// by
																					// half
					damage = damage * 60 / 100;
					if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
						damage2 = damage2 * 60 / 100;
					}
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage - damage2 < 0) {
					damage2 = PlayerHandler.players[i].playerLevel[3] - damage;
				}
				if (damage < 0) {
					damage = 0;
				}
				if (damage2 < 0 && damage2 != -1) {
					damage2 = 0;
				}
				if (o.vengOn) {
					appendVengeance(i, damage);
					appendVengeance(i, damage2);
				}
				if (damage > 0) {
					applyRecoil(c, damage, i);
				}
				if (damage2 > 0) {
					applyRecoil(c, damage2, i);
				}
				if (c.fightMode == 3) {
					c.getPlayerAssistant().addSkillXP(damage * Constants.RANGE_EXP_RATE / 3, 4);
					c.getPlayerAssistant().addSkillXP(damage / 3, 1);
					c.getPlayerAssistant().addSkillXP(damage / 3, 3);
					c.getPlayerAssistant().refreshSkill(1);
					c.getPlayerAssistant().refreshSkill(3);
					c.getPlayerAssistant().refreshSkill(4);
				} else {
					c.getPlayerAssistant().addSkillXP(damage * Constants.RANGE_EXP_RATE, 4);
					c.getPlayerAssistant().addSkillXP(damage / 3, 3);
					c.getPlayerAssistant().refreshSkill(3);
					c.getPlayerAssistant().refreshSkill(4);
				}
				boolean dropArrows = true;

				for (int noArrowId : RangeData.NO_ARROW_DROP) {
					if (c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					c.getItemAssistant().dropArrowPlayer();
				}
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = c.playerId;
				// Server.playerHandler.players[i].setHitDiff(damage);
				// Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage(damage);
				PlayerHandler.players[i].damageTaken[c.playerId] += damage;
				c.killedBy = PlayerHandler.players[i].playerId;
				PlayerHandler.players[i].handleHitMask(damage);
				if (damage2 != -1) {
					// Server.playerHandler.players[i].playerLevel[3] -=
					// damage2;
					PlayerHandler.players[i].dealDamage(damage2);
					PlayerHandler.players[i].damageTaken[c.playerId] += damage2;
					PlayerHandler.players[i].handleHitMask(damage2);

				}
				o.getPlayerAssistant().refreshSkill(3);

				// Server.playerHandler.players[i].setHitUpdateRequired(true);
				PlayerHandler.players[i].updateRequired = true;
				applySmite(i, damage);
				if (damage2 != -1) {
					applySmite(i, damage2);
				}

			} else if (c.projectileStage > 0) { // magic hit damage
				int damage = Misc
						.random(MagicData.MAGIC_SPELLS[c.oldSpellId][6]);
				if (MagicSpells.godSpells(c)) {
					if (System.currentTimeMillis() - c.godSpellDelay < Constants.GOD_SPELL_CHARGE) {
						damage += 10;
					}
				}
				// c.playerIndex = 0;
				if (c.magicFailed) {
					damage = 0;
				}

				if (o.getPrayer().prayerActive[16]
						&& System.currentTimeMillis() - o.protMageDelay > 1500) { // if
																					// prayer
																					// active
																					// reduce
																					// damage
																					// by
																					// half
					damage = damage * 60 / 100;
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (o.vengOn) {
					appendVengeance(i, damage);
				}
				if (damage > 0) {
					applyRecoil(c, damage, i);
				}
				c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[c.oldSpellId][7] + damage * Constants.MAGIC_EXP_RATE, 6);
				if (MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1161 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1153 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1157 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1542 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1543 && MagicData.MAGIC_SPELLS[c.oldSpellId][0] != 1562) {
					c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[c.oldSpellId][7] + damage / 3, 3);
				}
				c.getPlayerAssistant().refreshSkill(3);
				c.getPlayerAssistant().refreshSkill(6);

				if (MagicSpells.getEndGfxHeight(c) == 100 && !c.magicFailed) { // end GFX
					PlayerHandler.players[i]
							.gfx100(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!c.magicFailed) {
					PlayerHandler.players[i]
							.gfx0(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (c.magicFailed) {
					PlayerHandler.players[i].gfx100(85);
					c.getActionSender().sendSound(SoundList.MAGE_FAIL, 100,
							0);
				}

				if (!c.magicFailed) {
					if (System.currentTimeMillis()
							- PlayerHandler.players[i].reduceStat > 35000) {
						PlayerHandler.players[i].reduceStat = System
								.currentTimeMillis();
						switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
						case 12987:
						case 13011:
						case 12999:
						case 13023:
							PlayerHandler.players[i].playerLevel[0] -= o
									.getPlayerAssistant()
									.getLevelForXP(
											PlayerHandler.players[i].playerXP[0]) * 10 / 100;
							break;
						}
					}

					switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
					case 12445: // teleblock
						if (System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength) {
							o.teleBlockDelay = System.currentTimeMillis();
							o.getActionSender().sendMessage(
									"You have been teleblocked.");
							o.getActionSender().sendSound(
									SoundList.TELEBLOCK_HIT, 100, 0);
							if (o.getPrayer().prayerActive[16]
									&& System.currentTimeMillis()
											- o.protMageDelay > 1500) {
								o.teleBlockLength = 150000;
							} else {
								o.teleBlockLength = 300000;
							}
						}
						break;

					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = damage / 4;
						if (c.playerLevel[3] + heal > c.getPlayerAssistant()
								.getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPlayerAssistant()
									.getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPlayerAssistant().refreshSkill(3);
						break;

					case 1153:
						PlayerHandler.players[i].playerLevel[0] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[0]) * 5 / 100;
						o.getActionSender().sendMessage(
								"Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;

					case 1157:
						PlayerHandler.players[i].playerLevel[2] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[2]) * 5 / 100;
						o.getActionSender().sendMessage(
								"Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(2);
						break;

					case 1161:
						PlayerHandler.players[i].playerLevel[1] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[1]) * 5 / 100;
						o.getActionSender().sendMessage(
								"Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(1);
						break;

					case 1542:
						PlayerHandler.players[i].playerLevel[1] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[1]) * 10 / 100;
						o.getActionSender().sendMessage(
								"Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(1);
						break;

					case 1543:
						PlayerHandler.players[i].playerLevel[2] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[2]) * 10 / 100;
						o.getActionSender().sendMessage(
								"Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(2);
						break;

					case 1562:
						PlayerHandler.players[i].playerLevel[0] -= o
								.getPlayerAssistant().getLevelForXP(
										PlayerHandler.players[i].playerXP[0]) * 10 / 100;
						o.getActionSender().sendMessage(
								"Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
								.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;
					}
				}

				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].killerId = c.playerId;
				PlayerHandler.players[i].singleCombatDelay = System
						.currentTimeMillis();
				if (MagicData.MAGIC_SPELLS[c.oldSpellId][6] != 0) {
					// Server.playerHandler.players[i].playerLevel[3] -= damage;
					PlayerHandler.players[i].dealDamage(damage);
					PlayerHandler.players[i].damageTaken[c.playerId] += damage;
					c.totalPlayerDamageDealt += damage;
					if (!c.magicFailed) {
						// Server.playerHandler.players[i].setHitDiff(damage);
						// Server.playerHandler.players[i].setHitUpdateRequired(true);
						PlayerHandler.players[i].handleHitMask(damage);
					}
				}
				applySmite(i, damage);
				c.killedBy = PlayerHandler.players[i].playerId;
				o.getPlayerAssistant().refreshSkill(3);
				PlayerHandler.players[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				if (o.inMulti() && MagicSpells.multis(c)) {
					c.barrageCount = 0;
					for (int j = 0; j < PlayerHandler.players.length; j++) {
						if (PlayerHandler.players[j] != null) {
							if (j == o.playerId) {
								continue;
							}
							if (c.barrageCount >= 9) {
								break;
							}
							if (o.goodDistance(o.getX(), o.getY(),
									PlayerHandler.players[j].getX(),
									PlayerHandler.players[j].getY(), 1)) {
								MagicSpells.appendMultiBarrage(c, j, c.magicFailed);
							}
						}
					}
				}
				c.getPlayerAssistant().refreshSkill(3);
				c.getPlayerAssistant().refreshSkill(6);
				c.oldSpellId = 0;
			}
		}
		c.getPlayerAssistant().requestUpdates();
		if (c.bowSpecShot <= 0) {
			c.oldPlayerIndex = 0;
			c.projectileStage = 0;
			c.lastWeaponUsed = 0;
			c.doubleHit = false;
			c.bowSpecShot = 0;
		}
		if (c.bowSpecShot != 0) {
			c.bowSpecShot = 0;
		}
	}

	public void applyPlayerMeleeDamage(int i, int damageMask) {
		Client o = (Client) PlayerHandler.players[i];
		if (o == null) {
			return;
		}
		int damage = 0;
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if (c.getPlayerAssistant().fullVeracs()) {
			if (Misc.random(4) == 1) {
				veracsEffect = true;
			}
		}
		if (c.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(4) == 1) {
				guthansEffect = true;
			}
		}
		if (damageMask == 1) {
			damage = c.delayedDamage;
			c.delayedDamage = 0;
		} else {
			damage = c.delayedDamage2;
			c.delayedDamage2 = 0;
		}
		if (Misc.random(o.getCombatAssistant().calcDef()) > Misc
				.random(calcAtt()) && !veracsEffect) {
			damage = 0;
			c.bonusAttack = 0;
		} else if (c.playerEquipment[c.playerWeapon] == 5698
				&& o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPlayerAssistant().appendPoison(13);
			c.bonusAttack += damage / 3;
		} else {
			c.bonusAttack += damage / 3;
		}
		if (o.getPrayer().prayerActive[18]
				&& System.currentTimeMillis() - o.protMeleeDelay > 1500
				&& !veracsEffect) { // if prayer active reduce damage by 40%
			damage = damage * 60 / 100;
		}
		if (c.maxNextHit) {
			damage = meleeMaxHit();
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			}
			c.getPlayerAssistant().refreshSkill(3);
			o.gfx0(398);
		}
		if (c.ssSpec && damageMask == 2) {
			damage = 5 + Misc.random(11);
			c.ssSpec = false;
		}
		if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
			damage = PlayerHandler.players[i].playerLevel[3];
		}
		if (o.vengOn && damage > 0) {
			appendVengeance(i, damage);
		}
		if (damage > 0) {
			applyRecoil(c, damage, i);
		}
		switch (c.specEffect) {
		case 1: // dragon scimmy special
			if (damage > 0) {
				if (o.getPrayer().prayerActive[16]
						|| o.getPrayer().prayerActive[17]
						|| o.getPrayer().prayerActive[18]) {
					o.headIcon = -1;
					o.getPlayerAssistant().sendConfig(
							c.getPrayer().PRAYER_GLOW[16], 0);
					o.getPlayerAssistant().sendConfig(
							c.getPrayer().PRAYER_GLOW[17], 0);
					o.getPlayerAssistant().sendConfig(
							c.getPrayer().PRAYER_GLOW[18], 0);
				}
				o.getActionSender().sendMessage("You have been injured!");
				o.getPrayer().stopPrayerDelay = System.currentTimeMillis();
				o.getPrayer().prayerActive[16] = false;
				o.getPrayer().prayerActive[17] = false;
				o.getPrayer().prayerActive[18] = false;
				o.getPlayerAssistant().requestUpdates();
			}
			break;
		case 2:
			if (damage > 0) {
				if (o.freezeTimer <= 0) {
					o.freezeTimer = 30;
				}
				o.gfx0(369);
				o.getActionSender().sendMessage("You have been frozen.");
				o.frozenBy = c.playerId;
				o.stopMovement();
				c.getActionSender().sendMessage("You freeze your enemy.");
			}
			break;
		case 3:
			if (damage > 0) {
				o.playerLevel[1] -= damage;
				o.getActionSender().sendMessage("You feel weak.");
				if (o.playerLevel[1] < 1) {
					o.playerLevel[1] = 1;
				}
				o.getPlayerAssistant().refreshSkill(1);
			}
			break;
		case 4:
			if (damage > 0) {
				if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3])) {
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
						;
					} else {
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					}
				} else {
					c.playerLevel[3] += damage;
				}
				c.getPlayerAssistant().refreshSkill(3);
			}
			break;
		}
		c.specEffect = 0;
		if (c.fightMode == 3) {//melee shared
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 0);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 1);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE / 3, 2);
			c.getPlayerAssistant().addSkillXP(damage / 3, 3);
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(1);
			c.getPlayerAssistant().refreshSkill(2);
			c.getPlayerAssistant().refreshSkill(3);
		} else {
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE, c.fightMode);
			c.getPlayerAssistant().addSkillXP(damage * Constants.MELEE_EXP_RATE/3, 3);
			c.getPlayerAssistant().refreshSkill(c.fightMode);
			c.getPlayerAssistant().refreshSkill(3);
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = c.playerId;
		PlayerHandler.players[i].killerId = c.playerId;
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if (c.killedBy != PlayerHandler.players[i].playerId) {
			c.totalPlayerDamageDealt = 0;
		}
		c.killedBy = PlayerHandler.players[i].playerId;
		applySmite(i, damage);
		switch (damageMask) {
		case 1:
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			o.getPlayerAssistant().refreshSkill(3);
			break;

		case 2:
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			c.doubleHit = false;
			o.getPlayerAssistant().refreshSkill(3);
			break;
		}
		PlayerHandler.players[i].handleHitMask(damage);
	}

	public void applySmite(int index, int damage) {
		if (!c.getPrayer().prayerActive[23]) {
			return;
		}
		if (damage <= 0) {
			return;
		}
		if (PlayerHandler.players[index] != null) {
			Client c2 = (Client) PlayerHandler.players[index];
			c2.playerLevel[5] -= damage / 4;
			if (c2.playerLevel[5] <= 0) {
				c2.playerLevel[5] = 0;
				PrayerDrain.resetPrayers(c2);
			}
			c2.getPlayerAssistant().refreshSkill(5);
		}

	}

	public void fireProjectilePlayer() {
		if (c.oldPlayerIndex > 0) {
			if (PlayerHandler.players[c.oldPlayerIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int oX = PlayerHandler.players[c.oldPlayerIndex].getX();
				int oY = PlayerHandler.players[c.oldPlayerIndex].getY();
				int offX = (pY - oY) * -1;
				int offY = (pX - oX) * -1;
				if (!c.msbSpec) {
					c.getPlayerAssistant().createPlayersProjectile(pX, pY,
							offX, offY, 50, RangeData.getProjectileSpeed(c),
							RangeData.getRangeProjectileGFX(c), 43, 31,
							-c.oldPlayerIndex - 1, MagicSpells.getStartDelay(c));
				} else if (c.msbSpec) {
					c.getPlayerAssistant().createPlayersProjectile2(pX, pY,
							offX, offY, 50, RangeData.getProjectileSpeed(c),
							RangeData.getRangeProjectileGFX(c), 43, 31,
							-c.oldPlayerIndex - 1, MagicSpells.getStartDelay(c), 10);
					c.msbSpec = false;
				}
				if (RangeData.usingDbow(c)) {
					c.getPlayerAssistant().createPlayersProjectile2(pX, pY,
							offX, offY, 50, RangeData.getProjectileSpeed(c),
							RangeData.getRangeProjectileGFX(c), 60, 31,
							-c.oldPlayerIndex - 1, MagicSpells.getStartDelay(c), 35);
				}
			}
		}
	}

	public void resetPlayerAttack() {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.getPlayerAssistant().resetFollow();
		// c.getPacketDispatcher().sendMessage("Reset attack.");
	}

	public int getCombatDifference(int combat1, int combat2) {
		if (combat1 > combat2) {
			return combat1 - combat2;
		}
		if (combat2 > combat1) {
			return combat2 - combat1;
		}
		return 0;
	}

	/**
	 * Wildy and duel info
	 **/

	public boolean checkReqs() {
		if (PlayerHandler.players[c.playerIndex] == null) {
			return false;
		}
		if (c.inCw()) {
			return true;
		}
		if (c.playerIndex == c.playerId) {
			return false;
		}
		if (c.inPits && PlayerHandler.players[c.playerIndex].inPits) {
			return true;
		}
		if (PlayerHandler.players[c.playerIndex].inDuelArena() && c.duelStatus != 5 && !c.usingMagic) {
			if (c.duelingArena() || c.duelStatus == 5) {
				c.getActionSender().sendMessage("You can't challenge inside the arena!");
				return false;
			}
			c.getDueling().requestDuel(c.playerIndex);
			return false;
		}
		if (c.duelStatus == 5
				&& PlayerHandler.players[c.playerIndex].duelStatus == 5) {
			if (PlayerHandler.players[c.playerIndex].duelingWith == c.getId()) {
				return true;
			} else {
				c.getActionSender()
						.sendMessage("This isn't your opponent!");
				return false;
			}
		}
		if (CastOnOther.castOnOtherSpells(c)) {
			return true;
		}
		if (!PlayerHandler.players[c.playerIndex].inWild()
				&& !PlayerHandler.players[c.playerIndex].inCwGame()
				&& !CastOnOther.castOnOtherSpells(c)) {
			c.getActionSender().sendMessage(
					"That player is not in the wilderness.");
			c.stopMovement();
			resetPlayerAttack();
			return false;
		}
		if (!c.inWild() && !PlayerHandler.players[c.playerIndex].inCwGame()
				&& !CastOnOther.castOnOtherSpells(c)) {
			c.getActionSender().sendMessage(
					"You are not in the wilderness.");
			c.stopMovement();
			resetPlayerAttack();
			return false;
		}
		if (Constants.COMBAT_LEVEL_DIFFERENCE && !c.inCw()) {
			int combatDif1 = getCombatDifference(c.combatLevel,
					PlayerHandler.players[c.playerIndex].combatLevel);
			if (combatDif1 > c.wildLevel
					|| combatDif1 > PlayerHandler.players[c.playerIndex].wildLevel) {
				c.getActionSender()
						.sendMessage(
								"Your combat level difference is too great to attack that player here.");
				c.stopMovement();
				resetPlayerAttack();
				return false;
			}
		}

		if (Constants.SINGLE_AND_MULTI_ZONES) {
			if (!PlayerHandler.players[c.playerIndex].inMulti()) { // single
																	// combat
																	// zones
				if (PlayerHandler.players[c.playerIndex].underAttackBy != c.playerId
						&& PlayerHandler.players[c.playerIndex].underAttackBy != 0) {
					c.getActionSender().sendMessage(
							"That player is already in combat.");
					c.stopMovement();
					resetPlayerAttack();
					return false;
				}
				if (PlayerHandler.players[c.playerIndex].playerId != c.underAttackBy
						&& c.underAttackBy != 0 || c.underAttackBy2 > 0) {
					c.getActionSender().sendMessage(
							"You are already in combat.");
					c.stopMovement();
					resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}

	public int getRequiredDistance() {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving) {
			return 2;
		} else if (c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}

	public void handleDfs() {
		if (System.currentTimeMillis() - c.dfsDelay > 30000) {
			if (c.playerIndex > 0
					&& PlayerHandler.players[c.playerIndex] != null) {
				int damage = Misc.random(15) + 5;
				c.startAnimation(2836);
				c.gfx0(600);
				PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
				PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
				PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[c.playerIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();
			} else {
				c.getActionSender().sendMessage(
						"I should be in combat before using this.");
			}
		} else {
			c.getActionSender().sendMessage(
					"My shield hasn't finished recharging yet.");
		}
	}

	public void handleDfsNPC() {
		if (System.currentTimeMillis() - c.dfsDelay > 30000) {
			if (c.npcIndex > 0 && NpcHandler.npcs[c.npcIndex] != null) {
				int damage = Misc.random(15) + 5;
				c.startAnimation(2836);
				c.gfx0(600);
				NpcHandler.npcs[c.npcIndex].HP -= damage;
				NpcHandler.npcs[c.npcIndex].hitDiff2 = damage;
				NpcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
				NpcHandler.npcs[c.npcIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();
			} else {
				c.getActionSender().sendMessage(
						"I should be in combat before using this.");
			}
		} else {
			c.getActionSender().sendMessage(
					"My shield hasn't finished recharging yet.");
		}
	}

	/*
	 * public void applyRecoil(int damage, int i) { if (damage > 0 &&
	 * PlayerHandler.players[i].playerEquipment[c.playerRing] == 2550) { int
	 * recDamage = damage / 10 + 1; if (!c.getHitUpdateRequired()) {
	 * c.setHitDiff(recDamage); c.setHitUpdateRequired(true); } else if
	 * (!c.getHitUpdateRequired2()) { c.setHitDiff2(recDamage);
	 * c.setHitUpdateRequired2(true); } c.dealDamage(recDamage);
	 * c.updateRequired = true; } }
	 */

	public static void applyRecoilNPC(Client c, int damage, int i) {
		if (c == null || c.npcIndex == 0) {
			return;
		}
		if (damage > 0 && c.playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			NpcHandler.npcs[c.npcIndex].HP -= recDamage;
			NpcHandler.npcs[c.npcIndex].handleHitMask(recDamage);
			removeRecoil(c);
			c.recoilHits += damage;
		}
	}

	public static void applyRecoil(Client c, int damage, int i) {
		if (damage > 0
				&& PlayerHandler.players[i].playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
				c.setHitUpdateRequired(true);
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
			removeRecoil(c);
			c.recoilHits += damage;
		}
	}

	public static void removeRecoil(Client c) {
		if (c.recoilHits >= 400) {
			c.getItemAssistant().removeItem(2550, c.playerRing);
			c.getItemAssistant().deleteItem(2550,
					c.getItemAssistant().getItemSlot(2550), 1);
			c.getActionSender().sendMessage("Your ring of recoil shaters!");
			c.recoilHits = 0;
		} else {
			c.recoilHits++;
		}
	}

	public int getBonusAttack(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case 2883:
			return Misc.random(50) + 30;
		case 2026:
		case 2027:
		case 2029:
		case 2030:
			return Misc.random(50) + 30;
		}
		return 0;
	}

	public void handleGmaulPlayer() {
		int equippedWeapon = c.playerEquipment[c.playerWeapon];
		if (c.playerIndex > 0) {
			Client o = (Client) PlayerHandler.players[c.playerIndex];
			if (c.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(),
					getRequiredDistance())) {
				if (checkReqs()) {
					if (checkSpecAmount(equippedWeapon)) {
						boolean hit = Misc.random(calcAtt()) > Misc.random(o
								.getCombatAssistant().calcDef());
						int damage = 0;
						if (hit) {
							damage = Misc.random(meleeMaxHit());
						}
						if (o.getPrayer().prayerActive[18]
								&& System.currentTimeMillis()
										- o.protMeleeDelay > 1500) {
							damage *= .6;
						}
						o.handleHitMask(damage);
						c.startAnimation(1667);
						c.gfx100(337);
						o.dealDamage(damage);
					}
				}
			}
		}
	}

	public boolean armaNpc(int i) {
		switch (NpcHandler.npcs[i].npcType) {
		case 2558:
		case 2559:
		case 2560:
		case 2561:
			return true;
		}
		return false;
	}

	public void activateSpecial(int weapon, int i) {
		c.getSpecials().activateSpecial(weapon, i);
	}

	public boolean checkSpecAmount(int weapon) {
		if (c.specAmount >= c.getSpecials().specAmount()) {
			c.specAmount -= c.getSpecials().specAmount();
			c.getItemAssistant().addSpecialBar(weapon);
			return true;
		}
		return false;
	}

	public int meleeMaxHit() {
		return MeleeMaxHit.calculateMeleeMaxHit(c);
	}

	public int calcDef() {
		return MeleeData.calculateMeleeDefence(c);
	}

	public int calcAtt() {
		return MeleeData.calculateMeleeAttack(c);
	}

	public void getPlayerAnimIndex() {
		MeleeData.getPlayerAnimIndex(c);
	}

	public int getHitDelay() {
		return MeleeData.getHitDelay(c);
	}

	public int getAttackDelay() {
		return MeleeData.getAttackDelay(c);
	}

	public int getWepAnim() {
		return MeleeData.getWeaponAnimation(c);
	}

	public int getBlockEmote() {
		return MeleeData.getBlockEmote(c);
	}

	public int rangeMaxHit() {
		return RangeMaxHit.rangeMaxHit(c);
	}

	public boolean checkMagicReqs(int spell) {
		return MagicRequirements.checkMagicReqs(c, spell);
	}

	public int calculateRangeDefence() {
		return RangeMaxHit.calculateRangeDefence(c);
	}

	public int calculateRangeAttack() {
		return RangeMaxHit.calculateRangeAttack(c);
	}

	public boolean usingBolts() {
		return RangeData.usingBolts(c);
	}

	public boolean properBolts() {
		return RangeData.properBolts(c);
	}

	public int mageDef() {
		return MagicMaxHit.mageDefenceBonus(c);
	}

	public int mageAtk() {
		return MagicMaxHit.mageAttackBonus(c);
	}

}
