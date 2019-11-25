package redone.game.content.quests;

import redone.game.players.Client;

/**
 * Quest Rewards
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class QuestRewards {

	public static String QUEST_NAME;

	public static void QuestReward(Client client, String questName, String Line1, String Line2, String Line3, String Line4, String Line5, String Line6, int itemID) {
		client.getPlayerAssistant().sendFrame126("You have completed " + questName + "!", 12144);
		client.getPlayerAssistant().sendFrame126("" + client.questPoints, 12147);
		client.getPlayerAssistant().sendFrame126(Line1, 12150);
		client.getPlayerAssistant().sendFrame126(Line2, 12151);
		client.getPlayerAssistant().sendFrame126(Line3, 12152);
		client.getPlayerAssistant().sendFrame126(Line4, 12153);
		client.getPlayerAssistant().sendFrame126(Line5, 12154);
		client.getPlayerAssistant().sendFrame126(Line6, 12155);
		if (itemID > 0) {
			client.getPlayerAssistant().sendFrame246(12145, 250, itemID);
		}
		client.getPlayerAssistant().showInterface(12140);
		client.getActionSender().sendMessage("You completed " + questName + "!");
		QuestAssistant.sendStages(client);
		client.getActionSender().sendQuickSong(93, 0);
	}
	
	public static void knightsReward(Client client) {
		QuestReward(client, "Knight's Sword Quest", "1 Quest Point", "12,725 Smithing XP", "", "", "", "", 0);
		QUEST_NAME = "The Knight's Sword";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7346);
		client.getPlayerAssistant().addSkillXP(12725, client.playerSmithing);
		client.questPoints ++;
		client.knightS = 9;
	}

	public static void gertFinish(Client client) {
		QuestReward(client, "Gertrude's Cat", "1 Quest Point", "1,525 Cooking XP", "A kitten!", "Ability to raise cats", "A chocolate cake", "A bowl of stew", 1897);
		QUEST_NAME = "Gertrude's Cat";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7360);
		client.getItemAssistant().addItem(1897, 1);
		client.getItemAssistant().addItem(2003, 1);
		client.getItemAssistant().addItem(1560, 1);
		client.getPlayerAssistant().addSkillXP(1525, client.playerCooking);
		client.questPoints++;
		client.gertCat = 7;
	}

	public static void pirateFinish(Client client) {
		QuestReward(client, "Pirate's Treasure", "2 Quest Points", "One-Eyed Hector's Treasure", "", "", "", "", 2714);
		QUEST_NAME = "Pirate's Treasure";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7341);
		client.getItemAssistant().addItem(2714, 1);
		client.questPoints += 2;
		client.pirateTreasure = 6;
	}

	public static void witchFinish(Client client) {
		QuestReward(client, "Witch's Potion", "1 Quest Point", "325 Magic XP", "", "", "", "", 325);
		QUEST_NAME = "Witch's Potion";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7348);
		client.getPlayerAssistant().addSkillXP(325, client.playerMagic);
		client.questPoints++;
		client.witchspot = 3;
	}

	public static void julietFinish(Client client) {
		QuestReward(client, "Romeo and Juliet", "5 Quest Points", "", "", "", "", "", 0);
		QUEST_NAME = "Romeo and Juliet";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7343);
		client.questPoints += 5;
		client.romeojuliet = 9;
	}

	public static void restFinish(Client client) {
		QuestReward(client, "Restless Ghost", "1 Quest Point", "125 Prayer XP", "", "", "", "", 0);
		QUEST_NAME = "Restless Ghost";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7337);
		client.getPlayerAssistant().addSkillXP(125, client.playerPrayer);
		client.questPoints++;
		client.restGhost = 5;
	}

	public static void vampFinish(Client client) {
		QuestReward(client, "Vampyre Slayer", "3 Quest Points", "4,825 Attack XP", "", "", "", "", 0);
		QUEST_NAME = "Vampyre Slayer";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7347);
		client.getPlayerAssistant().addSkillXP(4825, client.playerAttack);
		client.questPoints += 3;
		client.vampSlayer = 5;
	}

	public static void runeFinish(Client client) {
		QuestReward(client, "Rune Mysteries", "1 Quest Point", "Air Talisman", "", "", "", "", 1438);
		QUEST_NAME = "Rune Mysteries";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7335);
		client.getItemAssistant().addItem(1438, 1);
		client.questPoints++;
		client.runeMist = 4;
	}

	public static void sheepFinish(Client client) {
		QuestReward(client, "Sheep Shearer", "1 Quest Point", "150 Crafting Exp", "60 Coins", "", "", "", 995);
		QUEST_NAME = "Sheep Shearer";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7344);
		client.getItemAssistant().addItem(995, 60);
		client.getPlayerAssistant().addSkillXP(150, client.playerCrafting);
		client.questPoints++;
		client.sheepShear = 2;
	}

	public static void doricFinish(Client client) {
		QuestReward(client, "Doric's Quest", "1 Quest Point", "1,300 Mining XP", "180 Coins", "", "", "", 995);
		QUEST_NAME = "Doric's Quest";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7336);
		client.getItemAssistant().addItem(995, 180);
		client.getPlayerAssistant().addSkillXP(1300, client.playerMining);
		client.questPoints++;
		client.doricQuest = 3;
	}

	public static void impFinish(Client client) {
		QuestReward(client, "Imp Catcher", "1 Quest Point", "875 Magic XP", "Amulet of Accuracy", "", "", "", 1478);
		QUEST_NAME = "Imp Catcher";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7340);
		client.getItemAssistant().addItem(1478, 1);
		client.getPlayerAssistant().addSkillXP(875, client.playerMagic);
		client.questPoints++;
		client.impsC = 2;
	}

	public static void cookReward(Client client) {
		QuestReward(client, "Cook's Assistant", "1 Quest Point", "500 Coins", "300 Cooking XP", "", "", "", 326);
		QUEST_NAME = "Cook's Assistant";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7333);
		client.getItemAssistant().addItem(995, 500);
		client.getPlayerAssistant().addSkillXP(300, client.playerCooking);
		client.questPoints++;
		client.cookAss = 3;
	}

	public static void blackKnightReward(Client client) {
		QuestReward(client, "Black Knights' Fortress", "3 Quest Points", "2,500 Coins", "", "", "", "", 0);
		QUEST_NAME = "Black Knights' Fortress";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7332);
		client.getItemAssistant().addItem(995, 2500);
		client.questPoints += 3;
		client.blackKnight = 3;
	}

	public static void shieldArravReward(Client client) {
		QuestReward(client, "Shield of Arrav", "1 Quest Point", "1,200 Coins", "", "", "", "", 767);
		QUEST_NAME = "Shield of Arrav";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7345);
		client.getItemAssistant().addItem(995, 1200);
		client.questPoints++;
		client.shieldArrav = 8;
	}
}
