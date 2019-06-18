package redone.game.npcs;

import redone.game.content.quests.QuestAssistant;
import redone.game.content.skills.core.Fishing;
import redone.game.content.skills.crafting.Tanning;
import redone.game.content.skills.thieving.Pickpocket;
import redone.game.content.traveling.Sailing;
import redone.game.players.Client;
import redone.game.players.PlayerAssistant;
import redone.game.shops.ShopAssistant;
import redone.game.shops.Shops;
import redone.util.Misc;

public class NpcActions {

	private final Client c;

	public NpcActions(Client Client) {
		c = Client;
	}

	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		Shops.dialogueShop(c, npcType);
		if (Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
		if (Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 1, npcType);
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't click any npcs, until you confirm you are not botting.");
			c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
			return;
		}
		switch (npcType) {
		  case 209:
	        	c.getDialogueHandler().sendDialogues(3500, 209);
	        break;
		
		case 2238:
			c.getDialogueHandler().sendDialogues(3214, npcType);
		break;
		
		case 958:
			c.getDialogueHandler().sendDialogues(3208, npcType);
		break;

		case 606://squire
		if (c.knightS == 0) {
			c.getDialogueHandler().sendDialogues(610, 606);
		} else if (c.knightS == 4) {
			c.getDialogueHandler().sendDialogues(654, 606);
		} else if (c.knightS == 8) {
			c.getDialogueHandler().sendDialogues(682, 606);
		}
		break;
		case 647://reldo
		if (c.knightS == 1) {
			c.getDialogueHandler().sendDialogues(626, 647);
		}
		break;
		case 604://thurgo
		if (c.knightS == 2) {
			c.getDialogueHandler().sendDialogues(640, 604);
		} else if (c.knightS == 3) {
			c.getDialogueHandler().sendDialogues(648, 604);
		} else if (c.knightS == 6) {
			c.getDialogueHandler().sendDialogues(660, 604);
		} else if (c.knightS == 7) {
			c.getDialogueHandler().sendDialogues(669, 604);
		} else if (c.knightS == 8) {
			c.getDialogueHandler().sendDialogues(674, 604);
		}
		break;
		
		case 693: //rang guild shots
			c.getDialogueHandler().sendDialogues(3201, npcType);
		break;
		
		case 694: //rang guild store
			c.getShopAssistant().openShop(111);
		break;

		case 1834:
			c.getDialogueHandler().sendDialogues(1378, npcType);
			break;

		case 537:
		case 536:
			if (c.questPoints >= QuestAssistant.MAXIMUM_QUESTPOINTS) {
				c.getDialogueHandler().sendDialogues(1373, npcType);
			} else {
				c.getActionSender().sendMessage(
						"You need " + QuestAssistant.MAXIMUM_QUESTPOINTS
								+ " quest points to open this shop.");
			}
			break;

		case 599:
			c.getDialogueHandler().sendDialogues(1369, npcType);
			break;

		case 663:
			c.getDialogueHandler().sendDialogues(3189, npcType);
			break;

		case 802:
			c.getDialogueHandler().sendDialogues(1358, npcType);
			break;

		case 2205:
			c.getDialogueHandler().sendDialogues(1353, npcType);
			break;

		case 3830:
			c.getDialogueHandler().sendDialogues(1349, npcType);
			break;

		case 2270:
			if (c.playerLevel[c.playerThieving] > 98) {
				c.getShopAssistant().openShop(118);
			} else if (c.playerLevel[c.playerThieving] > 49
					&& c.playerLevel[c.playerAgility] > 49) {
				c.getShopAssistant().openShop(118);
			} else {
				c.getActionSender().sendMessage(
						"You don't have the required skills to open this shop");
			}
			break;

		case 1071:
			c.getDialogueHandler().sendDialogues(1345, npcType);
			break;

		case 666:
			c.getDialogueHandler().sendDialogues(3183, npcType);
			break;

		case 510:
			if (c.absY > 3209 && c.absY < 3215) {
				c.getDialogueHandler().sendDialogues(3173, npcType);
			} else {
				c.getDialogueHandler().sendDialogues(3178, npcType);
			}
			break;

		case 1042:
			c.getDialogueHandler().sendDialogues(3167, npcType);
			break;

		case 735:
			c.getDialogueHandler().sendDialogues(3167, npcType);
			break;

		case 36:
			c.getDialogueHandler().sendDialogues(3158, npcType);
			break;

		case 844:
			if (c.runeMist < 4) {
				c.getDialogueHandler().sendStatement(
						"You need to beat rune mysteries first to do this.");
				c.nextChat = 0;
				return;
			}
			c.getDialogueHandler().sendDialogues(3144, npcType);
			break;

		case 798:
			c.getDialogueHandler().sendDialogues(3133, npcType);
			break;

		case 736:
		case 3217:
		case 3218:
			c.getDialogueHandler().sendDialogues(3118, npcType);
			break;

		/*
		 * tutorial island
		 */
		case 945:
			if (c.tutorialProgress == 0) {
				c.getDialogueHandler().sendDialogues(3001, npcType);
			}
			if (c.tutorialProgress == 1) {
				c.getDialogueHandler().sendDialogues(3008, npcType);
			}
			if (c.tutorialProgress == 2) {
				c.getDialogueHandler().sendNpcChat1("You should move on now.",
						npcType, "Runescape Guide");
			}
			break;

		case 943:// survival
			if (c.tutorialProgress == 2) {
				c.getDialogueHandler().sendDialogues(3012, npcType);
			}
			if (c.tutorialProgress == 5) {
				c.getDialogueHandler().sendDialogues(3017, npcType);
			}
			break;

		case 942: // master chef
			if (c.tutorialProgress == 7) {
				c.getDialogueHandler().sendDialogues(3021, npcType);
			}
			break;

		case 949: // quest guide
			if (c.tutorialProgress == 12) {
				c.getDialogueHandler().sendDialogues(3043, npcType);
			}
			if (c.tutorialProgress == 13) {
				c.getDialogueHandler().sendDialogues(3045, npcType);
			}
			break;
		case 948: // mining tutor
			if (c.tutorialProgress == 14) {
				c.getDialogueHandler().sendDialogues(3052, npcType);
			}
			if (c.tutorialProgress == 16) {
				c.getDialogueHandler().sendDialogues(3056, npcType);
			}
			if (c.tutorialProgress == 20) {
				c.getDialogueHandler().sendDialogues(3063, npcType);
			}
			break;
		case 944: // Combat deud
			if (c.tutorialProgress == 21) {
				c.getDialogueHandler().sendDialogues(3067, npcType);
			} else if (c.tutorialProgress == 23
					&& !c.getItemAssistant().playerHasItem(1171)
					&& !c.getItemAssistant().playerHasItem(1277)) {
				c.getDialogueHandler().sendDialogues(3072, npcType);
			} else if (c.getItemAssistant().playerHasItem(1171)
					&& c.getItemAssistant().playerHasItem(1277) && c.tutorialProgress == 23) {
				c.getActionSender().sendMessage(
						"I already gave you a sword and shield.");
				c.nextChat = 0;
				c.getDialogueHandler()
						.chatboxText(
								c,
								"In your worn inventory panel, right click on the dagger and",
								"select the remove option from the drop down list. After you've",
								"unequipped the dagger, wield the sword and shield. As you",
								"pass the mouse over an item you will see its name.",
								"Unequipping items");
				PlayerAssistant.removeHintIcon(c);
			} else if (c.tutorialProgress == 25) {
				c.getDialogueHandler().sendDialogues(3074, npcType);
			}
			break;

		case 947: // fiancial dude
			if (c.tutorialProgress == 27) {
				c.getDialogueHandler().sendDialogues(3079, npcType);
			}
			// c.getPacketDispatcher().createArrow(1, 7);
			break;

		case 954: // prayer dude
			if (c.tutorialProgress == 28) {
				c.getDialogueHandler().sendDialogues(3089, npcType);
			}
			if (c.tutorialProgress == 29) {
				c.getDialogueHandler().sendDialogues(3092, npcType);
			}
			if (c.tutorialProgress == 31) {
				c.getDialogueHandler().sendDialogues(3097, npcType);
			}
			break;
		case 946:// mage
			if (c.tutorialProgress == 32) {
				c.getDialogueHandler().sendDialogues(3105, npcType);
			}
			if (c.tutorialProgress == 33) {
				c.getDialogueHandler().sendDialogues(3108, npcType);
			}
			if (c.tutorialProgress == 34) {
				c.getDialogueHandler().sendDialogues(3110, npcType);
			}
			if (c.tutorialProgress == 35) {
				c.getDialogueHandler().sendDialogues(3112, npcType);
			}
			break;

		case 922:
			c.getDialogueHandler().sendDialogues(1312, npcType);
			break;

		case 805:
			c.getDialogueHandler().sendDialogues(1317, npcType);
			break;

		case 519:
			c.getDialogueHandler().sendDialogues(15, npcType); // barrows fix
															// barrows
			break;

		case 598:
			c.getDialogueHandler().sendDialogues(1300, npcType);
			break;

		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			c.getDialogueHandler().sendDialogues(1228, npcType);
			c.SlayerMaster = npcType;
			break;

		case 1595:
			c.getDialogueHandler().sendDialogues(1036, npcType);
			break;

		case 170:
			c.getDialogueHandler().sendDialogues(591, npcType);
			break;

		case 925:
		case 926:
			c.getDialogueHandler().sendDialogues(1018, npcType);
			break;

		case 2728:
		case 2729:
			c.getDialogueHandler().sendDialogues(1011, npcType);
			break;

		case 376:
		case 377:
		case 378:
			if (c.getItemAssistant().playerHasItem(995, 30)) {
				c.getDialogueHandler().sendDialogues(33, npcType);
			} else {
				c.getDialogueHandler().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		case 380:
			if (c.getItemAssistant().playerHasItem(995, 30)) {
				c.getDialogueHandler().sendDialogues(584, npcType);
			} else {
				c.getDialogueHandler().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		/**
		 * Start of Quests
		 */

		case 557:
			if (c.ptjob == 0) {
				c.getDialogueHandler().sendDialogues(37, npcType);
			} else if (c.ptjob == 1) {
				c.getDialogueHandler().sendDialogues(47, npcType);
			} else if (c.ptjob == 2) {
				c.getDialogueHandler().sendDialogues(1000, npcType);
			}
			break;

		case 375:
			if (c.pirateTreasure == 0) {
				c.getDialogueHandler().sendDialogues(554, npcType);
			} else if (c.pirateTreasure == 1) {
				c.getDialogueHandler().sendStatement(
						"Talk to lucas and help him transport the bannanas.");
			} else if (c.pirateTreasure == 2) {
				c.getDialogueHandler().sendDialogues(569, npcType);
			} else if (c.pirateTreasure == 3) {
				c.getDialogueHandler().sendDialogues(580, npcType);
			} else {
				c.getActionSender().sendMessage(
						"Arr! Thanks for me helping me.");
			}
			break;

		case 307:
			if (c.witchspot == 0) {
				c.getDialogueHandler().sendDialogues(532, npcType);
			} else if (c.witchspot == 1) {
				c.getDialogueHandler().sendDialogues(546, npcType);
			} else if (c.witchspot == 2) {
				c.getDialogueHandler().sendDialogues(548, npcType);
			} else if (c.witchspot == 3) {
				c.getDialogueHandler().sendNpcChat1(
						"Welcome back, thank you again for helping me.",
						c.talkingNpc, "Hetty");
			}
			break;

		case 755:// morgan
			if (c.vampSlayer == 3) {
				c.getDialogueHandler().sendDialogues(531, npcType);
			} else if (c.vampSlayer == 4) {
				c.getDialogueHandler().sendDialogues(529, npcType);
			} else if (c.vampSlayer == 0) {
				c.getDialogueHandler().sendDialogues(476, npcType);
			}
			break;

		case 743:// ned
			if (c.vampSlayer == 0) {
				c.getDialogueHandler().sendDialogues(211, npcType);
			} else if (c.vampSlayer == 1) {
				c.getDialogueHandler().sendStatement("I should go find harlow.");
			} else if (c.vampSlayer > 1) {
				c.getDialogueHandler().sendDialogues(1337, npcType);
			}
			break;

		case 756:// harlow
			if (c.vampSlayer == 1) {
				c.getDialogueHandler().sendDialogues(510, npcType);
			} else if (c.vampSlayer == 3) {
				c.getDialogueHandler().sendDialogues(531, npcType);
			} else {
				c.getDialogueHandler().sendStatement("I'm not on this step yet.");
			}
			break;

		case 456:
			if (c.restGhost == 0) {
				c.getDialogueHandler().sendDialogues(338, 456);
			}
			break;

		case 457:
			if (c.restGhost == 2) {
				c.getDialogueHandler().sendDialogues(371, npcType);
			}
			break;

		case 458:
			if (c.restGhost == 1) {
				c.getDialogueHandler().sendDialogues(352, npcType);
			}
			break;

		case 759:
			if (c.getItemAssistant().playerHasItem(1927, 1) && c.gertCat == 2) {
				c.getDialogueHandler().sendDialogues(319, npcType);
				c.getItemAssistant().deleteItem2(1927, 1);
				c.getItemAssistant().addItem(1925, 1);
				c.gertCat = 3;
			} else if (c.getItemAssistant().playerHasItem(1552, 1)
					&& c.gertCat == 3) {
				c.getDialogueHandler().sendDialogues(323, npcType);
				c.getItemAssistant().deleteItem2(1552, 1);
				c.gertCat = 4;
			} else if (c.gertCat == 4) {
				c.getDialogueHandler().sendStatement("Hiss!");
				c.getDialogueHandler().sendDialogues(325, npcType);
				c.gertCat = 5;
			} else if (c.getItemAssistant().playerHasItem(1554, 1)
					&& c.gertCat == 6) {
				c.getItemAssistant().deleteItem2(1554, 1);
				c.getDialogueHandler().sendDialogues(326, npcType);
				c.gertCat = 6;
			} else if (c.gertCat == 2) {
				c.getActionSender().sendMessage("Hiss!");
				c.getDialogueHandler().sendStatement("Fluffs hisses but clearly wants something - maybe she is thirsty?");
			}
			break;

		case 780:
			if (c.playerLevel[10] < 4) {
				c.getDialogueHandler().sendStatement(
						"You don't have the requirements to do this quest.");
				return;
			}
			if (c.gertCat == 0) {
				c.getDialogueHandler().sendDialogues(269, npcType);
			} else if (c.gertCat == 1) {
				c.getDialogueHandler().sendDialogues(276, npcType);
			} else if (c.gertCat == 6) {
				c.getDialogueHandler().sendDialogues(328, npcType);
			} else {
				c.getDialogueHandler()
						.sendStatement("She has nothing to say to you.");
			}
			break;

		case 783:
			if (c.gertCat == 1) {
				c.getDialogueHandler().sendDialogues(286, npcType);
			} else if (c.gertCat == 2) {
				c.getDialogueHandler().sendDialogues(314, npcType);
			}
			break;

		case 639:
			if (c.romeojuliet == 0) {
				c.getDialogueHandler().sendDialogues(389, npcType);
			} else if (c.romeojuliet == 1) {
				c.getDialogueHandler().sendDialogues(408, npcType);
			} else if (c.romeojuliet == 3) {
				c.getDialogueHandler().sendDialogues(415, npcType);
			} else if (c.romeojuliet == 4) {
				c.getDialogueHandler().sendDialogues(424, npcType);
			} else if (c.romeojuliet == 5) {
				c.getDialogueHandler().sendDialogues(431, npcType);
			} else if (c.romeojuliet == 6) {
				c.getDialogueHandler().sendDialogues(443, npcType);
			} else if (c.romeojuliet == 8) {
				c.getDialogueHandler().sendDialogues(469, npcType);
			} else if (c.romeojuliet == 9) {
				c.getActionSender().sendMessage("Thanks for helping me!");
			}
			if (c.romeojuliet == 2
					&& c.getItemAssistant().playerHasItem(755, 1)) {
				c.getDialogueHandler().sendDialogues(415, npcType);
			}
			if (c.romeojuliet == 2
					&& !c.getItemAssistant().playerHasItem(755, 1)) {
				c.getDialogueHandler().sendDialogues(421, npcType);
			}
			break;

		case 276:
			if (c.romeojuliet == 5) {
				c.getDialogueHandler().sendDialogues(432, npcType);
			}
			if (c.romeojuliet == 6
					&& c.getItemAssistant().playerHasItem(300, 1)
					&& c.getItemAssistant().playerHasItem(227, 1)
					&& c.getItemAssistant().playerHasItem(526, 1)) {
				c.getDialogueHandler().sendDialogues(448, npcType);
			} else {
				if (c.romeojuliet == 6) {
					c.getDialogueHandler().sendDialogues(439, npcType);
				}
			}
			break;

		case 637:
			if (c.romeojuliet == 0) {
				c.getDialogueHandler().sendDialogues(409, npcType);
			} else if (c.romeojuliet == 1) {
				c.getDialogueHandler().sendDialogues(410, npcType);
			} else if (c.romeojuliet == 2) {
				c.getDialogueHandler().sendDialogues(414, npcType);
			} else if (c.romeojuliet == 7) {
				c.getDialogueHandler().sendDialogues(457, npcType);
			} else if (c.romeojuliet == 8) {
				c.getDialogueHandler().sendDialogues(468, npcType);
			}
			break;

		case 741:
			c.getDialogueHandler().sendDialogues(190, npcType);
			break;

		case 553:
			if (c.runeMist == 2) {
				c.getDialogueHandler().sendDialogues(229, npcType);
			} else if (c.runeMist == 3) {
				c.getDialogueHandler().sendDialogues(237, npcType);
			}
			break;

		case 300:
			if (c.runeMist == 1) {
				c.getDialogueHandler().sendDialogues(201, npcType);
			} else if (c.runeMist == 2) {
				c.getDialogueHandler().sendDialogues(213, npcType);
			} else if (c.runeMist == 3) {
				c.getDialogueHandler().sendDialogues(238, npcType);
			} else if (c.runeMist > 3 || c.runeMist < 1) {
				c.getActionSender().sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 284:
			if (c.playerLevel[14] < 14) {
				c.getDialogueHandler().sendStatement(
						"You don't have the requirements to do this quest.");
				return;
			}
			if (c.doricQuest == 0) {
				c.getDialogueHandler().sendDialogues(89, npcType);
			} else if (c.doricQuest == 1) {
				c.getDialogueHandler().sendDialogues(84, npcType);
			} else if (c.doricQuest == 2) {
				c.getDialogueHandler().sendDialogues(86, npcType);
			} else if (c.doricQuest == 3) {
				c.getDialogueHandler().sendDialogues(100, npcType);
			}
			break;

		case 706:
			if (c.impsC == 0) {
				c.getDialogueHandler().sendDialogues(145, npcType);
			} else if (c.impsC == 1) {
				c.getDialogueHandler().sendDialogues(156, npcType);
			}
			if (c.impsC == 1 && c.getItemAssistant().playerHasItem(1470, 1)
					&& c.getItemAssistant().playerHasItem(1472, 1)
					&& c.getItemAssistant().playerHasItem(1474, 1)
					&& c.getItemAssistant().playerHasItem(1476, 1)) {
				c.getDialogueHandler().sendDialogues(158, npcType);
			} else if (c.impsC == 1) {
				c.getDialogueHandler().sendDialogues(157, npcType);
			}
			break;

		case 278:
			if (c.cookAss == 0) {
				c.getDialogueHandler().sendDialogues(50, npcType);
			} else if (c.cookAss == 1) {
				c.getDialogueHandler().sendDialogues(67, npcType);
			} else if (c.cookAss == 2) {
				c.getDialogueHandler().sendDialogues(69, npcType);
			} else if (c.cookAss == 3) {
				c.getDialogueHandler().sendDialogues(76, npcType);
			}
			break;

		case 758:
			if (c.sheepShear == 0) {
				c.getDialogueHandler().sendDialogues(164, npcType);
			} else if (c.sheepShear == 1) {
				c.getDialogueHandler().sendDialogues(185, 1);
			} else {
				c.getActionSender().sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 379:
			if (c.bananas == 0 || c.luthas == false) {
				c.getDialogueHandler().sendDialogues(8, npcType);
			} else if (c.bananas > 0) {
				c.getDialogueHandler().sendDialogues(4, npcType);
			} else {
				c.getActionSender()
						.sendMessage(
								"You may now talk to Luthas your bananna task has been reset.");
				c.luthas = false;
				c.bananas = 0;
			}
			break;

		/**
		 * End of Quests
		 */

		case 2294:
			c.getDialogueHandler().sendDialogues(24, npcType);
			break;

		case 2296:
			c.getDialogueHandler().sendDialogues(26, npcType);
			break;

		case 500:
			c.getDialogueHandler().sendDialogues(21, npcType);
			break;

		case 659:
			c.getDialogueHandler().sendDialogues(18, npcType);
			break;

		case 2244:
			c.getDialogueHandler().sendDialogues(14, npcType);
			break;

		case 641:
			c.getDialogueHandler().sendDialogues(11, npcType);
			break;

		case 2458:
			c.getDialogueHandler().sendDialogues(2, npcType);
			break;

		case 731:
			c.getDialogueHandler().sendDialogues(19, npcType);
			break;

		case 732:
			c.getDialogueHandler().sendDialogues(3150, npcType);
			break;

		/**
		 * Bankers
		 */
		case 953:
		case 166:
		case 1702: 
		case 495: 
		case 496:
		case 497:
		case 498: 
		case 499: 
		case 567: 
		case 1036: 
		case 1360: 
		case 2163: 
		case 2164:
		case 2354: 
		case 2355: 
		case 2568: 
		case 2569: 
		case 2570: 
		case 2271: 
		case 494: 
		case 2619:
			c.getDialogueHandler().sendDialogues(1013, npcType);
		break;

		case 1152:
			c.getDialogueHandler().sendDialogues(16, npcType);
			break;

		case 905:
			c.getDialogueHandler().sendDialogues(5, npcType);
			break;

		case 460:
			c.getDialogueHandler().sendDialogues(3, npcType);
			break;

		case 462:
			c.getDialogueHandler().sendDialogues(3149, npcType);
			break;

		case 658:
			Sailing.startTravel(c, 2);
			break;
			
		case 2437:
		case 2438:
		if (!c.getItemAssistant().playerHasItem(995, 1000)) {
			c.getDialogueHandler().sendStatement("You need 1000 coins to go here!");
			c.nextChat = 0;
			return;
		}
		if (c.absX > 2619 && c.absX < 2622 && c.absY > 3680 && c.absY < 3689 && c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 18);
			c.getPlayerAssistant().startTeleport(2551, 3759, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDialogueHandler().sendStatement("You arrive safely.");
			c.nextChat = 0;
		} else {
			 if (c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 17);	
			c.getPlayerAssistant().startTeleport(2620, 3686, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDialogueHandler().sendStatement("You arrive safely.");
			c.nextChat = 0;
			 }
		}
		break;

		case 381:
			if (c.absY > 3230 && c.absY < 3236) {
				Sailing.startTravel(c, 8);
			} else {
				Sailing.startTravel(c, 7);
			}
			break;

		case 3506:
		case 3507:
		case 761:
		case 760:
		case 762:
		case 763:
		case 764:
		case 765:
		case 766:
		case 767:
		case 768:
		case 769:
		case 770:
		case 771:
		case 772:
		case 773:
		case 3505:
			c.getSummon().pickUpClean(c, c.summonId);
			c.hasNpc = false;
			c.summonId = 0;
			break;

		case 804:
		case 1041:
			Tanning.sendTanningInterface(c);
			break;

		case 657:
			Sailing.startTravel(c, 1);
			break;

		case 8689:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				if (c.getItemAssistant().playerHasItem(1925, 1)) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItemAssistant().addItem(1927, 1);
					c.getItemAssistant().deleteItem2(1925, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.getActionSender().sendMessage(
							"You need a bucket to milk a cow!");
				}
			}
			break;

		case 3789:
			c.getActionSender().sendMessage(
					new StringBuilder().append("You currently have ")
							.append(c.pcPoints).append(" pest control points.")
							.toString());
			break;

		/* Shops */

		/*
		 * case 588: c.getShops().openShop(2); break; case 550:
		 * c.getShops().openShop(3); break; case 575: c.getShops().openShop(4);
		 * break; case 2356: c.getShops().openShop(5); break; case 3796:
		 * c.getShops().openShop(6); break; case 1860: c.getShops().openShop(7);
		 * break; case 559: c.getShops().openShop(9); break; case 562:
		 * c.getShops().openShop(10); break; case 581:
		 * c.getShops().openShop(11); break; case 548:
		 * c.getShops().openShop(12); break; case 554:
		 * c.getShops().openShop(13); break; case 601:
		 * c.getShops().openShop(14); break; case 1301:
		 * c.getShops().openShop(15); break; case 1039:
		 * c.getShops().openShop(16); break; case 2353:
		 * c.getShops().openShop(17); break; case 3166:
		 * c.getShops().openShop(18); break; case 2161:
		 * c.getShops().openShop(19); break; case 2162:
		 * c.getShops().openShop(20); break; case 600:
		 * c.getShops().openShop(21); break; case 603:
		 * c.getShops().openShop(22); break; case 593:
		 * c.getShops().openShop(23); break; case 545:
		 * c.getShops().openShop(24); break; case 585:
		 * c.getShops().openShop(25); break; case 2305:
		 * c.getShops().openShop(26); break; case 2307:
		 * c.getShops().openShop(27); break; case 2304:
		 * c.getShops().openShop(28); break; case 2306:
		 * c.getShops().openShop(29); break; case 517:
		 * c.getShops().openShop(30); break; case 558:
		 * c.getShops().openShop(31); break; case 576:
		 * c.getShops().openShop(32); break; case 1369:
		 * c.getShops().openShop(33); break; case 1038:
		 * c.getShops().openShop(35); break; case 1433:
		 * c.getShops().openShop(36); break; case 584:
		 * c.getShops().openShop(37); break; case 540:
		 * c.getShops().openShop(38); break; case 2157:
		 * c.getShops().openShop(39); break; case 538:
		 * c.getShops().openShop(40); break; case 1303:
		 * c.getShops().openShop(41); break; case 578:
		 * c.getShops().openShop(42); break; case 587:
		 * c.getShops().openShop(43); break; case 1398:
		 * c.getShops().openShop(44); break; case 556:
		 * c.getShops().openShop(45); break; case 1865:
		 * c.getShops().openShop(46); break; case 543:
		 * c.getShops().openShop(47); break; case 2198:
		 * c.getShops().openShop(48); break; case 580:
		 * c.getShops().openShop(49); break; case 1862:
		 * c.getShops().openShop(50); break; case 583:
		 * c.getShops().openShop(51); break; case 553:
		 * c.getShops().openShop(52); break; case 461:
		 * c.getShops().openShop(53); break; case 903:
		 * c.getShops().openShop(54); break; case 1435:
		 * c.getShops().openShop(56); break; case 3800:
		 * c.getShops().openShop(57); break; case 2623:
		 * c.getShops().openShop(58); break; case 594:
		 * c.getShops().openShop(59); break; case 579:
		 * c.getShops().openShop(60); break; case 2160:
		 * c.getShops().openShop(61); break; case 2191:
		 * c.getShops().openShop(61); break; case 589:
		 * c.getShops().openShop(62); break; case 549:
		 * c.getShops().openShop(63); break; case 542:
		 * c.getShops().openShop(64); break; case 3038:
		 * c.getShops().openShop(65); break; case 544:
		 * c.getShops().openShop(66); break; case 541:
		 * c.getShops().openShop(67); break; case 1434:
		 * c.getShops().openShop(68); break; case 577:
		 * c.getShops().openShop(69); break; case 539:
		 * c.getShops().openShop(70); break; case 1980:
		 * c.getShops().openShop(71); break; case 546:
		 * c.getShops().openShop(72); break; case 382:
		 * c.getShops().openShop(73); break; case 3541:
		 * c.getShops().openShop(74); break; case 520:
		 * c.getShops().openShop(75); break; case 1436:
		 * c.getShops().openShop(76); break; case 590:
		 * c.getShops().openShop(77); break; case 971:
		 * c.getShops().openShop(78); break; case 1917:
		 * c.getShops().openShop(79); break; case 1040:
		 * c.getShops().openShop(80); break; case 563:
		 * c.getShops().openShop(81); break; case 522:
		 * c.getShops().openShop(82); break; case 524:
		 * c.getShops().openShop(83); break; case 526:
		 * c.getShops().openShop(84); break; case 2154:
		 * c.getShops().openShop(85); break; case 1334:
		 * c.getShops().openShop(86); break; case 2552:
		 * c.getShops().openShop(87); break; case 528:
		 * c.getShops().openShop(88); break; case 1254:
		 * c.getShops().openShop(89); break; case 2086:
		 * c.getShops().openShop(90); break; case 3824:
		 * c.getShops().openShop(91); break; case 1866:
		 * c.getShops().openShop(92); break; case 1699:
		 * c.getShops().openShop(93); break; case 1282:
		 * c.getShops().openShop(94); break; case 530:
		 * c.getShops().openShop(95); break; case 516:
		 * c.getShops().openShop(96); break; case 560:
		 * c.getShops().openShop(97); break; case 471:
		 * c.getShops().openShop(98); break; case 1208:
		 * c.getShops().openShop(99); break; case 532:
		 * c.getShops().openShop(100); break; case 555:
		 * c.getShops().openShop(101); break; case 534:
		 * c.getShops().openShop(102); break; case 551:
		 * c.getShops().openShop(104); break; case 586:
		 * c.getShops().openShop(105); break; case 564:
		 * c.getShops().openShop(106); break; case 747:
		 * c.getShops().openShop(107); break; case 573:
		 * c.getShops().openShop(108); break; case 1316:
		 * c.getShops().openShop(108); break; case 1787:
		 * c.getShops().openShop(110); break; case 694:
		 * c.getShops().openShop(111); break; case 1526:
		 * c.getShops().openShop(112); break; case 568:
		 * c.getShops().openShop(113); break; case 1079:
		 * c.getShops().openShop(114); break;
		 */

		}
	}

	public void secondClickNpc(int npcType) {
		String type = c.playerMagicBook == 0 ? "modern" : "ancient";
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		Shops.openShop(c, npcType);
		if (Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
		if (Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 2, npcType);
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't click any npcs, until you confirm you are not botting.");
			c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
			return;
		}
		if (npcType >= 761 && npcType <= 773 || npcType > 3504
				&& npcType < 3508 && npcType != 767) {
			c.getDialogueHandler().sendDialogues(908, npcType);
		}

		switch (npcType) {
		
		 case 209:
	        	c.getShopAssistant().openShop(144);
	        break;
	        
	        
		case 2437:
		case 2438:
		if (!c.getItemAssistant().playerHasItem(995, 1000)) {
			c.getDialogueHandler().sendStatement("You need 1000 coins to go here!");
			return;
		}
		if (c.absX > 2619 && c.absX < 2622 && c.absY > 3680 && c.absY < 3689 && c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 18);
			c.getPlayerAssistant().startTeleport(2551, 3759, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDialogueHandler().sendStatement("You arrive safely.");
			c.nextChat = 0;
		} else {
			 if (c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 17);	
			c.getPlayerAssistant().startTeleport(2620, 3686, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDialogueHandler().sendStatement("You arrive safely.");
			c.nextChat = 0;
			 }
		}
		break;

		case 537:
		case 536:
			if (c.questPoints >= QuestAssistant.MAXIMUM_QUESTPOINTS) {
				c.getShopAssistant().openShop(npcType);
			} else {
				c.getActionSender().sendMessage(
						"You need 19 quest points to open this shop.");
			}
			break;

		case 300:
			if (c.runeMist < 4) {
				c.getDialogueHandler().sendStatement(
						"You need to beat rune mysteries first to do this.");
				c.nextChat = 0;
				return;
			}
			c.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 557:
			c.getShopAssistant().openShop(34);
			break;

		case 804:
		case 1041:
			Tanning.sendTanningInterface(c);
			break;

		case 2270:
			if (c.playerLevel[c.playerThieving] > 98) {
				c.getShopAssistant().openShop(118);
			} else if (c.playerLevel[c.playerThieving] > 49
					&& c.playerLevel[c.playerAgility] > 49) {
				c.getShopAssistant().openShop(118);
			} else {
				c.getActionSender().sendMessage(
						"You don't have the required skills to open this shop");
			}
			break;

		case 1042:
			if (c.getItemAssistant().playerHasItem(995, 5)) {
				c.getItemAssistant().addItem(2955, 1);
				c.getItemAssistant().deleteItem2(995, 5);
			} else {
				c.getDialogueHandler().sendNpcChat1(
						"You need 5 coins to buy a moonlight mead.",
						c.talkingNpc, "Roavar");
				c.nextChat = 0;
			}
			break;

		case 844:
		case 462:
			if (c.runeMist < 4) {
				c.getDialogueHandler().sendStatement(
						"You need to beat rune mysteries first to do this.");
				c.nextChat = 0;
				return;
			}
			c.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 519:
			c.getShopAssistant().openShop(8);
			break;

		case 1595:
			c.getDialogueHandler().sendDialogues(1053, npcType);
			break;

			/**
			 * Bankers
			 */
			case 953:
			case 166:
			case 1702: 
			case 495: 
			case 496:
			case 497:
			case 498: 
			case 499: 
			case 567: 
			case 1036: 
			case 1360: 
			case 2163: 
			case 2164:
			case 2354: 
			case 2355: 
			case 2568: 
			case 2569: 
			case 2570: 
			case 2271: 
			case 494: 
			case 2619:
				c.getPlayerAssistant().openUpBank();
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		if (Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't click any npcs, until you confirm you are not botting.");
			c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
			return;
		}
		if (npcType >= 761 && npcType <= 773 && npcType != 767) {
			if (NpcHandler.npcs[c.rememberNpcIndex].spawnedBy == c.playerId)
				c.getDialogueHandler().sendDialogues(910, npcType);
			else
				c.getActionSender().sendMessage("This isn't your cat.");
		}
		switch (npcType) {
		
		/**
		 * Banker
		 */
		case 3824:
			c.getPlayerAssistant().openUpBank();
		break;
		
		case 958:
			c.getShopAssistant().openShop(143);
		break;

		case 1526:
			c.getShopAssistant().openShop(ShopAssistant.CASTLE_SHOP);
		break;
		
		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			c.getShopAssistant().openShop(109);
			break;

		case 836:
			if (c.getItemAssistant().playerHasItem(995, 5)) {
				c.getActionSender().sendMessage(
						"You buy a shantay pass quickly.");
				c.getItemAssistant().deleteItem2(995, 5);
				c.getItemAssistant().addItem(1854, 1);
			} else {
				c.getActionSender().sendMessage(
						"You need 5 coins to buy a pass.");
			}
			break;
		case 553:
			if (c.runeMist < 4) {
				c.getDialogueHandler().sendStatement(
						"You need to beat rune mysteries first to do this.");
				return;
			}
			String type = c.playerMagicBook == 0 ? "modern" : "ancient";
			c.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;
		default:
			if (c.playerRights == 3) {
				Misc.println("Third Click NPC : " + npcType);
			}
			break;

		}
	}

}
