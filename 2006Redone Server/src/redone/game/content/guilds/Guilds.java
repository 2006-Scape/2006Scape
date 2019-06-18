package redone.game.content.guilds;

import redone.Constants;
import redone.game.content.quests.QuestAssistant;
import redone.game.globalworldobjects.PassDoor;
import redone.game.objects.impl.Climbing;
import redone.game.objects.impl.UseOther;
import redone.game.players.Client;

/**
 * Guilds Class
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Guilds {

	private static boolean hasCompletedLegends = false;

	public static void attemptGuild(Client c, int objectId) {
		if (!Constants.GUILDS) {
			c.getActionSender().sendMessage("Guilds are currently disabled.");
			return;
		}
		if (checkRequirments(c, objectId)) {
			c.getActionSender().sendMessage("You pass through the guild.");
			movePlayer(c, objectId);
		}
	}

	public static void movePlayer(Client c, int objectId) {
		switch (objectId) {
		case 2514: // Range Guild
			if (c.absX == 2659 && c.absY == 3438) {
				PassDoor.passThroughDoor(c, objectId, 1, 2, 9, -1, 1, 0);
			} else if (c.absX == 2658 && c.absY == 3437) {
				PassDoor.passThroughDoor(c, objectId, 1, 2, 9, -1, 1, 0);
			} else if (c.absX == 2658 && c.absY == 3439) {
				PassDoor.passThroughDoor(c, objectId, 1, 2, 9, 1, -1, 0);
			} else if (c.absX == 2657 && c.absY == 3438) {
				PassDoor.passThroughDoor(c, objectId, 1, 2, 9, 1, -1, 0);
			}
			break;
		case 1805: // Champions Guild
			if (c.absY == 3362 && c.absX != 3192 && c.absX != 3190) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
			} else if (c.absY == 3363 && c.absX != 3192 && c.absX != 3190) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
			}
			break;
		case 2641: // Monastery
			if (c.heightLevel == 0) {
				Climbing.climbUp(c);
			} else if (c.heightLevel == 1) {
				Climbing.climbDown(c);
			}
			break;
		case 2712: // Cooks Guild
			if (c.absY == 3443) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
			} else if (c.absY == 3444) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
			}
			break;
		case 2647: // Crafting Guild
			if (c.absY == 3289) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
			} else if (c.absY == 3288) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
			}
			break;
		case 2113: // Mining Guild
			UseOther.useDown(c, objectId);
			break;
		case 1755: // Mining Guild
			UseOther.useUp(c, objectId);
			break;
		case 2025: // Fishing Guild
			if (c.absY == 3393) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
			} else if (c.absY == 3394) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
			}
			break;
		case 1600: // Wizards Guild
		case 1601:
			if (c.absX == 2597) {
				c.getPlayerAssistant().movePlayer(c.absX - 1, c.absY, 0);
			} else if (c.absX == 2596) {
				c.getPlayerAssistant().movePlayer(c.absX + 1, c.absY, 0);
			}
			break;
		case 2624: // Heroes Guild
		case 2625:
			if (c.absX == 2902) {
				c.getPlayerAssistant().movePlayer(c.absX - 1, c.absY, 0);
			} else if (c.absX == 2901) {
				c.getPlayerAssistant().movePlayer(c.absX + 1, c.absY, 0);
			}
			break;
		case 2392: // Legends Guild
		case 2391:
			if (c.absY == 3349) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY + 1, 0);
			} else if (c.absY == 3350) {
				c.getPlayerAssistant().movePlayer(c.absX, c.absY - 1, 0);
			}
			break;
		default:
			c.getActionSender().sendMessage(
					"You can't access this guild from here.");
			break;
		}
	}

	public static boolean checkRequirments(Client c, int objectId) {
		switch (objectId) {
		case 1805: // Champions Guild
			if (c.questPoints < QuestAssistant.MAXIMUM_QUESTPOINTS) {
				c.getDialogueHandler().sendStatement("You need " + QuestAssistant.MAXIMUM_QUESTPOINTS + " quest points to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2392:
		case 2391:
			if (hasCompletedLegends == false && c.playerRights < 3) {
				c.getDialogueHandler().sendStatement("You need to complete Legends Quest to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2641: // Prayer Guild
			if (c.getPlayerAssistant().getLevelForXP(c.playerXP[5]) < 31) {
				c.getDialogueHandler().sendStatement("You need 31 prayer to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2712: // Cooking Guild
			if (c.playerLevel[c.playerCooking] < 32 || c.playerEquipment[c.playerHat] != 1949) {
				c.getDialogueHandler().sendStatement("You need 32 cooking and a chefs hat to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2647: // Crafting Guild
			if (c.playerLevel[c.playerCrafting] < 40 || c.playerEquipment[c.playerChest] != 1757) {
				c.getDialogueHandler().sendStatement("You need 40 Crafting and a Brown Apron to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2113: // Mining Guild
			if (c.playerLevel[c.playerMining] < 60) {
				c.getDialogueHandler().sendStatement("You need 60 Mining to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2025: // Fishing Guild
			if (c.playerLevel[c.playerFishing] < 68) {
				c.getDialogueHandler().sendStatement("You need 68 Fishing to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 1600:
		case 1601: // Wizards Guild
			if (c.playerLevel[c.playerMagic] < 66) {
				c.getDialogueHandler().sendStatement("You need 66 Magic to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		case 2514:
			if (c.playerLevel[c.playerRanged] < 40) { // Ranging Guild
				c.getDialogueHandler().sendStatement("You need 40 Range to enter this guild!");
				c.nextChat = 0;
				return false;
			}
			break;
		}
		return true;
	}
}
