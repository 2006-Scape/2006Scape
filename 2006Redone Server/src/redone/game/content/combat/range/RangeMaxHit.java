package redone.game.content.combat.range;

import redone.game.players.Client;

public class RangeMaxHit {
	
	public static int calculateRangeDefence(Client c) {
		int defenceLevel = c.playerLevel[1];
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
		return defenceLevel + c.playerBonus[9] + c.playerBonus[9] / 2;
	}

	public static int calculateRangeAttack(Client c) {
		int rangeLevel = c.playerLevel[4];
		rangeLevel *= c.specAccuracy;
		if (RangeData.fullVoidRange(c)) {
			rangeLevel += c.getLevelForXP(c.playerXP[c.playerRanged]) * 0.1;
		}
		if (c.getPrayer().prayerActive[3]) {
			rangeLevel *= 1.05;
		} else if (c.getPrayer().prayerActive[11]) {
			rangeLevel *= 1.10;
		} else if (c.getPrayer().prayerActive[19]) {
			rangeLevel *= 1.15;
		}
		// dbow spec
		if (RangeData.fullVoidRange(c) && c.specAccuracy > 1.15) {
			rangeLevel *= 1.75;
		}
		return (int) (rangeLevel + c.playerBonus[4] * 1.95);
	}

	public static int rangeMaxHit(Client c) {
		int rangeLevel = c.playerLevel[4];
		int itemUsed = getRangeStr(c.usingBow ? c.lastArrowUsed : c.lastWeaponUsed);
		double modifier = 1.00;
		if (c.getPrayer().prayerActive[3]) {
			modifier *= 1.05;
		} else if (c.getPrayer().prayerActive[11]) {
			modifier *= 1.10;
		} else if (c.getPrayer().prayerActive[19]) {
			modifier *= 1.15;
		}
		if (RangeData.fullVoidRange(c)) {
			modifier *= 1.20;
		}
		double e = Math.floor(rangeLevel * modifier);
		if (c.fightMode == 0) {
			e = (e + 3.0);
		}
		double darkbow = 1.0;
		if (c.usingSpecial) {
			if (c.playerEquipment[3] == 11235) {
				if (c.lastArrowUsed == 11212) {
					darkbow = 1.5;
				} else {
					darkbow = 1.3;
				}
			}
		}
		double max = (1.3 + e / 10 + itemUsed / 80 + e * itemUsed / 640) * darkbow;
		return (int) max;
	}

	public static int getRangeStr(int i) {
		int str = 0;
		int[][] data = { { 877, 10 }, { 9140, 46 }, { 9145, 36 }, { 9141, 64 },
				{ 9142, 82 }, { 9143, 100 }, { 9144, 115 }, { 9236, 14 },
				{ 9237, 30 }, { 9238, 48 }, { 9239, 66 }, { 9240, 83 },
				{ 9241, 85 }, { 9242, 103 }, { 9243, 105 }, { 9244, 117 },
				{ 9245, 120 }, { 882, 7 }, { 884, 10 }, { 886, 16 },
				{ 888, 22 }, { 890, 31 }, { 892, 49 }, { 4740, 55 },
				{ 11212, 60 }, { 806, 1 }, { 807, 3 }, { 808, 4 }, { 809, 7 },
				{ 810, 10 }, { 811, 14 }, { 11230, 20 }, { 864, 3 },
				{ 863, 4 }, { 865, 7 }, { 866, 10 }, { 867, 14 }, { 868, 24 },
				{ 825, 6 }, { 826, 10 }, { 827, 12 }, { 828, 18 }, { 829, 28 },
				{ 830, 42 }, { 800, 5 }, { 801, 7 }, { 802, 11 }, { 803, 16 },
				{ 804, 23 }, { 805, 36 }, { 9976, 0 }, { 9977, 15 },
				{ 4212, 70 }, { 4214, 70 }, { 4215, 70 }, { 4216, 70 },
				{ 4217, 70 }, { 4218, 70 }, { 4219, 70 }, { 4220, 70 },
				{ 4221, 70 }, { 4222, 70 }, { 4223, 70 }, { 6522, 49 },
				{ 10034, 15 }, };
		for (int[] element : data) {
			if (i == element[0]) {
				str = element[1];
			}
		}
		return str;
	}

}
