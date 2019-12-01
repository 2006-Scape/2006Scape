package com.rebotted.game.items.impl;

import com.rebotted.game.players.Player;

public class ExperienceLamp {

	public static int LAMP = 4447, LAMP_2 = 2528, SKILL_MENU = 2808,
			skill = -1;

	/**
	 * Handles the actionbuttons
	 * 
	 * @param player
	 * @param id
	 */
	public static void buttons(Player player, int id) {
		switch (id) {
		case 10252:
			skill = 0;
			player.getPacketSender().sendMessage("You select Attack.");
			break;
		case 10253:
			skill = 2;
			player.getPacketSender().sendMessage("You select Strength.");
			break;
		case 10254:
			skill = 4;
			player.getPacketSender().sendMessage("You select Ranged.");
			break;
		case 10255:
			skill = 6;
			player.getPacketSender().sendMessage("You select Magic.");
			break;
		case 11000:
			skill = 1;
			player.getPacketSender().sendMessage("You select Defence.");
			break;
		case 11001:
			skill = 3;
			player.getPacketSender().sendMessage("You select Hitpoints.");
			break;
		case 11002:
			skill = 5;
			player.getPacketSender().sendMessage("You select Prayer.");
			break;
		case 11003:
			skill = 16;
			player.getPacketSender().sendMessage("You select Agility.");
			break;
		case 11004:
			skill = 15;
			player.getPacketSender().sendMessage("You select Herblore.");
			break;
		case 11005:
			skill = 17;
			player.getPacketSender().sendMessage("You select Thieving.");
			break;
		case 11006:
			skill = 12;
			player.getPacketSender().sendMessage("You select Crafting.");
			break;
		case 11007:
			skill = 20;
			player.getPacketSender().sendMessage("You select Runecrafting.");
			break;
		case 47002:
			skill = 18;
			player.getPacketSender().sendMessage("You select Slayer.");
			break;
		case 54090:
			skill = -1;
			player.getPacketSender().sendMessage("You can't select this skill.");
			break;
		case 11008:
			skill = 14;
			player.getPacketSender().sendMessage("You select Mining.");
			break;
		case 11009:
			skill = 13;
			player.getPacketSender().sendMessage("You select Smithing.");
			break;
		case 11010:
			skill = 10;
			player.getPacketSender().sendMessage("You select Fishing.");
			break;
		case 11011:
			skill = 7;
			player.getPacketSender().sendMessage("You select Cooking.");
			break;
		case 11012:
			skill = 11;
			player.getPacketSender().sendMessage("You select Firemaking.");
			break;
		case 11013:
			skill = 8;
			player.getPacketSender().sendMessage("You select Woodcutting.");
			break;
		case 11014:
			skill = 9;
			player.getPacketSender().sendMessage("You select Fletching.");
			break;
		case 11015:
			if (skill == -1) {
				player.getPacketSender().closeAllWindows();
			}
			if (player.getItemAssistant().playerHasItem(LAMP, 1) && skill > -1) {// normal
																			// lamp
				int xp = player.getPlayerAssistant()
						.getLevelForXP(player.playerXP[skill]) * 10;
				player.getPlayerAssistant().addSkillXP(xp, skill);
				player.getItemAssistant().deleteItem(LAMP, 1);
				player.getPacketSender().sendMessage(
						"@blu@Your wish has been granted!");
				player.getPacketSender().sendMessage(
						"@blu@You have been awarded " + xp
								+ " experience in your selected skill!");
				player.getPacketSender().closeAllWindows();
			} else if (player.getItemAssistant().playerHasItem(LAMP_2, 1)
					&& skill > -1) {// vote
									// reward
				player.getItemAssistant().deleteItem(LAMP_2, 1);
				player.getPacketSender().sendMessage(
						"@blu@Your wish has been granted!");
				addExp(player);
				player.getPacketSender().closeAllWindows();
			}
			break;
		}
	}

	public static void addExp(Player c) {
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
	public static void rubLamp(Player c, int id) {
		c.getPacketSender().sendMessage("You rub the lamp.");
		c.getPacketSender().showInterface(SKILL_MENU);
	}

}
