package redone.game.content.combat.melee;

import redone.Constants;
import redone.game.content.combat.magic.MagicData;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

public class MeleeData {

	public static boolean fullVoidMelee(Client c) {
		return c.playerEquipment[c.playerHat] == 11665
				&& c.playerEquipment[c.playerLegs] == 8840
				&& c.playerEquipment[c.playerChest] == 8839
				&& c.playerEquipment[c.playerHands] == 8842;
	}

	public static int calculateMeleeAttack(Client c) {
		int attackLevel = c.playerLevel[0];
		// 2, 5, 11, 18, 19
		if (c.getPrayer().prayerActive[2]) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.05;
		} else if (c.getPrayer().prayerActive[7]) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.1;
		} else if (c.getPrayer().prayerActive[15]) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.15;
		} else if (c.getPrayer().prayerActive[24]) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.15;
		} else if (c.getPrayer().prayerActive[25]) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.2;
		}
		if (fullVoidMelee(c)) {
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.1;
		}
		attackLevel *= c.specAccuracy;
		// c.sendMessage("Attack: " + (attackLevel +
		// (c.playerBonus[bestMeleeAtk()] * 2)));
		int i = c.playerBonus[bestMeleeAtk(c)];
		i += c.bonusAttack;
		if (c.playerEquipment[c.playerAmulet] == 11128
				&& c.playerEquipment[c.playerWeapon] == 6528) {
			i *= 1.30;
		}
		return (int) (attackLevel + attackLevel * 0.15 + (i + i * 0.05));
	}

	public static int bestMeleeAtk(Client c) {
		if (c.playerBonus[0] > c.playerBonus[1]
				&& c.playerBonus[0] > c.playerBonus[2]) {
			return 0;
		}
		if (c.playerBonus[1] > c.playerBonus[0]
				&& c.playerBonus[1] > c.playerBonus[2]) {
			return 1;
		}
		return c.playerBonus[2] <= c.playerBonus[1]
				|| c.playerBonus[2] <= c.playerBonus[0] ? 0 : 2;
	}

	public static int calculateMeleeDefence(Client c) {
		int defenceLevel = c.playerLevel[1];
		int i = c.playerBonus[bestMeleeDef(c)];
		if (c.getPrayer().prayerActive[0]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
		} else if (c.getPrayer().prayerActive[5]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
		} else if (c.getPrayer().prayerActive[13]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
		} else if (c.getPrayer().prayerActive[24]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
		} else if (c.getPrayer().prayerActive[25]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
		}
		return (int) (defenceLevel + defenceLevel * 0.15 + (i + i * 0.05));
	}

	public static int bestMeleeDef(Client c) {
		if (c.playerBonus[5] > c.playerBonus[6]
				&& c.playerBonus[5] > c.playerBonus[7]) {
			return 5;
		}
		if (c.playerBonus[6] > c.playerBonus[5]
				&& c.playerBonus[6] > c.playerBonus[7]) {
			return 6;
		}
		return c.playerBonus[7] <= c.playerBonus[5]
				|| c.playerBonus[7] <= c.playerBonus[6] ? 5 : 7;
	}

	/**
	 * Weapon and magic attack speed!
	 **/

	public static int getAttackDelay(Client c) {
		String s = ItemAssistant.getItemName(c.playerEquipment[Constants.WEAPON]).toLowerCase();
		if (c.usingMagic) {
			switch (MagicData.MAGIC_SPELLS[c.spellId][0]) {
			case 12871: // ice blitz
			case 13023: // shadow barrage
			case 12891: // ice barrage
				return 5;

			default:
				return 5;
			}
		}
		if (c.playerEquipment[c.playerWeapon] == -1) {
			return 4;// unarmed
		}

		switch (c.playerEquipment[c.playerWeapon]) {
		case 11235:
			return 9;
		case 11730:
			return 4;
		case 6528:
			return 7;
		}

		if (s.endsWith("greataxe")) {
			return 7;
		} else if (s.equals("torags hammers")) {
			return 5;
		} else if (s.equals("guthans warspear")) {
			return 5;
		} else if (s.equals("veracs flail")) {
			return 5;
		} else if (s.equals("ahrims staff")) {
			return 6;
		} else if (s.contains("staff")) {
			if (s.contains("zamarok") || s.contains("guthix")
					|| s.contains("saradomian") || s.contains("slayer")
					|| s.contains("ancient")) {
				return 4;
			} else {
				return 5;
			}
		} else if (s.contains("bow")) {
			if (s.contains("composite") || s.equals("seercull")) {
				return 5;
			} else if (s.contains("aril")) {
				return 4;
			} else if (s.contains("Ogre")) {
				return 8;
			} else if (s.contains("short") || s.contains("hunt")
					|| s.contains("sword")) {
				return 4;
			} else if (s.contains("long") || s.contains("crystal")) {
				return 6;
			} else if (s.contains("'bow")) {
				return 7;
			}

			return 5;
		} else if (s.contains("dagger")) {
			return 4;
		} else if (s.contains("godsword") || s.contains("2h")) {
			return 6;
		} else if (s.contains("longsword")) {
			return 5;
		} else if (s.contains("sword")) {
			return 4;
		} else if (s.contains("scimitar")) {
			return 4;
		} else if (s.contains("mace")) {
			return 5;
		} else if (s.contains("battleaxe")) {
			return 6;
		} else if (s.contains("pickaxe")) {
			return 5;
		} else if (s.contains("thrownaxe")) {
			return 5;
		} else if (s.contains("axe")) {
			return 5;
		} else if (s.contains("warhammer")) {
			return 6;
		} else if (s.contains("2h")) {
			return 7;
		} else if (s.contains("spear")) {
			return 5;
		} else if (s.contains("claw")) {
			return 4;
		} else if (s.contains("halberd")) {
			return 7;
		} else if (s.equals("granite maul")) {
			return 7;
		} else if (s.equals("toktz-xil-ak")) {
			return 4;
		} else if (s.equals("tzhaar-ket-em")) {
			return 5;
		} else if (s.equals("tzhaar-ket-om")) {
			return 7;
		} else if (s.equals("toktz-xil-ek")) {
			return 4;
		} else if (s.equals("toktz-xil-ul")) {
			return 4;
		} else if (s.equals("toktz-mej-tal")) {
			return 6;
		} else if (s.contains("whip")) {
			return 4;
		} else if (s.contains("dart")) {
			return 3;
		} else if (s.contains("knife")) {
			return 3;
		} else if (s.contains("javelin")) {
			return 6;
		}
		return 5;
	}

	/**
	 * Weapon stand, walk, run, etc emotes
	 **/

	public static void getPlayerAnimIndex(Client c) {
		String weaponName = ItemAssistant.getItemName(
				c.playerEquipment[Constants.WEAPON]).toLowerCase();
		c.playerStandIndex = 0x328;
		c.playerTurnIndex = 0x337;
		c.playerWalkIndex = 0x333;
		c.playerTurn180Index = 0x334;
		c.playerTurn90CWIndex = 0x335;
		c.playerTurn90CCWIndex = 0x336;
		c.playerRunIndex = 0x338;

		if (weaponName.contains("halberd") || weaponName.contains("guthan")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("dharok")) {
			c.playerStandIndex = 0x811;
			c.playerWalkIndex = 0x67F;
			c.playerRunIndex = 0x680;
			return;
		}
		if (weaponName.contains("ahrim")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("verac")) {
			c.playerStandIndex = 1832;
			c.playerWalkIndex = 1830;
			c.playerRunIndex = 1831;
			return;
		}
		if (weaponName.contains("wand") || weaponName.contains("staff")) {
			c.playerStandIndex = 809;
			c.playerRunIndex = 1210;
			c.playerWalkIndex = 1146;
			return;
		}
		if (weaponName.contains("karil")) {
			c.playerStandIndex = 2074;
			c.playerWalkIndex = 2076;
			c.playerRunIndex = 2077;
			return;
		}
		if (weaponName.contains("2h sword")) {
			c.playerStandIndex = 2561;
			c.playerWalkIndex = 2562;
			c.playerRunIndex = 2563;
			return;
		}
		if (weaponName.contains("bow")) {
			c.playerStandIndex = 808;
			c.playerWalkIndex = 819;
			c.playerRunIndex = 824;
			return;
		}

		switch (c.playerEquipment[c.playerWeapon]) {
		case 4151:
			c.playerStandIndex = 1832;
			c.playerWalkIndex = 1660;
			c.playerRunIndex = 1661;
			break;
		case 6528:
			c.playerStandIndex = 0x811;
			c.playerWalkIndex = 2064;
			c.playerRunIndex = 1664;
			break;
		case 4153:
			c.playerStandIndex = 1662;
			c.playerWalkIndex = 1663;
			c.playerRunIndex = 1664;
			break;
		case 11694:
		case 11696:
		case 11730:
		case 11698:
		case 11700:
			c.playerStandIndex = 4300;
			c.playerWalkIndex = 4306;
			c.playerRunIndex = 4305;
			break;
		case 1305:
			c.playerStandIndex = 809;
			break;
		}
	}

	/**
	 * Weapon emotes
	 **/

	public static int getWeaponAnimation(Client c) {
		String weaponName = ItemAssistant.getItemName(
				c.playerEquipment[Constants.WEAPON]).toLowerCase();
		if (c.playerEquipment[c.playerWeapon] <= 0) {
			switch (c.fightMode) {
			case 0:
				return 422;
			case 2:
				return 423;
			case 1:
				return 451;
			}
		}
		if (weaponName.contains("knife") || weaponName.contains("dart")
				|| weaponName.contains("javelin")
				|| weaponName.contains("thrownaxe")) {
			return 806;
		}
		if (weaponName.contains("halberd")) {
			return 440;
		}
		if (weaponName.contains("dragon dagger")) {
			return 402;
		}
		if (weaponName.endsWith("dagger")) {
			return 412;
		}
		if (weaponName.contains("2h sword") || weaponName.contains("godsword")
				|| weaponName.contains("aradomin sword")) {
			switch (c.fightMode) {
			case 4:
				return 406;
			case 0:
			case 2:
			case 1:
				return 407;
			}
		}
		if (weaponName.contains("sword")) {
			switch (c.fightMode) {
			case 0:
			case 1:
				return 412;
			case 2:
				return 451;
			}
		}
		if (weaponName.contains("karil")) {
			return 2075;
		}
		if (weaponName.contains("bow") && !weaponName.contains("'bow")) {
			return 426;
		}
		if (weaponName.contains("'bow")) {
			return 4230;
		}

		switch (c.playerEquipment[c.playerWeapon]) { // if you don't want to
														// use
														// strings
		case 6522:
			return 2614;
		case 4153: // granite maul
			return 1665;
		case 4726: // guthan
			return 2080;
		case 4747: // torag
			return 0x814;
		case 4718: // dharok
			return 2067;
		case 4710: // ahrim
			return 406;
		case 4755: // verac
			return 2062;
		case 4734: // karil
			return 2075;
		case 4151:
			return 1658;
		case 6528:
			return 2661;
		default:
			return 451;
		}
	}

	/**
	 * Block emotes
	 */
	public static int getBlockEmote(Client c) {
		if (c.playerEquipment[c.playerShield] >= 8844
				&& c.playerEquipment[c.playerShield] <= 8850) {
			return 4177;
		}
		switch (c.playerEquipment[c.playerWeapon]) {
		case 4755:
			return 2063;

		case 4153:
			return 1666;

		case 4151:
			return 1659;

		case 11694:
		case 11698:
		case 11700:
		case 11696:
		case 11730:
			return -1;
		default:
			return 404;
		}
	}

	/**
	 * How long it takes to hit your enemy
	 **/
	public static int getHitDelay(Client c) {
		String weaponName = ItemAssistant.getItemName(
				c.playerEquipment[Constants.WEAPON]).toLowerCase();
		if (c.usingMagic) {
			switch (MagicData.MAGIC_SPELLS[c.spellId][0]) {
			case 12891:
				return 4;
			case 12871:
				return 6;
			default:
				return 4;
			}
		} else {

			if (weaponName.contains("knife") || weaponName.contains("dart")
					|| weaponName.contains("javelin")
					|| weaponName.contains("thrownaxe")) {
				return 3;
			}
			if (weaponName.contains("cross") || weaponName.contains("c'bow")) {
				return 4;
			}
			if (weaponName.contains("bow") && !c.dbowSpec) {
				return 4;
			} else if (c.dbowSpec) {
				return 4;
			}

			switch (c.playerEquipment[c.playerWeapon]) {
			case 6522: // Toktz-xil-ul
				return 3;

			default:
				return 2;
			}
		}
	}
}
