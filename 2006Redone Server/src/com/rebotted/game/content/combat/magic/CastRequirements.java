package com.rebotted.game.content.combat.magic;

import com.rebotted.game.players.Client;

public class CastRequirements {

	public static boolean hasRunes(Client c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			if (c.getItemAssistant().playerHasItem(runes[i], amount[i]) || MagicRequirements.wearingStaff(c, runes[i])) {
				return true;
			}
		}
		c.getActionSender().sendMessage(
				"You don't have enough required runes to cast this spell!");
		return false;
	}

	public static boolean hasRunes(Client c, int[][] runes) {
		for (int[] rune : runes) {
			// if player doesn't have the required amount of runes or a staff for that rune
			if (!c.getItemAssistant().playerHasItem(rune[0], rune[1]) && !MagicRequirements.wearingStaff(c, rune[0])) {
				return false;
			}
		}
		return true;
	}

	public static void deleteRunes(Client c, int[][] runes) {
		for (int[] rune : runes) {
			if (!MagicRequirements.wearingStaff(c, rune[0])) {
				c.getItemAssistant().deleteItem(rune[0], rune[1]);
			}
		}
	}

	public static void deleteRunes(Client c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			c.getItemAssistant().deleteItem(runes[i],
					c.getItemAssistant().getItemSlot(runes[i]), amount[i]);
		}
	}

	public static boolean hasRequiredLevel(Client c, int i) {
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
