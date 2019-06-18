package redone.game.content.combat.prayer;

import redone.game.players.Client;

public class PrayerDrain {

	public static void handlePrayerDrain(Client c) {
		c.getPrayer().usingPrayer = false;
		double toRemove = 0.0;
		for (int j = 0; j < PrayerData.prayerData.length; j++) {
			if (c.getPrayer().prayerActive[j]) {
				toRemove += PrayerData.prayerData[j] / 20;
				c.getPrayer().usingPrayer = true;
			}
		}
		if (toRemove > 0) {
			toRemove /= 1 + 0.035 * c.playerBonus[11];
		}
		PrayerData.prayerPoint -= toRemove;
		if (PrayerData.prayerPoint <= 0) {
			PrayerData.prayerPoint = 1.0 + PrayerData.prayerPoint;
			reducePrayerLevel(c);
		}

	}

	public static void reducePrayerLevel(Client c) {
		if (c.playerLevel[5] - 1 > 0) {
			c.playerLevel[5] -= 1;
		} else {
			c.getActionSender().sendMessage(
					"You have run out of prayer points!");
			c.playerLevel[5] = 0;
			resetPrayers(c);
			c.getPrayer().prayerId = -1;
		}
		c.getPlayerAssistant().refreshSkill(5);
	}

	public static void resetPrayers(Client c) {
		for (int i = 0; i < c.getPrayer().prayerActive.length; i++) {
			c.getPrayer().prayerActive[i] = false;
			c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[i], 0);
		}
		c.headIcon = -1;
		c.getPlayerAssistant().requestUpdates();
	}

}
