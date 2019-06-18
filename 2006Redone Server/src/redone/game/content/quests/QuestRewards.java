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
		client.getPlayerAssistant().addNormalExperienceRate(12725, client.playerSmithing); 
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7346);
		client.knightS = 9;
		client.questPoints ++;
	}

	public static void gertFinish(Client client) {
		QuestReward(client, "Gertrude's Cat", "1 Quest Point", "1,525 Cooking XP", "A kitten!", "Ability to raise cats", "A choclate cake", "A bowl of stew", 1897);
		QUEST_NAME = "Gertrude's Cat";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7360);
		client.gertCat = 7;
		client.getItemAssistant().addItem(1897, 1);
		client.getItemAssistant().addItem(2003, 1);
		client.getItemAssistant().addItem(1560, 1);
		client.getPlayerAssistant().addNormalExperienceRate(1525, client.playerCooking);
	}

	public static void pirateFinish(Client client) {
		QuestReward(client, "Pirate's Treasure", "2 Quest Points", "One-Eyed Hector's Treasure", "", "", "", "", 2714);
		QUEST_NAME = "Pirate's Treasure";
		client.questPoints += 2;
		client.pirateTreasure = 6;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7341);
		client.getItemAssistant().addItem(2714, 1);
	}

	public static void witchFinish(Client client) {
		QuestReward(client, "Witch's Potion", "1 Quest Point", "325 Magic XP", "", "", "", "", 325);
		QUEST_NAME = "Witch's Potion";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7348);
		client.getPlayerAssistant().addNormalExperienceRate(325,
				client.playerMagic);
		client.witchspot = 3;
	}

	public static void julietFinish(Client client) {
		QuestReward(client, "Romeo and Juliet", "5 Quest Points", "", "", "", "", "", 0);
		QUEST_NAME = "Romeo and Juliet";
		client.questPoints += 5;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7343);
		client.romeojuliet = 9;
	}

	public static void restFinish(Client client) {
		QuestReward(client, "Restless Ghost", "1 Quest Point", "125 Prayer XP", "", "", "", "", 0);
		QUEST_NAME = "Restless Ghost";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7337);
		client.restGhost = 5;
		client.getPlayerAssistant().addNormalExperienceRate(125, client.playerPrayer);
	}

	public static void vampFinish(Client client) {
		QuestReward(client, "Vampyre Slayer", "3 Quest Points", "4,825 Attack XP", "", "", "", "", 0);
		QUEST_NAME = "Vampyre Slayer";
		client.questPoints += 3;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7347);
		client.vampSlayer = 5;
		client.getPlayerAssistant().addNormalExperienceRate(4825, client.playerAttack);
	}

	public static void runeFinish(Client client) {
		QuestReward(client, "Rune Mysteries", "1 Quest Points", "Air Talisman", "", "", "", "", 1438);
		QUEST_NAME = "Rune Mysteries";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7335);
		client.getItemAssistant().addItem(1438, 1);
		client.runeMist = 4;
	}

	public static void sheepFinish(Client client) {
		QuestReward(client, "Sheep Shearer", "1 Quest Point", "150 Crafting Exp", "60 Coins", "", "", "", 995);
		QUEST_NAME = "Sheep Shearer";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7344);
		client.sheepShear = 2;
		client.getItemAssistant().addItem(995, 60);
		client.getPlayerAssistant().addNormalExperienceRate(150, client.playerCrafting);
	}

	public static void doricFinish(Client client) {
		QuestReward(client, "Doric's Quest", "1 Quest Point", "1,300 Mining XP", "180 Coins", "", "", "", 995);
		QUEST_NAME = "Doric's Quest";
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7336);
		client.doricQuest = 3;
		client.getPlayerAssistant().addNormalExperienceRate(1300, client.playerMining);
		client.getItemAssistant().addItem(995, 180);
	}

	public static void impFinish(Client client) {
		QuestReward(client, "Imp Catcher", "1 Quest Point", "875 Magic XP", "Amulet of Accuracy", "", "", "", 1478);
		QUEST_NAME = "Imp Catcher";
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7340);
		client.questPoints++;
		client.impsC = 2;
		client.getPlayerAssistant().addNormalExperienceRate(875, client.playerMagic);
		client.getItemAssistant().addItem(1478, 1);
	}

	public static void cookReward(Client client) {
		QuestReward(client, "Cook's Assistant", "1 Quest Point", "500 Coins", "300 Cooking XP", "", "", "", 326);
		QUEST_NAME = "Cook's Assistant";
		client.getItemAssistant().addItem(995, 500);
		client.getPlayerAssistant().addNormalExperienceRate(300, client.playerCooking);
		client.questPoints++;
		client.getPlayerAssistant().sendFrame126("@gre@" + QUEST_NAME + "", 7333);
		client.cookAss = 3;
	}
}
