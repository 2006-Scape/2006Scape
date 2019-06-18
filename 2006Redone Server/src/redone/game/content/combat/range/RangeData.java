package redone.game.content.combat.range;

import redone.game.players.Client;

public class RangeData {

	public final static int[] BOWS = { 9185, 839, 845, 847, 851, 855, 859, 841,
			843, 849, 853, 857, 861, 4212, 4214, 4215, 11235, 4216, 4217, 4218,
			4219, 4220, 4221, 4222, 4223, 6724, 4734, 4934, 4935, 4936, 4937 };
	public final static int[] ARROWS = { 882, 884, 886, 888, 890, 892, 4740,
			11212, 9140, 9141, 4142, 9143, 9144, 9240, 9241, 9242, 9243, 9244,
			9245, 4150, 4160, 4172 };
	public final static int[] NO_ARROW_DROP = { 4212, 4214, 4215, 4216, 4217,
			4218, 4219, 4220, 4221, 4222, 4223, 4734, 4934, 4935, 4936, 4937 };
	public final static int[] OTHER_RANGE_WEAPONS = { 863, 864, 865, 866, 867,
			868, 869, 806, 807, 808, 809, 810, 811, 825, 826, 827, 828, 829,
			830, 800, 801, 802, 803, 804, 805, 6522 };
	
	public static boolean usingDbow(Client c) {
		return c.playerEquipment[c.playerWeapon] == 11235;
	}
	
	public static boolean usingCrystalBow(Client c) {
		return c.playerEquipment[c.playerWeapon] >= 4212
				&& c.playerEquipment[c.playerWeapon] <= 4223;
	}

	public static boolean usingBolts(Client c) {
		return c.playerEquipment[c.playerArrows] >= 9130
				&& c.playerEquipment[c.playerArrows] <= 9145
				|| c.playerEquipment[c.playerArrows] >= 9230
				&& c.playerEquipment[c.playerArrows] <= 9245;
	}

	public static boolean properBolts(Client c) {
		return c.playerEquipment[c.playerArrows] >= 9140
				&& c.playerEquipment[c.playerArrows] <= 9144
				|| c.playerEquipment[c.playerArrows] >= 9240
				&& c.playerEquipment[c.playerArrows] <= 9244;
	}
	
	public static boolean usingHally(Client c) {
		switch (c.playerEquipment[c.playerWeapon]) {
		case 3190:
		case 3192:
		case 3194:
		case 3196:
		case 3198:
		case 3200:
		case 3202:
		case 3204:
			return true;

		default:
			return false;
		}
	}

	public static int correctBowAndArrows(Client c) {
		if (usingBolts(c)) {
			return -1;
		}
		switch (c.playerEquipment[c.playerWeapon]) {

		case 839:
		case 841:
			return 882;

		case 843:
		case 845:
			return 884;

		case 847:
		case 849:
			return 886;

		case 851:
		case 853:
			return 888;

		case 855:
		case 857:
			return 890;

		case 859:
		case 861:
			if (c.playerEquipment[c.playerArrows] == 892) {
				return 892;
			} else if (c.playerEquipment[c.playerArrows] == 4172) {
				return 4172;
			}

		case 4734:
		case 4935:
		case 4936:
		case 4937:
			return 4740;

		case 11235:
			return 11212;
		}
		return -1;
	}

	public static int getRangeStartGFX(Client c) {
		switch (c.rangeItemUsed) {

		case 863:
			return 220;
		case 864:
			return 219;
		case 865:
			return 221;
		case 866: // knives
			return 223;
		case 867:
			return 224;
		case 868:
			return 225;
		case 869:
			return 222;

		case 806:
			return 232;
		case 807:
			return 233;
		case 808:
			return 234;
		case 809: // darts
			return 235;
		case 810:
			return 236;
		case 811:
			return 237;

		case 825:
			return 206;
		case 826:
			return 207;
		case 827: // javelin
			return 208;
		case 828:
			return 209;
		case 829:
			return 210;
		case 830:
			return 211;

		case 800:
			return 42;
		case 801:
			return 43;
		case 802:
			return 44; // axes
		case 803:
			return 45;
		case 804:
			return 46;
		case 805:
			return 48;

		case 882:
			return 19;

		case 884:
			return 18;

		case 886:
			return 20;

		case 888:
			return 21;

		case 890:
			return 22;

		case 892:
			return 24;

		case 11212:
			return 26;

		case 4212:
		case 4214:
		case 4215:
		case 4216:
		case 4217:
		case 4218:
		case 4219:
		case 4220:
		case 4221:
		case 4222:
		case 4223:
			return 250;

		}
		return -1;
	}

	public static int getRangeProjectileGFX(Client c) {
		if (c.dbowSpec) {
			return 672;
		}
		if (c.bowSpecShot > 0) {
			switch (c.rangeItemUsed) {
			default:
				return 249;
			}
		}
		if (c.playerEquipment[c.playerWeapon] == 9185) {
			return 27;
		}
		switch (c.rangeItemUsed) {

		case 863:
			return 213;
		case 864:
			return 212;
		case 865:
			return 214;
		case 866: // knives
			return 216;
		case 867:
			return 217;
		case 868:
			return 218;
		case 869:
			return 215;

		case 806:
			return 226;
		case 807:
			return 227;
		case 808:
			return 228;
		case 809: // darts
			return 229;
		case 810:
			return 230;
		case 811:
			return 231;

		case 825:
			return 200;
		case 826:
			return 201;
		case 827: // javelin
			return 202;
		case 828:
			return 203;
		case 829:
			return 204;
		case 830:
			return 205;

		case 6522: // Toktz-xil-ul
			return 442;

		case 800:
			return 36;
		case 801:
			return 35;
		case 802:
			return 37; // axes
		case 803:
			return 38;
		case 804:
			return 39;
		case 805:
			return 40;

		case 882:
			return 10;

		case 884:
			return 9;

		case 886:
			return 11;

		case 888:
			return 12;

		case 890:
			return 13;

		case 892:
			return 15;

		case 11212:
			return 17;

		case 4740: // bolt rack
			return 27;

		case 4212:
		case 4214:
		case 4215:
		case 4216:
		case 4217:
		case 4218:
		case 4219:
		case 4220:
		case 4221:
		case 4222:
		case 4223:
			return 249;

		}
		return -1;
	}

	public static int getProjectileSpeed(Client c) {
		if (c.dbowSpec) {
			return 100;
		}
		return 70;
	}

	public static int getProjectileShowDelay(Client c) {
		switch (c.playerEquipment[c.playerWeapon]) {
		case 863:
		case 864:
		case 865:
		case 866: // knives
		case 867:
		case 868:
		case 869:

		case 806:
		case 807:
		case 808:
		case 809: // darts
		case 810:
		case 811:

		case 825:
		case 826:
		case 827: // javelin
		case 828:
		case 829:
		case 830:

		case 800:
		case 801:
		case 802:
		case 803: // axes
		case 804:
		case 805:

		case 4734:
		case 9185:
		case 4935:
		case 4936:
		case 4937:
			return 15;

		default:
			return 5;
		}
	}
	
	public static boolean fullVoidRange(Client c) {
		return c.playerEquipment[c.playerHat] == 11664
				&& c.playerEquipment[c.playerLegs] == 8840
				&& c.playerEquipment[c.playerChest] == 8839
				&& c.playerEquipment[c.playerHands] == 8842;
	}

}
