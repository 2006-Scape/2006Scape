package redone.game.content.skills;

import redone.game.players.Client;

/**
 * @author Sanity
 */

public class SkillMenu {

	private static final int INTERFACE_ID = 8714;
	private static final int LEVEL_LINE = 8720;
	private static final int TEXT_LINE = 8760;
	private static final int TITLE_LINE = 8716;
	private static final int[][] items = {
			{ 1321, 1323, 1325, 1327, 1329, 1331, 1333, 4153, 4587, 4151, 4718,
					11694, 9747 },
			{ 1117, 1115, 1119, 1125, 1121, 6916, 1123, 1127, 3751, 2513,
					10348, 11724, 11720, 4720, 11283, 9753 },
			{ 4153, 6528, 9750 },
			{ 9768 },
			{ 841, 843, 849, 853, 857, 1135, 861, 2499, 11235, 6522, 2501,
					9185, 10330, 4214, 2503, 4734, 9756 }, { 9759 },
			{ 4099, 6916, 6889, 7401, 3387, 4675, 10338, 4712 } };
	private static final String[][] LEVELS = {
			{ "1", "1", "5", "10", "20", "30", "40", "50", "60", "70", "70",
					"75", "99" },
			{ "1", "1", "5", "10", "20", "25", "30", "40", "45", "60", "65",
					"65", "70", "70", "75", "99" },
			{ "50", "60", "99" },
			{ "99" },
			{ "1", "5", "20", "30", "30", "40", "50", "50", "60", "60", "60",
					"61", "65", "70", "70", "70", "99" }, { "99" },
			{ "20", "25", "25", "40", "40", "50", "65", "70" } };

	private static final String[][] DESCRIPTION = {
			{ "Bronze Weapons", "Iron Weapons", "Steel Weapons",
					"Black Weapons", "Mithril Weapons", "Adamant Weapons",
					"Rune Weapons", "Granite Maul", "Dragon Weapons",
					"Abyssal Whip", "Barrows Weapons", "Godswords",
					"Cape of Achievement" },
			{ "Bronze Armour", "Iron Armour", "Steel Armour", "Black Armour",
					"Mithril Armour", "Infinity", "Adamant Armour",
					"Rune Armour", "Fremennik Helmets", "Dragon Armour",
					"3rd Age Armour", "Bandos", "Armadyl", "Barrows Armour",
					"Dragonfire Shield", "Cape of Achievement" },
			{ "Granite Items", "Obby Maul", "Cape of Achievement" },
			{ "Cape of Achievement" },
			{ "Normal Bows", "Oak Bows", "Willow Bows", "Maple Bows",
					"Yew Bows", "Green D'hide", "Magic Bows", "Blue D'hide",
					"Dark Bow", "Obby Ring", "Red D'hide", "Rune C'bow",
					"3rd age Range", "Crystal Bow", "Black D'hide", "Karil's",
					"Cape of Achievement" },
			{ "Cape of Achievement" },
			{ "Mystic ", "Infinity ", "Mage's book", "Enchanted ",
					"Splitbark ", "Ancient staff", "3rd age mage", "Ahrims" } };

	private static final String[] SKILLS = { "Attack", "Defence", "Strength",
			"Hitpoints", "Ranged", "Prayer", "Magic" };

	public static void openInterface(Client c, int skillType) {
		removeSidebars(c);
		writeItems(c, skillType);
		writeText(c, skillType);
		c.getPlayerAssistant().showInterface(INTERFACE_ID);
	}

	private static void removeSidebars(Client c) {
		int[] temp = { 8849, 8846, 8823, 8824, 8827, 8837, 8840, 8843, 8859,
				8862, 8865, 15303, 15306, 15309 };
		for (int element : temp) {
			c.getPlayerAssistant().sendFrame126("", element);
		}
	}

	private static void writeItems(Client c, int skillType) {
		synchronized (c) {
			c.outStream.createFrameVarSizeWord(53);
			c.outStream.writeWord(8847);
			c.outStream.writeWord(items[skillType].length);
			for (int j = 0; j < items[skillType].length; j++) {
				c.outStream.writeByte(1);
				if (items[skillType][j] > 0) {
					c.outStream.writeWordBigEndianA(items[skillType][j] + 1);
				} else {
					c.outStream.writeWordBigEndianA(0);
				}
			}
			c.outStream.endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	private static void writeText(Client c, int skillType) {
		c.getPlayerAssistant().sendFrame126(SKILLS[skillType], TITLE_LINE);
		for (int j = 0; j < LEVELS[skillType].length; j++) {
			c.getPlayerAssistant().sendFrame126(LEVELS[skillType][j],
					LEVEL_LINE + j);
		}
		for (int j = 0; j < DESCRIPTION[skillType].length; j++) {
			c.getPlayerAssistant().sendFrame126(DESCRIPTION[skillType][j],
					TEXT_LINE + j);
		}

		for (int j = DESCRIPTION[skillType].length; j < 30; j++) {
			c.getPlayerAssistant().sendFrame126("", LEVEL_LINE + j);
		}
		for (int j = LEVELS[skillType].length; j < 30; j++) {
			c.getPlayerAssistant().sendFrame126("", TEXT_LINE + j);
		}
	}
}
