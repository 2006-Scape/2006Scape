package com.rebotted.game.content.combat.prayer;

import com.rebotted.game.players.Player;

public class PrayerDrain {

	public static void handlePrayerDrain(Player player) {
		player.getPrayer().usingPrayer = false;
		double toRemove = 0.0;
		for (int j = 0; j < PrayerData.prayerData.length; j++) {
			if (player.getPrayer().prayerActive[j]) {
				toRemove += PrayerData.prayerData[j] / 20;
				player.getPrayer().usingPrayer = true;
			}
		}
		if (toRemove > 0) {
			toRemove /= 1 + 0.035 * player.playerBonus[11];
		}
		PrayerData.prayerPoint -= toRemove;
		if (PrayerData.prayerPoint <= 0) {
			PrayerData.prayerPoint = 1.0 + PrayerData.prayerPoint;
			reducePrayerLevel(player);
		}

	}

	public static void reducePrayerLevel(Player c) {
		if (c.playerLevel[5] - 1 > 0) {
			c.playerLevel[5] -= 1;
		} else {
			c.getPacketSender().sendMessage(
					"You have run out of prayer points!");
			c.playerLevel[5] = 0;
			resetPrayers(c);
			c.getPrayer().prayerId = -1;
		}
		c.getPlayerAssistant().refreshSkill(5);
	}

	public static void resetPrayers(Player player) {
		for (int i = 0; i < player.getPrayer().prayerActive.length; i++) {
			player.getPrayer().prayerActive[i] = false;
			player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[i], 0);
		}
		player.headIcon = -1;
		player.getPlayerAssistant().requestUpdates();
	}

}
