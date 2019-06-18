package redone.game.content.skills;

import redone.game.players.Client;

public class SkillInterfaces {

	private final Client c;

	public int selected;

	private final int item[] = new int[40];

	public SkillInterfaces(Client client) {
		c = client;
	}

	/*
	 * @param screen
	 * 
	 * @return Used to call all of the menus, redundant No change needed to the
	 * method no matter How many menus added
	 */
	public void menuCompilation(int screen) {
		if (selected == 0) {
			attackComplex(screen);
		} else if (selected == 1) {
			strengthComplex(screen);
		} else if (selected == 2) {
			defenceComplex(screen);
		} else if (selected == 3) {
			rangedComplex(screen);
		} else if (selected == 4) {
			prayerComplex(screen);
		} else if (selected == 5) {
			magicComplex(screen);
		} else if (selected == 6) {
			runecraftingComplex(screen);
		} else if (selected == 7) {
			hitpointsComplex(screen);
		} else if (selected == 8) {
			agilityComplex(screen);
		} else if (selected == 9) {
			herbloreComplex(screen);
		} else if (selected == 10) {
			thievingComplex(screen);
		} else if (selected == 11) {
			craftingComplex(screen);
		} else if (selected == 12) {
			fletchingComplex(screen);
		} else if (selected == 13) {
			slayerComplex(screen);
		} else if (selected == 14) {
			miningComplex(screen);
		} else if (selected == 15) {
			smithingComplex(screen);
		} else if (selected == 16) {
			fishingComplex(screen);
		} else if (selected == 17) {
			cookingComplex(screen);
		} else if (selected == 18) {
			firemakingComplex(screen);
		} else if (selected == 19) {
			woodcuttingComplex(screen);
		} else if (selected == 20) {
			farmingComplex(screen);
		}
	}

	/**
	 * @param title
	 * @param currentTab
	 * @param write
	 *            []
	 * @return Used to shorten the sidebar tab texts, shortens by up to 12x,
	 *         Also includes the title of the menu and the current tab
	 */
	private void optionTab(String title, String currentTab, String op1,
			String op2, String op3, String op4, String op5, String op6,
			String op7, String op8, String op9, String op10, String op11,
			String op12, String op13) {
		if (SkillHandler.isSkilling(c)) {
			c.getActionSender().sendMessage(
					"You can't open this while skilling!");
			return;
		}
		c.getPlayerAssistant().sendFrame126(title, 8716);
		c.getPlayerAssistant().sendFrame126(currentTab, 8849);
		c.getPlayerAssistant().sendFrame126(op1, 8846);
		c.getPlayerAssistant().sendFrame126(op2, 8823);
		c.getPlayerAssistant().sendFrame126(op3, 8824);
		c.getPlayerAssistant().sendFrame126(op4, 8827);
		c.getPlayerAssistant().sendFrame126(op5, 8837);
		c.getPlayerAssistant().sendFrame126(op6, 8840);
		c.getPlayerAssistant().sendFrame126(op7, 8843);
		c.getPlayerAssistant().sendFrame126(op8, 8859);
		c.getPlayerAssistant().sendFrame126(op9, 8862);
		c.getPlayerAssistant().sendFrame126(op10, 8865);
		c.getPlayerAssistant().sendFrame126(op11, 15303);
		c.getPlayerAssistant().sendFrame126(op12, 15306);
		c.getPlayerAssistant().sendFrame126(op13, 15309);
		c.getPlayerAssistant().showInterface(8714);
	}

	/**
	 * @param levels
	 * @param lines
	 * @param ids
	 * @param lineCounter
	 * @return Used to reduce code by 3x. Contains the item on interface, Level
	 *         text, and the item description, along with the line Counter to
	 *         ensure it is placed in the right spot
	 */
	private void menuLine(final String levels, final String lines,
			final int ids, final int lineCounter) {
		if (SkillHandler.isSkilling(c)) {
			return;
		}
		c.getPlayerAssistant().sendFrame126(lines, 8760 + lineCounter);
		c.getPlayerAssistant().sendFrame126(levels, 8720 + lineCounter);
		item[0 + lineCounter] = ids;
		writeInterfaceItem(item);
	}

	/**
	 * Clears the menus
	 */
	private void clearMenu() {
		for (int i = 0; i < 39; i++) {
			item[i] = 0;
		}
		for (int i = 8720; i < 8799; i++) {
			c.getPlayerAssistant().sendFrame126("", i);
		}
	}

	/**
	 * @param id
	 *            []
	 * @return Used to place the item on the interface
	 */
	private void writeInterfaceItem(int id[]) {
		synchronized (c) {
			c.outStream.createFrameVarSizeWord(53);
			c.outStream.writeWord(8847); // 8847
			c.outStream.writeWord(id.length);
			for (int element : id) {
				c.outStream.writeByte(1);
				if (element > 0) {
					c.outStream.writeWordBigEndianA(element + 1);
				} else {
					c.outStream.writeWordBigEndianA(0);
				}
			}
			c.outStream.endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Skill ID: 0
	 * 
	 * @param screen
	 * @return
	 */
	public void attackComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			String level = "1";
			String type = "Bronze";
			menuLine(level, type + " Dagger", 1205, 0);
			menuLine(level, type + " Axe", 1351, 1);
			menuLine(level, type + " Mace", 1422, 2);
			menuLine(level, type + " Claws", 3095, 3);
			menuLine(level, type + " Sword", 1277, 4);
			menuLine(level, type + " Longsword", 1291, 5);
			menuLine(level, type + " Scimitar", 1321, 6);
			menuLine(level, type + " Spear", 1237, 7);
			menuLine(level, type + " Warhammer", 1337, 8);
			menuLine(level, type + " Battleaxe", 1375, 9);
			menuLine(level, type + " Two-Handed Sword", 1307, 10);
			menuLine(level, type + " Halberd", 3190, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 2) {
			clearMenu();
			String level = "1";
			String type = "Iron";
			menuLine(level, type + " Dagger", 1203, 0);
			menuLine(level, type + " Axe", 1349, 1);
			menuLine(level, type + " Mace", 1420, 2);
			menuLine(level, type + " Claws", 3096, 3);
			menuLine(level, type + " Sword", 1279, 4);
			menuLine(level, type + " Longsword", 1293, 5);
			menuLine(level, type + " Scimitar", 1323, 6);
			menuLine(level, type + " Spear", 1239, 7);
			menuLine(level, type + " Warhammer", 1335, 8);
			menuLine(level, type + " Battleaxe", 1363, 9);
			menuLine(level, type + " Two-Handed Sword", 1309, 10);
			menuLine(level, type + " Halberd", 3192, 11);
			optionTab("Attack", type + "", "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 3) {
			clearMenu();
			String level = "5";
			String type = "Steel";
			menuLine(level, type + " Dagger", 1207, 0);
			menuLine(level, type + " Axe", 1353, 1);
			menuLine(level, type + " Mace", 1424, 2);
			menuLine(level, type + " Claws", 3097, 3);
			menuLine(level, type + " Sword", 1281, 4);
			menuLine(level, type + " Longsword", 1295, 5);
			menuLine(level, type + " Scimitar", 1325, 6);
			menuLine(level, type + " Spear", 1241, 7);
			menuLine(level, type + " Warhammer", 1339, 8);
			menuLine(level, type + " Battleaxe", 1365, 9);
			menuLine(level, type + " Two-Handed Sword", 1311, 10);
			menuLine(level, type + " Halberd", 3194, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 4) {
			clearMenu();
			String level = "10";
			String type = "Black";
			menuLine(level, type + " Dagger", 1217, 0);
			menuLine(level, type + " Axe", 1361, 1);
			menuLine(level, type + " Mace", 1426, 2);
			menuLine(level, type + " Claws", 3098, 3);
			menuLine(level, type + " Sword", 1283, 4);
			menuLine(level, type + " Longsword", 1297, 5);
			menuLine(level, type + " Scimitar", 1327, 6);
			menuLine(level, type + " Spear", 4580, 7);
			menuLine(level, type + " Warhammer", 1341, 8);
			menuLine(level, type + " Battleaxe", 1367, 9);
			menuLine(level, type + " Two-Handed Sword", 1313, 10);
			menuLine(level, type + " Halberd", 3196, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 5) {
			clearMenu();
			String level = "10";
			String type = "White";
			menuLine(level, type + " Dagger", 6591, 0);
			menuLine(level, type + " Mace", 6601, 1);
			menuLine(level, type + " Claws", 6587, 2);
			menuLine(level, type + " Sword", 6605, 3);
			menuLine(level, type + " Longsword", 6607, 4);
			menuLine(level, type + " Scimitar", 6611, 5);
			menuLine(level, type + " Warhammer", 6613, 6);
			menuLine(level, type + " Battleaxe", 6589, 7);
			menuLine(level, type + " Two-Handed Sword", 6609, 8);
			menuLine(level, type + " Halberd", 6599, 9);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 6) {
			clearMenu();
			String level = "20";
			String type = "Mithril";
			menuLine(level, type + " Dagger", 1209, 0);
			menuLine(level, type + " Axe", 1355, 1);
			menuLine(level, type + " Mace", 1428, 2);
			menuLine(level, type + " Claws", 3099, 3);
			menuLine(level, type + " Sword", 1285, 4);
			menuLine(level, type + " Longsword", 1299, 5);
			menuLine(level, type + " Scimitar", 1329, 6);
			menuLine(level, type + " Spear", 1243, 7);
			menuLine(level, type + " Warhammer", 1343, 8);
			menuLine(level, type + " Battleaxe", 1369, 9);
			menuLine(level, type + " Two-Handed Sword", 1315, 10);
			menuLine(level, type + " Halberd", 3198, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 7) {
			clearMenu();
			String level = "30";
			String type = "Adamant";
			menuLine(level, type + " Dagger", 1211, 0);
			menuLine(level, type + " Axe", 1357, 1);
			menuLine(level, type + " Mace", 1430, 2);
			menuLine(level, type + " Claws", 3100, 3);
			menuLine(level, type + " Sword", 1287, 4);
			menuLine(level, type + " Longsword", 1301, 5);
			menuLine(level, type + " Scimitar", 1331, 6);
			menuLine(level, type + " Spear", 1245, 7);
			menuLine(level, type + " Warhammer", 1345, 8);
			menuLine(level, type + " Battleaxe", 1371, 9);
			menuLine(level, type + " Two-Handed Sword", 1317, 10);
			menuLine(level, type + " Halberd", 3200, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 8) {
			clearMenu();
			String level = "40";
			String type = "Rune";
			menuLine(level, type + " Dagger", 1213, 0);
			menuLine(level, type + " Axe", 1359, 1);
			menuLine(level, type + " Mace", 1432, 2);
			menuLine(level, type + " Claws", 3101, 3);
			menuLine(level, type + " Sword", 1289, 4);
			menuLine(level, type + " Longsword", 1303, 5);
			menuLine(level, type + " Scimitar", 1333, 6);
			menuLine(level, type + " Spear", 1247, 7);
			menuLine(level, type + " Warhammer", 1347, 8);
			menuLine(level, type + " Battleaxe", 1373, 9);
			menuLine(level, type + " Two-Handed Sword", 1319, 10);
			menuLine(level, type + " Halberd", 3202, 11);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 9) {
			clearMenu();
			String level = "60";
			String type = "Dragon";
			menuLine(level, type + " Dagger", 1215, 0);
			menuLine(level, type + " Axe", 6739, 1);
			menuLine(level, type + " Mace", 1434, 2);
			menuLine(level, type + " Longsword", 1305, 3);
			menuLine(level, type + " Scimitar", 4587, 4);
			menuLine(level, type + " Spear", 1249, 5);
			menuLine(level, type + " Battleaxe", 1377, 6);
			menuLine(level, type + " Two-Handed Sword", 7158, 7);
			menuLine(level, type + " Halberd", 3204, 8);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 10) {
			clearMenu();
			String level = "70";
			String type = "Barrows";
			menuLine(level, "Ahrim's Staff(With 70 Magic)", 4710, 0);
			menuLine(level, "Dharok's Greataxe(With 70 Strength)", 4718, 1);
			menuLine(level, "Guthan's Spear", 4726, 2);
			menuLine(level, "Torag's Hammers(With 70 Strength", 4747, 3);
			menuLine(level, "Verac's Flail", 4755, 4);
			optionTab("Attack", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		} else if (screen == 11) {
			clearMenu();
			menuLine("50", "Leaf-Bladed Spear(With 55 Slayer)", 4158, 0);
			menuLine("50", "Ancient Staff(With 50 Magic)", 4675, 1);
			menuLine("60", "TokTz-Xil-Ak(Obsidian Sword)", 6523, 2);
			menuLine("60", "TzHaar-Ket-Om(Obsidian Maul)", 6528, 3);
			menuLine("60", "TokTz-Xil-Ek(Obsidian Knife)", 6525, 4);
			menuLine("60", "TokTz-Mej-Tal(Obsidian Staff)", 6526, 5);
			menuLine("60", "TokTz-Ket-Em(Obsidian Mace)", 6527, 6);
			menuLine("70", "Abyssal Whip", 4151, 7);
			optionTab("Attack", "Special", "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Special", "", "");
		}
	}

	/*
	 * Skill ID: 1
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void strengthComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("5", "Black Halberd(With 10 Attack)", 3196, 0);
			menuLine("5", "White Halberd(With 10 Attack)", 6599, 1);
			menuLine("10", "Mithril Halberd(With 20 Attack)", 3198, 2);
			menuLine("15", "Adamant Halberd(With 30 Attack)", 3200, 3);
			menuLine("20", "Rune Halberd(With 40 Attack)", 3202, 4);
			menuLine("30", "Dragon Halberd(With 60 Attack)", 3204, 5);
			menuLine("50", "Granite Maul(With 50 Attack)", 4153, 6);
			menuLine("60", "TzHaar-Ket-Om(Obsidian Maul)", 6528, 7);
			menuLine("70", "Dharok's Greataxe(With 70 Attack)", 4718, 8);
			menuLine("70", "Torag's Hammers(With 70 Attack)", 4747, 9);
			optionTab("Strength", "Weaponry", "Weaponry", "Armor",
					"", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("50", "Granite Shield(With 50 Defence)", 3122, 0);
			optionTab("Strength", "Armor", "Weaponry", "Armor", "",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 2
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void defenceComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			String level = "1";
			String type = "Bronze";
			menuLine(level, type + " Square Shield", 1173, 0);
			menuLine(level, type + " Kiteshield", 1189, 1);
			menuLine(level, type + " Medium Helm", 1139, 2);
			menuLine(level, type + " Full Helm", 1155, 3);
			menuLine(level, type + " Chainbody", 1103, 4);
			menuLine(level, type + " Platebody", 1117, 5);
			menuLine(level, type + " Plateskirt", 1087, 6);
			menuLine(level, type + " Platelegs", 1075, 7);
			menuLine(level, type + " Boots", 4119, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 2) {
			clearMenu();
			String level = "1";
			String type = "Iron";
			menuLine(level, type + " Square Shield", 1175, 0);
			menuLine(level, type + " Kiteshield", 1191, 1);
			menuLine(level, type + " Medium Helm", 1137, 2);
			menuLine(level, type + " Full Helm", 1153, 3);
			menuLine(level, type + " Chainbody", 1101, 4);
			menuLine(level, type + " Platebody", 1115, 5);
			menuLine(level, type + " Plateskirt", 1081, 6);
			menuLine(level, type + " Platelegs", 1067, 7);
			menuLine(level, type + " Boots", 4121, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 3) {
			clearMenu();
			String level = "5";
			String type = "Steel";
			menuLine(level, type + " Square Shield", 1177, 0);
			menuLine(level, type + " Kiteshield", 1193, 1);
			menuLine(level, type + " Medium Helm", 1141, 2);
			menuLine(level, type + " Full Helm", 1157, 3);
			menuLine(level, type + " Chainbody", 1105, 4);
			menuLine(level, type + " Platebody", 1119, 5);
			menuLine(level, type + " Plateskirt", 1083, 6);
			menuLine(level, type + " Platelegs", 1069, 7);
			menuLine(level, type + " Boots", 4123, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 4) {
			clearMenu();
			String level = "10";
			String type = "Black";
			menuLine(level, type + " Square Shield", 1179, 0);
			menuLine(level, type + " Kiteshield", 1195, 1);
			menuLine(level, type + " Medium Helm", 1151, 2);
			menuLine(level, type + " Full Helm", 1165, 3);
			menuLine(level, type + " Chainbody", 1107, 4);
			menuLine(level, type + " Platebody", 1125, 5);
			menuLine(level, type + " Plateskirt", 1089, 6);
			menuLine(level, type + " Platelegs", 1077, 7);
			menuLine(level, type + " Boots", 4125, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 5) {
			clearMenu();
			String level = "10";
			String type = "White";
			menuLine(level, type + " Square Shield", 6631, 0);
			menuLine(level, type + " Kiteshield", 6633, 1);
			menuLine(level, type + " Medium Helm", 6621, 2);
			menuLine(level, type + " Full Helm", 6623, 3);
			menuLine(level, type + " Chainbody", 6615, 4);
			menuLine(level, type + " Platebody", 6617, 5);
			menuLine(level, type + " Plateskirt", 6627, 6);
			menuLine(level, type + " Platelegs", 6625, 7);
			menuLine(level, type + " Boots", 6619, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 6) {
			clearMenu();
			String level = "20";
			String type = "Mithril";
			menuLine(level, type + " Square Shield", 1181, 0);
			menuLine(level, type + " Kiteshield", 1197, 1);
			menuLine(level, type + " Medium Helm", 1143, 2);
			menuLine(level, type + " Full Helm", 1159, 3);
			menuLine(level, type + " Chainbody", 1109, 4);
			menuLine(level, type + " Platebody", 1121, 5);
			menuLine(level, type + " Plateskirt", 1085, 6);
			menuLine(level, type + " Platelegs", 1071, 7);
			menuLine(level, type + " Boots", 4127, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 7) {
			clearMenu();
			String level = "30";
			String type = "Adamant";
			menuLine(level, type + " Square Shield", 1183, 0);
			menuLine(level, type + " Kiteshield", 1199, 1);
			menuLine(level, type + " Medium Helm", 1145, 2);
			menuLine(level, type + " Full Helm", 1161, 3);
			menuLine(level, type + " Chainbody", 1111, 4);
			menuLine(level, type + " Platebody", 1123, 5);
			menuLine(level, type + " Plateskirt", 1091, 6);
			menuLine(level, type + " Platelegs", 1073, 7);
			menuLine(level, type + " Boots", 4129, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 8) {
			clearMenu();
			String level = "40";
			String type = "Rune";
			menuLine(level, type + " Square Shield", 1185, 0);
			menuLine(level, type + " Kiteshield", 1201, 1);
			menuLine(level, type + " Medium Helm", 1147, 2);
			menuLine(level, type + " Full Helm", 1163, 3);
			menuLine(level, type + " Chainbody", 1113, 4);
			menuLine(level, type + " Platebody", 1127, 5);
			menuLine(level, type + " Plateskirt", 1093, 6);
			menuLine(level, type + " Platelegs", 1079, 7);
			menuLine(level, type + " Boots", 4131, 8);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 9) {
			clearMenu();
			String level = "60";
			String type = "Dragon";
			menuLine(level, type + " Square Shield", 1187, 0);
			menuLine(level, type + " Medium Helm", 1149, 1);
			menuLine(level, type + " Chainbody", 3140, 2);
			menuLine(level, type + " Plateskirt", 4585, 3);
			menuLine(level, type + " Platelegs", 4087, 4);
			optionTab("Defence", type, "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 10) {
			clearMenu();
			String level = "70";
			menuLine(level, "Ahrim's Hood(With 70 Magic)", 4708, 0);
			menuLine(level, "Ahrim's Robe Top(With 70 Magic)", 4712, 1);
			menuLine(level, "Ahrim's Robeskirt(With 70 Magic)", 4714, 2);
			menuLine(level, "Dharok's Helm", 4716, 3);
			menuLine(level, "Dharok's Platebody", 4720, 4);
			menuLine(level, "Dharok's Platelegs", 4722, 5);
			menuLine(level, "Guthan's Helm", 4724, 6);
			menuLine(level, "Guthan's Platebody", 4728, 7);
			menuLine(level, "Guthan's Chainskirt", 4730, 8);
			menuLine(level, "Karil's Coif(With 70 Ranged)", 4732, 9);
			menuLine(level, "Karil's Leather Top(With 70 Ranged)", 4736, 10);
			menuLine(level, "Karil's Leather Skirt(With 70 Ranged)", 4738, 11);
			menuLine(level, "Torag's Helm", 4745, 12);
			menuLine(level, "Torag's Platebody", 4749, 13);
			menuLine(level, "Torag's Platelegs", 4751, 14);
			menuLine(level, "Verac's Helm", 4753, 15);
			menuLine(level, "Verac's Brassard", 4757, 16);
			menuLine(level, "Verac's Plateskirt", 4759, 17);
			optionTab("Defence", "Barrows", "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 11) {
			clearMenu();
			menuLine("1", "Skeletal Gloves", 6153, 0);
			menuLine("1", "Skeletal Boots", 6147, 1);
			menuLine("1", "Elemental Shield", 2890, 2);
			menuLine("20", "Enchanted Hat(With 40 Magic)", 7400, 3);
			menuLine("20", "Enchanted Robe Top(With 40 Magic)", 7399, 4);
			menuLine("20", "Enchanted Robe(With 40 Magic)", 7398, 5);
			menuLine("20", "Mystic Hat(With 40 Magic)", 4099, 6);
			menuLine("20", "Mystic Robe Top(With 40 Magic)", 4101, 7);
			menuLine("20", "Mystic Robe(With 40 Magic)", 4103, 8);
			menuLine("20", "Mystic Gloves(With 40 Magic)", 4105, 9);
			menuLine("20", "Mystic Boots(With 40 Magic)", 4107, 10);
			menuLine("25", "Infinity Hat(With 50 Magic)", 6918, 11);
			menuLine("25", "Infinity Ttop(With 50 Magic)", 6916, 12);
			menuLine("25", "Infinity Bottom(With 50 Magic)", 6924, 13);
			menuLine("25", "Infinity Gloves(With 50 Magic)", 6922, 14);
			menuLine("25", "Infinity Boots(With 50 Magic)", 6920, 15);
			menuLine("40", "Splitbark Helm(With 40 Magic)", 3385, 16);
			menuLine("40", "Splitbark Body(With 40 Magic)", 3387, 17);
			menuLine("40", "Splitbark Legs(With 40 Magic)", 3389, 18);
			menuLine("40", "Splitbark Gauntlets(With 40 Magic)", 3391, 19);
			menuLine("40", "Splitbark Boots(With 40 Magic)", 3393, 20);
			menuLine("40", "Skeletal Helm(With 40 Magic)", 6137, 21);
			menuLine("40", "Skeletal Top(With 40 Magic)", 6139, 22);
			menuLine("40", "Skeletal Bottoms(With 40 Magic)", 6141, 23);
			menuLine("44", "Farseer Helm", 3755, 24);
			optionTab("Defence", "Magic", "Bronze", "Iron", "Steel", "Black",
					"White", "Mithril", "Adamant", "Rune", "Dragon", "Barrows",
					"Magic", "Equipment", "");
		}

		else if (screen == 12) {
			clearMenu();
			menuLine("1", "Khazard Helmet", 74, 0);
			menuLine("1", "Khazard Armour", 75, 1);
			menuLine("1", "Anti-Dragonbreath Shield", 1540, 2);
			menuLine("1", "Spined Gloves", 6149, 3);
			menuLine("1", "Spined Boots", 6143, 4);
			menuLine("1", "Rock-Shell Gloves", 6151, 5);
			menuLine("1", "Rock-Shell Boots", 6145, 6);
			menuLine("5", "Spiny Helmet", 4551, 7);
			menuLine("10", "Hard Leather Body", 1131, 8);
			menuLine("20", "Studded Body(With 20 Ranged)", 1133, 9);
			menuLine("20", "Initiate Helm(With 10 Prayer)", 5574, 10);
			menuLine("20", "Initiate Platemail(With 10 Prayer)", 5575, 11);
			menuLine("20", "Initiate Platelegs(With 10 Prayer)", 5576, 12);
			menuLine("20", "Mirror Shield(with 20 Slayer)", 4156, 13);
			menuLine("30", "Snakeskin armour(With 30 Ranged)", 6322, 14);
			menuLine("40", "Green Dragonhide Body(With 40 Ranged)", 1135, 15);
			menuLine("50", "Blue Dragonhide Body(With 50 Ranged)", 2499, 16);
			menuLine("60", "Red Dragonhide Body(With 60 Ranged)", 2501, 17);
			menuLine("70", "Black Dragonhide Body(With 70 Ranged)", 2503, 18);
			menuLine("40", "Spined Armour(With 40 Ranged)", 6133, 19);
			menuLine("40", "Rock-shell Armour", 6129, 20);
			menuLine("45", "Berserker Helm", 3751, 21);
			menuLine("45", "Warrior Helm", 3753, 22);
			menuLine("45", "Archer Helm", 3749, 23);
			menuLine("45", "Farseer Helm", 3755, 24);
			menuLine("50", "Granite Shield(With 50 Strength)", 3122, 25);
			menuLine("60", "TokTz-Ket-Xil(Obsidian Shield)", 6524, 26);
			menuLine("70", "Crystal Shield(With 50 Agility)", 4224, 27);
			optionTab("Defence", "Equipment", "Bronze", "Iron", "Steel",
					"Black", "White", "Mithril", "Adamant", "Rune", "Dragon",
					"Barrows", "Magic", "Equipment", "");
		}
	}

	/*
	 * Skill ID: 3
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void rangedComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Standard Bow", 839, 0);
			menuLine("5", "Oak Bow", 845, 1);
			menuLine("20", "Willow Bow", 847, 2);
			menuLine("30", "Maple Bow", 851, 3);
			menuLine("30", "Ogre Bow", 2883, 4);
			menuLine("30", "Ogre Composite Bow", 4827, 5);
			menuLine("40", "Yew Bow", 855, 6);
			menuLine("50", "Magic Bow", 859, 7);
			menuLine("50", "Seercull", 6724, 8);
			menuLine("70", "Crystal Bow(With 50 Agility)", 4212, 9);
			optionTab("Ranged", "Bows", "Bows", "Thrown", "Armour",
					"Crossbows", "Other", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Bronze Dart", 806, 0);
			menuLine("1", "Bronze Javelin", 825, 1);
			menuLine("1", "Bronze Throwing Axe", 800, 2);
			menuLine("1", "Bronze Throwing Knife", 864, 3);
			menuLine("1", "Iron Dart", 807, 4);
			menuLine("1", "Iron Javelin", 826, 5);
			menuLine("1", "Iron Throwing Axe", 801, 6);
			menuLine("1", "Iron Throwing Knife", 863, 7);
			menuLine("5", "Steel Dart", 808, 8);
			menuLine("5", "Steel Davelin", 827, 9);
			menuLine("5", "Steel Throwing Axe", 802, 10);
			menuLine("5", "Steel Throwing Knife", 865, 11);
			menuLine("10", "Black Dart", 3093, 12);
			menuLine("10", "Black Throwing Knife", 869, 13);
			menuLine("20", "Mithril Dart", 809, 14);
			menuLine("20", "Mithril Javelin", 828, 15);
			menuLine("20", "Mithril Throwing Axe", 803, 16);
			menuLine("20", "Mithril Throwing Knife", 866, 17);
			menuLine("30", "Adamant Dart", 810, 18);
			menuLine("30", "Adamant Javelin", 829, 19);
			menuLine("30", "Adamant Throwing Axe", 804, 20);
			menuLine("30", "Adamant Throwing Knife", 867, 21);
			menuLine("40", "Rune Dart", 811, 22);
			menuLine("40", "Rune Javelin", 830, 23);
			menuLine("40", "Rune Throwing Axe", 805, 24);
			menuLine("40", "Rune Throwing Knife", 868, 25);
			menuLine("60", "TokTz-Xil-Ul(Obsidian Rings)", 6522, 26);
			optionTab("Ranged", "Thrown", "Bows", "Thrown", "Armour",
					"Crossbows", "Other", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("1", "Leather Items", 1129, 0);
			menuLine("1", "Hardleather Body(With 10 Defence)", 1131, 1);
			menuLine("1", "Spined Boots", 6143, 2);
			menuLine("1", "Spined Gloves", 6149, 3);
			menuLine("1", "Archer Helm(With 45 Defence)", 3749, 4);
			menuLine("20", "Studded Leather Body(With 20 Defence)", 1133, 5);
			menuLine("20", "Studded Leather Chaps", 1097, 6);
			menuLine("20", "Coif", 1169, 7);
			menuLine("30", "Snakeskin Body(With 30 Defence)", 6322, 8);
			menuLine("30", "Snakeskin Chaps(With 30 Defence)", 6324, 9);
			menuLine("30", "Snakeskin Vambraces(With 30 Defence)", 6330, 10);
			menuLine("30", "Snakeskin Bandana(With 30 Defence)", 6326, 11);
			menuLine("30", "Snakeskin Boots(With 30 Defence)", 6328, 12);
			menuLine("40", "Ranger Boots", 2577, 13);
			menuLine("40", "Robin Hood Hat", 2581, 14);
			menuLine("40", "Green Dragonhide Vambraces", 1065, 15);
			menuLine("40", "Green Dragonhide Chaps", 1099, 16);
			menuLine("40", "Green Dragonhide Body(With 40 Defence)", 1135, 17);
			menuLine("40", "Spined Body(With 40 Defence)", 6133, 18);
			menuLine("40", "Spined Chaps(With 40 Defence)", 6135, 19);
			menuLine("40", "Spined Helm(With 40 Defence)", 6131, 20);
			menuLine("50", "Blue Dragonhide Vambraces", 2487, 21);
			menuLine("50", "Blue Dragonhide Chaps", 2493, 22);
			menuLine("50", "Blue Dragonhide Body(With 40 Defence)", 2499, 23);
			menuLine("60", "Red Dragonhide Vambraces", 2489, 24);
			menuLine("60", "Red Dragonhide Chaps", 2495, 25);
			menuLine("60", "Red Dragonhide Body(With 40 Defence)", 2501, 26);
			menuLine("70", "Black Dragonhdie Vambraces", 2491, 27);
			menuLine("70", "Black Dragonhdie Chaps", 2497, 28);
			menuLine("70", "Black Dragonhdie Body(With 40 Defence)", 2503, 29);
			menuLine("70", "Karil's Coif(With 70 Defence)", 4732, 30);
			menuLine("70", "Karil's Leathertop(With 70 Defence)", 4736, 31);
			menuLine("70", "Karil's Leatherskirt(With 70 Defence)", 4738, 32);
			optionTab("Ranged", "Armour", "Bows", "Thrown", "Armour",
					"Crossbows", "Other", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("1", "Crossbow", 837, 0);
			menuLine("1", "Pheonix Crossbow", 767, 1);
			menuLine("70", "Karil's Crossbow", 4734, 2);
			optionTab("Ranged", "Crossbows", "Bows", "Thrown", "Armour",
					"Crossbows", "Other", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 5) {
			clearMenu();
			menuLine("50", "Broad Arrow(With 55 Slayer)", 4150, 0);
			optionTab("Ranged", "Other", "Bows", "Thrown", "Armour",
					"Crossbows", "Other", "", "", "", "", "", "", "",
					"");
		}
	}

	/*
	 * Skill ID: 4
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void prayerComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("", "You can see what each of these prayers", 3840, 0);
			menuLine("", "does by selecting the Prayer icon on", 0, 1);
			menuLine("", "your side interface. Move your mouse", 3844, 2);
			menuLine("", "over the icon of the prayer you want", 0, 3);
			menuLine("", "and a description will be available", 3842, 4);
			menuLine("", "", 0, 5);
			menuLine("1", "Thick Skin", 1714, 6);
			menuLine("4", "Burst of Strength", 1714, 7);
			menuLine("7", "Clarity of Thought", 1714, 8);
			menuLine("10", "Rock Skin", 1714, 9);
			menuLine("13", "Superhuman Strength", 1714, 10);
			menuLine("16", "Improved Reflexes", 1714, 11);
			menuLine("19", "Rapid Restore", 1714, 12);
			menuLine("22", "Rapid Heal", 1714, 13);
			menuLine("25", "Protect Item", 1714, 14);
			menuLine("28", "Steel Skin", 1714, 15);
			menuLine("31", "Ultimate Strength", 1714, 16);
			menuLine("34", "Incredible Reflexes", 1714, 17);
			menuLine("37", "Protect from Magic", 1714, 18);
			menuLine("40", "Protect from Missles", 1714, 19);
			menuLine("43", "Protect from Melee", 1714, 20);
			menuLine("46", "Retribution", 1714, 21);
			menuLine("49", "Redemption", 1714, 22);
			menuLine("52", "Smite", 1714, 23);
			optionTab("Prayer", "Prayers", "Prayers", "Equipment",
					"", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("10", "Initiate Helm(With 20 Defence)", 5574, 0);
			menuLine("10", "Initiate Platemail(With 20 Defence)", 5575, 1);
			menuLine("10", "Initiate Platelegs(With 20 Defence)", 5576, 2);
			menuLine("50", "Enchant Unholy And Holy Symbols", 1724, 3);
			optionTab("Prayer", "Equipment", "Prayers", "Equipment",
					"", "", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 5
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void magicComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("", "You can see what each of the spells", 6949, 0);
			menuLine("", "does by selecting the spellbook icon on", 0, 1);
			menuLine("", "your side interface. To switch to ancient", 0, 2);
			menuLine("", "magicks, visit the Dark Mage near the bank", 0, 3);
			menuLine("", "in Camelot.", 0, 4);
			optionTab("Magic", "Spells", "Spells", "Ancients", "Armour",
					"Weapons", "Special", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("", "@cr1@Must have completed Desert Treasure", 6949, 0);
			menuLine("", "", 0, 1);
			menuLine("50", "Smoke Rush", 0, 2);
			menuLine("52", "Shadow Rush", 0, 3);
			menuLine("54", "Teleport to Paddewwa", 0, 4);
			menuLine("56", "Blood Rush", 0, 5);
			menuLine("58", "Ice Rush", 0, 6);
			menuLine("60", "Teleport to Senntisten", 0, 7);
			menuLine("62", "Smoke Burst", 0, 8);
			menuLine("64", "Shadow Burst", 0, 9);
			menuLine("66", "Teleport to Kharyrll", 0, 10);
			menuLine("68", "Blood Burst", 0, 11);
			menuLine("70", "Ice Burst", 0, 12);
			menuLine("72", "Teleport to Lassar", 0, 13);
			menuLine("74", "Smoke Blitz", 0, 14);
			menuLine("76", "Shadow Blitz", 0, 15);
			menuLine("78", "Teleport Dareeyak", 0, 16);
			menuLine("80", "Blood Blitz", 0, 17);
			menuLine("82", "Ice Blitz", 0, 18);
			menuLine("84", "Teleport to Carrallangar", 0, 19);
			menuLine("86", "Smoke Barrage", 0, 20);
			menuLine("88", "Shadow Barrage", 0, 21);
			menuLine("90", "Teleport to Annakarl", 0, 22);
			menuLine("92", "Blood Barrage", 0, 23);
			menuLine("94", "Ice Barrage", 0, 24);
			menuLine("96", "Teleport to Ghorrock", 0, 25);
			optionTab("Magic", "Ancients", "Spells", "Ancients", "Armour",
					"Weapons", "Special", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("1", "Farseer Helm(With 45 Defence)", 3755, 0);
			menuLine("1", "Elemental Shield", 2890, 1);
			menuLine("1", "Skeletal Gloves", 6153, 2);
			menuLine("1", "Skeletal Boots", 6147, 3);
			menuLine("20", "Wizard Boots", 2579, 4);
			menuLine("40", "Mystic Hat(With 20 Defence)", 4099, 5);
			menuLine("40", "Mystic Robe Top(With 20 Defence)", 4101, 6);
			menuLine("40", "Mystic Robe Bottom(With 20 Defence)", 4103, 7);
			menuLine("40", "Mystic Gloves(With 20 Defence)", 4105, 8);
			menuLine("40", "Mystic Boots(With 20 Defence)", 4107, 9);
			menuLine("40", "Enchanted Hat(With 20 Defence)", 7400, 10);
			menuLine("40", "Enchanted Top(With 20 Defence)", 7399, 11);
			menuLine("40", "Enchanted Robe(With 20 Defence)", 7398, 12);
			menuLine("40", "Splitbark Helm(With 40 Defence)", 3385, 13);
			menuLine("40", "Splitbark Body(With 40 Defence)", 3387, 14);
			menuLine("40", "Splitbark Legs(With 40 Defence)", 3389, 15);
			menuLine("40", "Splitbark Gauntlets(With 40 Defence)", 3391, 16);
			menuLine("40", "Splitbark Boots(With 40 Defence)", 3393, 17);
			menuLine("40", "Skeletal Helmet(With 40 Defence)", 6137, 18);
			menuLine("40", "Skeletal Top(With 40 Defence)", 6139, 19);
			menuLine("40", "Skeletal Bottoms(With 40 Defence)", 6141, 20);
			menuLine("50", "Infinity Hat(With 25 Defence)", 6918, 21);
			menuLine("50", "Infinity Top(With 25 Defence)", 6916, 22);
			menuLine("50", "Infinity Bottom(With 25 Defence)", 6924, 23);
			menuLine("50", "Infinity Boots(With 25 Defence)", 6920, 24);
			menuLine("50", "Infinity Gloves(with 25 Defence)", 6922, 25);
			menuLine("70", "Ahrim's Hood(With 70 Defence)", 4708, 26);
			menuLine("70", "Ahrim's Robe Top(With 70 Defence)", 4712, 27);
			menuLine("70", "Ahrim's Robeskirt(With 70 Defence)", 4714, 28);
			optionTab("Magic", "Armour", "Spells", "Ancients", "Armour",
					"Weapons", "Special", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("1", "Staff", 1379, 0);
			menuLine("1", "Magic staff", 1389, 1);
			menuLine("1", "Staff of Air", 1381, 2);
			menuLine("1", "Staff of Earth", 1385, 3);
			menuLine("1", "Staff of Fire", 1387, 4);
			menuLine("1", "Staff of Water", 1383, 5);
			menuLine("30", "Air Battlestaff(With 30 Attack)", 1397, 6);
			menuLine("30", "Earth Battlestaff(With 30 Attack)", 1399, 7);
			menuLine("30", "Fire Battlestaff(With 30 Attack)", 1393, 8);
			menuLine("30", "Water Battlestaff(With 30 Attack)", 1395, 9);
			menuLine("30", "Lava Battlestaff(With 30 Attack)", 3053, 10);
			menuLine("30", "Mud Battlestaff(With 30 Attack)", 6562, 11);
			menuLine("40", "Mystic Air Staff(With 40 Attack)", 1405, 12);
			menuLine("40", "Mystic Earth Staff(With 40 Attack)", 1407, 13);
			menuLine("40", "Mystic Fire Staff(With 40 Attack)", 1401, 14);
			menuLine("40", "Mystic Water Staff(With 40 Attack)", 1403, 15);
			menuLine("40", "Mystic Lava Staff(With 40 Attack)", 3054, 16);
			menuLine("40", "Mystic Mud Staff(With 40 Attack)", 6563, 17);
			menuLine("50", "Slayer's Staff(With 55 Slayer)", 4170, 18);
			menuLine("50", "Iban's Staff(With 50 Attack)", 1409, 19);
			menuLine("50", "Ancient Staff(With 50 Attack)", 4675, 20);
			menuLine("60", "Saradomin Staff", 2415, 21);
			menuLine("60", "Guthix Staff", 2416, 22);
			menuLine("60", "Zamorak Staff", 2417, 23);
			menuLine("60", "TokTz-Mej-Tal(Obsidian Staff)", 6526, 24);
			menuLine("70", "Ahrim's Staff(With 70 Attack)", 4710, 25);
			optionTab("Magic", "Weapons", "Spells", "Ancients", "Armour",
					"Weapons", "Special", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 5) {
			clearMenu();
			menuLine("45", "Beginner Wand", 6908, 0);
			menuLine("50", "Apprentice Wand", 6910, 1);
			menuLine("55", "Teacher Wand", 6912, 2);
			menuLine("60", "Master Wand", 6914, 3);
			menuLine("60", "Mage's Book", 6889, 4);
			optionTab("Magic", "Special", "Spells", "Ancients", "Armour",
					"Weapons", "Special", "", "", "", "", "", "", "",
					"");
		}
	}

	/*
	 * Skill ID: 6
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void runecraftingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Air runes", 556, 0);
			menuLine("2", "Mind runes", 558, 1);
			menuLine("5", "Water runes", 555, 2);
			menuLine("6", "Mist runes", 4695, 3);
			menuLine("9", "Earth runes", 557, 4);
			menuLine("10", "Dust runes", 4696, 5);
			menuLine("13", "Mud runes", 4698, 6);
			menuLine("14", "Fire runes", 554, 7);
			menuLine("15", "Smoke runes", 4697, 8);
			menuLine("19", "Steam runes", 4694, 9);
			menuLine("20", "Body runes", 559, 10);
			menuLine("23", "Lava runes", 4699, 11);
			menuLine("27", "Cosmic runes", 564, 12);
			menuLine("35", "Chaos runes", 562, 13);
			menuLine("44", "Nature runes", 561, 14);
			menuLine("54", "Law runes", 563, 15);
			menuLine("65", "Death runes", 560, 16);
			menuLine("77", "Blood runes", 565, 17);
			optionTab("RuneCrafting", "Runes", "Runes", "Multiples",
					"Equipment", "", "", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("11", "2 Air runes per essence", 556, 0);
			menuLine("14", "2 Mind runes per essence", 558, 1);
			menuLine("19", "2 Water runes per essence", 555, 2);
			menuLine("22", "3 Air runes per essence", 556, 3);
			menuLine("26", "2 Earth runes per essence", 557, 4);
			menuLine("28", "3 Mind runes per essence", 558, 5);
			menuLine("33", "4 Air runes per essence", 556, 6);
			menuLine("35", "2 Fire runes per essence", 554, 7);
			menuLine("38", "3 Water runes per essence", 555, 8);
			menuLine("42", "4 Mind runes per essence", 558, 9);
			menuLine("44", "5 Air runes per essence", 556, 10);
			menuLine("46", "2 Body runes per essence", 559, 11);
			menuLine("52", "3 Earth runes per essence", 557, 12);
			menuLine("55", "6 Air runes per essence", 556, 13);
			menuLine("56", "5 Mind runes per essence", 558, 14);
			menuLine("57", "4 Water runes per essence", 555, 15);
			menuLine("59", "2 Cosmic runes per essence", 564, 16);
			menuLine("66", "7 Air runes per essence", 556, 17);
			menuLine("70", "6 Mind runes per essence", 558, 18);
			menuLine("74", "3 Fire runes per essence", 554, 19);
			menuLine("76", "2 Chaos runes per essence", 562, 20);
			menuLine("77", "5 Water runes per essence", 555, 21);
			menuLine("78", "8 Air runes per essence", 556, 22);
			menuLine("82", "4 Earth runes per essence", 557, 23);
			menuLine("84", "7 Mind runes per essence", 558, 24);
			menuLine("88", "9 Air runes per essence", 556, 25);
			menuLine("91", "2 Nature runes per essence", 561, 26);
			menuLine("92", "3 Body runes per essence", 559, 27);
			menuLine("95", "6 Water runes per essence", 555, 28);
			menuLine("98", "8 Mind runes per essence", 558, 29);
			menuLine("99", "10 Air runes per essence", 556, 30);
			optionTab("RuneCrafting", "Multiples", "Runes", "Multiples",
					"Equipment", "", "", "", "", "", "", "", "", "",
					"");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("1", "Small Pouch(3 Essence)", 5509, 0);
			menuLine("25", "Medium Pouch(6 Essence)", 5510, 1);
			menuLine("50", "Large Pouch(9 Essence)", 5512, 2);
			menuLine("75", "Giant Pouch(12 Essence)", 5514, 3);
			optionTab("RuneCrafting", "Equipment", "Runes", "Multiples",
					"Equipment", "", "", "", "", "", "", "", "", "",
					"");
		}
	}

	/*
	 * Skill ID: 7
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void hitpointsComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("", "Hitpoints tell you how healthy your", 4049, 0);
			menuLine("", "character is. A character who reaches 0", 0, 1);
			menuLine("", "Hitpoints has died, but will reappear at", 0, 2);
			menuLine("", "their chosen respawn location", 0, 3);
			menuLine("", "", 0, 4);
			menuLine("", "If you see any red 'hitsplats' during", 4049, 5);
			menuLine("", "combat, the number shown corresponds", 0, 6);
			menuLine("", "to the number of Hitpoints lost as a", 0, 7);
			menuLine("", "result of that strike.", 0, 8);
			menuLine("", "", 0, 9);
			menuLine("", "Blue hitsplats mean no damage has", 4049, 10);
			menuLine("", "been dealt.", 0, 11);
			menuLine("", "", 0, 12);
			menuLine("", "Green hitspats are poison damage", 4049, 13);
			menuLine("", "", 0, 14);
			menuLine("", "Yellow hitsplats are disease damage", 4049, 15);
			optionTab("Hitpoints", "Hitpoints", "Hitpoints", "", "",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 8
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void agilityComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Gnome Agility Course", 2150, 0);// swamptoad
			menuLine("1", "Gnome Ball", 751, 1);// gnomeball
			menuLine("1", "Low-Level Agility Arena Obstacles", 2996, 2);// arena
			// ticket
			menuLine("20", "Medium-Level Agility Arena Obstacles", 2996, 3);// arena
			// ticket
			menuLine("25", "Werewolf Skullball Game", 1061, 4);// boots
			menuLine("30", "Agility Pyramid", 6970, 5);// boots
			menuLine("35", "Barbarian Outpost Agility Course", 1365, 6);// steel
			menuLine("40", "High-Level Agility Area Obstacles", 2996, 7);
			menuLine("48", "Ape Atoll Agility Course", 4024, 8);// greegree
			menuLine("52", "Wilderness Agility Course", 964, 9);// skull
			menuLine("60", "Werewolf Agility Course", 6465, 19);// charos
			optionTab("Agility", "Courses", "Courses", "Areas", "Shortcuts",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("10", "Moss Giant Island Rope Swing", 6518, 0);
			menuLine("12", "Karamja Dungeon Stepping Stones", 6518, 1);
			menuLine("15", "Edgeville Dungeon Monkey Bars", 6518, 2);
			menuLine("18", "Watchtower Wall Climb", 6521, 3);
			menuLine("22", "Karamja Dungeon Pipe Contortion", 6520, 4);
			menuLine("30", "South-east Karamja Stepping Stones", 6518, 5);
			menuLine("34", "Karamja Dungeon Pipe Contortion", 6520, 6);
			menuLine("45", "Isafdar Log Balance", 6519, 7);
			menuLine("49", "Yanille Dungeon Contortion", 6520, 8);
			menuLine("50", "Rogues' Den(With 50 Thieving)", 6518, 9);
			menuLine("67", "Yanille Dungeon Rubble Climb", 6521, 10);
			optionTab("Agility", "Areas", "Courses", "Areas", "Shortcuts",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("5", "Falador Agility Shortcut", 6517, 0);
			menuLine("8", "River Crossing To Al Kharid", 6515, 1);
			menuLine("11", "Falador Wall", 6517, 2);
			menuLine("13", "Varrock South Fence Jump", 6514, 3);
			menuLine("16", "Yanille Agility Shortcut", 6516, 4);
			menuLine("20", "Coal Truck Log Balance", 6515, 5);
			menuLine("21", "Varrock Agility Shortcut", 6516, 6);
			menuLine("26", "Falador wall Crawl", 6516, 7);
			menuLine("28", "Draynor Manor Broken Railing", 6516, 8);
			menuLine("29", "Draynor Manor Stones To The Champions' Guild",
					6516, 9);
			menuLine("31", "Catherby Cliff", 6515, 10);
			menuLine("32", "Ardougne Log Balance Shortcut", 6517, 11);
			menuLine("33", "Water Obelisk Island Escape", 6516, 12);
			menuLine("36", "Gnome Stronghold Shortcut", 6517, 13);
			menuLine("37", "Al Kharid Mining Pit Sliffside Scramble", 6517, 14);
			menuLine("39", "Yanille Wall", 6517, 15);
			menuLine("40", "Trollheim Easy Cliffside Scramble", 6517, 16);
			menuLine("41", "Dwarven Mine Narrow Crevice", 6517, 17);
			menuLine("42", "Trollheim Medium Cliffside Scramble", 6516, 18);
			menuLine("43", "Trollheim Advanced Cliffside Scramble", 6517, 19);
			menuLine("44", "Cosmic Temple Medium Narrow Walkway", 6517, 20);
			menuLine("46", "Trollheim Hard Cliffside Scramble", 6516, 21);
			menuLine("47", "Log Balance To The Fremennik Province", 6517, 22);
			menuLine("48", "Edgeville Dungeon To Varrock Sewers Pipe", 6515, 23);
			menuLine("51", "Karamja Crossing, South Of The Volcano", 6516, 24);
			menuLine("53", "Port Phasmatys Ectopool Shortcut", 6517, 25);
			menuLine("58", "Elven Overpass Easy Cliffside Scramble", 6517, 26);
			menuLine("59", "Slayer Tower Medium Spiked Chain Climb", 6517, 27);
			menuLine("61", "Slayer Dungon Narrow Crevice", 6517, 28);
			menuLine("62", "Trollheim Wilderness Route", 6516, 29);
			menuLine("64", "Paterdomus Temple To Morytania Shortcut", 6517, 30);
			menuLine("66", "Cosmic Temple Advanced Narrow Walkway", 6517, 31);
			menuLine("68", "Elven Overpass Medium Cliffside Scramble", 6517, 32);
			menuLine("70", "Taverly Dungeon Pipe Squeeze", 6516, 33);
			menuLine("71", "Slayer Tower Advanced Spiked Chain Climb", 6517, 34);
			menuLine("74", "Shilo Village Stepping Stone", 6514, 35);
			menuLine("80", "Taverly Dungeon Spiked Blade Jump", 6514, 36);
			menuLine("81", "Slayer Dungeon Chasm Jump", 6514, 37);
			menuLine("85", "Elven Overpass Advanced Cliff Scramble", 6517, 38);
			optionTab("Agility", "Shortcuts", "Courses", "Areas", "Shortcuts",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("50", "Crystal Equipment", 4207, 0);
			optionTab("Agility", "", "Courses", "Areas", "Shortcuts",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 9
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void herbloreComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("3", "Attack potion", 121, 0);
			menuLine("5", "Anti-poison", 175, 1);
			menuLine("12", "Strength potion", 115, 2);
			menuLine("22", "Stat restore potion", 127, 3);
			menuLine("26", "Energy potion", 3010, 4);
			menuLine("30", "Defence potion", 133, 5);
			menuLine("34", "Agility potion", 3034, 6);
			menuLine("38", "Prayer restore potion", 139, 7);
			menuLine("45", "Super Attack potion", 145, 8);
			menuLine("48", "Super anti-poison", 181, 9);
			menuLine("50", "Fishing potion", 151, 10);
			menuLine("52", "Super energy potion", 3018, 11);
			menuLine("55", "Super Strength potion", 157, 12);
			menuLine("60", "Weapon poison", 187, 13);
			menuLine("63", "Super restore potion", 3026, 14);
			menuLine("66", "Super Defence potion", 163, 15);
			menuLine("68", "Antidote+", 5945, 16);
			menuLine("69", "Ranging potion", 169, 17);
			menuLine("72", "Antifire potion", 2454, 18);
			menuLine("73", "Weapon poison+", 5937, 19);
			menuLine("76", "Magic potion", 3042, 20);
			menuLine("78", "Zamorak brew", 189, 21);
			menuLine("79", "Antidote++", 5954, 22);
			menuLine("81", "Saradomin brew", 6687, 23);
			menuLine("82", "Weapon poison++", 5940, 24);
			optionTab("Herblore", "Potions", "Potions", "Herbs", "",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("3", "Guam Leaf", 249, 0);
			menuLine("5", "Marrentill", 251, 1);
			menuLine("11", "Tarromin", 253, 2);
			menuLine("20", "Harralander", 255, 3);
			menuLine("25", "Ranarr", 257, 4);
			menuLine("30", "Toadflax", 2998, 5);
			menuLine("40", "Irit Leaf", 259, 6);
			menuLine("48", "Avantoe", 261, 7);
			menuLine("54", "Kwuarm", 263, 8);
			menuLine("59", "Snapdragon", 3000, 9);
			menuLine("65", "Cadantine", 265, 10);
			menuLine("67", "Lantadyme", 2481, 11);
			menuLine("70", "Dwarf Weed", 267, 12);
			menuLine("75", "Torstol", 269, 13);
			optionTab("Herblore", "Herbs", "Potions", "Herbs", "",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 10
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void thievingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Man", 3241, 0);
			menuLine("10", "Farmer", 3243, 1);
			menuLine("15", "Female H.A.M. Follower", 4295, 2);
			menuLine("20", "Male H.A.M. Follower", 4297, 3);
			menuLine("25", "Warrior", 3245, 4);
			menuLine("32", "Rogue", 3247, 5);
			menuLine("38", "Master Farmer", 5068, 6);
			menuLine("40", "Guard", 3249, 7);
			menuLine("45", "Fremennik Citizen", 3686, 8);
			menuLine("45", "Beared Pollnivnian Bandit", 6781, 9);
			menuLine("53", "Desert Bandit", 4625, 10);
			menuLine("55", "Knight", 3251, 11);
			menuLine("55", "Pollnivnian Bandit", 6782, 12);
			menuLine("65", "Watchman", 3253, 13);
			menuLine("65", "Menaphite Thug", 6780, 14);
			menuLine("70", "Paladin", 3255, 15);
			menuLine("75", "Gnome", 3257, 16);
			menuLine("80", "Hero", 3259, 17);
			menuLine("85", "Elf", 6105, 18);
			optionTab("Thieving", "Pickpocket", "Pickpocket", "Stalls",
					"Chests", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("2", "Vegetable stall", 1965, 0);
			menuLine("5", "Baker's stall", 2309, 1);
			menuLine("5", "Ape Atoll general stall", 1933, 2);
			menuLine("5", "Tea stall", 1978, 3);
			menuLine("5", "Crafting stall", 1755, 4);
			menuLine("5", "Monkey food stall", 1963, 5);
			menuLine("15", "Rock cake stall", 2379, 6);
			menuLine("20", "Silk stall", 950, 7);
			menuLine("22", "Wine stall", 1993, 8);
			menuLine("27", "Seed stall", 5318, 9);
			menuLine("35", "Fur stall", 958, 10);
			menuLine("42", "Fish stall", 333, 11);
			menuLine("50", "Silver stall", 2355, 12);
			menuLine("65", "Magic stall", 6422, 13);
			menuLine("65", "Scimitar stall", 1323, 14);
			menuLine("65", "Spice stall", 2007, 15);
			menuLine("75", "Gem stall", 1607, 16);
			optionTab("Thieving", "Stalls", "Pickpocket", "Stalls", "Chests",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("13", "Ardougne, Rellekka, and the Wilderness", 617, 0);
			menuLine("28", "Upstairs in Ardougne and Rellekka", 561, 1);
			menuLine("43", "Upstairs in Ardougne", 617, 2);
			menuLine("47", "Hemenster", 41, 3);
			menuLine("47", "Rellekka", 617, 4);
			menuLine("59", "Chaos Druid Tower north of Ardougne", 565, 5);
			menuLine("72", "King Lathas's castle in Ardougne", 383, 6);
			optionTab("Thieving", "Chests", "Pickpocket", "Stalls", "Chests",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 11
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void craftingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("10", "Cloth", 3224, 0);
			menuLine("21", "Vegetable Sack", 5418, 1);
			optionTab("Crafting", "Weaving", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Leather Gloves", 1059, 0);
			menuLine("7", "Leather Boots", 1061, 1);
			menuLine("9", "Leather Cowl", 1167, 2);
			menuLine("11", "Leather Vambraces", 1063, 3);
			menuLine("14", "Leather Body", 1129, 4);
			menuLine("18", "Leather Chaps", 1095, 5);
			menuLine("28", "Hard Leather Body", 1131, 6);
			menuLine("35", "Broodoo Shield", 6257, 7);
			menuLine("38", "Coif", 1169, 8);
			menuLine("41", "Studded Body", 1133, 9);
			menuLine("44", "Studded Chaps", 1097, 10);
			menuLine("45", "Snakeskin Boots", 6328, 11);
			menuLine("47", "Snakeskin Vambraces", 6330, 12);
			menuLine("48", "Snakeskin Bandana", 6326, 13);
			menuLine("51", "Snakeskin Chaps", 6324, 14);
			menuLine("53", "Snakeskin Body", 6322, 15);
			menuLine("57", "Green Dragonhide Vambraces", 1065, 16);
			menuLine("60", "Green Dragonhide Chaps", 1099, 17);
			menuLine("63", "Green Dragonhide Body", 1135, 18);
			menuLine("66", "Blue Dragonhide Vambraces", 2487, 19);
			menuLine("68", "Blue Dragonhide Chaps", 2493, 20);
			menuLine("71", "Blue Dragonhide Body", 2499, 21);
			menuLine("73", "Red Dragonhide Vambraces", 2489, 22);
			menuLine("75", "Red Dragonhide Chaps", 2495, 23);
			menuLine("77", "Red Dragonhide Body", 2501, 24);
			menuLine("79", "Black Dragonhide Vambraces", 2491, 25);
			menuLine("82", "Black Dragonhide Chaps", 2497, 26);
			menuLine("84", "Black Dragonhide Body", 2503, 27);
			optionTab("Crafting", "Armour", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("1", "Wool", 1759, 0);
			menuLine("10", "Flax into Bow Strings", 1777, 1);
			optionTab("Crafting", "Spinning", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("1", "Pot", 1931, 0);
			menuLine("7", "Pie Dish", 2313, 1);
			menuLine("8", "Bowl", 1923, 2);
			menuLine("19", "Plant Pot", 5350, 3);
			menuLine("25", "Pot Lid", 4440, 4);
			optionTab("Crafting", "Pottery", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 5) {
			clearMenu();
			menuLine("1", "Beer Glass", 1919, 0);
			menuLine("4", "Candle Lantern", 4527, 1);
			menuLine("12", "Oil Lamp", 4525, 2);
			menuLine("26", "Oil Lantern", 4535, 3);
			menuLine("33", "Vial", 229, 4);
			menuLine("42", "Fishbowl", 6667, 5);
			menuLine("46", "Glass Orb", 567, 6);
			menuLine("49", "Bullseye Lantern Lens", 4542, 7);
			optionTab("Crafting", "Glass", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 6) {
			clearMenu();
			menuLine("1", "Cut Opal", 1609, 0);
			menuLine("5", "Gold Ring", 1635, 1);
			menuLine("6", "Gold Necklace", 1654, 2);
			menuLine("8", "Gold Amulet", 1673, 3);
			menuLine("13", "Cut Jade", 1611, 4);
			menuLine("16", "Holy Symbol", 1714, 5);
			menuLine("16", "Cut Red Topaz", 1613, 6);
			menuLine("17", "Unholy Symbol", 1724, 7);
			menuLine("20", "Cut Sapphire", 1607, 8);
			menuLine("20", "Sapphire Ring", 1637, 9);
			menuLine("22", "Sapphire Necklace", 1656, 10);
			menuLine("23", "Tiara", 5525, 11);
			menuLine("24", "Sapphire Amulet", 1675, 12);
			menuLine("27", "Cut Emerald", 1605, 13);
			menuLine("27", "Emerald Ring", 1639, 14);
			menuLine("29", "Emerald Necklace", 1658, 15);
			menuLine("31", "Emerald Amulet", 1677, 16);
			menuLine("34", "Cut Ruby", 1603, 17);
			menuLine("34", "Ruby Ring", 1641, 18);
			menuLine("40", "Ruby Necklace", 1660, 19);
			menuLine("43", "Cut Diamond", 1601, 20);
			menuLine("43", "Diamond Ring", 1643, 21);
			menuLine("50", "Ruby Amulet", 1679, 22);
			menuLine("55", "Cut Dragonstone", 1615, 23);
			menuLine("55", "Dragonstone Ring", 1645, 24);
			menuLine("56", "Diamond Necklace", 1662, 25);
			menuLine("67", "Cut Onyx", 6573, 26);
			menuLine("67", "Onyx Ring", 6575, 27);
			menuLine("70", "Diamond Amulet", 1681, 28);
			menuLine("80", "Dragonstone Amulet", 1683, 29);
			menuLine("82", "Onyx Necklace", 6577, 30);
			menuLine("90", "Onyx Amulet", 6579, 31);
			optionTab("Crafting", "Jewellery", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}

		else if (screen == 7) {
			clearMenu();
			menuLine("54", "Water battlestaff", 1395, 0);
			menuLine("58", "Earth battlestaff", 1399, 1);
			menuLine("62", "Fire battlestaff", 1393, 2);
			menuLine("66", "Air battlestaff", 1397, 3);
			optionTab("Crafting", "Weaponry", "Weaving", "Armour", "Spinning",
					"Pottery", "Glass", "Jewellery", "Weaponry", "",
					"", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 12
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void fletchingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Bronze arrow", 882, 0);
			menuLine("5", "Ogre arrow", 2866, 1);
			menuLine("7", "Bronze 'brutal' arrow", 4773, 2);
			menuLine("15", "Iron arrow", 884, 3);
			menuLine("18", "Iron 'brutal' arrow", 4778, 4);
			menuLine("30", "Steel arrow", 886, 5);
			menuLine("33", "Steel 'brutal' arrow", 4783, 6);
			menuLine("38", "Black 'brutal' arrow", 4788, 7);
			menuLine("45", "Mithril arrow", 888, 8);
			menuLine("49", "Mithril 'brutal' arrow", 4793, 9);
			menuLine("60", "Adamant arrow", 890, 10);
			menuLine("62", "Adamant 'brutal' arrow", 4798, 11);
			menuLine("75", "Rune arrow", 892, 12);
			menuLine("77", "Rune 'brutal' arrow", 4803, 13);
			optionTab("Fletching", "Arrows", "Arrows", "Bows", "Darts",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("5", "Shortbow", 841, 0);
			menuLine("10", "Longbow", 839, 1);
			menuLine("20", "Oak Shortbow", 843, 2);
			menuLine("25", "Oak Longbow", 845, 3);
			menuLine("30", "Ogre Composite Bow(After Zogre Flesh Eaters)",
					4827, 4);
			menuLine("35", "Willow Shortbow", 849, 5);
			menuLine("40", "Willow Longbow", 847, 6);
			menuLine("50", "Maple Shortbow", 853, 7);
			menuLine("55", "Maple Longbow", 851, 8);
			menuLine("65", "Yew Shortbow", 857, 9);
			menuLine("70", "Yew Longbow", 855, 10);
			menuLine("80", "Magic Shortbow", 861, 11);
			menuLine("85", "Magic Longbow", 859, 12);
			optionTab("Fletching", "Bows", "Arrows", "Bows", "Darts",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("1", "Bronze Dart", 806, 0);
			menuLine("22", "Iron Dart", 807, 1);
			menuLine("37", "Steel Dart", 808, 2);
			menuLine("52", "Mithril Dart", 809, 3);
			menuLine("67", "Adamant Dart", 810, 4);
			menuLine("81", "Rune Dart", 811, 5);
			optionTab("Fletching", "Darts", "Arrows", "Bows", "Darts",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 13
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void slayerComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("5", "Crawling Hand", 4133, 0);
			menuLine("7", "Cave Bug", 4521, 1);
			menuLine("10", "Cave Crawler", 4134, 2);
			menuLine("15", "Banshee", 4135, 3);
			menuLine("17", "Cave Slime", 4520, 4);
			menuLine("20", "Rockslug", 4136, 5);
			menuLine("25", "Cockatrice", 4137, 6);
			menuLine("30", "Pyrefiend", 4138, 7);
			menuLine("40", "Basalisk", 4139, 8);
			menuLine("45", "Infernal Mage", 4140, 9);
			menuLine("50", "Bloodveld", 4141, 10);
			menuLine("52", "Jelly", 4142, 11);
			menuLine("55", "Turoth", 4143, 12);
			menuLine("60", "Aberrant Spectre", 4144, 13);
			menuLine("65", "Dust Devil", 4145, 14);
			menuLine("70", "Kurask", 4146, 15);
			menuLine("72", "Skeletal Wyvern", 6811, 16);
			menuLine("75", "Gargoyle", 4147, 17);
			menuLine("80", "Nechrael", 4148, 18);
			menuLine("85", "Abyssal Demon", 4149, 19);
			menuLine("90", "Dark Beast", 6637, 20);
			optionTab("Slayer", "Monsters", "Monsters", "Equipment", "", "", "", "", "", "", "", "", "", "", "");
		}
		
		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Enchanted Gem", 4155, 0);
			menuLine("1", "Bag Of Salt", 4161, 1);
			menuLine("1", "Ice Cooler", 6696, 2);
			menuLine("1", "Spiny Helmet (With 5 Defence)", 4551, 3);
			menuLine("1", "Rock Hammer", 4162, 4);
			menuLine("10", "Facemask", 4164, 5);
			menuLine("15", "Earmuffs", 4166, 6);
			menuLine("25", "Mirror Shield (With 20 Defence)", 4156, 7);
			menuLine("32", "Fishing Explosive", 6660, 8);
			menuLine("33", "Harpie Bug Lantern (Not a light source)", 7053, 9);
			menuLine("37", "Insulated Boots", 7159, 10);
			menuLine("42", "Slayer Gloves", 6708, 11);
			menuLine("55", "Leaf-Bladed Spear", 4158, 12);
			menuLine("55", "Broad arrows (With 50 Range)", 4150, 13);
			menuLine("55", "Slayer's staff(With 50 Magic)", 4170, 14);
			menuLine("57", "Fungicide Spray", 7421, 15);
			menuLine("60", "Nose Peg", 4168, 16);
			optionTab("Slayer", "Equipment", "Monsters", "Equipment", "", "", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 14
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void miningComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Rune Essence(After Rune Mysteries)", 1436, 0);
			menuLine("1", "Clay", 434, 1);
			menuLine("1", "Copper Ore", 436, 2);
			menuLine("1", "Tin Ore", 438, 3);
			menuLine("10", "Limestone", 3211, 4);
			menuLine("10", "Blurite Ore", 668, 5);
			menuLine("15", "Iron Ore", 440, 6);
			menuLine("20", "Elemental Ore(After Starting Elemental Workshop)", 2892, 7);
			menuLine("20", "Silver Ore", 442, 8);
			menuLine("30", "Coal", 453, 9);
			menuLine("30", "Pure Essence(After Rune Mysteries)", 7936, 10);
			menuLine("35", "Sandstone", 6977, 11);
			menuLine("40", "Gold Ore", 444, 12);
			menuLine("40", "Gem Rocks", 1603, 13);
			menuLine("45", "Granite", 6983, 14);
			menuLine("55", "Mithril Ore", 447, 15);
			menuLine("70", "Adamantite Ore", 449, 16);
			menuLine("85", "Runite Ore", 451, 17);
			optionTab("Mining", "Ores", "Ores", "Pickaxes", "", "",
					"", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Bronze Pickaxe", 1265, 0);
			menuLine("1", "Iron Pickaxe", 1267, 1);
			menuLine("6", "Steel Pickaxe", 1269, 2);
			menuLine("21", "Mithril Pickaxe", 1273, 3);
			menuLine("31", "Adamant Pickaxe", 1271, 4);
			menuLine("41", "Rune Pickaxe", 1275, 5);
			optionTab("Mining", "Pickaxes", "Ores", "Pickaxes", "",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("60", "Mining Guild", 447, 0);
			optionTab("Mining", "", "Ores", "Pickaxes", "",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 15
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void smithingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Bronze(1 Tin Ore + 1 Copper Ore)", 2349, 0);
			menuLine("15", "Iron(50% Chance of Success)", 2351, 1);
			menuLine("20", "Elemental Metal(After Elemental Workshop)", 2893, 2);
			menuLine("20", "Silver", 2355, 3);
			menuLine("30", "Steel(2 Coal + 1 Iron Ore)", 2353, 4);
			menuLine("40", "Gold", 2357, 5);
			menuLine("50", "Mithril(4 Coal + 1 Mithril Ore)", 2359, 6);
			menuLine("70", "Adamant(6 Coal + 1 Adamantite Ore)", 2361, 7);
			menuLine("85", "Runite(8 Coal + 1 Runite Ore)", 2363, 8);
			optionTab("Smithing", "Smelting", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			String type = "Bronze";
			menuLine("1", type + " Dagger - 1 Bar", 1205, 0);
			menuLine("1", type + " Hatchet - 1 Bar", 1351, 1);
			menuLine("2", type + " Mace - 1 Bar", 1422, 2);
			menuLine("3", type + " Med Helm - 1 Bar", 1139, 3);
			menuLine("4", type + " Sword - 1 Bar", 1277, 4);
			menuLine("4", type + " Dart Tips - 1 Bar makes 10", 819, 5);
			menuLine("4", type + " Wire - 1 Bar", 1794, 6);
			menuLine("4", type + " Nails - 1 Bar makes 15", 4819, 7);
			menuLine("5", type + " Scimitar - 2 Bars", 1321, 8);
			menuLine("5", type + " Spear - 1 Bar + 1 Log", 1237, 9);
			menuLine("5", type + " Arrowhead - 1 Bar makes 15", 39, 10);
			menuLine("6", type + " Longsword - 2 Bars", 1291, 11);
			menuLine("7", type + " Full Helm - 2 Bars", 1155, 12);
			menuLine("7", type + " Throwing Knife", 864, 13);
			menuLine("8", type + " Square Shield - 2 Bars", 1173, 14);
			menuLine("9", type + " Warhammer - 3 Bars", 1337, 15);
			menuLine("10", type + " Battleaxe - 3 Bars", 1375, 16);
			menuLine("11", type + " Chainbody - 3 Bars", 1103, 17);
			menuLine("12", type + " Kiteshield - 3 Bars", 1189, 18);
			menuLine("13", type + " Claws(After Death Plateu) - 2 Bars", 3095,
					19);
			menuLine("14", type + " Two-Handed Sword - 3 Bars", 1307, 20);
			menuLine("16", type + " Platelegs - 3 Bars", 1075, 21);
			menuLine("16", type + " Plateskirt - 3 Bars", 1087, 22);
			menuLine("18", type + " Platebody - 5 Bars", 1117, 23);
			optionTab("Smithing", "Bronze", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 3) {
			clearMenu();
			String type = "Iron";
			menuLine("15", type + " Dagger - 1 Bar", 1203, 0);
			menuLine("16", type + " Hatchet - 1 Bar", 1349, 1);
			menuLine("17", type + " Mace - 1 Bar", 1420, 2);
			menuLine("17", type + " Spit - 1 Bar", 7225, 3);
			menuLine("18", type + " Med Helm - 1 Bar", 1137, 4);
			menuLine("19", type + " Sword - 1 Bar", 1279, 5);
			menuLine("19", type + " Dart Tips - 1 Bar makes 10", 820, 6);
			menuLine("19", type + " Nails - 1 Bar makes 15", 4819, 7);
			menuLine("20", type + " Scimitar - 2 Bars", 1323, 8);
			menuLine("20", type + " Spear - 1 Bar + 1 Log", 1239, 9);
			menuLine("20", type + " Arrowhead - 1 Bar makes 15", 40, 10);
			menuLine("21", type + " Longsword - 2 Bars", 1293, 11);
			menuLine("22", type + " Full Helm - 2 Bars", 1153, 12);
			menuLine("22", type + " Throwing Knife", 863, 13);
			menuLine("23", type + " Square Shield - 2 Bars", 1175, 14);
			menuLine("24", type + " Warhammer - 3 Bars", 1335, 15);
			menuLine("25", type + " Battleaxe - 3 Bars", 1363, 16);
			menuLine("26", type + " Chainbody - 3 Bars", 1101, 17);
			menuLine("26", "Oil Lantern Frame - 1 Bar", 4540, 18);
			menuLine("27", type + " Kiteshield - 3 Bars", 1191, 19);
			menuLine("28", type + " Claws(After Death Plateau) - 2 Bars", 3096,
					20);
			menuLine("29", type + " Two-Handed Sword - 3 Bars", 1309, 21);
			menuLine("31", type + " Platelegs - 3 Bars", 1067, 22);
			menuLine("31", type + " Plateskirt - 3 Bars", 1081, 23);
			menuLine("33", type + " Platebody - 5 Bars", 1115, 24);
			optionTab("Smithing", "Iron", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		}

		else if (screen == 4) {
			clearMenu();
			String type = "Steel";
			menuLine("30", type + " Dagger - 1 Bar", 1207, 0);
			menuLine("31", type + " Hatchet - 1 Bar", 1353, 1);
			menuLine("32", type + " Mace - 1 Bar", 1424, 2);
			menuLine("33", type + " Med Helm - 1 Bar", 1141, 3);
			menuLine("34", type + " Sword - 1 Bar", 1281, 4);
			menuLine("34", type + " Dart Tips - 1 Bar makes 10", 821, 5);
			menuLine("34", type + " Nails - 1 Bar makes 15", 1539, 6);
			menuLine("35", type + " Scimitar - 2 Bars", 1325, 7);
			menuLine("35", type + " Spear - 1 Bar + 1 Log", 1241, 8);
			menuLine("35", type + " Arrowhead - 1 Bar makes 15", 41, 9);
			menuLine("36", type + " Longsword - 2 Bars", 1295, 10);
			menuLine("36", type + " Studs - 1 Bar", 2370, 11);
			menuLine("37", type + " Full Helm - 2 Bars", 1157, 12);
			menuLine("37", type + " Throwing Knife", 865, 13);
			menuLine("38", type + " Square Shield - 2 Bars", 1177, 14);
			menuLine("39", type + " Warhammer - 3 Bars", 1339, 15);
			menuLine("40", type + " Battleaxe - 3 Bars", 1365, 16);
			menuLine("41", type + " Chainbody - 3 Bars", 1105, 17);
			menuLine("42", type + " Kiteshield - 3 Bars", 1193, 18);
			menuLine("43", type + " Claws(After Death Plateu) - 2 Bars", 3097,
					19);
			menuLine("44", type + " Two-Handed Sword - 3 Bars", 1311, 20);
			menuLine("46", type + " Platelegs - 3 Bars", 1069, 21);
			menuLine("46", type + " Plateskirt - 3 Bars", 1083, 22);
			menuLine("48", type + " Platebody - 5 Bars", 1119, 23);
			optionTab("Smithing", "Steel", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 5) {
			clearMenu();
			String type = "Mithril";
			menuLine("50", type + " Dagger - 1 Bar", 1209, 0);
			menuLine("51", type + " Hatchet - 1 Bar", 1355, 1);
			menuLine("52", type + " Mace - 1 Bar", 1428, 2);
			menuLine("53", type + " Med Helm - 1 Bar", 1143, 3);
			menuLine("54", type + " Sword - 1 Bar", 1285, 4);
			menuLine("54", type + " Dart Tips - 1 Bar makes 10", 822, 5);
			menuLine("54", type + " Nails - 1 Bar makes 15", 4822, 6);
			menuLine("55", type + " Scimitar - 2 Bars", 1329, 7);
			menuLine("55", type + " Spear - 1 Bar + 1 Log", 1243, 8);
			menuLine("55", type + " Arrowhead - 1 Bar makes 15", 42, 9);
			menuLine("56", type + " Longsword - 2 Bars", 1299, 10);
			menuLine("57", type + " Full Helm - 2 Bars", 1159, 11);
			menuLine("57", type + " Throwing Knife", 866, 12);
			menuLine("58", type + " Square Shield - 2 Bars", 1181, 13);
			menuLine("59", type + " Warhammer - 3 Bars", 1343, 14);
			menuLine("60", type + " Battleaxe - 3 Bars", 1369, 15);
			menuLine("61", type + " Chainbody - 3 Bars", 1109, 16);
			menuLine("62", type + " Kiteshield - 3 Bars", 1197, 17);
			menuLine("63", type + " Claws(After Death Plateau) - 2 Bars", 3099,
					18);
			menuLine("64", type + " Two-Handed Sword - 3 Bars", 1315, 19);
			menuLine("66", type + " Platelegs - 3 Bars", 1071, 20);
			menuLine("66", type + " Plateskirt - 3 Bars", 1085, 21);
			menuLine("68", type + " Platebody - 5 Bars", 1121, 22);
			optionTab("Smithing", "Mithril", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 6) {
			clearMenu();
			String type = "Adamant";
			menuLine("70", type + " Dagger - 1 Bar", 1211, 0);
			menuLine("71", type + " Hatchet - 1 Bar", 1357, 1);
			menuLine("72", type + " Mace - 1 Bar", 1430, 2);
			menuLine("73", type + " Med Helm - 1 Bar", 1145, 3);
			menuLine("74", type + " Sword - 1 Bar", 1287, 4);
			menuLine("74", type + " Dart Tips - 1 Bar makes 10", 823, 5);
			menuLine("74", type + " Nails - 1 Bar makes 15", 4823, 6);
			menuLine("75", type + " Scimitar - 2 Bars", 1331, 7);
			menuLine("75", type + " Spear - 1 Bar + 1 Log", 1245, 8);
			menuLine("75", type + " Arrowhead - 1 Bar makes 15", 43, 9);
			menuLine("76", type + " Longsword - 2 Bars", 1301, 10);
			menuLine("77", type + " Full Helm - 2 Bars", 1161, 11);
			menuLine("77", type + " Throwing Knife", 867, 12);
			menuLine("78", type + " Square Shield - 2 Bars", 1183, 13);
			menuLine("79", type + " Warhammer - 3 Bars", 1343, 14);
			menuLine("80", type + " Battleaxe - 3 Bars", 1371, 15);
			menuLine("81", type + " Chainbody - 3 Bars", 1111, 16);
			menuLine("82", type + " Kiteshield - 3 Bars", 1199, 17);
			menuLine("83", type + " Claws(After Death Plateau) - 2 Bars", 3100,
					18);
			menuLine("84", type + " Two-Handed Sword - 3 Bars", 1317, 19);
			menuLine("86", type + " Platelegs - 3 Bars", 1073, 20);
			menuLine("86", type + " Plateskirt - 3 Bars", 1091, 21);
			menuLine("88", type + " Platebody - 5 Bars", 1123, 22);
			optionTab("Smithing", "Adamantite", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 7) {
			clearMenu();
			String type = "Rune";
			menuLine("85", type + " Dagger - 1 Bar", 1213, 0);
			menuLine("86", type + " Hatchet - 1 Bar", 1359, 1);
			menuLine("87", type + " Mace - 1 Bar", 1432, 2);
			menuLine("88", type + " Med Helm - 1 Bar", 1147, 3);
			menuLine("89", type + " Sword - 1 Bar", 1289, 4);
			menuLine("89", type + " Dart Tips - 1 Bar makes 10", 824, 5);
			menuLine("89", type + " Nails - 1 Bar makes 15", 4824, 6);
			menuLine("90", type + " Scimitar - 2 Bars", 1333, 7);
			menuLine("90", type + " Spear - 1 Bar + 1 Log", 1247, 8);
			menuLine("90", type + " Arrowhead - 1 Bar makes 15", 44, 9);
			menuLine("91", type + " Longsword - 2 Bars", 1303, 10);
			menuLine("92", type + " Full Helm - 2 Bars", 1163, 11);
			menuLine("92", type + " Throwing Knife", 868, 12);
			menuLine("93", type + " Square Shield - 2 Bars", 1185, 13);
			menuLine("94", type + " Warhammer - 3 Bars", 1347, 14);
			menuLine("95", type + " Battleaxe - 3 Bars", 1373, 15);
			menuLine("96", type + " Chainbody - 3 Bars", 1113, 16);
			menuLine("97", type + " Kiteshield - 3 Bars", 1201, 17);
			menuLine("98", type + " Claws(After Death Plateau) - 2 Bars", 3101,
					18);
			menuLine("99", type + " Two-Handed Sword - 3 Bars", 1319, 19);
			menuLine("99", type + " Platelegs - 3 Bars", 1079, 20);
			menuLine("99", type + " Plateskirt - 3 Bars", 1093, 21);
			menuLine("99", type + " Platebody - 5 Bars", 1127, 22);
			optionTab("Smithing", "Runite", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 8) {
			clearMenu();
			menuLine("50", "Gold Bowl(After Starting Legends' Quest)", 721, 0);
			menuLine("50", "Gold Helmet(After starting Between a Rock)", 	4567, 1);
			optionTab("Smithing", "Gold", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 9) {
			clearMenu();
			menuLine("20", "Elemental Shield(After Elemental Workshop)", 2890,
					0);
			optionTab("Smithing", "Elemental", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		} else if (screen == 10) {
			clearMenu();
			menuLine("60", "Dragon Square Shield", 1187, 0);
			optionTab("Smithing", "Other", "Smelting", "Bronze", "Iron",
					"Steel", "Mithril", "Adamantite", "Runite", "Gold",
					"Elemental", "Other", "", "", "");
		}
	}

	/*
	 * Skill ID: 16
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void fishingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Small Net", 303, 0);
			menuLine("5", "Bait Fishing", 307, 1);
			menuLine("16", "Big Net Fishing", 305, 2);
			menuLine("20", "Fly Fishing Rod", 309, 3);
			menuLine("35", "Harpoon", 311, 4);
			menuLine("40", "Lobster Pot", 301, 5);
			menuLine("65", "Vessel Fishing", 3157, 6);
			optionTab("Fishing", "Techniques", "Techniques", "Catches",
					"", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Shrimp - Net Fishing", 317, 0);
			menuLine("5", "Sardine - Sea Bait Fishing", 327, 1);
			menuLine("5", "Karambwanji - Net Fishing", 3150, 2);
			menuLine("10", "Herring - Sea Bait Fishing", 347, 3);
			menuLine("15", "Anchovie - Net Fishing", 321, 4);
			menuLine("16", "Mackerel - Big Net Fishing", 353, 5);
			menuLine("16", "Oyster - Big Net Fishing", 407, 6);
			menuLine("16", "Casket - Big Net Fishing", 405, 7);
			menuLine("16", "Seaweed - Big Net Fishing", 401, 8);
			menuLine("20", "Trout - Fly Fishing", 335, 9);
			menuLine("23", "Cod - Big Net Fishing", 341, 10);
			menuLine("25", "Pike - River Bait Fishing", 349, 11);
			menuLine("28", "Slimy Eel - River Bait Fishing", 3379, 12);
			menuLine("30", "Salmon - Fly Fishing", 331, 13);
			menuLine("35", "Tuna - Harpoon Fishing", 359, 14);
			menuLine("38", "Cave Eel - River Bait Fishing", 5001, 15);
			menuLine("40", "Lobster - Lobster Pot Fishing", 377, 16);
			menuLine("46", "Bass - Big Net Fishing", 363, 17);
			menuLine("50", "Swordfish - Harpoon Fishing", 371, 18);
			menuLine("53", "Lava Eel - Bait Fishing", 2148, 19);
			menuLine("62", "Monkfish - Net Fishing", 7944, 20);
			menuLine("65", "Karambwan - Vessel Fishing", 3142, 21);
			menuLine("76", "Shark - Harpoon Fishing", 383, 22);
			menuLine("79", "Sea Turtle - Fishing Trawler", 395, 23);
			menuLine("81", "Manta Ray - Fishing Trawler", 389, 24);
			optionTab("Fishing", "Catches", "Techniques", "Catches",
					"", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("68", "Fishing Guild", 385, 0);
			optionTab("Fishing", "", "Techniques", "Catches",
					"", "", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 17
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void cookingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Meat", 2142, 0);
			menuLine("1", "Shrimp", 315, 1);
			menuLine("1", "Chicken", 2140, 2);
			menuLine("1", "Rabbit", 3228, 3);
			menuLine("1", "Anchovies", 319, 4);
			menuLine("1", "Sardine", 325, 5);
			menuLine("1", "Karambwanji", 3151, 6);
			menuLine("1", "Karambwan", 3144, 7);
			menuLine("1", "Ugthanki Kebab", 1883, 8);
			menuLine("5", "Herring", 347, 9);
			menuLine("10", "Mackerel", 355, 10);
			menuLine("12", "Thin Snail", 3363, 11);
			menuLine("15", "Trout", 333, 12);
			menuLine("16", "Spider", 6293, 13);
			menuLine("16", "Roasted Rabbit", 7223, 14);
			menuLine("17", "Lean Snail", 3365, 15);
			menuLine("18", "Cod", 339, 16);
			menuLine("20", "Pike", 351, 17);
			menuLine("22", "Fat Snail", 3367, 18);
			menuLine("25", "Salmon", 329, 19);
			menuLine("28", "Slimy Eel", 3379, 20);
			menuLine("30", "Tuna", 361, 21);
			menuLine("30", "Roasted Chompy", 2878, 22);
			menuLine("31", "Fishcake", 7530, 23);
			menuLine("38", "Cave Eel", 5003, 24);
			menuLine("40", "Lobster", 379, 25);
			menuLine("41", "Jubbly", 7568, 26);
			menuLine("43", "Bass", 365, 27);
			menuLine("45", "Swordfish", 373, 28);
			menuLine("53", "Lava Eel", 2149, 29);
			menuLine("62", "Monkfish", 7946, 30);
			menuLine("80", "Shark", 385, 31);
			menuLine("82", "Sea Turtle", 397, 32);
			menuLine("91", "Manta Ray", 391, 33);
			optionTab("Cooking", "Meats", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Bread", 2309, 0);
			menuLine("58", "Pitta Bread", 1865, 1);
			menuLine("", "To make bread:", 0, 2);
			menuLine("", "1.Pick some grain and use a hopper to make flour", 0,
					3);
			menuLine("", "2.Use a pot to collect the flour you have made", 0, 4);
			menuLine("", "3.Fill a bucket or jug with water from a sink", 0, 5);
			menuLine("", "4.Mix the flour and water to make some dough", 0, 6);
			menuLine("", "5.Cook the dough by using it with a stove", 0, 7);
			optionTab("Cooking", "Bread", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("10", "Redberry Pie", 2325, 0);
			menuLine("20", "Meat Pie", 2327, 1);
			menuLine("29", "Mud Pie", 7170, 2);
			menuLine("30", "Apple Pie", 2323, 3);
			menuLine("34", "Garden Pie", 7178, 4);
			menuLine("47", "Fish Pie", 7188, 5);
			menuLine("70", "Admiral Pie", 7198, 6);
			menuLine("85", "Wild Pie", 7208, 7);
			menuLine("95", "Summer Pie", 7218, 8);
			menuLine("", "To make a pie:", 0, 9);
			menuLine("", "1.Mixe flour and water to make pastry dough", 0, 10);
			menuLine("", "2.Place the dough in an empty pie dish", 0, 11);
			menuLine("", "3.Use our choice of filling with the empty pie", 0,
					12);
			menuLine("", "4.Cook the pie by using it with a stove", 0, 13);
			optionTab("Cooking", "Pies", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("25", "Stew", 2003, 0);
			menuLine("60", "Curry", 2011, 1);
			menuLine("", "To make stew:", 0, 2);
			menuLine("", "1.Obtain a bowl and fill it with water", 0, 3);
			menuLine("", "2.Pick a potato and place it in the bowl.", 0, 4);
			menuLine("", "3.Cook some meat and place it in the bowl", 0, 5);
			menuLine("", "4.Cook the stew by using it with a stove or fire", 0,
					6);
			menuLine("", "To make curry:", 0, 7);
			menuLine("", "Make uncooked stew as above.", 0, 8);
			menuLine("", "Before cooking, add some spices or curry leaves", 0,
					9);
			optionTab("Cooking", "Stews", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 5) {
			clearMenu();
			menuLine("35", "Plain Pizza", 2289, 0);
			menuLine("45", "Meat Pizza", 2293, 1);
			menuLine("55", "Anchovy Pizza", 2297, 2);
			menuLine("65", "Pineapple Pizza", 2301, 3);
			menuLine("", "To make a pizza:", 0, 4);
			menuLine("", "1.Mix flour and water to make a pizza base", 0, 5);
			menuLine("", "2.Add a tomato to the pizza", 0, 6);
			menuLine("", "3.Add some cheese to the pizza", 0, 7);
			menuLine("", "4.Cook the pizza by using it with a stove", 0, 8);
			menuLine("", "5.Add your choice of topping to the pizza", 0, 9);
			optionTab("Cooking", "Pizzas", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 6) {
			clearMenu();
			menuLine("40", "Cake", 1891, 0);
			menuLine("50", "Chocolate Cake", 1897, 1);
			menuLine("", "To make a cake:", 0, 2);
			menuLine("", "1.Mix flour, eggs and milk together in a cake tin",
					0, 3);
			menuLine("", "2.Cook the cake by using it with a stove", 0, 4);
			menuLine("", "3.Optional:Buy some chocolate and add", 0, 5);
			menuLine("", "it to the cake to make a choclate cake", 0, 6);
			optionTab("Cooking", "Cakes", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 7) {
			clearMenu();
			menuLine("35", "Wine", 1993, 0);
			menuLine("", "To make wine:", 0, 1);
			menuLine("", "1.Fill a jug with water", 0, 2);
			menuLine("", "2.Use grapes with the jug of water", 0, 3);
			menuLine("", "3.Wait until the wine ferments", 0, 4);
			menuLine("", "4.The wine will ferment while left in your", 0, 5);
			menuLine("", "inventory or the bank", 0, 6);
			optionTab("Cooking", "Wine", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 8) {
			clearMenu();
			menuLine("20", "Nettle Tea", 1978, 0);
			menuLine("", "To make nettle tea:", 0, 1);
			menuLine("", "1.Fill a bowl with water", 0, 2);
			menuLine("", "2.Put some picked nettles into the bowl of water", 0,
					3);
			menuLine("", "3.Boil the nettle-water by using it with a range", 0,
					4);
			menuLine("", "4.Use the bowl of nettle tea with a cup", 0, 5);
			menuLine("", "5.If you take milk, use some milk on the tea", 0, 6);
			optionTab("Cooking", "Hot Drinks", "Meats", "Bread", "Pies",
					"Stews", "Pizzas", "Cakes", "Wine", "Hot Drinks",
					"Brewing", "Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 9) {
			clearMenu();
			menuLine("14", "Cider(4 Apple Mush)", 5763, 0);
			menuLine("19", "Dwarven Stout(4 Hammerstone Hops)", 1913, 1);
			menuLine("24", "Asgarnian Ale(4 Asgarnian Hops)", 1905, 2);
			menuLine("29", "Greenman's Ale(4 Harralander Leaves)", 1909, 3);
			menuLine("34", "Wizard's Mind Bomb(4 Yanillian Hops)", 1907, 4);
			menuLine("39", "Dragon Bitter(4 Krandorian Hops)", 1911, 5);
			menuLine("44", "Moonlight Mead(4 Bittercap Mushrooms)", 2955, 6);
			menuLine("49", "Axeman's Folly(1 Oak Root)", 5751, 7);
			menuLine("54", "Chef's Delight(4 Portions of Chocolate Dust)",
					5755, 8);
			menuLine("59", "Slayer's Respite(4 Wildblood Hops)", 5759, 9);
			optionTab("Cooking", "Brewing", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 10) {
			clearMenu();
			menuLine("7", "Baked Potato", 6701, 0);
			menuLine("9", "Spicy Sauce(Topping Ingredient)", 7072, 1);
			menuLine("11", "Chilli Con Carne(Topping)", 7062, 2);
			menuLine("13", "Scrambled Egg(Topping Ingredient)", 7078, 3);
			menuLine("23", "Scrambled Egg and Tomato(Topping)", 7064, 4);
			menuLine("28", "Sweetcorn", 5988, 5);
			menuLine("39", "Baked Potato with Butter", 6703, 6);
			menuLine("41", "Baked Potato with Chilli Con Carne", 7054, 7);
			menuLine("42", "Fried Onion(Topping Ingredient)", 7084, 8);
			menuLine("46", "Fried Mushroom(Topping Ingredient)", 7082, 9);
			menuLine("47", "Baked Potato with Butter and Cheese", 6705, 10);
			menuLine("51", "Baked Potato with Egg and Tomato", 7056, 11);
			menuLine("57", "Fried Mushroom and Onion(Topping)", 7066, 12);
			menuLine("64", "Baked Potato with Mushroom and Onion", 7058, 13);
			menuLine("67", "Tuna and Sweetcorn(Topping)", 7068, 14);
			menuLine("68", "Baked Potato with Tuna and Sweetcorn", 7060, 15);
			menuLine("", "To make baked potatoes with toppings:", 0, 16);
			menuLine("", "1.Bake the potato on a range", 0, 17);
			menuLine("", "2.Add some butter", 0, 18);
			menuLine("", "3.If needed, combine topping ingredients", 0, 19);
			menuLine("", "by chopping them into a bowl", 0, 20);
			menuLine("", "Ingredients for toppings:", 0, 21);
			menuLine("", "1.Chilli con carne: Meat & spicy sauce", 0, 22);
			menuLine("", "2.Egg and tomato: Scrambled egg & tomato", 0, 23);
			menuLine("", "3.Mushroom and onion: Fried mushroom & onion", 0, 24);
			menuLine("", "4.Tuna and sweetcorn: Tuna & cooked sweetcorn", 0, 25);
			optionTab("Cooking", "Potatoes", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 11) {
			clearMenu();
			menuLine("4", "Chocolate Milk", 1977, 0);
			menuLine("21", "Cream", 2130, 1);
			menuLine("38", "Butter", 6697, 2);
			menuLine("48", "Cheese", 1985, 3);
			menuLine("", "To make churned dairy products:", 0, 4);
			menuLine("", "1.Get a bucket of milk, a pot of cream or butter", 0,
					5);
			menuLine("", "2.Use the milk, cream or butter in a churn", 0, 6);
			menuLine("", "3.Milk can be churned into cream, ", 0, 7);
			menuLine("", "then into butter, then into cheese", 0, 8);
			optionTab("Cooking", "Dairy", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 12) {
			clearMenu();
			menuLine("6", "Fruit Blast", 2034, 0);
			menuLine("8", "Pineapple Punch", 2036, 1);
			menuLine("10", "Toad Crunchies", 2217, 2);
			menuLine("12", "Spicy Crunchies", 2213, 3);
			menuLine("14", "Worm Crunchies", 2237, 4);
			menuLine("16", "Chocolate Chip Crunchies", 2239, 5);
			menuLine("18", "Wizard Blizzard", 2040, 6);
			menuLine("20", "Short Green Guy(SGG)", 2038, 7);
			menuLine("25", "Fruit Batta", 2225, 8);
			menuLine("26", "Toad Batta", 2221, 9);
			menuLine("27", "Worm Batta", 2219, 10);
			menuLine("28", "Vegetable Batta", 2227, 11);
			menuLine("29", "Cheese and Tomato Batta", 2223, 12);
			menuLine("30", "Worm Hole", 2191, 13);
			menuLine("32", "Drunk Dragon", 2032, 14);
			menuLine("33", "Chocolate Saturday", 2030, 15);
			menuLine("35", "Vegetable Ball", 2195, 16);
			menuLine("37", "Blurberry Special", 2028, 17);
			menuLine("40", "Tangled Toads' Legs", 2187, 18);
			menuLine("42", "Chocolate Bomb", 2185, 19);
			optionTab("Cooking", "Gnome", "Meats", "Bread", "Pies", "Stews",
					"Pizzas", "Cakes", "Wine", "Hot Drinks", "Brewing",
					"Potatoes", "Diary", "Gnome", "");
		}

		else if (screen == 13) {
			clearMenu();
			menuLine("32", "Chefs' Guild", 1949, 0);
			optionTab("Cooking", "", "Meats", "Bread", "Pies",
					"Stews", "Pizzas", "Cakes", "Wine", "Hot Drinks",
					"Brewing", "Potatoes", "Diary", "Gnome", "");
		}
	}

	/*
	 * Skill ID: 18
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void firemakingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Normal Logs", 1511, 0);
			menuLine("1", "Torch", 596, 1);
			menuLine("1", "Candle", 36, 2);
			menuLine("1", "Achey Logs", 2862, 3);
			menuLine("4", "Candle Lantern", 4527, 4);
			menuLine("5", "Pyre Logs", 3438, 5);
			menuLine("12", "Oil Lamp", 4522, 6);
			menuLine("15", "Oak Logs", 1521, 7);
			menuLine("20", "Iron Spit", 7225, 8);
			menuLine("20", "Oak Pyre Logs", 3440, 9);
			menuLine("26", "Oil Lantern", 4535, 10);
			menuLine("30", "Willow Logs", 1519, 11);
			menuLine("33", "Harpie Bug Lantern", 7051, 12);
			menuLine("35", "Teak Logs", 6333, 13);
			menuLine("35", "Willow Pyre Logs", 3442, 14);
			menuLine("40", "Teak Pyre Logs", 6211, 15);
			menuLine("45", "Maple Logs", 1517, 16);
			menuLine("49", "Bullseye Lantern", 4546, 17);
			menuLine("49", "Sapphire Lantern", 4700, 18);
			menuLine("50", "Mahogany Logs", 6332, 19);
			menuLine("50", "Maple Pyre Logs", 3444, 20);
			menuLine("55", "Mahogany Pyre Logs", 6213, 21);
			menuLine("60", "Yew Logs", 1515, 22);
			menuLine("65", "Cave Goblin Mining Helmet", 5014, 23);
			menuLine("65", "Yew Pyre Logs", 3446, 24);
			menuLine("75", "Magic Logs", 1513, 25);
			menuLine("80", "Magic Pyre Logs", 3448, 26);
			optionTab("Firemaking", "Firemaking", "Firemaking", "Equipment",
					"", "", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Tinderbox", 590, 0);
			optionTab("Firemaking", "Equipment", "Firemaking", "Equipment",
					"", "", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 19
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void woodcuttingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Normal Tree", 1511, 0);
			menuLine("1", "Achey Tree", 2862, 1);
			menuLine("10", "Light Jungle", 6281, 2);
			menuLine("15", "Oak Tree", 1521, 3);
			menuLine("20", "Medium Jungle", 6283, 4);
			menuLine("30", "Willow Tree", 1519, 5);
			menuLine("35", "Dense Jungle", 6285, 6);
			menuLine("35", "Teak Tree", 6333, 7);
			menuLine("45", "Maple Tree", 1517, 8);
			menuLine("45", "Hollow Tree", 3239, 9);
			menuLine("50", "Mahogany Tree", 6332, 10);
			menuLine("60", "Yew Tree", 1515, 11);
			menuLine("75", "Magic Tree", 1513, 12);
			optionTab("Woodcutting", "Trees", "Trees", "Hatchets", "Canoes",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("1", "Bronze Axe", 1351, 0);
			menuLine("1", "Iron Axe", 1349, 1);
			menuLine("6", "Steel Axe", 1353, 2);
			menuLine("6", "Black Axe", 1361, 3);
			menuLine("21", "Mithril Axe", 1355, 4);
			menuLine("31", "Adamant Axe", 1357, 5);
			menuLine("41", "Rune Axe", 1359, 6);
			menuLine("61", "Dragon Axe", 6739, 7);
			optionTab("Woodcutting", "Hatchets", "Trees", "Hatchets", "Canoes",
					"", "", "", "", "", "", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("12", "Log Canoe", 7414, 0);
			menuLine("27", "Dugout Canoe", 7414, 1);
			menuLine("42", "Stable Dugout Canoe", 7414, 2);
			menuLine("57", "Waka Canoe", 7414, 3);
			optionTab("Woodcutting", "Canoes", "Trees", "Hatchets", "Canoes",
					"", "", "", "", "", "", "", "", "", "");
		}
	}

	/*
	 * Skill ID: 20
	 * 
	 * @param screen
	 * 
	 * @return
	 */
	public void farmingComplex(int screen) {
		if (screen == 1) {
			clearMenu();
			menuLine("1", "Potato", 1942, 0);
			menuLine("5", "Onion", 1957, 1);
			menuLine("7", "Cabbage", 1965, 2);
			menuLine("12", "Tomato", 1982, 3);
			menuLine("20", "Sweetcorn", 5986, 4);
			menuLine("31", "Strawberry", 5504, 5);
			menuLine("47", "Watermelon", 5982, 6);
			optionTab("Farming", "Allotments", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 2) {
			clearMenu();
			menuLine("3", "Barley", 6006, 0);
			menuLine("4", "Hammerstone Hop", 5994, 1);
			menuLine("8", "Asgarnian Hop", 5996, 2);
			menuLine("13", "Jute Plant", 5931, 3);
			menuLine("16", "Yanillian Hop", 5998, 4);
			menuLine("21", "Krandorian Hop", 6000, 5);
			menuLine("28", "Wildblood Hop", 6002, 6);
			optionTab("Farming", "Allotments", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 3) {
			clearMenu();
			menuLine("15", "Oak Tree", 1521, 0);
			menuLine("30", "Willow Tree", 1519, 1);
			menuLine("45", "Maple Tree", 1517, 2);
			menuLine("60", "Yew Tree", 1515, 3);
			menuLine("75", "Magic Tree", 1513, 4);
			optionTab("Farming", "Trees", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 4) {
			clearMenu();
			menuLine("27", "Apple Tree", 1955, 0);
			menuLine("33", "Banana Tree", 1963, 1);
			menuLine("39", "Orange Tree", 2108, 2);
			menuLine("42", "Curry Tree", 5970, 3);
			menuLine("51", "Pineapple Plant", 2114, 4);
			menuLine("57", "Papaya Tree", 5972, 5);
			menuLine("68", "Palm Tree", 5974, 6);
			optionTab("Farming", "Fruit Trees", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 5) {
			clearMenu();
			menuLine("10", "Redberry Bush", 1951, 0);
			menuLine("22", "Cadavaberry Bush", 753, 1);
			menuLine("36", "Dwellberry Bush", 2126, 2);
			menuLine("48", "Jangerberry Bush", 247, 3);
			menuLine("59", "White Berry Bush", 239, 4);
			menuLine("70", "Poison Ivy Bush", 6018, 5);
			optionTab("Farming", "Bushes", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 6) {
			clearMenu();
			menuLine("2", "Marigold(Protects low level crops from Disease)",
					6010, 0);
			menuLine("11", "Rosemary(Protects Cabbages from Disease)", 6014, 1);
			menuLine("24", "Nasturtium(Protects Watermelons from Disease)",
					6012, 2);
			menuLine("25", "Woad", 1793, 3);
			menuLine("26", "Limpwurt", 225, 4);
			optionTab("Farming", "Flowers", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 7) {
			clearMenu();
			menuLine("9", "Guam", 249, 0);
			menuLine("14", "Marrentill", 251, 1);
			menuLine("19", "Tarromin", 253, 2);
			menuLine("26", "Harralander", 255, 3);
			menuLine("32", "Ranarr", 257, 4);
			menuLine("38", "Toadflax", 2998, 5);
			menuLine("44", "Irit", 259, 6);
			menuLine("50", "Avantoe", 261, 7);
			menuLine("56", "Kwuarm", 263, 8);
			menuLine("62", "Snapdragon", 3000, 9);
			menuLine("67", "Cadantine", 265, 10);
			menuLine("73", "Lantadyme", 2481, 11);
			menuLine("79", "Dwarf Weed", 267, 12);
			menuLine("85", "Torstol", 269, 13);
			optionTab("Farming", "Herbs", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 8) {
			clearMenu();
			menuLine("55", "Cactus", 6016, 0);
			menuLine("63", "Belladonna", 5281, 1);
			menuLine("72", "Calquat Tree", 5980, 2);
			menuLine("83", "Spirit Tree", 6063, 3);
			optionTab("Farming", "Special", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}

		else if (screen == 9) {
			clearMenu();
			menuLine("23", "Able to make and place a scarecrow", 6059, 0);
			menuLine("", "", 0, 1);
			menuLine("", "Scarecrows help to stop sweetcorn from", 0, 2);
			menuLine("", "being attacked by birds, while also", 0, 3);
			menuLine("", "helping to prevent disease", 0, 4);
			menuLine("", "", 0, 5);
			menuLine("", "How to make a scarecrow:", 0, 6);
			menuLine("", "", 0, 7);
			menuLine("", "1.Fill an empty sack with straw.", 0, 8);
			menuLine("", "2.Drive a hay sack onto a bronze spear", 0, 9);
			menuLine("", "3.Place a watermelon at the top as a head", 0, 10);
			menuLine("", "4.Stand the scarecrow in a flower patch", 0, 11);
			optionTab("Farming", "Scarecrows", "Allotments", "Hops", "Trees",
					"Fruit Trees", "Bushes", "Flowers", "Herbs", "Special",
					"Scarecrows", "", "", "", "");
		}
	}
}
