package redone.game.content.combat.magic;

import redone.game.content.quests.QuestAssistant;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class MagicTeleports {

	public static final int AIR_RUNE = 556, FIRE_RUNE = 554,
			WATER_RUNE = 555, EARTH_RUNE = 557,
			LAW_RUNE = 563, BLOOD_RUNE = 565,
			SOUL_RUNE = 566, BANANA = 1963;

	public static void handleLoginText(Client player) {
		player.getPlayerAssistant().sendFrame126("Level 25: Varrock Teleport", 1300);
		player.getPlayerAssistant().sendFrame126("Level 31: Lumbridge Teleport", 1325);
		player.getPlayerAssistant().sendFrame126("Level 37: Falador Teleport", 1350);
		player.getPlayerAssistant().sendFrame126("Level 45: Camelot Teleport", 1382);
		player.getPlayerAssistant().sendFrame126("Level 51: Ardougne Teleport", 1415);
		player.getPlayerAssistant().sendFrame126("Level 54: Paddewwa Teleport", 13037);
		player.getPlayerAssistant().sendFrame126("Level 60: Senntisten Teleport", 13047);
		player.getPlayerAssistant().sendFrame126("Level 66: Kharyrll Teleport", 13055);
		player.getPlayerAssistant().sendFrame126("Level 72: Lassar Teleport", 13063);
		player.getPlayerAssistant().sendFrame126("Level 78: Dareeyak Teleport", 13071);
	}

	/**
	 * Modern Teleports
	 */
	public static final int VARROCK_X = 3213, VARROCK_Y = 3423;
	public static final int LUMBRIDGE_X = 3222, LUMBRIDGE_Y = 3218;
	public static final int FALADOR_X = 2964, FALADOR_Y = 3378;
	public static final int CAMELOT_X = 2757, CAMELOT_Y = 3479;
	public static final int ARDOUGNE_X = 2662, ARDOUGNE_Y = 3305;
	public static final int WATCHTOWER_X = 2547, WATCHTOWER_Y = 3112;
	public static final int TROLLHEIM_X = 2910, TROLLHEIM_Y = 3612;
	public static final int APE_ATOLL_X = 2754, APE_ATOLL_Y = 2784;

	/**
	 * Ancient Teleports
	 */
	public static final int PADDEWWA_X = 3098, PADDEWWA_Y = 9884;
	public static final int SENNTISTEN_X = 3321, SENNTISTEN_Y = 3335;
	public static final int KHARYRLL_X = 3493, KHARYRLL_Y = 3472;
	public static final int LASSAR_X = 3006, LASSAR_Y = 3471;
	public static final int DAREEYAK_X = 3161, DAREEYAK_Y = 3671;
	public static final int CARRALLANGAR_X = 3157, CARRALLANGAR_Y = 3669;
	public static final int ANNAKARL_X = 3286, ANNAKARL_Y = 3884;
	public static final int GHORROCK_X = 2977, GHORROCK_Y = 3873;

	public static final boolean MAGIC_LEVEL_REQUIRED = true, RUNES_REQUIRED = true;

	public static void paddewwaTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 1}, {AIR_RUNE, 1}})) {
						player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
						return;
					}
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 54) {
						player.getActionSender().sendMessage("You need a magic level of 54 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 1}, {AIR_RUNE, 1}});
				player.getPlayerAssistant().startTeleport(PADDEWWA_X + Misc.random(2), PADDEWWA_Y - Misc.random(2), 0, "ancient");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(64, player.playerMagic);
			}

	public static void senntisenTeleport(Client player) {
		if (System.currentTimeMillis() - player.lastCast < 5000) {
			return;
		}
		RandomEventHandler.addRandom(player);
		if (RUNES_REQUIRED) {
			if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {SOUL_RUNE, 1}})) {
				player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
				return;
			}
		}
		if (MAGIC_LEVEL_REQUIRED) {
			if (player.playerLevel[player.playerMagic] < 60) {
				player.getActionSender().sendMessage("You need a magic level of 60 to cast this spell.");
				return;
			}
		}
		CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {SOUL_RUNE, 1}});
		player.getPlayerAssistant().startTeleport(SENNTISTEN_X + Misc.random(1), SENNTISTEN_Y - Misc.random(1), 0, "ancient");
		player.lastCast = System.currentTimeMillis();
		player.getPlayerAssistant().addSkillXP(70, player.playerMagic);
	}

	public static void kharyllTeleport(Client player) {
		if (System.currentTimeMillis() - player.lastCast < 5000) {
			return;
		}
		RandomEventHandler.addRandom(player);
		if (RUNES_REQUIRED) {
			if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {BLOOD_RUNE, 1}})) {
				player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
				return;
			}
		}
		if (MAGIC_LEVEL_REQUIRED) {
			if (player.playerLevel[player.playerMagic] < 66) {
				player.getActionSender().sendMessage("You need a magic level of 66 to cast this spell.");
				return;
			}
		}
		CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {BLOOD_RUNE, 1}});
		player.getPlayerAssistant().startTeleport(KHARYRLL_X, KHARYRLL_Y, 0, "ancient");
		player.lastCast = System.currentTimeMillis();
		player.getPlayerAssistant().addSkillXP(76, player.playerMagic);
	}

	public static void lassarTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 4}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 72) {
					player.getActionSender().sendMessage("You need a magic level of 72 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 4}});
			player.getPlayerAssistant().startTeleport(LASSAR_X + Misc.random(2), LASSAR_Y - Misc.random(2), 0, "ancient");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(82, player.playerMagic);
		}

	public static void dareeyakTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 3}, {AIR_RUNE, 2}})) {
						player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
						return;
					}
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 78) {
						player.getActionSender().sendMessage("You need a magic level of 78 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 3}, {AIR_RUNE, 2}});
				player.getPlayerAssistant().startTeleport(
						DAREEYAK_X + Misc.random(1),
						DAREEYAK_Y - Misc.random(1), 0, "ancient");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(88, player.playerMagic);
			}

	public static void carrallangarTeleport(Client player) {
		if (System.currentTimeMillis() - player.lastCast < 5000) {
			return;
		}
		RandomEventHandler.addRandom(player);
		if (RUNES_REQUIRED) {
			if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {SOUL_RUNE, 2}})) {
				player.getActionSender()
						.sendMessage(
								"You don't have the required runes to cast this spell.");
				return;
			}
		}
		if (MAGIC_LEVEL_REQUIRED) {
			if (player.playerLevel[player.playerMagic] < 84) {
				player.getActionSender().sendMessage("You need a magic level of 84 to cast this spell.");
				return;
			}
		}
		CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {SOUL_RUNE, 2}});
		player.getPlayerAssistant().startTeleport(CARRALLANGAR_X + Misc.random(2), CARRALLANGAR_Y - Misc.random(2), 0, "ancient");
		player.lastCast = System.currentTimeMillis();
		player.getPlayerAssistant().addSkillXP(94, player.playerMagic);
	}

	public static void annakarlTeleport(Client player) {
		if (System.currentTimeMillis() - player.lastCast < 5000) {
			return;
		}
		RandomEventHandler.addRandom(player);
		if (RUNES_REQUIRED) {
			if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {BLOOD_RUNE, 2}})) {
				player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
				return;
			}
		}
		if (MAGIC_LEVEL_REQUIRED) {
			if (player.playerLevel[player.playerMagic] < 90) {
				player.getActionSender().sendMessage("You need a magic level of 90 to cast this spell.");
				return;
			}
		}
		CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {BLOOD_RUNE, 2}});
		player.getPlayerAssistant().startTeleport(ANNAKARL_X + Misc.random(1), ANNAKARL_Y - Misc.random(1), 0, "ancient");
		player.lastCast = System.currentTimeMillis();
		player.getPlayerAssistant().addSkillXP(100, player.playerMagic);
	}

	public static void ghorrockTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 8}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 96) {
					player.getActionSender().sendMessage("You need a magic level of 96 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 8}});
			player.getPlayerAssistant().startTeleport(GHORROCK_X + Misc.random(3),
					GHORROCK_Y - Misc.random(3), 0, "ancient");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(106, player.playerMagic);
		}

	public static void varrockTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 1}, {FIRE_RUNE, 1}, {AIR_RUNE, 3}})) {
						player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
						return;
					}
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 25) {
						player.getActionSender().sendMessage("You need a magic level of 25 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 1}, {FIRE_RUNE, 1}, {AIR_RUNE, 3}});
				player.getPlayerAssistant().startTeleport(VARROCK_X + Misc.random(2), VARROCK_Y - Misc.random(2), 0, "modern");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(35, player.playerMagic);
			}

	public static void lumbridgeTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 1}, {EARTH_RUNE, 1}, {AIR_RUNE, 3}})) {
						player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
						return;
					}
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 32) {
						player.getActionSender().sendMessage("You need a magic level of 32 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 1}, {EARTH_RUNE, 1}, {AIR_RUNE, 3}});
				player.getPlayerAssistant().startTeleport(LUMBRIDGE_X, LUMBRIDGE_Y, 0, "modern");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(35, player.playerMagic);
			}

	public static void faladorTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 1}, {WATER_RUNE, 1}, {AIR_RUNE, 3}})) {
						player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
						return;
					}
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 37) {
						player.getActionSender().sendMessage("You need a magic level of 37 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 1}, {WATER_RUNE, 1}, {AIR_RUNE, 3}});
				player.getPlayerAssistant().startTeleport(FALADOR_X + Misc.random(4), FALADOR_Y - Misc.random(4), 0, "modern");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(48, player.playerMagic);
			}

	public static void camelotTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 1}, {AIR_RUNE, 5}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 45) {
					player.getActionSender().sendMessage("You need a magic level of 45 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 1}, {AIR_RUNE, 5}});
			// 2757, 3479
			player.getPlayerAssistant().startTeleport(CAMELOT_X + Misc.random(1), CAMELOT_Y - Misc.random(1), 0, "modern");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(55.5, player.playerMagic);
		}

	public static void ardougneTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 2}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 51) {
					player.getActionSender().sendMessage("You need a magic level of 51 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {WATER_RUNE, 2}});
			player.getPlayerAssistant().startTeleport(ARDOUGNE_X + Misc.random(4),
					ARDOUGNE_Y - Misc.random(4), 0, "modern");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(61, player.playerMagic);
		}

	public static void watchTowerTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {EARTH_RUNE, 2}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 58) {
					player.getActionSender().sendMessage(
							"You need a magic level of 58 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {EARTH_RUNE, 2}});
			player.getPlayerAssistant().startTeleport(WATCHTOWER_X, WATCHTOWER_Y, 1, "modern");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(68, player.playerMagic);
		}

	public static void trollhiemTeleport(Client player) {
			if (System.currentTimeMillis() - player.lastCast < 5000) {
				return;
			}
			RandomEventHandler.addRandom(player);
			if (RUNES_REQUIRED) {
				if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 2}})) {
					player.getActionSender().sendMessage("You don't have the required runes to cast this spell.");
					return;
				}
			}
			if (MAGIC_LEVEL_REQUIRED) {
				if (player.playerLevel[player.playerMagic] < 61) {
					player.getActionSender().sendMessage("You need a magic level of 61 to cast this spell.");
					return;
				}
			}
			CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 2}});
			player.getPlayerAssistant().startTeleport(2892 + Misc.random(2),
					3679 - Misc.random(2), 0, "modern");
			player.lastCast = System.currentTimeMillis();
			player.getPlayerAssistant().addSkillXP(68, player.playerMagic);
		}

	public static void apeAtollTeleport(Client player) {
				if (System.currentTimeMillis() - player.lastCast < 5000) {
					return;
				}
				RandomEventHandler.addRandom(player);
				if (RUNES_REQUIRED) {
					if (!CastRequirements.hasRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 2}, {WATER_RUNE, 2}}) || !player.getItemAssistant().playerHasItem(BANANA, 1)) {
						player.getActionSender().sendMessage("You don't have the required items to cast this spell.");
						return;
					}
				}
				if (player.questPoints < QuestAssistant.MAXIMUM_QUESTPOINTS) {
					player.getActionSender().sendMessage("You need " + QuestAssistant.MAXIMUM_QUESTPOINTS + " quest points to teleport here.");
					return;
				}
				if (MAGIC_LEVEL_REQUIRED) {
					if (player.playerLevel[player.playerMagic] < 64) {
						player.getActionSender().sendMessage("You need a magic level of 64 to cast this spell.");
						return;
					}
				}
				CastRequirements.deleteRunes(player, new int[][]{{LAW_RUNE, 2}, {FIRE_RUNE, 2}, {WATER_RUNE, 2}});
				player.getItemAssistant().deleteItem2(BANANA, 1);
				player.getPlayerAssistant().startTeleport(2798 + Misc.random(1), 2798 - Misc.random(1), 1, "modern");
				player.lastCast = System.currentTimeMillis();
				player.getPlayerAssistant().addSkillXP(76, player.playerMagic);
			}
}