package redone;

public class Constants {

	public final static boolean SERVER_DEBUG = false;

	public final static String SERVER_NAME = "2006Redone", SERVER_VERSION = "Server Stage v " + Constants.TEST_VERSION + ".";
	public final static double TEST_VERSION = 1.13;
	public static int BANK_SIZE = 352;
	public final static int ITEM_LIMIT = 15000, MAXITEM_AMOUNT = Integer.MAX_VALUE, CLIENT_VERSION = 999999,
			WORLD = 1, IPS_ALLOWED = 3, CONNECTION_DELAY = 100,
			MESSAGE_DELAY = 6000, MAX_PLAYERS = 100, REQ_AMOUNT = 150;
	public final static boolean SOUND = true, MEMBERS_AREAS = true,
			GUILDS = true, MEMBERSHIP = true, WORLD_LIST_FIX = false,
			PARTY_ROOM_DISABLED = true, combatSounds = true,
			printobjectId = false, EXPERIMENTS = false;
	public static int[] SIDEBARS = { 2423, 3917, 638, 3213, 1644, 5608, 1151,
			18128, 5065, 5715, 2449, 904, 147, 962 };
	public static boolean TUTORIAL_ISLAND = true, HOLIDAYS = true,
			MEMBERS_ONLY = false, sendServerPackets = false, HALLOWEEN = false;
	
	public final static int HAT = 0, CAPE = 1, AMULET = 2, WEAPON = 3,
			CHEST = 4, SHIELD = 5, LEGS = 7, HANDS = 9, FEET = 10, RING = 12,
			ARROWS = 13;

	public final static int[] COMBAT_RELATED_ITEMS = { 35, 39, 40, 41, 42, 43,
			44, 50, 53, 54, 60, 64, 75, 76, 78, 88, 546, 548, 577, 581, 598,
			626, 628, 630, 632, 634, 667, 687, 746, 747, 767, 772, 775, 776,
			777, 778, 818, 837, 839, 841, 843, 845, 847, 849, 851, 853, 855,
			857, 859, 861, 863, 864, 865, 866, 867, 868, 869, 870, 871, 872,
			873, 874, 875, 876, 877, 878, 879, 880, 881, 882, 883, 884, 885,
			886, 887, 888, 889, 890, 891, 892, 893, 942, 975, 1007, 1019, 1021,
			1023, 1027, 1029, 1031, 1033, 1035, 1052, 1059, 1061, 1063, 1065,
			1067, 1069, 1071, 1073, 1075, 1077, 1079, 1081, 1083, 1085, 1087,
			1089, 1091, 1093, 1095, 1097, 1099, 1101, 1103, 1105, 1107, 1109,
			1111, 1113, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 1129, 1131,
			1133, 1135, 1137, 1139, 1141, 1143, 1145, 1147, 1149, 1151, 1153,
			1155, 1157, 1159, 1161, 1163, 1165, 1167, 1169, 1171, 1173, 1175,
			1177, 1179, 1181, 1183, 1185, 1187, 1189, 1191, 1193, 1195, 1197,
			1199, 1201, 1203, 1205, 1207, 1209, 1211, 1213, 1215, 1217, 1219,
			1221, 1223, 1225, 1227, 1229, 1231, 1233, 1237, 1239, 1241, 1243,
			1245, 1247, 1249, 1251, 1253, 1255, 1257, 1259, 1261, 1263, 1265,
			1267, 1269, 1271, 1273, 1275, 1277, 1279, 1281, 1283, 1285, 1287,
			1289, 1291, 1293, 1295, 1297, 1299, 1301, 1303, 1305, 1307, 1309,
			1311, 1313, 1315, 1317, 1319, 1321, 1323, 1325, 1327, 1329, 1331,
			1333, 1335, 1337, 1339, 1341, 1343, 1345, 1347, 1349, 1351, 1353,
			1355, 1357, 1359, 1361, 1363, 1365, 1367, 1369, 1371, 1373, 1375,
			1377, 1379, 1381, 1383, 1385, 1387, 1389, 1391, 1393, 1395, 1397,
			1399, 1401, 1403, 1405, 1407, 1409, 1419, 1420, 1422, 1424, 1426,
			1428, 1430, 1432, 1434, 1540, 1718, 1724, 2402, 2412, 2413, 2414,
			2415, 2416, 2417, 2487, 2489, 2491, 2493, 2495, 2497, 2499, 2501,
			2503, 2513, 2532, 2533, 2534, 2535, 2536, 2537, 2538, 2539, 2540,
			2541, 2577, 2579, 2581, 2583, 2585, 2587, 2589, 2591, 2593, 2595,
			2597, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 2615, 2617,
			2619, 2621, 2623, 2625, 2627, 2629, 2653, 2655, 2659, 2661, 2663,
			2667, 2669, 2671, 2673, 2861, 2864, 2865, 2866, 2890, 2896, 2906,
			2916, 2926, 2936, 2961, 2963, 3053, 3054, 3095, 3096, 3097, 3098,
			3099, 3100, 3101, 3105, 3107, 3122, 3140, 3170, 3171, 3172, 3173,
			3174, 3175, 3176, 3190, 3192, 3194, 3196, 3198, 3200, 3202, 3204,
			3327, 3329, 3331, 3333, 3335, 3337, 3339, 3341, 3343, 3385, 3387,
			3389, 3391, 3393, 3472, 3473, 3474, 3475, 3476, 3477, 3479, 3481,
			3483, 3485, 3486, 3488, 3748, 3749, 3751, 3753, 3755, 3757, 3758,
			3759, 3761, 3763, 3765, 3767, 3769, 3771, 3773, 3775, 3777, 3779,
			3781, 3783, 3785, 3787, 3789, 3791, 3797, 3840, 3841, 3842, 3843,
			3844, 4087, 4089, 4091, 4093, 4095, 4097, 4099, 4101, 4103, 4105,
			4107, 4109, 4111, 4113, 4115, 4117, 4119, 4121, 4123, 4125, 4127,
			4129, 4131, 4150, 4151, 4153, 4156, 4158, 4160, 4170, 4172, 4173,
			4174, 4175, 4212, 4214, 4215, 4216, 4217, 4218, 4219, 4220, 4221,
			4222, 4223, 4224, 4226, 4227, 4228, 4229, 4230, 4231, 4232, 4233,
			4234, 4298, 4300, 4302, 4304, 4308, 4310, 4502, 4503, 4504, 4505,
			4506, 4507, 4508, 4509, 4510, 4511, 4512, 4580, 4582, 4585, 4587,
			4600, 4675, 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724,
			4726, 4728, 4730, 4732, 4734, 4736, 4738, 4740, 4745, 4747, 4749,
			4751, 4753, 4755, 4757, 4759, 4778, 4783, 4788, 4793, 4803, 4827,
			4860, 4866, 4872, 4878, 4884, 4890, 4896, 4902, 4908, 4914, 4920,
			4926, 4932, 4938, 4944, 4950, 4956, 4962, 4968, 4974, 4980, 4986,
			4992, 4998, 5014, 5016, 5018, 5553, 5554, 5555, 5556, 5557, 5574,
			5575, 5576, 5616, 5617, 5618, 5619, 5620, 5621, 5622, 5623, 5624,
			5625, 5626, 5627, 5648, 5654, 5655, 5656, 5657, 5658, 5659, 5660,
			5661, 5662, 5663, 5664, 5665, 5666, 5667, 5668, 5670, 5672, 5674,
			5676, 5678, 5680, 5682, 5686, 5688, 5690, 5692, 5694, 5696, 5698,
			5700, 5704, 5706, 5708, 5710, 5712, 5714, 5716, 5718, 5720, 5722,
			5724, 5726, 5728, 5730, 5734, 5736, 6061, 6062, 6106, 6107, 6108,
			6109, 6110, 6111, 6128, 6129, 6130, 6131, 6133, 6135, 6137, 6139,
			6141, 6143, 6145, 6147, 6149, 6151, 6153, 6235, 6257, 6279, 6313,
			6315, 6317, 6322, 6324, 6326, 6328, 6330, 6416, 6522, 6523, 6524,
			6525, 6526, 6527, 6528, 6562, 6563, 6568, 6570, 6587, 6589, 6591,
			6593, 6595, 6597, 6599, 6601, 6603, 6605, 6607, 6609, 6611, 6613,
			6615, 6617, 6619, 6621, 6623, 6625, 6627, 6629, 6631, 6633, 6720,
			6724, 6726, 6739, 6745, 6746, 6760, 6762, 6764, 6809, 6889, 6893,
			6894, 6895, 6897, 6908, 6910, 6912, 6914, 6916, 6918, 6920, 6922,
			6924, 6959, 7158, 7159, 7332, 7334, 7336, 7338, 7340, 7342, 7344,
			7346, 7348, 7350, 7352, 7354, 7356, 7358, 7360, 7362, 7364, 7366,
			7368, 7374, 7390, 7392, 7394, 7396, 7398, 7399, 7400, 7410, 7433,
			7435, 7437, 7439, 7441, 7443, 7445, 7447, 7449, 7451, 7453, 7454,
			7455, 7456, 7457, 7458, 7459, 7460, 7461, 7462, 7539, 7552, 7553,
			7639, 7640, 7641, 7642, 7643, 7644, 7645, 7646, 7647, 7648, 7668,
			7686, 7687, 7806, 7807, 7808, 7809 };

	public final static int[] ALCOHOL_RELATED_ITEMS = { 8940, 3803, 3712, 3711,
			2092, 2074, 3801 };

	public final static int[] ITEM_SELLABLE = { 3842, 3844, 3840, 8844, 8845,
			8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459,
			7458, 7457, 7456, 7455, 7454, 8839, 8840, 8842, 11663, 11664, 11666,
			10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784,
			9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766,
			9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803, 9809, 9785, 9800,
			9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747,
			9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804,
			9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 995, 2415,
			2416, 2417, 88, 1540, 2714, 432, 433, 1555, 1556, 1557, 1558, 1559,
			1560, 1561, 1562, 1563, 1564, 1565, 7585, 7584, 300, 775, 776, 777,
			6180, 6181, 6182, 6183, 6184, 6185, 6186, 6187, 6188, 2528, 4447,
			290, 666, 667 };
	public final static int[] ITEM_TRADEABLE = { 3842, 3844, 3840, 8844, 8845,
			8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459,
			7458, 7457, 7456, 7455, 7454, 8839, 8840, 8842, 11663, 11664,
			11665, 10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808,
			9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811,
			9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803, 9809, 9785,
			9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767,
			9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798,
			9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 2528,
			4447, 772, 6180, 6181, 6182, 6183, 6184, 6185, 6186, 6187, 6188,
			775, 776, 777, 300, 88, 2415, 2416, 2417, 4214, 4215, 4216, 4217,
			4218, 4219, 4220, 4221, 4222, 4223, 4224, 1555, 1556, 1557, 1558,
			1559, 1560, 1561, 1562, 1563, 1564, 1565, 7585, 7584, 2714, 432,
			433, 290, 5075, 5074, 5073, 5071, 5070, 7413, 6529, 4067, 2996, 1464, 666, 667 };

	public final static int[] ITEM_UNALCHABLE = { 995, 1555, 1556, 1557, 1558,
			1559, 1560, 1561, 1562, 1563, 1564, 1565, 7583, 1566, 7585, 2528,
			4214, 4212, 2714, 432, 433, 300, 775, 776, 777, 6180, 6181, 6182,
			6183, 6184, 6185, 6186, 6187, 6188, 2528, 4447, 290, 666, 667};
	
	public final static int[] ITEM_BANKABLE = {2528, 4447};
	
	public final static int[] DESTROYABLE_ITEMS = {775, 776, 777, 2528, 6570, 2714, 432, 433, 300, 666};

	public final static int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464,
			2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
			2476, 2477 }; // fun weapons for dueling

	public static boolean ADMIN_CAN_TRADE = false; // can admins trade?
	public final static boolean ADMIN_DROP_ITEMS = false;
	public final static boolean ADMIN_CAN_SELL_ITEMS = false;
	public final static int RESPAWN_X = 3222; // when dead respawn here
	public final static int RESPAWN_Y = 3218;
	public final static int DUELING_RESPAWN_X = 3362;
	public final static int DUELING_RESPAWN_Y = 3263;
	public final static int NO_TELEPORT_WILD_LEVEL = 20;
	public final static int SKULL_TIMER = 1200;
	public final static int TELEBLOCK_DELAY = 20000;
	public final static boolean SINGLE_AND_MULTI_ZONES = true;
	public final static boolean COMBAT_LEVEL_DIFFERENCE = true;
	public final static boolean itemRequirements = true;
	public final static int MELEE_EXP_RATE = 4; // damage * exp rate
	public final static int RANGE_EXP_RATE = 4;
	public final static int MAGIC_EXP_RATE = 4;
	public final static int CASTLE_WARS_X = 2439;
	public final static int CASTLE_WARS_Y = 3087;
	public static double SERVER_EXP_BONUS = 5;
	public final static int INCREASE_SPECIAL_AMOUNT = 17500;
	public final static boolean PRAYER_POINTS_REQUIRED = true;
	public final static boolean PRAYER_LEVEL_REQUIRED = true;
	public final static int GOD_SPELL_CHARGE = 300000;
	public final static boolean CORRECT_ARROWS = true;
	public final static boolean CRYSTAL_BOW_DEGRADES = true;
	public final static int SAVE_TIMER = 120; // save every 2 minute
	public final static int NPC_RANDOM_WALK_DISTANCE = 5;
	public final static int NPC_FOLLOW_DISTANCE = 10;
	public final static String[] UNDEAD = {
		"armoured zombie", "ankous", "banshee", "crawling hand", "dried zombie", "ghost", "ghostly warrior", "ghast",
		"mummy", "mighty banshee", "reventant imp", "reventant goblin",  "reventant icefiend",  "reventant pyrefiend",
		"reventant hobgoblin",  "reventant vampyre",  "reventant werewolf", "reventant cyclops", "reventant darkbeast",
		"reventant demon", "reventant ork",  "reventant hellhound", "reventant knight", "reventant dragon",
		"shade", "skeleton", "skeleton brute", "skeleton thug", "skeleton warload", "summoned zombie",
		"skorge", "tortured soul", "undead chicken", "undead cow", "undead one", "undead troll", "zombie", "zombie rat", "zogre"
	};
	public final static int TIMEOUT = 20;
	public final static int CYCLE_TIME = 600;
	public final static int BUFFER_SIZE = 10000;

	public final static int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
			HITPOINTS = 3, RANGED = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
			WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
			CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
			RUNECRAFTING = 20;
}
