package redone.game.content.skills.smithing;

import redone.game.players.Client;

public class SmithingInterface {

	Client c;

	public SmithingInterface(Client c) {
		this.c = c;
	}

	public void showSmithInterface(int itemId) {
		if (itemId == 2349 && c.tutorialProgress == 20) {
			c.getActionSender().chatbox(6180);
			c.getDialogueHandler().chatboxText(c, "Now you have the Smithing menu open, you will see a list of all", "the things you can make. Only the dagger can be made at your", "skill level, this is shown by the white text under it. You'll need", "to select the dagger to continue.", "Smithing a dagger");
			c.getActionSender().chatbox(6179);
			makeBronzeInterface(c);
			c.isSmithing = true;
		} else if (itemId == 2349 && c.tutorialProgress > 20) {
			makeBronzeInterface(c);
		} else if (itemId == 2351) {
			makeIronInterface(c);
		} else if (itemId == 2353) {
			makeSteelInterface(c);
		} else if (itemId == 2359) {
			makeMithInterface(c);
		} else if (itemId == 2361) {
			makeAddyInterface(c);
		} else if (itemId == 2363) {
			makeRuneInterface(c);
		}
	}
	
	private void makeRuneInterface(Client c) {
		String fiveb = GetForBars(2363, 5, c);
		String threeb = GetForBars(2363, 3, c);
		String twob = GetForBars(2363, 2, c);
		String oneb = GetForBars(2363, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(88, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(99, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(99, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(99, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(97, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(96, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(95, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(94, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(93, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(92, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(92, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(91, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(90, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(90, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(89, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(89, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(89, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(88, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(87, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(85, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(86, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame34(1213, 0, 1119, 1); // dagger
		c.getPlayerAssistant().sendFrame34(1359, 0, 1120, 1); // axe
		c.getPlayerAssistant().sendFrame34(1113, 0, 1121, 1); // chain body
		c.getPlayerAssistant().sendFrame34(1147, 0, 1122, 1); // med helm
		c.getPlayerAssistant().sendFrame34(824, 0, 1123, 10); // Dart Tips
		c.getPlayerAssistant().sendFrame34(1289, 1, 1119, 1); // s-sword
		c.getPlayerAssistant().sendFrame34(1432, 1, 1120, 1); // mace
		c.getPlayerAssistant().sendFrame34(1079, 1, 1121, 1); // platelegs
		c.getPlayerAssistant().sendFrame34(1163, 1, 1122, 1); // full helm
		c.getPlayerAssistant().sendFrame34(44, 1, 1123, 15); // arrowtips
		c.getPlayerAssistant().sendFrame34(1333, 2, 1119, 1); // scimmy
		c.getPlayerAssistant().sendFrame34(1347, 2, 1120, 1); // warhammer
		c.getPlayerAssistant().sendFrame34(1093, 2, 1121, 1); // plateskirt
		c.getPlayerAssistant().sendFrame34(1185, 2, 1122, 1); // Sq. Shield
		c.getPlayerAssistant().sendFrame34(868, 2, 1123, 5); // throwing-knives

		c.getPlayerAssistant().sendFrame34(1303, 3, 1119, 1); // longsword
		c.getPlayerAssistant().sendFrame34(1373, 3, 1120, 1); // battleaxe
		c.getPlayerAssistant().sendFrame34(1127, 3, 1121, 1); // platebody
		c.getPlayerAssistant().sendFrame34(1201, 3, 1122, 1); // kiteshield
		c.getPlayerAssistant().sendFrame34(1319, 4, 1119, 1); // 2h sword
		c.getPlayerAssistant().sendFrame34(3101, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame34(4824, 4, 1122, 15); // nails
		c.getPlayerAssistant().sendFrame34(-1, 3, 1123, 1);
		c.getPlayerAssistant().sendFrame126("", 1135);
		c.getPlayerAssistant().sendFrame126("", 1134);
		c.getPlayerAssistant().sendFrame126("", 11461);
		c.getPlayerAssistant().sendFrame126("", 11459);
		c.getPlayerAssistant().sendFrame126("", 1132);
		c.getPlayerAssistant().sendFrame126("", 1096);
		c.getPlayerAssistant().showInterface(994);
	}

	private void makeAddyInterface(Client c) {
		String fiveb = GetForBars(2361, 5, c);
		String threeb = GetForBars(2361, 3, c);
		String twob = GetForBars(2361, 2, c);
		String oneb = GetForBars(2361, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(88, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(86, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(86, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(84, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(82, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(81, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(80, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(79, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(78, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(77, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(77, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(76, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(75, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(75, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(74, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(74, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(74, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(73, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(72, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(70, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(71, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame34(1211, 0, 1119, 1); // dagger
		c.getPlayerAssistant().sendFrame34(1357, 0, 1120, 1); // axe
		c.getPlayerAssistant().sendFrame34(1111, 0, 1121, 1); // chain body
		c.getPlayerAssistant().sendFrame34(1145, 0, 1122, 1); // med helm
		c.getPlayerAssistant().sendFrame34(823, 0, 1123, 10); // Dart Tips
		c.getPlayerAssistant().sendFrame34(1287, 1, 1119, 1); // s-sword
		c.getPlayerAssistant().sendFrame34(1430, 1, 1120, 1); // mace
		c.getPlayerAssistant().sendFrame34(1073, 1, 1121, 1); // platelegs
		c.getPlayerAssistant().sendFrame34(1161, 1, 1122, 1); // full helm
		c.getPlayerAssistant().sendFrame34(43, 1, 1123, 15); // arrowtips
		c.getPlayerAssistant().sendFrame34(1331, 2, 1119, 1); // scimmy
		c.getPlayerAssistant().sendFrame34(1345, 2, 1120, 1); // warhammer
		c.getPlayerAssistant().sendFrame34(1091, 2, 1121, 1); // plateskirt
		c.getPlayerAssistant().sendFrame34(1183, 2, 1122, 1); // Sq. Shield
		c.getPlayerAssistant().sendFrame34(867, 2, 1123, 5); // throwing-knives
		c.getPlayerAssistant().sendFrame34(1301, 3, 1119, 1); // longsword
		c.getPlayerAssistant().sendFrame34(1371, 3, 1120, 1); // battleaxe
		c.getPlayerAssistant().sendFrame34(1123, 3, 1121, 1); // platebody
		c.getPlayerAssistant().sendFrame34(1199, 3, 1122, 1); // kiteshield
		c.getPlayerAssistant().sendFrame34(1317, 4, 1119, 1); // 2h sword
		c.getPlayerAssistant().sendFrame34(3100, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame34(4823, 4, 1122, 15); // nails
		c.getPlayerAssistant().sendFrame34(-1, 3, 1123, 1);
		c.getPlayerAssistant().sendFrame126("", 1135);
		c.getPlayerAssistant().sendFrame126("", 1134);
		c.getPlayerAssistant().sendFrame126("", 11461);
		c.getPlayerAssistant().sendFrame126("", 11459);
		c.getPlayerAssistant().sendFrame126("", 1132);
		c.getPlayerAssistant().sendFrame126("", 1096);
		c.getPlayerAssistant().showInterface(994);
	}

	private void makeMithInterface(Client c) {
		String fiveb = GetForBars(2359, 5, c);
		String threeb = GetForBars(2359, 3, c);
		String twob = GetForBars(2359, 2, c);
		String oneb = GetForBars(2359, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(68, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(66, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(66, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(64, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(62, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(61, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(60, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(59, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(58, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(57, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(57, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(56, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(55, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(55, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(54, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(54, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(54, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(53, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(52, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(50, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(51, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame34(1209, 0, 1119, 1); // dagger
		c.getPlayerAssistant().sendFrame34(1355, 0, 1120, 1); // axe
		c.getPlayerAssistant().sendFrame34(1109, 0, 1121, 1); // chain body
		c.getPlayerAssistant().sendFrame34(1143, 0, 1122, 1); // med helm
		c.getPlayerAssistant().sendFrame34(822, 0, 1123, 10); // Dart Tips
		c.getPlayerAssistant().sendFrame34(1285, 1, 1119, 1); // s-sword
		c.getPlayerAssistant().sendFrame34(1428, 1, 1120, 1); // mace
		c.getPlayerAssistant().sendFrame34(1071, 1, 1121, 1); // platelegs
		c.getPlayerAssistant().sendFrame34(1159, 1, 1122, 1); // full helm
		c.getPlayerAssistant().sendFrame34(42, 1, 1123, 15); // arrowtips
		c.getPlayerAssistant().sendFrame34(1329, 2, 1119, 1); // scimmy
		c.getPlayerAssistant().sendFrame34(1343, 2, 1120, 1); // warhammer
		c.getPlayerAssistant().sendFrame34(1085, 2, 1121, 1); // plateskirt
		c.getPlayerAssistant().sendFrame34(1181, 2, 1122, 1); // Sq. Shield
		c.getPlayerAssistant().sendFrame34(866, 2, 1123, 5); // throwing-knives
		c.getPlayerAssistant().sendFrame34(1299, 3, 1119, 1); // longsword
		c.getPlayerAssistant().sendFrame34(1369, 3, 1120, 1); // battleaxe
		c.getPlayerAssistant().sendFrame34(1121, 3, 1121, 1); // platebody
		c.getPlayerAssistant().sendFrame34(1197, 3, 1122, 1); // kiteshield
		c.getPlayerAssistant().sendFrame34(1315, 4, 1119, 1); // 2h sword
		c.getPlayerAssistant().sendFrame34(3099, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame34(4822, 4, 1122, 15); // nails
		c.getPlayerAssistant().sendFrame34(-1, 3, 1123, 1);
		c.getPlayerAssistant().sendFrame126("", 1135);
		c.getPlayerAssistant().sendFrame126("", 1134);
		c.getPlayerAssistant().sendFrame126("", 11461);
		c.getPlayerAssistant().sendFrame126("", 11459);
		c.getPlayerAssistant().sendFrame126("", 1132);
		c.getPlayerAssistant().sendFrame126("", 1096);
		c.getPlayerAssistant().showInterface(994);
	}

	private void makeSteelInterface(Client c) {
		String fiveb = GetForBars(2353, 5, c);
		String threeb = GetForBars(2353, 3, c);
		String twob = GetForBars(2353, 2, c);
		String oneb = GetForBars(2353, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1132);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1135);
		c.getPlayerAssistant().sendFrame126("", 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(48, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(46, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(46, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(44, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(42, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(41, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126("", 11461);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(40, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(39, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(38, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(37, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(37, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(36, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(35, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(35, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(34, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(34, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(34, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(33, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(32, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(30, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(31, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(35, c) + "Cannon Ball" + GetForlvl(35, c), 1096);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(36, c) + "Studs" + GetForlvl(36, c), 1134);
		c.getPlayerAssistant().sendFrame34(1207, 0, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1353, 0, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1105, 0, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1141, 0, 1122, 1);
		c.getPlayerAssistant().sendFrame34(821, 0, 1123, 10);
		c.getPlayerAssistant().sendFrame34(1281, 1, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1424, 1, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1069, 1, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1157, 1, 1122, 1);
		c.getPlayerAssistant().sendFrame34(41, 1, 1123, 15);
		c.getPlayerAssistant().sendFrame34(1325, 2, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1339, 2, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1083, 2, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1177, 2, 1122, 1);
		c.getPlayerAssistant().sendFrame34(865, 2, 1123, 5);
		c.getPlayerAssistant().sendFrame34(1295, 3, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1365, 3, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1119, 3, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1193, 3, 1122, 1);
		c.getPlayerAssistant().sendFrame34(1311, 4, 1119, 1);
		c.getPlayerAssistant().sendFrame34(3097, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame34(1539, 4, 1122, 15);
		c.getPlayerAssistant().sendFrame34(2, 3, 1123, 4);
		c.getPlayerAssistant().sendFrame34(2370, 4, 1123, 1);
		c.getPlayerAssistant().showInterface(994);
	}

	private void makeIronInterface(Client c) {
		String fiveb = GetForBars(2351, 5, c);
		String threeb = GetForBars(2351, 3, c);
		String twob = GetForBars(2351, 2, c);
		String oneb = GetForBars(2351, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(33, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(31, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(31, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(29, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(27, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Oil Lantern Frame" + GetForlvl(11, c),
				11461);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(25, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(24, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(23, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(22, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(21, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(21, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(20, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(20, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(19, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(19, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(19, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(18, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(17, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(15, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(16, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame34(1203, 0, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1349, 0, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1101, 0, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1137, 0, 1122, 1);
		c.getPlayerAssistant().sendFrame34(820, 0, 1123, 10);
		c.getPlayerAssistant().sendFrame34(1279, 1, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1420, 1, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1067, 1, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1153, 1, 1122, 1);
		c.getPlayerAssistant().sendFrame34(40, 1, 1123, 15);
		c.getPlayerAssistant().sendFrame34(1323, 2, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1335, 2, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1081, 2, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1175, 2, 1122, 1);
		c.getPlayerAssistant().sendFrame34(863, 2, 1123, 5);
		c.getPlayerAssistant().sendFrame34(1293, 3, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1363, 3, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1115, 3, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1191, 3, 1122, 1);
		c.getPlayerAssistant().sendFrame34(1309, 4, 1119, 1);
		c.getPlayerAssistant().sendFrame34(4820, 4, 1122, 15);
		c.getPlayerAssistant().sendFrame34(4540, 4, 1121, 1);
		c.getPlayerAssistant().sendFrame34(3096, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame126("", 1135);
		c.getPlayerAssistant().sendFrame126("", 1134);
		c.getPlayerAssistant().sendFrame126("", 1132);
		c.getPlayerAssistant().sendFrame126("", 1096);
		c.getPlayerAssistant().showInterface(994);
	}

	private void makeBronzeInterface(Client c) {
		String fiveb = GetForBars(2349, 5, c);
		String threeb = GetForBars(2349, 3, c);
		String twob = GetForBars(2349, 2, c);
		String oneb = GetForBars(2349, 1, c);
		c.getPlayerAssistant().sendFrame126(fiveb + "5 Bars" + fiveb, 1112);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1109);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1110);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1118);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1111);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1095);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1115);
		c.getPlayerAssistant().sendFrame126(threeb + "3 Bars" + threeb, 1090);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1113);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1116);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1114);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 1089);
		c.getPlayerAssistant().sendFrame126(twob + "2 Bars" + twob, 8428);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1124);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1125);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1126);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1127);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1128);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1129);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1130);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 1131);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 13357);
		c.getPlayerAssistant().sendFrame126(oneb + "1 Bar" + oneb, 11459);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(18, c) + "Plate Body" + GetForlvl(18, c), 1101);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(16, c) + "Plate Legs" + GetForlvl(16, c), 1099);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(16, c) + "Plate Skirt" + GetForlvl(16, c), 1100);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(14, c) + "2 Hand Sword" + GetForlvl(14, c), 1088);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(26, c) + "Claws" + GetForlvl(11, c), 8429); // claws
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(12, c) + "Kite Shield" + GetForlvl(12, c), 1105);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(11, c) + "Chain Body" + GetForlvl(11, c), 1098);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(10, c) + "Battle Axe" + GetForlvl(10, c), 1092);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(9, c) + "Warhammer" + GetForlvl(9, c), 1083);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(8, c) + "Square Shield" + GetForlvl(8, c), 1104);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(7, c) + "Full Helm" + GetForlvl(7, c), 1103);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(7, c) + "Throwing Knives" + GetForlvl(7, c), 1106);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(6, c) + "Long Sword" + GetForlvl(6, c), 1086);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(5, c) + "Scimitar" + GetForlvl(5, c), 1087);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(5, c) + "Arrowtips" + GetForlvl(5, c), 1108);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(4, c) + "Sword" + GetForlvl(4, c), 1085);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(4, c) + "Dart Tips" + GetForlvl(4, c), 1107);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(4, c) + "Nails" + GetForlvl(4, c), 13358);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(3, c) + "Medium Helm" + GetForlvl(3, c), 1102);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(2, c) + "Mace" + GetForlvl(2, c), 1093);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(1, c) + "Dagger" + GetForlvl(1, c), 1094);
		c.getPlayerAssistant().sendFrame126(
				GetForlvl(1, c) + "Axe" + GetForlvl(1, c), 1091);
		c.getPlayerAssistant().sendFrame34(1205, 0, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1351, 0, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1103, 0, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1139, 0, 1122, 1);
		c.getPlayerAssistant().sendFrame34(819, 0, 1123, 10);
		c.getPlayerAssistant().sendFrame34(1277, 1, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1422, 1, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1075, 1, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1155, 1, 1122, 1);
		c.getPlayerAssistant().sendFrame34(39, 1, 1123, 15);
		c.getPlayerAssistant().sendFrame34(1321, 2, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1337, 2, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1087, 2, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1173, 2, 1122, 1);
		c.getPlayerAssistant().sendFrame34(864, 2, 1123, 5);
		c.getPlayerAssistant().sendFrame34(1291, 3, 1119, 1);
		c.getPlayerAssistant().sendFrame34(1375, 3, 1120, 1);
		c.getPlayerAssistant().sendFrame34(1117, 3, 1121, 1);
		c.getPlayerAssistant().sendFrame34(1189, 3, 1122, 1);
		c.getPlayerAssistant().sendFrame34(1307, 4, 1119, 1);
		c.getPlayerAssistant().sendFrame34(3095, 4, 1120, 1); // claws
		c.getPlayerAssistant().sendFrame34(4819, 4, 1122, 15);
		c.getPlayerAssistant().sendFrame34(-1, 3, 1123, 1);
		c.getPlayerAssistant().sendFrame126("", 1135);
		c.getPlayerAssistant().sendFrame126("", 1134);
		c.getPlayerAssistant().sendFrame126("", 11461);
		c.getPlayerAssistant().sendFrame126("", 11459);
		c.getPlayerAssistant().sendFrame126("", 1132);
		c.getPlayerAssistant().sendFrame126("", 1096);
		c.getPlayerAssistant().showInterface(994);
	}

	private String GetForlvl(int i, Client c) {
		if (c.playerLevel[13] >= i) {
			return "@whi@";
		}

		return "@bla@";
	}

	private String GetForBars(int i, int j, Client c) {
		if (c.getItemAssistant().playerHasItem(i, j)) {
			return "@gre@";
		}

		return "@red@";
	}

}
