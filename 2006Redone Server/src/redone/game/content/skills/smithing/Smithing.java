package redone.game.content.skills.smithing;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * Cleaned by Andrew
 * @author Andrew
 */

public class Smithing {

	private int addItem, XP, removeItem, removeAmount, makeTimes;

	public void readInput(int levelReq, String type, Client c, int amountToMake) {
		if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Bronze")) {
			CheckBronze(c, levelReq, amountToMake, type);
		} else if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Iron")) {
			CheckIron(c, levelReq, amountToMake, type);
		} else if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Steel")) {
			CheckSteel(c, levelReq, amountToMake, type);
		} else if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Mith")) {
			CheckMith(c, levelReq, amountToMake, type);
		} else if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Adam") || ItemAssistant.getItemName(Integer.parseInt(type)).contains("Addy")) {
			CheckAddy(c, levelReq, amountToMake, type);
		} else if (ItemAssistant.getItemName(Integer.parseInt(type)).contains("Rune") || ItemAssistant.getItemName(Integer.parseInt(type)).contains("Runite")) {
			CheckRune(c, levelReq, amountToMake, type);
		}
		if (c.playerRights > 1) {
			c.getActionSender().sendMessage("You made item id: " + type + ".");
		}
	}

	private void CheckBronze(Client c, int levelReq, int amountToMake, String type) {
		if (c.tutorialProgress == 20 && !type.equalsIgnoreCase("1205")) {
			c.getDialogueHandler().sendStatement("You can only make a bronze dagger on this step.");
			c.nextChat = 0;
			return;
		}
		if (type.equalsIgnoreCase("1351") && levelReq >= 1) {
			XP = 13;
			addItem = 1351;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1205") && levelReq >= 1 && c.tutorialProgress > 20) {
			XP = 13;
			addItem = 1205;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1205") && levelReq >= 1 && c.tutorialProgress == 20) {
			XP = 13;
			addItem = 1205;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
			c.getDialogueHandler().sendDialogues(3066, -1);
		} else if (type.equals("1422") && levelReq >= 2) {
			XP = 13;
			addItem = 1422;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1139") && levelReq >= 3) {
			XP = 13;
			addItem = 1139;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("819") && levelReq >= 4) {
			XP = 13;
			addItem = 819;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1277") && levelReq >= 4) {
			XP = 13;
			addItem = 1277;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("4819") && levelReq >= 4) {
			XP = 13;
			addItem = 4819;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("39") && levelReq >= 5) {
			XP = 13;
			addItem = 39;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1321") && levelReq >= 5) {
			XP = 25;
			addItem = 1321;
			removeItem = 2349;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1291") && levelReq >= 6) {
			XP = 25;
			addItem = 1291;
			removeItem = 2349;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("864") && levelReq >= 7) {
			XP = 25;
			addItem = 864;
			removeItem = 2349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1155") && levelReq >= 7) {
			XP = 25;
			addItem = 1155;
			removeItem = 2349;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1173") && levelReq >= 8) {
			XP = 25;
			addItem = 1173;
			removeItem = 2349;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("3095") && levelReq >= 8) { // claws
			XP = 25;
			addItem = 3095;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1337") && levelReq >= 9) {
			XP = 38;
			addItem = 1337;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1375") && levelReq >= 10) {
			XP = 38;
			addItem = 1375;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1103") && levelReq >= 11) {
			XP = 38;
			addItem = 1103;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1189") && levelReq >= 12) {
			XP = 38;
			addItem = 1189;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1307") && levelReq >= 14) {
			XP = 38;
			addItem = 1307;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1075") && levelReq >= 16) {
			XP = 38;
			addItem = 1075;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1087") && levelReq >= 16) {
			XP = 38;
			addItem = 1087;
			removeItem = 2349;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1117") && levelReq >= 18) {
			XP = 63;
			addItem = 1117;
			removeItem = 2349;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}
		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);
	}

	private void CheckIron(Client c, int levelReq, int amountToMake, String type) {
		removeItem = 2351;
		if (type.equalsIgnoreCase("1349") && levelReq >= 16) { // Axe
			XP = 25;
			addItem = 1349;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1203") && levelReq >= 15) { // Dagger
			XP = 25;
			addItem = 1203;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1420") && levelReq >= 17) { // Mace
			XP = 25;
			addItem = 1420;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1137") && levelReq >= 18) { // Med helm
			XP = 25;
			addItem = 1137;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("820") && levelReq >= 19) { // Dart tips
			XP = 25;
			addItem = 820;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1279") && levelReq >= 19) { // Sword (s)
			XP = 25;
			addItem = 1279;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("4820") && levelReq >= 19) { // Nails
			XP = 25;
			addItem = 4820;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("40") && levelReq >= 20) { // Arrow tips
			XP = 25;
			addItem = 40;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1323") && levelReq >= 20) {// Scim
			XP = 50;
			addItem = 1323;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1293") && levelReq >= 21) { // Longsword
			XP = 50;
			addItem = 1293;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("863") && levelReq >= 22) { // Knives
			XP = 25;
			addItem = 863;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1153") && levelReq >= 22) { // Full Helm
			XP = 50;
			addItem = 1153;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1175") && levelReq >= 23) { // Square shield
			XP = 50;
			addItem = 1175;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1335") && levelReq >= 24) { // Warhammer
			XP = 38;
			addItem = 1335;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1363") && levelReq >= 25) { // Battle axe
			XP = 75;
			addItem = 1363;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1101") && levelReq >= 26) { // Chain
			XP = 75;
			addItem = 1101;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("4540") && levelReq >= 26) { // lantern
			XP = 25;
			addItem = 4540;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1191") && levelReq >= 27) { // Kite
			XP = 75;
			addItem = 1191;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("3096") && levelReq >= 28) { // claws
			XP = 50;
			addItem = 3096;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1309") && levelReq >= 29) { // 2h Sword
			XP = 75;
			addItem = 1309;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1067") && levelReq >= 31) { // Platelegs
			XP = 75;
			addItem = 1067;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1081") && levelReq >= 31) { // PlateSkirt
			XP = 75;
			addItem = 1081;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1115") && levelReq >= 33) { // Platebody
			XP = 125;
			addItem = 1115;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}

		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);

	}

	private void CheckSteel(Client c, int levelReq, int amountToMake, String type) {
		removeItem = 2353;
		if (type.equalsIgnoreCase("1353") && levelReq >= 31) { // Axe
			XP = 38;
			addItem = 1353;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1207") && levelReq >= 30) { // Dagger
			XP = 50;
			addItem = 1207;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1424") && levelReq >= 32) { // Mace
			XP = 50;
			addItem = 1424;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1141") && levelReq >= 33) { // Med helm
			XP = 50;
			addItem = 1141;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("821") && levelReq >= 34) { // Dart tips
			XP = 50;
			addItem = 821;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1281") && levelReq >= 34) { // Sword (s)
			XP = 50;
			addItem = 1281;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1539") && levelReq >= 34) { // Nails
			XP = 50;
			addItem = 1539;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("41") && levelReq >= 35) { // Arrow tips
			XP = 50;
			addItem = 41;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1325") && levelReq >= 35) {// Scim
			XP = 75;
			addItem = 1325;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("2370") && levelReq >= 36) {// Studs
			XP = 37;
			addItem = 2370;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1295") && levelReq >= 36) { // Longsword
			XP = 75;
			addItem = 1295;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("865") && levelReq >= 37) { // Knives
			XP = 50;
			addItem = 865;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1157") && levelReq >= 37) { // Full Helm
			XP = 75;
			addItem = 1157;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1177") && levelReq >= 38) { // Square shield
			XP = 75;
			addItem = 1177;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1339") && levelReq >= 39) { // Warhammer
			XP = 113;
			addItem = 1339;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1365") && levelReq >= 40) { // Battle axe
			XP = 113;
			addItem = 1365;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1105") && levelReq >= 41) { // Chain
			XP = 113;
			addItem = 1105;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1193") && levelReq >= 42) { // Kite
			XP = 113;
			addItem = 1193;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("3097") && levelReq >= 43) { // claws
			XP = 75;
			addItem = 3097;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1311") && levelReq >= 44) { // 2h Sword
			XP = 113;
			addItem = 1311;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1069") && levelReq >= 46) { // Platelegs
			XP = 113;
			addItem = 1069;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1083") && levelReq >= 46) { // PlateSkirt
			XP = 113;
			addItem = 1083;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1119") && levelReq >= 48) { // Platebody
			XP = 188;
			addItem = 1119;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}
		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);
	}

	private void CheckMith(Client c, int levelReq, int amountToMake, String type) {
		removeItem = 2359;
		if (type.equalsIgnoreCase("1355") && levelReq >= 51) { // Axe
			XP = 50;
			addItem = 1355;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1209") && levelReq >= 50) { // Dagger
			XP = 50;
			addItem = 1209;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1428") && levelReq >= 52) { // Mace
			XP = 50;
			addItem = 1428;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1143") && levelReq >= 53) {// Med helm
			XP = 50;
			addItem = 1143;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("822") && levelReq >= 54) { // Dart tips
			XP = 50;
			addItem = 822;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1285") && levelReq >= 54) { // Sword (s)
			XP = 50;
			addItem = 1285;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("4822") && levelReq >= 54) { // Nails
			XP = 50;
			addItem = 4822;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("42") && levelReq >= 55) { // Arrow tips
			XP = 50;
			addItem = 42;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1329") && levelReq >= 55) {// Scim
			XP = 100;
			addItem = 1329;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1299") && levelReq >= 56) { // Longsword
			XP = 100;
			addItem = 1299;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("866") && levelReq >= 57) { // Knives
			XP = 50;
			addItem = 866;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1159") && levelReq >= 57) { // Full Helm
			XP = 100;
			addItem = 1159;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1181") && levelReq >= 58) { // Square shield
			XP = 100;
			addItem = 1181;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1343") && levelReq >= 59) { // Warhammer
			XP = 150;
			addItem = 1343;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1369") && levelReq >= 60) { // Battle axe
			XP = 150;
			addItem = 1369;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1109") && levelReq >= 61) { // Chain
			XP = 150;
			addItem = 1109;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1197") && levelReq >= 62) { // Kite
			XP = 150;
			addItem = 1197;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("3099") && levelReq >= 63) { // claws
			XP = 100;
			addItem = 3099;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1315") && levelReq >= 64) { // 2h Sword
			XP = 150;
			addItem = 1315;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1071") && levelReq >= 66) { // Platelegs
			XP = 150;
			addItem = 1071;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1085") && levelReq >= 66) { // PlateSkirt
			XP = 150;
			addItem = 1085;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1121") && levelReq >= 68) { // Platebody
			XP = 250;
			addItem = 1121;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}
		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);
	}

	private void CheckAddy(Client c, int levelReq, int amountToMake, String type) {
		removeItem = 2361;
		if (type.equalsIgnoreCase("1357") && levelReq >= 71) { // Axe
			XP = 63;
			addItem = 1357;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1211") && levelReq >= 70) { // Dagger
			XP = 63;
			addItem = 1211;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1430") && levelReq >= 72) { // Mace
			XP = 63;
			addItem = 1430;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1145") && levelReq >= 73) { // Med helm
			XP = 63;
			addItem = 1145;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("823") && levelReq >= 74) { // Dart tips
			XP = 63;
			addItem = 823;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1287") && levelReq >= 74) { // Sword (s)
			XP = 63;
			addItem = 1287;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("4823") && levelReq >= 74) { // Nails
			XP = 63;
			addItem = 4823;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("43") && levelReq >= 75) { // Arrow tips
			XP = 63;
			addItem = 43;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1331") && levelReq >= 75) {// Scim
			XP = 125;
			addItem = 1331;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1301") && levelReq >= 76) { // Longsword
			XP = 125;
			addItem = 1301;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("867") && levelReq >= 77) { // Knives
			XP = 63;
			addItem = 867;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1161") && levelReq >= 77) { // Full Helm
			XP = 125;
			addItem = 1161;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1183") && levelReq >= 78) { // Square shield
			XP = 125;
			addItem = 1183;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1345") && levelReq >= 79) { // Warhammer
			XP = 188;
			addItem = 1345;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1371") && levelReq >= 80) { // Battle axe
			XP = 188;
			addItem = 1371;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1111") && levelReq >= 81) { // Chain
			XP = 188;
			addItem = 1111;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1199") && levelReq >= 82) { // Kite
			XP = 188;
			addItem = 1199;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("3100") && levelReq >= 83) { // claws
			XP = 125;
			addItem = 3100;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1317") && levelReq >= 84) { // 2h Sword
			XP = 188;
			addItem = 1317;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1073") && levelReq >= 86) { // Platelegs
			XP = 188;
			addItem = 1073;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1091") && levelReq >= 86) { // PlateSkirt
			XP = 188;
			addItem = 1091;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1123") && levelReq >= 88) { // Platebody
			XP = 313;
			addItem = 1123;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}
		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);
	}

	private void CheckRune(Client c, int levelReq, int amountToMake, String type) {
		removeItem = 2363;
		if (type.equalsIgnoreCase("1359") && levelReq >= 86) { // Axe
			XP = 75;
			addItem = 1359;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equalsIgnoreCase("1213") && levelReq >= 85) { // Dagger
			XP = 75;
			addItem = 1213;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1432") && levelReq >= 87) { // Mace
			XP = 75;
			addItem = 1432;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1147") && levelReq >= 88) { // Med helm
			XP = 75;
			addItem = 1147;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("824") && levelReq >= 89) { // Dart tips
			XP = 75;
			addItem = 824;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1289") && levelReq >= 89) { // Sword (s)
			XP = 75;
			addItem = 1289;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("4824") && levelReq >= 89) { // Nails
			XP = 75;
			addItem = 4824;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("44") && levelReq >= 90) { // Arrow tips
			XP = 75;
			addItem = 44;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1333") && levelReq >= 90) {// Scim
			XP = 150;
			addItem = 1333;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1303") && levelReq >= 91) { // Longsword
			XP = 150;
			addItem = 1303;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("868") && levelReq >= 92) { // Knives
			XP = 75;
			addItem = 868;
			removeAmount = 1;
			makeTimes = amountToMake;
		} else if (type.equals("1163") && levelReq >= 92) { // Full Helm
			XP = 150;
			addItem = 1163;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1185") && levelReq >= 93) { // Square shield
			XP = 150;
			addItem = 1185;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1347") && levelReq >= 94) { // Warhammer
			XP = 225;
			addItem = 1347;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1373") && levelReq >= 95) { // Battle axe
			XP = 225;
			addItem = 1373;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1113") && levelReq >= 96) { // Chain
			XP = 225;
			addItem = 1113;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1201") && levelReq >= 97) { // Kite
			XP = 225;
			addItem = 1201;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1319") && levelReq >= 99) { // 2h Sword
			XP = 225;
			addItem = 1319;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1079") && levelReq >= 99) { // Platelegs
			XP = 225;
			addItem = 1079;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("3101") && levelReq >= 98) { // claws
			XP = 150;
			addItem = 3101;
			removeAmount = 2;
			makeTimes = amountToMake;
		} else if (type.equals("1093") && levelReq >= 99) { // PlateSkirt
			XP = 225;
			addItem = 1093;
			removeAmount = 3;
			makeTimes = amountToMake;
		} else if (type.equals("1127") && levelReq >= 99) { // Platebody
			XP = 313;
			addItem = 1127;
			removeAmount = 5;
			makeTimes = amountToMake;
		} else if (c.playerLevel[c.playerSmithing] < levelReq) {
			c.getActionSender().sendMessage(
					"You need " + levelReq + " smithing to do this!");
			return;
		}
		smithItem(c, addItem, removeItem, removeAmount, makeTimes, XP);
	}
	
	/*public boolean smithItem(final Client c, final int addItem, final int removeItem, final int removeItem2, int timesToMake, final int XP) {
		final int makeTimes = timesToMake;
		c.isSmithing = true;
		c.startAnimation(898);
		final String name = ItemAssistant.getItemName(addItem);
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
	            	if (!c.getItemAssistant().playerHasItem(removeItem, removeItem2)) {
	            		container.stop();
	            	}
	            	if (makeTimes == 0 || c.isSmithing == false) {
	            		container.stop();
	            	}
					c.getItemAssistant().deleteItem2(removeItem, removeItem2);
					c.getPlayerAssistant().addSkillXP(XP, c.playerSmithing);
					c.getPlayerAssistant().refreshSkill(c.playerSmithing);
					makeTimes--;
					c.getActionSender().sendSound(468, 100, 0);	
					if (ItemAssistant.getItemName(addItem).contains("bolt")) {
						c.getItemAssistant().addItem(addItem, 10);
					} else if (ItemAssistant.getItemName(addItem).contains("tip") && !ItemAssistant.getItemName(addItem).contains("dart tip")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("dart tip")) {
						c.getItemAssistant().addItem(addItem, 10);
					} else if (ItemAssistant.getItemName(addItem).contains("nail")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("arrow")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("knife")) {
						c.getItemAssistant().addItem(addItem, 5);
					} else if (ItemAssistant.getItemName(addItem).contains("cannon")) {
						c.getItemAssistant().addItem(addItem, 4);
					} else {
						c.getItemAssistant().addItem(addItem, 1);
					}	
	            }

				@Override
				public void stop() {
					// TODO Auto-generated method stub
				}	
		}, 1);
		if (makeTimes > 1 && c.getItemAssistant().playerHasItem(removeItem, removeItem2 * 2) && !name.contains("claws") && !name.contains("nails") && !name.contains("dart tip") && !name.contains("tip") && !name.contains("platelegs")) {
			c.getActionSender().sendMessage("You make some " + ItemAssistant.getItemName(addItem) + "s.");
		} else if (makeTimes > 1 && c.getItemAssistant().playerHasItem(removeItem, removeItem2 * 2) && name.contains("claws") || name.contains("nails") || name.contains("dart tip") || name.contains("tip") || name.contains("platelegs")) {
			c.getActionSender().sendMessage("You make some " + ItemAssistant.getItemName(addItem) + ".");
		} else {
			c.getActionSender().sendMessage("You hammer out a " + ItemAssistant.getItemName(addItem) + ".");
		}
		return true;
	}*/

	public boolean smithItem(Client c, int addItem, int removeItem,
			int removeItem2, int timesToMake, int XP) {
		int makeTimes = timesToMake;
		c.getPlayerAssistant().closeAllWindows();
		if (c.playerRights == 3) {
			c.getActionSender().sendMessage(
					"Your smithing is now set to true.");
		}
		c.isSmithing = true;
		String name = ItemAssistant.getItemName(addItem);
		if (c.getItemAssistant().playerHasItem(removeItem, removeItem2)) {
			c.startAnimation(898);
			if (makeTimes > 1 && c.getItemAssistant().playerHasItem(removeItem, removeItem2 * 2) && !name.contains("claws") && !name.contains("nails") && !name.contains("dart tip") && !name.contains("tip") && !name.contains("platelegs")) {
				c.getActionSender().sendMessage("You make some " + ItemAssistant.getItemName(addItem) + "s.");
			} else if (makeTimes > 1 && c.getItemAssistant().playerHasItem(removeItem, removeItem2 * 2) && name.contains("claws") || name.contains("nails") || name.contains("dart tip") || name.contains("tip") || name.contains("platelegs")) {
				c.getActionSender().sendMessage("You make some " + ItemAssistant.getItemName(addItem) + ".");
			} else {
				c.getActionSender().sendMessage("You hammer out a " + ItemAssistant.getItemName(addItem) + ".");
			}
			while (makeTimes > 0 && c.isSmithing == true) {
				if (c.getItemAssistant().playerHasItem(removeItem, removeItem2)) {
					c.getItemAssistant().deleteItem2(removeItem, removeItem2);
					if (ItemAssistant.getItemName(addItem).contains("bolt")) {
						c.getItemAssistant().addItem(addItem, 10);
					} else if (ItemAssistant.getItemName(addItem).contains("tip") && !ItemAssistant.getItemName(addItem).contains("dart tip")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("dart tip")) {
						c.getItemAssistant().addItem(addItem, 10);
					} else if (ItemAssistant.getItemName(addItem).contains("nail")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("arrow")) {
						c.getItemAssistant().addItem(addItem, 15);
					} else if (ItemAssistant.getItemName(addItem).contains("knife")) {
						c.getItemAssistant().addItem(addItem, 5);
					} else if (ItemAssistant.getItemName(addItem).contains("cannon")) {
						c.getItemAssistant().addItem(addItem, 4);
					} else {
						c.getItemAssistant().addItem(addItem, 1);
					}
					c.getPlayerAssistant().addSkillXP(XP, c.playerSmithing);
					c.getPlayerAssistant().refreshSkill(c.playerSmithing);
					makeTimes--;
					c.getActionSender().sendSound(468, 100, 0);
				} else {
					if (c.playerRights == 3) {
						c.getActionSender().sendMessage("Smithing set to false, ran out of bars.");
					}
					c.isSmithing = false;
					break;
				}
			}
		} else {
			c.getActionSender().sendMessage("You don't have enough bars to make this item!");
			if (c.playerRights == 3) {
				c.getActionSender().sendMessage("Smithing set to false, doesn't have enough bars.");
			}
			c.isSmithing = false;
			return false;
		}
		return true;
	}
}
