package redone.game.content.combat.melee;

import redone.game.players.Client;

public class MeleeMaxHit {

	public static int calculateMeleeMaxHit(Client c) {
		double maxHit = 0;
		int strBonus = c.playerBonus[10];
		int strength = c.playerLevel[2];
		int lvlForXP = c.getLevelForXP(c.playerXP[2]);
		if (c.getPrayer().prayerActive[1]) {
			strength += (int) (lvlForXP * .05);
		} else if (c.getPrayer().prayerActive[6]) {
			strength += (int) (lvlForXP * .10);
		} else if (c.getPrayer().prayerActive[14]) {
			strength += (int) (lvlForXP * .15);
		} else if (c.getPrayer().prayerActive[24]) {
			strength += (int) (lvlForXP * .18);
		} else if (c.getPrayer().prayerActive[25]) {
			strength += (int) (lvlForXP * .23);
		}
		if (c.playerEquipment[c.playerHat] == 2526
				&& c.playerEquipment[c.playerChest] == 2520
				&& c.playerEquipment[c.playerLegs] == 2522) {
			maxHit += maxHit * 10 / 100;
		}
		maxHit += 1.05D + strBonus * strength * 0.00175D;
		maxHit += strength * 0.11D;
		if (c.playerEquipment[c.playerWeapon] == 4718
				&& c.playerEquipment[c.playerHat] == 4716
				&& c.playerEquipment[c.playerChest] == 4720
				&& c.playerEquipment[c.playerLegs] == 4722) {
			maxHit += (c.getPlayerAssistant().getLevelForXP(c.playerXP[3]) - c.playerLevel[3]) / 2;
		}
		if (c.specDamage > 1) {
			maxHit = (int) (maxHit * c.specDamage);
		}
		if (maxHit < 0) {
			maxHit = 1;
		}
		if (MeleeData.fullVoidMelee(c)) {
			maxHit = (int) (maxHit * 1.10);
		}
		if (c.playerEquipment[c.playerAmulet] == 11128
				&& c.playerEquipment[c.playerWeapon] == 6528) {
			maxHit *= 1.20;
		}
		return (int) Math.floor(maxHit);
	}

}
