package com.rebotted.game.items.impl;

import com.rebotted.game.players.Client;

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
			c.getPacketSender().sendMessage("You select Attack.");
			break;
		case 10253:
			skill = 2;
			c.getPacketSender().sendMessage("You select Strength.");
			break;
		case 10254:
			skill = 4;
			c.getPacketSender().sendMessage("You select Ranged.");
			break;
		case 10255:
			skill = 6;
			c.getPacketSender().sendMessage("You select Magic.");
			break;
		case 11000:
			skill = 1;
			c.getPacketSender().sendMessage("You select Defence.");
			break;
		case 11001:
			skill = 3;
			c.getPacketSender().sendMessage("You select Hitpoints.");
			break;
		case 11002:
			skill = 5;
			c.getPacketSender().sendMessage("You select Prayer.");
			break;
		case 11003:
			skill = 16;
			c.getPacketSender().sendMessage("You select Agility.");
			break;
		case 11004:
			skill = 15;
			c.getPacketSender().sendMessage("You select Herblore.");
			break;
		case 11005:
			skill = 17;
			c.getPacketSender().sendMessage("You select Thieving.");
			break;
		case 11006:
			skill = 12;
			c.getPacketSender().sendMessage("You select Crafting.");
			break;
		case 11007:
			skill = 20;
			c.getPacketSender().sendMessage("You select Runecrafting.");
			break;
		case 47002:
			skill = 18;
			c.getPacketSender().sendMessage("You select Slayer.");
			break;
		case 54090:
			skill = -1;
			c.getPacketSender().sendMessage("You can't select this skill.");
			break;
		case 11008:
			skill = 14;
			c.getPacketSender().sendMessage("You select Mining.");
			break;
		case 11009:
			skill = 13;
			c.getPacketSender().sendMessage("You select Smithing.");
			break;
		case 11010:
			skill = 10;
			c.getPacketSender().sendMessage("You select Fishing.");
			break;
		case 11011:
			skill = 7;
			c.getPacketSender().sendMessage("You select Cooking.");
			break;
		case 11012:
			skill = 11;
			c.getPacketSender().sendMessage("You select Firemaking.");
			break;
		case 11013:
			skill = 8;
			c.getPacketSender().sendMessage("You select Woodcutting.");
			break;
		case 11014:
			skill = 9;
			c.getPacketSender().sendMessage("You select Fletching.");
			break;
		case 11015:
			if (skill == -1) {
				c.getPacketSender().closeAllWindows();
			}
			if (c.getItemAssistant().playerHasItem(LAMP, 1) && skill > -1) {// normal
																			// lamp
				int xp = c.getPlayerAssistant()
						.getLevelForXP(c.playerXP[skill]) * 10;
				c.getPlayerAssistant().addSkillXP(xp, skill);
				c.getItemAssistant().deleteItem(LAMP, 1);
				c.getPacketSender().sendMessage(
						"@blu@Your wish has been granted!");
				c.getPacketSender().sendMessage(
						"@blu@You have been awarded " + xp
								+ " experience in your selected skill!");
				c.getPacketSender().closeAllWindows();
			} else if (c.getItemAssistant().playerHasItem(LAMP_2, 1)
					&& skill > -1) {// vote
									// reward
				c.getItemAssistant().deleteItem(LAMP_2, 1);
				c.getPacketSender().sendMessage(
						"@blu@Your wish has been granted!");
				addExp(c);
				c.getPacketSender().closeAllWindows();
			}
			break;
		}
	}

	public static void addExp(Client c) {
		if (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) < 20) {
			c.getPlayerAssistant().addSkillXP(1000, skill);
			c.getPacketSender()
					.sendMessage(
							"@blu@You have been awarded 1000 experience in your selected skill!");
		} else if (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) > 19
				&& c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) < 35) {
			c.getPlayerAssistant().addSkillXP(2000, skill);
			c.getPacketSender()
					.sendMessage(
							"@blu@You have been awarded 2000 experience in your selected skill!");
		} else {
			c.getPlayerAssistant().addSkillXP(3000, skill);
			c.getPacketSender()
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
		c.getPacketSender().sendMessage("You rub the lamp.");
		c.getPacketSender().showInterface(SKILL_MENU);
	}

}
