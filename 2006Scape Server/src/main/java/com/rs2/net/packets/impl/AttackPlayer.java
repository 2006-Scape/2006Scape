package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.game.content.combat.CombatConstants;
import com.rs2.game.content.combat.magic.CastOnOther;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.range.RangeData;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

public class AttackPlayer implements PacketType {

	public static final int ATTACK_PLAYER = 73, MAGE_PLAYER = 249;

	@Override
	public void processPacket(Player player, Packet packet) {
		player.endCurrentTask();
		player.playerIndex = 0;
		player.npcIndex = 0;
		switch (packet.getOpcode()) {

		/**
		 * Attack player
		 **/
		case ATTACK_PLAYER:
			player.playerIndex = packet.readSignedWordBigEndian();
			if (PlayerHandler.players[player.playerIndex] == null) {
				break;
			}
			
			if (player.inDuelArena() && !player.duelingArena()) {
				player.getChallengePlayer().processPacket(player, packet);
			}

			if (player.respawnTimer > 0) {
				break;
			}

			if (player.autocastId > 0) {
				player.autocasting = true;
			}

			if (!player.autocasting && player.spellId > 0) {
				player.spellId = 0;
			}
			player.mageFollow = false;
			player.spellId = 0;
			player.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
			for (int bowId : RangeData.BOWS) {
				if (player.playerEquipment[player.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : RangeData.ARROWS) {
						if (player.playerEquipment[player.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : RangeData.OTHER_RANGE_WEAPONS) {
				if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			if (player.duelStatus == 5) {
				if (player.duelCount > 0) {
					player.getPacketSender().sendMessage("The duel hasn't started yet!");
					player.playerIndex = 0;
					return;
				}
				if (player.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : Constants.FUN_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						return;
					}
				}

				if (player.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					player.getPacketSender().sendMessage("Range has been disabled for this duel!");
					return;
				}
				if (player.duelRule[3] && !usingBow && !usingOtherRangeWeapons) {
					player.getPacketSender().sendMessage("Melee has been disabled for this duel!");
					return;
				}
			}

			if ((usingBow || player.autocasting) && player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[player.playerIndex].getX(), PlayerHandler.players[player.playerIndex].getY(), 6)) {
				player.usingBow = true;
				player.stopMovement();
			}

			if (usingOtherRangeWeapons && player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[player.playerIndex].getX(), PlayerHandler.players[player.playerIndex].getY(), 3)) {
				player.usingRangeWeapon = true;
				player.stopMovement();
			}
			if (!usingBow) {
				player.usingBow = false;
			}
			if (!usingOtherRangeWeapons) {
				player.usingRangeWeapon = false;
			}

			if (!usingCross && !usingArrows && usingBow && player.playerEquipment[player.playerWeapon] < 4212 && player.playerEquipment[player.playerWeapon] > 4223) {
				player.getPacketSender().sendMessage("You have run out of arrows!");
				return;
			}
			if (RangeData.correctBowAndArrows(player) < player.playerEquipment[player.playerArrows]
					&& CombatConstants.CORRECT_ARROWS
					&& usingBow
					&& !RangeData.usingCrystalBow(player)
					&& player.playerEquipment[player.playerWeapon] != 9185) {
				player.getPacketSender().sendMessage("You can't use " + DeprecatedItems.getItemName(player.playerEquipment[player.playerArrows]).toLowerCase() + "s with a " + DeprecatedItems.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase() + ".");
				player.stopMovement();
				player.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (player.playerEquipment[player.playerWeapon] == 9185 && !player.getCombatAssistant().properBolts()) {
				player.getPacketSender().sendMessage("You must use bolts with a crossbow.");
				player.stopMovement();
				player.getCombatAssistant().resetPlayerAttack();
				return;
			}
			if (player.getCombatAssistant().checkReqs()) {
				player.followId = player.playerIndex;
				if (!player.usingMagic && !usingBow && !usingOtherRangeWeapons) {
					player.followDistance = 1;
					player.getPlayerAssistant().followPlayer();
				}
			}
			break;

		/**
		 * Attack player with magic
		 **/
		case MAGE_PLAYER:
			if (!player.mageAllowed) {
				player.mageAllowed = true;
				break;
			}

			player.playerIndex = packet.readSignedWordA();
			int castingSpellId = packet.readSignedWordBigEndian();
			player.castingSpellId = castingSpellId;
			player.usingMagic = false;

			boolean teleother = CastOnOther.castOnOtherSpells(castingSpellId);

			if (PlayerHandler.players[player.playerIndex] == null) {
				break;
			}

			if (player.respawnTimer > 0) {
				break;
			}

			if (player.playerRights == 3) {
				player.getPacketSender().sendMessage(
						"Casting Spell id: " + castingSpellId + ".");
			}

			if (teleother) {
				if (player.inTrade) {
					player.getTrading().declineTrade(true);
				}
				if (player.inWild()
						&& player.wildLevel > Constants.NO_TELEPORT_WILD_LEVEL) {
					player.getPacketSender().sendMessage(
							"You can't teleport above level "
									+ Constants.NO_TELEPORT_WILD_LEVEL
									+ " in the wilderness.");
					break;
				}
				if (player.duelStatus == 5) {
					player.getPacketSender().sendMessage("You can't do that in a duel.");
					player.usingMagic = false;
					player.stopMovement();
					player.getCombatAssistant().resetPlayerAttack();
					return;
				}
			}

			switch (player.castingSpellId) {
			case 12425:
				CastOnOther.teleOtherDistance(player, 0, player.playerIndex);
				break;
			case 12435:
				CastOnOther.teleOtherDistance(player, 1, player.playerIndex);
				break;
			case 12455:
				CastOnOther.teleOtherDistance(player, 2, player.playerIndex);
				break;
			}

			if (teleother) {
				player.stopMovement();
				player.getCombatAssistant().resetPlayerAttack();
			}

			for (int i = 0; i < MagicData.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == MagicData.MAGIC_SPELLS[i][0]) {
					player.spellId = i;
					player.usingMagic = true;
					break;
				}
			}

			if (player.autocasting) {
				player.autocasting = false;
			}

			if (!teleother) {
				if (!player.getCombatAssistant().checkReqs()) {
					break;
				}

				if (player.duelStatus == 5) {
					if (player.duelCount > 0) {
						player.getPacketSender().sendMessage(
								"The duel hasn't started yet!");
						player.playerIndex = 0;
						return;
					}
					if (player.duelRule[4]) {
						player.getPacketSender().sendMessage(
								"Magic has been disabled in this duel!");
						return;
					}
					if (MagicData.MAGIC_SPELLS[player.spellId][0] == 12445)
					{
						player.getPacketSender().sendMessage(
								"You can't do that in a duel.");
						player.usingMagic = false;
						player.stopMovement();
						player.getCombatAssistant().resetPlayerAttack();
						return;
					}
				}

				for (int r = 0; r < player.REDUCE_SPELLS.length; r++) { // reducing
																	// spells,
																	// confuse
																	// etc
					if (PlayerHandler.players[player.playerIndex].REDUCE_SPELLS[r] == MagicData.MAGIC_SPELLS[player.spellId][0]) {
						if (System.currentTimeMillis()
								- PlayerHandler.players[player.playerIndex].reduceSpellDelay[r] < PlayerHandler.players[player.playerIndex].REDUCE_SPELL_TIME[r]) {
							player.getPacketSender()
									.sendMessage(
											"That player is currently immune to this spell.");
							player.usingMagic = false;
							player.stopMovement();
							player.getCombatAssistant().resetPlayerAttack();
						}
						break;
					}
				}

				if (System.currentTimeMillis()
						- PlayerHandler.players[player.playerIndex].teleBlockDelay < PlayerHandler.players[player.playerIndex].teleBlockLength
						&& MagicData.MAGIC_SPELLS[player.spellId][0] == 12445) {
					player.getPacketSender().sendMessage(
							"That player is already affected by this spell.");
					player.usingMagic = false;
					player.stopMovement();
					player.getCombatAssistant().resetPlayerAttack();
				}
			}
			break;
		}

	}

}
