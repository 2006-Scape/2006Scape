package com.rs2.game.content.combat.magic;

import com.rs2.Constants;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;

public class MagicRequirements {

	public static boolean wearingStaff(Player player, int runeId) {
		int wep = player.playerEquipment[player.playerWeapon];
		switch (runeId) {
		case 554: // Fire runes
			if (wep == 1387 || wep == 1393 || wep == 1401 || wep == 3053 || wep == 3054) {
				return true;
			}
			break;
		case 555: // Water runes
			if (wep == 1383 || wep == 1395 || wep == 1403 || wep == 6562 || wep == 6563) {
				return true;
			}
			break;
		case 556: // Air runes
			if (wep == 1381 || wep == 1397 || wep == 1405) {
				return true;
			}
			break;
		case 557: // Earth runes
			if (wep == 1385 || wep == 1399 || wep == 3053 || wep == 3054 || wep == 6562 || wep == 6563) {
				return true;
			}
			break;
		}
		return false;
	}

	public static boolean checkMagicReqs(Player c, int spell) {
		return checkMagicReqs(c, spell, true);
	}

	public static boolean checkMagicReqs(Player c, int spell, boolean runesRequired) {
		int[] spellData = MagicData.MAGIC_SPELLS[spell];
		if (runesRequired) { // check for runes
			if (
				!c.getItemAssistant().playerHasItem(spellData[8], spellData[9]) && !wearingStaff(c, spellData[8])
				|| !c.getItemAssistant().playerHasItem(spellData[10], spellData[11]) && !wearingStaff(c, spellData[10])
				|| !c.getItemAssistant().playerHasItem(spellData[12], spellData[13]) && !wearingStaff(c, spellData[12])
				|| !c.getItemAssistant().playerHasItem(spellData[14], spellData[15]) && !wearingStaff(c, spellData[14])
			) {
				c.getPacketSender().sendMessage("You don't have the required runes to cast this spell.");
				return false;
			}
		}

		if (c.playerIndex > 0) {
			if (PlayerHandler.players[c.playerIndex] != null) {
				for (int r = 0; r < c.REDUCE_SPELLS.length; r++) { // reducing
																	// spells,
																	// confuse
																	// etc
					if (PlayerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == MagicData.MAGIC_SPELLS[spell][0]) {
						c.reduceSpellId = r;
						if (System.currentTimeMillis()
								- PlayerHandler.players[c.playerIndex].reduceSpellDelay[c.reduceSpellId] > PlayerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[c.reduceSpellId]) {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = true;
						} else {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = false;
						}
						break;
					}
				}
				if (!PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId]) {
					c.getPacketSender().sendMessage(
							"That player is currently immune to this spell.");
					c.usingMagic = false;
					c.stopMovement();
					c.getCombatAssistant().resetPlayerAttack();
					return false;
				}
			}
		}

		int staffRequired = getStaffNeeded(c);
		if (staffRequired > 0 && runesRequired) { // staff
																			// required
			if (c.playerEquipment[c.playerWeapon] != staffRequired) {
				c.getPacketSender()
						.sendMessage(
								"You need a "
										+ DeprecatedItems.getItemName(
												staffRequired).toLowerCase()
										+ " to cast this spell.");
				return false;
			}
		}

		// check magic level
		if (c.playerLevel[Constants.MAGIC] < MagicData.MAGIC_SPELLS[spell][1]) {
			c.getPacketSender().sendMessage(
					"You need to have a magic level of "
							+ MagicData.MAGIC_SPELLS[spell][1]
							+ " to cast this spell.");
			return false;
		}
		if (runesRequired) {
			if (MagicData.MAGIC_SPELLS[spell][8] > 0) { // deleting runes
				if (!wearingStaff(c, MagicData.MAGIC_SPELLS[spell][8])) {
					c.getItemAssistant().deleteItem(
							MagicData.MAGIC_SPELLS[spell][8],
							c.getItemAssistant().getItemSlot(
									MagicData.MAGIC_SPELLS[spell][8]),
							MagicData.MAGIC_SPELLS[spell][9]);
				}
			}
			if (MagicData.MAGIC_SPELLS[spell][10] > 0) {
				if (!wearingStaff(c, MagicData.MAGIC_SPELLS[spell][10])) {
					c.getItemAssistant().deleteItem(
							MagicData.MAGIC_SPELLS[spell][10],
							c.getItemAssistant().getItemSlot(
									MagicData.MAGIC_SPELLS[spell][10]),
							MagicData.MAGIC_SPELLS[spell][11]);
				}
			}
			if (MagicData.MAGIC_SPELLS[spell][12] > 0) {
				if (!wearingStaff(c, MagicData.MAGIC_SPELLS[spell][12])) {
					c.getItemAssistant().deleteItem(
							MagicData.MAGIC_SPELLS[spell][12],
							c.getItemAssistant().getItemSlot(
									MagicData.MAGIC_SPELLS[spell][12]),
							MagicData.MAGIC_SPELLS[spell][13]);
				}
			}
			if (MagicData.MAGIC_SPELLS[spell][14] > 0) {
				if (!wearingStaff(c, MagicData.MAGIC_SPELLS[spell][14])) {
					c.getItemAssistant().deleteItem(
							MagicData.MAGIC_SPELLS[spell][14],
							c.getItemAssistant().getItemSlot(
									MagicData.MAGIC_SPELLS[spell][14]),
							MagicData.MAGIC_SPELLS[spell][15]);
				}
			}
		}
		return true;
	}

	public static int getStaffNeeded(Player c) {
		switch (MagicData.MAGIC_SPELLS[c.spellId][0]) {
		case 1539:
			return 1409;

		case 12037:
			return 4170;

		case 1190:
			return 2415;

		case 1191:
			return 2416;

		case 1192:
			return 2417;

		default:
			return 0;
		}
	}

}
