package com.rebotted.game.content.skills.smithing;

/**
 * SmithingData
 * @author Andrew (Mr Extremez)
 */

public enum SmithingData {
	
	//id, xp, lvl, amount
	BRONZE_AXE(1351, 13, 1, 1),
	BRONZE_DAGGER(1205, 13, 1, 1),
	BRONZE_MACE(1422, 13, 2, 1),
	BRONZE_MED(1139, 13, 3, 1),
	BRONZE_DART(819, 13, 4, 1),
	BRONZE_SWORD(1277, 13, 4, 1),
	BRONZE_NAILS(4819, 13, 4, 1),
	BRONZE_TIPS(39, 13, 5, 1),
	BRONZE_KNIFE(864, 25, 7, 1),
	BRONZE_SCIM(1321, 25, 5, 2),
	BRONZE_LONG(1291, 25, 6, 2),
	BRONZE_FULL(1155, 25, 7, 2),
	BRONZE_SQ(1173, 25, 8, 2),
	BRONZE_HAMMER(1337, 38, 9, 3),
	BRONZE_BATTLE(1375, 38, 10, 3),
	BRONZE_CHAIN(1103, 38, 11, 3),
	BRONZE_KITE(1189, 38, 12, 3),
	BRONZE_2H(1307, 38, 14, 3),
	BRONZE_PLATELEGS(1075, 38, 16, 3),
	BRONZE_PLATESKIRT(1087, 38, 16, 3),
	BRONZE_PLATEBODY(1117, 63, 18, 5),
    IRON_AXE(1349, 25, 16, 1),
    IRON_DAGGER(1203, 25, 15, 1),
    IRON_MACE(1420, 25, 17, 1),
    IRON_MED(1137, 25, 18, 1),
    IRON_DART(820, 25, 19, 1),
    IRON_SWORD(1279, 25, 19, 1),
    IRON_NAILS(4820, 25, 19, 1),
    IRON_TIPS(40, 25, 20, 1),
    IRON_SCIM(1323, 25, 20, 2),
    IRON_LONG(1293, 50, 21, 2),
    IRON_KNIFE(863, 25, 22, 1),
    IRON_FULL(1153, 50, 22, 1),
    IRON_SQ(1175, 50, 23, 2),
    IRON_HAMMER(1335, 38, 24, 3),
    IRON_BATTLEAXE(1363, 75, 25, 3),
    IRON_CHAIN(1101, 75, 26, 3),
    IRON_KITE(1191, 75, 27, 3),
    IRON_2H(1309, 75, 29, 3),
    IRON_LEGS(1067, 75, 31, 3),
    IRON_SKIRT(1081, 75, 31, 3),
    IRON_BODY(1115, 100, 33, 3),
    STEEL_AXE(1353, 38, 31, 1),
    STEEL_DAGGER(1207, 50, 30, 1),
    STEEL_MACE(1424, 50, 32, 1),
    STEEL_MED(1141, 50, 33, 1),
    STEEL_SWORD(1281, 50, 34, 1),
    STEEL_NAILS(1539, 50, 34, 1),
    STEEL_TIPS(41, 50, 35, 1),
    STEEL_SCIM(1325, 75, 35, 2),
    STEEL_LONG(1295, 75, 36, 2),
    STEEL_KNIFE(865, 50, 37, 1),
    STEEL_FULL(1157, 75, 37, 2),
    STEEL_SQ(1177, 75, 38, 2),
    STEEL_HAMMER(1339, 113, 39, 3),
    STEEL_BATTLEAXE(1365, 113, 40, 3),
    STEEL_CHAIN(1105, 113, 41, 3),
    STEEL_KITE(1193, 113, 42, 3),
    STEEL_2H(1311, 113, 44, 3),
    STEEL_LEGS(1069, 113, 46, 3),
    STEEL_SKIRT(1083, 113, 46, 3),
    STEEL_PLATE(1119, 188, 48, 5),
    MITH_AXE(1355, 50, 51, 1),
    MITH_DAGGER(1209, 50, 50, 1),
    MITH_MACE(1428, 50, 52, 1),
    MITH_MED(1143, 50, 53, 1),
    MITH_SWORD(1285, 50, 54, 1),
    MITH_NAILS(4822, 50, 54, 1),
    MITH_TIPS(42, 50, 55, 1),
    MITH_SCIM(1329, 100, 55, 2),
    MITH_LONG(1299, 100, 56, 2),
    MITH_KNIFE(866, 50, 57, 1),
    MITH_FULL(1159, 100, 57, 2),
    MITH_SQ(1181, 100, 58, 2),
    MITH_HAMMER(1343, 150, 59, 3),
    MITH_BATTLEAXE(1369, 150, 60, 3),
    MITH_CHAIN(1109, 150, 61, 3),
    MITH_KITE(1197, 150, 62, 3),
    MITH_2H(1315, 150, 64, 3),
    MITH_LEGS(1071, 150, 66, 3),
    MITH_SKIRT(1085, 150, 66, 3),
    MITH_PLATE(1121, 250, 68, 5),
    ADDY_AXE(1357, 63, 71, 1),
    ADDY_DAGGER(1211, 63, 70, 1),
    ADDY_MACE(1430, 63, 72, 1),
    ADDY_MED(1145, 63, 73, 1),
    ADDY_SWORD(1287, 63, 74, 1),
    ADDY_NAILS(4823, 63, 74, 1),
    ADDY_TIPS(43, 63, 75, 1),
    ADDY_SCIM(1331, 125, 75, 2),
    ADDY_LONG(1301, 125, 76, 2),
    ADDY_KNIFE(867, 63, 77, 1),
    ADDY_HELM(1161, 125, 77, 2),
    ADDY_SQ(1183, 125, 78, 2),
    ADDY_HAMMER(1345, 188, 79, 3),
    ADDY_BATTLEAXE(1371, 188, 80, 3),
    ADDY_CHAIN(1111, 188, 81, 3),
    ADDY_KITE(1199, 188, 82, 3),
    ADDY_2H(1317, 188, 84, 3),
    ADDY_LEGS(1073, 188, 86, 3),
    ADDY_SKIRT(1091, 188, 86, 3),
    ADDY_PLATE(1123, 313, 88, 5),
    RUNE_AXE(1359, 75, 86, 1),
    RUNE_DAGGER(1213, 75, 85, 1),
    RUNE_MACE(1432, 75, 87, 1),
    RUNE_MED(1147, 75, 88, 1),
    RUNE_SWORD(1289, 75, 89, 1),
    RUNE_NAILS(4824, 75, 89, 1),
    RUNE_TIPS(44, 75, 90, 1),
    RUNE_SCIM(1333, 150, 90, 2),
    RUNE_LONG(1303, 150, 91, 2),
    RUNE_KNIFE(868, 75, 92, 1),
    RUNE_HELM(1163, 150, 92, 2),
    RUNE_SQ(1185, 150, 93, 2),
    RUNE_HAMMER(1347, 225, 94, 3),
    RUNE_BATTLEAXE(1373, 225, 95, 3),
    RUNE_CHAIN(1113, 225, 96, 3),
    RUNE_KITE(1201, 225, 97, 3),
    RUNE_2H(1319, 225, 99, 3),
    RUNE_LEGS(1079, 225, 99, 3),
    RUNE_SKIRT(1093, 225, 99, 3),
    RUNE_PLATE(1127, 313, 99, 5);
	
	private final int id, xp, lvl, barsNeeded;
	
	SmithingData(int id, int xp, int lvl, int barsNeeded) {
		this.id = id;
		this.xp = xp;
		this.lvl = lvl;
		this.barsNeeded = barsNeeded;
	}
	
	public int getId() {
		return id;
	}
	
	public int getXp() {
		return xp;
	}
	
	public int getLvl() {
		return lvl;
	}
	
	public int getAmount() {
		return barsNeeded;
	}

	public static SmithingData forId(int itemId) {
		for (SmithingData item : SmithingData.values()) {
			if (itemId == item.getId()) {
				return item;
			}
		}
		return null;
	}
}
