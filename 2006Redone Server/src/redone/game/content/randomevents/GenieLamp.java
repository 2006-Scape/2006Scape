package redone.game.content.randomevents;

import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author Aintaro
 */

public class GenieLamp {

	private static final int GENIE_LAMP1 = 7498;// , GENIE_LAMP2 = 6543;

	private static int expAmount;

	public static boolean spawnGenieNpc(Client c) {
		if (Misc.random(100) == 1 && !c.inWild()) {
			NpcHandler.spawnNpc(c, 409, c.absX, c.absY + 1, 0, 0, 0, 0, 0, 0,
					false, false);
			return true;
		}
		return false;
	}

	public static void removeGenieNpc(Client c) {
		NpcHandler.npcs[c.lastNpcClickIndex].absX = 0;
		NpcHandler.npcs[c.lastNpcClickIndex].absY = 0;
	}

	public static void rubGenieLamp(Client c, int itemId) {
		if (itemId == GENIE_LAMP1) {// || itemId == GENIE_LAMP2) {
			c.getActionSender().sendMessage("You rub the lamp...");
			c.getPlayerAssistant().showInterface(2808);
		}
	}

	private static int[][] genieData = { { 10252, 0 }, { 11000, 1 },
			{ 10253, 2 }, { 11001, 3 }, { 10254, 4 }, { 11002, 5 },
			{ 10255, 6 }, { 11011, 7 }, { 11013, 8 }, { 11014, 9 },
			{ 11010, 10 }, { 11012, 11 }, { 11006, 12 }, { 11009, 13 },
			{ 11008, 14 }, { 11004, 15 }, { 11003, 16 }, { 11005, 17 },
			{ 47002, 18 }, { 54090, 19 }, { 11007, 20 }, };
	public String statName[] = { "attack", "defence", "strength", "hitpoints",
			"range", "prayer", "magic", "cooking", "woodcutting", "fletching",
			"fishing", "firemaking", "crafting", "smithing", "mining",
			"herblore", "agility", "thieving", "slayer", "farming",
			"runecrafting" };

	private static void whatSkillAreWeLookingFor(Client c, int actionButtonId) {
		for (int[] genie : genieData) {
			if (genie[0] == actionButtonId) {
				if (c.getItemAssistant().playerHasItem(GENIE_LAMP1, 1)) {
					c.getItemAssistant().deleteItem(GENIE_LAMP1, 1);
					expAmount = 10 * c.playerLevel[genie[1]];
					c.getPlayerAssistant().addSkillXP(expAmount, genie[1]);
					// c.getDH().sendStatement(
					// "You gained " + expAmount * Config.SERVER_EXP_BONUS
					// + " experience.");
					c.nextChat = 0;
					break;
					/*
					 * } else if
					 * (c.getItemAssistant().playerHasItem(GENIE_LAMP2, 1)) {
					 * c.getItemAssistant().deleteItem(GENIE_LAMP2, 1);
					 * expAmount = 10 * c.playerLevel[genie[1]];
					 * c.getPlayerAssistant().addSkillXP(expAmount, genie[1]);
					 * //c.getDH().sendStatement( //"You gained " + expAmount *
					 * Config.SERVER_EXP_BONUS // + " experience."); c.nextChat
					 * = 0; break;
					 */
				}
			}
		}
	}

	public static boolean genieInterfaceButtons(Client c, int actionButtonId) {
		switch (actionButtonId) {
		case 10252:
		case 10253:
		case 10254:
		case 10255:
		case 11000:
		case 11001:
		case 11002:
		case 11003:
		case 11004:
		case 11005:
		case 11006:
		case 11007:
		case 47002:
		case 54090:
		case 11008:
		case 11009:
		case 11010:
		case 11011:
		case 11012:
		case 11013:
		case 11014:
			whatSkillAreWeLookingFor(c, actionButtonId);
			return true;
		}
		return false;
	}
}
