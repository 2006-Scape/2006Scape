package com.rebotted.game.content.combat.magic;

import com.rebotted.game.players.Player;

public class CastRequirements {

	public static boolean hasRunes(Player c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			if (c.getItemAssistant().playerHasItem(runes[i], amount[i]) || MagicRequirements.wearingStaff(c, runes[i])) {
				return true;
			}
		}
		c.getPacketSender().sendMessage(
				"You don't have enough required runes to cast this spell!");
		return false;
	}

	public static boolean hasRunes(Player player, int[][] runes) {
		for (int[] rune : runes) {
			// if player doesn't have the required amount of runes or a staff for that rune
			if (!player.getItemAssistant().playerHasItem(rune[0], rune[1]) && !MagicRequirements.wearingStaff(player, rune[0])) {
				return false;
			}
		}
		return true;
	}

	public static void deleteRunes(Player player, int[][] runes) {
		for (int[] rune : runes) {
			if (!MagicRequirements.wearingStaff(player, rune[0])) {
				player.getItemAssistant().deleteItem(rune[0], rune[1]);
			}
		}
	}

	public static void deleteRunes(Player c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			c.getItemAssistant().deleteItem(runes[i],
					c.getItemAssistant().getItemSlot(runes[i]), amount[i]);
		}
	}

	public static boolean hasRequiredLevel(Player c, int i) {
		return c.playerLevel[6] >= i;
	}

	public static final int FIRE = 554,
							WATER = 555,
							AIR = 556,
							EARTH = 557,
							MIND = 558,
							BODY = 559,
							DEATH = 560,
							NATURE = 561,
							CHAOS = 562,
							LAW = 563,
							COSMIC = 564,
							BLOOD = 565,
							SOUL = 566,
							ASTRAL = 9075;
}
