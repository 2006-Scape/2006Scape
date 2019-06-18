package redone.game.items.impl;

import redone.game.players.Client;

public class ExperienceLamp {

	public static int LAMP = 4447, LAMP_2 = 2528, SKILL_MENU = 2808,
			skill = -1;

	/**
	 * Handles the actionbuttons
	 * 
	 * @param c
	 * @param id
	 */
	public static void buttons(Client c, int id) {
		switch (id) {
		case 10252:
			skill = 0;
			c.getActionSender().sendMessage("You select Attack.");
			break;
		case 10253:
			skill = 2;
			c.getActionSender().sendMessage("You select Strength.");
			break;
		case 10254:
			skill = 4;
			c.getActionSender().sendMessage("You select Ranged.");
			break;
		case 10255:
			skill = 6;
			c.getActionSender().sendMessage("You select Magic.");
			break;
		case 11000:
			skill = 1;
			c.getActionSender().sendMessage("You select Defence.");
			break;
		case 11001:
			skill = 3;
			c.getActionSender().sendMessage("You select Hitpoints.");
			break;
		case 11002:
			skill = 5;
			c.getActionSender().sendMessage("You select Prayer.");
			break;
		case 11003:
			skill = 16;
			c.getActionSender().sendMessage("You select Agility.");
			break;
		case 11004:
			skill = 15;
			c.getActionSender().sendMessage("You select Herblore.");
			break;
		case 11005:
			skill = 17;
			c.getActionSender().sendMessage("You select Thieving.");
			break;
		case 11006:
			skill = 12;
			c.getActionSender().sendMessage("You select Crafting.");
			break;
		case 11007:
			skill = 20;
			c.getActionSender().sendMessage("You select Runecrafting.");
			break;
		case 47002:
			skill = 18;
			c.getActionSender().sendMessage("You select Slayer.");
			break;
		case 54090:
			skill = -1;
			c.getActionSender().sendMessage("You can't select this skill.");
			break;
		case 11008:
			skill = 14;
			c.getActionSender().sendMessage("You select Mining.");
			break;
		case 11009:
			skill = 13;
			c.getActionSender().sendMessage("You select Smithing.");
			break;
		case 11010:
			skill = 10;
			c.getActionSender().sendMessage("You select Fishing.");
			break;
		case 11011:
			skill = 7;
			c.getActionSender().sendMessage("You select Cooking.");
			break;
		case 11012:
			skill = 11;
			c.getActionSender().sendMessage("You select Firemaking.");
			break;
		case 11013:
			skill = 8;
			c.getActionSender().sendMessage("You select Woodcutting.");
			break;
		case 11014:
			skill = 9;
			c.getActionSender().sendMessage("You select Fletching.");
			break;
		case 11015:
			if (skill == -1) {
				c.getPlayerAssistant().removeAllWindows();
			}
			if (c.getItemAssistant().playerHasItem(LAMP, 1) && skill > -1) {// normal
																			// lamp
				int xp = c.getPlayerAssistant()
						.getLevelForXP(c.playerXP[skill]) * 10;
				c.getPlayerAssistant().addNormalExperienceRate(xp, skill);
				c.getItemAssistant().deleteItem2(LAMP, 1);
				c.getActionSender().sendMessage(
						"@blu@Your wish has been granted!");
				c.getActionSender().sendMessage(
						"@blu@You have been awarded " + xp
								+ " experience in your selected skill!");
				c.getPlayerAssistant().removeAllWindows();
			} else if (c.getItemAssistant().playerHasItem(LAMP_2, 1)
					&& skill > -1) {// vote
									// reward
				c.getItemAssistant().deleteItem2(LAMP_2, 1);
				c.getActionSender().sendMessage(
						"@blu@Your wish has been granted!");
				addExp(c);
				c.getPlayerAssistant().removeAllWindows();
			}
			break;
		}
	}

	public static void addExp(Client c) {
		if (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) < 20) {
			c.getPlayerAssistant().addNormalExperienceRate(1000, skill);
			c.getActionSender()
					.sendMessage(
							"@blu@You have been awarded 1000 experience in your selected skill!");
		} else if (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) > 19
				&& c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) < 35) {
			c.getPlayerAssistant().addNormalExperienceRate(2000, skill);
			c.getActionSender()
					.sendMessage(
							"@blu@You have been awarded 2000 experience in your selected skill!");
		} else {
			c.getPlayerAssistant().addNormalExperienceRate(3000, skill);
			c.getActionSender()
					.sendMessage(
							"@blu@You have been awarded 3000 experience in your selected skill!");
		}
	}

	/**
	 * Rubbing the lamp. ClickItem
	 * 
	 * @param c
	 * @param id
	 */
	public static void rubLamp(Client c, int id) {
		c.getActionSender().sendMessage("You rub the lamp.");
		c.getPlayerAssistant().showInterface(SKILL_MENU);
	}

}
