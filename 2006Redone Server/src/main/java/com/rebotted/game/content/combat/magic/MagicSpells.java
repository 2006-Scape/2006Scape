package com.rebotted.game.content.combat.magic;

import com.rebotted.game.content.combat.CombatConstants;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.util.Misc;
import com.rebotted.world.Boundary;

public class MagicSpells extends MagicData {
	
	public static void appendMultiBarrage(Player c, int playerId, boolean splashed) {
		if (PlayerHandler.players[playerId] != null) {
			Player c2 = (Client) PlayerHandler.players[playerId];
			if (c2.isDead || c2.respawnTimer > 0) {
				return;
			}
			if (checkMultiBarrageReqs(c, playerId)) {
				c.barrageCount++;
				if (Misc.random(mageAtk(c)) > Misc.random(mageDef(c))
						&& !c.magicFailed) {
					if (getEndGfxHeight(c) == 100) { // end GFX
						c2.gfx100(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
					} else {
						c2.gfx0(MagicData.MAGIC_SPELLS[c.oldSpellId][5]);
					}
					int damage = Misc
							.random(MagicData.MAGIC_SPELLS[c.oldSpellId][6]);
					if (c2.getPrayer().prayerActive[12]) {
						damage *= (int) .60;
					}
					if (c2.playerLevel[3] - damage < 0) {
						damage = c2.playerLevel[3];
					}
					c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[c.oldSpellId][7] + damage * CombatConstants.MAGIC_EXP_RATE, 6);
					c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[c.oldSpellId][7] + damage / 3, 3);
					// Server.playerHandler.players[playerId].setHitDiff(damage);
					// Server.playerHandler.players[playerId].setHitUpdateRequired(true);
					PlayerHandler.players[playerId].handleHitMask(damage);
					// Server.playerHandler.players[playerId].playerLevel[3] -=
					// damage;
					PlayerHandler.players[playerId].dealDamage(damage);
					PlayerHandler.players[playerId].damageTaken[c.playerId] += damage;
					c2.getPlayerAssistant().refreshSkill(3);
					c.totalPlayerDamageDealt += damage;
					multiSpellEffect(c, playerId, damage);
				} else {
					c2.gfx100(85);
					c.getPacketSender().sendSound(SoundList.MAGE_FAIL, 100,
							0);
				}
			}
		}
	}

	public static void multiSpellEffect(Player c, int playerId, int damage) {
		switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
		case 13011:
		case 13023:
			if (System.currentTimeMillis()
					- PlayerHandler.players[playerId].reduceStat > 35000) {
				PlayerHandler.players[playerId].reduceStat = System
						.currentTimeMillis();
				PlayerHandler.players[playerId].playerLevel[0] -= PlayerHandler.players[playerId]
						.getLevelForXP(PlayerHandler.players[playerId].playerXP[0]) * 10 / 100;
			}
			break;
		case 12919: // blood spells
		case 12929:
			int heal = damage / 4;
			if (c.playerLevel[3] + heal >= c.getPlayerAssistant()
					.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] = c.getPlayerAssistant().getLevelForXP(
						c.playerXP[3]);
			} else {
				c.playerLevel[3] += heal;
			}
			c.getPlayerAssistant().refreshSkill(3);
			break;
		case 12891:
		case 12881:
			if (PlayerHandler.players[playerId].freezeTimer < -4) {
				PlayerHandler.players[playerId].freezeTimer = getFreezeTime(c);
				PlayerHandler.players[playerId].stopMovement();
			}
			break;
		}
	}
	
	public static boolean checkMultiBarrageReqs(Player c, int i) {
		if (PlayerHandler.players[i] == null) {
			return false;
		}
		if (i == c.playerId) {
			return false;
		}
		if (c.inPits && PlayerHandler.players[i].inPits) {
			return true;
		}
		if (!PlayerHandler.players[i].inWild()) {
			return false;
		}
		if (CombatConstants.COMBAT_LEVEL_DIFFERENCE) {
			int combatDif1 = c.getCombatAssistant().getCombatDifference(c.combatLevel,
					PlayerHandler.players[i].combatLevel);
			if (combatDif1 > c.wildLevel
					|| combatDif1 > PlayerHandler.players[i].wildLevel) {
				c.getPacketSender()
						.sendMessage(
								"Your combat level difference is too great to attack that player here.");
				return false;
			}
		}

		if (CombatConstants.SINGLE_AND_MULTI_ZONES) {
			if (!Boundary.isIn(PlayerHandler.players[i], Boundary.MULTI)) { // single combat zones
				if (PlayerHandler.players[i].underAttackBy != c.playerId && PlayerHandler.players[i].underAttackBy != 0) {
					return false;
				}
				if (PlayerHandler.players[i].playerId != c.underAttackBy && c.underAttackBy != 0) {
					c.getPacketSender().sendMessage("You are already in combat.");
					return false;
				}
			}
		}
		return true;
	}
	
	public static int mageAtk(Player c) {
		return MagicMaxHit.mageAttackBonus(c);
	}
	
	public static int mageDef(Player c) {
		return MagicMaxHit.mageDefenceBonus(c);
	}
}