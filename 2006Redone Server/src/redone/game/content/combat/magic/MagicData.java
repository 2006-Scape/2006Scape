package redone.game.content.combat.magic;

import redone.game.players.Client;
public class MagicData {

	public static final int[][] MAGIC_SPELLS = {
			// example {magicId, level req, animation, startGFX, projectile Id,
			// endGFX, maxhit, exp gained, rune 1, rune 1 amount, rune 2, rune 2
			// amount, rune 3, rune 3 amount, rune 4, rune 4 amount}

			// Modern Spells
			{ 1152, 1, 711, 90, 91, 92, 2, 5, 556, 1, 558, 1, 0, 0, 0, 0, 993 }, // wind
																					// strike
			{ 1154, 5, 711, 93, 94, 95, 4, 7, 555, 1, 556, 1, 558, 1, 0, 0, 211 }, // water
																					// strike
			{ 1156, 9, 711, 96, 97, 98, 6, 9, 557, 2, 556, 1, 558, 1, 0, 0, 0 },// earth
																				// strike
			{ 1158, 13, 711, 99, 100, 101, 8, 11, 554, 3, 556, 2, 558, 1, 0, 0,
					0 }, // fire
							// strike
			{ 1160, 17, 711, 117, 118, 119, 9, 13, 556, 2, 562, 1, 0, 0, 0, 0,
					0 }, // wind
							// bolt
			{ 1163, 23, 711, 120, 121, 122, 10, 16, 556, 2, 555, 2, 562, 1, 0,
					0, 0 }, // water
							// bolt
			{ 1166, 29, 711, 123, 124, 125, 11, 20, 556, 2, 557, 3, 562, 1, 0,
					0, 0 }, // earth
							// bolt
			{ 1169, 35, 711, 126, 127, 128, 12, 22, 556, 3, 554, 4, 562, 1, 0,
					0, 0 }, // fire
							// bolt
			{ 1172, 41, 711, 132, 133, 134, 13, 25, 556, 3, 560, 1, 0, 0, 0, 0,
					0 }, // wind
							// blast
			{ 1175, 47, 711, 135, 136, 137, 14, 28, 556, 3, 555, 3, 560, 1, 0,
					0, 0 }, // water
							// blast
			{ 1177, 53, 711, 138, 139, 140, 15, 31, 556, 3, 557, 4, 560, 1, 0,
					0, 0 }, // earth
							// blast
			{ 1181, 59, 711, 129, 130, 131, 16, 35, 556, 4, 554, 5, 560, 1, 0,
					0, 0 }, // fire
							// blast
			{ 1183, 62, 727, 158, 159, 160, 17, 36, 556, 5, 565, 1, 0, 0, 0, 0,
					0 }, // wind
							// wave
			{ 1185, 65, 727, 161, 162, 163, 18, 37, 556, 5, 555, 7, 565, 1, 0,
					0, 0 }, // water
							// wave
			{ 1188, 70, 727, 164, 165, 166, 19, 40, 556, 5, 557, 7, 565, 1, 0,
					0, 0 }, // earth
							// wave
			{ 1189, 75, 727, 155, 156, 157, 20, 42, 556, 5, 554, 7, 565, 1, 0,
					0, 0 }, // fire
							// wave

			{ 1153, 3, 716, 102, 103, 104, 0, 13, 555, 3, 557, 2, 559, 1, 0, 0,
					0 }, // confuse
			{ 1157, 11, 716, 105, 106, 107, 0, 20, 555, 3, 557, 2, 559, 1, 0,
					0, 0 }, // weaken
			{ 1161, 19, 716, 108, 109, 110, 0, 29, 555, 2, 557, 3, 559, 1, 0,
					0, 0 }, // curse
			{ 1542, 66, 729, 167, 168, 169, 0, 76, 557, 5, 555, 5, 566, 1, 0,
					0, 0 }, // vulnerability
			{ 1543, 73, 729, 170, 171, 172, 0, 83, 557, 8, 555, 8, 566, 1, 0,
					0, 0 }, // enfeeble
			{ 1562, 80, 729, 173, 174, 107, 0, 90, 557, 12, 555, 12, 556, 1, 0,
					0, 0 }, // stun

			{ 1572, 20, 710, 177, 178, 181, 0, 30, 557, 3, 555, 3, 561, 2, 0,
					0, 0 }, // bind
			{ 1582, 50, 710, 177, 178, 180, 2, 60, 557, 4, 555, 4, 561, 3, 0,
					0, 0 }, // snare
			{ 1592, 79, 710, 177, 178, 179, 4, 90, 557, 5, 555, 5, 561, 4, 0,
					0, 0 }, // entangle

			{ 1171, 39, 724, 145, 146, 147, 15, 25, 556, 2, 557, 2, 562, 1, 0, 0, 0 }, // crumble
			{ 1539, 50, 708, 87, 88, 89, 25, 42, 554, 5, 560, 1, 0, 0, 0, 0, 0 }, // iban
																					// blast
			{ 12037, 50, 1576, 327, 328, 329, 19, 30, 560, 1, 558, 4, 0, 0, 0,
					0, 0 }, // magic
							// dart

			{ 1190, 60, 811, 0, 0, 76, 20, 60, 554, 2, 565, 2, 556, 4, 0, 0, 0 }, // sara
																					// strike
			{ 1191, 60, 811, 0, 0, 77, 20, 60, 554, 1, 565, 2, 556, 4, 0, 0, 0 }, // cause
																					// of
																					// guthix
			{ 1192, 60, 811, 0, 0, 78, 20, 60, 554, 4, 565, 2, 556, 1, 0, 0, 0 }, // flames
																					// of
																					// zammy

			{ 12445, 85, 1819, 0, 344, 345, 0, 65, 563, 1, 562, 1, 560, 1, 0,
					0, 0 }, // teleblock

			// Ancient Spells
			{ 12939, 50, 1978, 0, 384, 385, 13, 30, 560, 2, 562, 2, 554, 1,
					556, 1, 0 }, // smoke
									// rush
			{ 12987, 52, 1978, 0, 378, 379, 14, 31, 560, 2, 562, 2, 566, 1,
					556, 1, 0 }, // shadow
									// rush
			{ 12901, 56, 1978, 0, 0, 373, 15, 33, 560, 2, 562, 2, 565, 1, 0, 0,
					0 }, // blood
							// rush
			{ 12861, 58, 1978, 0, 360, 361, 16, 34, 560, 2, 562, 2, 555, 2, 0,
					0, 0 }, // ice
							// rush
			{ 12963, 62, 1979, 0, 0, 389, 19, 36, 560, 2, 562, 4, 556, 2, 554,
					2, 0 }, // smoke
							// burst
			{ 13011, 64, 1979, 0, 0, 382, 20, 37, 560, 2, 562, 4, 556, 2, 566,
					2, 0 }, // shadow
							// burst
			{ 12919, 68, 1979, 0, 0, 376, 21, 39, 560, 2, 562, 4, 565, 2, 0, 0,
					0 }, // blood
							// burst
			{ 12881, 70, 1979, 0, 0, 363, 22, 40, 560, 2, 562, 4, 555, 4, 0, 0,
					0 }, // ice
							// burst
			{ 12951, 74, 1978, 0, 386, 387, 23, 42, 560, 2, 554, 2, 565, 2,
					556, 2, 0 }, // smoke
									// blitz
			{ 12999, 76, 1978, 0, 380, 381, 24, 43, 560, 2, 565, 2, 556, 2,
					566, 2, 0 }, // shadow
									// blitz
			{ 12911, 80, 1978, 0, 374, 375, 25, 45, 560, 2, 565, 4, 0, 0, 0, 0,
					0 }, // blood
							// blitz
			{ 12871, 82, 1978, 366, 0, 367, 26, 46, 560, 2, 565, 2, 555, 3, 0,
					0, 0 }, // ice
							// blitz
			{ 12975, 86, 1979, 0, 0, 391, 27, 48, 560, 4, 565, 2, 556, 4, 554,
					4, 0 }, // smoke
							// barrage
			{ 13023, 88, 1979, 0, 0, 383, 28, 49, 560, 4, 565, 2, 556, 4, 566,
					3, 0 }, // shadow
							// barrage
			{ 12929, 92, 1979, 0, 0, 377, 29, 51, 560, 4, 565, 4, 566, 1, 0, 0,
					0 }, // blood
							// barrage
			{ 12891, 94, 1979, 0, 0, 369, 30, 52, 560, 4, 565, 2, 555, 6, 0, 0,
					0 }, // ice
							// barrage

			{ -1, 80, 811, 301, 0, 0, 0, 0, 554, 3, 565, 3, 556, 3, 0, 0, 0 }, // charge
			{ -1, 21, 712, 112, 0, 0, 0, 10, 554, 3, 561, 1, 0, 0, 0, 0, 0 }, // low
																				// alch
			{ -1, 55, 713, 113, 0, 0, 0, 20, 554, 5, 561, 1, 0, 0, 0, 0, 0 }, // high
																				// alch
			{ -1, 33, 728, 142, 143, 144, 0, 35, 556, 1, 563, 1, 0, 0, 0, 0, 0 } // telegrab

	};

	public static String getSpellName(int id) {
		switch (id) {
		case 0:
			return "Air Strike";
		case 1:
			return "Water Strike";
		case 2:
			return "Earth Strike";
		case 3:
			return "Fire Strike";
		case 4:
			return "Air Bolt";
		case 5:
			return "Water Bolt";
		case 6:
			return "Earth Bolt";
		case 7:
			return "Fire Bolt";
		case 8:
			return "Air Blast";
		case 9:
			return "Water Blast";
		case 10:
			return "Earth Blast";
		case 11:
			return "Fire Blast";
		case 12:
			return "Air Wave";
		case 13:
			return "Water Wave";
		case 14:
			return "Earth Wave";
		case 15:
			return "Fire Wave";
		case 32:
			return "Shadow Rush";
		case 33:
			return "Smoke Rush";
		case 34:
			return "Blood Rush";
		case 35:
			return "Ice Rush";
		case 36:
			return "Shadow Burst";
		case 37:
			return "Smoke Burst";
		case 38:
			return "Blood Burst";
		case 39:
			return "Ice Burst";
		case 40:
			return "Shadow Blitz";
		case 41:
			return "Smoke Blitz";
		case 42:
			return "Blood Blitz";
		case 43:
			return "Ice Blitz";
		case 44:
			return "Shadow Barrage";
		case 45:
			return "Smoke Barrage";
		case 46:
			return "Blood Barrage";
		case 47:
			return "Ice Barrage";
		default:
			return "Select Spell";
		}
	}
	
	public static boolean multis(Client c) {
		switch (MAGIC_SPELLS[c.oldSpellId][0]) {
		case 12891:
		case 12881:
		case 13011:
		case 13023:
		case 12919: // blood spells
		case 12929:
		case 12963:
		case 12975:
			return true;
		}
		return false;

	}
	
	public static int getFreezeTime(Client c) {
		switch (MAGIC_SPELLS[c.oldSpellId][0]) {
		case 1572:
		case 12861: // ice rush
			return 10;

		case 1582:
		case 12881: // ice burst
			return 17;

		case 1592:
		case 12871: // ice blitz
			return 25;

		case 12891: // ice barrage
			return 33;

		default:
			return 0;
		}
	}

	public static int getStartHeight(Client c) {
		switch (MAGIC_SPELLS[c.spellId][0]) {
		case 1562: // stun
			return 25;

		case 12939:// smoke rush
			return 35;

		case 12987: // shadow rush
			return 38;

		case 12861: // ice rush
			return 15;

		case 12951: // smoke blitz
			return 38;

		case 12999: // shadow blitz
			return 25;

		case 12911: // blood blitz
			return 25;

		default:
			return 43;
		}
	}

	public static int getEndHeight(Client c) {
		switch (MAGIC_SPELLS[c.spellId][0]) {
		case 1562: // stun
			return 10;

		case 12939: // smoke rush
			return 20;

		case 12987: // shadow rush
			return 28;

		case 12861: // ice rush
			return 10;

		case 12951: // smoke blitz
			return 28;

		case 12999: // shadow blitz
			return 15;

		case 12911: // blood blitz
			return 10;

		default:
			return 31;
		}
	}

	public static int getStartDelay(Client c) {
		switch (MAGIC_SPELLS[c.spellId][0]) {
		case 1539:
			return 60;

		default:
			return 53;
		}
	}

	public static boolean godSpells(Client c) {
		switch (MAGIC_SPELLS[c.spellId][0]) {
		case 1190:
			return true;

		case 1191:
			return true;

		case 1192:
			return true;

		default:
			return false;
		}
	}

	public static int getEndGfxHeight(Client c) {
		switch (MAGIC_SPELLS[c.oldSpellId][0]) {
		case 12987:
		case 12901:
		case 12861:
		case 12445:
		case 1192:
		case 13011:
		case 12919:
		case 12881:
		case 12999:
		case 12911:
		case 12871:
		case 13023:
		case 12929:
		case 12891:
			return 0;

		default:
			return 100;
		}
	}

	public static int getStartGfxHeight(Client c) {
		switch (MAGIC_SPELLS[c.spellId][0]) {
		case 12871:
		case 12891:
			return 0;

		default:
			return 100;
		}
	}
	
	public static boolean fullVoidMage(Client c) {
		return c.playerEquipment[c.playerHat] == 11663
				&& c.playerEquipment[c.playerLegs] == 8840
				&& c.playerEquipment[c.playerChest] == 8839
				&& c.playerEquipment[c.playerHands] == 8842;
	}

}
