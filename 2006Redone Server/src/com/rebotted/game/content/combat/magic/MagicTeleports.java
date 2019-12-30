package com.rebotted.game.content.combat.magic;

import com.rebotted.game.content.randomevents.RandomEventHandler;
import com.rebotted.game.players.Player;

public class MagicTeleports {

	public static void handleLoginText(Player player) {
		player.getPacketSender().sendFrame126("Level 25: Varrock Teleport", 1300);
		player.getPacketSender().sendFrame126("Level 31: Lumbridge Teleport", 1325);
		player.getPacketSender().sendFrame126("Level 37: Falador Teleport", 1350);
		player.getPacketSender().sendFrame126("Level 45: Camelot Teleport", 1382);
		player.getPacketSender().sendFrame126("Level 51: Ardougne Teleport", 1415);
		player.getPacketSender().sendFrame126("Level 54: Paddewwa Teleport", 13037);
		player.getPacketSender().sendFrame126("Level 60: Senntisten Teleport", 13047);
		player.getPacketSender().sendFrame126("Level 66: Kharyrll Teleport", 13055);
		player.getPacketSender().sendFrame126("Level 72: Lassar Teleport", 13063);
		player.getPacketSender().sendFrame126("Level 78: Dareeyak Teleport", 13071);
	}

	public static final boolean MAGIC_LEVEL_REQUIRED = true, RUNES_REQUIRED = true;

	public static void handleSpellTeleport(Player player, SpellTeleport teleport) {
		if (player.teleTimer > 0) {
			return;
		}
		if (player.wildLevel > 20) {
			player.getPacketSender().sendMessage("You can't teleport above level 20 wilderness.");
			return;
		}
		if (player.playerLevel[player.playerMagic] < teleport.getRequiredLevel()) {
			player.getPacketSender().sendMessage("You need a magic level of " + teleport.getRequiredLevel() + " to cast this spell.");
			return;
		}
		if (!CastRequirements.hasRunes(player, teleport.getRequiredRunes())) {
			player.getPacketSender().sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		RandomEventHandler.addRandom(player);
		CastRequirements.deleteRunes(player, teleport.getRequiredRunes());
		player.getPlayerAssistant().startTeleport(teleport.getDestX(), teleport.getDestY(), teleport.getDestZ(), teleport.getType());
		player.getPlayerAssistant().addSkillXP(teleport.getExperienceGained(), player.playerMagic);
	}

















}