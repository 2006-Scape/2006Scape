package redone.game.content.combat.prayer;

import redone.Constants;
import redone.game.content.music.sound.SoundList;
import redone.game.players.Client;

public class ActivatePrayers {

	public static void activatePrayer(Client c, int i) {
		if (c.duelRule[7]) {
			for (int p = 0; p < c.getPrayer().PRAYER.length; p++) { // reset
																	// prayer
																	// glows
				c.getPrayer().prayerActive[p] = false;
				c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[p],
						0);
			}
			c.getActionSender().sendMessage(
					"Prayer has been disabled in this duel!");
			return;
		}
		if (i == 24 && c.playerLevel[1] < 65) {
			c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[i], 0);
			c.getActionSender().sendMessage(
					"You may not use this prayer yet.");
			return;
		}
		if (i == 25 && c.playerLevel[1] < 70) {
			c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[i], 0);
			c.getActionSender().sendMessage(
					"You may not use this prayer yet.");
			return;
		}
		int[] defencePrayer = { 0, 5, 13, 24, 25 };
		int[] strengthPrayer = { 1, 6, 14, 24, 25 };
		int[] attackPrayer = { 2, 7, 15, 24, 25 };
		int[] rangePrayer = { 3, 11, 19 };
		int[] magePrayer = { 4, 12, 20 };

		if (c.playerLevel[5] > 0 || !Constants.PRAYER_POINTS_REQUIRED) {
			if (c.getPlayerAssistant().getLevelForXP(c.playerXP[5]) >= c
					.getPrayer().PRAYER_LEVEL_REQUIRED[i]
					|| !Constants.PRAYER_LEVEL_REQUIRED) {
				boolean headIcon = false;
				switch (i) {
				case 0:
				case 5:
				case 13:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < defencePrayer.length; j++) {
							if (defencePrayer[j] != i) {
								c.getPrayer().prayerActive[defencePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[defencePrayer[j]],
												0);
							}
						}
					}
					break;

				case 1:
				case 6:
				case 14:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								c.getPrayer().prayerActive[strengthPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[strengthPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								c.getPrayer().prayerActive[rangePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[rangePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								c.getPrayer().prayerActive[magePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[magePrayer[j]],
												0);
							}
						}
					}
					break;

				case 2:
				case 7:
				case 15:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								c.getPrayer().prayerActive[attackPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[attackPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								c.getPrayer().prayerActive[rangePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[rangePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								c.getPrayer().prayerActive[magePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[magePrayer[j]],
												0);
							}
						}
					}
					break;

				case 3:// range prays
				case 11:
				case 19:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								c.getPrayer().prayerActive[attackPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[attackPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								c.getPrayer().prayerActive[strengthPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[strengthPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								c.getPrayer().prayerActive[rangePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[rangePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								c.getPrayer().prayerActive[magePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[magePrayer[j]],
												0);
							}
						}
					}
					break;
				case 4:
				case 12:
				case 20:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								c.getPrayer().prayerActive[attackPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[attackPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								c.getPrayer().prayerActive[strengthPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[strengthPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								c.getPrayer().prayerActive[rangePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[rangePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								c.getPrayer().prayerActive[magePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[magePrayer[j]],
												0);
							}
						}
					}
					break;
				case 10:
					c.lastProtItem = System.currentTimeMillis();
					break;

				case 16:
				case 17:
				case 18:
					if (System.currentTimeMillis()
							- c.getPrayer().stopPrayerDelay < 5000) {
						c.getActionSender()
								.sendMessage(
										"You have been injured and can't use this prayer!");
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[16], 0);
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[17], 0);
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[18], 0);
						return;
					}
					if (i == 16) {
						c.protMageDelay = System.currentTimeMillis();
					} else if (i == 17) {
						c.protRangeDelay = System.currentTimeMillis();
					} else if (i == 18) {
						c.protMeleeDelay = System.currentTimeMillis();
					}
				case 21:
				case 22:
				case 23:
					headIcon = true;
					for (int p = 16; p < 24; p++) {
						if (i != p && p != 19 && p != 20) {
							c.getPrayer().prayerActive[p] = false;
							c.getPlayerAssistant().sendConfig(
									c.getPrayer().PRAYER_GLOW[p], 0);
						}
					}
					break;
				case 24:
				case 25:
					if (c.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								c.getPrayer().prayerActive[attackPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[attackPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								c.getPrayer().prayerActive[strengthPrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[strengthPrayer[j]],
												0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								c.getPrayer().prayerActive[rangePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[rangePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								c.getPrayer().prayerActive[magePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[magePrayer[j]],
												0);
							}
						}
						for (int j = 0; j < defencePrayer.length; j++) {
							if (defencePrayer[j] != i) {
								c.getPrayer().prayerActive[defencePrayer[j]] = false;
								c.getPlayerAssistant()
										.sendConfig(
												c.getPrayer().PRAYER_GLOW[defencePrayer[j]],
												0);
							}
						}
					}
					break;
				}

				if (!headIcon) {
					if (c.getPrayer().prayerActive[i] == false) {
						c.getPrayer().prayerActive[i] = true;
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[i], 1);
					} else {
						c.getPrayer().prayerActive[i] = false;
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[i], 0);
					}
				} else {
					if (c.getPrayer().prayerActive[i] == false) {
						c.getPrayer().prayerActive[i] = true;
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[i], 1);
						c.headIcon = c.getPrayer().PRAYER_HEAD_ICONS[i];
						c.getPlayerAssistant().requestUpdates();
					} else {
						c.getPrayer().prayerActive[i] = false;
						c.getPlayerAssistant().sendConfig(
								c.getPrayer().PRAYER_GLOW[i], 0);
						c.headIcon = -1;
						c.getPlayerAssistant().requestUpdates();
					}
				}
			} else {
				c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[i],
						0);
				c.getPlayerAssistant().sendFrame126(
						"You need a @blu@Prayer level of "
								+ c.getPrayer().PRAYER_LEVEL_REQUIRED[i]
								+ " to use " + c.getPrayer().PRAYER_NAME[i]
								+ ".", 357);
				c.getPlayerAssistant()
						.sendFrame126("Click here to continue", 358);
				c.getPlayerAssistant().sendChatInterface(356);
			}
		} else {
			c.getPlayerAssistant().sendConfig(c.getPrayer().PRAYER_GLOW[i], 0);
			c.getActionSender().sendMessage(
					"You have run out of prayer points!");
			c.getActionSender().sendSound(SoundList.NO_PRAY, 100, 0);
		}

	}

}
