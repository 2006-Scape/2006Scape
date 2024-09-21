package com.rs2.game.content.combat;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.game.content.combat.magic.CastOnOther;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.magic.MagicMaxHit;
import com.rs2.game.content.combat.magic.MagicRequirements;
import com.rs2.game.content.combat.magic.MagicSpells;
import com.rs2.game.content.combat.melee.MeleeData;
import com.rs2.game.content.combat.melee.MeleeMaxHit;
import com.rs2.game.content.combat.npcs.NpcEmotes;
import com.rs2.game.content.combat.prayer.PrayerDrain;
import com.rs2.game.content.combat.range.RangeData;
import com.rs2.game.content.combat.range.RangeMaxHit;
import com.rs2.game.content.minigames.FightCaves;
import com.rs2.game.content.minigames.FightPits;
import com.rs2.game.content.minigames.PestControl;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.music.sound.CombatSounds;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.skills.slayer.SlayerRequirements;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.Boundary;
import com.rs2.world.clip.PathFinder;

/**
 * @author whoever contributed
 * @author Andrew (Mr Extremez)
 */

public class CombatAssistant {

	private final Player player;

	public CombatAssistant(Player player2) {
		player = player2;
	}

	public boolean inCombat() {
		return (player.underAttackBy > 0 || player.underAttackBy2 > 0);
	}

	public void delayedHit(int i) { // npc hit delay
		if (NpcHandler.npcs[i] != null) {
			if (NpcHandler.npcs[i].isDead) {
				player.npcIndex = 0;
				return;
			}
			if (NpcHandler.npcs[i].attackTimer <= 3 || NpcHandler.npcs[i].attackTimer == 0 && NpcHandler.npcs[i].hitDelayTimer > 0 && !player.castingMagic) { // block animation
				NpcHandler.npcs[i].animNumber = NpcEmotes.getBlockEmote(i); // block emote
				NpcHandler.npcs[i].animUpdateRequired = true;
				NpcHandler.npcs[i].updateRequired = true;
			}
			if (CombatConstants.COMBAT_SOUNDS) {
				if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
					return;
				}
				player.getPacketSender().sendSound(CombatSounds.getNpcBlockSound(NpcHandler.npcs[player.oldNpcIndex].npcType), 100, 0);
			}
			NpcHandler.npcs[i].facePlayer(player);
			if (NpcHandler.npcs[i].underAttackBy > 0 && GameEngine.npcHandler.getsPulled(player, i)) {
				NpcHandler.npcs[i].killerId = player.playerId;
			} else if (NpcHandler.npcs[i].underAttackBy < 0 && !GameEngine.npcHandler.getsPulled(player, i)) {
				NpcHandler.npcs[i].killerId = player.playerId;
			}
			player.lastNpcAttacked = i;
			if (player.projectileStage == 0) { // melee hit damage
				applyNpcMeleeDamage(i, 1);
				if (player.doubleHit) {
					applyNpcMeleeDamage(i, 2);
				}
			}
			if (!player.castingMagic && player.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					damage2 = Misc.random(rangeMaxHit());
				}
				boolean ignoreDef = false;
				if (Misc.random(5) == 1 && player.lastArrowUsed == 9243) {
					ignoreDef = true;
					NpcHandler.npcs[i].gfx0(758);
				}
				if (Misc.random(NpcHandler.npcs[i].defence) > Misc.random(10 + calculateRangeAttack()) && !ignoreDef
					|| (NpcHandler.npcs[i].npcType == 2881 || NpcHandler.npcs[i].npcType == 2883 && !ignoreDef)) {
					damage = 0;
				}
				if (Misc.random(4) == 1 && player.lastArrowUsed == 9242 && damage > 0) {
					NpcHandler.npcs[i].gfx0(754);
					damage = NpcHandler.npcs[i].HP / 5;
					player.handleHitMask(player.playerLevel[Constants.HITPOINTS] / 10);
					player.dealDamage(player.playerLevel[Constants.HITPOINTS] / 10);
					player.gfx0(754);
				}
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					if (Misc.random(NpcHandler.npcs[i].defence) > Misc.random(10 + calculateRangeAttack())) {
						damage2 = 0;
					}
				}
				if (damage > 0 && Misc.random(5) == 1 && player.lastArrowUsed == 9244) {
					damage *= 1.45;
					NpcHandler.npcs[i].gfx0(756);
				}
				if (NpcHandler.npcs[i].HP - damage < 0) {
					damage = NpcHandler.npcs[i].HP;
				}
				if (NpcHandler.npcs[i].HP - damage <= 0 && damage2 > 0) {
					damage2 = 0;
				}
				player.globalDamageDealt += damage;
				if (damage2 > 0) {
					player.globalDamageDealt += damage2;
				}
				if (player.fightMode == 3) {//range shared [long]
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE / 2, 4);
					player.getPlayerAssistant().addSkillXP(damage / 2, 1);
					player.getPlayerAssistant().addSkillXP(damage / 3, 3);
					player.getPlayerAssistant().refreshSkill(Constants.DEFENCE);//defense
					player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);//hp
					player.getPlayerAssistant().refreshSkill(Constants.RANGED);//range
				} else {
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE, 4);
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE /3, 3);
					player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
					player.getPlayerAssistant().refreshSkill(Constants.RANGED);
				}
				if (damage > 0) {
					if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
						player.pcDamage += damage;
					}
				}
				boolean dropArrows = true;
				for (int noArrowId : RangeData.NO_ARROW_DROP) {
					if (player.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					player.getItemAssistant().dropArrowNpc();
				}
				if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == player.getId() && ((NpcHandler.npcs[i].HP < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
					(NpcHandler.npcs[i].HP-damage+(damage2 > -1 ? damage2 : 0) < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
					if (player.canHealersRespawn) {
						FightCaves.spawnHealers(player, i, 4-player.spawnedHealers);
					}
				}
				NpcHandler.npcs[i].underAttack = true;
				NpcHandler.npcs[i].hitDiff = damage;
				NpcHandler.npcs[i].HP -= damage;
				if (damage2 > -1) {
					NpcHandler.npcs[i].hitDiff2 = damage2;
					NpcHandler.npcs[i].HP -= damage2;
					player.totalDamageDealt += damage2;
				}
				if (player.killingNpcIndex != player.oldNpcIndex) {
					player.totalDamageDealt = 0;
				}
				player.killingNpcIndex = player.oldNpcIndex;
				player.totalDamageDealt += damage;
				NpcHandler.npcs[i].hitUpdateRequired = true;
				if (damage2 > -1) {
					NpcHandler.npcs[i].hitUpdateRequired2 = true;
				}
				NpcHandler.npcs[i].updateRequired = true;
			} else if (player.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(MagicData.MAGIC_SPELLS[player.oldSpellId][6]);
				if (MagicSpells.godSpells(player)) {
					if (System.currentTimeMillis() - player.godSpellDelay < CombatConstants.GOD_SPELL_CHARGE) {
						damage += Misc.random(10);
					}
				}
				boolean magicFailed = false;
				int bonusAttack = getBonusAttack(i);
				if (Misc.random(NpcHandler.npcs[i].defence) > 10 + Misc.random(mageAtk()) + bonusAttack) {
					damage = 0;
					magicFailed = true;
				} else if (NpcHandler.npcs[i].npcType == 2881 || NpcHandler.npcs[i].npcType == 2882) {
					damage = 0;
					magicFailed = true;
				}
				if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == player.getId() && ((NpcHandler.npcs[i].HP > (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
					(NpcHandler.npcs[i].HP-damage < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
					if (player.canHealersRespawn) {
						FightCaves.spawnHealers(player, i, 4-player.spawnedHealers);
					}
				}
				if (NpcHandler.npcs[i].HP - damage < 0) {
					damage = NpcHandler.npcs[i].HP;
				}
				//magic
				player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[player.oldSpellId][7] + damage * CombatConstants.MAGIC_EXP_RATE, 6);
				player.totalDamageDealt += damage;
				if (MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1161 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1153 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1157 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1542 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1543 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1562) {
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MAGIC_EXP_RATE / 3, 3);
				}
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
				if (damage > 0) {
					if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
						player.pcDamage += damage;
					}
				}
				if (MagicSpells.getEndGfxHeight(player) == 100 && !magicFailed) { // end GFX
					NpcHandler.npcs[i].gfx100(MagicData.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (!magicFailed) {
					NpcHandler.npcs[i].gfx0(MagicData.MAGIC_SPELLS[player.oldSpellId][5]);
				}
				if (magicFailed) {
					NpcHandler.npcs[i].gfx100(85);
				}
				if (!magicFailed) {
					int freezeDelay = MagicSpells.getFreezeTime(player);// freeze
					if (freezeDelay > 0 && NpcHandler.npcs[i].freezeTimer == 0) {
						NpcHandler.npcs[i].freezeTimer = freezeDelay;
					}
					switch (MagicData.MAGIC_SPELLS[player.oldSpellId][0]) {
					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = Misc.random(damage / 2);
						if (player.playerLevel[Constants.HITPOINTS] + heal >= player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
							player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
						} else {
							player.playerLevel[Constants.HITPOINTS] += heal;
						}
						player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
						break;
					}
				}
				NpcHandler.npcs[i].underAttack = true;
				if (MagicData.MAGIC_SPELLS[player.oldSpellId][6] != 0) {
					NpcHandler.npcs[i].hitDiff = damage;
					NpcHandler.npcs[i].HP -= damage;
					NpcHandler.npcs[i].hitUpdateRequired = true;
					player.totalDamageDealt += damage;
				}
				player.killingNpcIndex = player.oldNpcIndex;
				NpcHandler.npcs[i].updateRequired = true;
				player.usingMagic = false;
				player.castingMagic = false;
				player.oldSpellId = 0;
			}
		}

		if (player.bowSpecShot <= 0) {
			player.oldNpcIndex = 0;
			player.projectileStage = 0;
			player.doubleHit = false;
			player.lastWeaponUsed = 0;
			player.bowSpecShot = 0;
		}
		if (player.bowSpecShot >= 2) {
			player.bowSpecShot = 0;
		}
		if (player.bowSpecShot == 1) {
			fireProjectileNpc();
			player.hitDelay = 2;
			player.bowSpecShot = 0;
		}
	}

	public void applyNpcMeleeDamage(int i, int damageMask) {
		int damage = Misc.random(meleeMaxHit());
		boolean fullVeracsEffect = player.getPlayerAssistant().fullVeracs() && Misc.random(3) == 1;
		if (NpcHandler.npcs[i].HP - damage < 0) {
			damage = NpcHandler.npcs[i].HP;
		}

		if (!fullVeracsEffect) {
			if (Misc.random(NpcHandler.npcs[i].defence) > 10 + Misc.random(calcAtt())) {
				damage = 0;
			} else if (NpcHandler.npcs[i].npcType == 2882 || NpcHandler.npcs[i].npcType == 2883) {
				damage = 0;
			}
		}
		player.globalDamageDealt += damage;
		if (NpcHandler.npcs[i].HP - damage > 0) {
			if (NpcHandler.npcs[i].npcType == FightCaves.TZTOK_JAD && NpcHandler.npcs[i].spawnedBy == player.getId() && ((NpcHandler.npcs[i].HP > (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)) &&
				(NpcHandler.npcs[i].HP-damage < (FightCaves.getHp(FightCaves.TZTOK_JAD)/2)))) {
			if (player.canHealersRespawn)
				FightCaves.spawnHealers(player, i, 4-player.spawnedHealers);
			}
		}
		boolean guthansEffect = false;
		if (player.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(3) == 1) {
				guthansEffect = true;
			}
		}
		if (player.fightMode == 3 && NpcHandler.npcs[i].npcType != 2459 && NpcHandler.npcs[i].npcType != 2460 && NpcHandler.npcs[i].npcType != 2461 && NpcHandler.npcs[i].npcType != 2462) {
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 0);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 1);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 2);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 3);
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
			player.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
		} else {
			if (NpcHandler.npcs[i].npcType != 2459 && NpcHandler.npcs[i].npcType != 2460 && NpcHandler.npcs[i].npcType != 2461 && NpcHandler.npcs[i].npcType != 2462) {
				player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE, player.fightMode);
				player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 3);
				player.getPlayerAssistant().refreshSkill(player.fightMode);
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			}
		}
		if (damage > 0) {
			if (PestControl.npcIsPCMonster(NpcHandler.npcs[i].npcType) || PestControl.isPCPortal(NpcHandler.npcs[i].npcType)) {
				player.pcDamage += damage;
			}
		}
		if (damage > 0 && guthansEffect) {
			player.playerLevel[Constants.HITPOINTS] += damage;
			if (player.playerLevel[Constants.HITPOINTS] > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
				player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
			}
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			NpcHandler.npcs[i].gfx0(398);
		}
		NpcHandler.npcs[i].underAttack = true;
		player.killingNpcIndex = player.npcIndex;
		player.lastNpcAttacked = i;
		switch (player.specEffect) {
		case 4:
			if (damage > 0) {
				if (player.playerLevel[Constants.HITPOINTS] + damage > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
					if (player.playerLevel[Constants.HITPOINTS] > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
					} else {
						player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
					}
				} else {
					player.playerLevel[Constants.HITPOINTS] += damage;
				}
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			}
			break;

		}
		switch (damageMask) {
		case 1:
			NpcHandler.npcs[i].hitDiff = damage;
			NpcHandler.npcs[i].HP -= damage;
			player.totalDamageDealt += damage;
			NpcHandler.npcs[i].hitUpdateRequired = true;
			NpcHandler.npcs[i].updateRequired = true;
			break;

		case 2:
			NpcHandler.npcs[i].hitDiff2 = damage;
			NpcHandler.npcs[i].HP -= damage;
			player.totalDamageDealt += damage;
			NpcHandler.npcs[i].hitUpdateRequired2 = true;
			NpcHandler.npcs[i].updateRequired = true;
			player.doubleHit = false;
			break;
		}
	}

	public void fireProjectileNpc() {
		if (player.oldNpcIndex > 0) {
			if (NpcHandler.npcs[player.oldNpcIndex] != null) {
				player.projectileStage = 2;
				int pX = player.getX();
				int pY = player.getY();
				int nX = NpcHandler.npcs[player.oldNpcIndex].getX();
				int nY = NpcHandler.npcs[player.oldNpcIndex].getY();
				int offX = (pY - nY) * -1;
				int offY = (pX - nX) * -1;
				player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, RangeData.getProjectileSpeed(player), RangeData.getRangeProjectileGFX(player), 43, 31, player.oldNpcIndex + 1, MagicSpells.getStartDelay(player));
			}
		}
	}

	public void attackingNpcTick() {
		int i = player.npcIndex;
		if (i > 0 && NpcHandler.npcs[i] != null) {
			if (NpcHandler.npcs[i].isDead) {
				player.npcIndex = 0;
				player.followId2 = 0;
				player.faceNpc(0);
				return;
			}

			if (!PathFinder.isProjectilePathClear(player.getX(), player.getY(), player.heightLevel, NpcHandler.npcs[i].absX, NpcHandler.npcs[i].absY)) {
				return;
			}

			/**
			 * Processing the npc attack distances
			 */
			//distance 1 = melee [good]
			//distance 2 = hally [good]
			//distance 3 = darts, long range = 5 [good]
			//distance 4 = knifes, axes, long range = 6 [good]
			//distance 7 = shortbow, mode = normal, long range = 9 [good]
			//distance 9 = longbow, long range = 10 [good]
			//distance = 10 = crystal bow, both modes [good]
			if (!player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 2) && RangeData.usingHally(player) && !player.usingRangeWeapon && !player.usingBow && !player.usingMagic
					|| !player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 4) && player.usingRangeWeapon && !player.usingBow && !player.usingMagic
					|| !player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize()) && !player.usingRangeWeapon && !RangeData.usingHally(player) && !player.usingBow && !player.usingMagic
					|| !player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 7) && (player.usingBow || player.usingMagic)) {
				return;
			} else {
				if (player.usingMagic || player.usingBow || player.usingRangeWeapon) {
					player.followId2 = 0;
				}
				player.stopMovement();
			}
		}
	}

	public void attackingPlayerTick() {
		int i = player.playerIndex;
		if (i > 0 && PlayerHandler.players[i] != null) {
			if (PlayerHandler.players[i].isDead) {
				player.playerIndex = 0;
				player.followId = 0;
				player.faceNpc(0);
				return;
			}
			
			if (!checkReqs()) {
				return;
			}

			boolean projectile = player.usingBow || player.usingMagic || player.usingRangeWeapon;
			if (projectile && !PathFinder.isProjectilePathClear(player.getX(), player.getY(), player.heightLevel, PlayerHandler.players[i].absX, PlayerHandler.players[i].absY)) {
				return;
			}

			/**
			 * Processing the player attacking distances
			 */
			if (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 4) && player.usingRangeWeapon && !player.usingBow && !player.usingMagic 
					|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 2) && !player.usingRangeWeapon 	&& RangeData.usingHally(player) && !player.usingBow && !player.usingMagic
					|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), getRequiredDistance()) && !player.usingRangeWeapon && !RangeData.usingHally(player) 	&& !player.usingBow && !player.usingMagic
					|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 10) && (player.usingBow || player.usingMagic)) {
				return;
			} else {
				if (player.usingMagic || player.usingBow || player.usingRangeWeapon) {
					player.followId = 0;
				}
				player.stopMovement();
			}
		}
	}

	public void attackNpc(int i) {
		if (NpcHandler.npcs[i] != null) {
            Npc npc = NpcHandler.npcs[i];
			if (NpcHandler.npcs[i].isDead || NpcHandler.npcs[i].MaxHP <= 0) {
				player.usingMagic = false;
				player.faceUpdate(0);
				player.npcIndex = 0;
				return;
			}
			if (player.usingMagic && MagicData.MAGIC_SPELLS[player.spellId][0] == 1171) {
				if (!NpcHandler.isUndead(i)) {
					player.getPacketSender().sendMessage("This spell only affects skeletons, zombies, ghosts and shades.");
					resetPlayerAttack();
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}
			}
			if (player.respawnTimer > 0) {
				player.npcIndex = 0;
				return;
			}
			if (!SlayerRequirements.itemNeededSlayer(player, i) || !player.getSlayer().canAttackNpc(i)) {
				return;
			}
			if (NpcHandler.npcs[i].npcType == 757 && player.vampSlayer > 2) {
				if (!player.getItemAssistant().playerHasItem(1549, 1) || !player.getItemAssistant().playerHasItem(2347, 1)) {
					player.getPacketSender().sendMessage("You need a stake and hammer to attack count draynor.");
					resetPlayerAttack();
					return;
				}
			}
			if (player.isWoodcutting) {
				player.getPacketSender().sendMessage("You can't attack an npc while woodcutting.");
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].npcType == 1676) {
				player.getPacketSender().sendMessage("You don't have the heart to kill the poor creature again.");
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].npcType == 411) {
				player.getPacketSender().sendMessage("You can't attack a swarm!");
				resetPlayerAttack();
				return;
			}
			if (NpcHandler.npcs[i].underAttackBy > 0 && NpcHandler.npcs[i].underAttackBy != player.playerId && !NpcHandler.npcs[i].inMulti()) {
				player.npcIndex = 0;
				player.getPacketSender().sendMessage("This monster is already in combat.");
				return;
			}
			if ((player.underAttackBy > 0 || player.underAttackBy2 > 0) && player.underAttackBy2 != i && !Boundary.isIn(player, Boundary.MULTI)) {
				resetPlayerAttack();
				player.getPacketSender().sendMessage("I am already under attack.");
				return;
			}
			if (NpcHandler.npcs[i].spawnedBy != player.playerId && NpcHandler.npcs[i].spawnedBy > 0) {
				resetPlayerAttack();
				player.getPacketSender().sendMessage("This monster was not spawned for you.");
				return;
			}

			player.followId2 = i;
			player.followId = 0;
			if (!player.usingRangeWeapon && !RangeData.usingHally(player) && !player.usingBow && !player.usingMagic && player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize())) {
				//System.out.println("distance good! stop movement 2");
				player.stopMovement();
			}
			if (player.attackTimer <= 0) {
				player.usingBow = false;
				player.usingRangeWeapon = false;
				boolean usingArrows = false;
				boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
				player.bonusAttack = 0;
				player.rangeItemUsed = 0;
				player.projectileStage = 0;
				if (player.autocasting) {
					player.spellId = player.autocastId;
					player.usingMagic = true;
				}
				if (player.spellId > 0) {
					player.usingMagic = true;
				}

				player.specAccuracy = 1.0;
				player.specDamage = 1.0;
				if (!player.usingMagic) {
					for (int bowId : RangeData.BOWS) {
						if (player.playerEquipment[player.playerWeapon] == bowId) {
							player.usingBow = true;
							for (int arrowId : RangeData.ARROWS) {
								if (player.playerEquipment[player.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
							player.usingRangeWeapon = true;
						}
					}
				}
				if (player.usingRangeWeapon || player.usingBow && CombatConstants.COMBAT_SOUNDS) {
					player.getPacketSender().sendSound(SoundList.SHOOT_ARROW, 100, 0);
				}

				if (!PathFinder.isProjectilePathClear(player.getX(), player.getY(), player.heightLevel, npc.getX(), npc.getY())) {
					System.err.println("projectile path is not clear! exiting early");
					return;
				}
				//System.out.println("npc id is " + NpcHandler.npcs[i].npcType + " with size " + NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize());
				/**
				 * Distances for attacking npcs
				 */
				if ((!player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 2) && RangeData.usingHally(player) && !player.usingRangeWeapon && !player.usingBow && !player.usingMagic)
						|| (!player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 4) && player.usingRangeWeapon && !player.usingBow && !player.usingMagic)
						|| (!player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), NPCDefinition.forId(NpcHandler.npcs[i].npcType).getSize()) && !player.usingRangeWeapon && !RangeData.usingHally(player) && !player.usingBow && !player.usingMagic)
						|| (!player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 8) && (player.usingBow || player.usingMagic))) {
					//System.err.println("npc distance check early return! probably not good");
					return;
				} else {
					if (player.usingMagic || player.usingBow || player.usingRangeWeapon) {
						player.followId2 = 0;
					}
					player.stopMovement();
				}

				if (!usingCross
						&& !usingArrows
						&& player.usingBow
						&& (player.playerEquipment[player.playerWeapon] < 4212 || player.playerEquipment[player.playerWeapon] > 4223)) {
					player.getPacketSender().sendMessage(
							"There is no ammo left in your quiver.");
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}
				if (RangeData.correctBowAndArrows(player) < player.playerEquipment[player.playerArrows] && CombatConstants.CORRECT_ARROWS && player.usingBow && !RangeData.usingCrystalBow(player) && player.playerEquipment[player.playerWeapon] != 9185) {
					player.getItemAssistant();
					player.getItemAssistant();
					player.getPacketSender().sendMessage("You can't use " + DeprecatedItems.getItemName(player.playerEquipment[player.playerArrows]).toLowerCase() + "s with a " + DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase() + ".");
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}
				if (player.playerEquipment[player.playerWeapon] == 9185 && !properBolts()) {
					player.getPacketSender().sendMessage("You must use bolts with a crossbow.");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}
				/**
				 * Don't run up to player if using hally, set distance to 2
				 */
				if (player.usingBow || player.usingMagic || player.usingRangeWeapon || player.goodDistance(player.getX(), player.getY(), NpcHandler.npcs[i].getX(), NpcHandler.npcs[i].getY(), 2) && RangeData.usingHally(player)) {
					player.stopMovement();
				}
				
				if (!checkMagicReqs(player.spellId)) {
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}
				player.faceUpdate(i);
				player.attackTimer = getAttackDelay();
				NpcHandler.npcs[i].underAttackBy = player.playerId;
				NpcHandler.npcs[i].lastDamageTaken = System.currentTimeMillis();
				if (player.usingSpecial && !player.usingMagic) {
					if (player.getCombatAssistant().checkSpecAmount(player.playerEquipment[player.playerWeapon])) {
						player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
						player.lastArrowUsed = player.playerEquipment[player.playerArrows];
						player.getSpecials().activateSpecial(player.playerEquipment[player.playerWeapon], null, i);
						return;
					} else {
						player.getPacketSender().sendMessage("You don't have the required special energy to use this attack.");
						player.usingSpecial = false;
						player.getItemAssistant().updateSpecialBar();
						if (CombatConstants.COMBAT_SOUNDS) {
							player.getPacketSender().sendSound(CombatSounds.specialSounds(player.playerEquipment[player.playerWeapon]), 100, 0);
						}
						player.npcIndex = 0;
						return;
					}
				}
				player.specMaxHitIncrease = 0;
				if (!player.usingMagic) {
					if (CombatConstants.COMBAT_SOUNDS) {
						player.getPacketSender().sendSound(
								CombatSounds.getWeaponSounds(player), 100, 0);
					}
					player.startAnimation(getWepAnim());
				} else {
					if (CombatConstants.COMBAT_SOUNDS) {
						player.getPacketSender().sendSound(CombatSounds.getMagicSound(player, player.spellId), 100, 0);
					}
					player.startAnimation(MagicData.MAGIC_SPELLS[player.spellId][2]);
				}
				player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
				player.lastArrowUsed = player.playerEquipment[player.playerArrows];
				if (!player.usingBow && !player.usingMagic && !player.usingRangeWeapon) { // melee hit delay
					player.hitDelay = getHitDelay();
					player.projectileStage = 0;
					player.oldNpcIndex = i;
				}
				if (player.usingBow && !player.usingRangeWeapon && !player.usingMagic || usingCross) { // range hit delay
					if (usingCross) {
						player.usingBow = true;
					}
					if (player.fightMode == 2) {
						player.attackTimer--;
					}
					player.lastArrowUsed = player.playerEquipment[player.playerArrows];
					player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
					player.gfx100(RangeData.getRangeStartGFX(player));
					player.hitDelay = getHitDelay();
					player.projectileStage = 1;
					player.oldNpcIndex = i;
					if (player.playerEquipment[player.playerWeapon] >= 4212 && player.playerEquipment[player.playerWeapon] <= 4223) {
						player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
						player.crystalBowArrowCount++;
						player.lastArrowUsed = 0;
					} else {
						player.rangeItemUsed = player.playerEquipment[player.playerArrows];
						player.getItemAssistant().deleteArrow();
					}
					fireProjectileNpc();
				}

				if (player.usingRangeWeapon && !player.usingMagic && !player.usingBow) { // knives, darts, etc hit delay
					player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
					player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
					player.getItemAssistant().deleteEquipment();
					player.gfx100(RangeData.getRangeStartGFX(player));
					player.lastArrowUsed = 0;
					player.hitDelay = getHitDelay();
					player.projectileStage = 1;
					player.oldNpcIndex = i;
					if (player.fightMode == 2) {
						player.attackTimer--;
					}
					fireProjectileNpc();
				}
				if (player.usingMagic) { // magic hit delay
					int pX = player.getX();
					int pY = player.getY();
					int nX = NpcHandler.npcs[i].getX();
					int nY = NpcHandler.npcs[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					player.castingMagic = true;
					player.projectileStage = 2;
					if (MagicData.MAGIC_SPELLS[player.spellId][3] > 0) {
						if (MagicSpells.getStartGfxHeight(player) == 100) {
							player.gfx100(MagicData.MAGIC_SPELLS[player.spellId][3]);
						} else {
							player.gfx0(MagicData.MAGIC_SPELLS[player.spellId][3]);
						}
					}
					if (MagicData.MAGIC_SPELLS[player.spellId][4] > 0) {
						player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, 78, MagicData.MAGIC_SPELLS[player.spellId][4], MagicSpells.getStartHeight(player), MagicSpells.getEndHeight(player), i + 1, 50);
					}
					player.hitDelay = getHitDelay();
					player.oldNpcIndex = i;
					player.oldSpellId = player.spellId;
					player.spellId = 0;
					if (!player.autocasting) {
						player.npcIndex = 0;
					}
				}
				if (player.usingBow && CombatConstants.CRYSTAL_BOW_DEGRADES) { // crystal bow degrading
					if (player.playerEquipment[player.playerWeapon] == 4212) { // new crystal bow
						player.getItemAssistant().wearItem(4214, 1, 3);
					}
					if (player.crystalBowArrowCount >= 250) {
						switch (player.playerEquipment[player.playerWeapon]) {
						case 4223: // 1/10 bow
							player.getItemAssistant().wearItem(-1, 1, 3);
							player.getPacketSender().sendMessage("Your crystal bow has fully degraded.");
							if (!player.getItemAssistant().addItem(4207, 1)) {
								GameEngine.itemHandler.createGroundItem(player, 4207, player.getX(), player.getY(), 1, player.getId());
							}
							player.crystalBowArrowCount = 0;
							break;

						default:
							player.getItemAssistant().wearItem(++player.playerEquipment[player.playerWeapon], 1, 3);
							player.getPacketSender().sendMessage("Your crystal bow degrades.");
							player.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}

	public void attackPlayer(int i) {
		Client o = (Client) PlayerHandler.players[i];
		int equippedWeapon = player.playerEquipment[player.playerWeapon];
		if (PlayerHandler.players[i] != null) {
			if (player.usingMagic && MagicData.MAGIC_SPELLS[player.spellId][0] == 1171) {
				player.getPacketSender().sendMessage("This spell only affects skeletons, zombies, ghosts and shades, not humans.");
				resetPlayerAttack();
				player.stopMovement();
				return;
			}
			if (CastleWars.isInCw(PlayerHandler.players[i]) && CastleWars.isInCw(player)) {
				if (CastleWars.getTeamNumber(player) == CastleWars.getTeamNumber((Client) PlayerHandler.players[i])) {
					player.getPacketSender().sendMessage("You cannot attack your own teammate.");
					resetPlayerAttack();
					return;
				}
			}
			if (!CastleWars.isInCw(PlayerHandler.players[i]) && CastleWars.isInCw(player)) {
				player.getPacketSender().sendMessage("You cannot attack people outside castle wars.");
				resetPlayerAttack();
				return;
			}
			if (PlayerHandler.players[i].isDead) {
				resetPlayerAttack();
				return;
			}
			if (player.respawnTimer > 0 || PlayerHandler.players[i].respawnTimer > 0) {
				resetPlayerAttack();
				return;
			}
			boolean sameSpot = player.getX() == PlayerHandler.players[i].getX() && player.getY() == PlayerHandler.players[i].getY();
			if (!player.goodDistance(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), player.getX(), player.getY(), 25) && !sameSpot) {
				resetPlayerAttack();
				return;
			}
			if (PlayerHandler.players[i].respawnTimer > 0) {
				PlayerHandler.players[i].playerIndex = 0;
				resetPlayerAttack();
				return;
			}
			if (PlayerHandler.players[i].heightLevel != player.heightLevel) {
				resetPlayerAttack();
				return;
			}
			if (player.attackTimer <= 0) {
				player.usingBow = false;
				player.specEffect = 0;
				player.usingRangeWeapon = false;
				player.rangeItemUsed = 0;
				boolean usingArrows = false;
				boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
				player.projectileStage = 0;
				if (player.getX() == PlayerHandler.players[i].absX && player.getY() == PlayerHandler.players[i].absY) {
					if (player.freezeTimer > 0) {
						resetPlayerAttack();
						return;
					}
					player.followId = i;
					player.attackTimer = 0;
					return;
				}
				if (!player.usingMagic) {
					for (int bowId : RangeData.BOWS) {
						if (player.playerEquipment[player.playerWeapon] == bowId) {
							player.usingBow = true;
							for (int arrowId : RangeData.ARROWS) {
								if (player.playerEquipment[player.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
							player.usingRangeWeapon = true;
						}
					}
				}
				if (player.autocasting) {
					player.spellId = player.autocastId;
					player.usingMagic = true;
				}
				if (player.spellId > 0) {
					player.usingMagic = true;
				}
				if (player.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : Constants.FUN_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						player.getPacketSender().sendMessage("You can only use fun weapons for this duel!");
						resetPlayerAttack();
						return;
					}
				}
				if (player.duelRule[2] && (player.usingBow || player.usingRangeWeapon)) {
					player.getPacketSender().sendMessage("Range has been disabled for this duel!");
					return;
				}
				if (player.duelRule[3] && !player.usingBow && !player.usingRangeWeapon && !player.usingMagic) {
					player.getPacketSender().sendMessage("Melee has been disabled for this duel!");
					return;
				}
				if (player.duelRule[4] && player.usingMagic) {
					player.getPacketSender().sendMessage("Magic has been disabled for this duel!");
					resetPlayerAttack();
					return;
				}

				if (!PathFinder.isProjectilePathClear(player.getX(), player.getY(), player.heightLevel, PlayerHandler.players[i].absX, PlayerHandler.players[i].absY)) {
					return;
				}

				/**
				 * Attacking player distances
				 */
				if (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 4) && player.usingRangeWeapon && !player.usingBow && !player.usingMagic
						|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 2) && !player.usingRangeWeapon && RangeData.usingHally(player) && !player.usingBow && !player.usingMagic
						|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), getRequiredDistance()) && !player.usingRangeWeapon && !RangeData.usingHally(player) && !player.usingBow && !player.usingMagic 
						|| !player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 10) && (player.usingBow || player.usingMagic)) {
					if (!player.usingBow && !player.usingMagic && !player.usingRangeWeapon && player.freezeTimer > 0) {
						resetPlayerAttack();
					}
					return;
				} else {
					if (player.usingMagic || player.usingBow || player.usingRangeWeapon) {
						player.followId = 0;
					}
				}

				if (!usingCross
						&& !usingArrows
						&& player.usingBow
						&& (player.playerEquipment[player.playerWeapon] < 4212 || player.playerEquipment[player.playerWeapon] > 4223)
						&& !player.usingMagic) {
					player.getPacketSender().sendMessage("There is no ammo left in your quiver.");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (RangeData.correctBowAndArrows(player) < player.playerEquipment[player.playerArrows]
						&& CombatConstants.CORRECT_ARROWS && player.usingBow
						&& !RangeData.usingCrystalBow(player)
						&& player.playerEquipment[player.playerWeapon] != 9185
						&& !player.usingMagic) {
					player.getItemAssistant();
					player.getItemAssistant();
					player.getPacketSender().sendMessage("You can't use " + DeprecatedItems.getItemName(player.playerEquipment[player.playerArrows]).toLowerCase() + "s with a " + DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase() + ".");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (player.playerEquipment[player.playerWeapon] == 9185 && !properBolts() && !player.usingMagic) {
					player.getPacketSender().sendMessage("You must use bolts with a crossbow.");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}

				/**
				 * Ensuring the player doesnt run up to the player when ranging/maging
				 */
				if (player.usingBow || player.usingMagic || player.usingRangeWeapon || RangeData.usingHally(player)) {
					player.stopMovement();
				}

				if (!checkMagicReqs(player.spellId)) {
					player.stopMovement();
					resetPlayerAttack();
					return;
				}

				player.faceUpdate(i + 32768);

				if (player.duelStatus != 5 && !PlayerHandler.players[player.playerIndex].inCwGame() && FightPits.getState(player) == null) {
					if (!player.attackedPlayers.contains(player.playerIndex) && !PlayerHandler.players[player.playerIndex].attackedPlayers.contains(player.playerId)) {
						player.attackedPlayers.add(player.playerIndex);
						player.isSkulled = true;
						player.skullTimer = CombatConstants.SKULL_TIMER;
						player.headIconPk = 0;
						player.getPlayerAssistant().requestUpdates();
					}
				}
				player.specAccuracy = 1.0;
				player.specDamage = 1.0;
				player.delayedDamage = player.delayedDamage2 = 0;
				if (player.usingSpecial && !player.usingMagic) {
					if (player.duelRule[10] && player.duelStatus == 5) {
						player.getPacketSender().sendMessage("Special attacks have been disabled during this duel!");
						player.usingSpecial = false;
						player.getItemAssistant().updateSpecialBar();
						if (CombatConstants.COMBAT_SOUNDS) {
							player.getPacketSender().sendSound(CombatSounds.specialSounds(player.playerEquipment[player.playerWeapon]), 100, 0);
						}
						resetPlayerAttack();
						return;
					}
					if (checkSpecAmount(equippedWeapon)) {
						player.lastArrowUsed = player.playerEquipment[player.playerArrows];
						player.getSpecials().activateSpecial(player.playerEquipment[player.playerWeapon], o, i);
						player.followId = player.playerIndex;
						return;
					} else {
						player.getPacketSender().sendMessage("You don't have the required special energy to use this attack.");
						player.usingSpecial = false;
						player.getItemAssistant().updateSpecialBar();
						if (CombatConstants.COMBAT_SOUNDS) {
							player.getPacketSender().sendSound(CombatSounds.specialSounds(player.playerEquipment[player.playerWeapon]), 100, 0);
						}
						player.playerIndex = 0;
						return;
					}
				}

				if (!player.usingMagic) {
					if (CombatConstants.COMBAT_SOUNDS) {
						player.getPacketSender().sendSound(CombatSounds.getWeaponSounds(player), 100, 0);
					}
					player.startAnimation(getWepAnim());
					player.mageFollow = false;
				} else {
					player.startAnimation(MagicData.MAGIC_SPELLS[player.spellId][2]);
					if (CombatConstants.COMBAT_SOUNDS) {
						player.getPacketSender().sendSound(CombatSounds.getMagicSound(player, player.spellId), 100, 0);
					}
				}

				player.attackTimer = getAttackDelay();
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = player.playerId;
				player.lastArrowUsed = 0;
				player.rangeItemUsed = 0;
				if (!player.usingBow && !player.usingMagic && !player.usingRangeWeapon) { // melee hit delay
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.hitDelay = getHitDelay();
					player.delayedDamage = Misc.random(meleeMaxHit());
					player.projectileStage = 0;
					player.oldPlayerIndex = i;
				}
				if (player.usingBow && !player.usingRangeWeapon && !player.usingMagic || usingCross) { // range hit delay
					if (player.playerEquipment[player.playerWeapon] >= 4212&& player.playerEquipment[player.playerWeapon] <= 4223) {
						player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
						player.crystalBowArrowCount++;
					} else {
						player.rangeItemUsed = player.playerEquipment[player.playerArrows];
						player.getItemAssistant().deleteArrow();
					}
					if (player.fightMode == 2) {
						player.attackTimer--;
					}
					if (usingCross) {
						player.usingBow = true;
					}
					player.usingBow = true;
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
					player.lastArrowUsed = player.playerEquipment[player.playerArrows];
					player.gfx100(RangeData.getRangeStartGFX(player));
					player.hitDelay = getHitDelay();
					player.projectileStage = 1;
					player.oldPlayerIndex = i;
					fireProjectilePlayer();
				}
				if (player.usingRangeWeapon) { // knives, darts, etc hit delay
					player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
					player.getItemAssistant().deleteEquipment();
					player.usingRangeWeapon = true;
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.gfx100(RangeData.getRangeStartGFX(player));
					if (player.fightMode == 2) {
						player.attackTimer--;
					}
					player.hitDelay = getHitDelay();
					player.projectileStage = 1;
					player.oldPlayerIndex = i;
					fireProjectilePlayer();
				}
				if (player.usingMagic) { // magic hit delay
					int pX = player.getX();
					int pY = player.getY();
					int nX = PlayerHandler.players[i].getX();
					int nY = PlayerHandler.players[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					player.castingMagic = true;
					player.projectileStage = 2;
					if (MagicData.MAGIC_SPELLS[player.spellId][3] > 0) {
						if (MagicSpells.getStartGfxHeight(player) == 100) {
							player.gfx100(MagicData.MAGIC_SPELLS[player.spellId][3]);
						} else {
							player.gfx0(MagicData.MAGIC_SPELLS[player.spellId][3]);
						}
					}
					if (MagicData.MAGIC_SPELLS[player.spellId][4] > 0) {
						player.getPlayerAssistant().createPlayersProjectile(pX, pY,
								offX, offY, 50, 78,
								MagicData.MAGIC_SPELLS[player.spellId][4],
								MagicSpells.getStartHeight(player), MagicSpells.getEndHeight(player), -i - 1,
								MagicSpells.getStartDelay(player));
					}
					if (player.autocastId > 0) {
						//We don't need to set the followId if they are already autocasting, setting followId here makes a manual cast (when autocast is set) run up to the player.
						//player.followId = player.playerIndex;
						player.followDistance = 5;
					}
					player.hitDelay = getHitDelay();
					player.oldPlayerIndex = i;
					player.oldSpellId = player.spellId;
					player.spellId = 0;
					if (MagicData.MAGIC_SPELLS[player.oldSpellId][0] == 12891 && o.isMoving) {
						player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, 85, 368, 25, 25, -i - 1, MagicSpells.getStartDelay(player));
					}
					if (Misc.random(o.getCombatAssistant().mageDef()) > Misc.random(mageAtk())) {
						player.magicFailed = true;
					} else {
						player.magicFailed = false;
					}
					int freezeDelay = MagicSpells.getFreezeTime(player);// freeze time
					if (freezeDelay > 0 && PlayerHandler.players[i].freezeTimer <= -3 && !player.magicFailed) {
						PlayerHandler.players[i].freezeTimer = freezeDelay;
						o.resetWalkingQueue();
						o.getPacketSender().sendMessage("You have been frozen.");
						o.frozenBy = player.playerId;
					}
					if (!checkReqs()) {
						return;
					}
					if (!player.autocasting && player.spellId <= 0) {
						player.playerIndex = 0;
					}
				}
				if (player.usingBow && CombatConstants.CRYSTAL_BOW_DEGRADES) { // crystal bow degrading
					if (player.playerEquipment[player.playerWeapon] == 4212) { // new crystal bow becomes full
						player.getItemAssistant().wearItem(4214, 1, 3);
					}
					if (player.crystalBowArrowCount >= 250) {
						switch (player.playerEquipment[player.playerWeapon]) {
						case 4223: // 1/10 bow
							player.getItemAssistant().wearItem(-1, 1, 3);
							player.getPacketSender().sendMessage("Your crystal bow has fully degraded.");
							if (!player.getItemAssistant().addItem(4207, 1)) {
								GameEngine.itemHandler.createGroundItem(player, 4207, player.getX(), player.getY(), 1, player.getId());
							}
							player.crystalBowArrowCount = 0;
							break;

						default:
							player.getItemAssistant().wearItem(++player.playerEquipment[player.playerWeapon], 1, 3);
							player.getPacketSender().sendMessage("Your crystal bow degrades.");
							player.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}

	public void playerDelayedHit(int i) {
		if (PlayerHandler.players[i] != null) {
			if (PlayerHandler.players[i].isDead || player.isDead || PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] <= 0 || player.playerLevel[Constants.HITPOINTS] <= 0) {
				player.playerIndex = 0;
				return;
			}
			if (PlayerHandler.players[i].respawnTimer > 0) {
				player.faceUpdate(0);
				player.playerIndex = 0;
				return;
			}
			Client o = (Client) PlayerHandler.players[i];
			o.getPacketSender().closeAllWindows();
			if (o.playerIndex <= 0 && o.npcIndex <= 0) {
				if (o.autoRet == 1) {
					o.playerIndex = player.playerId;
				}
			}
			if (o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0 && !player.castingMagic) { // block animation
				o.startAnimation(o.getCombatAssistant().getBlockEmote());
				if (CombatConstants.COMBAT_SOUNDS) {
					o.getPacketSender().sendSound(CombatSounds.getPlayerBlockSounds(o), 100, 0);
				}
			}
			if (o.inTrade) {
				o.getTrading().declineTrade();
			}
			if (player.projectileStage == 0) { // melee hit damage
				applyPlayerMeleeDamage(i, 1);
				if (player.doubleHit) {
					applyPlayerMeleeDamage(i, 2);
				}
			}
			if (!player.castingMagic && player.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					damage2 = Misc.random(rangeMaxHit());
				}
				boolean ignoreDef = false;
				if (Misc.random(4) == 1 && player.lastArrowUsed == 9243) {
					ignoreDef = true;
					o.gfx0(758);
				}
				if (Misc.random(10 + o.getCombatAssistant().calculateRangeDefence()) > Misc.random(10 + calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				}
				if (Misc.random(4) == 1 && player.lastArrowUsed == 9242 && damage > 0) {
					PlayerHandler.players[i].gfx0(754);
					damage = NpcHandler.npcs[i].HP / 5;
					player.handleHitMask(player.playerLevel[Constants.HITPOINTS] / 10);
					player.dealDamage(player.playerLevel[Constants.HITPOINTS] / 10);
					player.gfx0(754);
				}
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					if (Misc.random(10 + o.getCombatAssistant().calculateRangeDefence()) > Misc.random(10 + calculateRangeAttack())) {
						damage2 = 0;
					}
				}
				if (damage > 0 && Misc.random(5) == 1 && player.lastArrowUsed == 9244) {
					damage *= 1.45;
					o.gfx0(756);
				}
				if (o.getPrayer().prayerActive[17]&& System.currentTimeMillis() - o.protRangeDelay > 1500) { //if pray reduce damage
					damage = damage * 60 / 100;
					if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
						damage2 = damage2 * 60 / 100;
					}
				}
				if (PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[Constants.HITPOINTS];
				}
				if (PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] - damage - damage2 < 0) {
					damage2 = PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] - damage;
				}
				if (damage < 0) {
					damage = 0;
				}
				if (damage2 < 0 && damage2 != -1) {
					damage2 = 0;
				}
				if (damage > 0) {
					applyRecoil(player, damage, i);
				}
				if (damage2 > 0) {
					applyRecoil(player, damage2, i);
				}
				if (player.fightMode == 3) {
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE / 3, 4);
					player.getPlayerAssistant().addSkillXP(damage / 3, 1);
					player.getPlayerAssistant().addSkillXP(damage / 3, 3);
					player.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
					player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
					player.getPlayerAssistant().refreshSkill(Constants.RANGED);
				} else {
					player.getPlayerAssistant().addSkillXP(damage * CombatConstants.RANGE_EXP_RATE, 4);
					player.getPlayerAssistant().addSkillXP(damage / 3, 3);
					player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
					player.getPlayerAssistant().refreshSkill(Constants.RANGED);
				}
				boolean dropArrows = true;
				for (int noArrowId : RangeData.NO_ARROW_DROP) {
					if (player.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					player.getItemAssistant().dropArrowPlayer();
				}
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = player.playerId;
				PlayerHandler.players[i].dealDamage(damage);
				PlayerHandler.players[i].damageTaken[player.playerId] += damage;
				player.killedBy = PlayerHandler.players[i].playerId;
				PlayerHandler.players[i].handleHitMask(damage);
				if (damage2 != -1) {
					PlayerHandler.players[i].dealDamage(damage2);
					PlayerHandler.players[i].damageTaken[player.playerId] += damage2;
					PlayerHandler.players[i].handleHitMask(damage2);

				}
				o.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				PlayerHandler.players[i].updateRequired = true;
				applySmite(i, damage);
				if (damage2 != -1) {
					applySmite(i, damage2);
				}
			} else if (player.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(MagicData.MAGIC_SPELLS[player.oldSpellId][6]);
				if (MagicSpells.godSpells(player)) {
					if (System.currentTimeMillis() - player.godSpellDelay < CombatConstants.GOD_SPELL_CHARGE) {
						damage += 10;
					}
				}
				if (player.magicFailed) {
					damage = 0;
				}
				if (o.getPrayer().prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500) { // if prayer active reduce damage
					damage = damage * 60 / 100;
				}
				if (PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[Constants.HITPOINTS];
				}
				if (damage > 0) {
					applyRecoil(player, damage, i);
				}
				player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[player.oldSpellId][7] + damage * CombatConstants.MAGIC_EXP_RATE, 6);
				if (MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1161 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1153 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1157 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1542 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1543 && MagicData.MAGIC_SPELLS[player.oldSpellId][0] != 1562) {
					player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[player.oldSpellId][7] + damage / 3, 3);
				}
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				player.getPlayerAssistant().refreshSkill(Constants.MAGIC);

				if (MagicSpells.getEndGfxHeight(player) == 100 && !player.magicFailed) { // end GFX
					PlayerHandler.players[i].gfx100(MagicData.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (!player.magicFailed) {
					PlayerHandler.players[i].gfx0(MagicData.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (player.magicFailed) {
					PlayerHandler.players[i].gfx100(85);
					player.getPacketSender().sendSound(SoundList.MAGE_FAIL, 100, 0);
				}

				if (!player.magicFailed) {
					if (System.currentTimeMillis() - PlayerHandler.players[i].reduceStat > 35000) {
						PlayerHandler.players[i].reduceStat = System.currentTimeMillis();
						switch (MagicData.MAGIC_SPELLS[player.oldSpellId][0]) {
						case 12987:
						case 13011:
						case 12999:
						case 13023:
							PlayerHandler.players[i].playerLevel[Constants.ATTACK] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.ATTACK]) * 10 / 100;
							break;
						}
					}

					switch (MagicData.MAGIC_SPELLS[player.oldSpellId][0]) {
					case 12445: // teleblock
						if (System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength) {
							o.teleBlockDelay = System.currentTimeMillis();
							o.getPacketSender().sendMessage("You have been teleblocked.");
							o.getPacketSender().sendSound(SoundList.TELEBLOCK_HIT, 100, 0);
							if (o.getPrayer().prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500) {
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
						if (player.playerLevel[Constants.HITPOINTS] + heal > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
							player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
						} else {
							player.playerLevel[Constants.HITPOINTS] += heal;
						}
						player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
						break;

					case 1153:
						PlayerHandler.players[i].playerLevel[Constants.ATTACK] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.ATTACK]) * 5 / 100;
						o.getPacketSender().sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;

					case 1157:
						PlayerHandler.players[i].playerLevel[Constants.STRENGTH] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.STRENGTH]) * 5 / 100;
						o.getPacketSender().sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
						break;

					case 1161:
						PlayerHandler.players[i].playerLevel[Constants.DEFENCE] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.DEFENCE]) * 5 / 100;
						o.getPacketSender().sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
						break;

					case 1542:
						PlayerHandler.players[i].playerLevel[Constants.DEFENCE] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.DEFENCE]) * 10 / 100;
						o.getPacketSender().sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
						break;

					case 1543:
						PlayerHandler.players[i].playerLevel[Constants.STRENGTH] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.STRENGTH]) * 10 / 100;
						o.getPacketSender().sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
						break;

					case 1562:
						PlayerHandler.players[i].playerLevel[Constants.ATTACK] -= o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[Constants.ATTACK]) * 10 / 100;
						o.getPacketSender().sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;
					}
				}
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].killerId = player.playerId;
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				if (MagicData.MAGIC_SPELLS[player.oldSpellId][6] != 0) {
					PlayerHandler.players[i].dealDamage(damage);
					PlayerHandler.players[i].damageTaken[player.playerId] += damage;
					player.totalPlayerDamageDealt += damage;
					if (!player.magicFailed) {
						PlayerHandler.players[i].handleHitMask(damage);
					}
				}
				applySmite(i, damage);
				player.killedBy = PlayerHandler.players[i].playerId;
				o.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				PlayerHandler.players[i].updateRequired = true;
				player.usingMagic = false;
				player.castingMagic = false;
				if (Boundary.isIn(o, Boundary.MULTI) && MagicSpells.multis(player)) {
					player.barrageCount = 0;
					for (int j = 0; j < PlayerHandler.players.length; j++) {
						if (PlayerHandler.players[j] != null) {
							if (j == o.playerId) {
								continue;
							}
							if (player.barrageCount >= 9) {
								break;
							}
							if (o.goodDistance(o.getX(), o.getY(), PlayerHandler.players[j].getX(), PlayerHandler.players[j].getY(), 1)) {
								MagicSpells.appendMultiBarrage(player, j, player.magicFailed);
							}
						}
					}
				}
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
				player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
				player.oldSpellId = 0;
			}
		}
		player.getPlayerAssistant().requestUpdates();
		if (player.bowSpecShot <= 0) {
			player.oldPlayerIndex = 0;
			player.projectileStage = 0;
			player.lastWeaponUsed = 0;
			player.doubleHit = false;
			player.bowSpecShot = 0;
		}
		if (player.bowSpecShot != 0) {
			player.bowSpecShot = 0;
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
		if (player.getPlayerAssistant().fullVeracs()) {
			if (Misc.random(4) == 1) {
				veracsEffect = true;
			}
		}
		if (player.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(4) == 1) {
				guthansEffect = true;
			}
		}
		if (damageMask == 1) {
			damage = player.delayedDamage;
			player.delayedDamage = 0;
		} else {
			damage = player.delayedDamage2;
			player.delayedDamage2 = 0;
		}
		if (Misc.random(o.getCombatAssistant().calcDef()) > Misc.random(calcAtt()) && !veracsEffect) {
			damage = 0;
			player.bonusAttack = 0;
		} else if (player.playerEquipment[player.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPlayerAssistant().appendPoison(13);
			player.bonusAttack += damage / 3;
		} else {
			player.bonusAttack += damage / 3;
		}
		if (o.getPrayer().prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500 && !veracsEffect) { // if prayer active reduce damage by 40%
			damage = damage * 60 / 100;
		}
		if (damage > 0 && guthansEffect) {
			player.playerLevel[Constants.HITPOINTS] += damage;
			if (player.playerLevel[Constants.HITPOINTS] > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
				player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
			}
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			o.gfx0(398);
		}
		if (PlayerHandler.players[i].playerLevel[Constants.HITPOINTS] - damage < 0) {
			damage = PlayerHandler.players[i].playerLevel[Constants.HITPOINTS];
		}
		if (damage > 0) {
			applyRecoil(player, damage, i);
		}
		switch (player.specEffect) {
		case 1: // dragon scimmy special
			if (damage > 0) {
				if (o.getPrayer().prayerActive[16] || o.getPrayer().prayerActive[17] || o.getPrayer().prayerActive[18]) {
					o.headIcon = -1;
					o.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[16], 0);
					o.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[17], 0);
					o.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[18], 0);
				}
				o.getPacketSender().sendMessage("You have been injured!");
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
				o.getPacketSender().sendMessage("You have been frozen.");
				o.frozenBy = player.playerId;
				o.stopMovement();
				player.getPacketSender().sendMessage("You freeze your enemy.");
			}
			break;
		case 3:
			if (damage > 0) {
				o.playerLevel[Constants.DEFENCE] -= damage;
				o.getPacketSender().sendMessage("You feel weak.");
				if (o.playerLevel[Constants.DEFENCE] < 1) {
					o.playerLevel[Constants.DEFENCE] = 1;
				}
				o.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
			}
			break;
		case 4:
			if (damage > 0) {
				if (player.playerLevel[Constants.HITPOINTS] + damage > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
					if (player.playerLevel[Constants.HITPOINTS] < player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
						player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
					}
				} else {
					player.playerLevel[Constants.HITPOINTS] += damage;
				}
				player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			}
			break;
		}
		player.specEffect = 0;
		if (player.fightMode == 3) {//melee shared
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 0);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 1);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE / 3, 2);
			player.getPlayerAssistant().addSkillXP(damage / 3, 3);
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
			player.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
		} else {
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE, player.fightMode);
			player.getPlayerAssistant().addSkillXP(damage * CombatConstants.MELEE_EXP_RATE/3, 3);
			player.getPlayerAssistant().refreshSkill(player.fightMode);
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = player.playerId;
		PlayerHandler.players[i].killerId = player.playerId;
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if (player.killedBy != PlayerHandler.players[i].playerId) {
			player.totalPlayerDamageDealt = 0;
		}
		player.killedBy = PlayerHandler.players[i].playerId;
		applySmite(i, damage);
		switch (damageMask) {
		case 1:
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[player.playerId] += damage;
			player.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			o.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			break;

		case 2:
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[player.playerId] += damage;
			player.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			player.doubleHit = false;
			o.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
			break;
		}
		PlayerHandler.players[i].handleHitMask(damage);
	}

	public void applySmite(int index, int damage) {
		if (!player.getPrayer().prayerActive[23]) {
			return;
		}
		if (damage <= 0) {
			return;
		}
		if (PlayerHandler.players[index] != null) {
			Client c2 = (Client) PlayerHandler.players[index];
			c2.playerLevel[Constants.PRAYER] -= damage / 4;
			if (c2.playerLevel[Constants.PRAYER] <= 0) {
				c2.playerLevel[Constants.PRAYER] = 0;
				PrayerDrain.resetPrayers(c2);
			}
			c2.getPlayerAssistant().refreshSkill(Constants.PRAYER);
		}

	}

	public void fireProjectilePlayer() {
		if (player.oldPlayerIndex > 0) {
			if (PlayerHandler.players[player.oldPlayerIndex] != null) {
				player.projectileStage = 2;
				int pX = player.getX();
				int pY = player.getY();
				int oX = PlayerHandler.players[player.oldPlayerIndex].getX();
				int oY = PlayerHandler.players[player.oldPlayerIndex].getY();
				int offX = (pY - oY) * -1;
				int offY = (pX - oX) * -1;
				if (!player.msbSpec) {
					player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, RangeData.getProjectileSpeed(player), RangeData.getRangeProjectileGFX(player), 43, 31, -player.oldPlayerIndex - 1, MagicSpells.getStartDelay(player));
				} else if (player.msbSpec) {
					player.getPlayerAssistant().createPlayersProjectile2(pX, pY, offX, offY, 50, RangeData.getProjectileSpeed(player), RangeData.getRangeProjectileGFX(player), 43, 31, -player.oldPlayerIndex - 1, MagicSpells.getStartDelay(player), 10);
					player.msbSpec = false;
				}
			}
		}
	}

	public void resetPlayerAttack() {
		player.usingMagic = false;
		player.npcIndex = 0;
		player.faceUpdate(0);
		player.playerIndex = 0;
		player.getPlayerAssistant().resetFollow();
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
		if (PlayerHandler.players[player.playerIndex] == null) {
			return false;
		}
		if (player.inCw()) {
			return true;
		}
		if (player.playerIndex == player.playerId) {
			return false;
		}
		if (player.inPits && PlayerHandler.players[player.playerIndex].inPits) {
			return true;
		}
		if (PlayerHandler.players[player.playerIndex].inDuelArena() && player.duelStatus != 5 && !player.usingMagic) {
			if (player.duelingArena() || player.duelStatus == 5) {
				player.getPacketSender().sendMessage("You can't challenge inside the arena!");
				resetPlayerAttack();
				return false;
			}
			player.getDueling().requestDuel(player.playerIndex);
			return false;
		}
		if (player.duelStatus == 5 && PlayerHandler.players[player.playerIndex].duelStatus == 5) {
			if (PlayerHandler.players[player.playerIndex].duelingWith == player.getId()) {
				return true;
			} else {
				player.getPacketSender().sendMessage("This isn't your opponent!");
				return false;
			}
		}
		if (CastOnOther.castOnOtherSpells(player.castingSpellId)) {
			return true;
		}
		if (!PlayerHandler.players[player.playerIndex].inWild() && !PlayerHandler.players[player.playerIndex].inCwGame() && !CastOnOther.castOnOtherSpells(player.castingSpellId)) {
			player.getPacketSender().sendMessage("That player is not in the wilderness.");
			player.stopMovement();
			resetPlayerAttack();
			return false;
		}
		if (!player.inWild() && !PlayerHandler.players[player.playerIndex].inCwGame() && !CastOnOther.castOnOtherSpells(player.castingSpellId)) {
			player.getPacketSender().sendMessage("You are not in the wilderness.");
			player.stopMovement();
			resetPlayerAttack();
			return false;
		}
		if (CombatConstants.COMBAT_LEVEL_DIFFERENCE && !player.inCw()) {
			int combatDif1 = getCombatDifference(player.combatLevel, PlayerHandler.players[player.playerIndex].combatLevel);
			if (combatDif1 > player.wildLevel || combatDif1 > PlayerHandler.players[player.playerIndex].wildLevel) {
				player.getPacketSender().sendMessage("Your combat level difference is too great to attack that player here.");
				player.stopMovement();
				resetPlayerAttack();
				return false;
			}
		}
		if (CombatConstants.SINGLE_AND_MULTI_ZONES) {
			if (!Boundary.isIn(PlayerHandler.players[player.playerIndex], Boundary.MULTI)) { // single single zones
				if (PlayerHandler.players[player.playerIndex].underAttackBy != player.playerId && PlayerHandler.players[player.playerIndex].underAttackBy != 0) {
					player.getPacketSender().sendMessage("That player is already in combat.");
					player.stopMovement();
					resetPlayerAttack();
					return false;
				}
				if (PlayerHandler.players[player.playerIndex].playerId != player.underAttackBy && player.underAttackBy != 0 || player.underAttackBy2 > 0) {
					player.getPacketSender().sendMessage("You are already in combat.");
					player.stopMovement();
					resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}

	public int getRequiredDistance() {
		if (player.followId > 0 && player.freezeTimer <= 0) {
			return player.isMoving ? 3 : 2;
		}
		return 1;
	}

	public void applyRecoilNPC(Player c, int damage, int i) {
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

	public void applyRecoil(Player c2, int damage, int i) {
		if (damage > 0 && PlayerHandler.players[i].playerEquipment[c2.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			if (!c2.getHitUpdateRequired()) {
				c2.setHitDiff(recDamage);
				c2.setHitUpdateRequired(true);
			} else if (!c2.getHitUpdateRequired2()) {
				c2.setHitDiff2(recDamage);
				c2.setHitUpdateRequired2(true);
			}
			c2.dealDamage(recDamage);
			c2.updateRequired = true;
			removeRecoil(c2);
			c2.recoilHits += damage;
		}
	}

	public void removeRecoil(Player c2) {
		if (c2.recoilHits >= 400) {
			c2.getItemAssistant().removeItem(2550, c2.playerRing);
			c2.getItemAssistant().deleteItem(2550, c2.getItemAssistant().getItemSlot(2550), 1);
			c2.getPacketSender().sendMessage("Your ring of recoil shaters!");
			c2.recoilHits = 0;
		} else {
			c2.recoilHits++;
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

	public boolean checkSpecAmount(int weapon) {
		if (player.specAmount >= player.getSpecials().specAmount()) {
			player.specAmount -= player.getSpecials().specAmount();
			player.getItemAssistant().addSpecialBar(weapon);
			return true;
		}
		return false;
	}

	public int meleeMaxHit() {
		return MeleeMaxHit.calculateMeleeMaxHit(player);
	}

	public int calcDef() {
		return MeleeData.calculateMeleeDefence(player);
	}

	public int calcAtt() {
		return MeleeData.calculateMeleeAttack(player);
	}

	public void getPlayerAnimIndex() {
		MeleeData.getPlayerAnimIndex(player);
	}

	public int getHitDelay() {
		return MeleeData.getHitDelay(player);
	}

	public int getAttackDelay() {
		return MeleeData.getAttackDelay(player);
	}

	public int getWepAnim() {
		return MeleeData.getWeaponAnimation(player);
	}

	public int getBlockEmote() {
		return MeleeData.getBlockEmote(player);
	}

	public int rangeMaxHit() {
		return RangeMaxHit.rangeMaxHit(player);
	}

	public boolean checkMagicReqs(int spell) {
		return MagicRequirements.checkMagicReqs(player, spell, player.usingMagic);
	}

	public int calculateRangeDefence() {
		return RangeMaxHit.calculateRangeDefence(player);
	}

	public int calculateRangeAttack() {
		return RangeMaxHit.calculateRangeAttack(player);
	}

	public boolean usingBolts() {
		return RangeData.usingBolts(player);
	}

	public boolean properBolts() {
		return RangeData.properBolts(player);
	}

	public int mageDef() {
		return MagicMaxHit.mageDefenceBonus(player);
	}

	public int mageAtk() {
		return MagicMaxHit.mageAttackBonus(player);
	}

}
