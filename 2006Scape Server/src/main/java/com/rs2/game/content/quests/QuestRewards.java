package com.rs2.game.content.quests;

import com.rs2.Constants;
import com.rs2.game.players.Player;

/**
 * Quest Rewards
 * @author Andrew (Mr Extremez)
 */

public class QuestRewards {

	public static String QUEST_NAME;

	public static void questReward(Player player, String questName, String Line1, String Line2, String Line3, String Line4, String Line5, String Line6, int itemID) {
		player.getPacketSender().sendString("You have completed " + questName + "!", 12144);
		player.getPacketSender().sendString("" + player.questPoints, 12147);
		player.getPacketSender().sendString(Line1, 12150);
		player.getPacketSender().sendString(Line2, 12151);
		player.getPacketSender().sendString(Line3, 12152);
		player.getPacketSender().sendString(Line4, 12153);
		player.getPacketSender().sendString(Line5, 12154);
		player.getPacketSender().sendString(Line6, 12155);
		if (itemID > 0) {
			player.getPacketSender().sendFrame246(12145, 250, itemID);
		}
		player.getPacketSender().showInterface(12140);
		player.getPacketSender().sendMessage("You completed " + questName + "!");
		QuestAssistant.sendStages(player);
		player.getPacketSender().sendQuickSong(93, 0);
	}
	
	public static void knightsReward(Player player) {
		questReward(player, "Knight's Sword Quest", "1 Quest Point", "12,725 Smithing XP", "", "", "", "", 0);
		QUEST_NAME = "The Knight's Sword";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7346);
		player.getPlayerAssistant().addSkillXP(12725, Constants.SMITHING);
		player.questPoints ++;
		player.knightS = 9;
	}

	public static void gertFinish(Player player) {
		questReward(player, "Gertrude's Cat", "1 Quest Point", "1,525 Cooking XP", "A kitten!", "Ability to raise cats", "A chocolate cake", "A bowl of stew", 1897);
		QUEST_NAME = "Gertrude's Cat";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7360);
		player.getItemAssistant().addOrDropItem(1897, 1);
		player.getItemAssistant().addOrDropItem(2003, 1);
		player.getItemAssistant().addOrDropItem(1560, 1);
		player.getPlayerAssistant().addSkillXP(1525, Constants.COOKING);
		player.questPoints++;
		player.gertCat = 7;
	}

	public static void pirateFinish(Player c) {
		questReward(c, "Pirate's Treasure", "2 Quest Points", "One-Eyed Hector's Treasure", "", "", "", "", 2714);
		QUEST_NAME = "Pirate's Treasure";
		c.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7341);
		c.getItemAssistant().addOrDropItem(2714, 1);
		c.questPoints += 2;
		c.pirateTreasure = 6;
	}

	public static void witchFinish(Player client) {
		questReward(client, "Witch's Potion", "1 Quest Point", "325 Magic XP", "", "", "", "", 325);
		QUEST_NAME = "Witch's Potion";
		client.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7348);
		client.getPlayerAssistant().addSkillXP(325, Constants.MAGIC);
		client.questPoints++;
		client.witchspot = 3;
	}

	public static void julietFinish(Player player) {
		questReward(player, "Romeo and Juliet", "5 Quest Points", "", "", "", "", "", 0);
		QUEST_NAME = "Romeo and Juliet";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7343);
		player.questPoints += 5;
		player.romeojuliet = 9;
	}

	public static void restFinish(Player client) {
		questReward(client, "Restless Ghost", "1 Quest Point", "125 Prayer XP", "", "", "", "", 0);
		QUEST_NAME = "Restless Ghost";
		client.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7337);
		client.getPlayerAssistant().addSkillXP(125, Constants.PRAYER);
		client.questPoints++;
		client.restGhost = 5;
	}

	public static void vampFinish(Player player) {
		questReward(player, "Vampyre Slayer", "3 Quest Points", "4,825 Attack XP", "", "", "", "", 0);
		QUEST_NAME = "Vampyre Slayer";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7347);
		player.getPlayerAssistant().addSkillXP(4825, Constants.ATTACK);
		player.questPoints += 3;
		player.vampSlayer = 5;
	}

	public static void runeFinish(Player player) {
		questReward(player, "Rune Mysteries", "1 Quest Point", "Air Talisman", "", "", "", "", 1438);
		QUEST_NAME = "Rune Mysteries";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7335);
		player.getItemAssistant().addOrDropItem(1438, 1);
		player.questPoints++;
		player.runeMist = 4;
	}

	public static void sheepFinish(Player player) {
		questReward(player, "Sheep Shearer", "1 Quest Point", "150 Crafting Exp", "60 Coins", "", "", "", 995);
		QUEST_NAME = "Sheep Shearer";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7344);
		player.getItemAssistant().addOrDropItem(995, 60);
		player.getPlayerAssistant().addSkillXP(150, Constants.CRAFTING);
		player.questPoints++;
		player.sheepShear = 2;
	}

	public static void doricFinish(Player player) {
		questReward(player, "Doric's Quest", "1 Quest Point", "1,300 Mining XP", "180 Coins", "", "", "", 995);
		QUEST_NAME = "Doric's Quest";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7336);
		player.getItemAssistant().addOrDropItem(995, 180);
		player.getPlayerAssistant().addSkillXP(1300, Constants.MINING);
		player.questPoints++;
		player.doricQuest = 3;
	}

	public static void impFinish(Player player) {
		questReward(player, "Imp Catcher", "1 Quest Point", "875 Magic XP", "Amulet of Accuracy", "", "", "", 1478);
		QUEST_NAME = "Imp Catcher";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7340);
		player.getItemAssistant().addOrDropItem(1478, 1);
		player.getPlayerAssistant().addSkillXP(875, Constants.MAGIC);
		player.questPoints++;
		player.impsC = 2;
	}

	public static void cookReward(Player player) {
		questReward(player, "Cook's Assistant", "1 Quest Point", "500 Coins", "300 Cooking XP", "", "", "", 326);
		QUEST_NAME = "Cook's Assistant";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7333);
		player.getItemAssistant().addOrDropItem(995, 500);
		player.getPlayerAssistant().addSkillXP(300, Constants.COOKING);
		player.questPoints++;
		player.cookAss = 3;
	}

	public static void blackKnightReward(Player player) {
		questReward(player, "Black Knights' Fortress", "3 Quest Points", "2,500 Coins", "", "", "", "", 0);
		QUEST_NAME = "Black Knights' Fortress";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7332);
		player.getItemAssistant().addOrDropItem(995, 2500);
		player.questPoints += 3;
		player.blackKnight = 3;
	}

	public static void shieldArravReward(Player player) {
		questReward(player, "Shield of Arrav", "1 Quest Point", "1,200 Coins", "", "", "", "", 767);
		QUEST_NAME = "Shield of Arrav";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7345);
		player.getItemAssistant().addOrDropItem(995, 1200);
		player.questPoints++;
		player.shieldArrav = 8;
	}
	
	public static void lostCityReward(Player player) {
		questReward(player, "Lost City", "3 Quest Points", "Access to Zanaris", "", "", "", "", 0);
		QUEST_NAME = "Lost City";
		player.getPacketSender().sendString("@gre@" + QUEST_NAME + "", 7367);
		player.questPoints += 3;
		player.lostCity = 3;
	}
}
