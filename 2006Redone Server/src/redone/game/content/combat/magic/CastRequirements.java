package redone.game.content.combat.magic;

import redone.game.players.Client;

public class CastRequirements {

	public static boolean hasRunes(Client c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			if (c.getItemAssistant().playerHasItem(runes[i], amount[i])) {
				return true;
			}
		}
		c.getActionSender().sendMessage(
				"You don't have enough required runes to cast this spell!");
		return false;
	}

	public static boolean hasRunes(Client c, int[][] runes) {
		int hasRunes = 0;
		for (int[] rune : runes) {
			if (c.getItemAssistant().playerHasItem(rune[0], rune[1])) {
				hasRunes++;
			}
		}
		if (hasRunes == runes.length) {
			deleteRunes(c, runes);
			return true;
		}
		c.getActionSender().sendMessage(
				"You don't have the required runes to cast this spell.");
		return false;
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

	public static final int FIRE = 554;
	public static final int WATER = 555;
	public static final int AIR = 556;
	public static final int EARTH = 557;
	public static final int MIND = 558;
	public static final int BODY = 559;
	public static final int DEATH = 560;
	public static final int NATURE = 561;
	public static final int CHAOS = 562;
	public static final int LAW = 563;
	public static final int COSMIC = 564;
	public static final int BLOOD = 565;
	public static final int SOUL = 566;
	public static final int ASTRAL = 9075;
}
