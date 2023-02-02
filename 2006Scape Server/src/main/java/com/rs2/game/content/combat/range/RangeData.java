package com.rs2.game.content.combat.range;

import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.*;

public class RangeData {

	public final static int[] BOWS = { 9185,LONGBOW,OAK_LONGBOW,WILLOW_LONGBOW,MAPLE_LONGBOW,YEW_LONGBOW,MAGIC_LONGBOW,SHORTBOW,OAK_SHORTBOW,WILLOW_SHORTBOW,MAPLE_SHORTBOW,YEW_SHORTBOW,MAGIC_SHORTBOW,NEW_CRYSTAL_BOW,CRYSTAL_BOW_FULL,CRYSTAL_BOW_910,CRYSTAL_BOW_810,CRYSTAL_BOW_710,CRYSTAL_BOW_610,CRYSTAL_BOW_510,CRYSTAL_BOW_410,CRYSTAL_BOW_310,CRYSTAL_BOW_210,CRYSTAL_BOW_110,SEERCULL,KARILS_CROSSBOW,KARILS_XBOW_100,KARILS_XBOW_75,KARILS_XBOW_50,KARILS_XBOW_25


	};
	public final static int[] ARROWS = { BRONZE_ARROW,BRONZE_ARROWP,IRON_ARROW,IRON_ARROWP,STEEL_ARROW,STEEL_ARROWP,MITHRIL_ARROW,MITHRIL_ARROWP,ADAMANT_ARROW,ADAMANT_ARROWP,RUNE_ARROW,RUNE_ARROWP,BOLT_RACK,9140,9141,9142,9143,9144,9240,9241,9242,9243,9244,9245,BROAD_ARROWS,BROAD_ARROWS_4160,BROAD_ARROWS_4172
	};
	public final static int[] NO_ARROW_DROP = { NEW_CRYSTAL_BOW,CRYSTAL_BOW_FULL,CRYSTAL_BOW_910,CRYSTAL_BOW_810,CRYSTAL_BOW_710,CRYSTAL_BOW_610,CRYSTAL_BOW_510,CRYSTAL_BOW_410,CRYSTAL_BOW_310,CRYSTAL_BOW_210,CRYSTAL_BOW_110,KARILS_CROSSBOW,KARILS_XBOW_100,KARILS_XBOW_75,KARILS_XBOW_50,KARILS_XBOW_25
	};
	public final static int[] OTHER_RANGE_WEAPONS = { IRON_KNIFE,BRONZE_KNIFE,STEEL_KNIFE,MITHRIL_KNIFE,ADAMANT_KNIFE,RUNE_KNIFE,BLACK_KNIFE,BRONZE_DART,IRON_DART,STEEL_DART,MITHRIL_DART,ADAMANT_DART,RUNE_DART,BRONZE_JAVELIN,IRON_JAVELIN,STEEL_JAVELIN,MITHRIL_JAVELIN,ADAMANT_JAVELIN,RUNE_JAVELIN,BRONZE_THROWNAXE,IRON_THROWNAXE,STEEL_THROWNAXE,MITHRIL_THROWNAXE,ADAMNT_THROWNAXE,RUNE_THROWNAXE,TOKTZXILUL };

	public static boolean usingCrystalBow(Player c) {
		return c.playerEquipment[c.playerWeapon] >= NEW_CRYSTAL_BOW
				&& c.playerEquipment[c.playerWeapon] <= CRYSTAL_BOW_110;
	}

	public static boolean usingBolts(Player c) {
		return c.playerEquipment[c.playerArrows] >= 9130
				&& c.playerEquipment[c.playerArrows] <= 9145
				|| c.playerEquipment[c.playerArrows] >= 9230
				&& c.playerEquipment[c.playerArrows] <= 9245;
	}

	public static boolean properBolts(Player c) {
		return c.playerEquipment[c.playerArrows] >= 9140
				&& c.playerEquipment[c.playerArrows] <= 9144
				|| c.playerEquipment[c.playerArrows] >= 9240
				&& c.playerEquipment[c.playerArrows] <= 9244;
	}

	public static boolean usingHally(Player c) {
		switch (c.playerEquipment[c.playerWeapon]) {
			case BRONZE_HALBERD:
			case IRON_HALBERD:
			case STEEL_HALBERD:
			case BLACK_HALBERD:
			case MITHRIL_HALBERD:
			case ADAMANT_HALBERD:
			case RUNE_HALBERD:
			case DRAGON_HALBERD:
				return true;
		}
		return false;
	}

	public static boolean usingDart(Player player) {
		switch (player.playerEquipment[player.playerWeapon]) {
			case BRONZE_DART:
			case IRON_DART:
			case STEEL_DART:
			case MITHRIL_DART:
			case ADAMANT_DART:
			case RUNE_DART:
				return true;
		}
		return false;
	}

	public static boolean usingLongbow(Player player) {
		if (usingCrystalBow(player)) {
			return true;
		}
		switch (player.playerEquipment[player.playerWeapon]) {
			case LONGBOW:
			case OAK_LONGBOW:
			case WILLOW_LONGBOW:
			case MAPLE_LONGBOW:
			case YEW_LONGBOW:
			case MAGIC_LONGBOW:
				return true;
		}
		return false;
	}


	public static int correctBowAndArrows(Player c) {
		if (usingBolts(c)) {
			return -1;
		}
		switch (c.playerEquipment[c.playerWeapon]) {
			case LONGBOW:
			case SHORTBOW:
				return BRONZE_ARROW;

			case OAK_SHORTBOW:
			case OAK_LONGBOW:
				return IRON_ARROW;

			case WILLOW_LONGBOW:
			case WILLOW_SHORTBOW:
				return STEEL_ARROW;

			case MAPLE_LONGBOW:
			case MAPLE_SHORTBOW:
				return MITHRIL_ARROW;

			case YEW_LONGBOW:
			case YEW_SHORTBOW:
				return ADAMANT_ARROW;

			case MAGIC_LONGBOW:
			case MAGIC_SHORTBOW:
				if (c.playerEquipment[c.playerArrows] == RUNE_ARROW) {
					return RUNE_ARROW;
				} else if (c.playerEquipment[c.playerArrows] == BROAD_ARROWS_4172) {
					return BROAD_ARROWS_4172;
				}

			case KARILS_CROSSBOW:
			case KARILS_XBOW_75:
			case KARILS_XBOW_50:
			case KARILS_XBOW_25:
				return BOLT_RACK;
		}
		return -1;
	}

	public static int getRangeStartGFX(Player c) {
		switch (c.rangeItemUsed) {

			case IRON_KNIFE:
				return 220;
			case BRONZE_KNIFE:
				return 219;
			case STEEL_KNIFE:
				return 221;
			case MITHRIL_KNIFE:
				return 223;
			case ADAMANT_KNIFE:
				return 224;
			case RUNE_KNIFE:
				return 225;
			case BLACK_KNIFE:
				return 222;

			case BRONZE_DART:
				return 232;
			case IRON_DART:
				return 233;
			case STEEL_DART:
				return 234;
			case MITHRIL_DART:
				return 235;
			case ADAMANT_DART:
				return 236;
			case RUNE_DART:
				return 237;

			case BRONZE_JAVELIN:
				return 206;
			case IRON_JAVELIN:
				return 207;
			case STEEL_JAVELIN:
				return 208;
			case MITHRIL_JAVELIN:
				return 209;
			case ADAMANT_JAVELIN:
				return 210;
			case RUNE_JAVELIN:
				return 211;

			case BRONZE_THROWNAXE:
				return 42;
			case IRON_THROWNAXE:
				return 43;
			case STEEL_THROWNAXE:
				return 44;
			case MITHRIL_THROWNAXE:
				return 45;
			case ADAMNT_THROWNAXE:
				return 46;
			case RUNE_THROWNAXE:
				return 48;

			case BRONZE_ARROW:
				return 19;
			case IRON_ARROW:
				return 18;
			case STEEL_ARROW:
				return 20;
			case MITHRIL_ARROW:
				return 21;
			case ADAMANT_ARROW:
				return 22;
			case RUNE_ARROW:
				return 24;

			case NEW_CRYSTAL_BOW:
			case CRYSTAL_BOW_FULL:
			case CRYSTAL_BOW_910:
			case CRYSTAL_BOW_810:
			case CRYSTAL_BOW_710:
			case CRYSTAL_BOW_610:
			case CRYSTAL_BOW_510:
			case CRYSTAL_BOW_410:
			case CRYSTAL_BOW_310:
			case CRYSTAL_BOW_210:
			case CRYSTAL_BOW_110:
				return 250;

		}
		return -1;
	}

	public static int getRangeProjectileGFX(Player c) {
		if (c.bowSpecShot > 0) {
			switch (c.rangeItemUsed) {
				default:
					return 249;
			}
		}
		if (c.playerEquipment[c.playerWeapon] == 9185) { //TODO magic numbwr rune cbow
			return 27;
		}
		switch (c.rangeItemUsed) {

			case IRON_KNIFE:
				return 213;
			case BRONZE_KNIFE:
				return 212;
			case STEEL_KNIFE:
				return 214;
			case MITHRIL_KNIFE:
				return 216;
			case ADAMANT_KNIFE:
				return 217;
			case RUNE_KNIFE:
				return 218;
			case BLACK_KNIFE:
				return 215;

			case BRONZE_DART:
				return 226;
			case IRON_DART:
				return 227;
			case STEEL_DART:
				return 228;
			case MITHRIL_DART:
				return 229;
			case ADAMANT_DART:
				return 230;
			case RUNE_DART:
				return 231;

			case BRONZE_JAVELIN:
				return 200;
			case IRON_JAVELIN:
				return 201;
			case STEEL_JAVELIN:
				return 202;
			case MITHRIL_JAVELIN:
				return 203;
			case ADAMANT_JAVELIN:
				return 204;
			case RUNE_JAVELIN:
				return 205;

			case TOKTZXILUL:
				return 442;

			case BRONZE_THROWNAXE:
				return 36;
			case IRON_THROWNAXE:
				return 35;
			case STEEL_THROWNAXE:
				return 37;
			case MITHRIL_THROWNAXE:
				return 38;
			case ADAMNT_THROWNAXE:
				return 39;
			case RUNE_THROWNAXE:
				return 40;

			case BRONZE_ARROW:
				return 10;

			case IRON_ARROW:
				return 9;

			case STEEL_ARROW:
				return 11;

			case MITHRIL_ARROW:
				return 12;

			case ADAMANT_ARROW:
				return 13;

			case RUNE_ARROW:
				return 15;

			case BOLT_RACK:
				return 27;

			case NEW_CRYSTAL_BOW:
			case CRYSTAL_BOW_FULL:
			case CRYSTAL_BOW_910:
			case CRYSTAL_BOW_810:
			case CRYSTAL_BOW_710:
			case CRYSTAL_BOW_610:
			case CRYSTAL_BOW_510:
			case CRYSTAL_BOW_410:
			case CRYSTAL_BOW_310:
			case CRYSTAL_BOW_210:
			case CRYSTAL_BOW_110:
				return 249;

		}
		return -1;
	}

	public static int getProjectileSpeed(Player c) {
		return 70;
	}

	public static int getProjectileShowDelay(Player c) {
		switch (c.playerEquipment[c.playerWeapon]) {
			case IRON_KNIFE:
			case BRONZE_KNIFE:
			case STEEL_KNIFE:
			case MITHRIL_KNIFE:
			case ADAMANT_KNIFE:
			case RUNE_KNIFE:
			case BLACK_KNIFE:

			case BRONZE_DART:
			case IRON_DART:
			case STEEL_DART:
			case MITHRIL_DART:
			case ADAMANT_DART:
			case RUNE_DART:

			case BRONZE_JAVELIN:
			case IRON_JAVELIN:
			case STEEL_JAVELIN:
			case MITHRIL_JAVELIN:
			case ADAMANT_JAVELIN:
			case RUNE_JAVELIN:

			case BRONZE_THROWNAXE:
			case IRON_THROWNAXE:
			case STEEL_THROWNAXE:
			case MITHRIL_THROWNAXE:
			case ADAMNT_THROWNAXE:
			case RUNE_THROWNAXE:

			case KARILS_CROSSBOW:
			case 9185: //TODO Rune cbow Magic Number
			case KARILS_XBOW_75:
			case KARILS_XBOW_50:
			case KARILS_XBOW_25:
				return 15;

			default:
				return 5;
		}
	}

	public static boolean fullVoidRange(Player c) {
		return c.playerEquipment[c.playerHat] == 11664
				&& c.playerEquipment[c.playerLegs] == 8840
				&& c.playerEquipment[c.playerChest] == 8839
				&& c.playerEquipment[c.playerHands] == 8842;
	}

}