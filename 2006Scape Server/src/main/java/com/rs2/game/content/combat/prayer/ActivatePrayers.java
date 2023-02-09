package com.rs2.game.content.combat.prayer;

import com.rs2.Constants;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.players.Player;

public class ActivatePrayers {

	public static void activatePrayer(Player player, int i) {
		if (player.duelRule[7]) {
			for (int p = 0; p < player.getPrayer().PRAYER.length; p++) { // reset
																	// prayer
																	// glows
				player.getPrayer().prayerActive[p] = false;
				player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[p],
						0);
			}
			player.getPacketSender().sendMessage(
					"Prayer has been disabled in this duel!");
			return;
		}
		if (i == 24 && player.playerLevel[Constants.DEFENCE] < 65) {
			player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[i], 0);
			player.getPacketSender().sendMessage(
					"You may not use this prayer yet.");
			return;
		}
		if (i == 25 && player.playerLevel[Constants.DEFENCE] < 70) {
			player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[i], 0);
			player.getPacketSender().sendMessage(
					"You may not use this prayer yet.");
			return;
		}
		int[] defencePrayer = { 0, 5, 13, 24, 25 };
		int[] strengthPrayer = { 1, 6, 14, 24, 25 };
		int[] attackPrayer = { 2, 7, 15, 24, 25 };
		int[] rangePrayer = { 3, 11, 19 };
		int[] magePrayer = { 4, 12, 20 };

		if (player.playerLevel[Constants.PRAYER] > 0) {
			if (player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.PRAYER]) >= player
					.getPrayer().PRAYER_LEVEL_REQUIRED[i]) {
				boolean headIcon = false;
				switch (i) {
				case 0:
				case 5:
				case 13:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < defencePrayer.length; j++) {
							if (defencePrayer[j] != i) {
								player.getPrayer().prayerActive[defencePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[defencePrayer[j]], 0);
							}
						}
					}
					break;

				case 1:
				case 6:
				case 14:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								player.getPrayer().prayerActive[strengthPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[strengthPrayer[j]], 0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								player.getPrayer().prayerActive[rangePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[rangePrayer[j]],0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								player.getPrayer().prayerActive[magePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[magePrayer[j]], 0);
							}
						}
					}
					break;

				case 2:
				case 7:
				case 15:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								player.getPrayer().prayerActive[attackPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[attackPrayer[j]], 0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								player.getPrayer().prayerActive[rangePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[rangePrayer[j]], 0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								player.getPrayer().prayerActive[magePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[magePrayer[j]], 0);
							}
						}
					}
					break;

				case 3:// range prays
				case 11:
				case 19:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								player.getPrayer().prayerActive[attackPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[attackPrayer[j]], 0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								player.getPrayer().prayerActive[strengthPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[strengthPrayer[j]], 0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								player.getPrayer().prayerActive[rangePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[rangePrayer[j]], 0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								player.getPrayer().prayerActive[magePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[magePrayer[j]], 0);
							}
						}
					}
					break;
				case 4:
				case 12:
				case 20:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								player.getPrayer().prayerActive[attackPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[attackPrayer[j]], 0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								player.getPrayer().prayerActive[strengthPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[strengthPrayer[j]], 0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								player.getPrayer().prayerActive[rangePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[rangePrayer[j]], 0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								player.getPrayer().prayerActive[magePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[magePrayer[j]], 0);
							}
						}
					}
					break;
				case 10:
					player.lastProtItem = System.currentTimeMillis();
					break;

				case 16:
				case 17:
				case 18:
					if (System.currentTimeMillis()
							- player.getPrayer().stopPrayerDelay < 5000) {
						player.getPacketSender()
								.sendMessage(
										"You have been injured and can't use this prayer!");
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[16], 0);
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[17], 0);
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[18], 0);
						return;
					}
					if (i == 16) {
						player.protMageDelay = System.currentTimeMillis();
					} else if (i == 17) {
						player.protRangeDelay = System.currentTimeMillis();
					} else if (i == 18) {
						player.protMeleeDelay = System.currentTimeMillis();
					}
				case 21:
				case 22:
				case 23:
					headIcon = true;
					for (int p = 16; p < 24; p++) {
						if (i != p && p != 19 && p != 20) {
							player.getPrayer().prayerActive[p] = false;
							player.getPacketSender().sendConfig(
									player.getPrayer().PRAYER_GLOW[p], 0);
						}
					}
					break;
				case 24:
				case 25:
					if (player.getPrayer().prayerActive[i] == false) {
						for (int j = 0; j < attackPrayer.length; j++) {
							if (attackPrayer[j] != i) {
								player.getPrayer().prayerActive[attackPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[attackPrayer[j]], 0);
							}
						}
						for (int j = 0; j < strengthPrayer.length; j++) {
							if (strengthPrayer[j] != i) {
								player.getPrayer().prayerActive[strengthPrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[strengthPrayer[j]], 0);
							}
						}
						for (int j = 0; j < rangePrayer.length; j++) {
							if (rangePrayer[j] != i) {
								player.getPrayer().prayerActive[rangePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[rangePrayer[j]], 0);
							}
						}
						for (int j = 0; j < magePrayer.length; j++) {
							if (magePrayer[j] != i) {
								player.getPrayer().prayerActive[magePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[magePrayer[j]], 0);
							}
						}
						for (int j = 0; j < defencePrayer.length; j++) {
							if (defencePrayer[j] != i) {
								player.getPrayer().prayerActive[defencePrayer[j]] = false;
								player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[defencePrayer[j]], 0);
							}
						}
					}
					break;
				}

				if (!headIcon) {
					if (player.getPrayer().prayerActive[i] == false) {
						player.getPrayer().prayerActive[i] = true;
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[i], 1);
					} else {
						player.getPrayer().prayerActive[i] = false;
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[i], 0);
					}
				} else {
					if (player.getPrayer().prayerActive[i] == false) {
						player.getPrayer().prayerActive[i] = true;
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[i], 1);
						player.headIcon = player.getPrayer().PRAYER_HEAD_ICONS[i];
						if (i == 16)
							player.getPacketSender().sendSound(SoundList.PROTECT_MAGIC, 100, 0);
						else if (i == 17)
							player.getPacketSender().sendSound(SoundList.PROTECT_RANGE, 100, 0);
						else if (i == 18)
							player.getPacketSender().sendSound(SoundList.PROTECT_MELEE, 100, 0);
						player.getPlayerAssistant().requestUpdates();
					} else {
						player.getPrayer().prayerActive[i] = false;
						player.getPacketSender().sendConfig(
								player.getPrayer().PRAYER_GLOW[i], 0);
						player.headIcon = -1;
						player.getPacketSender().sendSound(SoundList.NO_PRAY, 100, 0);
						player.getPlayerAssistant().requestUpdates();
					}
				}
			} else {
				player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[i],
						0);
				player.getPacketSender().sendString(
						"You need a @blu@Prayer level of "
								+ player.getPrayer().PRAYER_LEVEL_REQUIRED[i]
								+ " to use " + player.getPrayer().PRAYER_NAME[i]
								+ ".", 357);
				player.getPacketSender().sendString("Click here to continue", 358);
				player.getPacketSender().sendChatInterface(356);
			}
		} else {
			player.getPacketSender().sendConfig(player.getPrayer().PRAYER_GLOW[i], 0);
			player.getPacketSender().sendMessage("You have run out of prayer points!");
			player.getPacketSender().sendSound(SoundList.NO_PRAY, 100, 0);
		}

	}

}
