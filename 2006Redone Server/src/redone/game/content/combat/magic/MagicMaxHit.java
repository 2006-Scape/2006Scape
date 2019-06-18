package redone.game.content.combat.magic;

import redone.game.players.Client;

public class MagicMaxHit {

	public static int mageAttackBonus(Client c) {
		int magicBonus = c.playerLevel[6];
		if (MagicData.fullVoidMage(c)) {
			magicBonus += c.getLevelForXP(c.playerXP[6]) * 0.2;
		}
		if (c.getPrayer().prayerActive[4]) {
			magicBonus *= 1.05;
		} else if (c.getPrayer().prayerActive[12]) {
			magicBonus *= 1.10;
		} else if (c.getPrayer().prayerActive[20]) {
			magicBonus *= 1.15;
		}
		return magicBonus + c.playerBonus[3] * 2;
	}

	public static int mageDefenceBonus(Client c) {
		int defenceBonus = c.playerLevel[1] / 2 + c.playerLevel[6] / 2;
		if (c.getPrayer().prayerActive[0]) {
			defenceBonus += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
		} else if (c.getPrayer().prayerActive[3]) {
			defenceBonus += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
		} else if (c.getPrayer().prayerActive[9]) {
			defenceBonus += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
		} else if (c.getPrayer().prayerActive[18]) {
			defenceBonus += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
		} else if (c.getPrayer().prayerActive[19]) {
			defenceBonus += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
		}
		return defenceBonus + c.playerBonus[8] + c.playerBonus[8] / 3;
	}

}
