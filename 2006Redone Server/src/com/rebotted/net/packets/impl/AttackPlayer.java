package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.game.content.combat.magic.CastOnOther;
import com.rebotted.game.content.combat.magic.MagicData;
import com.rebotted.game.content.combat.range.RangeData;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;

/**
 * Attack Player
 **/
public class AttackPlayer implements PacketType {

	public static final int ATTACK_PLAYER = 73, MAGE_PLAYER = 249;

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		c.endCurrentTask();
		c.playerIndex = 0;
		c.npcIndex = 0;
		switch (packetType) {

		/**
		 * Attack player
		 **/
		case ATTACK_PLAYER:
			c.playerIndex = c.getInStream().readSignedWordBigEndian();
			if (PlayerHandler.players[c.playerIndex] == null) {
				break;
			}
			
			if (c.inDuelArena() && !c.duelingArena()) {
				c.getChallengePlayer().processPacket(c, packetType, packetSize);
			}

			if (c.respawnTimer > 0) {
				break;
			}

			if (c.autocastId > 0) {
				c.autocasting = true;
			}

			if (!c.autocasting && c.spellId > 0) {
				c.spellId = 0;
			}
			c.mageFollow = false;
			c.spellId = 0;
			c.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
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
			if (c.duelStatus == 5) {
				if (c.duelCount > 0) {
					c.getPacketSender().sendMessage(
							"The duel hasn't started yet!");
					c.playerIndex = 0;
					return;
				}
				if (c.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : GameConstants.FUN_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						c.getPacketSender().sendMessage(
								"You can only use fun weapons in this duel!");
						return;
					}
				}

				if (c.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					c.getPacketSender().sendMessage(
							"Range has been disabled in this duel!");
					return;
				}
				if (c.duelRule[3] && !usingBow && !usingOtherRangeWeapons) {
					c.getPacketSender().sendMessage(
							"Melee has been disabled in this duel!");
					return;
				}
			}

			if ((usingBow || c.autocasting)
					&& c.goodDistance(c.getX(), c.getY(),
							PlayerHandler.players[c.playerIndex].getX(),
							PlayerHandler.players[c.playerIndex].getY(), 6)) {
				c.usingBow = true;
				c.stopMovement();
			}

			if (usingOtherRangeWeapons
					&& c.goodDistance(c.getX(), c.getY(),
							PlayerHandler.players[c.playerIndex].getX(),
							PlayerHandler.players[c.playerIndex].getY(), 3)) {
				c.usingRangeWeapon = true;
				c.stopMovement();
			}
			if (!usingBow) {
				c.usingBow = false;
			}
			if (!usingOtherRangeWeapons) {
				c.usingRangeWeapon = false;
			}

			if (!usingCross && !usingArrows && usingBow
					&& c.playerEquipment[c.playerWeapon] < 4212
					&& c.playerEquipment[c.playerWeapon] > 4223) {
				c.getPacketSender().sendMessage(
						"You have run out of arrows!");
				return;
			}
			if (RangeData.correctBowAndArrows(c) < c.playerEquipment[c.playerArrows]
					&& GameConstants.CORRECT_ARROWS
					&& usingBow
					&& !RangeData.usingCrystalBow(c)
					&& c.playerEquipment[c.playerWeapon] != 9185) {
				c.getPacketSender().sendMessage(
						"You can't use "
								+ ItemAssistant.getItemName(
										c.playerEquipment[c.playerArrows])
										.toLowerCase()
								+ "s with a "
								+ ItemAssistant.getItemName(
										c.playerEquipment[c.playerWeapon])
										.toLowerCase() + ".");
				c.stopMovement();
				c.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 9185
					&& !c.getCombatAssistant().properBolts()) {
				c.getPacketSender().sendMessage(
						"You must use bolts with a crossbow.");
				c.stopMovement();
				c.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (c.getCombatAssistant().checkReqs()) {
				c.followId = c.playerIndex;
				if (!c.usingMagic && !usingBow && !usingOtherRangeWeapons) {
					c.followDistance = 1;
					c.getPlayerAssistant().followPlayer();
				}
				if (c.attackTimer <= 0) {
					// c.getPacketDispatcher().sendMessage("Tried to attack...");
					// c.getCombat().attackPlayer(c.playerIndex);
					// c.attackTimer++;
				}
			}
			break;

		/**
		 * Attack player with magic
		 **/
		case MAGE_PLAYER:
			if (!c.mageAllowed) {
				c.mageAllowed = true;
				break;
			}

			c.playerIndex = c.getInStream().readSignedWordA();
			int castingSpellId = c.getInStream().readSignedWordBigEndian();
			c.castingSpellId = castingSpellId;
			c.usingMagic = false;

			boolean teleother = CastOnOther.castOnOtherSpells(castingSpellId);

			if (PlayerHandler.players[c.playerIndex] == null) {
				break;
			}

			if (c.respawnTimer > 0) {
				break;
			}

			if (c.playerRights == 3) {
				c.getPacketSender().sendMessage(
						"Casting Spell id: " + castingSpellId + ".");
			}

			if (teleother) {
				if (c.inTrade) {
					c.getTrading().declineTrade(true);
				}
				if (c.inWild()
						&& c.wildLevel > GameConstants.NO_TELEPORT_WILD_LEVEL) {
					c.getPacketSender().sendMessage(
							"You can't teleport above level "
									+ GameConstants.NO_TELEPORT_WILD_LEVEL
									+ " in the wilderness.");
					break;
				}
			}

			switch (c.castingSpellId) {
			case 12425:
				CastOnOther.teleOtherDistance(c, 0, c.playerIndex);
				break;
			case 12435:
				CastOnOther.teleOtherDistance(c, 1, c.playerIndex);
				break;
			case 12455:
				CastOnOther.teleOtherDistance(c, 2, c.playerIndex);
				break;
			}

			if (teleother) {
				c.stopMovement();
				c.getCombatAssistant().resetPlayerAttack();
			}

			for (int i = 0; i < MagicData.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == MagicData.MAGIC_SPELLS[i][0]) {
					c.spellId = i;
					c.usingMagic = true;
					break;
				}
			}

			if (c.autocasting) {
				c.autocasting = false;
			}

			if (!teleother) {
				if (!c.getCombatAssistant().checkReqs()) {
					break;
				}

				if (c.duelStatus == 5) {
					if (c.duelCount > 0) {
						c.getPacketSender().sendMessage(
								"The duel hasn't started yet!");
						c.playerIndex = 0;
						return;
					}
					if (c.duelRule[4]) {
						c.getPacketSender().sendMessage(
								"Magic has been disabled in this duel!");
						return;
					}
				}

				for (int r = 0; r < c.REDUCE_SPELLS.length; r++) { // reducing
																	// spells,
																	// confuse
																	// etc
					if (PlayerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == MagicData.MAGIC_SPELLS[c.spellId][0]) {
						if (System.currentTimeMillis()
								- PlayerHandler.players[c.playerIndex].reduceSpellDelay[r] < PlayerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[r]) {
							c.getPacketSender()
									.sendMessage(
											"That player is currently immune to this spell.");
							c.usingMagic = false;
							c.stopMovement();
							c.getCombatAssistant().resetPlayerAttack();
						}
						break;
					}
				}

				if (System.currentTimeMillis()
						- PlayerHandler.players[c.playerIndex].teleBlockDelay < PlayerHandler.players[c.playerIndex].teleBlockLength
						&& MagicData.MAGIC_SPELLS[c.spellId][0] == 12445) {
					c.getPacketSender().sendMessage(
							"That player is already affected by this spell.");
					c.usingMagic = false;
					c.stopMovement();
					c.getCombatAssistant().resetPlayerAttack();
				}
			}
			break;
		}

	}

}
