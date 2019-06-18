package redone.game.dialogues;

import redone.Constants;
import redone.game.content.quests.QuestAssistant;
import redone.game.content.quests.QuestRewards;
import redone.game.content.randomevents.FreakyForester;
import redone.game.content.randomevents.RandomEventHandler;
import redone.game.content.skills.SkillHandler;
import redone.game.content.traveling.Sailing;
import redone.game.globalworldobjects.PassDoor;
import redone.game.items.ItemAssistant;
import redone.game.npcs.NpcHandler;
import redone.game.objects.impl.SpecialObjects;
import redone.game.players.Client;
import redone.game.players.PlayerAssistant;
import redone.game.shops.Shops.Shop;
import redone.util.Misc;

/**
 * Anims:
 * http://www.rune-server.org/runescape-development/rs2-server/tutorials/518991-pi-317-player-npc-facial-dialogue-expressions.html
 */

public class DialogueHandler {

	private final Client player;

	public DialogueHandler(Client client) {
		this.player = client;
	}

	public void sendDialogues(int dialogue, int npcId) {
		int MOLE_SKIN = player.getItemAssistant().getItemCount(7418), MOLE_CLAW = player.getItemAssistant().getItemCount(7416);
		player.talkingNpc = npcId;
		switch (dialogue) {
		case 0:
			player.talkingNpc = -1;
			player.getPlayerAssistant().removeAllWindows();
			player.nextChat = 0;
			break;

		case 1: // was 16
			sendOption2("I would like to reset my barrows brothers.",
					"I would like to fix all my barrows");
			player.dialogueAction = 8;
			break;

		case 2:
			if (player.canLeaveArea) {
				sendNpcChat2(
						"Just step through the glowing portal when you're ready",
						"to leave, and I'll ensure you get a nice reward.",
						player.talkingNpc, "Freaky Forester");
				player.getItemAssistant().deleteItem(6178, player.getItemAssistant().getItemSlot(6178), player.getItemAssistant().getItemAmount(6178));
			} else if (FreakyForester.hasKilledPheasant(player)
					&& player.getItemAssistant().playerHasItem(6178, 1)) {
				sendNpcChat1(
						"Thank you. I take that pheasant, you can leave now.",
						player.talkingNpc, "Freaky Forester");
				player.canLeaveArea = true;
				player.getItemAssistant().deleteItem(6178, player.getItemAssistant().getItemSlot(6178), player.getItemAssistant().getItemAmount(6178));
			} else {
				sendNpcChat2("Hello there mate. Can you please kill a "
						+ FreakyForester.getPheasant(player) + " and",
						"bring it back to me and I shall let you leave.",
						player.talkingNpc, "Freaky Forester");
			}
			player.nextChat = 0;
			break;// go to where they appear or whatever

		case 3:
			sendNpcChat1("Come back mate! You can't leave yet!",
					player.talkingNpc, "Freaky Forester");
			player.nextChat = 0;
			break;
			
		case 4:
			if (player.luthas == true && player.bananas >= 2) {
				player.getItemAssistant().addOrDropItem(995, 30);
				sendNpcChat1(
						"Thank you for your services you have been rewarded 30 coins.",
						player.talkingNpc, "Luthas");
				player.luthas = false;
				player.bananas = 0;
				player.nextChat = 0;
			} else {
				player.getPlayerAssistant().bananasCheck();
				player.nextChat = 0;
			}
			break;

		case 8:
			sendOption2("I would like to collect some banana's for you",
					"Never mind");
			player.dialogueAction = 92;
			break;

		case 9:
			sendPlayerChat1("I would like to collect some banana's for you.");
			player.luthas = true;
			player.bananas = 1;
			player.nextChat = 10;
			break;

		case 10:
			sendNpcChat1(
					"Please put 10 bannanas in a crate then I will reward you.",
					player.talkingNpc, "Luthas");
			player.nextChat = 4;
			break;

		case 5:
			sendPlayerChat1("Hello, how's it going?");
			player.nextChat = 6;
			break;
		case 6:
			sendNpcChat1("I'm fine, how are you?", player.talkingNpc, "Man");
			player.nextChat = 7;
			break;
		case 7:
			sendPlayerChat1("Very well thank you.");
			player.nextChat = 0;
			break;

		case 11:
			sendOption2("Here's 5 coins you tramp.", "Leave, me alone.");
			player.dialogueAction = 90;
			break;

		case 12:
			if (player.getItemAssistant().playerHasItem(995, 5)) {
				player.getItemAssistant().deleteItem2(995, 5);
				sendPlayerChat1("Here's 5 coins you tramp.");
				player.nextChat = 0;
			} else {
				player.nextChat = 13;
			}
			break;

		case 13:
			sendPlayerChat1("No! Leave me alone.");
			player.nextChat = 0;
			break;

		case 14: // lumby guide
			sendNpcChat1(
					"Greetings, welcome to " + Constants.SERVER_NAME + ".",
					player.talkingNpc, "Lumbridge Guide");
			player.nextChat = 0;
			break;

		case 15:
			sendOption2("I would like to view your shop",
					"I would like to fix my barrows");
			player.dialogueAction = 91;
			break;

		case 16:
			player.getShopAssistant().openShop(8);
			player.nextChat = 0;
			break;

		case 17:
			player.getPlayerAssistant().fixAllBarrows();
			player.nextChat = 0;
			break;

		case 18:
			sendNpcChat1("Hi welcome to the partyroom.", player.talkingNpc,
					"Party Pete");
			player.nextChat = 605;
			break;

		case 19:
			sendNpcChat1("Hello would you like to buy a beer for 2 gp?",
					player.talkingNpc, "Bartender");
			player.nextChat = 20;
			break;

		case 20:
			if (player.getItemAssistant().playerHasItem(995, 2)) {
				sendPlayerChat1("Yes I would love a beer.");
				player.getItemAssistant().deleteItem2(995, 2);
				player.getItemAssistant().addOrDropItem(1917, 1);
				player.nextChat = 0;
			} else {
				sendPlayerChat1("I don't have enough coins to buy a beer.");
				player.nextChat = 0;
			}
			break;

		case 21:
			sendNpcChat1(
					"Hello, would you like me to bring you into to shilo village?",
					player.talkingNpc, "Mosol Rei");
			player.nextChat = 22;
			break;

		case 22:
			sendOption2("Yes", "No");
			player.dialogueAction = 93;
			break;

		case 23:
			player.getPlayerAssistant().movePlayer(2867, 2952, 0);
			player.nextChat = 0;
			break;

		case 24:
			if (player.getItemAssistant().playerHasItem(995, 200)) {
				sendNpcChat3("Hello Fair Traveler.",
						"Can i interest you in a ride back to shantay",
						"for 200 coins?", player.talkingNpc, "Rug Merchant");
				player.nextChat = 25;
			} else {
				sendNpcChat1("You need 200 coins to travel my rug.",
						player.talkingNpc, "Rug Merchant");
				player.nextChat = 0;
			}
			break;

		case 25:
			sendPlayerChat1("Yes please.");
			player.getPlayerAssistant().startTeleport(3308, 3108, 0, "modern");
			player.getItemAssistant().deleteItem2(995, 200);
			player.nextChat = 0;
			break;

		case 26:
			if (player.getItemAssistant().playerHasItem(995, 200)) {
				sendNpcChat2("Hello Fair Traveler.",
						"Can i interest you in a ride for 200 coins?",
						player.talkingNpc, "Rug Merchant");
				player.nextChat = 27;
			} else {
				sendNpcChat1("You need 200 coins to travel my rug.",
						player.talkingNpc, "Rug Merchant");
				player.nextChat = 0;
			}
			break;

		case 27:
			sendOption4("Pollnivneach (North)", "Bedabin Camp", "Uzer",
					"Shantay Pass");
			player.dialogueAction = 700;
			break;

		case 28:
			sendPlayerChat1("Pollnivneach please.");
			player.getPlayerAssistant().startTeleport(3350, 3004, 0, "modern");
			player.nextChat = 32;
			break;
		case 29:
			sendPlayerChat1("Bedabin Camp please.");
			player.getPlayerAssistant().startTeleport(3180, 3043, 0, "modern");
			player.nextChat = 32;
			break;
		case 30:
			sendPlayerChat1("Uzer please.");
			player.getPlayerAssistant().startTeleport(3469, 3111, 0, "modern");
			player.nextChat = 32;
			break;
		case 31:
			sendPlayerChat1("Shantay pass please.");
			player.getPlayerAssistant().startTeleport(3308, 3108, 0, "modern");
			player.nextChat = 32;
			break;
		case 32:
			sendNpcChat1("Enjoy!", player.talkingNpc, "Rug Merchant");
			player.getItemAssistant().deleteItem2(995, 200);
			player.nextChat = 0;
			break;
		case 33:
			sendNpcChat1("The trip to karamja will cost you 30 coins.",
					player.talkingNpc, "Sailor");
			player.nextChat = 34;
			break;

		case 34:
			sendOption2("Yes", "No");
			player.dialogueAction = 67;
			break;

		case 35:
			sendPlayerChat1("No thank you.");
			player.nextChat = 0;
			break;
		case 36:
			sendPlayerChat1("Yes please.");
			player.nextChat = 583;
			break;

		case 37:
			sendNpcChat2("Welcome to my food store!",
					"Would you like to buy anything?", player.talkingNpc,
					"Wydin");
			player.nextChat = 38;
			break;
		case 38:
			sendOption3("Yes please.", "No thank you.", "Can I get a job here?");
			player.dialogueAction = 68;
			break;
		case 39:
			sendPlayerChat1("Yes please.");
			player.getShopAssistant().openShop(34);
			break;
		case 40:
			sendPlayerChat1("No thank you.");
			player.nextChat = 0;
			break;
		case 41:
			sendPlayerChat1("Can I get a job here?");
			player.ptjob = 1;
			player.nextChat = 42;
			break;
		case 42:
			sendNpcChat3("Well you're keen, I'll give you that.",
					"Okay, I'll give you a go.",
					"Have you got your own white apron?", player.talkingNpc,
					"Wydin");
			player.nextChat = 43;
			break;
		case 43:
			sendPlayerChat1("No, I haven't.");
			player.nextChat = 44;
			break;
		case 44:
			sendNpcChat2(
					"Well, you can't work here unless you have a white apron.",
					"Health and safety regulations, you understand.",
					player.talkingNpc, "Wydin");
			player.nextChat = 45;
			break;
		case 45:
			sendPlayerChat1("Where can I get one of those?");
			player.nextChat = 46;
			break;
		case 46:
			sendNpcChat2(
					"Well I get all mine at the clothing shop in Varrock.",
					"They sell them cheap there.", player.talkingNpc, "Wydin");
			player.nextChat = 47;
			player.ptjob = 1;
			break;
		case 47:
			sendNpcChat1("Have you got your white apron now?",
					player.talkingNpc, "Wydin");
			player.nextChat = 47;
			break;
		case 48:
			if (player.getItemAssistant().playerHasItem(1005, 1)) {
				sendPlayerChat1("Yes I have one here.");
				player.nextChat = 49;
				player.ptjob = 1;
			} else {
				sendPlayerChat1("No I still need to get one.");
				player.nextChat = 0;
			}
			break;
		case 49:
			sendNpcChat2("Wow, your well prepared! Your hired.",
					"Go through the back and tidy up for me please.",
					player.talkingNpc, "Wydin");
			player.nextChat = 0;
			player.ptjob = 2;
			break;
		case 50:
			sendNpcChat1("What am I to do?", player.talkingNpc, "Cook");
			player.nextChat = 51;
			break;
		case 51:
			sendOption4("What's wrong?", "Can you cook me a cake?",
					"You don't look very happy.", "Nice hat.");
			player.dialogueAction = 52;
			break;
		case 52:
			sendPlayerChat1("What's wrong?");
			player.nextChat = 54;
			break;
		case 54:
			sendNpcChat3(
					"Oh dear, oh dear, oh dear, I'm in a terrible terrible",
					"mess! It's the Duke's birthday today, and I should be",
					"making him a lovely big birthday cake!",
					player.talkingNpc, "Cook");
			player.nextChat = 55;
			break;
		case 55:
			sendNpcChat4(
					"I've forgotten to buy the ingredients. I'll never get",
					"them in time now. He'll sack me! What will I do? I have",
					"four children and a goat to look after. Would you help",
					"me? Please?", player.talkingNpc, "Cook");
			player.nextChat = 56;
			break;
		case 56:
			sendOption2("I'm always happy to help a cook in distress.",
					"I can't right now, Maybe later.");
			player.dialogueAction = 57;
			break;
		case 57:
			sendPlayerChat1("Yes, I'll help you.");// 9157
			player.nextChat = 60;
			break;
		case 58:
			sendPlayerChat1("I can't right now, Maybe later.");// 9158
			player.nextChat = 59;
			break;
		case 59:
			sendNpcChat1("Oh please! Hurry then!", player.talkingNpc, "Cook");
			player.nextChat = 0;
			break;
		case 60:
			sendNpcChat2("Oh thank you, thank you. I need milk, an egg, and",
					"flour. I'd be very grateful if you can get them for me.",
					player.talkingNpc, "Cook");
			player.cookAss = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 61;
			break;
		case 61:
			sendPlayerChat1("So where do I find these ingredients then?");
			player.nextChat = 62;
			break;
		case 62:
			sendNpcChat3("You can find flour in any of the shops here.",
					"You can find eggs by killing chickens.",
					"You can find milk by using a bucket on a cow",
					player.talkingNpc, "Cook");
			player.nextChat = 0;
			break;
		case 63:
			sendNpcChat1("I don't have time for your jibber-jabber!",
					player.talkingNpc, "Cook");
			player.nextChat = 0;
			break;
		case 64:
			sendNpcChat1("Does it look like I have the time?",
					player.talkingNpc, "Cook");
			player.nextChat = 0;
			break;
		case 65:
			sendPlayerChat1("You don't look so happy.");
			player.nextChat = 54;
			break;
		case 66:
			sendNpcChat1(
					"How are you getting on with finding the ingredients?",
					player.talkingNpc, "Cook");
			player.nextChat = 67;
			break;
		case 67:
			if (player.getItemAssistant().playerHasItem(1944, 1)
					&& player.getItemAssistant().playerHasItem(1927, 1)
					&& player.getItemAssistant().playerHasItem(1933, 1)) {
				sendPlayerChat1("Here's all the items!");
				player.nextChat = 68;
			} else {
				sendPlayerChat1("I don't have all the items yet.");
				player.nextChat = 59;
			}
			break;
		case 68:
			player.getItemAssistant().deleteItem(1944, 1);
			player.getItemAssistant().deleteItem(1927, 1);
			player.getItemAssistant().deleteItem(1933, 1);
			player.cookAss = 2;
			sendNpcChat2("You brought me everything I need! I'm saved!",
					"Thank you!", player.talkingNpc, "Cook");
			player.nextChat = 69;
			break;
		case 69:
			sendPlayerChat1("So do I get to go to the Duke's Party?");
			player.nextChat = 70;
			break;
		case 70:
			sendNpcChat2(
					"I'm afraid not, only the big cheeses get to dine with the",
					"Duke.", player.talkingNpc, "Cook");
			player.nextChat = 72;
			break;
		case 72:
			sendPlayerChat2(
					"Well, maybe one day I'll be important enough to sit on",
					"the Duke's table");
			player.nextChat = 74;
			break;
		case 74:
			sendNpcChat1("Maybe, but I won't be holding my breath.",
					player.talkingNpc, "Cook");
			player.nextChat = 75;
			break;
		case 75:
			QuestRewards.cookReward(player);
			break;
		case 76:
			sendNpcChat1("Thanks for helping me out friend!",
					player.talkingNpc, "Cook");
			player.nextChat = 0;
			break;
		case 84:
			sendNpcChat1("How are you getting on finding all my supplies",
					player.talkingNpc, "Doric");
			player.nextChat = 85;
			break;
		case 85:
			if (player.getItemAssistant().playerHasItem(434, 6)
					&& player.getItemAssistant().playerHasItem(436, 4)
					&& player.getItemAssistant().playerHasItem(440, 2)) {
				sendPlayerChat1("Here's all the items!");
				player.nextChat = 86;
			} else {
				sendPlayerChat1("I haven't found all the items yet.");
				player.nextChat = 88;
			}
			break;
		case 86:
			player.getItemAssistant().deleteItem2(434, 6);
			player.getItemAssistant().deleteItem2(436, 4);
			player.getItemAssistant().deleteItem2(440, 2);
			player.doricQuest = 2;
			sendNpcChat2("You brought me everything i need.", "Thank You!",
					player.talkingNpc, "Doric");
			player.nextChat = 87;
			break;
		case 87:
			QuestRewards.doricFinish(player);
			player.nextChat = 0;
			break;
		case 88:
			sendNpcChat1("Hurry Then!", player.talkingNpc, "Doric");
			player.nextChat = 0;
			break;
		case 89:
			sendNpcChat1(
					"Hello traveler, what brings you to my humble smithy?",
					player.talkingNpc, "Doric");
			player.nextChat = 90;
			break;
		case 90:
			sendOption2("Mind your own buisness, Shortstuff!",
					"I wanted to use your anivils.");
			player.dialogueAction = 55;
			break;
		case 91:
			sendNpcChat1("Mind your own buisness, Shortstuff!",
					player.talkingNpc, "Doric");
			player.nextChat = 0;
			break;
		case 92:
			sendNpcChat1("So you want to use my anivils?", player.talkingNpc,
					"Doric");
			player.nextChat = 98;
			break;
		case 98:
			sendPlayerChat1("Yes, I would like to use your anivil.");
			player.nextChat = 93;
			break;
		case 93:
			sendNpcChat4("My anvils get enough work with my own use.",
					"I make pickaxes, and it takes a lot of hard work.",
					"If you could get me some more materials,",
					"then i could let use them.", player.talkingNpc, "Doric");
			player.nextChat = 94;
			break;
		case 94:
			sendOption2("Yes i will get you the materials.",
					"No, hitting rocks is boring.");
			player.dialogueAction = 56;
			break;
		case 95:
			sendPlayerChat1("No, hitting rocks is boring.");
			player.nextChat = 0;
			break;
		case 96:
			sendPlayerChat1("Yes i will get you the materials.");
			player.nextChat = 97;
			break;
		case 97:
			sendNpcChat4(
					"Clay is what i use more than anything, to make casts.",
					"Could you get me 6 clay, 4 copper, and 2 iron, please?",
					"I could give a nice little reward",
					"Take my pickaxe with you just incase you need it.",
					player.talkingNpc, "Doric");
			player.getItemAssistant().addOrDropItem(1265, 1);
			player.nextChat = 99;
			break;
		case 99:
			sendPlayerChat1("Certainly, I'll be right back!");
			player.doricQuest = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 0;
			break;
		case 100:
			sendNpcChat1("Thanks for the help!", player.talkingNpc, "Doric");
			player.nextChat = 0;
			break;
		case 101:
			sendNpcChat2("You're on your own now, Jal-Yt. Prepare to fight", "for your life!", 2617, "TzHaar-Mej-Jal");
				player.nextChat = 0;
			break;
			case 102:
			sendNpcChat1("Look out, here comes TzTok-Jad!", 2617, "TzHaar-Mej-Jal");
				player.nextChat = 0;
			break;
			case 103:
			sendNpcChat2("You even defeated TzTok-Jad, I am most impressed!", "Please accept this gift as a reward.", 2617, "TzHaar-Mej-Jal");
				player.nextChat = 0;
			break;
			case 104:
			sendNpcChat1("Well done in the cave, here, take TokKul as reward.", 2617, "TzHaar-Mej-Jal");
				player.nextChat = 0;
			break;
		case 145:
			sendPlayerChat1("Give me a quest!");
			player.nextChat = 146;
			break;
		case 146:
			sendNpcChat1("Give me a quest what?", player.talkingNpc,
					"Wizard Mizgog");
			player.nextChat = 147;
			break;
		case 147:
			sendPlayerChat1("Give me a quest please.");
			player.nextChat = 148;
			break;
		case 148:
			sendNpcChat2(
					"Well seeing as you asked nicely... I could do with some",
					"help.", player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 149;
			break;
		case 149:
			sendNpcChat2("The wizard Grayzag next door decided he didn't like",
					"me so he enlisted an army of hundreds of imps.",
					player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 150;
			break;
		case 150:
			sendNpcChat3(
					"These imps stole all sorts of my things. Most of these",
					"things I don't really care about, just eggs and balls of",
					"string and things.", player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 151;
			break;
		case 151:
			sendNpcChat2(
					"But they stole my four magical beads. There was a red",
					"one, a yellow one, a black one, and a white one.",
					player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 152;
			break;
		case 152:
			sendNpcChat2(
					"These imps have now spread out all over the kingdom.",
					"Could you get my beads back for me?", player.talkingNpc,
					"Wizard Mizgog");
			player.nextChat = 153;
			break;
		case 153:
			sendOption2("I'll try.",
					"I've better things to do than chase imps.");
			player.dialogueAction = 125;
			break;
		case 154:
			sendPlayerChat1("I'll try.");
			player.impsC = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 155;
			break;
		case 155:
			sendNpcChat1("That's great, thank you.", player.talkingNpc,
					"Wizard Mizgog");
			player.nextChat = 0;
			break;

		case 156:
			sendNpcChat1("So how are you doing finding my beads?",
					player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 157;
			break;
		case 157:
			sendPlayerChat1("I am still working on it.");
			player.nextChat = 0;
			break;

		case 158:
			sendNpcChat1("So how are you doing finding my beads?",
					player.talkingNpc, "Wizard Mizgog");
			player.nextChat = 159;
			break;
		case 159:
			sendPlayerChat1("I've got all four beads. It was hard work I can tell you.");
			player.nextChat = 160;
			break;
		case 160:
			sendNpcChat3(
					"Give them here and I'll check that really are MY",
					"beads, before I give you your reward. You'll take it, it's",
					"an amulet of accuracy.", player.talkingNpc,
					"Wizard Mizgog");
			player.nextChat = 161;
			break;
		case 161:
			sendStatement("You give four coloured beads to Wizard Mizgog.");
			if (player.getItemAssistant().playerHasItem(1470, 1)
					&& player.getItemAssistant().playerHasItem(1472, 1)
					&& player.getItemAssistant().playerHasItem(1474, 1)
					&& player.getItemAssistant().playerHasItem(1476, 1)) {
				player.getItemAssistant().deleteItem2(1470, 1);
				player.getItemAssistant().deleteItem2(1472, 1);
				player.getItemAssistant().deleteItem2(1474, 1);
				player.getItemAssistant().deleteItem2(1476, 1);
				player.impsC = 2;
				player.nextChat = 162;
			} else {
				player.nextChat = 157;
			}
			break;
		case 162:
			QuestRewards.impFinish(player);
			break;
		case 163:
			sendPlayerChat1("I've better things to do than chase imps.");
			player.nextChat = 0;
			break;

		case 164:
			sendNpcChat3("What are you doing on my land? ",
					"You're not the one who keeps leaving all my gates open",
					"and letting out all my sheep are you?", player.talkingNpc,
					"Fred");
			player.nextChat = 165;
			break;
		case 165:
			sendOption3("I'm looking for a quest.",
					"I'm looking for something to kill.", "I'm lost.");
			player.dialogueAction = 63;
			break;
		case 166:
			sendPlayerChat1("I'm looking for a quest.");
			player.nextChat = 170;
			break;
		case 167:
			sendNpcChat1(
					"Goblins are great for killing near the bridge in lumbridge.",
					player.talkingNpc, "Fred");
			player.nextChat = 0;
			break;
		case 168:
			sendNpcChat1("Your in lumbridge silly.", player.talkingNpc, "Fred");
			player.nextChat = 0;
			break;
		case 169:
			sendNpcChat2("You're after a quest, you say",
					"Actually I could do with a bit of help.",
					player.talkingNpc, "Fred");
			player.nextChat = 170;
			break;
		case 170:
			sendNpcChat3("My sheep are getting mighty wolly.",
					"I'd be much obliged if you could shear them.",
					"And while you're at it spin the wool for me too.",
					player.talkingNpc, "Fred");
			player.nextChat = 171;
			break;
		case 171:
			sendNpcChat4("Yes, that's it.", " Bring me 20 balls of wool.",
					"And I'm sure I could sort out some sort of payment.",
					" Of course, there's the small matter of The Thing.",
					player.talkingNpc, "Fred");
			player.nextChat = 172;
			break;
		case 172:
			sendOption3("Yes okay. I can do that.",
					"That doesn't sound a very exciting quest.",
					"What do you mean, The Thing?");
			player.sheepShear = 1;
			QuestAssistant.sendStages(player);
			player.dialogueAction = 64;
			break;
		case 173:
			sendPlayerChat1("Yes okay. I can do that.");
			player.nextChat = 177;
			break;
		case 174:
			sendPlayerChat1("Nevermind, that doesn't sound a very exciting quest.");
			player.nextChat = 0;
			break;
		case 175:
			sendPlayerChat1("What do you mean, The Thing?");
			player.nextChat = 176;
			break;
		case 176:
			sendNpcChat1("Never mind.", player.talkingNpc, "Fred");
			player.nextChat = 0;
			break;
		case 177:
			sendNpcChat2("Good! Now one more thing,",
					"do you actually know how to shear a sheep?.",
					player.talkingNpc, "Fred");
			player.nextChat = 178;
			break;
		case 178:
			sendOption2("Of course!", "Err. No, I don't know acctually.");
			player.dialogueAction = 65;
			break;
		case 179:
			sendPlayerChat1("Of course!");
			player.nextChat = 180;
			break;
		case 180:
			sendNpcChat1("And you know how to spin wool into balls?",
					player.talkingNpc, "Fred");
			player.nextChat = 181;
			break;
		case 181:
			sendOption2("I'm something of an expert actually!",
					"I don't know how to spin wool, sorry..");
			player.dialogueAction = 66;
			break;
		case 182:
			sendPlayerChat1("I'm something of an expert actually!");
			player.nextChat = 183;
			break;
		case 183:
			sendNpcChat1("Well you can stop grinning and get to work then?",
					player.talkingNpc, "Fred");
			player.nextChat = 184;
			break;
		case 184:
			sendNpcChat1("I'm not paying you by the hour!", player.talkingNpc,
					"Fred");
			player.nextChat = 0;
			break;
		case 185:
			sendNpcChat1("How are you doing getting my balls of wool?",
					player.talkingNpc, "Fred");
			player.nextChat = 186;
			break;
		case 186:
			if (player.getItemAssistant().playerHasItem(1759, 20)) {
				sendPlayerChat1("I have some.");
				player.getItemAssistant().deleteItem(1759, 20);
				player.nextChat = 187;
			} else {
				sendStatement("I should get 20 balls wool first.");
				player.nextChat = 0;
			}
			break;
		case 187:
			sendNpcChat1("Give em here then.", player.talkingNpc, "Fred");
			player.nextChat = 188;
			break;
		case 188:
			sendPlayerChat1("That's the last of them.");
			player.nextChat = 189;
			break;
		case 189:
			sendNpcChat1("I guess I'd better pay you then.", player.talkingNpc,
					"Fred");
			player.nextChat = 263;
			break;
		case 263:
			QuestRewards.sheepFinish(player);
			break;
		case 190:
			sendNpcChat1("Greetings, welcome to my castle.", player.talkingNpc,
					"Duke Horacio");
			player.nextChat = 191;
			break;
		case 191:
			sendOption3("Have you any quests for me?",
					"Where can I find money?",
					"Can I have an anti dragon shield please?");
			player.dialogueAction = 124;
			break;
		case 192:// 9158
			sendPlayerChat1("Where can I find money?");
			player.nextChat = 193;
			break;
		case 193:
			sendNpcChat1("I'm sorry, I'm not sure.", player.talkingNpc,
					"Duke Horacio");
			player.nextChat = 0;
			break;
		case 194:// 9157
			if (player.runeMist == 0) {
				sendNpcChat2("Well, it's not really a quest",
						"but I recently discovered this strange talisman.",
						player.talkingNpc, "Duke Horacio");
				player.nextChat = 195;
			} else {
				sendNpcChat1("You have already started this quest.",
						player.talkingNpc, "Duke Horacio");
				player.nextChat = 0;
			}
			break;
		case 195:
			sendNpcChat2(
					"It seems to be mystical and I have never seen anything like it before.",
					"Would you take it to the head wizard at",
					player.talkingNpc, "Duke Horacio");
			player.nextChat = 196;
			break;
		case 196:
			sendNpcChat3(
					"the Wizards Tower for me?",
					"It's just south-west of here and should not take you very long at all.",
					"I would be awfully grateful.", player.talkingNpc,
					"Duke Horacio");
			player.nextChat = 197;
			break;
		case 197:
			sendOption2("Sure, no problem.", "Not right now.");
			player.dialogueAction = 140;
			break;
		case 198:// 9157
			sendPlayerChat1("Sure, no problem.");
			player.nextChat = 199;
			break;
		case 199:
			sendNpcChat2(
					"Thank you very much, stranger.",
					"I'm sure the head wizard will reward you for such an interesting find.",
					player.talkingNpc, "Duke Horacio");
			player.nextChat = 200;
			break;
		case 200:
			itemMessage1("The duke hands you an @blu@air talisman@blu@.", 1438,
					1);
			player.getItemAssistant().addOrDropItem(1438, 1);
			player.runeMist = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 0;
			break;
		case 201:
			sendNpcChat2(
					"Welcome adventurer, to the world renowed Wizards Tower",
					"How may I help you?", player.talkingNpc, "Sedridor");
			player.nextChat = 202;
			break;
		case 202:
			sendOption3("Nothing thanks, I'm just looking around.",
					"What are you doing down here?",
					"I'm looking for the head wizard.");
			player.dialogueAction = 126;
			break;
		case 203: // 9168
			sendNpcChat1("That's none of your buisness.", player.talkingNpc,
					"Sedridor");
			player.nextChat = 0;
			break;
		case 204: // 9169
			sendPlayerChat1("I'm looking for the head wizard.");
			player.nextChat = 205;
			break;
		case 205:
			sendNpcChat2("Oh, you are, are you?",
					"And just why would you be doing that?", player.talkingNpc,
					"Sedridor");
			player.nextChat = 207;
			break;
		case 207:
			sendPlayerChat3(
					"The Duke of Lumbridge sent me to find him. Most of these",
					"I have this weird talisman he found.",
					"He said the head wizard would be very interested in it.");
			player.nextChat = 208;
			break;
		case 208:
			sendNpcChat4("Did he now? HmmmMMMMMmmmm.",
					"Well that IS interested. Hand it over then adverturer",
					"let me see what all the hubbub about it is.",
					"Just some amulet I'll wager.", player.talkingNpc,
					"Sedridor");
			player.nextChat = 209;
			break;
		case 209:
			sendOption2("Ok, here you are.",
					"No, I'll only give it to the head wizard.");
			player.dialogueAction = 127;
			break;
		case 210:// 9157
			sendPlayerChat1("Ok, here you are.");
			player.nextChat = 212;
			break;
		case 212:
			itemMessage1("You hand the Talisman to the wizard.", 1438, 1);
			player.getItemAssistant().deleteItem(1438, 1);
			player.runeMist = 2;
			player.nextChat = 213;
			break;
		case 213:
			sendNpcChat1("Wow! This is... incredible!", player.talkingNpc,
					"Sedridor");
			player.nextChat = 214;
			break;
		case 214:
			sendNpcChat4("Th-this talisman you brought me...! ",
					"It is the last piece of the puzzle, I think! Finally!",
					"The legacy of our ancestors.",
					"It will return to us once more!", player.talkingNpc,
					"Sedridor");
			player.nextChat = 215;
			break;
		case 215:
			sendNpcChat3(
					"I need time to study this, " + player.playerName + ".",
					"Can you please do me this task while I study this talisman you have brought me?",
					"In the mighty town of Varrock, which", player.talkingNpc,
					"Sedridor");
			player.nextChat = 216;
			break;
		case 216:
			sendNpcChat2(
					"is located North East of here, there is a certain shop that sells magical runes.",
					"I have in this package all of the research I have done relating to the Rune Stones, and",
					player.talkingNpc, "Sedridor");
			player.nextChat = 217;
			break;
		case 217:
			sendNpcChat3(
					"require sombody to take them to the shopkeeper so that he may share my research",
					"and offer me his insights.",
					"Do this thing for me, and bring back what he gives you,",
					player.talkingNpc, "Sedridor");
			player.nextChat = 218;
			break;
		case 218:
			sendNpcChat3(
					"and if my suspicions are correct,",
					"I will let you into the knowledge of one of the greatest secrets this world has ever known!",
					"A secret so powerful that it destroyed the",
					player.talkingNpc, "Sedridor");
			player.nextChat = 219;
			break;
		case 219:
			sendNpcChat3(
					"original Wizards tower all of those centuries ago!",
					"My research, combined with this mysterious talisman...",
					"I cannot believe the answer to the mysteries is so close now!",
					player.talkingNpc, "Sedridor");
			player.nextChat = 220;
			break;
		case 220:
			sendNpcChat2("Do this thing for me " + player.playerName + ".",
					" Be rewarded in a way you can never imagine.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 222;
			break;
		case 222:
			sendOption2("Yes, certainly.", "No, I'm busy.");
			player.dialogueAction = 128;
			break;
		case 223:// 9157
			sendPlayerChat1("Yes, certainly.");
			player.nextChat = 224;
			break;
		case 224:
			sendNpcChat3(
					"Take this package, and head directly North from here.",
					"through Draynor village, until you reach the Barbarian Village.",
					"Then head East from there until you reach Varrock.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 225;
			break;
		case 225:
			sendNpcChat3(
					"Once in Varrock, take this package to the owner of the rune shop.",
					"His name is Aubury.",
					"You may find it helpful to ask one of Varrock's citizens for directions,",
					player.talkingNpc, "Sedridor");
			player.nextChat = 226;
			break;
		case 226:
			sendNpcChat3(
					"as Varrock can be a confusing place for the first time visitor.",
					"He will give you a special item - bring it back to me,",
					"and I shall show you the mystery of the runes...",
					player.talkingNpc, "Sedridor");
			player.nextChat = 227;
			break;
		case 227:
			itemMessage1("The head wizard gives you a package.", 290, 1);
			player.getItemAssistant().addOrDropItem(290, 1);
			player.nextChat = 228;
			break;
		case 228:
			sendNpcChat1("Best of luck with your quest, " + player.playerName
					+ ".", player.talkingNpc, "Sedridor");
			player.nextChat = 0;
			break;
		case 229:
			sendNpcChat1("Do you want to buy some runes?", player.talkingNpc,
					"Aubury");
			player.nextChat = 230;
			break;
		case 230:
			sendOption3("Yes please!",
					"Oh, it's a rune shop. No thank you, then.",
					"I have been sent here with a package for you.");
			player.dialogueAction = 129;
			break;
		case 231: // 9167
			sendPlayerChat1("Yes please!");
			player.getShopAssistant().openShop(52);
			player.nextChat = 0;
			break;
		case 232: // 9169
			sendPlayerChat1("I have been sent here with a package for you. It's for the head wizard at the Wizards Tower.");
			player.nextChat = 233;
			break;
		case 233:
			sendNpcChat3(
					"Really? But... surely he can't have..?",
					"Please, let me have it,",
					"it must be extremely important for him to have sent a stranger.",
					player.talkingNpc, "Aubury");
			player.nextChat = 234;
			break;
		case 234:
			itemMessage1("You hand Aubury the research package.", 290, 1);
			player.getItemAssistant().deleteItem(290, 1);
			player.runeMist = 3;
			player.nextChat = 235;
			break;
		case 235:
			sendNpcChat2(
					"This... this is incredible. Please,",
					"give me a few moments to quickly look over this, and then talk to me again.",
					player.talkingNpc, "Aubury");
			player.nextChat = 236;
			break;
		case 236:
			itemMessage1("Aubury gives you the research notes.", 290, 1);
			player.getItemAssistant().addOrDropItem(290, 1);
			player.nextChat = 237;
			break;
		case 237:
			sendNpcChat1(
					"Thank you, now you should head back to Sedridor and tell him your discoveries.",
					player.talkingNpc, "Aubury");
			player.nextChat = 0;
			break;
		case 238:
			sendNpcChat2(
					"Welcome, adventure to the world-renowed Wizards Tower.",
					"How may i help you?", player.talkingNpc, "Sedridor");
			player.nextChat = 239;
			break;
		case 239:
			sendNpcChat2("Ah, " + player.playerName + ". How goes your quest?",
					"Have you delivered the research notes to my friend yet?",
					player.talkingNpc, "Sedridor");
			player.nextChat = 240;
			break;
		case 240:
			sendPlayerChat1("Yes, I have. He gave me some research notes to pass on to you.");
			player.nextChat = 241;
			break;
		case 241:
			sendNpcChat1("May I have them?", player.talkingNpc, "Sedridor");
			player.nextChat = 242;
			break;
		case 242:
			sendPlayerChat1("Sure. I have them here.");
			player.nextChat = 243;
			break;
		case 243:
			sendNpcChat2(
					"You have been nothing but helpful, adventured.",
					"In return, I can let you in on the secret of our research.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 245;
			break;
		case 245:
			sendNpcChat2(
					"Many centuries ago, the wizards of the Wizards Tower learnt the secret of creating runes,",
					"which allowed them to cast magic very easily.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 247;
			break;
		case 247:
			sendNpcChat3(
					"But, when this tower was burnt down, the sercret of creating runes was lost with it...",
					"or so I thought.",
					"Some months ago, while searching these ruins for information, ",
					player.talkingNpc, "Sedridor");
			player.nextChat = 248;
			break;
		case 248:
			sendNpcChat2(
					"I came upon a scroll that made refrence to a magical rock",
					"deep in the ice fields of the north.", player.talkingNpc,
					"Sedridor");
			player.nextChat = 249;
			break;
		case 249:
			sendNpcChat3(
					"This rock was called the rune essence by those magicians who studied it's powers.",
					"Apparently, by simply breaking a chunk for it,",
					"a rune could be fashioned and taken to certain",
					player.talkingNpc, "Sedridor");
			player.nextChat = 250;
			break;
		case 250:
			sendNpcChat3(
					"magical altars that were scattered across the land.",
					"Now, this is an intersting little peice of history,",
					"not much use to us since we do not have access to this rune essence",
					player.talkingNpc, "Sedridor");
			player.nextChat = 251;
			break;
		case 251:
			sendNpcChat2(
					"teleportations spell that he had never come across before, When cast,",
					"it took him to a strange rock, yet it felt strangly familiar.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 252;
			break;
		case 252:
			sendNpcChat3(
					"As I'm sure you have guessed, he had discovered a spell to the mythical rune essence.",
					"As soon as he told me of this,",
					"I saw the importance of the find.", player.talkingNpc,
					"Sedridor");
			player.nextChat = 253;
			break;
		case 253:
			sendNpcChat2(
					"For, if we could find the altars spoken of in the ancient texts",
					"we would once more be able to create runes as our ancestors had done!",
					player.talkingNpc, "Sedridor");
			player.nextChat = 254;
			break;
		case 254:
			sendPlayerChat1("I'm still not sure how I fit into this little story of yours.");
			player.nextChat = 255;
			break;
		case 255:
			sendNpcChat3(
					"You haven't guessed?",
					"This talisman you brough me is the key to the elemental altar of air!",
					"When you hold it, it directs you to", player.talkingNpc,
					"Sedridor");
			player.nextChat = 256;
			break;
		case 256:
			sendNpcChat3("the entrance of the long-forgotten Air Altar.",
					"By bringing peices of the rune essence the Air Altar,",
					"you will be able to fashion your own air runes",
					player.talkingNpc, "Sedridor");
			player.nextChat = 257;
			break;
		case 257:
			sendNpcChat3(
					"That's not all!",
					"By finding other talismans similar to his one,",
					"you will eventually be able to craft every rune that is available in this world, jus",
					player.talkingNpc, "Sedridor");
			player.nextChat = 258;
			break;
		case 258:
			sendNpcChat3(
					"as our ancestors did.",
					"I cannot stress enough what find this is!",
					"Now, due to the risks invovled in letting this mighty power fall into the wrong hands.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 259;
			break;
		case 259:
			sendNpcChat3(
					"I will keep the teleport spell to the rune essence a closely guarded secret.",
					"This means that, if any evil power should discover the talismans required to enter the emental temples,",
					"we will be able to prevent their", player.talkingNpc,
					"Sedridor");
			player.nextChat = 260;
			break;
		case 260:
			sendNpcChat3(
					"access to the rune essence.",
					"I know not where the altars are located, not do I know where the talismans have been scattered,",
					"but now return your air talisman.", player.talkingNpc,
					"Sedridor");
			player.nextChat = 261;
			break;
		case 261:
			sendNpcChat1(
					"Find the Air Altar and you will be able to craft you blank runes into air runes at will.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 262;
			break;
		case 262:
			sendNpcChat2(
					"Any time you wish to visit the rune essence,",
					"speak to me or Aubury and we will open a portal to that mystical place.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 264;
			break;
		case 264:
			sendPlayerChat1("So, only you and Aubury know the teleport spell to the rune essence?");
			player.nextChat = 266;
			break;
		case 266:
			sendNpcChat2(
					"No, there are others. When you speak to them,",
					"they will know you and grant you access to that place when asked.",
					player.talkingNpc, "Sedridor");
			player.nextChat = 267;
			break;
		case 267:
			sendNpcChat2(
					"Use the air talisman to locate the Air Altar and use any further talismans you find to locate the other altars.",
					"Now, my research notes please?", player.talkingNpc,
					"Sedridor");
			player.nextChat = 268;
			break;
		case 268:
			itemMessage1(
					"You give the research notes to Sedrdior. He gives you an air talisman.",
					290, 1);
			player.getItemAssistant().deleteItem(290, 1);
			player.runeMist = 4;
			QuestRewards.runeFinish(player);
			player.nextChat = 0;
			break;
		case 269:
			sendPlayerChat1("Hello, are you ok?");
			player.nextChat = 270;
			break;
		case 270:
			sendNpcChat2("Do I look ok?", "Those kid's drive me crazy.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 271;
			break;
		case 271:
			sendNpcChat1("I'm sorry. It's just that I've lost her.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 272;
			break;
		case 272:
			sendPlayerChat1("Lost whom?");
			player.nextChat = 273;
			break;
		case 273:
			sendNpcChat1("Fluffs, poor Fluffs, She never hurt anyone.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 274;
			break;
		case 274:
			sendPlayerChat1("Who's Fluffs?");
			player.nextChat = 275;
			break;
		case 275:
			sendNpcChat4("My beloved feline friend, Fluffs.",
					"She's been purring by my side for almost a decade.",
					"Could you go and search for her while",
					"I take care of the children?", player.talkingNpc,
					"Gertrude");
			player.nextChat = 276;
			break;
		case 276:
			sendOption3(
					"Well, I suppose I could though I'd need more details.",
					"What's in it for me?",
					"Sorry, I'm too busy to play per rescue.");
			player.dialogueAction = 60;
			break;
		case 277: // 9167
			sendPlayerChat1("Well, I suppose I could though I'd need more details.");
			player.nextChat = 280;
			break;
		case 278: // 9169
			sendPlayerChat1("Sorry I'm too busy to play per rescue.");
			player.nextChat = 0;
			break;
		case 279: // 9168
			sendNpcChat1(
					"Come back with a better attitude a maybe you will find out.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 0;
			break;
		case 280:
			sendNpcChat2("Really? Thank you so much!",
					"I really have no idea where she could be!",
					player.talkingNpc, "Gertrude");
			player.gertCat = 1;
			player.nextChat = 281;
			break;
		case 281:
			sendNpcChat2(
					"I think my sons, Shilop and Wilough, saw the cat last.",
					"They'll be out in the marketplace.", player.talkingNpc,
					"Gertrude");
			player.nextChat = 282;
			break;
		case 282:
			sendPlayerChat2("The marketplace? Which one would that be?",
					"It would help to know what they get up to, as well.");
			player.nextChat = 283;
			break;
		case 283:
			sendNpcChat4(
					"Really? Well, I generally let them do what they want,",
					"so I've no idea exactly what they would be doing.",
					"They are good lads, though. I'm sure, they are",
					"just watching the passers-by in Varrock Marketplace.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 284;
			break;
		case 284:
			sendNpcChat1("Oh, to be young and carefree again!",
					player.talkingNpc, "Gertrude");
			player.nextChat = 285;
			break;
		case 285:
			sendPlayerChat2(
					"I'll see what I can do. Two young lads in Varrock.",
					"I hope that there's no school trip passing when I arrive.");
			QuestAssistant.sendStages(player);
			player.nextChat = 0;
			break;
		case 286:
			sendPlayerChat1("Hello there, I've been looking for you.");
			player.nextChat = 287;
			break;
		case 287:
			sendNpcChat1("I didn't mean to take it! I just forgot to pay.",
					player.talkingNpc, "Wilough");
			player.nextChat = 288;
			break;
		case 288:
			sendPlayerChat2("What?",
					"I'm trying to help your mum find some cat called Fluffs.");
			player.nextChat = 289;
			break;
		case 289:
			sendNpcChat4("Ohh...well, in that case I might be able to help.",
					"Fluffs followed me to my super secret hideout.",
					"I haven't seen her since.",
					"She's probably off eating small creatures somewhere.",
					player.talkingNpc, "Wilough");
			player.nextChat = 290;
			break;
		case 290:
			sendPlayerChat2("Where is this secret hideout?",
					"I really need to find that cat for you mum.");
			player.nextChat = 291;
			break;
		case 291:
			sendNpcChat2("If I told you that, It wouldn't be a secret.",
					"What if I need to escape for the law? I need a hideout.",
					player.talkingNpc, "Wilough");
			player.nextChat = 292;
			break;
		case 292:
			sendPlayerChat2("From my limited knowledge of law,",
					"they are not usually involved in manhunts for children.");
			player.nextChat = 293;
			break;
		case 293:
			sendNpcChat3(
					"Well it's still mine anyway, we need a place to relax,",
					"sometimes.",
					"Those two little brothers at the house are just such babies.",
					player.talkingNpc, "Wilough");
			player.nextChat = 294;
			break;
		case 294:
			sendOption3(
					"Tell me sonny, or I will inform you are a pair of criminals.",
					"What will make you tell me?",
					"Well never mind, it's Fluffs loss.");
			player.dialogueAction = 61;
			break;
		case 295: // 9167
			sendNpcChat1("No. Where not criminals.", player.talkingNpc,
					"Wilough");
			player.nextChat = 0;
			break;
		case 296: // 9169
			sendPlayerChat1("Well never mind, it's Fluffs loss.");
			player.nextChat = 0;
			break;
		case 297: // 9168
			sendPlayerChat1("What will make you tell me?");
			player.nextChat = 298;
			break;
		case 298:
			sendNpcChat1("Well...now you ask, I am a bit short on cash.",
					player.talkingNpc, "Wilough");
			player.nextChat = 299;
			break;
		case 299:
			sendPlayerChat1("How much?");
			player.nextChat = 300;
			break;
		case 300:
			sendNpcChat1("10 coins.", player.talkingNpc, "Wilough");
			player.nextChat = 301;
			break;
		case 301:
			sendNpcChat1("10 coins?!", player.talkingNpc, "Shilop");
			player.nextChat = 302;
			break;
		case 302:
			sendNpcChat1("I'll handle this.", player.talkingNpc, "Shilop");
			player.nextChat = 303;
			break;
		case 303:
			sendNpcChat1("100 coins should cover it.", player.talkingNpc,
					"Shilop");
			player.nextChat = 304;
			break;
		case 304:
			sendPlayerChat2("100 coins!",
					"What sort of expensive things do you need that badly?");
			player.nextChat = 305;
			break;
		case 305:
			sendNpcChat2("Well I don't like chocolate",
					"and have you seen how much sweets cost to buy?",
					player.talkingNpc, "Shilop");
			player.nextChat = 306;
			break;
		case 306:
			sendPlayerChat2("Why should I pay you then",
					"can you answer that as easily?");
			player.nextChat = 307;
			break;
		case 307:
			sendNpcChat4("Obviously you shouldn't pay that much,",
					"but I won't help otherwise. I never liked,",
					"that cat anyway, fussy scratchy thing it is",
					"so what do you say?", player.talkingNpc, "Shilop");
			player.nextChat = 308;
			break;
		case 308:
			sendOption2("I'm not paying you a thing.", "Okay then, I'll pay.");
			player.dialogueAction = 62;
			break;
		case 309: // 9158
			sendPlayerChat2("Okay then. I'll pay, but I'll want you,",
					"to tell your mother what a nice person I am.");
			player.nextChat = 310;
			break;
		case 310:
			sendNpcChat1("What?", player.talkingNpc, "Shilop");
			player.nextChat = 311;
			break;
		case 311:
			sendPlayerChat2(
					"I'll want you to tell your mother what a nice person I am",
					"so she rewards me for this search.");
			player.nextChat = 312;
			break;
		case 312:
			sendNpcChat1("It's a deal.", player.talkingNpc, "Shilop");
			player.nextChat = 313;
			break;
		case 313:
			if (player.getItemAssistant().playerHasItem(995, 100)) {
				sendStatement("You give the lad 100 coins.");
				player.getItemAssistant().deleteItem2(995, 100);
				player.nextChat = 314;
				player.gertCat = 2;
			} else {
				sendStatement("I don't have 100 coin's I should come back.");
				player.nextChat = 0;
			}
			break;
		case 314:
			sendPlayerChat1("There you go, now where did you see Fluffs?");
			player.nextChat = 315;
			break;
		case 315:
			sendNpcChat4(
					"We hide out at the lumber mill to the northeast.",
					"Just beyond the Jolly Beat Inn.",
					"I saw Fluffs running around in there. Well,",
					"not so much running as plodding lazily, you get the point.",
					player.talkingNpc, "Wilough");
			player.nextChat = 316;
			break;
		case 316:
			sendPlayerChat1("Anything else?");
			player.nextChat = 317;
			break;
		case 317:
			sendNpcChat4(
					"Well, technically you are tresspassing inside there but noone seems to care.",
					"You'll have to find the broken fence to get in.",
					"It will be a bit of a squeeze for a grown-up but",
					"I'm sure you can manage that.", player.talkingNpc,
					"Wilough");
			player.nextChat = 0;
			break;
		case 318:
			sendStatement("Mew");
			player.nextChat = 319;
			break;
		case 319:
			sendPlayerChat1("Progress atleast.");
			player.nextChat = 321;
			break;
		case 321:
			sendStatement("Fluffs laps up the milk greedly. The she mews at you again.");
			player.nextChat = 0;
			break;
		case 322:
			sendStatement("Mew!");
			player.nextChat = 323;
			break;
		case 323:
			sendPlayerChat1("Progress atleast.");
			player.nextChat = 324;
			break;
		case 324:
			sendStatement2("Fluffs devours the dougle sardine greedly.", "Then she mews at you again.");
			player.nextChat = 0;
			break;
		case 325:
			sendStatement2("Fluffs seems afraid to leave.", "In the lumberyard below you can hear the mewing.");
			player.nextChat = 0;
			break;
		case 326:
			sendNpcChat1("Purr...", player.talkingNpc, "Fluffs");
			player.nextChat = 327;
			break;
		case 327:
			sendStatement("Fluffs and her offspring will now live happily.");
			player.nextChat = 0;
			break;
		case 328:
			sendPlayerChat2(
					"Hello Gertrude. Fluffs has run off with her lost kittens.",
					"That I have now returned to her.");
			player.nextChat = 329;
			break;
		case 329:
			sendNpcChat4(
					"You're Back!",
					"Thank you, thank you!",
					"Fluffs just came back. I think she was upset,",
					"because she couldn't find her kittens.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 330;
			break;
		case 330:
			sendStatement("Gertrude thanks you heartily.");
			player.nextChat = 331;
			break;
		case 331:
			sendNpcChat2("If you wouldn't have found her kittens,",
					"then they would have died out there.", player.talkingNpc,
					"Gertrude");
			player.nextChat = 332;
			break;
		case 332:
			sendPlayerChat1("That's okay, I like to do my bit.");
			player.nextChat = 333;
			break;
		case 333:
			sendNpcChat3(
					"I don't know how to thank you.",
					"I have no real material possesions, but I do have kittens.",
					"I can really only look after one or two.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 334;
			break;
		case 334:
			sendPlayerChat1("Well, if one needs a home.");
			player.nextChat = 335;
			break;
		case 335:
			sendNpcChat4("I would sell one to my couzin in West Aroudnge.",
					"I hear there's a " + " epidemic there,",
					"but it's too far for me to travel.",
					"With all my boys and all.", player.talkingNpc, "Gertrude");
			player.nextChat = 336;
			break;
		case 336:
			sendNpcChat1("Here you go look after her and thank you.",
					player.talkingNpc, "Gertrude");
			player.nextChat = 337;
			break;
		case 337:
			sendStatement("Gertrude gives you a kitten.");
			QuestRewards.gertFinish(player);
			player.nextChat = 0;
			break;
		case 338:
			sendNpcChat1("Welcome to the church of holy Saradomin..",
					player.talkingNpc, "Father Aereck");
			player.nextChat = 339;
			break;
		case 339:
			sendOption4("Who's Saradomin?", "Nice place you've got here",
					"I'm looking for a quest", "Never Mind");
			player.dialogueAction = 32;
			break;
		case 340:// 9178
			sendNpcChat1("None of your buisness.", player.talkingNpc,
					"Father Aereck");
			player.nextChat = 0;
			break;
		case 341:// 9179
			sendPlayerChat1("Nice place you've got here.");
			player.nextChat = 0;
			break;
		case 342:// 9180
			sendPlayerChat1("I'm looking for a quest.");
			player.nextChat = 343;
			break;
		case 343:
			sendNpcChat1("That's lucky, I need someone to do a quest for me.",
					player.talkingNpc, "Father Aereck");
			player.nextChat = 344;
			break;
		case 344:
			sendPlayerChat1("Okay, let me help then.");
			player.nextChat = 345;
			break;
		case 345:
			sendNpcChat3(
					"Thank you. The problem is there's,",
					"a ghost in the graveyard crypt just south of this church.",
					"I would like you to get rid of it.", player.talkingNpc,
					"Father Aereck");
			player.nextChat = 346;
			break;
		case 346:
			sendNpcChat2("You'll need the help of my friend, Father Urhney,",
					"who is a bit of a ghost expert.", player.talkingNpc,
					"Father Aereck");
			player.nextChat = 347;
			break;
		case 347:
			sendNpcChat2(
					"He's currently living in a little shack to the south of,",
					"the Lumbridge Swamp near the coast.", player.talkingNpc,
					"Father Aereck");
			player.nextChat = 348;
			break;
		case 348:
			sendNpcChat2("My name is Father Aereck, by the way.",
					"Pleased to meet you.", player.talkingNpc, "Father Aereck");
			player.nextChat = 349;
			break;
		case 349:
			sendPlayerChat1("Likewise.");
			player.nextChat = 350;
			break;
		case 350:
			sendNpcChat3("Take care traveling through the swamps.",
					"To get there just follow the path south,",
					"through the graveyard.", player.talkingNpc,
					"Father Aereck");
			player.nextChat = 351;
			player.restGhost = 1;
			QuestAssistant.sendStages(player);
			break;
		case 351:
			sendPlayerChat1("I will thanks.");
			player.nextChat = 0;
			break;
		case 352:
			sendNpcChat1("Go away! I'm meditating.", player.talkingNpc,
					"Father Urhney");
			player.nextChat = 353;
			break;
		case 353:
			sendOption4("Well, that's friendly",
					"Father Aereck sent me to talk to you",
					"I've come to repossess your house", "Never Mind");
			player.dialogueAction = 33;
			break;
		case 354:// 9178
			sendPlayerChat1("Well, that's friendly.");
			player.nextChat = 0;
			break;
		case 355: // 9180
			sendPlayerChat1("I've come to repossess your house.");
			player.nextChat = 0;
			break;
		case 356: // 9179
			sendPlayerChat1("Father Aereck sent me to talk to you.");
			player.nextChat = 357;
			break;
		case 357:
			sendNpcChat2("I suppose I better talk to you then.",
					"What has he got himself into this time?",
					player.talkingNpc, "Father Urhney");
			player.nextChat = 358;
			break;
		case 358:
			sendOption2("A ghost is haunting his graveyard",
					"You mean he gets into lots of problems?");
			player.dialogueAction = 34;
			break;
		case 359: // 9158
			sendPlayerChat1("You mean he gets into lots of problems?");
			player.nextChat = 360;
			break;
		case 360:
			sendNpcChat1("Yes, he does. A ghost is haunting his graveyard.",
					player.talkingNpc, "Father Urhney");
			player.nextChat = 0;
			break;
		case 361:
			sendPlayerChat1("A ghost is haunting his graveyard");
			player.nextChat = 362;
			break;
		case 362:
			sendNpcChat1("Oh, the silly fool.", player.talkingNpc,
					"Father Urhney");
			player.nextChat = 363;
			break;
		case 363:
			sendNpcChat2("I leave town for five months,",
					"and he's already having problems.", player.talkingNpc,
					"Father Urhney");
			player.nextChat = 364;
			break;
		case 364:
			sendNpcChat1("*sigh*", player.talkingNpc, "Father Urhney");
			player.nextChat = 365;
			break;
		case 365:
			sendNpcChat3("Well I can't go back and exorcise it",
					"I vowed not to leave this place until,",
					"I've spent a full two years praying and meditating.",
					player.talkingNpc, "Father Urhney");
			player.nextChat = 366;
			break;
		case 366:
			sendNpcChat1(
					"I'll tell you what I can do though take this amulet.",
					player.talkingNpc, "Father Urhney");
			player.nextChat = 367;
			player.getItemAssistant().addOrDropItem(552, 1);
			player.restGhost = 2;
			break;
		case 367:
			sendNpcChat1("It's a ghost speak amulet.", player.talkingNpc,
					"Father Urhney");
			player.nextChat = 368;
			break;
		case 368:
			sendNpcChat3(
					"It's called that because, when you wear it, you can,",
					"speak to ghosts. Many ghosts are doomed to remain in this,",
					"world because they have some important task left uncompleted.",
					player.talkingNpc, "Father Urhney");
			player.nextChat = 369;
			break;
		case 369:
			sendNpcChat3(
					"If you know what this task is, you can get rid of the ghost.",
					"I'm not making any guarentees, mind you,",
					"but it's the best I can do right now.", player.talkingNpc,
					"Father Urhney");
			player.nextChat = 370;
			break;
		case 370:
			sendPlayerChat1("Thank you. I'll give it a try.");
			player.nextChat = 0;
			break;
		case 371:
			sendPlayerChat1("Hello ghost how are you?");
			player.nextChat = 372;
			break;
		case 372:
			sendNpcChat1("Not very good, actually.", player.talkingNpc,
					"Restless Ghost");
			player.nextChat = 373;
			break;
		case 373:
			sendPlayerChat1("What's the problem?");
			player.nextChat = 374;
			break;
		case 374:
			sendNpcChat1("Did you just understand what I said?",
					player.talkingNpc, "Restless Ghost");
			player.nextChat = 375;
			break;
		case 375:
			sendOption4("Yep. Now, tell me what the problem is.",
					"No, you sound like you're speaking nonsense to me.",
					"Wow, this amulet works!", "Never mind.");
			player.dialogueAction = 35;
			break;
		case 376: // 9179
			sendPlayerChat1("No, you sound like you're speaking nonsense to me.");
			player.nextChat = 0;
			break;
		case 377: // 9180
			sendPlayerChat1("Wow, this amulet works!");
			player.nextChat = 0;
			break;
		case 378: // 9178
			sendPlayerChat1("Yep. Now, tell me what the problem is.");
			player.nextChat = 379;
			break;
		case 379:
			sendNpcChat2("Wow! This is incredible!",
					"I didn't expect anyone to ever understand me!",
					player.talkingNpc, "Restless Ghost");
			player.nextChat = 380;
			break;
		case 380:
			sendPlayerChat1("Okay, okay, I can understand you.");
			player.nextChat = 381;
			break;
		case 381:
			sendPlayerChat1("But have you any idea why you're doomed to be a ghost?");
			player.nextChat = 382;
			break;
		case 382:
			sendNpcChat1("Well, to be honest, I'm not sure.",
					player.talkingNpc, "Restless Ghost");
			player.nextChat = 383;
			break;
		case 383:
			sendPlayerChat2(
					"I've been told that a certain task needs to be completed",
					"before you can rest in peace.");
			player.nextChat = 384;
			break;
		case 384:
			sendNpcChat1("I should think it's because I've lost my head.",
					player.talkingNpc, "Restless Ghost");
			player.nextChat = 385;
			break;
		case 385:
			sendPlayerChat1("What? I can see your head perfectly fine. Well, see through it at least.");
			player.nextChat = 386;
			break;
		case 386:
			sendNpcChat4(
					"No, no, I mean from my REAL body.",
					"If you look in my coffin you'll see my corpse is without it's,",
					"skull. Last thing I remember was being attacked by a warlock,",
					"while I was mining. It was at the mine just south of this",
					player.talkingNpc, "Restless Ghost");
			player.nextChat = 387;
			break;
		case 387:
			sendNpcChat1("graveyard.", player.talkingNpc, "Restless Ghost");
			player.nextChat = 388;
			player.restGhost = 3;
			break;
		case 388:
			sendPlayerChat1("Okay. I'll try to get your skull back for you so you can rest in peace.");
			player.nextChat = 0;
			break;
		case 389:
			sendNpcChat1("Why me?...", player.talkingNpc, "Romeo");
			player.nextChat = 390;
			break;
		case 390:
			sendNpcChat2("Why isn't she returning any of them...",
					"Is it my hair...", player.talkingNpc, "Romeo");
			player.nextChat = 391;
			break;
		case 391:
			sendOption2("What's wrong?", "Yes it's your hair");
			player.dialogueAction = 118;
			break;
		case 392: // 9158
			sendPlayerChat1("Haha yes it's your hair, get a haircut loser!");
			player.nextChat = 393;
			break;
		case 393:
			sendNpcChat1("Tis' a sad world...", player.talkingNpc, "Romeo");
			player.nextChat = 0;
			break;
		case 394: // 9157
			sendPlayerChat1("What's wrong?");
			player.nextChat = 396;
			break;
		case 396:
			sendNpcChat1("My Juliet..my poor poor Juliet", player.talkingNpc,
					"Romeo");
			player.nextChat = 397;
			break;
		case 397:
			sendNpcChat3("I've been trying to contact her all day",
					"but the problem is...she won't return any",
					"of my letters...", player.talkingNpc, "Romeo");
			player.nextChat = 398;
			break;
		case 398:
			sendOption2("Why don't you just meet in person?",
					"I might have to go now...");
			player.dialogueAction = 119;
			break;
		case 399: // 9157
			sendNpcChat1("Well you see...the problem is..", player.talkingNpc,
					"Romeo");
			player.nextChat = 401;
			break;
		case 401:
			sendNpcChat1("Her mother doesn't know we've been dating.",
					player.talkingNpc, "Romeo");
			player.nextChat = 402;
			break;
		case 402:
			sendNpcChat1(
					"Can you please speak with Juliet and see what's going on?",
					player.talkingNpc, "Romeo");
			player.nextChat = 403;
			break;
		case 403:
			sendOption2("Yes I'll do so now", "Is that my fish calling me?");
			player.dialogueAction = 120;
			break;
		case 404:// 9158
			sendPlayerChat1("I might have to go now...");
			player.nextChat = 0;
			break;
		case 405:
			sendPlayerChat1("Why not just meet her in person?");
			player.nextChat = 406;
			break;
		case 406:
			sendPlayerChat1("Yeah anything to help a lover in need.");
			player.nextChat = 407;
			break;
		case 407:
			player.romeojuliet++;
			QuestAssistant.sendStages(player);
			sendNpcChat2("Great, Juliet is just in the house west of here",
					"You will most likely find her upstairs.",
					player.talkingNpc, "Romeo");
			player.nextChat = 0;
			break;
		case 408:
			sendNpcChat1("Please speak to Juliet for me", player.talkingNpc,
					"Romeo");
			player.nextChat = 0;
			break;
		case 409:
			sendNpcChat1("How I long him...", player.talkingNpc, "Juliet");
			player.nextChat = 0;
			break;
		case 410:
			sendNpcChat1("Please you have to go.", player.talkingNpc, "Juliet");
			player.nextChat = 411;
			break;
		case 411:
			sendPlayerChat2("Wait, what's happening? Romeo has",
					"been looking all over for you.");
			player.nextChat = 412;
			break;
		case 412:
			sendNpcChat1("I can't explain much. Please just go.",
					player.talkingNpc, "Juliet");
			player.nextChat = 413;
			break;
		case 413:
			sendNpcChat1("Take this...and go...", player.talkingNpc, "Juliet");
			player.getItemAssistant().addOrDropItem(755, 1);
			player.romeojuliet++;
			player.nextChat = 0;
			break;
		case 414:
			sendNpcChat1("Just go...you shouldn't be here.", player.talkingNpc,
					"Juliet");
			player.nextChat = 0;
			break;
		case 415:
			sendNpcChat1(
					"Hey did you talk to her yet?..How I long for Juliet.",
					player.talkingNpc, "Romeo");
			player.nextChat = 416;
			break;
		case 416:
			sendPlayerChat1("Yes she gave me this let---");
			player.nextChat = 417;
			break;
		case 417:
			sendNpcChat1("Pass it here, pass it!", player.talkingNpc, "Romeo");
			player.getItemAssistant().deleteItem2(755, 1);
			player.nextChat = 418;
			break;
		case 418:
			sendNpcChat3(
					"Dear Romeo...sadly we can not see each other anymore",
					"mother has been complaining on how you aren't the right",
					"person.", player.talkingNpc, "Romeo");
			player.nextChat = 419;
			break;
		case 419:
			sendNpcChat3("We come from two different classes..",
					"I'm just some lonely Varrock girl, and",
					"your a fine prince that travels around the world..",
					player.talkingNpc, "Romeo");
			player.nextChat = 420;
			break;
		case 420:
			sendNpcChat1("This is my goodbyes...Juliet...", player.talkingNpc,
					"Romeo");
			player.nextChat = 0;
			player.romeojuliet++;
			player.romeojuliet++;
			break;
		case 421:
			sendNpcChat1("Well have you spoken to her?", player.talkingNpc,
					"Romeo");
			player.nextChat = 422;
			break;
		case 422:
			sendPlayerChat2("She gave me a letter to give you..",
					"Which I don't have on me");
			player.nextChat = 423;
			break;
		case 423:
			sendNpcChat2("Please bring it as soon as possible...",
					"How I miss my Juliet", player.talkingNpc, "Romeo");
			player.nextChat = 0;
			break;
		case 424:
			sendNpcChat1("She just...", player.talkingNpc, "Romeo");
			player.nextChat = 425;
			break;
		case 425:
			sendNpcChat1("What have I done wrong...", player.talkingNpc,
					"Romeo");
			player.nextChat = 426;
			break;
		case 426:
			sendNpcChat1("My Juliet...", player.talkingNpc, "Romeo");
			player.nextChat = 427;
			break;
		case 427:
			sendPlayerChat2("Are you just going to give up??",
					"What about love?");
			player.nextChat = 428;
			break;
		case 428:
			sendNpcChat1("No...", player.talkingNpc, "Romeo");
			player.nextChat = 429;
			break;
		case 429:
			sendNpcChat2("No, your right. Please",
					"speak to the witch just south west of here.",
					player.talkingNpc, "Romeo");
			player.nextChat = 430;
			break;
		case 430:
			sendNpcChat1("She'll know what to do.", player.talkingNpc, "Romeo");
			player.nextChat = 0;
			player.romeojuliet++;
			break;
		case 431:
			sendNpcChat1("Speak to Winelda. She's south west.",
					player.talkingNpc, "Romeo");
			player.nextChat = 0;
			break;
		case 432:
			sendNpcChat1("So I see that prince sent you here.",
					player.talkingNpc, "Winelda");
			player.nextChat = 433;
			break;
		case 433:
			sendPlayerChat1("Wait how did you know?");
			player.nextChat = 434;
			break;
		case 434:
			sendNpcChat2("I'm a witch..I've studied the arts",
					"of magic for years.", player.talkingNpc, "Winelda");
			player.nextChat = 435;
			break;
		case 435:
			sendNpcChat2("I will help you. This one time.",
					"But next time I won't be so kind.", player.talkingNpc,
					"Winelda");
			player.nextChat = 436;
			break;
		case 436:
			sendOption2("So what do I need to do?",
					"I don't think I'm up for this anymore...");
			player.dialogueAction = 121;
			break;
		case 437:// 9158
			sendPlayerChat1("This is just getting more twisted...I have to go...");
			player.nextChat = 0;
			break;
		case 438:// 9157
			sendPlayerChat1("So what do you need me to do?");
			player.nextChat = 439;
			break;
		case 439:
			sendNpcChat1("You need, 1 rat's tail, 1 bone, and a vial of water",
					player.talkingNpc, "Winelda");
			player.nextChat = 440;
			break;
		case 440:
			sendNpcChat2("Bring those items here and I'll make you a potion",
					"that makes anyone tell the truth.", player.talkingNpc,
					"Winelda");
			player.nextChat = 0;
			player.romeojuliet++;
			break;
		case 441:
			sendNpcChat1("You need, 1 rats tail, 1 bone, and a vial of water",
					player.talkingNpc, "Winelda");
			player.nextChat = 442;
			break;
		case 442:
			sendNpcChat1("Speak to me when you have all 3 items.",
					player.talkingNpc, "Winelda");
			player.nextChat = 0;
			break;
		case 443:
			sendNpcChat1("What did the witch say?", player.talkingNpc, "Romeo");
			player.nextChat = 444;
			break;
		case 444:
			sendPlayerChat2("She wants me to bring her 3 items",
					"Then she'll speak to me");
			player.nextChat = 445;
			break;
		case 445:
			sendNpcChat1("Which items if you don't mind me asking?",
					player.talkingNpc, "Romeo");
			player.nextChat = 446;
			break;
		case 446:
			sendPlayerChat1("Oh just a rat's");
			player.nextChat = 448;
			break;
		case 447:
			sendNpcChat1("Nevermind.", player.talkingNpc, "Romeo");
			player.nextChat = 0;
			break;
		case 448:
			sendNpcChat1("I'll take that", player.talkingNpc, "Winelda");
			player.getItemAssistant().deleteItem2(300, 1);
			player.getItemAssistant().deleteItem2(227, 1);
			player.getItemAssistant().deleteItem2(526, 1);
			player.nextChat = 449;
			break;
		case 449:
			sendNpcChat1("Azari-Ahmi-Grantai!!", player.talkingNpc, "Winelda");
			player.nextChat = 450;
			break;
		case 450:
			sendNpcChat1("Here take this", player.talkingNpc, "Winelda");
			player.romeojuliet++;
			player.getItemAssistant().addOrDropItem(4836, 1);
			player.nextChat = 451;
			break;
		case 451:
			sendNpcChat1("Tell Juliet to drink this", player.talkingNpc,
					"Winelda");
			player.nextChat = 453;
			break;
		case 453:
			sendPlayerChat1("Wait but will Juliet actually drink it?");
			player.nextChat = 454;
			break;
		case 454:
			sendNpcChat3("I don't know?!", "What do I look like some kind of",
					"fortune teller?", player.talkingNpc, "Winelda");
			player.nextChat = 455;
			break;
		case 455:
			sendNpcChat1("Tell her it's soup or something.", player.talkingNpc,
					"Winelda");
			player.nextChat = 456;
			break;
		case 456:
			sendPlayerChat1("Wow she's polite...");
			player.nextChat = 0;
			break;
		case 457:
			sendNpcChat1("I told you to leave....", player.talkingNpc, "Juliet");
			player.nextChat = 458;
			break;
		case 458:
			sendPlayerChat1("Here take this.");
			player.nextChat = 459;
			break;
		case 459:
			sendStatement("Juliet drinks the potion.");
			player.getItemAssistant().deleteItem2(4836, 1);
			player.nextChat = 460;
			break;
		case 460:
			sendNpcChat1("What was that..", player.talkingNpc, "Juliet");
			player.nextChat = 461;
			break;
		case 461:
			sendPlayerChat1("Now tell me why you've been ignoring Romeo!");
			player.nextChat = 462;
			break;
		case 462:
			sendNpcChat1("Well...tomorrow's Romeos birthday.",
					player.talkingNpc, "Juliet");
			player.nextChat = 463;
			break;
		case 463:
			sendNpcChat3("Mama and I wanted to show Romeo that",
					"Even though he's richer then us we still",
					"care for him.", player.talkingNpc, "Juliet");
			player.nextChat = 464;
			break;
		case 464:
			sendPlayerChat1("Wait was that it?");
			player.nextChat = 465;
			break;
		case 465:
			sendNpcChat2("Yeah, I've been telling Mother to act",
					"grouchy with him.", player.talkingNpc, "Juliet");
			player.nextChat = 466;
			break;
		case 466:
			sendNpcChat2("That way we can throw him off easily.",
					"that's what a surprise is.", player.talkingNpc, "Juliet");
			player.nextChat = 467;
			break;
		case 467:
			sendPlayerChat1("I should get going");
			player.nextChat = 0;
			player.romeojuliet++;
			break;
		case 468:
			sendNpcChat1("Wait your not going to tell Romeo are you?",
					player.talkingNpc, "Juliet");
			player.nextChat = 0;
			break;
		case 469:
			sendNpcChat1("So? What's going on with Juliet?", player.talkingNpc,
					"Romeo");
			player.nextChat = 470;
			break;
		case 470:
			sendPlayerChat2("She's been planning a surprise",
					"birthday party for you all along");
			player.nextChat = 471;
			break;
		case 471:
			sendPlayerChat2("Her mother was in on in too. They",
					"just wanted to show that they're always there for you");
			player.nextChat = 472;
			break;
		case 472:
			sendNpcChat1("Wait but my birthday isn't till next week",
					player.talkingNpc, "Romeo");
			player.nextChat = 473;
			break;
		case 473:
			sendNpcChat2("That was very thoughtful of her",
					"Thank you young traveller for all your help",
					player.talkingNpc, "Romeo");
			player.nextChat = 474;
			break;
		case 474:
			QuestRewards.julietFinish(player);
			sendNpcChat2("Juliet and I have been great ever",
					"since you've helped. Thank you adventurer",
					player.talkingNpc, "Romeo");
			player.romeojuliet = 9;
			player.nextChat = 0;
			break;
		case 475:
			if (player.romeojuliet == 8) {
				player.nextChat = 474;
			}
			break;
		case 211:
			sendOption2("Quest.", "Buy Wool.");
			player.dialogueAction = 168;
			break;
		case 476:
			sendNpcChat2(
					"Praise Saradomin! He has Brought you here to save us",
					"all!", player.talkingNpc, "Morgan");
			player.nextChat = 477;
			break;
		case 477:
			sendPlayerChat1("Wha-");
			player.nextChat = 478;
			break;
		case 478:
			sendNpcChat3("He has guided your steps to my door, So that",
					"I may beseech you to save my village from a terrible",
					"threat.", player.talkingNpc, "Morgan");
			player.nextChat = 479;
			break;
		case 479:
			sendOption2("Why don't you save your own village?",
					"What terrible threat?");
			player.dialogueAction = 29;
			break;
		case 480:// 9157
			sendPlayerChat1("Why don't you save your own village?");
			player.nextChat = 0;
			break;
		case 481:// 9158
			sendPlayerChat1("What terrible threat?");
			player.nextChat = 482;
			break;
		case 482:
			sendNpcChat3(
					"Our village is plagued by a vampire. He visits us",
					"frequently and demands blood payments or he will,",
					"terroise us all!", player.talkingNpc, "Morgan");
			player.nextChat = 483;
			break;
		case 483:
			sendPlayerChat2("The vampire showed up all of a sudden",
					"and started attacking your village?");
			player.nextChat = 484;
			break;
		case 484:
			sendNpcChat3("I don't know, I just moved here with my wife.",
					"We'd move on again,",
					"but we're down on our luck and can't afford to.",
					player.talkingNpc, "Morgan");
			player.nextChat = 485;
			break;
		case 485:
			sendNpcChat3(
					"Besides, I don't want to abandon other innocents to this,",
					"fate. This could be a good community.",
					"If only that vampire would leave us.", player.talkingNpc,
					"Morgan");
			player.nextChat = 486;
			break;
		case 486:
			sendNpcChat1("Will you help me, brave adventurer?",
					player.talkingNpc, "Morgan");
			player.nextChat = 487;
			break;
		case 487:
			sendOption2("Yes", "No");
			player.dialogueAction = 30;
			break;
		case 488:// 9157
			sendPlayerChat1("Yes I'll help you.");
			player.vampSlayer = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 489;
			break;
		case 489:
			sendNpcChat4("Wonderful! You will succeed.,",
					"I'm sure of it you are very brave to take this on.",
					"But you should speak to my friend Harlow before you,",
					"do anything else.", player.talkingNpc, "Morgan");
			player.nextChat = 490;
			break;
		case 490:
			sendPlayerChat1("Who is this harlow?");
			player.nextChat = 491;
			break;
		case 491:
			sendNpcChat4("He is a retired vampire slayer!,",
					"I met him when i was a missionaire, long ago.",
					"He will be able to advise you on the best methods to,",
					"vanquish the vampire.", player.talkingNpc, "Morgan");
			player.nextChat = 492;
			break;
		case 492:
			sendPlayerChat2("You already know a vampire slayer?",
					"What do you need me for?");
			player.nextChat = 493;
			break;
		case 493:
			sendNpcChat3(
					"Harlow is... past his prime.. He's seen too many evil things in",
					"his life, and, to forget that, he drinks himself into,",
					"oblivion. I fear he will slayer vampires no more.",
					player.talkingNpc, "Morgan");
			player.nextChat = 494;
			break;
		case 494:
			sendPlayerChat1("Where can i find this Harlow?");
			player.nextChat = 495;
			break;
		case 495:
			sendNpcChat4(
					"He spends his time at the Blue Moon Inn, located in,",
					"Varrock. If you enter Varrock from the south it is,",
					"the second building on your right. I'm sure,",
					"it's filled with lively people, so you shouldn't miss it.",
					player.talkingNpc, "Morgan");
			player.nextChat = 496;
			break;
		case 496:
			sendPlayerChat1("Okay, I'll go find Harlow.");
			player.nextChat = 497;
			break;
		case 497:
			sendNpcChat1("May Saradomin protect you, my friend!",
					player.talkingNpc, "Morgan");
			player.nextChat = 0;
			break;
		case 498:
			sendNpcChat1("Buy me a drink please.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 499;
			break;
		case 499:
			sendOption4("No you've had enough.",
					"Are you Dr Harlow, the famous vampire slayer?",
					"You couldn't possibly be Dr Harlow, your just a drunk.",
					"Never mind.");
			player.dialogueAction = 31;
			break;
		case 500:// 9178
			sendPlayerChat1("No you've had enough.");
			player.nextChat = 0;
			break;
		case 501:// 9180
			sendPlayerChat1("You couldn't possibly be Dr Harlow, your just a drunk.");
			player.nextChat = 0;
			break;
		case 502:// 9179
			sendPlayerChat1("Are you Dr harlow, the famous vampire Slayer?");
			player.nextChat = 503;
			break;
		case 503:
			sendNpcChat1("Dependish whose is ashking.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 504;
			break;
		case 504:
			sendPlayerChat2("Your friend Morgan sent me.",
					"He said you could teach me how to slay a vampire.");
			player.nextChat = 505;
			break;
		case 505:
			sendNpcChat2("Shure I can teach you.",
					"I wash the best vampire shhlayer ever.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 506;
			break;
		case 506:
			sendNpcChat1("Buy me a beer and I'll teach you.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 507;
			break;
		case 507:
			sendPlayerChat2(
					"Your good friend Morgan is living in fear of a vampire,",
					"and all you think about is beer?");
			player.nextChat = 508;
			break;
		case 508:
			sendNpcChat1("Buy ush a drink anyway.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 509;
			break;
		case 509:
			if (player.getItemAssistant().playerHasItem(1917, 1)) {
				sendPlayerChat1("Okay, here you go.");
				player.getItemAssistant().deleteItem2(1917, 1);
				player.nextChat = 510;
			} else {
				sendPlayerChat1("Okay, let me get one.");
				player.nextChat = 0;
			}
			break;
		case 510:
			sendNpcChat1("Cheersh, matey.", player.talkingNpc, "Doctor Harlow");
			player.vampSlayer = 2;
			player.nextChat = 511;
			break;
		case 511:
			sendPlayerChat1("So tell me how to kill vampires then.");
			player.nextChat = 512;
			break;
		case 512:
			sendNpcChat1(
					"Yes, yes, vampires, I was very good at killing em once.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 513;
			break;
		case 513:
			sendNpcChat2("Vampire slaying is not to be undertaken lighty.",
					"You must go in prepared, or you will die.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 514;
			break;
		case 514:
			sendNpcChat1("*Sigh*", player.talkingNpc, "Doctor Harlow");
			player.nextChat = 515;
			break;
		case 515:
			sendNpcChat3(
					"A stake is an essential tool for any vampire slayer. The,",
					"stake must be used in the final blow againt the vampire.",
					"Or his dark magic will regenerate him to full health.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 516;
			break;
		case 516:
			sendNpcChat1("I always carry a spare, so you can have one.",
					player.talkingNpc, "Doctor Harlow");
			player.getItemAssistant().addOrDropItem(1549, 1);
			player.nextChat = 517;
			break;
		case 517:
			sendNpcChat2(
					"You'll need a special hammer as well, to drive it in in",
					"properly.", player.talkingNpc, "Doctor Harlow");
			player.nextChat = 518;
			break;
		case 518:
			sendNpcChat1("Hmm, I think i have a spare hammer you can have.",
					player.talkingNpc, "Doctor Harlow");
			player.getItemAssistant().addOrDropItem(2347, 1);
			player.vampSlayer = 3;
			player.nextChat = 519;
			break;
		case 519:
			sendNpcChat2("One last thing. It's wise to carry garlic with you,",
					"vampires are slightly weakened if they can smell garlic.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 520;
			break;
		case 520:
			sendNpcChat4("Garlic is pretty common,",
					"I know I always advised Morgan to keep a supply,",
					"so you might be able to get some from him.",
					"If not, I know they it is in Port Sarim.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 521;
			break;
		case 521:
			sendPlayerChat2("Okay, So those are the supplies I need",
					"but how do I acctually kill him?");
			player.nextChat = 522;
			break;
		case 522:
			sendNpcChat1("You are a eager one.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 523;
			break;
		case 523:
			sendNpcChat4("Killing a vampire is D A N G E R O U S!",
					"Never forget that. Go in prepared",
					"Understand you may die.",
					"It's a risk we all take in the buisness.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 524;
			break;
		case 524:
			sendNpcChat2("I've seen many fine men and women,",
					"die at the hands of vampires.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 525;
			break;
		case 525:
			sendNpcChat4(
					"Enter the vampire's lair and attempt to open the coffin.",
					"He should be asleep in there, so try to use the stake on,",
					"him. As you're new at this you'll,",
					"probably just wake him up and the real fight begins.",
					player.talkingNpc, "Doctor Harlow");
			player.nextChat = 526;
			break;
		case 526:
			sendNpcChat3("Fight him until he's nearly dead, and,",
					"when the moment is right. Stake him through the heart",
					"and hammer it in.", player.talkingNpc, "Doctor Harlow");
			player.nextChat = 527;
			break;
		case 527:
			sendNpcChat3("It's gruesome, but it's the only way.",
					"Once he's dead speak to morgan so",
					"he can notify the village.", player.talkingNpc,
					"Doctor Harlow");
			player.nextChat = 528;
			break;
		case 528:
			sendPlayerChat1("Thank you!");
			player.nextChat = 0;
			break;
		case 529:
			sendPlayerChat1("I killed the vampire!");
			player.nextChat = 530;
			break;
		case 530:
			sendNpcChat1("Congratulations! You have saved the village.",
					player.talkingNpc, "Morgan");
			player.nextChat = 3194;
			break;
		case 531:
			if (player.getItemAssistant().playerHasItem(1549)) {
				sendPlayerChat1("I still need to kill the vampire.");
				player.nextChat = 0;
			} else if (!player.getItemAssistant().playerHasItem(1549) && player.vampSlayer == 3) {
				player.getItemAssistant().addOrDropItem(1549, 1);
				sendPlayerChat1("Thank you, I will be more careful next time.");
				player.nextChat = 0;
			}
			break;
		case 532:
			sendNpcChat1("What could you want with an old woman like me?",
					player.talkingNpc, "Hetty");
			player.nextChat = 533;
			break;
		case 533:
			sendOption2("I am in search of a quest",
					"I've heard that you are a witch");
			player.dialogueAction = 74;
			break;
		case 534: // 9157
			sendPlayerChat1("I am in search of a quest.");
			player.nextChat = 536;
			break;
		case 535: // 9158
			sendNpcChat1("Yes I am...", player.talkingNpc, "Hetty");
			player.nextChat = 0;
			break;
		case 536:
			sendNpcChat2("Would you like to become more proficient in the",
					"dark arts?", player.talkingNpc, "Hetty");
			player.nextChat = 537;
			break;
		case 537:
			sendOption3("Yes help me become one with my darker side.",
					"No I have my principles and hour.",
					"What, you mean improve my magic?");
			player.dialogueAction = 58;
			break;
		case 538:// 9168
			sendPlayerChat1("No I have my principles and hour.");
			player.nextChat = 0;
			break;
		case 539:// 9169
			sendPlayerChat1("What, you mean improve my magic?");
			player.nextChat = 0;
			break;
		case 540:// 9167
			sendPlayerChat1("Yes help me become one with my darker side.");
			player.nextChat = 541;
			break;
		case 541:
			sendNpcChat2(
					"Ok, I'm going to make a potion to help bring out your",
					"darker self.", player.talkingNpc, "Hetty");
			player.nextChat = 542;
			break;
		case 542:
			sendNpcChat1("You will need certain ingredients.",
					player.talkingNpc, "Hetty");
			player.nextChat = 543;
			break;
		case 543:
			sendPlayerChat1("What do I need?");
			player.nextChat = 544;
			break;
		case 544:
			sendNpcChat2("You need an eye of newt, a rat's tail, an onion...",
					"Oh and a peice of burnt mean.", player.talkingNpc, "Hetty");
			player.nextChat = 545;
			break;
		case 545:
			sendPlayerChat1("Great, I'll go and get them.");
			player.witchspot = 1;
			QuestAssistant.sendStages(player);
			player.nextChat = 0;
			break;
		case 546:
			sendNpcChat1("So have you found the things for my potion?",
					player.talkingNpc, "Hetty");
			player.nextChat = 547;
			break;
		case 547:
			if (player.getItemAssistant().playerHasItem(221, 1)
					&& player.getItemAssistant().playerHasItem(300, 1)
					&& player.getItemAssistant().playerHasItem(2146, 1)) {
				sendPlayerChat1("Yes I have everything!");
				player.witchspot = 2;
				player.nextChat = 550;
			} else {
				sendPlayerChat1("No I still need to keep looking.");
				player.nextChat = 0;
			}
			break;
		case 548:
			sendPlayerChat1("Yes I have everything!");
			player.witchspot = 2;
			player.nextChat = 550;
			break;
		case 549:
			sendPlayerChat1("No I still need to keep looking.");
			player.nextChat = 0;
			break;
		case 550:
			sendNpcChat1("Excellent can I have them?", player.talkingNpc,
					"Hetty");
			player.nextChat = 551;
			break;
		case 551:
			if (player.getItemAssistant().playerHasItem(221, 1)
					&& player.getItemAssistant().playerHasItem(300, 1)
					&& player.getItemAssistant().playerHasItem(2146, 1)) {
				sendStatement4(
						"You pass the ingredients to Hetty and she puts them all into her,",
						"Cauldron.",
						"Hetty closes her eyes and begins to chant.",
						"The caludron bubbles mysteriously.");
				player.getItemAssistant().deleteItem2(221, 1);
				player.getItemAssistant().deleteItem2(300, 1);
				player.getItemAssistant().deleteItem2(2146, 1);
				player.nextChat = 552;
			} else {
				sendPlayerChat1("I don't have them anymore.");
				player.nextChat = 0;
			}
			break;
		case 552:
			sendPlayerChat1("Well, is it ready?");
			player.nextChat = 553;
			break;
		case 553:
			sendNpcChat1("Ok, now drink from the cauldron.", player.talkingNpc,
					"Hetty");
			player.nextChat = 0;
			break;
		case 554:
			sendNpcChat1("Arr, Matey!", player.talkingNpc, "Redbeard Frank");
			player.nextChat = 555;
			break;
		case 555:
			sendOption2("I'm in search of treasure.", "Arr!");
			player.dialogueAction = 71;
			break;
		case 556:// 9157
			sendPlayerChat1("I'm in search of treasure.");
			player.nextChat = 557;
			break;
		case 557:
			sendNpcChat2(
					"Arr, trasure you be after eh?",
					"Well I might be able to tell you where to find some... For a price...",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 558;
			break;
		case 558:
			sendPlayerChat1("What sort of price?");
			player.nextChat = 559;
			break;
		case 559:
			sendNpcChat2(
					"Well for example if you can get me a bottle of rum..",
					"Not just any rum mind...", player.talkingNpc,
					"Redbeard Frank");
			player.nextChat = 560;
			break;
		case 560:
			sendNpcChat2("I'd like some rum made on Karamja Island.",
					"There's no rum like Karamja Rum!", player.talkingNpc,
					"Redbeard Frank");
			player.nextChat = 561;
			break;
		case 561:
			sendOption2("Ok, I will bring you some rum.", "Not right now.");
			player.dialogueAction = 72;
			break;
		case 562:// 9158
			sendPlayerChat1("Not right now.");
			player.nextChat = 0;
			break;
		case 563:// 9157
			sendPlayerChat1("Ok, I will bring you some rum.");
			player.nextChat = 564;
			break;
		case 564:
			sendNpcChat1(
					"Yer a saint, although it'll take a miracle to get it off Karamja.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 565;
			break;
		case 565:
			sendPlayerChat1("What do you mean?");
			player.nextChat = 566;
			break;
		case 566:
			sendNpcChat3(
					"The customs office has been clampin' down on the export of spirits. ",
					"You seem like a resourceful young lad,",
					" I'm sure ye'll be able to find a way to slip the stuff past them.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 567;
			break;
		case 567:
			sendPlayerChat1("Well, I'll give it a shot.");
			player.nextChat = 568;
			break;
		case 568:
			sendNpcChat1("Arr, that's the spirit!", player.talkingNpc,
					"Redbeard Frank");
			player.nextChat = 0;
			player.pirateTreasure = 1;
			QuestAssistant.sendStages(player);
			break;
		case 569:
			if (player.pirateTreasure == 2
					&& player.getItemAssistant().playerHasItem(431, 1)) {
				sendNpcChat1("Arr, Matey!", player.talkingNpc, "Redbeard Frank");
				player.nextChat = 570;
			} else {
				sendPlayerChat1("No I still need to get some rum.");
				player.nextChat = 0;
			}
			break;
		case 570:
			sendNpcChat1("Have ye brought some rum for yer ol' mate Frank?",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 571;
			break;
		case 571:
			if (player.getItemAssistant().playerHasItem(431, 1)) {
				sendPlayerChat1("Yes I've got some.");
				player.nextChat = 572;
			} else {
				sendPlayerChat1("No I still need to get it.");
				player.nextChat = 0;
			}
			break;
		case 572:
			sendNpcChat2(
					"Now a deal's a deal, I'll tell ye about the treasure.",
					"I used to server under a pirate captain called One-Eyed Hector.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 573;
			break;
		case 573:
			sendNpcChat2(
					"Hector were very successful and became very rich.",
					"But about a year ago we were boarded by the Customs and Excise Agents.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 574;
			break;
		case 574:
			sendNpcChat2("Hector were killed along with many of the crew,",
					"I were one of the few to escape and I escaped with this.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 575;
			break;
		case 575:
			if (player.getItemAssistant().playerHasItem(431, 1)) {
				sendStatement("Frank happily takes the rum... and hands you a key");
				player.getItemAssistant().addOrDropItem(432, 1);
				player.getItemAssistant().deleteItem2(431, 1);
				player.nextChat = 576;
			} else {
				sendPlayerChat1("I still need to get some rum.");
				player.nextChat = 0;
			}
			break;
		case 576:
			sendNpcChat2(
					"This be Hector's key. ",
					"I belive it opens his chest in his old room in the Blue Moon Inn in Varrock.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 577;
			break;
		case 577:
			sendNpcChat1("With any luck his treasure will be in there.",
					player.talkingNpc, "Redbeard Frank");
			player.nextChat = 578;
			break;
		case 578:
			sendOption2("Ok thanks, I'll go and get it.",
					"So why didn't you ever get it?");
			player.dialogueAction = 73;
			break;
		case 579:// 9157
			sendPlayerChat1("Ok thanks, I'll go and get it.");
			player.pirateTreasure = 3;
			player.nextChat = 0;
			break;
		case 580:// 9158
			sendPlayerChat1("So why didn't you ever get it?");
			player.nextChat = 581;
			break;
		case 581:
			sendNpcChat1("That's none of your buisness.", player.talkingNpc,
					"Redbeard Frank");
			player.nextChat = 0;
			break;
		case 582:// 9158
			sendPlayerChat1("Arr!");
			player.nextChat = 0;
			break;

		case 583:
			if (!player.getItemAssistant().playerHasItem(995, 30)) {
				sendPlayerChat1("Sorry, I don't have enough coins for that.");
				player.nextChat = 0;
				return;
			} else {
				Sailing.startTravel(player, 5);
				player.getItemAssistant().deleteItem2(995, 30);
				player.nextChat = 0;
			}
			break;

		case 584:
			sendNpcChat1("The trip back to port sarim will cost you 30 coins.",
					player.talkingNpc, "Sailor");
			player.nextChat = 585;
			break;

		case 585:
			sendOption2("Yes", "No");
			player.dialogueAction = 68;
			break;

		case 586:
			sendPlayerChat1("No thank you.");
			player.nextChat = 0;
			break;

		case 587:
			sendPlayerChat1("Yes please.");
			player.nextChat = 588;
			break;

		case 588:
			if (!player.getItemAssistant().playerHasItem(995, 30)) {
				sendPlayerChat1("Sorry, I don't have enough coins for that.");
				player.nextChat = 0;
				return;
			} else {
				Sailing.startTravel(player, 6);
				player.getItemAssistant().deleteItem2(995, 30);
				player.nextChat = 0;
			}
			break;

		case 589:
			player.getItemAssistant().deleteItem2(995, 30);
			player.nextChat = 0;
			break;

		case 590:
			sendNpcChat1("Hello " + player.playerName + ".", player.talkingNpc,
					"Gnome Pilot");
			player.nextChat = 591;
			break;

		case 591:
			sendNpcChat1("Would you like to fly my glider?", player.talkingNpc,
					"Gnome Pilot");
			player.nextChat = 592;
			break;

		case 592:
			sendOption2("Yes", "No");
			player.dialogueAction = 130;
			break;

		case 593:
			sendPlayerChat1("No thank you.");
			player.nextChat = 0;
			break;

		case 594:
			sendPlayerChat1("Yes please.");
			player.nextChat = 595;
			break;

		case 595:
			player.gliderOpen = true;
			player.getPlayerAssistant().showInterface(802);
			break;

		case 596:
			sendNpcChat2("You didn't participate enough to take down",
					"You've gain less points", player.talkingNpc, "Void Knight");
			player.nextChat = 0;
			break;
		case 597:
			sendNpcChat3("You couldn't take down all the portals in time.",
					"Please try harder next time, or ask more",
					"people to join your game.", player.talkingNpc,
					"Void Knight");
			player.nextChat = 0;
			break;
		case 598:
			sendNpcChat3("Congratulations " + player.playerName
					+ "! you have taken",
					"down all the portals while keeping the Knight alive",
					"please accept this reward from us.", player.talkingNpc,
					"Void Knight");
			player.getActionSender().sendMessage(
					"You have won the Pest Control game!");
			player.nextChat = 0;
			break;
		case 599:
			sendNpcChat2("Do not let the Void Knights health reach 0!",
					"You can regain health by destroying more monsters,",
					player.talkingNpc, "Void Knight");
			player.nextChat = 600;
			break;
		case 600:
			sendNpcChat1("NOW GO AND DESTROY THOSE PORTALS!!!",
					player.talkingNpc, "Void Knight");
			player.nextChat = 0;
			break;
		case 601:
			sendNpcChat1("You call yourself a Knight?", player.talkingNpc,
					"Void Knight");
			player.nextChat = 0;
			break;
		case 602:
			sendNpcChat1("Hi welcome to Pest Control.", player.talkingNpc,
					"Void Knight");
			player.nextChat = 84;
			break;
		case 603:
			sendNpcChat1("Would you like to open the Armor Shop or Exp Shop?",
					player.talkingNpc, "Void Knight");
			player.nextChat = 85;
			break;
		case 604:
			sendOption2("Void Knight Armor", "Experience Shop");
			player.dialogueAction = 85;
			break;
		case 605:
			sendNpcChat2(
					"The party room is a fun place where you can put your items",
					"in the chest and drop them and have a party with your friends.",
					player.talkingNpc, "Party Pete");
			player.nextChat = 0;
			break;
			
		case 610:
			sendNpcChat1("Hello. I am the squire to Sir Vyvin.", player.talkingNpc, "Squire");
			player.nextChat = 611;
			break;
		case 611:
			sendOption2("And how is life as a squire?", "Wouldn't you prefer to be a squire for me?");
			player.dialogueAction = 181;/*DIALOGUE ACTION*/
			break;
		case 612:
			sendPlayerChat1("And how is life as a squire?");
			player.nextChat = 613;
			break;
		case 613:
			sendNpcChat3("Well, Sir Vyvin is a good guy to work for, however,", "I'm in a spot of trouble today. I've gone and lost Sir", "Vyvin's sword!", player.talkingNpc, "Squire");
			player.nextChat = 614;
			break;
		case 614:
			sendOption3("Do you know where you lost it?", "I can make a new sword if you like...", "Is he angry?");
			player.dialogueAction = 182;/*DIALOGUE ACTION*/
			break;
		case 615:
			sendPlayerChat1("I can make a new sword if you like...");
			player.nextChat = 616;
			break;
		case 616:
			sendNpcChat2("Thanks for the offer. I'd be surprised if you could", "though.", player.talkingNpc, "Squire");
			player.nextChat = 617;
			break;
		case 617:
			sendNpcChat4("The thing is, this sword is a family heirloom. It has been", "passed down through Vyvin's family for five", "generations! It was originally made by the Imacando", "dwarves, who were", player.talkingNpc, "Squire");
			player.nextChat = 618;
			break;
		case 618:
			sendNpcChat2("a particularly skilled tribe of dwarven smiths.", "I doubt anyone could make it in the style they do.", player.talkingNpc, "Squire");
			player.nextChat = 619;
			break;
		case 619:
			sendOption2("So would these dwarves make another one?", "Well I hope you find it soon.");
			player.dialogueAction = 183;/*DIALOGUE ACTION*/
			break;
		case 620:
			sendPlayerChat1("So would these dwarves make another one?");
			player.nextChat = 621;
			break;
		case 621:
			sendNpcChat4("I'm not a hundred percent sure the Imacando tribe", "exists anymore. I should think Reldo, the palace", "librarian will know; he has done a lot of", "research on the races of Runescape.", player.talkingNpc, "Squire");
			player.nextChat = 622;
			break;
		case 622:
			sendNpcChat3("I don't suppose you could try and track down the", "Imcando dwarves for me? I've got so much work to", "do...", player.talkingNpc, "Squire");
			player.nextChat = 623;
			break;
		case 623:
			sendOption2("Ok, I'll give it a go.", "No, I've got lots of mining work to do.");
			player.dialogueAction = 184;/*DIALOGUE ACTION*/
			break;
		case 624:
			sendPlayerChat1("Ok, I'll give it a go.");
		    player.knightS = 1;
		    QuestAssistant.sendStages(player);
			player.nextChat = 625;
			break;
		case 625:
			sendNpcChat2("Thank you very much! As I say, the best place to start", "should be with Reldo...", player.talkingNpc, "Squire");
			player.nextChat = 0;
			break;

		// reldo starts here
		case 626:
			sendNpcChat1("Hello stranger.", player.talkingNpc, "Reldo");
			player.nextChat = 630;
			break;
		case 627:
			sendOption3("Do you have anything to trade?", "What do you do?", "What do you know about the Imcando Dwarves?");
			player.dialogueAction = 185;/*DIALOGUE ACTION*/
		break;
		case 628:
			sendNpcChat1("I work here as a librarian.", player.talkingNpc, "Reldo");
			player.nextChat = 0;
		break;
		case 629:
			sendNpcChat1("I do not have anything to trade, sorry.", player.talkingNpc, "Reldo");
			player.nextChat = 0;
		break;
		case 630:
			if (player.knightS == 1) {
				sendPlayerChat1("What do you know about the Imcando dwarves?");
				player.nextChat = 631;
			} else {
				sendNpcChat1("You are not on this part of the Knights Sword quest.", player.talkingNpc, "Reldo");
				player.nextChat = 0;
			}
			break;
		case 631:
			sendNpcChat1("The imcando dwarves, you say?", player.talkingNpc, "Reldo");
			player.nextChat = 632;
			break;
		case 632:
			sendNpcChat3("Ah yes... for many hundreds of years they were the", "world's most skilled smiths. They used secret smithing", "knowledge passed down from generation to generation.", player.talkingNpc, "Reldo");
			player.nextChat = 633;
			break;
		case 633:
			sendNpcChat3("Unfortunately, about century ago, the once thriving", "race was wiped out during the barbarian invasions of", "that time.", player.talkingNpc, "Reldo");
			player.nextChat = 634;
			break;
		case 634:
			sendPlayerChat1("So are there any Imcando left at all?");
			player.nextChat = 635;
			break;
		case 635:
			sendNpcChat3("I believe a few of them survived, but with the bulk of", "their population destroyed their numbers have dwindled", "even further.", player.talkingNpc, "Reldo");
			player.nextChat = 636;
			break;
		case 636:
			sendNpcChat3("I believe I remember a couple living in Asgarnia near", "the cliffs on the Asgarnian southern peninsula, but they", "DO tend to keep to themselves.", player.talkingNpc, "Reldo");
			player.nextChat = 637;
			break;
		case 637:
			sendNpcChat4("They tend not to tell people they're the", "descendents of the Imcando, which is why people think", "that the tribe has died out totally, but you may well", "have more luck talking to them if you bring them some", player.talkingNpc, "Reldo");
			player.nextChat = 639;
			break;
		case 639:
			sendNpcChat1("redberry pie. They REALLY like redberry pie.", player.talkingNpc, "Reldo");
			player.knightS = 2;
			player.nextChat = 0;
			break;
			
		// start thurgo
		case 640:
			if (player.knightS == 2) {
				if (player.getItemAssistant().playerHasItem(2325, 1)) {
					sendPlayerChat1("Hello. Are you an Imcando dwarf?");
					player.nextChat = 641;
				} else if (!player.getItemAssistant().playerHasItem(2325, 1)) {
					sendNpcChat1("I am not interested in talking to you right now.", player.talkingNpc, "Thurgo");
					player.nextChat = 0;
				}
			}
			break;
			
		case 641:
			sendNpcChat1("Maybe. Who wants to know?", player.talkingNpc, "Thurgo");
			player.nextChat = 642;
		break;
		
		case 642:
			sendPlayerChat1("Would you like some redberry pie?");
			player.nextChat = 644;
		break;
		
		case 644:
			sendStatement("You see Thurgo's eyes light up.");
			player.nextChat = 645;
		break;
			
		case 645:
			sendNpcChat2("I'd never say no to a redberry pie! They're GREAT", "stuff!", player.talkingNpc, "Thurgo");
			player.nextChat = 646;
			break;
		case 646:
		if (player.getItemAssistant().playerHasItem(2325, 1)) {
			sendStatement2("You hand over the pie. Thurgo eats the pie. Thurgo pats his", "stomach.");
			player.getItemAssistant().deleteItem2(2325, 1);
			player.nextChat = 647;
		} else {
			sendPlayerChat1("I don't have pie anymore.");
			player.nextChat = 0;
		}
		break;
		case 647:
			sendNpcChat2("By Guthix! THAT was a good pie! Anyone who makes", "pie like THAT has got to be alright!", player.talkingNpc, "Thurgo");
			player.knightS = 3;
			player.nextChat = 0;
			break;
		case 648:
			sendPlayerChat1("Can you make a special sword?");
			player.nextChat = 649;
			break;
		case 649:
			sendNpcChat2("Well, after bringing me my favorite food I guess I", "should give it a go. What sort of sword is it?", player.talkingNpc, "Thurgo");
			player.nextChat = 650;
			break;
		case 650:
			sendPlayerChat4("I need you to make a sword for one of Falador's", "knights. He had one which was passed down through five", "generations, but his squire lost it. So we need an", "identical one to replace it.");
			player.nextChat = 651;
			break;
		case 651:
			sendNpcChat2("A knight's sword eh? Well I'd need to know exactly", "how it looked before I could make a new one.", player.talkingNpc, "Thurgo");
			player.nextChat = 652;
			break;
		case 652:
			sendNpcChat3("All the Faladorian knights used to have sword with", "unique designs according to their position. Could you bring me", "a picture or something?", player.talkingNpc, "Thurgo");
			player.nextChat = 653;
			break;
		case 653:
			sendPlayerChat1("I'll go ask his squire and see if I can find one.");
			player.knightS = 4;
			player.nextChat = 0;
			break;
		// back to squire
		case 654:
			sendNpcChat1("So how are you doing getting the sword?", player.talkingNpc, "Squire");
			player.nextChat = 655;
			break;
		case 655:
			sendPlayerChat2("I've found an Imcando dwarf but he needs a picture of", "the sword before he can make it.");
			player.nextChat = 656;
			break;
		case 656:
			sendNpcChat3("A picture eh? Hmmm.... The only one I can think of is", "in a small portrait of Sir Vyvin's father... Sir Vyvin", "keeps it in a cupboard in his room I think.", player.talkingNpc, "Squire");
			player.nextChat = 657;
			break;
		case 657:
			sendPlayerChat1("Ok, I'll try and get that then.");
			player.nextChat = 658;
			break;
		case 658:
			sendNpcChat2("Please don't let him catch you! He MUSTN'T know", "what happened!", player.talkingNpc, "Squire");
			player.knightS = 5;
			player.nextChat = 0;
			break;
		case 659:
			sendStatement("You find a small portrait in here which you take.");
			player.getItemAssistant().addItem(666, 1);
			player.nextChat = 0;
			break;
		// back to thurgo
		case 660:
			sendPlayerChat2("I have found a picture of the sword I would like you to", "make.");
			player.nextChat = 661;
			break;
		case 661:
			sendStatement("You give the portrait to Thurgo. Thurgo studies the portrait.");
			player.getItemAssistant().deleteItem2(666, 1);
			player.nextChat = 662;
			break;
		case 662:
			sendNpcChat2("Ok. You'll need to get me some stuff in order for me", "to make this.", player.talkingNpc, "Thurgo");
			player.nextChat = 663;
			break;
		case 663:
			sendNpcChat4("I'll need two iron bars to make the sword to start with.", "I'll also need an ore called blurite. It's useless for", "making actual weapons for fighting with except", "crossbows, but I'll need some as decoration for the hilt.", player.talkingNpc, "Thurgo");
			player.nextChat = 664;
			break;
		case 664:
			sendNpcChat2("It is a fairly rare sort of ore... The only place I know", "where to get it is under the cliff here...", player.talkingNpc, "Thurgo");
			player.nextChat = 665;
			break;
		case 665:
			sendNpcChat1("But it is guarded by a very powerful ice giant.", player.talkingNpc, "Thurgo");
			player.nextChat = 666;
			break;
		case 666:
			sendNpcChat3("Most of the rocks in that cliff are pretty useless, and", "don't contain much of anything, but there's", "DEFINITELY some blurite in there.", player.talkingNpc, "Thurgo");
			player.nextChat = 667;
			break;
		case 667:
			sendNpcChat2("You'll need a little bit of mining experience to be able to", "find it.", player.talkingNpc, "Thurgo");
			player.knightS = 7;
			player.nextChat = 668;
			break;
		case 668:
			sendPlayerChat1("Ok. I'll go and find them then.");
			player.nextChat = 0;
		break;

		// after getting the materials
		case 669:
			sendNpcChat1("How are you doing finding those sword materials?", player.talkingNpc, "Thurgo");
			player.nextChat = 670;
			break;
		case 670:
			if (player.getItemAssistant().playerHasItem(2351, 2) && player.getItemAssistant().playerHasItem(668, 1)) {
				sendPlayerChat1("I have them right here.");
				player.nextChat = 671;
			} else {
				sendPlayerChat1("I'm still working on it.");
				player.nextChat = 0;
			}
			break;
		case 671:
		if (player.knightS == 7) {
			sendStatement2("You give the blurite ore and two bars to Thurgo. Thurgo starts", "to make the sword. Thurgo hands you a sword.");
			player.getItemAssistant().deleteItem2(2351, 1);
			player.getItemAssistant().deleteItem2(2351, 1);
			player.getItemAssistant().deleteItem2(668, 1);
			player.knightS = 8;
			player.getItemAssistant().addItem(667, 1);
			player.nextChat = 672;
		} else if (player.knightS == 8) {
			sendStatement2("You give the blurite ore and two bars to Thurgo. Thurgo starts", "to make the sword. Thurgo hands you a sword.");
			player.getItemAssistant().deleteItem2(2351, 1);
			player.getItemAssistant().deleteItem2(2351, 1);
			player.getItemAssistant().deleteItem2(668, 1);
			player.getItemAssistant().addItem(667, 1);
			player.nextChat = 672;
		} else {
			sendNpcChat1("You are not on this part of the quest right now.", player.talkingNpc, "Thurgo");
			player.nextChat = 0;/*DIALOGUE ACTION*/
		}
		break;
		case 672:
			sendPlayerChat1("Thank you very much!");
			player.nextChat = 673;
		break;
		
		case 673:
			sendNpcChat1("Just remember to call in with more pie some time!", player.talkingNpc, "Thurgo");
			player.nextChat = 0;
		break;
		
		case 674:
		if(player.getItemAssistant().playerHasItem(667, 1)) {
			sendNpcChat1("You should bring the Squire that sword.", player.talkingNpc, "Thurgo");
			player.nextChat = 0;
		} else {
			sendNpcChat1("Did the sword work?", player.talkingNpc, "Thurgo");
			player.nextChat = 675;
		}
		break;
		
		case 675:
			sendPlayerChat2("I've seemed to have lost my sword.", "Can you make me another?");
			player.nextChat = 676;
		break;
			
		case 676:
		if (player.getItemAssistant().playerHasItem(2351, 2) && player.getItemAssistant().playerHasItem(668, 1)){
			sendNpcChat1("Sure, just let me see that blurite, and iron bars.", player.talkingNpc, "Thurgo");
			player.nextChat = 677;
		} else {
			sendNpcChat2("Sure, but you need to get more", "blurite ore, and iron bars.", player.talkingNpc, "Thurgo");
			player.nextChat = 0;
		}
		break;
		case 677:
			sendStatement("You give the bluerite ore and two bars to Thurgo");
			player.getItemAssistant().deleteItem(2351, 1);
			player.getItemAssistant().deleteItem(2351, 1);
			player.getItemAssistant().deleteItem(668, 1);
			player.nextChat = 678;
			break;	
		case 678:
			sendStatement("Thurgo starts to make the sword");
			player.nextChat = 679;
			break;
		case 679:
			sendStatement("Thurgo hands you the sword");
			player.getItemAssistant().addItem(667, 1);
			player.nextChat = 680;
			break;
		case 680:
			sendPlayerChat1("Thank you very much!");
			player.nextChat = 681;
			break;
		case 681:
			sendNpcChat1("Just remember to call in with more pie some time!", player.talkingNpc, "Thurgo");
			player.nextChat = 0;
		break;

		// back to squire
		case 682:
			sendPlayerChat1("I have retrieved your sword for you.");
			player.nextChat = 683;
			break;
		case 683:
			sendNpcChat2("Thank you, thank you, thank you! I was seriously", "worried I would have to own up to Sir Vyvin!", player.talkingNpc, "Squire");
			player.nextChat = 684;
			break;
		case 684:
			sendStatement("You give the sword to the squire.");
			player.getItemAssistant().deleteItem2(667, 1);
			player.knightS = 8;
			player.nextChat = 685;
			break;
		case 685:
			QuestRewards.knightsReward(player);
			break;	

		case 908:
			sendPlayerChat1("Hello there " + NpcHandler.getNpcListName(player.talkingNpc) + "!");
			player.nextChat = 909;
			break;
		case 909:
			sendNpcChat1("Meeeooow.", npcId,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;
		case 910:
			sendOption3("Pet", "Catch Rat", "Shoo Away");
			player.dialogueAction = 222;
			player.nextChat = 0;
			break;
		case 911:
			sendStatement("You pet your cat.");
			// Server.npcHandler.startAnimation(9166, client.rememberNpcIndex);
			// client.startAnimation(9087);
			NpcHandler.npcs[player.rememberNpcIndex].forceChat("Meow!");
			player.nextChat = 0;
			break;
		case 912:
			player.getDialogueHandler().sendStatement(
					"Catching rats is currently disabled.");
			player.nextChat = 0;
			break;
		case 913:
			sendStatement("You shoo your cat away.");
			if (NpcHandler.npcs[player.rememberNpcIndex].npcType >= 761
					&& NpcHandler.npcs[player.rememberNpcIndex].npcType >= 766)
				// client.ratsCaught = 0;
				NpcHandler.npcs[player.rememberNpcIndex].absX = 0;
			NpcHandler.npcs[player.rememberNpcIndex].absY = 0;
			NpcHandler.npcs[player.rememberNpcIndex] = null;
			player.summonId = 0;
			player.hasNpc = false;
			player.nextChat = 0;
			break;

		case 1000:
			sendNpcChat1("Is it nice and tidy round the back now?",
					player.talkingNpc, "Wydin");
			player.pirateTreasure = 2;
			player.nextChat = 1001;
			break;
		case 1001:
			sendOption4("Yes, can I work out front now?",
					"Yes, are you going to pay me yet?",
					"No it's a complete mess", "Can I buy something please?");
			player.dialogueAction = 69;
			break;
		case 1002:// 9179
			sendNpcChat1("Not yet.", player.talkingNpc, "Wydin");
			player.nextChat = 0;
			break;
		case 1003:// 9180
			sendPlayerChat1("No it's a complete mess");
			player.nextChat = 0;
			break;
		case 1004:// 9181
			sendPlayerChat1("Can I buy something please?");
			player.getShopAssistant().openShop(34);
			player.nextChat = 0;
			break;
		case 1005:// 9178
			sendPlayerChat1("Yes can I work out front now?");
			player.nextChat = 1006;
			break;
		case 1006:
			sendNpcChat1("No I'm the person who works here.",
					player.talkingNpc, "Wydin");
			player.nextChat = 0;
			break;
		case 1007:
			sendStatement("You find a hole. Would you like to enter it?");
			player.nextChat = 1008;
			break;
		case 1008:
			sendOption2("Yes", "No");
			player.dialogueAction = 70;
			break;
		case 1009:
			sendPlayerChat1("Yes.");
			player.getPlayerAssistant().movePlayer(1761, 5192, 0);
			player.nextChat = 0;
			break;
		case 1011:
			sendNpcChat2("How dare you try to take dangerous equipment?",
					"Come back when you have left it all behind.",
					player.talkingNpc, "Monk of Entrana");
			player.nextChat = 0;
			break;
		case 1012:
			sendNpcChat2("You even defeated TzTok-Jad, I am most impressed!",
					"Please accept this gift as a reward.", player.talkingNpc,
					"Tzhaar-Mej-Tal");
			player.nextChat = 0;
			break;

		/** Bank Settings **/
		case 1013:
			if (SkillHandler.isSkilling(player)) {
				return;
			}
			sendNpcChat1("Good day. How may I help you?", player.talkingNpc, "Banker");
			player.nextChat = 1014;
			break;
		case 1014:// bank open done, this place done, settings done, to do
					// delete pin
			sendOption3("I'd like to access my bank account, please.", "I'd like to check my my P I N settings.", "What is this place?");
			player.dialogueAction = 251;
			break;
		/** What is this place? **/
		case 1015:
			sendPlayerChat1("What is this place?");
			player.nextChat = 1016;
			break;
		case 1016:
			sendNpcChat2("This is the bank of " + Constants.SERVER_NAME + ".", "We have many branches in many towns.", player.talkingNpc, "Banker");
			player.nextChat = 0;
			break;
		/**
		 * Note on P I N. In order to check your "Pin Settings. You must have
		 * enter your Bank Pin first
		 **/
		/** I don't know option for Bank Pin **/
		case 1017:
			sendStartInfo("Since you don't know your P I N, it will be deleted in @red@3 days@bla@. If you", "wish to cancel this change, you may do so by entering your P I N", "correctly next time you attempt to use your bank.", "", "", false);
			player.nextChat = 0;
			break;

		case 1018:
			sendPlayerChat1("Can I come through this gate?");
			player.nextChat = 1019;
			break;
		case 1019:
			if (player.absX == 3267 || player.absX == 3268) {
			sendNpcChat1("You must pay a toll of 10 gold coins to pass.",
					player.talkingNpc, "Border Guard");
			player.nextChat = 1020;
			} else {
			sendNpcChat1("You need to be closer to the gate to use it.", player.talkingNpc, "Border Guard");
			player.nextChat = 0;
			}
			break;
		case 1020:
			sendOption3("Okay, I'll pay.", "Who does my money go to?",
					"No thanks, I'll walk around.");
			player.dialogueAction = 502;
			break;
		case 1022:
			sendPlayerChat1("Who does my money go to?");
			player.nextChat = 1023;
			break;
		case 1023:
			sendNpcChat2("The money goes to the city of Al-Kharid.",
					"Will you pay the toll?", player.talkingNpc, "Border Guard");
			player.nextChat = 1024;
			break;
		case 1024:
			sendOption2("Okay, I'll pay.", "No thanks, I'll walk around.");
			player.dialogueAction = 508;
			break;
		case 1025:
			sendPlayerChat1("No thanks, I'll walk around.");
			player.nextChat = 0;
			break;

		case 1026:
			if (!player.getItemAssistant().playerHasItem(995, 10)) {
				sendPlayerChat1("I haven't got that much.");
				player.nextChat = 0;
			} else {
				sendPlayerChat1("Okay, I'll pay.");
				player.nextChat = 1027;
			}
			break;

		case 1027:
			player.getDialogueHandler().sendStatement(
					"10 coins are removed from your inventory.");
			SpecialObjects.initKharid(player, player.objectId);
			player.nextChat = 0;
			break;

		/*
		 * case 1028: client.getDialogues().sendStatement(
		 * "10 coins are removed from your inventory."); client.nextChat = 0;
		 * break;
		 */

		case 1033:
			sendOption2(
					"I would like to skip tutorial Island and go to Mainland",
					"I would like to continue");
			player.dialogueAction = 132;
			break;

		case 1034:
			sendNpcChat1("You have been warned, you can't go back now.",
					player.talkingNpc, "Runescape Guide");
			player.getPlayerAssistant().movePlayer(3098, 3107, 0);
			player.nextChat = 0;
			break;

		case 1035:
			sendNpcChat1("You have successfully skipped tutorial island.",
					player.talkingNpc, "Runescape Guide");
			player.getPlayerAssistant().startTeleport(3222, 3218, 0, "modern");
			player.nextChat = 0;
			break;

		case 1036:
			sendNpcChat1("Good day to you Bwana.", player.talkingNpc,
					"Saniboch");
			player.nextChat = 1037;
			break;
		case 1037:
			sendOption4("Can I go through that door please?",
					"Where does this strange entrance lead?",
					"Good day to you too.",
					"I'm impressed, that tree is growing on that shed.");
			player.dialogueAction = 228;
			break;
		case 1038:
			sendPlayerChat1("I'm impressed, that tree is growing on that shed.");
			player.nextChat = 1039;
			break;
		case 1040:
			sendNpcChat2("My employer tells me it is an uncommon sort of tree",
					"called the Fyburglars tree.", player.talkingNpc,
					"Saniboch");
			player.nextChat = 0;
			break;
		case 1041:
			sendPlayerChat1("Good day to you too.");
			player.nextChat = 0;
			break;
		case 1042:
			sendPlayerChat1("Where does this strange entrance lead?");
			player.nextChat = 1043;
			break;
		case 1043:
			sendNpcChat3("To a huge fearsome dungeon, populated by giants and",
					"strange dogs. Adventurers come from all around to",
					"explore its depths", player.talkingNpc, "Saniboch");
			player.nextChat = 1044;
			break;
		case 1044:
			sendNpcChat2(
					"I know not what lies deeper in myself, for my skills in",
					"agility and woodcutting are inadequate.",
					player.talkingNpc, "Saniboch");
			player.nextChat = 0;
			break;
		case 1045:
			sendPlayerChat1("Can I go through that door please?");
			player.nextChat = 1046;
			break;
		case 1046:
			sendNpcChat2(
					"Most certainly, but I must charge you the sum of 875",
					"coins first.", player.talkingNpc, "Saniboch");
			player.nextChat = 1047;
			break;
		case 1047:
			sendOption3("Ok, here's 875 coins.", "Never mind.",
					"Why is it worth the entry cost?");
			player.dialogueAction = 230;
			break;
		case 1048:
			sendNpcChat1("You can't go in there without paying me!",
					player.talkingNpc, "Saniboch");
			player.nextChat = 0;
			break;
		case 1049:
			sendPlayerChat1("Never mind.");
			player.nextChat = 0;
			break;
		case 1050:
			sendPlayerChat1("Why is it worth the entry cost?");
			player.nextChat = 1051;
			break;
		case 1051:
			sendNpcChat3("It leads to a huge fearsome dungeon, populated by",
					"giants and strange dogs. Adventurers come from all",
					"around to explore its depths.", player.talkingNpc,
					"Saniboch");
			player.nextChat = 1052;
			break;
		case 1052:
			sendNpcChat3(
					"I know not what lies deeper in myself, for my skills in",
					"agility and woodcutting are inadequate, but I hear tell",
					"of even greater dangers deeper in.", player.talkingNpc,
					"Saniboch");
			player.nextChat = 0;
			break;
		case 1053:
			if (player.getItemAssistant().playerHasItem(995, 875)) {
				sendPlayerChat1("Ok, here's 875 coins.");
				player.nextChat = 1054;
			} else {
				sendPlayerChat1("I don't have the money at the moment.");
				player.nextChat = 1057;
			}
			break;
		case 1054:
			sendStatement("You give Saniboch 875 coins.");
			player.nextChat = 1055;
			player.getItemAssistant().deleteItem2(995, 875);
			player.hasPaidBrim = true;
			break;
		case 1055:
			sendNpcChat2("Many thanks. You may now pass the door. May your",
					"death be a glorious one!", player.talkingNpc, "Saniboch");
			player.nextChat = 0;
			break;
		case 1056:
			sendPlayerChat1("I don't have the money at the moment.");
			player.nextChat = 1057;
			break;
		case 1057:
			sendNpcChat2(
					"Well this is a dungeon for the more wealthy discerning",
					"adventurer, be gone with you riff raff.",
					player.talkingNpc, "Saniboch");
			player.nextChat = 1058;
			break;
		case 1058:
			sendPlayerChat2(
					"But you don't even have clothes, how can you seriously",
					"call anyone riff raff");
			player.nextChat = 1059;
			break;
		case 1059:
			sendNpcChat1("Hummph.", player.talkingNpc, "Saniboch");
			player.nextChat = 0;
			break;
		case 1226:
			sendNpcChat2("You already have a slayer task",
					"please finish it talk to me again.", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;
		case 1227:
			sendNpcChat1(
					"You already have an easier slayer task. Please finish it then talk to me again.",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;
		case 1228:
			sendNpcChat1("'Ello and what are you after then?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1229;
			break;
		case 1229:
			sendOption4("I need another assignement.",
					"Where is the location of my task?",
					"I would like to view your shop.",
					"I would like to cancel or remove my task.");
			player.dialogueAction = 142;
			break;
		case 1231:
			sendOption2("I want to cancel my current task.",
					"I want to remove my task for ever.");
			player.dialogueAction = 143;
			break;
		case 1232:
			player.getSlayer().cancelTask();
			player.getPlayerAssistant().closeAllWindows();
			player.nextChat = 0;
			break;
		case 1233:
			player.getSlayer().removeTask();
			player.getPlayerAssistant().closeAllWindows();
			player.nextChat = 0;
			break;
		case 1234:
			player.getSlayer().generateTask();
			break;
		case 1235:
			if (player.getSlayer().hasTask()) {
				sendNpcChat1("Your Slayer Task is located at "
						+ player.getSlayer().getLocation(player.slayerTask)
						+ ".", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			} else {
				sendNpcChat2("You don't have a slayer task",
						"if you wish to get one talk to a slayer mask.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;
		case 1236:
			player.getShopAssistant().openShop(109);
			player.nextChat = 0;
			break;
		case 1237:
			sendNpcChat2("You have been assigned " + player.taskAmount + " "
					+ player.getSlayer().getTaskName(player.slayerTask) + ",",
					"Good luck " + player.playerName + ".", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;

		case 1300:
			sendNpcChat2(
					"Good afternoon, sir. In need of a haircut or shave, are",
					"we?", player.talkingNpc, "Hairdresser");
			player.nextChat = 1301;
			break;

		case 1301:
			sendOption3("A haircut, please.", "A shave, please.",
					"No, thank you.");
			player.dialogueAction = 1301;
			break;

		case 1302:// first option
			sendPlayerChat1("A haircut, please.");
			player.nextChat = 1303;
			break;

		case 1303:
			sendNpcChat1("Certainly, sir. The fee will be 2,000 coins.",
					player.talkingNpc, "Hairdresser");
			player.nextChat = 1304;
			break;

		case 1304:
			if (player.getItemAssistant().playerHasItem(995, 2000)) {
				sendNpcChat2("Please select a hairstyle you would",
						"like from this brochure.", 598, "Hairdresser");
				player.nextChat = 1305;
			} else {
				sendNpcChat2("It looks like you don't have 2,000 coins,",
						"please revisit when you do.", 598, "Hairdresser");
				player.nextChat = 0;
			}
			break;

		case 1305:
			player.getPlayerAssistant().showInterface(2653); // hairstyle
																// interface
			break;
		// end of hairstyle cut.

		case 1306: // dialogue option 3
			sendPlayerChat1("No, thank you.");
			player.nextChat = 1307;
			break;

		case 1307:
			sendNpcChat1("Very well. Come back if you change your mind.",
					player.talkingNpc, "Hairdresser");
			player.nextChat = 0;
			break;
		// END
		case 1308: // start of shaving
			sendPlayerChat1("A shave, please.");
			player.nextChat = 1309;
			break;

		case 1309:
			sendNpcChat1("Certainly, sir. The fee will be 2,000 coins.",
					player.talkingNpc, "Hairdresser");
			player.nextChat = 1310;
			break;

		case 1310:
			if (player.getItemAssistant().playerHasItem(995, 2000)) {
				sendNpcChat2("Please select a beard and color you would",
						"like from this brochure.", player.talkingNpc,
						"Hairdresser");
				player.nextChat = 1311;
			} else {
				sendNpcChat2("It looks like you don't have 2,000 coins,",
						"please revisit when you do.", player.talkingNpc,
						"Hairdresser");
				player.nextChat = 0;
			}
			break;
		case 1311:
			player.getPlayerAssistant().showInterface(2007); // hair/beard
																// interface
			player.nextChat = 0;
			break;
		case 1312:
			sendNpcChat1(
					"What sort of dye would you like? Red, yellow, or blue?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1313;
			break;
		case 1313:
			sendOption3("Red Dye", "Yellow Dye", "Blue Dye");
			player.dialogueAction = 144;
			break;
		case 1314:// red 1763, yellow 1765, blue 1767
			if (player.getItemAssistant().playerHasItem(1951, 3)
					&& player.getItemAssistant().playerHasItem(995, 5)) {
				player.getItemAssistant().deleteItem2(1951, 3);
				player.getItemAssistant().addOrDropItem(1763, 1);
				sendPlayerChat1("Red Dye Please.");
				player.nextChat = 0;
			} else {
				sendNpcChat1(
						"You need 5 coins and 3 redberries to make red dye.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;
		case 1315:// red 1763, yellow 1765, blue 1767
			if (player.getItemAssistant().playerHasItem(1957, 2)
					&& player.getItemAssistant().playerHasItem(995, 5)) {
				player.getItemAssistant().deleteItem2(1957, 2);
				player.getItemAssistant().addOrDropItem(1765, 1);
				sendPlayerChat1("Yellow Dye Please.");
				player.nextChat = 0;
			} else {
				sendNpcChat1(
						"You need 5 coins and 2 onions to make yellow dye.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;
		case 1316:// red 1763, yellow 1765, blue 1767
			if (player.getItemAssistant().playerHasItem(1793, 2)
					&& player.getItemAssistant().playerHasItem(995, 5)) {
				player.getItemAssistant().deleteItem2(1793, 2);
				player.getItemAssistant().addOrDropItem(1767, 1);
				sendPlayerChat1("Blue Dye Please.");
				player.nextChat = 0;
			} else {
				sendNpcChat1(
						"You need 5 coins and 2 woad leaves to make blue dye.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;
		case 1317:
			sendNpcChat1("Welcome to the Guild of Master Craftsman.",
					player.talkingNpc, "Master Crafter");
			player.nextChat = 0;
			break;

		/* Slayer Gem */

		case 1318:
			if (player.getSlayer().hasTask()) {
				sendNpcChat3(
						"Hello " + player.playerName + ".",
						"You currently need to kill "
								+ player.taskAmount
								+ " more "
								+ player.getSlayer().getTaskName(
										player.slayerTask) + ".",
						"in the "
								+ player.getSlayer().getLocation(
										player.slayerTask) + ".",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have a slayer task.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
			}
			break;
		case 1319:
			if (player.getSlayer().hasTask()) {
				sendNpcChat1(
						"I am "
								+ player.getSlayer().getSlayerMaster(
										player.SlayerMaster) + ".",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have a slayer task.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
			}
			break;
		case 1320:
			if (player.getSlayer().hasTask()) {
				sendNpcChat2(
						"Hello " + player.playerName + ".",
						"I am located in "
								+ player.getSlayer().getMasterLocation(
										player.SlayerMaster) + ".",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have a slayer task.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.SlayerMaster));
			}
			break;
		case 1321:
				sendNpcChat1("You have " + player.slayerPoints + " slayer points nice job!", player.talkingNpc, NpcHandler.getNpcListName(player.SlayerMaster));
				player.nextChat = 0;
			break;

		case 1322:
			sendNpcChat2("Hello " + player.playerName + ".", "Are you interested in buying anything?", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1323;
			break;

		case 1323:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 146;
			break;

		case 1324:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1325:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1326;
			break;

		case 1326:
			for (Shop shop : Shop.values()) {
				if (shop != null) {
					if (shop.getNpc() == player.talkingNpc) {
						player.getShopAssistant().openShop(shop.getShop());
						RandomEventHandler.addRandom(player);
						player.nextChat = 0;
					}
				}
			}
			break;

		case 1329:
			sendNpcChat2("Hello " + player.playerName + ".",
					"Are you interested in buying anything?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1330;
			break;

		case 1330:
			sendOption3("Yes please.", "No Thanks.",
					"I have a frog token I would like to exchange.");
			player.dialogueAction = 148;
			break;

		case 1331:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1332:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1334;
			break;

		case 1333:
			sendOption2("A frog mask please!", "A frog outfit, please!");
			player.dialogueAction = 149;
			break;

		case 1334:
			player.getShopAssistant().openShop(12);
			RandomEventHandler.addRandom(player);
			break;

		case 1335:
			if (player.getItemAssistant().playerHasItem(6183, 1)) {
				sendPlayerChat1("A frog mask please!");
				player.getItemAssistant().deleteItem2(6183, 1);
				player.getItemAssistant().addOrDropItem(6188, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have any frog tokens.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1336:
			if (player.getItemAssistant().playerHasItem(6183, 1)
					&& player.playerAppearance[0] == 0) {
				sendPlayerChat1("A frog prince outfit, please!");
				player.getItemAssistant().deleteItem2(6183, 1);
				player.getItemAssistant().addOrDropItem(6184, 1);
				player.getItemAssistant().addOrDropItem(6185, 1);
				player.nextChat = 0;
			} else if (player.getItemAssistant().playerHasItem(6183, 1)
					&& player.playerAppearance[0] == 1) {
				sendPlayerChat1("A frog princess outfit, please!");
				player.getItemAssistant().deleteItem2(6183, 1);
				player.getItemAssistant().addOrDropItem(6186, 1);
				player.getItemAssistant().addOrDropItem(6187, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have any frog tokens.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1337:
			sendNpcChat1("Hello, would you like some rope?", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1338;
			break;

		case 1338:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 166;
			break;

		case 1339:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1340:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1341;
			break;

		case 1341:
			sendOption3("I will give you 15 coins for 1 rope.",
					"I will give you 4 balls of wool for 1 rope.",
					"Never mind.");
			player.dialogueAction = 167;
			break;

		case 1342:
			sendPlayerChat1("Never mind.");
			player.nextChat = 0;
			break;

		case 1343:
			if (player.getItemAssistant().playerHasItem(995, 15)) {
				player.getItemAssistant().deleteItem2(995, 15);
				player.getItemAssistant().addOrDropItem(954, 1);
				sendPlayerChat1("I will give you 15 coins for 1 rope.");
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't even have 15 coins.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1344:
			if (player.getItemAssistant().playerHasItem(1759, 4)) {
				player.getItemAssistant().deleteItem2(1759, 4);
				player.getItemAssistant().addOrDropItem(954, 1);
				sendPlayerChat1("I will give you 4 balls of wool for 1 rope.");
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't even have 4 balls of wool.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1345:
			sendNpcChat1(
					"Hello, would you like to buy climbing boots for 12 gp?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1346;
			break;

		case 1346:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 170;
			break;

		case 1347:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1348:
			if (player.getItemAssistant().playerHasItem(995, 12)) {
				player.getItemAssistant().deleteItem2(995, 12);
				player.getItemAssistant().addOrDropItem(3105, 1);
				sendPlayerChat1("Yes please.");
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 12 gp to buy these.", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1349:
			if (player.absY > 3485 && player.absY < 3489
					&& player.hasPaid != true) {
				sendNpcChat2(
						"Hello, are you interested in traveling up the river",
						"for 50 coins?", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 1350;
			} else if (player.hasPaid != true) {
				sendNpcChat2(
						"Hello, are you interested in traveling back down the river",
						"for 50 coins?", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 1350;
			} else if (player.hasPaid == true && player.absY > 3485
					&& player.absY < 3489) {
				sendNpcChat1(
						"Hello, are you interested in a free ride up the river?",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 1350;
			} else if (player.hasPaid == true) {
				sendNpcChat1(
						"Hello, are you interested in a free back down the river?",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 1350;
			}
			break;

		case 1350:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 171;
			break;

		case 1351:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1352:
			if (player.getItemAssistant().playerHasItem(995, 50)
					&& player.absY > 3485 && player.absY < 3489) {
				player.getItemAssistant().deleteItem2(995, 50);
				sendPlayerChat1("Yes please.");
				player.getPlayerAssistant().startTeleport(2358, 3640, 0,
						"modern");
				player.hasPaid = true;
				player.nextChat = 0;
			} else if (player.getItemAssistant().playerHasItem(995, 50)) {
				player.getItemAssistant().deleteItem2(995, 50);
				sendPlayerChat1("Yes please.");
				player.getPlayerAssistant().startTeleport(2367, 3488, 0,
						"modern");
				player.hasPaid = true;
				player.nextChat = 0;
			} else if (player.hasPaid == true && player.absY > 3485
					&& player.absY < 3489) {
				sendPlayerChat1("Yes please.");
				player.getPlayerAssistant().startTeleport(2358, 3640, 0,
						"modern");
			} else if (player.hasPaid == true) {
				sendPlayerChat1("Yes please.");
				player.getPlayerAssistant().startTeleport(2367, 3488, 0,
						"modern");
			}
			break;

		case 1353:
			sendNpcChat1("Would you like to travel to Keldagrim?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1354;
			break;

		case 1354:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 172;
			break;

		case 1355:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1357;
			break;

		case 1356:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1357:
			player.getPlayerAssistant().startTeleport(2827, 10214, 0, "modern");
			player.nextChat = 0;
			break;

		case 1358:
			sendNpcChat1("Hello, would you like some monk robes?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1359;
			break;

		case 1359:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 173;
			break;

		case 1360:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1362;
			break;

		case 1361:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1362:
			if (player.getItemAssistant().playerHasItem(542)
					|| player.getItemAssistant().playerHasItem(544)
					|| player.playerEquipment[player.playerLegs] == 542
					|| player.playerEquipment[player.playerChest] == 544) {
				sendNpcChat1("You already have some monks robes",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			} else {
				player.getItemAssistant().addOrDropItem(542, 1);
				player.getItemAssistant().addOrDropItem(544, 1);
				player.nextChat = 0;
			}
			break;

		case 1363:
			sendNpcChat4(
					"You already have the slayer task of " + player.slayerTask
							+ " and have",
					" " + player.taskAmount + " left to kill",
					"are you sure you would like an easier task?",
					"it will be something much simpiler than your previous task.",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1364;
			break;

		case 1364:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 174;
			break;

		case 1365:
			sendPlayerChat1("No thanks I would like to stay with my task.");
			player.nextChat = 0;
			break;

		case 1366:
			sendPlayerChat1("Yes please I would like an easier task.");
			player.nextChat = 1367;
			break;

		case 1367:
			player.getSlayer().generateTask();
			break;

		case 1368:
			sendNpcChat2("You have been assigned " + player.taskAmount + " "
					+ player.getSlayer().getTaskName(player.slayerTask) + ",",
					"Good luck " + player.playerName + ".", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;

		case 1369:
			sendNpcChat2("Hello, " + player.playerName
					+ " would you like to change your appearance?", "for 3k?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1370;
			break;

		case 1370:
			sendOption2("Yes please.", "No thanks, I'm not interested.");
			player.dialogueAction = 176;
			break;

		case 1371:
			sendPlayerChat1("No thanks, I'm not interested.");
			player.nextChat = 0;
			break;

		case 1372:
			if (player.getItemAssistant().playerHasItem(995, 3000)) {
				sendPlayerChat1("Yes please.");
				player.getPlayerAssistant().showInterface(3559);
				player.canChangeAppearance = true;
				player.getItemAssistant().deleteItem2(995, 3000);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough gold to do that.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 1373:
			sendNpcChat2("Hello " + player.playerName + ".",
					"Are you interested in buying anything?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 1374;
			break;

		case 1374:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 177;
			break;

		case 1375:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 1376:
			sendPlayerChat1("Yes please.");
			player.nextChat = 1377;
			break;

		case 1377:
			if (player.npcType == 537) {
				player.getShopAssistant().openShop(124);
			} else if (player.npcType == 536) {
				player.getShopAssistant().openShop(125);
			}
			RandomEventHandler.addRandom(player);
			player.nextChat = 0;
			break;

		case 1378:
			sendNpcChat2("Hello " + player.playerName + ".",
					"Are you interested in buying a candle for 1k?",
					player.talkingNpc, "Candle Seller");
			player.nextChat = 1379;
			break;

		case 1379:
			sendOption2("Yes please.", "No thank you.");
			player.dialogueAction = 179;
			break;

		case 1380:
			if (player.getItemAssistant().playerHasItem(995, 1000)) {
				sendPlayerChat1("Yes please.");
				player.getItemAssistant().deleteItem2(995, 1000);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins to buy a candle.",
						player.talkingNpc, "Candle Seller");
				player.nextChat = 0;
			}
			break;

		case 1381:
			sendPlayerChat1("No thank you.");
			player.nextChat = 0;
			break;

		case 2995:
			player.canWalkTutorial = false;
			sendStatement2(
					"Before you start if you would like to skip Tutorial Island,",
					"now is your chance. If you skip you will be brought to mainland.");
			player.nextChat = 2996;
			break;

		case 2996:
			sendStatement2(
					"If you don't want to skip Tutorial Island you can stay here.",
					"Would you like to skip Tutorial Island, or stay here?");
			player.nextChat = 2997;
			break;

		case 2997:
			sendOption2(
					"I would like to skip Tutorial Island and go to Mainland.",
					"I would like to stay here and complete Tutorial island.");
			player.dialogueAction = 151;
			break;

		case 2998:
			sendPlayerChat1("Yes I would like to skip Tutorial Island.");
			player.nextChat = 3115;
			break;

		case 2999:
			if (!player.getItemAssistant().playerHasItem(1549) && player.vampSlayer == 3) {
				player.getItemAssistant().addOrDropItem(1549, 1);
				sendPlayerChat1("Thank you, I will be more careful next time.");
				player.nextChat = 0;
			}
			break;

		case 3000: // first part. Entering the tutorial island
			player.getActionSender().chatbox(6180); // displays
														// client.getPacketDispatcher().chatbox
			// client.getPlayerAssistant().tutorialIslandInterface(0, 0); //
			// progress bar + tutorial int
			player.getActionSender().createArrow(1, 1);
			chatboxText(
					player,
					"To start the tutorial use your left mouse button to click on the",
					"" + Constants.SERVER_NAME
							+ " in this room. He is indicated by a flashing",
					"yellow arrow above his head. If you can't see him, use your",
					"keyboard's arrow keys to rotate the view.",
					"@blu@Getting started");
			player.getActionSender().chatbox(6179); // displays
														// client.getPacketDispatcher().chatbox
			player.tutorialProgress = 0;
			player.canWalkTutorial = true;
			player.nextChat = 0;
			break;
		/*
		 * RS guide section
		 */
		case 3001: // RS GUIDE
			sendNpcChat2(
					"Greetings! I see you are a new arrival to the land. My",
					"job is to welcome all new visitors. So welcome!",
					player.talkingNpc, "Runescape Guide");
			player.nextChat = 3002;
			break;
		case 3002: // 2
			sendNpcChat2("You have already learned the first thing needed to",
					"succeed in this world: talking to other people!",
					player.talkingNpc, "Runescape Guide");
			player.nextChat = 3003;
			break;

		case 3003: // 3
			sendNpcChat3(
					"You will find many inhabitants of this world have useful",
					"things to say to you. By clicking on them with your",
					"mouse you can talk to them.", player.talkingNpc,
					"Runescape Guide");
			player.nextChat = 3004;
			break;
		case 3004:
			sendNpcChat4(
					"I would also suggest reading through some of the",
					"supporting information on the website. There you can",
					"find the Knowledge Base, which contains all the",
					"additional information you're ever likely to need. It also",
					player.talkingNpc, "Runescape Guide");
			player.nextChat = 3005;
			break;
		case 3005:
			sendNpcChat2("contains maps and helpful tips to help you on your",
					"journey.", player.talkingNpc, "Runescape Guide");
			player.nextChat = 3006;
			break;

		case 3006:// show tab wrentch
			clearChatBoxText(player); // call this every time there is new
										// client.getPacketDispatcher().chatboxtext
										// coming up
			sendNpcChat2(
					"You will notice a flashing icon of a wrench, please click",
					"on this to continue the tutorial.", player.talkingNpc,
					"Runescape Guide");
			player.getActionSender().setSidebarInterface(11, 904); // wrench
																		// tab
			player.getActionSender().flashSideBarIcon(-11);
			player.nextChat = 3007;
			break;

		case 3007: // Player controls
			player.getPlayerAssistant().removeAllWindows();
			chatboxText(
					player,
					"Please click on the flashing wrench icon found at the bottom",
					"right of your screen. This will display your player controls.",
					"", "", "Player controls");
			player.nextChat = 0;
			break;

		case 3008: // Glad your making progress
			sendNpcChat1("I'm glad you're making progress!", player.talkingNpc,
					"Runescape Guide");
			player.nextChat = 3009;
			break;

		case 3009:
			sendNpcChat2("To continue the tutorial go through that door over",
					"there and speak to your first instructor!",
					player.talkingNpc, "Runescape Guide");
			player.nextChat = 3010;
			break;
		case 3010:
			player.tutorialProgress = 2;
			player.getActionSender().chatbox(6180);
			player.getPlayerAssistant().removeAllWindows();
			chatboxText(
					player,
					"You can interact with many items of scenery by simply clicking",
					"on them. Right clicking will also give more options. Feel free to",
					"try it with the things in this room, then click on the door",
					"indicated with the yellow arrow to go through to the next instructor.",
					"Interacting with scenery");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3098, 3107, player.getH(),
					2);
			player.nextChat = 0;
			break;

		case 3011: // door handling
			player.getPlayerAssistant().removeAllWindows();
			chatboxText(
					player,
					"Follow the path to find the next instructor. Clicking on the",
					"ground will walk you to that point. Talk to the Survival Expert by",
					"the pond to the continue the tutorial. Remember you can rotate",
					"the view by pressing the arrow keys.", "Moving around");
			player.getActionSender().createArrow(1, 2);
			// client.getPacketDispatcher().tutorialIslandInterface(5, 2);
			// // progress bar + tutorial int
			player.nextChat = 0;
			break;
		// end end end end end

		/*
		 * Survival Expert. Second part.
		 */
		case 3012: // Survival Expert
			sendNpcChat4(
					"Hello there, newcomer. My name is Brynna. My job is",
					"to teach you a few survival tips and tricks. First off",
					"we're going to start with the most basic survival skill of",
					"all: making a fire.", player.talkingNpc, "Survival Expert");
			player.nextChat = 3013;
			break;

		case 3013: // giving bronze and tinder
			sendItemChat2(player, "",
					"The Survival Guide gives you a @blu@tinderbox @bla@and a",
					"@blu@bronze axe!", 590, 150);
			player.getItemAssistant().addOrDropItem(590, 1);
			player.getItemAssistant().addOrDropItem(1351, 1);
			player.nextChat = 0;
			chatboxText(
					player,
					"Click on the flashing backpack icons to the right hand side of",
					"the main window to view your inventory. Your inventory is a list",
					"of everything you have on your backpack.", "",
					"Viewing the items that you were given");
			player.getActionSender().setSidebarInterface(3, 3213);// sends
																		// interface
			player.getActionSender().flashSideBarIcon(-3); // flashes
																// inventory
			player.tutorialProgress = 3;
			break;
		case 3014: // finished cutting tree
			sendItemChat1(player, "", "You got some logs", 1511, 150);
			PlayerAssistant.removeHintIcon(player);
			player.nextChat = 3015;
			break;
		case 3015: // firemaking time
			player.getPlayerAssistant().removeAllWindows();
			chatboxText(
					player,
					"Well done! You managed to cut some logs from the tree! Next,",
					"use the tinderbox in your inventory to light the logs.",
					"First click on the tinderbox to use it.",
					"Then click on the logs in your inventory to light them.",
					"Making a fire");
			// client.getPacketDispatcher().createArrow(1, 2); // sends to
			// Survival Expert
			player.tutorialProgress = 4;
			break;

		case 3016: // firemaking done skill tab flashing now.
			// client.getPlayerAssistant().removeAllWindows();
			chatboxText(
					player,
					"Click on the flashing bar graph icon near the inventory button",
					"to see your skill stats.", "", "",
					"You gained some experience.");
			player.getActionSender().flashSideBarIcon(-1); // flashes
																// skill
			player.getActionSender().setSidebarInterface(1, 3917); // sets
																		// the
																		// skill
																		// tab
			player.nextChat = 3017;
			break;

		case 3017: // survival expert part 2
			sendNpcChat3("Well done! Next we need to get some food in our",
					"bellies. We'll need something to cook. There are shrimp",
					"in the pond there so let's catch and cook some.",
					player.talkingNpc, "Survival Expert");
			player.nextChat = 3018;

			break;

		case 3018:
			sendItemChat1(player, "",
					"The Survival Guide gives you a @blu@net!", 303, 150);
			player.getItemAssistant().addOrDropItem(303, 1);
			player.nextChat = 0;
			chatboxText(
					player,
					"Click on the sparkling fishing spot indicated by the flashing",
					"arrow. Remember, you can check your inventory by clicking the",
					"backpack icon.", "", "Catch some Shrimp");
			player.getActionSender().createArrow(3101, 3092, player.getH(),
					2);
			player.tutorialProgress = 6;
			break;

		case 3019: // Cooking the shrimp
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Now you have caught some shrimp let's cook it. First light a",
					"fire, chop down a tree and then use the tinderbox on the logs.",
					"If you've lost your axe or tinderbox, Brynna will give you",
					"another.", "Cooking your shrimp.");
			player.getActionSender().chatbox(6179);
			break;
		// END

		/*
		 * Finding next tutor FAT BITCH
		 */
		case 3020: // tutorial PROGRESS = 7
			// client.getPacketDispatcher().tutorialIslandInterface(15,4);
			// // 15 percent
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Talk to the chef indicated. He will teach you the more advanced",
					"aspects of Cooking such as combining ingredients. He will also",
					"teach you about your music player menu as well.", "",
					"Find your next instructor");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3078, 3084, player.getH(),
					2);
			break;

		case 3021: // start of dialogue.
			sendNpcChat3(
					"Ah! Welcome newcomer. I am the Master Chef Leo. It",
					"is here I will teach you how to cook food truly fit for a",
					"king.", player.talkingNpc, "Master Chef");
			player.nextChat = 3022;
			break;

		case 3022:
			sendPlayerChat2(
					"I already know how to cook. Brynna taught me just", "now.");
			player.nextChat = 3023;
			break;
		case 3023:
			sendNpcChat3("Hahahahahaha! You call THAT cooking? Some shrimp",
					"on an open log fire? Oh no, no, no. I am going to",
					"teach you the fine art of cooking bread.",
					player.talkingNpc, "Master Chef");
			player.nextChat = 3024;
			break;
		case 3024:
			sendNpcChat2("And no fine meal is complete without good music, so",
					"we'll cover that while you're here too.",
					player.talkingNpc, "Master Chef");
			player.nextChat = 3025;
			break;

		case 3025: // he gives u bucket of water etc TTUOTRIAL PROG 8
			sendItemChat2(
					player,
					"",
					"The Cooking Guide gives you a @blu@bucket of water@bla@ and a",
					"@blu@pot of flour!", 1933, 150);
			player.getItemAssistant().addOrDropItem(1933, 1);
			player.getItemAssistant().addOrDropItem(1929, 1);
			player.nextChat = 0;
			chatboxText(
					player,
					"This is the base for many of the meals. To make dough we must",
					"mix flour and water. First right click the bucket of water and",
					"select use, then left click on the pot of flour.", "",
					"Making dough");
			PlayerAssistant.removeHintIcon(player);
			player.tutorialProgress = 8;

			break;
		case 3026: // cooking dough
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Now you have made dough, you can cook it. To cook the dough",
					"use it with the range shown by the arrow. If you lose your",
					"dough, talk to Leo - he will give you more ingredients.",
					"", "Cooking dough");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3075, 3081, player.getH(),
					2);

			player.nextChat = 0;
			break;

		case 3037: // new tutorial prog
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Well done! Your first loaf of bread. As you gain experience in",
					"Cooking you will be able to make other things like pies, cakes",
					"and even kebabs. Now you've got the hang of cooking, let's",
					"move on. Click on the flashing icon in the bottom right.",
					"Cooking dough");
			player.getActionSender().chatbox(6179);
			PlayerAssistant.removeHintIcon(player);
			// client.getPacketDispatcher().tutorialIslandInterface(20, 5);
			player.getActionSender().setSidebarInterface(13, 962); // sets
																		// music
			player.getActionSender().flashSideBarIcon(-13);
			player.tutorialProgress = 9;
			player.nextChat = 0;
			break;

		case 3038: // Emotes
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"",
					"Now, how about showing some feelings? You will see a flashing",
					"icon in the shape of a person. Click on that to access your",
					"emotes.", "Emotes");
			player.getActionSender().chatbox(6179);
			PlayerAssistant.removeHintIcon(player);
			// client.getPacketDispatcher().tutorialIslandInterface(25, 6);
			// // 25 percent now
			player.getActionSender().setSidebarInterface(12, 147); // run
																		// tab
			player.getActionSender().flashSideBarIcon(-12);
			player.nextChat = 0;
			break;
		case 3039: // running
			player.tutorialProgress = 11;
			player.getActionSender().chatbox(6180);
			chatboxText(player,
					"It's only a short distance to the next guide.",
					"Why not try running there? Start by opening the player",
					"settings, that's the flashing icon of a wrench.", "",
					"Running");
			player.getActionSender().chatbox(6179);
			player.getActionSender().flashSideBarIcon(-12);
			player.getActionSender().createArrow(3086, 3126, player.getH(),
					2);
			player.nextChat = 0;
			break;
		case 3040:
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"In this menu you will see many options. At the bottom in the",
					"middle is a button with the symbol of a running shoe. You can",
					"turn this button on or off to select run or walk. Give it a go,",
					"click on the run button now.", "Running");
			player.getActionSender().chatbox(6179);
			player.nextChat = 0;
			break;
		case 3041: // clicked on run
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Now that you have the run button turned on, follow the path",
					"until you come to the end. You may notice that the numbers on",
					"the button goes down. This is your run energy. If your run",
					"energy reaches zero, you'll stop running.",
					"Run to the next guide");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3086, 3125, player.getH(),
					2);

			player.nextChat = 0;
			break;

		/**
		 * Quest Guide
		 */
		case 3042: // entering the quest GUIDE
			player.getActionSender().createArrow(1, 4); // sends to
															// quest
															// guide
			// client.getPacketDispatcher().tutorialIslandInterface(35, 8);//30 or 35 percent
			player.getActionSender().chatbox(6180);
			chatboxText(player, "Talk with the Quest Guide.", "",
					"He will tell you all about quests.", "", "");
			player.getActionSender().chatbox(6179);
			player.tutorialProgress = 12;
			PassDoor.passThroughDoor(player, 3019, 2, 3, 0, 0, -1, 0);
			player.nextChat = 0;
			break;

		case 3043: // quest guide dialogue START
			sendNpcChat2(
					"Ah. Welcome, adventurer. I'm here to tell you all about",
					"quests. Let's start by opening the quest side panel.",
					player.talkingNpc, "Quest Guide");
			player.nextChat = 3044;
			break;

		case 3044: // Send quest tab
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(player, "Open the Quest Journal.", "",
					"Click on the flashing icon next to your inventory.", "",
					"");
			player.getActionSender().chatbox(6179);
			player.getActionSender().setSidebarInterface(2, 638); // quest
			player.getActionSender().flashSideBarIcon(-2);
			player.nextChat = 0;
			break;

		case 3045: // quest guide 2 prog 13
			sendNpcChat3(
					"Now you have the journal open. I'll tell you a bit about",
					"it. At the moment all the quests shown in red which",
					"means you have not started them yet.", player.talkingNpc,
					"Quest Guide");
			player.nextChat = 3046;

			break;

		case 3046:
			sendNpcChat4(
					"When you start a quest it will change colour to yellow",
					"and to green when you've finished. This is so you can",
					"easily see what's complete, what's started, and what's left",
					"to begin.", player.talkingNpc, "Quest Guide");
			player.nextChat = 3047;
			break;

		case 3047:
			sendNpcChat3(
					"The start of quests are easy to find. Look out for the",
					"star icons on the minimap, just like the one you should",
					"see marking my house.", player.talkingNpc, "Quest Guide");
			player.nextChat = 3048;
			break;

		case 3048:
			sendNpcChat4(
					"The quests themselves can vary greatly from collecting",
					"beads to hunting down dragons. Generally quests are",
					"started by talking to a non-player character like me,",
					"and will involve a series of tasks.", player.talkingNpc,
					"Quest Guide");
			player.nextChat = 3049;
			break;

		case 3049: // last
			sendNpcChat4(
					"There's not a lot more I can tell you about questing.",
					"You have to experience the thrill of it yourself to fully",
					"understand. You may find some adventure in the caves",
					"under my house.", player.talkingNpc, "Quest Guide");
			player.nextChat = 3050;
			break;

		case 3050: // moving on. Cave time biaches
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"",
					"It's time to enter some caves. Click on the ladder to go down to",
					"the next area.", "", "Moving on");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3088, 3119, player.getH(),
					2);
			player.nextChat = 0;
			player.tutorialProgress = 14;
			break;
		// end

		/*
		 * Start of Mining/Smithing
		 */
		case 3051:
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Next let's get you a weapon or more to the point, you can",
					"make your first weapon yourself. Don't panic, the Mining",
					"Instructor will help you. Talk to him and he'll tell you all about it.",
					"", "Mining and Smithing");
			player.getActionSender().chatbox(6179);
			// client.getPacketDispatcher().tutorialIslandInterface(40,9);
			player.getActionSender().createArrow(1, 5);
			player.nextChat = 0;
			break;

		case 3052:// mining tutor start
			sendNpcChat4("Hi there. You must be new around here. So what do I",
					"call you? Newcomer seems so impersonal and if we're",
					"going to be working together, I'd rather call you by",
					"name.", player.talkingNpc, "Mining Instructor");
			player.nextChat = 3053;
			break;

		case 3053:// mining tutor start
			sendPlayerChat1("You can call me "
					+ Misc.capitalize(player.playerName) + ".");
			player.nextChat = 3054;
			break;

		case 3054:// mining tutor start
			sendNpcChat2("Ok then, " + Misc.capitalize(player.playerName) + "."
					+ " My name is Dezzick and I'm a",
					"miner by trade. Let's prospect some of those rocks.",
					player.talkingNpc, "Mining Instructor");
			player.nextChat = 3055;
			break;

		case 3055: // prospecting
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"To prospect a mineable rock, just right click it and select the",
					"'prospect rock' option. This will tell you the type of ore you can",
					"mine from it. Try it now on one of the rocks indicated.",
					"", "Prospecting");
			player.getActionSender().chatbox(6179);
			// client.getPacketDispatcher().tutorialIslandInterface(40,9);
			player.getActionSender().createArrow(3076, 9504, player.getH(),
					2);
			player.nextChat = 0;
			player.tutorialProgress = 15;
			break;
		case 3056: // done prospecting
			sendPlayerChat2(
					"I prospected both types of rocks! One set contains tin",
					"and the other has copper ore inside.");
			player.nextChat = 3057;
			break;

		case 3057:
			sendNpcChat2(
					"Absolutely right, " + Misc.capitalize(player.playerName)
							+ "." + " These two ore types",
					"can be smelted together to make bronze.",
					player.talkingNpc, "Mining Instructor");
			player.nextChat = 3058;
			break;

		case 3058:
			sendNpcChat3(
					"So now you know what ore is in the rocks over there,",
					"why don't you have a go at mining some tin and",
					"copper? here, you'll need this to start with.",
					player.talkingNpc, "Mining Instructor");
			player.nextChat = 3060;
			break;
		case 3060:
			sendItemChat1(player, "",
					"Dezzick gives you a @blu@bronze pickaxe!", 1265, 300);
			player.getItemAssistant().addOrDropItem(1265, 1);
			player.nextChat = 0;
			chatboxText(
					player,
					"It's quite simple really. All you need to do is right click on the",
					"rock and select 'mine'. You can only mine when you have a",
					"pickaxe. So give a try: first mine one tin ore.", "",
					"Mining");
			player.getActionSender().createArrow(3076, 9504, player.getH(),
					2); // sends
						// hint
						// to
						// ore
			player.tutorialProgress = 17;
			break;

		case 3061: // furnace time
			player.tutorialProgress = 19;
			player.nextChat = 0;
			chatboxText(
					player,
					"You should now have both some copper and tin ore. So let's",
					"smelt them to make a bronze bar. To do this, right click on",
					"either tin or copper ore and select use, then left click on the",
					"furnace. Try it now.", "Smelting");
			break;
		case 3062: // smelting
			player.tutorialProgress = 20;
			player.nextChat = 0;
			player.getActionSender().chatbox(6180);
			chatboxText(player, "", "Speak to the Mining Instructor and he'll show you how to make", "it into a weapon.", "", "You've made a bronze bar!");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(1, 5);
			break;

		case 3063:
			player.nextChat = 3064;
			sendPlayerChat1("How do I make a weapon out of this?");
			break;

		case 3064:
			sendNpcChat2("Okay, I'll show you how to make a dagger out of it.",
					"You'll be needing this...", player.talkingNpc,
					"Mining Instructor");
			player.nextChat = 3065;
			break;

		case 3065: // giving you the hammer
			sendItemChat1(player, "", "Dezzick gives you a @blu@hammer!", 2347, 300);
			player.getItemAssistant().addOrDropItem(2347, 1);
			player.nextChat = 0;
			chatboxText(player, "To smith you'll need a hammer - like the one you were given by", "Dezzick - access to an anvil like the one with the arrow over it", "and enough metal bars to make what you are trying to smith.", "", "Smithing a dagger");
			player.getActionSender().createArrow(3082, 9499, player.getH(), 2); // send
						// hint
						// to
						// furnace
			break;

		case 3066:
			chatboxText(player, "So let's move on. Go through the gates shown by the arrow.", "Remember you may need to move the camera to see your,", "surroundings. Speak to the guide for a recap at any time.", "", "You've finished in this area");
			player.tutorialProgress = 21;
			player.getActionSender().createArrow(3094, 9503, player.getH(), 2); // send
						// hint
						// to
						// furnace
			break;

		// end of mining/smithing

		/*
		 * start of melee
		 */
		case 3067:// Melee instructor c.tutorialProgress = 22
			sendPlayerChat1("Hi! My name is "
					+ Misc.capitalize(player.playerName) + ".");
			player.nextChat = 3068;
			break;

		case 3068:
			sendNpcChat2("Do I look like I care? To me you're just another",
					"newcomer who thinks they're ready to fight.",
					player.talkingNpc, "Combat Instructor");
			player.nextChat = 3069;
			break;

		case 3069:
			sendNpcChat1("I am Vannaka, the greatest swordsman alive.",
					player.talkingNpc, "Combat Instructor");
			player.nextChat = 3070;
			break;

		case 3070:
			sendNpcChat1("Let's get started by teaching you to wield a weapon",
					player.talkingNpc, "Combat Instructor");
			player.nextChat = 3071;
			break;

		case 3071: // send wear interface
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"",
					"You now have access to a new interface. Click on the flashing",
					"icon of a man the one to the right of your backpack icon.",
					"", "Wielding weapons");
			player.getActionSender().chatbox(6179);
			player.getActionSender().setSidebarInterface(4, 1644);// worn
			player.getActionSender().flashSideBarIcon(-4);
			player.nextChat = 0;
			break;

		case 3072:
			sendNpcChat2(
					"Very good, but that little butter knife isn't going to",
					"protect you much. Here, take these.", player.talkingNpc,
					"Combat Instructor");
			player.nextChat = 3073;
			break;

		case 3073:
			sendItemChat2(
					player,
					"",// Gives me sword and shield
					"The Combat Guide gives you a @blu@bronze sword@bla@ and a",
					"@blu@wooden shield!", 1171, 300);
			player.getItemAssistant().addOrDropItem(1171, 1);
			player.getItemAssistant().addOrDropItem(1277, 1);
			player.nextChat = 0;
			chatboxText(
					player,
					"In your worn inventory panel, right click on the dagger and",
					"select the remove option from the drop down list. After you've",
					"unequipped the dagger, wield the sword and shield. As you",
					"pass the mouse over an item you will see its name.",
					"Unequipping items");
			PlayerAssistant.removeHintIcon(player);
			break;

		case 3074:
			sendPlayerChat1("I did it! I killed a giant rat!");
			player.nextChat = 3075;
			break;

		case 3075:
			sendNpcChat3("I saw, " + Misc.capitalize(player.playerName) + "."
					+ " You seem better at this than I",
					"thought. Now that you have grasped basic swordplay",
					"let's move on.", player.talkingNpc, "Combat Instructor");

			player.nextChat = 3076;
			break;

		case 3076:
			sendNpcChat4(
					"Let's try some ranged attacking, with this you can kill",
					"foes from a distance. Also, foes unable to reach you are",
					"as good as dead. You'll be able to attack the rats",
					"without entering the pit.", player.talkingNpc,
					"Combat Instructor");

			player.nextChat = 3077;
			break;

		case 3077: // gives me bow and arrow
			sendItemChat2(
					player,
					"",
					"The Combat Guide gives you some @blu@bronze arrows@bla@ and",
					"a @blu@shortbow!", 841, 300);
			player.getItemAssistant().addOrDropItem(841, 1);
			player.getItemAssistant().addOrDropItem(882, 50);
			player.nextChat = 0;
			chatboxText(
					player,
					"Now you have a bow and some arrows. Before you can use",
					"them you'll need to equip them. Remember: to attack, right",
					"click on the monster and select attack.", "",
					"Rat ranging");
			player.ratdied2 = true;
			player.getActionSender().drawHeadicon(1, 13, 0, 0); // draws
																	// headicon
																	// to
																	// rat
			break;
		/*
		 * FINISH . Last parts, Finacial,prayer,magic
		 */

		case 3078: // fresh
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"Follow the path and you will come to the front of the building.",
					"This is the Bank of " + Constants.SERVER_NAME
							+ ", where you can store all your",
					"most valued items. To open your bank box just right click on an",
					"open booth indicated and select 'use'.", "Banking");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3122, 3124, player.getH(),
					2);
			break;

		case 3079: // fiancial dude start
			sendPlayerChat1("Hello. Who are you?");
			player.nextChat = 3080;

			break;

		case 3080:
			sendNpcChat2(
					"I'm the Financial Advisor. I'm here to tell people how to",
					"make money.", player.talkingNpc, "Financial Advisor");
			player.nextChat = 3081;
			break;

		case 3081:
			sendPlayerChat1("Okay. How can I make money then?");
			player.nextChat = 3082;
			break;

		case 3082:
			sendNpcChat1("How you can make money? Quite.", player.talkingNpc,
					"Financial Advisor");
			player.nextChat = 3083;
			break;

		case 3083:
			sendNpcChat3(
					"Well there are three basic ways of making money here:",
					"combat, quests, and trading. I will talk you through each",
					"of them very quickly.", player.talkingNpc,
					"Financial Advisor");
			player.nextChat = 3084;
			break;

		case 3084:
			sendNpcChat3(
					"Let's start with combat as it is probably still fresh in",
					"your mind. Many enemies, both human and monster,",
					"will drop items when they die.", player.talkingNpc,
					"Financial Advisor");
			player.nextChat = 3085;
			break;

		case 3085:
			sendNpcChat3(
					"Now, the next way to earn money quickly is by quests.",
					"Many people on " + Constants.SERVER_NAME
							+ " have things they need",
					"doing, which they will reward you for.",
					player.talkingNpc, "Financial Advisor");
			player.nextChat = 3086;
			break;

		case 3086:
			sendNpcChat3(
					"By getting a high level in skills such as Cooking, Mining,",
					"Smithing or Fishing, you can create or catch your own",
					"items and sell them for pure profit.", player.talkingNpc,
					"Financial Advisor");
			player.nextChat = 3087;
			break;

		case 3087:
			sendNpcChat2(
					"Well that about covers it. Come back if you'd like to go",
					"over this again.", player.talkingNpc, "Financial Advisor");
			player.nextChat = 3088;
			break;

		case 3088: // end
			player.getPlayerAssistant().removeAllWindows();
			player.tutorialProgress = 28;
			player.getActionSender().chatbox(6180);
			chatboxText(player, "", "Continue through the next door.", "", "",
					"");
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(3129, 3124, player.getH(),
					2);
			player.getActionSender().createArrow(1, 8);
			player.nextChat = 0;
			break;
		// end of FINANCIAL

		/*
		 * Section Prayer
		 */

		case 3089: // start of dialogue
			sendPlayerChat1("Good day, brother, my name's "
					+ Misc.capitalize(player.playerName) + ".");
			player.nextChat = 3090;
			break;

		case 3090:
			sendNpcChat2("Hello, " + Misc.capitalize(player.playerName) + "."
					+ " I'm Brother Brace. I'm here to",
					"tell you all about Prayer.", player.talkingNpc,
					"Brother Brace");
			player.nextChat = 3091;

			break;
		case 3091:
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(player, "",
					"Click on the flashing icon to open the Prayer menu.", "",
					"", "Your Prayer menu");
			player.getActionSender().chatbox(6179);
			player.getActionSender().setSidebarInterface(5, 5608);
			player.getActionSender().flashSideBarIcon(-5);
			player.tutorialProgress = 29;
			player.nextChat = 0;
			break;

		case 3092:
			sendNpcChat3("This is your Prayer list. Prayers can help a lot in",
					"combat. Click on the prayer you wish to use to activate",
					"it and click it again to deactivate it.",
					player.talkingNpc, "Brother Brace");
			player.nextChat = 3093;

			break;
		case 3093:
			sendNpcChat3("Active prayers will drain your Prayer Points which",
					"you can recharge by finding an altar or other holy spot",
					"and praying there.", player.talkingNpc, "Brother Brace");
			player.nextChat = 3094;
			break;

		case 3094:
			sendNpcChat3("As you noticed, most enemies will drop bones when",
					"defeated. Burying bones by clicking them in your",
					"inventory will gain you Prayer experience.",
					player.talkingNpc, "Brother Brace");
			player.nextChat = 3095;
			break;

		case 3095:
			sendNpcChat2(
					"I'm also the community officer 'round here, so it's my",
					"job to tell you about your friends and ignore list.",
					player.talkingNpc, "Brother Brace");
			player.nextChat = 3096;
			break;

		case 3096:
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"You should now see another new icon. Click on the flashing",
					"icon to open your friends list.", "", "", "Friends list");
			player.getActionSender().chatbox(6179);
			player.getActionSender().setSidebarInterface(8, 5065);
			player.getActionSender().flashSideBarIcon(-8);
			player.tutorialProgress = 30;
			player.nextChat = 0;
			break;

		case 3097: // long dialogue START
			// client.getPacketDispatcher().tutorialIslandInterface(75, 16);
			sendNpcChat4("Good. Now you have both menus open, I'll tell you a",
					"little about each. You can add people to either list by",
					"clicking the add button then typing their name into the",
					"box that appears.", player.talkingNpc, "Brother Brace");
			player.nextChat = 3098;
			break;

		case 3098:
			sendNpcChat4(
					"You remove people from the lists in the same way. If",
					"you add someone to your ignore list they will not be",
					"able to talk to you or send any form of message to",
					"you.", player.talkingNpc, "Brother Brace");
			player.nextChat = 3099;

			break;
		case 3099:
			sendNpcChat4(
					"Your friends list shows the online status of your",
					"friends. Friends in the red are offline, friends in green are",
					"online and on the same server and friends in yellow",
					"are online but on a different server.", player.talkingNpc,
					"Brother Brace");
			player.nextChat = 3100;
			break;

		case 3100:
			sendPlayerChat1("Are there rules on in-game behaviour?");
			player.nextChat = 3101;
			break;
		case 3101:
			sendNpcChat3("Yes, you should read the rules of conduct on the",
					"website to make sure you do nothing to get yourself",
					"banned.", player.talkingNpc, "Brother Brace");
			player.nextChat = 3102;
			break;
		case 3102:
			sendNpcChat3("But in general, always try to be courteous to other",
					"players - remember the people in the game are real",
					"people with real feelings.", player.talkingNpc,
					"Brother Brace");
			player.nextChat = 3103;
			break;
		case 3103:
			sendNpcChat2(
					"If you go 'round being abusive or causing trouble your",
					"character could end up being the one in trouble.",
					player.talkingNpc, "Brother Brace");
			player.nextChat = 3104;
			break;
		case 3104: // last one
			sendPlayerChat1("Okay thanks. I'll bear that in mind.");
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"You're almost finished on tutorial island. Pass through the",
					"door to find the path leading to your final instructor.",
					"", "", "Your final instructor!");
			PlayerAssistant.removeHintIcon(player);
			player.tutorialProgress = 32;
			player.getActionSender().chatbox(6179);
			player.getActionSender().createArrow(1, 9);
			player.nextChat = 0;
			break;
		// END BROTHER BRACE PRAYER

		/*
		 * Start magic instructor
		 */

		case 3105:
			sendPlayerChat1("Hello.");
			player.nextChat = 3106;
			break;

		case 3106:
			sendNpcChat3("Good day, newcomer. My name is Terrova. I'm here",
					"to tell you about Magic. Let's start by opening your",
					"spell list.", player.talkingNpc, "Magic Instructor");
			player.nextChat = 3107;
			break;

		case 3107:
			// sendItemChat1(client, "", "", 0, 50);
			player.getPlayerAssistant().removeAllWindows();
			player.getActionSender().chatbox(6180);
			chatboxText(
					player,
					"",
					"Open up the Magic menu by clicking on the flashing icon next",
					"to the Prayer button you just learned about.", "",
					"Open up your final menu");
			player.getActionSender().chatbox(6179);
			player.nextChat = 0;
			player.getActionSender().setSidebarInterface(6, 1151); // modern
			player.getActionSender().flashSideBarIcon(-6);
			break;

		case 3108:
			sendNpcChat3(
					"Good. This is a list of your spells. Currently you can",
					"only cast one offensive spell called Wind Strike. Let's",
					"try it out on one of those chickens.", player.talkingNpc,
					"Magic Instructor");
			player.nextChat = 3109;
			break;

		case 3109:
			sendItemChat1(
					player,
					"",
					"Terrova gives you five @blu@air runes@bla@ and @blu@five mind runes!",
					556, 300);
			player.getItemAssistant().addOrDropItem(558, 5);
			player.getItemAssistant().addOrDropItem(556, 5);
			player.nextChat = 0;
			chatboxText(
					player,
					"Now you have runes you should see the Wind Strike icon at the",
					"top left corner of the Magic interface - second in from the",
					"left. Walk over to the caged chickens, click the Wind Strike icon",
					"and then select one of the chicken to cast it on.",
					"Cast Wind Strke at a chicken");
			// sendStatement4("Now you have runes you should see the Wind Strike icon at the",
			// "top left corner of the Magic interface - second in from the",
			// "left. Walk over to the caged chickens, click the Wind Strike icon",
			// "and then select one of the chicken to cast it on.");
			player.getActionSender().drawHeadicon(1, 20, 0, 0); // draws
																	// headicon
																	// to
																	// chicken
			break;

		case 3110:
			sendNpcChat2("Well you're all finished here now. I'll give you a",
					"reasonable number of runes when you leave.",
					player.talkingNpc, "Magic Instructor");
			player.nextChat = 3111;
			break;

		case 3111:
			sendOption2("Mainland", "Stay here");
			player.dialogueAction = 3111;
			player.nextChat = 0;
			break;

		case 3112:// Mainland
			player.tutorialProgress = 35;
			sendNpcChat4(
					"When you get to the mainland you will find yourself in",
					"the town of Lumbridge. If you want some ideas on",
					"where to go next talk to my friend the Lumbridge",
					"Guide. You can't miss him; he's holding a big staff with",
					player.talkingNpc, "Magic Instructor");
			player.nextChat = 3113;
			break;
		case 3113:
			sendNpcChat4(
					"a question mark on the end. He also has a white beard",
					"and carries a rucksack full of scrolls. There are also",
					"many tutors willing to teach you about the many skills",
					"you could learn.", player.talkingNpc, "Magic Instructor");
			player.nextChat = 3114;
			break;

		case 3114:
			sendNpcChat3(
					"If all else fails, visit the " + Constants.SERVER_NAME
							+ " website for a whole",
					"chestload of information on quests, skills, and minigames",
					"as well as a very good starter's guide.",
					player.talkingNpc, "Magic Instructor");
			player.nextChat = 3115;
			break;

		case 3115:
			player.tutorialProgress = 36;
			PlayerAssistant.removeHintIcon(player);
			player.getItemAssistant().deleteAllItems();
			player.getItemAssistant().clearBank();
			player.getPlayerAssistant().sendSidebars();
			player.getPlayerAssistant().walkableInterface(-1);
			player.getActionSender().chatbox(-1);
			player.getItemAssistant()
					.sendWeapon(
							player.playerEquipment[player.playerWeapon],
							ItemAssistant
									.getItemName(player.playerEquipment[player.playerWeapon]));
			player.getPlayerAssistant().addStarter();
			player.getPlayerAssistant().movePlayer(3233, 3229, 0);
			player.getActionSender().sendMessage(
					"Welcome to @blu@" + Constants.SERVER_NAME
							+ "@bla@ - currently in Server Stage v@blu@"
							+ Constants.TEST_VERSION + "@bla@.");
			sendStatement4(
					"Welcome to Lumbridge! To get more help, simply click on the",
					"Lumbridge Guide or one of the Tutors - these can be found by",
					"looking for the question mark icon on your mini-map. If you find",
					"you are lost at any time, look for a signpost.");
			player.nextChat = 3116;
			player.canWalkTutorial = true;
			break;

		case 3116:
			player.getPlayerAssistant().showInterface(3559);
			player.canChangeAppearance = true;
			player.closeTutorialInterface = true;
			player.nextChat = 0;
			break;

		case 3117:
			sendNpcChat1(
					"You can't stay here you've completed the tutorial already.",
					player.talkingNpc, "Magic Instructor");
			player.nextChat = 3112;
			break;

		case 3118:
			sendNpcChat2("Hello " + player.playerName + ".",
					"Are you interested in buying any beer?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3119;
			break;

		case 3119:
			sendOption2("Yes please.", "No Thanks.");
			player.dialogueAction = 152;
			break;

		case 3120:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3121:
			sendPlayerChat1("Yes please.");
			player.nextChat = 3122;
			break;

		case 3122:
			sendOption4("Asgarnain Ale", "Wizard's Mind Bomb", "Dwarven Stout",
					"Never Mind");
			player.dialogueAction = 153;
			break;

		case 3123:
			if (player.getItemAssistant().playerHasItem(995, 3)) {
				sendPlayerChat1("Asgarnian Ale please.");
				player.getItemAssistant().deleteItem2(995, 3);
				player.getItemAssistant().addOrDropItem(1905, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins to buy that.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
			}
			break;

		case 3124:
			sendPlayerChat1("Wizard's Mind Bomb please.");
			player.getItemAssistant().deleteItem2(995, 3);
			player.getItemAssistant().addOrDropItem(1907, 1);
			player.nextChat = 0;
			break;

		case 3125:
			sendPlayerChat1("Dwarven Stout please.");
			player.getItemAssistant().deleteItem2(995, 3);
			player.getItemAssistant().addOrDropItem(1913, 1);
			player.nextChat = 0;
			break;

		case 3126:
			sendPlayerChat1("Never mind.");
			player.nextChat = 0;
			break;

		/*case 3127:
			sendNpcChat3("Im sorry...", "I can't seem to find your reward,", "Did you vote correctly?", 945, "" + Constants.SERVER_NAME + " Guide");
			client.nextChat = 0;
			break;

		case 3128:
			sendNpcChat2("Im sorry,", "An error occured, please try again later.", 945, "" + Constants.SERVER_NAME + " Guide");
			client.nextChat = 0;
			break;

		case 3129:
			sendNpcChat2("Thank you so much for voting!", "Enjoy your free experience lamp!", 945, "" + Constants.SERVER_NAME + " Guide");
			client.getItemAssistant().addOrDropItem(2528, 1);
			VotingHandler.setAsReceived(client.playerName);
			client.nextChat = 0;
			break;

		case 3130:
			int reward = 1000 + Misc.random(3000);
			sendNpcChat2("Thank you so much for voting!", "Enjoy your " + reward + " coins!", 945, "" + Constants.SERVER_NAME + " Guide");
			client.getItemAssistant().addOrDropItem(995, reward);
			VotingHandler.setAsReceived(client.playerName);
			client.nextChat = 0;
			break;

		case 3131:
			sendNpcChat2("Thank you so much for voting!", "Enjoy your magical lamp that restores your run!", 945, "" + Constants.SERVER_NAME + " Guide");
			client.getItemAssistant().addOrDropItem(VotingRewards.ITEM, 1);
			VotingHandler.setAsReceived(client.playerName);
			client.nextChat = 0;
			break;

		case 3132:
			sendNpcChat2("We couldn't find your reward,", "Please report this with a screenshot of your chatbox!", 	945, "" + Constants.SERVER_NAME + " Guide");
			client.nextChat = 0;
			break;*/

		case 3133:
			sendNpcChat2(
					"Thank you for rescuing me! It isn't very comfy in this",
					"cell!", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3134;
			break;

		case 3134:
			sendOption2("So... do you know anywhere good to explore?",
					"Do I get a reward?");
			player.dialogueAction = 154;
			break;

		case 3135:
			sendPlayerChat1("Do I get a reward? For freeing you and all...");
			player.nextChat = 3136;
			break;

		case 3136:
			sendNpcChat2(
					"Well... not really... The Black Knights took all of my",
					"stuff before throwing me in here to rot!",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
			break;

		case 3137:
			sendPlayerChat1("So... do you know anywhere good to explore?");
			player.nextChat = 3138;
			break;

		case 3138:
			sendNpcChat3(
					"Well, this dungeon was quite good to explore ...until I",
					"got captured, anyway. I was given a key to an inner",
					"part of this dungeon by a mysterious cloaked stranger!",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3139;
			break;

		case 3139:
			sendNpcChat3("It's rather tough for me to get that far into the",
					"dungeon however... I just keep getting captured! Would",
					"you like to give it a go?", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3140;
			break;

		case 3140:
			sendOption2("Yes please!", "No, it's too dangerous for me.");
			player.dialogueAction = 155;
			break;

		case 3141:
			sendPlayerChat1("No, it's too dangerous for me.");
			player.nextChat = 0;
			break;

		case 3142:
			sendPlayerChat1("Yes please!");
			player.nextChat = 3143;
			break;

		case 3143:
			sendStatement("Velrak reaches somewhere mysterious and passes you a key.");
			player.getItemAssistant().addOrDropItem(1590, 1);
			player.nextChat = 0;
			break;

		case 3144:
			sendNpcChat1("Would you like me to teleport you to rune essence?",
					player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3145;
			break;

		case 3145:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 156;
			break;

		case 3146:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3147:
			sendPlayerChat1("Yes please.");
			player.nextChat = 3148;
			break;

		case 3148:
			String type = player.playerMagicBook == 0 ? "modern" : "ancient";
			player.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 3149:
			sendNpcChat1("Welcome to the Wizards Guild.", player.talkingNpc,
					"Wizard Distenor");
			player.nextChat = 0;
			break;

		case 3150:
			sendNpcChat1("Hello are you interested in buying anything?",
					player.talkingNpc, "Bartender");
			player.nextChat = 3151;
			break;

		case 3151:
			sendOption2("Yes please.", "No thanks.");
			player.dialogueAction = 157;
			break;

		case 3152:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3153:
			sendPlayerChat1("Yes please.");
			player.nextChat = 3154;
			break;

		case 3154:
			sendNpcChat1("What would you like?", player.talkingNpc, "Bartender");
			player.nextChat = 3155;
			break;

		case 3155:
			sendOption2("Meat Pie", "Stew");
			player.dialogueAction = 158;
			break;

		case 3156:
			if (player.getItemAssistant().playerHasItem(995, 16)) {
				sendPlayerChat1("Meat Pie please.");
				player.getItemAssistant().deleteItem2(995, 16);
				player.getItemAssistant().addOrDropItem(2327, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 16 coins to buy that.",
						player.talkingNpc, "Bartender");
				player.nextChat = 0;
			}
			break;

		case 3157:
			if (player.getItemAssistant().playerHasItem(995, 20)) {
				sendPlayerChat1("Stew please.");
				player.getItemAssistant().deleteItem2(995, 20);
				player.getItemAssistant().addOrDropItem(2003, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 20 coins to buy that.",
						player.talkingNpc, "Bartender");
				player.nextChat = 0;
			}
			break;

		case 3158:
			sendNpcChat1("Hello, " + player.playerName + " what would you like to do?", player.talkingNpc, "Wyson the Gardener");
			player.nextChat = 3159;
			break;

		case 3159:
			sendOption3("Buy woad leaves.", "Exchange mole skins/claws for nests.", "Nothing.");
			player.dialogueAction = 159;
			break;

		case 3160:
			sendPlayerChat1("I don't want to do anything.");
			player.nextChat = 0;
			break;

		case 3161:
			sendPlayerChat1("I would like to buy woad leaves.");
			player.nextChat = 3162;
			break;

		case 3162:
			sendNpcChat1("How much are you willing to pay for a woad leaf?",
					player.talkingNpc, "Wyson the Gardener");
			player.nextChat = 3163;
			break;

		case 3163:
			sendOption4("5 coins", "10 coins", "15 coins", "20 coins");
			player.dialogueAction = 160;
			break;

		case 3164:
			sendNpcChat2("Don't be ridiculous offer me more",
					"and I may sell you one!", player.talkingNpc,
					"Wyson the Gardener");
			player.nextChat = 0;
			break;

		case 3165:
			if (player.getItemAssistant().playerHasItem(995, 15)) {
				sendNpcChat1(
						"That sounds like a fair offer, here's one woaf leaf.",
						player.talkingNpc, "Wyson the Gardener");
				player.getItemAssistant().addOrDropItem(1793, 1);
				player.getItemAssistant().deleteItem2(995, 15);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins to buy a woad leaf.",
						player.talkingNpc, "Wyson the Gardener");
			}
			break;

		case 3166:
			if (player.getItemAssistant().playerHasItem(995, 15)) {
				sendNpcChat1(
						"That offer sounds generous, I will give you two woaf leaves!",
						player.talkingNpc, "Wyson the Gardener");
				player.getItemAssistant().addOrDropItem(1793, 2);
				player.getItemAssistant().deleteItem2(995, 20);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins to buy a woad leaf.",
						player.talkingNpc, "Wyson the Gardener");
			}
			break;

		case 3167:
			sendNpcChat1("Hello are you interested in buying anything?",
					player.talkingNpc, "Roavar");
			player.nextChat = 3168;
			break;

		case 3168:
			sendOption2("Yes please.", "No Thanks.");
			player.dialogueAction = 162;
			break;

		case 3169:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3170:
			sendPlayerChat1("Yes please can I get a moonlight mean?");
			player.nextChat = 3171;
			break;

		case 3171:
			if (player.getItemAssistant().playerHasItem(995, 5)) {
				sendNpcChat1("Sure thing.", player.talkingNpc, "Roavar");
				player.getItemAssistant().deleteItem2(995, 5);
				player.getItemAssistant().addOrDropItem(2955, 1);
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 5 coins to buy that.",
						player.talkingNpc, "Roavar");
				player.nextChat = 0;
			}
			break;

		case 3172:
			sendOption2("Experience Lamp",
					"Magical Lamp that restores your run to full");
			player.dialogueAction = 163;
			break;

		case 3173:
			sendNpcChat2("Hello, would you like to travel to Shilo Village",
					"for 200 coins?", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3174;
			break;

		case 3174:
			sendOption2("Yes please.", "No Thanks.");
			player.dialogueAction = 164;
			break;

		case 3175:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3176:
			sendPlayerChat1("Yes please.");
			player.nextChat = 3177;
			break;

		case 3177:
			if (player.getItemAssistant().playerHasItem(995, 200)) {
				player.getItemAssistant().deleteItem2(995, 200);
				player.getPlayerAssistant().startTeleport(2834, 2953, 0,
						"modern");
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 3178:
			sendNpcChat2(
					"Hello, would you like to travel to back to Brimhaven",
					"for 200 coins?", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3179;
			break;

		case 3179:
			sendOption2("Yes please.", "No Thanks.");
			player.dialogueAction = 165;
			break;

		case 3180:
			sendPlayerChat1("No thanks.");
			player.nextChat = 0;
			break;

		case 3181:
			sendPlayerChat1("Yes please.");
			player.nextChat = 3182;
			break;

		case 3182:
			if (player.getItemAssistant().playerHasItem(995, 200)) {
				player.getItemAssistant().deleteItem2(995, 200);
				player.getPlayerAssistant().startTeleport(2779, 3212, 0, "modern");
				player.nextChat = 0;
			} else {
				sendNpcChat1("You don't have enough coins", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 3183:
			sendNpcChat2("Hello, are you interested in buying cooking gauntlets", "for 25k?", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3184;
			break;

		case 3184:
			sendOption2("Yes please.", "No thank you.");
			player.dialogueAction = 178;
			break;

		case 3185:
			sendPlayerChat1("No thank you, do I look like I'm a cook?");
			player.nextChat = 0;
			break;

		case 3186:
			if (player.getItemAssistant().playerHasItem(995, 25000)
					&& !player.getItemAssistant().playerHasItem(775)
					&& player.playerEquipment[player.playerHands] != 775) {
				sendPlayerChat1("Yes please.");
				player.getItemAssistant().deleteItem2(995, 25000);
				player.getPlayerAssistant().removeGloves();
				player.getItemAssistant().addOrDropItem(775, 1);
				player.nextChat = 0;
			} else if (player.getItemAssistant().playerHasItem(775)
					|| player.playerEquipment[player.playerHands] == 775) {
				sendNpcChat1("Why would you want another pair?",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 25k to buy these.", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 3187:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			player.nextChat = 3188;
			break;

		case 3188:
			sendOption2("Yea! I'm fearless!", "No way! That looks scary!");
			player.dialogueAction = 1;
			player.nextChat = 0;
			break;

		case 3189:
			sendNpcChat2(
					"Hello, are you interested in buying goldsmith gantlets",
					"for 25k?", player.talkingNpc,
					NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 3190;
			break;

		case 3190:
			sendOption2("Yes please.", "No thank you.");
			player.dialogueAction = 175;
			break;

		case 3191:
			sendPlayerChat1("No thank you, do I look like I'm a smither?");
			player.nextChat = 0;
			break;

		case 3192:
			if (player.getItemAssistant().playerHasItem(995, 25000)
					&& !player.getItemAssistant().playerHasItem(776)
					&& player.playerEquipment[player.playerHands] != 776) {
				sendPlayerChat1("Yes please.");
				player.getItemAssistant().deleteItem2(995, 25000);
				player.getPlayerAssistant().removeGloves();
				player.getItemAssistant().addOrDropItem(776, 1);
				player.nextChat = 0;
			} else if (player.getItemAssistant().playerHasItem(776)
					|| player.playerEquipment[player.playerHands] == 776) {
				sendNpcChat1("Why would you want another pair?",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You need 25k to buy these.", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 3193:
			if (!player.getItemAssistant().playerHasItem(1540)) {
				player.getItemAssistant().addOrDropItem(1540, 1);
				sendNpcChat1("Enjoy!", player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			} else {
				sendNpcChat1("You already have one of those shields.",
						player.talkingNpc,
						NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;

		case 3194:
			QuestRewards.vampFinish(player);
			break;
			
		case 3195:
			sendPlayerChat1("I would like to exchange mole skins/claws for nests.");
			player.nextChat = 3196;
		break;
		
		case 3196:
			sendOption2("Mole skins.", "Mole claws.");
			player.dialogueAction = 180;
		break;
		
		case 3197:
		if (MOLE_SKIN > 0) {
			sendPlayerChat2("I would like to exchange my " + MOLE_SKIN + " mole skins,", "for bird nests.");
			player.nextChat = 3198;
		} else {
			sendNpcChat1("You don't have any mole skins.", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
		}
		break;
		
		case 3198:
			player.getItemAssistant().deleteItem2(7418, MOLE_SKIN);
			player.getItemAssistant().addOrDropItem(7413, MOLE_SKIN);
			sendNpcChat1("Here you go", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
			player.nextChat = 0;
		break;
		
		case 3199:
			if (MOLE_CLAW > 0) {
				sendPlayerChat2("I would like to exchange my " + MOLE_CLAW + " mole claws,", "for bird nests.");
				player.nextChat = 3200;
			} else {
				sendNpcChat1("You don't have any mole claws.", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			}
			break;
			
			case 3200:
				player.getItemAssistant().deleteItem2(7416, MOLE_CLAW);
				player.getItemAssistant().addOrDropItem(7413, MOLE_CLAW);
				sendNpcChat1("Here you go", player.talkingNpc, NpcHandler.getNpcListName(player.talkingNpc));
				player.nextChat = 0;
			break;
			
			//483
			case 3201: //competition judge
				sendNpcChat4("Hello!", "I'm the competition judge of the Ranging Guild.", "You can buy shots from me and shoot the targets", "for points. You can exchange the points with me.", player.talkingNpc, "Judge");
				player.nextChat = 3202;
				break;
				
			case 3202: 
				sendNpcChat1("What would you like to do?", player.talkingNpc, "Judge");
				player.nextChat = 3203;
				break;
				
			case 3203:
				sendOption4("I would like to buy shots.", "I would like to exchange my points.", "How am I doing right now?", "Never mind.");
				player.dialogueAction = 485;
				break;
				
			case 3204:
				sendOption2("Pastry Dough", "Bread Dough");
				player.dialogueAction = 3204;//186
			break;
			
			case 3205:
				sendOption2("Pastry Dough", "Bread Dough");
				player.dialogueAction = 3205;//187
			break;
			
			/*case 3206:
			if (Constants.HALLOWEEN == false) {
				sendNpcChat2("The halloween event is now over,", "what would you like?", 945, "" + Constants.SERVER_NAME + " Guide");
				client.nextChat = 3207;
			} else if (client.recievedMask == false && Constants.HALLOWEEN) {
				sendNpcChat2("Thank you so much for voting!", "Enjoy your mask!", 945, "" + Constants.SERVER_NAME + " Guide");
				client.getItemAssistant().addOrDropItem(client.getPlayerAssistant().randomReward(), 1);
				VotingHandler.setAsReceived(client.playerName);
				client.recievedMask = true;
				client.nextChat = 0;
			} else if (client.recievedMask == true && Constants.HALLOWEEN) {
				sendNpcChat2("You have already recieved a halloween mask.", "What reward would you like?", 945, "" + Constants.SERVER_NAME + " Guide");
				client.nextChat = 3207;
			}
			break;
			
			case 3207:
				sendOption3("Experience Lamp (Depends on Level of Skill)", "Coins (1-4k)", "Energy Lamp");
				client.dialogueAction = 188;
			break;*/
			
			case 3208:
				sendNpcChat1("Hello, " + player.playerName + " what would you like to do?", 958, "Fadli");
				player.nextChat = 3209;
			break;
				
			case 3209:
				sendOption2("I would like to open my bank.", "I would like to view your shop.");
				player.dialogueAction = 189;
			break;
			
			case 3210:
				sendPlayerChat1("I would like to open my bank.");
				player.nextChat = 3211;
			break;
			
			case 3211:
				player.getPlayerAssistant().openUpBank();
				player.nextChat = 0;
			break;
			
			case 3212:
				sendPlayerChat1("I would like to view your shop.");
				player.nextChat = 3213;
			break;
			
			case 3213:
				player.getShopAssistant().openShop(143);
				player.nextChat = 0;
			break;
			
			case 3214:
				sendPlayerChat1("How's it going?");
				player.nextChat = 3215;
			break;
			
			case 3215:
			if (Misc.random(1) == 1) {
				sendNpcChat1("I'm good, thank you for asking.", 2238, "Donie");
				player.nextChat = 0;
			} else {
				sendNpcChat1("I feel great, thanks for asking.", 2238, "Donie");
				player.nextChat = 0;
			}
			break;
			
			case 3216://23
				sendStartInfo("As you collect your reward, you notice an aweful smell.", "You look below the remaining debris to the bottom of the", "chest. You see a trapdoor. You open it and it leads to a ladder", "that goes down a long ways.", "Continue?");
				break;
				
			case 3217://24
				sendStatement("Would you like to continue?");
				player.nextChat = 3218;
				break;
				
			case 3218://25
				sendOption2("Yes, I'm not afraid of anything!", "No way, the smell itself turns me away.");
				player.dialogueAction = 3218;
				break;
				
			case 3219://26
				sendStatement("This is a very dangerous minigame, are you sure?");
				player.nextChat = 3220;
				break;
				
			case 3220://27
				player.dialogueAction = 3220;
				sendOption2("Yes, I'm a brave warrior!", "Maybe I shouldn't, I could lose my items!");
				break;
				
			case 3221://28
				sendStatement("Congratulations, "+player.playerName+". You've completed the barrows challenge & your reward has been delivered.");
				player.nextChat = 0;
				break;
				
			case 3222://29
				sendStatement("Are you ready to visit the chest room?");
				player.nextChat = 3223;
				player.dialogueAction = 3222;
				break;
				
			case 3223://30
				sendOption2("Yes, I've killed all the other brothers!", "No, I still need to kill more brothers");
				player.nextChat = 0;
				break;
				
			/**
			 * CANNNON
			 * @author Andrew
			 */
			case 3500:
				if (player.getCannon().needsCannon()) {
					sendNpcChat2("Hello, " + Misc.capitalize(player.playerName) + ".", "I see that you lost your cannon.", player.talkingNpc, "Nulodion");
					player.nextChat = 3501;
				} else {
					sendNpcChat1("" + Misc.capitalize(player.playerName) + " you do not have anything to collect currently.", player.talkingNpc, "Nulodion");
					player.nextChat = 0;
				}
			break;
			
			case 3501:
				if (player.getCannon().needsCannon()) {
				sendNpcChat1("Here is your cannon, try not to lose it again.", player.talkingNpc, "Nulodion");
				for (int i = 0; i < 4; i++) {
					player.getItemAssistant().addItem(player.getCannon().ITEM_PARTS[i], 1);
				}
				player.lostCannon = false;
				player.nextChat = 0;
				} else {
					sendNpcChat1("" + Misc.capitalize(player.playerName) + " you do not have a cannon to collect currently.", player.talkingNpc, "Nulodion");
					player.nextChat = 0;
				}
			break;

		}

	}

	public void chatboxText(Client c, String text, String text1, String text2,
			String text3, String title) {
		player.getPlayerAssistant().sendFrame126(title, 6180);
		player.getPlayerAssistant().sendFrame126(text, 6181);
		player.getPlayerAssistant().sendFrame126(text1, 6182);
		player.getPlayerAssistant().sendFrame126(text2, 6183);
		player.getPlayerAssistant().sendFrame126(text3, 6184);
	}

	public void clearChatBoxText(Client c) {
		player.getPlayerAssistant().sendFrame126("", 6180);
		player.getPlayerAssistant().sendFrame126("", 6181);
		player.getPlayerAssistant().sendFrame126("", 6182);
		player.getPlayerAssistant().sendFrame126("", 6183);
		player.getPlayerAssistant().sendFrame126("", 6184);
	}

	public void sendStartInfo(String text, String text1, String text2, String text3, String title, boolean send) {
		player.getPlayerAssistant().sendFrame126(title, 6180);
		player.getPlayerAssistant().sendFrame126(text, 6181);
		player.getPlayerAssistant().sendFrame126(text1, 6182);
		player.getPlayerAssistant().sendFrame126(text2, 6183);
		player.getPlayerAssistant().sendFrame126(text3, 6184);
		player.getPlayerAssistant().sendChatInterface(6179);
	}

	/*
	 * Options
	 */

	public void sendPlayerChat1(String s) {
		player.getPlayerAssistant().sendDialogueAnimation(969, 591);
		player.getPlayerAssistant().sendFrame126(player.playerName, 970);
		player.getPlayerAssistant().sendFrame126(s, 971);
		player.getPlayerAssistant().sendPlayerDialogueHead(969);
		player.getPlayerAssistant().sendChatInterface(968);
	}

	public void sendPlayerChat2(String s, String s1) {
		player.getPlayerAssistant().sendDialogueAnimation(974, 591);
		player.getPlayerAssistant().sendFrame126(player.playerName, 975);
		player.getPlayerAssistant().sendFrame126(s, 976);
		player.getPlayerAssistant().sendFrame126(s1, 977);
		player.getPlayerAssistant().sendPlayerDialogueHead(974);
		player.getPlayerAssistant().sendChatInterface(973);
	}
	
	private void sendPlayerChat3(String s, String s1, String s2) {
		player.getPlayerAssistant().sendDialogueAnimation(980, 591);
		player.getPlayerAssistant().sendFrame126(player.playerName, 981);
		player.getPlayerAssistant().sendFrame126(s, 982);
		player.getPlayerAssistant().sendFrame126(s1, 983);
		player.getPlayerAssistant().sendFrame126(s2, 984);
		player.getPlayerAssistant().sendPlayerDialogueHead(980);
		player.getPlayerAssistant().sendChatInterface(979);
	}

	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		player.getPlayerAssistant().sendDialogueAnimation(987, 591);
		player.getPlayerAssistant().sendFrame126(player.playerName, 988);
		player.getPlayerAssistant().sendFrame126(s, 989);
		player.getPlayerAssistant().sendFrame126(s1, 990);
		player.getPlayerAssistant().sendFrame126(s2, 991);
		player.getPlayerAssistant().sendFrame126(s3, 992);
		player.getPlayerAssistant().sendPlayerDialogueHead(987);
		player.getPlayerAssistant().sendChatInterface(986);
	}

	public void sendOption2(String s, String s1) {
		player.getPlayerAssistant().sendFrame126("Select an Option", 2460);
		player.getPlayerAssistant().sendFrame126(s, 2461);
		player.getPlayerAssistant().sendFrame126(s1, 2462);
		player.getPlayerAssistant().sendChatInterface(2459);
	}

	public void sendOption3(String s, String s1, String s2) {
		player.getPlayerAssistant().sendFrame126("Select an Option", 2470);
		player.getPlayerAssistant().sendFrame126(s, 2471);
		player.getPlayerAssistant().sendFrame126(s1, 2472);
		player.getPlayerAssistant().sendFrame126(s2, 2473);
		player.getPlayerAssistant().sendChatInterface(2469);
	}

	public void sendOption4(String s, String s1, String s2, String s3) {
		player.getPlayerAssistant().sendFrame126("Select an Option", 2481);
		player.getPlayerAssistant().sendFrame126(s, 2482);
		player.getPlayerAssistant().sendFrame126(s1, 2483);
		player.getPlayerAssistant().sendFrame126(s2, 2484);
		player.getPlayerAssistant().sendFrame126(s3, 2485);
		player.getPlayerAssistant().sendChatInterface(2480);
	}

	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		player.getPlayerAssistant().sendFrame126("Select an Option", 2493);
		player.getPlayerAssistant().sendFrame126(s, 2494);
		player.getPlayerAssistant().sendFrame126(s1, 2495);
		player.getPlayerAssistant().sendFrame126(s2, 2496);
		player.getPlayerAssistant().sendFrame126(s3, 2497);
		player.getPlayerAssistant().sendFrame126(s4, 2498);
		player.getPlayerAssistant().sendChatInterface(2492);
	}

	/*
	 * Statements
	 */

	public void sendStatement(String s) { // 1 line click here to continue chat
											// box interface
		player.getPlayerAssistant().sendFrame126(s, 357);
		player.getPlayerAssistant().sendFrame126("Click here to continue", 358);
		player.getPlayerAssistant().sendChatInterface(356);
	}

	public void sendStatement2(String s, String s1) {
		player.getPlayerAssistant().sendFrame126(s, 360);
		player.getPlayerAssistant().sendFrame126(s1, 361);
		player.getPlayerAssistant().sendFrame126("Click here to continue", 362);
		player.getPlayerAssistant().sendChatInterface(359);
	}

	public void sendStatement3(String s, String s1, String s2) {
		player.getPlayerAssistant().sendFrame126(s, 364);
		player.getPlayerAssistant().sendFrame126(s1, 365);
		player.getPlayerAssistant().sendFrame126(s1, 366);
		player.getPlayerAssistant().sendFrame126("Click here to continue", 367);
		player.getPlayerAssistant().sendChatInterface(363);
	}

	public void sendStatement4(String s, String s1, String s2, String s3) {
		player.getPlayerAssistant().sendFrame126(s, 369);
		player.getPlayerAssistant().sendFrame126(s1, 370);
		player.getPlayerAssistant().sendFrame126(s2, 371);
		player.getPlayerAssistant().sendFrame126(s3, 372);
		player.getPlayerAssistant().sendFrame126("Click here to continue", 373);
		player.getPlayerAssistant().sendChatInterface(368);
	}

	public void itemMessage(String title, String message, int itemid, int size) {
		player.getPlayerAssistant().sendDialogueAnimation(4883, 591);
		player.getPlayerAssistant().sendFrame126(title, 4884);
		player.getPlayerAssistant().sendFrame126(message, 4885);
		player.getPlayerAssistant().sendFrame126("Click here to continue.", 4886);
		player.getPlayerAssistant().sendFrame246(4883, size, itemid);
		player.getPlayerAssistant().sendChatInterface(4882);
	}

	/*
	 * Npc Chatting
	 */

	public void sendNpcChat1(String s, int ChatNpc, String name) {
		player.getPlayerAssistant().sendDialogueAnimation(4883, 591);
		player.getPlayerAssistant().sendFrame126(name, 4884);
		player.getPlayerAssistant().sendFrame126(s, 4885);
		player.getPlayerAssistant().sendNPCDialogueHead(ChatNpc, 4883);
		player.getPlayerAssistant().sendChatInterface(4882);
	}
	
	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		player.getPlayerAssistant().sendDialogueAnimation(4888, 591);
		player.getPlayerAssistant().sendFrame126(name, 4889);
		player.getPlayerAssistant().sendFrame126(s, 4890);
		player.getPlayerAssistant().sendFrame126(s1, 4891);
		player.getPlayerAssistant().sendNPCDialogueHead(ChatNpc, 4888);
		player.getPlayerAssistant().sendChatInterface(4887);
	}

	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc,
			String name) {
		player.getPlayerAssistant().sendDialogueAnimation(4894, 591);
		player.getPlayerAssistant().sendFrame126(name, 4895);
		player.getPlayerAssistant().sendFrame126(s, 4896);
		player.getPlayerAssistant().sendFrame126(s1, 4897);
		player.getPlayerAssistant().sendFrame126(s2, 4898);
		player.getPlayerAssistant().sendNPCDialogueHead(ChatNpc, 4894);
		player.getPlayerAssistant().sendChatInterface(4893);
	}


	public void sendNpcChat4(String s, String s1, String s2, String s3,
			int ChatNpc, String name) {
		player.getPlayerAssistant().sendDialogueAnimation(4901, 591);
		player.getPlayerAssistant().sendFrame126(name, 4902);
		player.getPlayerAssistant().sendFrame126(s, 4903);
		player.getPlayerAssistant().sendFrame126(s1, 4904);
		player.getPlayerAssistant().sendFrame126(s2, 4905);
		player.getPlayerAssistant().sendFrame126(s3, 4906);
		player.getPlayerAssistant().sendNPCDialogueHead(ChatNpc, 4901);
		player.getPlayerAssistant().sendChatInterface(4900);
	}

	/*
	 * Tutorial interface
	 */

	public void sendStartInfo(String text, String text1, String text2,
			String text3, String title) {
		player.getPlayerAssistant().sendFrame126(title, 6180);
		player.getPlayerAssistant().sendFrame126(text, 6181);
		player.getPlayerAssistant().sendFrame126(text1, 6182);
		player.getPlayerAssistant().sendFrame126(text2, 6183);
		player.getPlayerAssistant().sendFrame126(text3, 6184);
		player.getPlayerAssistant().sendChatInterface(6179);
	}

	/*
	 * ItemInformation Box
	 */

	public void itemMessage1(String message1, int itemid, int size) {
		player.getPlayerAssistant().sendDialogueAnimation(307, 591);
		player.getPlayerAssistant().sendFrame126(message1, 308);
		player.getPlayerAssistant().sendFrame246(307, size, itemid);
		player.getPlayerAssistant().sendChatInterface(306);
		player.nextChat = 0;
	}

	/*
	 * Give items
	 */

	public void sendGiveItemNpc(String text1, String text2, int item1, int item2) {
		player.getPlayerAssistant().sendFrame126(text1, 6232);
		player.getPlayerAssistant().sendFrame126(text2, 6233);
		player.getPlayerAssistant().sendFrame246(6235, 170, item1);
		player.getPlayerAssistant().sendFrame246(6236, 170, item2);
		player.getPlayerAssistant().sendChatInterface(6231);
	}

	public void sendGiveItemNpc(String text, int item) {
		player.getPlayerAssistant().sendFrame126(text, 308);
		player.getPlayerAssistant().sendFrame246(307, 200, item);
		player.getPlayerAssistant().sendChatInterface(306);
	}

	/**
	 * USAGE: displayTwoItemsOption(c, new String {"Whip", "DDS", new int {
	 * 4151, 5698}, new int { 150, 150}); Displays two items with a select one
	 * option.
	 */
	public void displayTwoItemsOption(Client c, String[] s, int items[],
			int[] zoom) {
		player.getPlayerAssistant().sendFrame126(s[0], 144);
		player.getPlayerAssistant().sendFrame126(s[1], 145);
		player.getPlayerAssistant().sendFrame246(items[0], zoom[0], 142);
		player.getPlayerAssistant().sendFrame246(items[1], zoom[1], 143);
		player.getPlayerAssistant().sendChatInterface(139);
	}

	/**
	 * Displays single line text
	 */
	public void displaySingleLine(Client c, String s) {
		player.getPlayerAssistant().sendFrame126(s, 357);
		player.getPlayerAssistant().sendChatInterface(356);
	}

	/**
	 * Displays two lined text
	 */
	public void displayTwoLined(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 360);
		player.getPlayerAssistant().sendFrame126(s[1], 361);
		player.getPlayerAssistant().sendChatInterface(359);
	}

	/**
	 * Displays Three lined text
	 */
	public void displayThreeLined(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 364);
		player.getPlayerAssistant().sendFrame126(s[1], 365);
		player.getPlayerAssistant().sendFrame126(s[2], 366);
		player.getPlayerAssistant().sendChatInterface(363);
	}

	/**
	 * Displays Four lined text
	 */
	public void displayFourLined(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 369);
		player.getPlayerAssistant().sendFrame126(s[1], 370);
		player.getPlayerAssistant().sendFrame126(s[2], 371);
		player.getPlayerAssistant().sendFrame126(s[2], 372);
		player.getPlayerAssistant().sendChatInterface(368);
	}

	/**
	 * Select Option 2
	 */
	public void displaySelectOption2(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 2461);
		player.getPlayerAssistant().sendFrame126(s[1], 2462);
		player.getPlayerAssistant().sendChatInterface(2459);
	}

	/**
	 * Select Option 3
	 */
	public void displaySelectOption3(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 2471);
		player.getPlayerAssistant().sendFrame126(s[1], 2472);
		player.getPlayerAssistant().sendFrame126(s[2], 2473);
		player.getPlayerAssistant().sendChatInterface(2469);
	}

	/**
	 * Select Option 4
	 */
	public void displaySelectOption4(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 2482);
		player.getPlayerAssistant().sendFrame126(s[1], 2483);
		player.getPlayerAssistant().sendFrame126(s[2], 2484);
		player.getPlayerAssistant().sendFrame126(s[3], 2485);
		player.getPlayerAssistant().sendChatInterface(2480);
	}

	/**
	 * Select Option 5
	 */
	public void displaySelectOption5(Client c, String[] s) {
		player.getPlayerAssistant().sendFrame126(s[0], 2494);
		player.getPlayerAssistant().sendFrame126(s[1], 2495);
		player.getPlayerAssistant().sendFrame126(s[2], 2496);
		player.getPlayerAssistant().sendFrame126(s[3], 2497);
		player.getPlayerAssistant().sendFrame126(s[4], 2498);
		player.getPlayerAssistant().sendChatInterface(2492);
	}

	public void itemMessage(Client c, String message1, int itemid, int size) {
		player.getPlayerAssistant().sendDialogueAnimation(307, 591);
		player.getPlayerAssistant().sendFrame126(message1, 308);
		player.getPlayerAssistant().sendFrame246(307, size, itemid);
		player.getPlayerAssistant().sendChatInterface(306);
		c.nextChat = 0;
	}

	public void sendItemChat1(Client c, String header, String one, int item,
			int zoom) {
		player.getPlayerAssistant().sendFrame246(4883, zoom, item);
		player.getPlayerAssistant().sendFrame126(header, 4884);
		player.getPlayerAssistant().sendFrame126(one, 4885);
		player.getPlayerAssistant().sendChatInterface(4882);
	}

	public void sendItemChat2(Client c, String header, String one, String two,
			int item, int zoom) {
		player.getPlayerAssistant().sendFrame246(4888, zoom, item);
		player.getPlayerAssistant().sendFrame126(header, 4889);
		player.getPlayerAssistant().sendFrame126(one, 4890);
		player.getPlayerAssistant().sendFrame126(two, 4891);
		player.getPlayerAssistant().sendChatInterface(4887);
	}

	public void sendItemChat3(Client c, String header, String one, String two,
			String three, int item, int zoom) {
		player.getPlayerAssistant().sendFrame246(4894, zoom, item);
		player.getPlayerAssistant().sendFrame126(header, 4895);
		player.getPlayerAssistant().sendFrame126(one, 4896);
		player.getPlayerAssistant().sendFrame126(two, 4897);
		player.getPlayerAssistant().sendFrame126(three, 4898);
		player.getPlayerAssistant().sendChatInterface(4893);
	}

	public void sendItemChat4(Client c, String header, String one, String two,
			String three, String four, int item, int zoom) {
		player.getPlayerAssistant().sendFrame246(4901, zoom, item);
		player.getPlayerAssistant().sendFrame126(header, 4902);
		player.getPlayerAssistant().sendFrame126(one, 4903);
		player.getPlayerAssistant().sendFrame126(two, 4904);
		player.getPlayerAssistant().sendFrame126(three, 4905);
		player.getPlayerAssistant().sendFrame126(four, 4906);
		player.getPlayerAssistant().sendChatInterface(4900);
	}
}
