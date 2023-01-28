package com.rs2.game.npcs;

import com.rs2.GameConstants;
import com.rs2.game.content.StaticNpcList;
import com.rs2.game.content.minigames.magetrainingarena.MageTrainingArena;
import com.rs2.game.content.quests.QuestAssistant;
import com.rs2.game.content.skills.core.Fishing;
import com.rs2.game.content.skills.crafting.Tanning;
import com.rs2.game.content.skills.thieving.Pickpocket;
import com.rs2.game.content.traveling.Sailing;
import com.rs2.game.npcs.impl.Pets;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerAssistant;
import com.rs2.game.shops.ShopAssistant;
import com.rs2.game.shops.Shops;
import com.rs2.util.Misc;

public class NpcActions {

	private final Player player;

	public NpcActions(Player player2) {
		player = player2;
	}

	public void firstClickNpc(int npcType) {
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;
		Shops.dialogueShop(player, npcType);
		if (Fishing.fishingNPC(player, npcType)) {
			Fishing.fishingNPC(player, 1, npcType);
		}
		if (Pets.isCat(npcType)) {
			if (NpcHandler.npcs[player.rememberNpcIndex].spawnedBy == player.playerId) {
				player.getSummon().pickUpPet(player, player.summonId);
				player.hasNpc = false;
				player.summonId = -1;
			} else {
				player.getPacketSender().sendMessage("This is not your pet.");
			}
		}
		switch (npcType) {
			case StaticNpcList.THORMAC:
				player.getDialogueHandler().sendDialogues(3574, npcType);
				break;
			case StaticNpcList.GHOST_DISCIPLE:
				player.getDialogueHandler().sendDialogues(1390, npcType);
				break;
			case StaticNpcList.GHOST_CAPTAIN:
			case StaticNpcList.GHOST_CAPTAIN_1705:
				player.getDialogueHandler().sendDialogues(1400, npcType);
				break;
			case StaticNpcList.ELSTAN:
			case StaticNpcList.DANTAERA:
			case StaticNpcList.KRAGEN:
			case StaticNpcList.LYRA:
			case StaticNpcList.FRANCIS:
			case StaticNpcList.GARTH:
			case StaticNpcList.ELLENA:
			case StaticNpcList.SELENA:
			case StaticNpcList.VASQUEN:
			case StaticNpcList.RHONEN:

		case 2337 :
		case 2336 :
		case 2335 :
		case 2338 :
		case 2343 :
		case 2344 :
		case 2340 :
		case 2339 :
		case 2341 :
		case 2342 :
			player.getDialogueHandler().sendDialogues(3530, npcType);
			break;
		  	case StaticNpcList.NULODION:
	        	player.getDialogueHandler().sendDialogues(3500, StaticNpcList.NULODION);
	        	break;
			case StaticNpcList.DONIE:
				player.getDialogueHandler().sendDialogues(3214, npcType);
				break;
			case StaticNpcList.BILL_TEACH_3156:
			case StaticNpcList.BILL_TEACH_3157:
				player.getDialogueHandler().sendDialogues(1410, npcType);
				break;
			case StaticNpcList.FADLI:
				player.getDialogueHandler().sendDialogues(3208, npcType);
				break;
			case StaticNpcList.SQUIRE:
		if (player.knightS == 0) {
			player.getDialogueHandler().sendDialogues(610, 606);
		} else if (player.knightS == 4) {
			player.getDialogueHandler().sendDialogues(654, 606);
		} else if (player.knightS == 8) {
			player.getDialogueHandler().sendDialogues(682, 606);
		}
		break;
		case 647://reldo
		if (player.knightS == 1) {
			player.getDialogueHandler().sendDialogues(626, 647);
		}
        else if (player.shieldArrav == 0) {
            player.getDialogueHandler().sendDialogues(690, 647);
        }
        else if (player.shieldArrav == 1) {
            player.getDialogueHandler().sendDialogues(694, 647);
        }
        else if (player.shieldArrav == 2) {
            player.getDialogueHandler().sendDialogues(697, 647);
        }
		break;
		case 604://thurgo
		if (player.knightS == 2) {
			player.getDialogueHandler().sendDialogues(640, 604);
		} else if (player.knightS == 3) {
			player.getDialogueHandler().sendDialogues(648, 604);
		} else if (player.knightS == 6) {
			player.getDialogueHandler().sendDialogues(660, 604);
		} else if (player.knightS == 7) {
			player.getDialogueHandler().sendDialogues(669, 604);
		} else if (player.knightS == 8) {
			player.getDialogueHandler().sendDialogues(674, 604);
		}
		break;

		case 693: //rang guild shots
			player.getDialogueHandler().sendDialogues(3201, npcType);
			break;

		case 694: //rang guild store
			player.getShopAssistant().openShop(111);
			break;

		case 1834:
			player.getDialogueHandler().sendDialogues(1378, npcType);
			break;

		case 1835:
			if (player.easterEvent == 0)
				player.getDialogueHandler().sendDialogues(6000, npcType);
			else if (player.easterEvent == 1)
			{
				int easter1 = player.getInventory().getItemAmount(7928);
				int easter2 = player.getInventory().getItemAmount(7929);
				int easter3 = player.getInventory().getItemAmount(7930);
				int easter4 = (easter1 + easter2 + easter3);
				if (easter4 >= 3)
					player.getDialogueHandler().sendDialogues(6017, npcType);
				else {
					player.getDialogueHandler().sendDialogues(6014, npcType);
				}
			}
			else
				{
					player.getDialogueHandler().sendDialogues(6022, npcType);
				}
			break;

		case 537:
		case 536:
			int requiredQP = Math.min(32, QuestAssistant.MAXIMUM_QUESTPOINTS);
			if (player.questPoints >= requiredQP) {
				player.getDialogueHandler().sendDialogues(1373, npcType);
			} else {
				player.getPacketSender().sendMessage(
						"You need " + requiredQP + " quest points to open this shop.");
			}
			break;

		case 547: //Baraek
			if (player.shieldArrav == 3) {
				player.getDialogueHandler().sendDialogues(701, npcType);
			}
			break;

		case 599:
			player.getDialogueHandler().sendDialogues(1369, npcType);
			break;
		case 649:
			player.getDialogueHandler().sendDialogues(3840, npcType);
			break;
		case 650:
			player.getDialogueHandler().sendDialogues(3578, npcType);
			break;
		case 651:
			player.getDialogueHandler().sendDialogues(3840, npcType);
			break;
		case 652:
			player.getDialogueHandler().sendDialogues(3840, npcType);
			break;
		case 654:
			player.getDialogueHandler().sendDialogues(3848, npcType);
			break;

		case 644: //Straven
			if (player.shieldArrav <= 4) {
				player.getDialogueHandler().sendDialogues(711, npcType);
			}
			else if (player.shieldArrav == 5) {
				player.getDialogueHandler().sendDialogues(730, npcType);
			}
			else if (player.shieldArrav > 5) {
				player.getDialogueHandler().sendDialogues(741, npcType);
			}
			break;

		case 646: //Curator Haig Halen
			player.getDialogueHandler().sendDialogues(745, npcType);
			break;

		case 648: //King Roald
			if (player.shieldArrav == 7 && player.getItemAssistant().playerHasItem(769))
				player.getDialogueHandler().sendDialogues(756, npcType);
			break;

		case 663:
			player.getDialogueHandler().sendDialogues(3189, npcType);
			break;

		case 802:
			player.getDialogueHandler().sendDialogues(1358, npcType);
			break;

		case 2205:
			player.getDialogueHandler().sendDialogues(1353, npcType);
			break;

		case 3830:
			player.getDialogueHandler().sendDialogues(1349, npcType);
			break;

		case 2270:
			if (player.playerLevel[GameConstants.THIEVING] > 98) {
				player.getShopAssistant().openShop(118);
			} else if (player.playerLevel[GameConstants.THIEVING] > 49
					&& player.playerLevel[GameConstants.AGILITY] > 49) {
				player.getShopAssistant().openShop(118);
			} else {
				player.getPacketSender().sendMessage(
						"You don't have the required skills to open this shop");
			}
			break;

		case 1071:
			player.getDialogueHandler().sendDialogues(1345, npcType);
			break;

		case 666:
			player.getDialogueHandler().sendDialogues(3183, npcType);
			break;

		case 510:
			if (player.absY > 3209 && player.absY < 3215) {
				player.getDialogueHandler().sendDialogues(3173, npcType);
			} else {
				player.getDialogueHandler().sendDialogues(3178, npcType);
			}
			break;

		case 1042:
			player.getDialogueHandler().sendDialogues(3167, npcType);
			break;

		case 735:
			player.getDialogueHandler().sendDialogues(3167, npcType);
			break;

		case 36:
			player.getDialogueHandler().sendDialogues(3158, npcType);
			break;

		case 844:
			if (player.runeMist < 4 && player.playerRights <= 1) {
				player.getDialogueHandler().sendStatement("You need to beat rune mysteries first to do this.");
				player.nextChat = 0;
				return;
			}
			player.getDialogueHandler().sendDialogues(3144, npcType);
			break;

		case 798:
			player.getDialogueHandler().sendDialogues(3133, npcType);
			break;

		case 736:
		case 3217:
		case 3218:
			player.getDialogueHandler().sendDialogues(3118, npcType);
			break;

		/*
		 * tutorial island
		 */
		case 945:
			if (player.tutorialProgress == 0) {
				player.getDialogueHandler().sendDialogues(3001, npcType);
			}
			if (player.tutorialProgress == 1) {
				player.getDialogueHandler().sendDialogues(3008, npcType);
			}
			if (player.tutorialProgress == 2) {
				player.getDialogueHandler().sendNpcChat1("You should move on now.", npcType, "Runescape Guide");
			}
			break;

		case 943:// survival
			if (player.tutorialProgress == 2) {
				player.getDialogueHandler().sendDialogues(3012, npcType);
			}
			if (player.tutorialProgress == 5) {
				player.getDialogueHandler().sendDialogues(3017, npcType);
			}
			break;

		case 942: // master chef
			if (player.tutorialProgress == 7) {
				player.getDialogueHandler().sendDialogues(3021, npcType);
			}
			break;

		case 949: // quest guide
			if (player.tutorialProgress == 12) {
				player.getDialogueHandler().sendDialogues(3043, npcType);
			}
			if (player.tutorialProgress == 13) {
				player.getDialogueHandler().sendDialogues(3045, npcType);
			}
			break;
		case 948: // mining tutor
			if (player.tutorialProgress == 14) {
				player.getDialogueHandler().sendDialogues(3052, npcType);
			}
			if (player.tutorialProgress == 16) {
				player.getDialogueHandler().sendDialogues(3056, npcType);
			}
			if (player.tutorialProgress == 20) {
				player.getDialogueHandler().sendDialogues(3063, npcType);
			}
			break;
		case 944: // Combat deud
			if (player.tutorialProgress == 21) {
				player.getDialogueHandler().sendDialogues(3067, npcType);
			} else if (player.tutorialProgress == 23
					&& !player.getItemAssistant().playerHasItem(1171)
					&& !player.getItemAssistant().playerHasItem(1277)) {
				player.getDialogueHandler().sendDialogues(3072, npcType);
			} else if (player.getItemAssistant().playerHasItem(1171)
					&& player.getItemAssistant().playerHasItem(1277) && player.tutorialProgress == 23) {
				player.getPacketSender().sendMessage(
						"I already gave you a sword and shield.");
				player.nextChat = 0;
				player.getDialogueHandler()
						.chatboxText(
								"In your worn inventory panel, right click on the dagger and",
								"select the remove option from the drop down list. After you've",
								"unequipped the dagger, wield the sword and shield. As you",
								"pass the mouse over an item you will see its name.",
								"Unequipping items");
				PlayerAssistant.removeHintIcon(player);
			} else if (player.tutorialProgress == 25) {
				player.getDialogueHandler().sendDialogues(3074, npcType);
			}
			break;

		case 947: // fiancial dude
			if (player.tutorialProgress == 27) {
				player.getDialogueHandler().sendDialogues(3079, npcType);
			}
			// c.getPacketDispatcher().createArrow(1, 7);
			break;

		case 954: // prayer dude
			if (player.tutorialProgress == 28) {
				player.getDialogueHandler().sendDialogues(3089, npcType);
			}
			if (player.tutorialProgress == 29) {
				player.getDialogueHandler().sendDialogues(3092, npcType);
			}
			if (player.tutorialProgress == 31) {
				player.getDialogueHandler().sendDialogues(3097, npcType);
			}
			break;
		case 946:// mage
			if (player.tutorialProgress == 32) {
				player.getDialogueHandler().sendDialogues(3105, npcType);
			}
			if (player.tutorialProgress == 33) {
				player.getDialogueHandler().sendDialogues(3108, npcType);
			}
			if (player.tutorialProgress == 34) {
				player.getDialogueHandler().sendDialogues(3110, npcType);
			}
			if (player.tutorialProgress == 35) {
				player.getDialogueHandler().sendDialogues(3112, npcType);
			}
			break;

		case 922:
			player.getDialogueHandler().sendDialogues(1312, npcType);
			break;

		case 805:
			player.getDialogueHandler().sendDialogues(1317, npcType);
			break;

		case 519:
			player.getDialogueHandler().sendDialogues(15, npcType); // barrows fix
															// barrows
			break;

		case 598:
			player.getDialogueHandler().sendDialogues(1300, npcType);
			break;

		case 216:
			player.getDialogueHandler().sendDialogues(3867, npcType);
			break;

		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			player.getDialogueHandler().sendDialogues(1228, npcType);
			player.SlayerMaster = npcType;
			break;

		case 1595:
			player.getDialogueHandler().sendDialogues(1036, npcType);
			break;

		case 170:
			player.getDialogueHandler().sendDialogues(591, npcType);
			break;

		case 925:
		case 926:
			player.getDialogueHandler().sendDialogues(1018, npcType);
			break;

		case 2728:
		case 2729:
			player.getDialogueHandler().sendDialogues(1011, npcType);
			break;

		case 376:
		case 377:
		case 378:
			if (player.getItemAssistant().playerHasItem(995, 30)) {
				player.getDialogueHandler().sendDialogues(33, npcType);
			} else {
				player.getDialogueHandler().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		case 380:
			if (player.getItemAssistant().playerHasItem(995, 30)) {
				player.getDialogueHandler().sendDialogues(584, npcType);
			} else {
				player.getDialogueHandler().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		/**
		 * Start of Quests
		 */

		case 557:
			if (player.ptjob == 0) {
				player.getDialogueHandler().sendDialogues(37, npcType);
			} else if (player.ptjob == 1) {
				player.getDialogueHandler().sendDialogues(47, npcType);
			} else if (player.ptjob == 2) {
				player.getDialogueHandler().sendDialogues(1000, npcType);
			}
			break;

		case 375:
			if (player.pirateTreasure == 0) {
				player.getDialogueHandler().sendDialogues(554, npcType);
			} else if (player.pirateTreasure == 1) {
				player.getDialogueHandler().sendStatement(
						"Talk to lucas and help him transport the bannanas.");
			} else if (player.pirateTreasure == 2) {
				player.getDialogueHandler().sendDialogues(569, npcType);
			} else if (player.pirateTreasure == 3) {
				player.getDialogueHandler().sendDialogues(580, npcType);
			} else {
				player.getPacketSender().sendMessage(
						"Arr! Thanks for me helping me.");
			}
			break;

		case 307:
			if (player.witchspot == 0) {
				player.getDialogueHandler().sendDialogues(532, npcType);
			} else if (player.witchspot == 1) {
				player.getDialogueHandler().sendDialogues(546, npcType);
			} else if (player.witchspot == 2) {
				player.getDialogueHandler().sendDialogues(548, npcType);
			} else if (player.witchspot == 3) {
				player.getDialogueHandler().sendNpcChat1(
						"Welcome back, thank you again for helping me.",
						player.talkingNpc, "Hetty");
			}
			break;

		case 755:// morgan
			if (player.vampSlayer == 3) {
				player.getDialogueHandler().sendDialogues(531, npcType);
			} else if (player.vampSlayer == 4) {
				player.getDialogueHandler().sendDialogues(529, npcType);
			} else if (player.vampSlayer == 0) {
				player.getDialogueHandler().sendDialogues(476, npcType);
			}
			break;

		case 743:// ned
			if (player.vampSlayer == 0) {
				player.getDialogueHandler().sendDialogues(211, npcType);
			} else if (player.vampSlayer == 1) {
				player.getDialogueHandler().sendStatement("I should go find harlow.");
			} else if (player.vampSlayer > 1) {
				player.getDialogueHandler().sendDialogues(1337, npcType);
			}
			break;

		case 756:// harlow
			if (player.vampSlayer == 1) {
				player.getDialogueHandler().sendDialogues(498, npcType);
			} else if (player.vampSlayer == 2) {
				player.getDialogueHandler().sendDialogues(510, npcType);
			} else if (player.vampSlayer == 3) {
				player.getDialogueHandler().sendDialogues(531, npcType);
			} else {
				player.getDialogueHandler().sendStatement("I'm not on this step yet.");
			}
			break;

		case 456:
			if (player.restGhost == 0) {
				player.getDialogueHandler().sendDialogues(338, 456);
			}
			break;

		case 457:
			if (player.restGhost == 2) {
				player.getDialogueHandler().sendDialogues(371, npcType);
			}
			break;

		case 458:
			if (player.restGhost == 1) {
				player.getDialogueHandler().sendDialogues(352, npcType);
			}
			break;

		case 759:
			if (player.getItemAssistant().playerHasItem(1927, 1) && player.gertCat == 2) {
				player.getDialogueHandler().sendDialogues(319, npcType);
				player.getItemAssistant().deleteItem(1927, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.gertCat = 3;
			} else if (player.getItemAssistant().playerHasItem(1552, 1)
					&& player.gertCat == 3) {
				player.getDialogueHandler().sendDialogues(323, npcType);
				player.getItemAssistant().deleteItem(1552, 1);
				player.gertCat = 4;
			} else if (player.gertCat == 4) {
				player.getDialogueHandler().sendStatement("Hiss!");
				player.getDialogueHandler().sendDialogues(325, npcType);
				player.gertCat = 5;
			} else if (player.getItemAssistant().playerHasItem(1554, 1)
					&& player.gertCat == 6) {
				player.getItemAssistant().deleteItem(1554, 1);
				player.getDialogueHandler().sendDialogues(326, npcType);
				player.gertCat = 6;
			} else if (player.gertCat == 2) {
				player.getPacketSender().sendMessage("Hiss!");
				player.getDialogueHandler().sendStatement("Fluffs hisses but clearly wants something - maybe she is thirsty?");
			}
			break;

		case 780:
			if (player.playerLevel[GameConstants.FISHING] < 4) {
				player.getDialogueHandler().sendStatement(
						"You don't have the requirements to do this quest.");
				return;
			}
			if (player.gertCat == 0) {
				player.getDialogueHandler().sendDialogues(269, npcType);
			} else if (player.gertCat == 1) {
				player.getDialogueHandler().sendDialogues(276, npcType);
			} else if (player.gertCat == 6) {
				player.getDialogueHandler().sendDialogues(328, npcType);
			} else {
				player.getDialogueHandler()
						.sendStatement("She has nothing to say to you.");
			}
			break;

		case 783:
			if (player.gertCat == 1) {
				player.getDialogueHandler().sendDialogues(286, npcType);
			} else if (player.gertCat == 2) {
				player.getDialogueHandler().sendDialogues(314, npcType);
			}
			break;

		case 639:
			if (player.romeojuliet == 0) {
				player.getDialogueHandler().sendDialogues(389, npcType);
			} else if (player.romeojuliet == 1) {
				player.getDialogueHandler().sendDialogues(408, npcType);
			} else if (player.romeojuliet == 3) {
				player.getDialogueHandler().sendDialogues(415, npcType);
			} else if (player.romeojuliet == 4) {
				player.getDialogueHandler().sendDialogues(424, npcType);
			} else if (player.romeojuliet == 5) {
				player.getDialogueHandler().sendDialogues(431, npcType);
			} else if (player.romeojuliet == 6) {
				player.getDialogueHandler().sendDialogues(443, npcType);
			} else if (player.romeojuliet == 8) {
				player.getDialogueHandler().sendDialogues(469, npcType);
			} else if (player.romeojuliet == 9) {
				player.getPacketSender().sendMessage("Thanks for helping me!");
			}
			if (player.romeojuliet == 2
					&& player.getItemAssistant().playerHasItem(755, 1)) {
				player.getDialogueHandler().sendDialogues(415, npcType);
			}
			if (player.romeojuliet == 2
					&& !player.getItemAssistant().playerHasItem(755, 1)) {
				player.getDialogueHandler().sendDialogues(421, npcType);
			}
			break;

		case 276:
			if (player.romeojuliet == 5) {
				player.getDialogueHandler().sendDialogues(432, npcType);
			}
			if (player.romeojuliet == 6
					&& player.getItemAssistant().playerHasItem(300, 1)
					&& player.getItemAssistant().playerHasItem(227, 1)
					&& player.getItemAssistant().playerHasItem(526, 1)) {
				player.getDialogueHandler().sendDialogues(448, npcType);
			} else {
				if (player.romeojuliet == 6) {
					player.getDialogueHandler().sendDialogues(439, npcType);
				}
			}
			break;

		case 637:
			if (player.romeojuliet == 0) {
				player.getDialogueHandler().sendDialogues(409, npcType);
			} else if (player.romeojuliet == 1) {
				player.getDialogueHandler().sendDialogues(410, npcType);
			} else if (player.romeojuliet == 2) {
				player.getDialogueHandler().sendDialogues(414, npcType);
			} else if (player.romeojuliet == 7) {
				player.getDialogueHandler().sendDialogues(457, npcType);
			} else if (player.romeojuliet == 8) {
				player.getDialogueHandler().sendDialogues(468, npcType);
			}
			break;

		case 741:
			player.getDialogueHandler().sendDialogues(190, npcType);
			break;

		case 553:
			if (player.runeMist == 2) {
				player.getDialogueHandler().sendDialogues(229, npcType);
			} else if (player.runeMist == 3) {
				player.getDialogueHandler().sendDialogues(237, npcType);
			}
			break;

		case 300:
			if (player.runeMist == 1) {
				player.getDialogueHandler().sendDialogues(201, npcType);
			} else if (player.runeMist == 2) {
				player.getDialogueHandler().sendDialogues(213, npcType);
			} else if (player.runeMist == 3) {
				player.getDialogueHandler().sendDialogues(238, npcType);
			} else if (player.runeMist > 3 || player.runeMist < 1) {
				player.getPacketSender().sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 284:
			if (player.doricQuest == 0) {
				player.getDialogueHandler().sendDialogues(89, npcType);
			} else if (player.doricQuest == 1) {
				player.getDialogueHandler().sendDialogues(84, npcType);
			} else if (player.doricQuest == 2) {
				player.getDialogueHandler().sendDialogues(86, npcType);
			} else if (player.doricQuest == 3) {
				player.getDialogueHandler().sendDialogues(100, npcType);
			}
			break;

		case 706:
			if (player.impsC == 0) {
				player.getDialogueHandler().sendDialogues(145, npcType);
			} else if (player.impsC == 1) {
				player.getDialogueHandler().sendDialogues(156, npcType);
			}
			if (player.impsC == 1 && player.getItemAssistant().playerHasItem(1470, 1)
					&& player.getItemAssistant().playerHasItem(1472, 1)
					&& player.getItemAssistant().playerHasItem(1474, 1)
					&& player.getItemAssistant().playerHasItem(1476, 1)) {
				player.getDialogueHandler().sendDialogues(158, npcType);
			} else if (player.impsC == 1) {
				player.getDialogueHandler().sendDialogues(157, npcType);
			}
			break;

		case 278:
			if (player.cookAss == 0) {
				player.getDialogueHandler().sendDialogues(50, npcType);
			} else if (player.cookAss == 1) {
				player.getDialogueHandler().sendDialogues(67, npcType);
			} else if (player.cookAss == 2) {
				player.getDialogueHandler().sendDialogues(69, npcType);
			} else if (player.cookAss == 3) {
				player.getDialogueHandler().sendDialogues(76, npcType);
			}
			break;

		case 608:
			if (player.blackKnight == 0 && player.questPoints >= 12) {
				player.getDialogueHandler().sendDialogues(3902, npcType);
			} else if (player.blackKnight == 1) {
				player.getDialogueHandler().sendDialogues(3510, npcType);
			}else if (player.blackKnight == 2) {
				player.getDialogueHandler().sendDialogues(3502, npcType);
			}else if (player.blackKnight == 3) {
				player.getPacketSender().sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 758:
			if (player.sheepShear == 0) {
				player.getDialogueHandler().sendDialogues(164, npcType);
			} else if (player.sheepShear == 1) {
				player.getDialogueHandler().sendDialogues(185, 1);
			} else {
				player.getPacketSender().sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 379:
			if (player.bananas == 0 || player.luthas == false) {
				player.getDialogueHandler().sendDialogues(8, npcType);
			} else if (player.bananas > 0) {
				player.getDialogueHandler().sendDialogues(4, npcType);
			} else {
				player.getPacketSender()
						.sendMessage(
								"You may now talk to Luthas your bananna task has been reset.");
				player.luthas = false;
				player.bananas = 0;
			}
			break;

		/**
		 * End of Quests
		 */

		case 2293:
			if (player.absY >= 2939 && player.absY <= 2945) {
				player.getDialogueHandler().sendDialogues(3565, npcType);
			} else {
				player.getDialogueHandler().sendDialogues(3570, npcType);
			}
			break;

		case 2294:
			player.getDialogueHandler().sendDialogues(3555, npcType);
			break;

		case 2296:
			player.getDialogueHandler().sendDialogues(3559, npcType);
			break;

		case 659:
			player.getDialogueHandler().sendDialogues(18, npcType);
			break;

		case 2244:
			player.getDialogueHandler().sendDialogues(14, npcType);
			break;

		case 641:
			player.getDialogueHandler().sendDialogues(11, npcType);
			break;

		case 2458:
			player.getDialogueHandler().sendDialogues(2, npcType);
			break;

		case 731:
			player.getDialogueHandler().sendDialogues(19, npcType);
			break;

		case 732:
			player.getDialogueHandler().sendDialogues(3150, npcType);
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
			player.getDialogueHandler().sendDialogues(1013, npcType);
		break;

		case 1152:
			player.getDialogueHandler().sendDialogues(16, npcType);
			break;

		case 905:
			player.getDialogueHandler().sendDialogues(5, npcType);
			break;

		case 460:
			player.getDialogueHandler().sendDialogues(3, npcType);
			break;

		case 462:
			player.getDialogueHandler().sendDialogues(3149, npcType);
			break;

		case 658:
			Sailing.startTravel(player, 2);
			break;

		case 2437:
		case 2438:
		if (!player.getItemAssistant().playerHasItem(995, 1000)) {
			player.getDialogueHandler().sendStatement("You need 1000 coins to go here!");
			player.nextChat = 0;
			return;
		}
		if (player.absX > 2619 && player.absX < 2622 && player.absY > 3680 && player.absY < 3689 && player.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 18);
			player.getPlayerAssistant().startTeleport(2551, 3759, 0, "modern");
			player.getItemAssistant().deleteItem(995, 1000);
			player.getDialogueHandler().sendStatement("You arrive safely.");
			player.nextChat = 0;
		} else {
			 if (player.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 17);
			player.getPlayerAssistant().startTeleport(2620, 3686, 0, "modern");
			player.getItemAssistant().deleteItem(995, 1000);
			player.getDialogueHandler().sendStatement("You arrive safely.");
			player.nextChat = 0;
			 }
		}
		break;

		case 381:
			if (player.absY > 3230 && player.absY < 3236) {
				Sailing.startTravel(player, 8);
			} else {
				Sailing.startTravel(player, 7);
			}
			break;

		case 804:
		case 1041:
			Tanning.sendTanningInterface(player);
			break;

		case 657:
			Sailing.startTravel(player, 1);
			break;

		case 8689:
			if (System.currentTimeMillis() - player.buryDelay > 1500) {
				if (player.getItemAssistant().playerHasItem(1925, 1)) {
					player.turnPlayerTo(player.objectX, player.objectY);
					player.startAnimation(2292);
					player.getItemAssistant().addItem(1927, 1);
					player.getItemAssistant().deleteItem(1925, 1);
					player.buryDelay = System.currentTimeMillis();
				} else {
					player.getPacketSender().sendMessage(
							"You need a bucket to milk a cow!");
				}
			}
			break;

		case 3789:
			player.getPacketSender().sendMessage(
					new StringBuilder().append("You currently have ")
							.append(player.pcPoints).append(" pest control points.")
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
		String type = player.playerMagicBook == 0 ? "modern" : "ancient";
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;
		Shops.openShop(player, npcType);
		if (Fishing.fishingNPC(player, npcType)) {
			Fishing.fishingNPC(player, 2, npcType);
		}
		if (Pets.isCat(npcType)) {
			if (NpcHandler.npcs[player.rememberNpcIndex].spawnedBy == player.playerId) {
				player.getDialogueHandler().sendDialogues(908, npcType);
			} else {
				player.getPacketSender().sendMessage("This is not your pet.");
			}
		}
		switch (npcType) {
		case 3021:
			player.getFarmingTools().loadInterfaces();
			break;
			
		 /*case 209:
	        player.getShopAssistant().openShop(144);
	        break;*/


		case 2437:
		case 2438:
		if (!player.getItemAssistant().playerHasItem(995, 1000)) {
			player.getDialogueHandler().sendStatement("You need 1000 coins to go here!");
			return;
		}
		if (player.absX > 2619 && player.absX < 2622 && player.absY > 3680 && player.absY < 3689 && player.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 18);
			player.getPlayerAssistant().startTeleport(2551, 3759, 0, "modern");
			player.getItemAssistant().deleteItem(995, 1000);
			player.getDialogueHandler().sendStatement("You arrive safely.");
			player.nextChat = 0;
		} else {
			 if (player.getItemAssistant().playerHasItem(995, 1000)) {
				//Sailing.startTravel(c, 17);
				player.getPlayerAssistant().startTeleport(2620, 3686, 0, "modern");
				player.getItemAssistant().deleteItem(995, 1000);
				player.getDialogueHandler().sendStatement("You arrive safely.");
				player.nextChat = 0;
			}
		}
		break;

		case 537:
		case 536:
			int requiredQP = Math.min(32, QuestAssistant.MAXIMUM_QUESTPOINTS);
			if (player.questPoints >= requiredQP) {
				player.getShopAssistant().openShop(npcType);
			} else {
				player.getPacketSender().sendMessage("You need " + requiredQP + " quest points to open this shop.");
			}
			break;

		case 300:
			if (player.runeMist < 4 && player.playerRights <= 1) {
				player.getDialogueHandler().sendStatement("You need to beat rune mysteries first to do this.");
				player.nextChat = 0;
				return;
			}
			player.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 557:
			player.getShopAssistant().openShop(34);
			break;

		case 804:
		case 1041:
			Tanning.sendTanningInterface(player);
			break;

		case 2270:
			if (player.playerLevel[GameConstants.THIEVING] > 98) {
				player.getShopAssistant().openShop(118);
			} else if (player.playerLevel[GameConstants.THIEVING] > 49
					&& player.playerLevel[GameConstants.AGILITY] > 49) {
				player.getShopAssistant().openShop(118);
			} else {
				player.getPacketSender().sendMessage(
						"You don't have the required skills to open this shop");
			}
			break;

		case 1042:
			if (player.getItemAssistant().playerHasItem(995, 5)) {
				player.getItemAssistant().addItem(2955, 1);
				player.getItemAssistant().deleteItem(995, 5);
			} else {
				player.getDialogueHandler().sendNpcChat1(
						"You need 5 coins to buy a moonlight mead.",
						player.talkingNpc, "Roavar");
				player.nextChat = 0;
			}
			break;

		case 844:
		case 462:
			if (player.runeMist < 4 && player.playerRights <= 1) {
				player.getDialogueHandler().sendStatement("You need to beat rune mysteries first to do this.");
				player.nextChat = 0;
				return;
			}
			player.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 519:
			player.getShopAssistant().openShop(8);
			break;

		case 1595:
			player.getDialogueHandler().sendDialogues(1053, npcType);
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
				player.getPacketSender().openUpBank();
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;
		if (Pets.isCat(npcType)) {
			if (NpcHandler.npcs[player.rememberNpcIndex].spawnedBy == player.playerId) {
				player.getDialogueHandler().sendDialogues(910, npcType);
			} else {
				player.getPacketSender().sendMessage("This is not your pet.");
			}
		}
		switch (npcType) {

		/**
		 * Banker
		 */
		case 3824:
			player.getPacketSender().openUpBank();
		break;

		case 958:
			player.getShopAssistant().openShop(143);
		break;

		case 1526:
			player.getShopAssistant().openShop(ShopAssistant.CASTLE_SHOP);
		break;

		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			player.getShopAssistant().openShop(109);
			break;

		case 836:
			if (player.getItemAssistant().playerHasItem(995, 5)) {
				player.getPacketSender().sendMessage(
						"You buy a shantay pass quickly.");
				player.getItemAssistant().deleteItem(995, 5);
				player.getItemAssistant().addItem(1854, 1);
			} else {
				player.getPacketSender().sendMessage(
						"You need 5 coins to buy a pass.");
			}
			break;
		case 553:
			if (player.runeMist < 4 && player.playerRights <= 1) {
				player.getDialogueHandler().sendStatement("You need to beat rune mysteries first to do this.");
				return;
			}
			String type = player.playerMagicBook == 0 ? "modern" : "ancient";
			player.getPlayerAssistant().startTeleport(2911, 4832, 0, type);
			break;

		case 2258:
			if (player.playerLevel[GameConstants.RUNECRAFTING] < 35) {
				player.getPacketSender().sendMessage("You need a Runecrafting level of 35 to enter the Abyss.");
				return;
			}
			player.getPlayerAssistant().spellTeleport(3027, 4852, 0);
			break;

		case 3103: // Mage arena point shop
			player.getMageTrainingArena().openShop();

		default:
			if (player.playerRights == 3) {
				Misc.println("Third Click NPC : " + npcType);
			}
			break;

		}
	}

}
