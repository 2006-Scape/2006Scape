package com.rebotted.game.dialogues;

import com.rebotted.game.content.skills.crafting.JewelryMaking;
import com.rebotted.game.items.impl.Flowers;
import com.rebotted.game.items.impl.Teles;
import com.rebotted.game.players.Player;

/**
 * Dialogue Options
 * @author Andrew (Mr Extremez)
 */

public class DialogueOptions {
	
	public static void handleDialogueOptions(Player player, int buttonId) {
		switch (buttonId) {
		case 9190:
			switch (player.dialogueAction) {
			case 112:
				player.getDialogueHandler().sendDialogues(3533, player.npcType);//4
				break;
			}
			break;
		case 9191:
			switch (player.dialogueAction) {
			case 112:
				player.getDialogueHandler().sendDialogues(3540, player.npcType);
				break;
			}
			break;
		case 9192:
			switch (player.dialogueAction) {
			case 112:
				player.getDialogueHandler().sendDialogues(3548, player.npcType);
				break;
			}
			break;
		case 9193:
			switch (player.dialogueAction) {
			case 112:
				player.getDialogueHandler().sendDialogues(3550, player.npcType);
				break;
			}
			break;
		case 9194:
			switch (player.dialogueAction) {
			case 112:
				player.getDialogueHandler().sendDialogues(3553, player.npcType);
				break;
			}
			break;
		case 9157:// barrows tele to tunnels
			if (player.dialogueAction == 1) {
				int r = 4;
				// int r = Misc.random(3);

				switch (r) {
				case 0:
					player.getPlayerAssistant().movePlayer(3534, 9677, 0);
					break;

				case 1:
					player.getPlayerAssistant().movePlayer(3534, 9712, 0);
					break;

				case 2:
					player.getPlayerAssistant().movePlayer(3568, 9712, 0);
					break;

				case 3:
					player.getPlayerAssistant().movePlayer(3568, 9677, 0);
					break;
				case 4:
					player.getPlayerAssistant().movePlayer(3551, 9694, 0);
					break;
				}
			} else if (player.dialogueAction == 2) {
				player.getPlayerAssistant().movePlayer(2507, 4717, 0);
			} else if (player.dialogueAction == 7) {
				player.getPlayerAssistant().startTeleport(3088, 3933, 0, "modern");
				player.getPacketSender().sendMessage(
						"NOTE: You are now in the wilderness...");
			} else if (player.dialogueAction == 8) {
				player.getPlayerAssistant().resetBarrows();
				player.getPacketSender().sendMessage(
						"Your barrows have been reset.");
			} else if (player.dialogueAction == 29) {
				player.getDialogueHandler().sendDialogues(480, player.npcType);
				return;
			} else if (player.dialogueAction == 30) {
				player.getDialogueHandler().sendDialogues(488, player.npcType);
				return;
			} else if (player.dialogueAction == 34) {
				player.getDialogueHandler().sendDialogues(361, player.npcType);
				return;
			} else if (player.dialogueAction == 50) {
				player.getPlayerAssistant().startTeleport(2898, 3562, 0, "modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 55) {
				player.getDialogueHandler().sendDialogues(91, player.npcType);
				return;
			} else if (player.dialogueAction == 56) {
				player.getDialogueHandler().sendDialogues(96, player.npcType);
				return;
			} else if (player.dialogueAction == 57) {
				player.getDialogueHandler().sendDialogues(57, player.npcType);
				return;
			} else if (player.dialogueAction == 3222) {
				player.getBarrows().checkCoffins();
				player.getPacketSender().closeAllWindows();
				return;
			} else if (player.dialogueAction == 3218) {
				player.getDialogueHandler().sendDialogues(3219, 0);
				return;
			} else if (player.dialogueAction == 65) {
				player.getDialogueHandler().sendDialogues(179, player.npcType);
				return;
			} else if (player.dialogueAction == 66) {
				player.getDialogueHandler().sendDialogues(182, player.npcType);
				return;
			} else if (player.dialogueAction == 67) {
				player.getDialogueHandler().sendDialogues(36, player.npcType);
				return;
			} else if (player.dialogueAction == 68) {
				player.getDialogueHandler().sendDialogues(587, player.npcType);
				return;
			} else if (player.dialogueAction == 70) {
				player.getDialogueHandler().sendDialogues(1009, player.npcType);
				return;
			} else if (player.dialogueAction == 71) {
				player.getDialogueHandler().sendDialogues(556, player.npcType);
				return;
			} else if (player.dialogueAction == 72) {
				player.getDialogueHandler().sendDialogues(563, player.npcType);
				return;
			} else if (player.dialogueAction == 73) {
				player.getDialogueHandler().sendDialogues(579, player.npcType);
				return;
			} else if (player.dialogueAction == 74) {
				player.getDialogueHandler().sendDialogues(534, player.npcType);
				return;
			} else if (player.dialogueAction == 90) {
				player.getDialogueHandler().sendDialogues(12, player.npcType);
				return;
			} else if (player.dialogueAction == 91) {
				player.getDialogueHandler().sendDialogues(16, player.npcType);
				return;
			} else if (player.dialogueAction == 92) {
				player.getDialogueHandler().sendDialogues(9, player.npcType);
				return;
			} else if (player.dialogueAction == 93) {
				player.getDialogueHandler().sendDialogues(23, player.npcType);
				return;
			} else if (player.dialogueAction == 114) {
				player.getDialogueHandler().sendDialogues(3537, player.npcType);
				return;
			} else if (player.dialogueAction == 116) {
				player.getDialogueHandler().sendDialogues(3545, player.npcType);
				return;
			} else if (player.dialogueAction == 118) {
				player.getDialogueHandler().sendDialogues(394, player.npcType);
				return;
			} else if (player.dialogueAction == 119) {
				player.getDialogueHandler().sendDialogues(399, player.npcType);
				return;
			} else if (player.dialogueAction == 120) {
				player.getDialogueHandler().sendDialogues(406, player.npcType);
				return;
			} else if (player.dialogueAction == 121) {
				player.getDialogueHandler().sendDialogues(438, player.npcType);
				return;
			} else if (player.dialogueAction == 125) {
				player.getDialogueHandler().sendDialogues(154, player.npcType);
				return;
			} else if (player.dialogueAction == 127) {
				player.getDialogueHandler().sendDialogues(210, player.npcType);
				return;
			} else if (player.dialogueAction == 128) {
				player.getDialogueHandler().sendDialogues(223, player.npcType);
				return;
			} else if (player.dialogueAction == 130) {
				player.getDialogueHandler().sendDialogues(594, player.npcType);
				return;
			} else if (player.dialogueAction == 132) {
				player.getDialogueHandler().sendDialogues(1013, player.npcType);
			} else if (player.dialogueAction == 133) {
				player.getDialogueHandler().sendDialogues(1016, player.npcType);
			} else if (player.dialogueAction == 140) {
				player.getDialogueHandler().sendDialogues(198, player.npcType);
				return;
			} else if (player.dialogueAction == 141) {
				player.getDialogueHandler().sendDialogues(1020, player.npcType);
				return;
			} else if (player.dialogueAction == 143) {
				player.getDialogueHandler().sendDialogues(1232, player.npcType);
				return;
			} else if (player.dialogueAction == 168) {
				player.getDialogueHandler().sendDialogues(476, player.npcType);
				return;
			} else if (player.dialogueAction == 508) {
				player.getDialogueHandler().sendDialogues(1026, player.npcType);
				return;
			} else if (player.dialogueAction == 855) {
				player.getItemAssistant().removeAllItems();
			} else if (player.dialogueAction == 146) {
				player.getDialogueHandler().sendDialogues(1325, player.npcType);
				return;
			} else if (player.dialogueAction == 177) {
				player.getDialogueHandler().sendDialogues(1376, player.npcType);
				return;
			} else if (player.dialogueAction == 151) {
				player.getDialogueHandler().sendDialogues(2998, player.npcType);
				return;
			} else if (player.dialogueAction == 152) {
				player.getDialogueHandler().sendDialogues(3121, player.npcType);
				return;
			} else if (player.dialogueAction == 154) {
				player.getDialogueHandler().sendDialogues(3137, player.npcType);
				return;
			} else if (player.dialogueAction == 155) {
				player.getDialogueHandler().sendDialogues(3142, player.npcType);
				return;
			} else if (player.dialogueAction == 156) {
				player.getDialogueHandler().sendDialogues(3147, player.npcType);
				return;
			} else if (player.dialogueAction == 157) {
				player.getDialogueHandler().sendDialogues(3153, player.npcType);
				return;
			} else if (player.dialogueAction == 158) {
				player.getDialogueHandler().sendDialogues(3156, player.npcType);
				return;
			} else if (player.dialogueAction == 3111) {
				player.getDialogueHandler().sendDialogues(3112, 946);
				return;
			} else if (player.dialogueAction == 162) {
				player.getDialogueHandler().sendDialogues(3170, player.npcType);
				return;
			} else if (player.dialogueAction == 163) {
				player.getDialogueHandler().sendDialogues(3129, player.npcType);
				return;
			} else if (player.dialogueAction == 164) {
				player.getDialogueHandler().sendDialogues(3177, 510);
				return;
			} else if (player.dialogueAction == 165) {
				player.getDialogueHandler().sendDialogues(3182, 510);
				return;
			} else if (player.dialogueAction == 166) {
				player.getDialogueHandler().sendDialogues(1340, player.npcType);
				return;
			} else if (player.dialogueAction == 170) {
				player.getDialogueHandler().sendDialogues(1348, player.npcType);
				return;
			} else if (player.dialogueAction == 171) {
				player.getDialogueHandler().sendDialogues(1352, player.npcType);
				return;
			} else if (player.dialogueAction == 172) {
				player.getDialogueHandler().sendDialogues(1355, player.npcType);
				return;
			} else if (player.dialogueAction == 173) {
				player.getDialogueHandler().sendDialogues(1360, player.npcType);
				return;
			} else if (player.dialogueAction == 175) {
				player.getDialogueHandler().sendDialogues(3192, player.npcType);
				return;
			} else if (player.dialogueAction == 176) {
				player.getDialogueHandler().sendDialogues(1372, player.npcType);
				return;
			} else if (player.dialogueAction == 178) {
				player.getDialogueHandler().sendDialogues(3186, player.npcType);
				return;
			} else if (player.dialogueAction == 179) {
				player.getDialogueHandler().sendDialogues(1380, player.npcType);
				return;
			} else if (player.dialogueAction == 180) {
				player.getDialogueHandler().sendDialogues(3197, player.npcType);
				return;
			} else if (player.dialogueAction == 181) {
				player.getDialogueHandler().sendDialogues(612, player.npcType);
				return;
			} else if (player.dialogueAction == 183) {
				player.getDialogueHandler().sendDialogues(620, player.npcType);
				return;
			} else if (player.dialogueAction == 184) {
				player.getDialogueHandler().sendDialogues(624, player.npcType);
				return;
			} else if (player.dialogueAction == 3204) {
				player.getItemAssistant().deleteItem(1929, 1);
				player.getItemAssistant().deleteItem(1933, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1931, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 3205) {
				player.getItemAssistant().deleteItem(1933, 1);
				player.getItemAssistant().deleteItem(1937, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1935, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 189) {
				player.getDialogueHandler().sendDialogues(3210, player.npcType);
				return;
			} else if (player.dialogueAction == 161) {// rod
				player.getPlayerAssistant().startTeleport(3313, 3234, 0, "modern");
				Teles.necklaces(player);
				return;
			}
			player.dialogueAction = 0;
			player.getPacketSender().closeAllWindows();
			break;

		case 9158:
			if (player.dialogueAction == 8) {
				player.getPlayerAssistant().fixAllBarrows();
			} else if (player.dialogueAction == 29) {
				player.getDialogueHandler().sendDialogues(481, player.npcType);
				return;
			} else if (player.dialogueAction == 34) {
				player.getDialogueHandler().sendDialogues(359, player.npcType);
				return;
			} else if (player.dialogueAction == 50) {
				player.getPlayerAssistant().startTeleport(2545, 3569, 0, "modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 55) {
				player.getDialogueHandler().sendDialogues(92, player.npcType);
				return;
			} else if (player.dialogueAction == 56) {
				player.getDialogueHandler().sendDialogues(95, player.npcType);
				return;
			} else if (player.dialogueAction == 74) {
				player.getDialogueHandler().sendDialogues(535, player.npcType);
				return;
			} else if (player.dialogueAction == 57) {
				player.getDialogueHandler().sendDialogues(58, player.npcType);
				return;
			} else if (player.dialogueAction == 62) {
				player.getDialogueHandler().sendDialogues(309, player.npcType);
				return;
			} else if (player.dialogueAction == 67) {
				player.getDialogueHandler().sendDialogues(35, player.npcType);
				return;
			} else if (player.dialogueAction == 68) {
				player.getDialogueHandler().sendDialogues(586, player.npcType);
				return;
			} else if (player.dialogueAction == 71) {
				player.getDialogueHandler().sendDialogues(582, player.npcType);
				return;
			} else if (player.dialogueAction == 72) {
				player.getDialogueHandler().sendDialogues(562, player.npcType);
				return;
			} else if (player.dialogueAction == 73) {
				player.getDialogueHandler().sendDialogues(580, player.npcType);
				return;
			} else if (player.dialogueAction == 90) {
				player.getDialogueHandler().sendDialogues(13, player.npcType);
				return;
			} else if (player.dialogueAction == 91) {
				player.getDialogueHandler().sendDialogues(17, player.npcType);
				return;
			} else if (player.dialogueAction == 114) {
				player.getDialogueHandler().sendDialogues(3538, player.npcType);
				return;
			} else if (player.dialogueAction == 116) {
				player.getDialogueHandler().sendDialogues(3546, player.npcType);
				return;
			} else if (player.dialogueAction == 118) {
				player.getDialogueHandler().sendDialogues(392, player.npcType);
				return;
			} else if (player.dialogueAction == 119) {
				player.getDialogueHandler().sendDialogues(404, player.npcType);
				return;
			} else if (player.dialogueAction == 120) {
				player.getDialogueHandler().sendDialogues(404, player.npcType);
				return;
			} else if (player.dialogueAction == 121) {
				player.getDialogueHandler().sendDialogues(437, player.npcType);
				return;
			} else if (player.dialogueAction == 125) {
				player.getDialogueHandler().sendDialogues(163, player.npcType);
				return;
			} else if (player.dialogueAction == 130) {
				player.getDialogueHandler().sendDialogues(593, player.npcType);
				return;
			} else if (player.dialogueAction == 131) {
				JewelryMaking.mouldInterface(player);
				return;
			} else if (player.dialogueAction == 141) {
				player.getDialogueHandler().sendDialogues(1021, player.npcType);
				return;
			} else if (player.dialogueAction == 143) {
				player.getDialogueHandler().sendDialogues(1233, player.npcType);
				return;
			} else if (player.dialogueAction == 161) {// rod
				player.getPlayerAssistant().startTeleport(2441, 3090, 0, "modern");
				Teles.necklaces(player);
				return;
			} else if (player.dialogueAction == 508) {
				player.getDialogueHandler().sendDialogues(1025, player.npcType);
				return;
			} else if (player.dialogueAction == 146) {
				player.getDialogueHandler().sendDialogues(1324, player.npcType);
				return;
			} else if (player.dialogueAction == 177) {
				player.getDialogueHandler().sendDialogues(1375, player.npcType);
				return;
			} else if (player.dialogueAction == 21) {
				Flowers.harvestFlower(player, Flowers.lastObject);
				player.getPacketSender().closeAllWindows();
			} else if (player.dialogueAction == 3111) {
				player.getDialogueHandler().sendDialogues(3117, 946);
				return;
			} else if (player.dialogueAction == 152) {
				player.getDialogueHandler().sendDialogues(3120, player.npcType);
				return;
			} else if (player.dialogueAction == 151) {
				player.getDialogueHandler().sendDialogues(3000, player.npcType);
				player.getPacketSender().closeAllWindows();
				return;
			} else if (player.dialogueAction == 154) {
				player.getDialogueHandler().sendDialogues(3135, player.npcType);
				return;
			} else if (player.dialogueAction == 155) {
				player.getDialogueHandler().sendDialogues(3141, player.npcType);
				return;
			} else if (player.dialogueAction == 156) {
				player.getDialogueHandler().sendDialogues(3146, player.npcType);
				return;
			} else if (player.dialogueAction == 157) {
				player.getDialogueHandler().sendDialogues(3152, player.npcType);
				return;
			} else if (player.dialogueAction == 158) {
				player.getDialogueHandler().sendDialogues(3157, player.npcType);
				return;
			} else if (player.dialogueAction == 162) {
				player.getDialogueHandler().sendDialogues(3169, player.npcType);
				return;
			} else if (player.dialogueAction == 163) {
				player.getDialogueHandler().sendDialogues(3131, player.npcType);
				return;
			} else if (player.dialogueAction == 164) {
				player.getDialogueHandler().sendDialogues(3175, player.npcType);
				return;
			} else if (player.dialogueAction == 165) {
				player.getDialogueHandler().sendDialogues(3180, player.npcType);
				return;
			} else if (player.dialogueAction == 166) {
				player.getDialogueHandler().sendDialogues(1339, player.npcType);
				return;
			} else if (player.dialogueAction == 168) {
				player.getDialogueHandler().sendDialogues(1337, player.npcType);
				return;
			} else if (player.dialogueAction == 170) {
				player.getDialogueHandler().sendDialogues(1347, player.npcType);
				return;
			} else if (player.dialogueAction == 171) {
				player.getDialogueHandler().sendDialogues(1351, player.npcType);
				return;
			} else if (player.dialogueAction == 172) {
				player.getDialogueHandler().sendDialogues(1356, player.npcType);
				return;
			} else if (player.dialogueAction == 173) {
				player.getDialogueHandler().sendDialogues(1361, player.npcType);
				return;
			} else if (player.dialogueAction == 175) {
				player.getDialogueHandler().sendDialogues(3191, player.npcType);
				return;
			} else if (player.dialogueAction == 176) {
				player.getDialogueHandler().sendDialogues(1371, player.npcType);
				return;
			} else if (player.dialogueAction == 178) {
				player.getDialogueHandler().sendDialogues(3185, player.npcType);
				return;
			} else if (player.dialogueAction == 179) {
				player.getDialogueHandler().sendDialogues(1381, player.npcType);
				return;
			} else if (player.dialogueAction == 180) {
				player.getDialogueHandler().sendDialogues(3199, player.npcType);
				return;
			} else if (player.dialogueAction == 181) {
				player.getDialogueHandler().sendNpcChat1("No I like my job as Squire, I just need some help.", player.talkingNpc, "Squire");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 183) {
				player.getDialogueHandler().sendPlayerChat("Well I hope you find it soon.");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 184) {
				player.getDialogueHandler().sendPlayerChat("No, I've got lots of mining work to do.");
				player.nextChat = 0;
				return;
			} else if (player.dialogueAction == 3204) {
				player.getItemAssistant().deleteItem(1929, 1);
				player.getItemAssistant().deleteItem(1933, 1);
				player.getItemAssistant().addItem(2307, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1931, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 3205) {
				player.getItemAssistant().deleteItem(1933, 1);
				player.getItemAssistant().deleteItem(1937, 1);
				player.getItemAssistant().addItem(1953, 1);
				player.getItemAssistant().addItem(1925, 1);
				player.getItemAssistant().addItem(1935, 1);
				player.getPlayerAssistant().addSkillXP(1, player.playerCooking);
				player.nextChat = 0;
			} else if (player.dialogueAction == 189) {
				player.getDialogueHandler().sendDialogues(3212, player.npcType);
				return;
			}
			player.dialogueAction = 0;
			player.getPacketSender().closeAllWindows();
			break;

		case 9178:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(3428, 3538, 0, "modern");
			}
			if (player.dialogueAction == 122 && player.objectId == 12164 || player.objectId == 12163 || player.objectId == 12166) {//barb
				player.getPlayerAssistant().startTeleport(3112, 3410, 0, "modern");
			} else if (player.objectId == 12165) {
				if (player.dialogueAction == 122) {
				player.getPacketSender().sendMessage("You can't take the canoe to barbarian village because you're already there!");
				player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(3565, 3314, 0,
						"modern");
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3088, 3500, 0,
						"modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(500, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(340, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(354, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(378, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3088, 3500, 0, "modern");
				Teles.necklaces(player);
			}
			if (player.dialogueAction == 113) {
				player.getDialogueHandler().sendDialogues(3540, player.npcType);
			}
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(52, player.npcType);
			}
			if (player.dialogueAction == 1000) {
				player.getDialogueHandler().sendDialogues(3524,player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1005, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1045, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1318, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3123, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3164, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1234, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().buyArrows();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(28, player.npcType);
			}
			break;

		case 9179:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2884, 3395, 0, "modern");
			}
			if (player.dialogueAction == 113) {
				player.getDialogueHandler().sendDialogues(3548, player.npcType);
			}
			if (player.dialogueAction == 122 && player.objectId == 12163 || player.objectId == 12165 || player.objectId == 12166) {//champ
				player.getPlayerAssistant().startTeleport(3203, 3343, 0, "modern");
			} else if (player.objectId == 12164) {
				if (player.dialogueAction == 122) {
					player.getPacketSender().sendMessage("You can't take the canoe to the Champion Guild because you're already there!");
					player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(2444, 5170, 0, "modern");
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3243, 3513, 0, "modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(502, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(341, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(356, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(376, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3293, 3174, 0, "modern");
				Teles.necklaces(player);
			}
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(64, player.npcType);
			}
			if (player.dialogueAction == 1000) {
				player.getDialogueHandler().sendDialogues(3523, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500002, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1042, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1319, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3124, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3164, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1235, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().exchangePoints();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(29, player.npcType);
			}
			break;

		case 9180:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2471, 10137, 0, "modern");
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500003, player.npcType);
			}
			if (player.dialogueAction == 113) {
				player.getDialogueHandler().sendDialogues(3550, player.npcType);
			}
			if (player.dialogueAction == 122 && player.objectId == 12164 || player.objectId == 12165 || player.objectId == 12166) {//lumby
				player.getPlayerAssistant().startTeleport(3243, 3237, 0, "modern");
			} else if (player.objectId == 12163) {
				if (player.dialogueAction == 122) {
					player.getPacketSender().sendMessage("You can't take the canoe to Lumbridge because you're already there!");
					player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(3363, 3676, 0, "modern");
			}
			if (player.dialogueAction == 4) {
				player.getPlayerAssistant().startTeleport(2659, 2676, 0, "modern");
			}
			if (player.dialogueAction == 31) {
				player.getDialogueHandler().sendDialogues(501, player.npcType);
			}
			if (player.dialogueAction == 32) {
				player.getDialogueHandler().sendDialogues(342, player.npcType);
			}
			if (player.dialogueAction == 33) {
				player.getDialogueHandler().sendDialogues(355, player.npcType);
			}
			if (player.dialogueAction == 35) {
				player.getDialogueHandler().sendDialogues(377, player.npcType);
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(2911, 3152, 0, "modern");
				Teles.necklaces(player);
			}
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(65, player.npcType);
			}
			if (player.dialogueAction == 1000) {
				player.getDialogueHandler().sendDialogues(3523,player.npcType);
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(30, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1041, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1320, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3125, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3165, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1236, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getRangersGuild().howAmIDoing();
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1003, player.npcType);
			}
			break;

		case 9181:
			if (player.dialogueAction == 2) {
				player.getPlayerAssistant().startTeleport(2669, 3714, 0, "modern");
			}
			if (player.dialogueAction == 113) {
				player.getDialogueHandler().sendDialogues(3553, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(500004, player.npcType);
			}
			if (player.dialogueAction == 122 && player.objectId == 12163 || player.objectId == 12164 || player.objectId == 12165) {//edge
				player.getPlayerAssistant().startTeleport(3132, 3509, 0, "modern");
			} else if (player.objectId == 12166) {
				if (player.dialogueAction == 122) {
					player.getPacketSender().sendMessage("You can't take the canoe to Edgeville because you're already there!");
					player.getPlayerAssistant().handleCanoe();
				}
			}
			if (player.dialogueAction == 3) {
				player.getPlayerAssistant().startTeleport(2540, 4716, 0, "modern");
			}
			if (player.dialogueAction == 51) {
				player.getPlayerAssistant().gloryTeleport(3103, 3249, 0, "modern");
				Teles.necklaces(player);
			}
			if (player.dialogueAction == 52) {
				player.getDialogueHandler().sendDialogues(63, player.npcType);
			}
			if (player.dialogueAction == 1000) {
				player.getDialogueHandler().sendDialogues(3523, player.npcType);
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(31, player.npcType);
			}
			if (player.dialogueAction == 69) {
				player.getDialogueHandler().sendDialogues(1004, player.npcType);
			}
			if (player.dialogueAction == 228) {
				player.getDialogueHandler().sendDialogues(1038, player.npcType);
			}
			if (player.dialogueAction == 145) {
				player.getDialogueHandler().sendDialogues(1321, player.SlayerMaster);
			}
			if (player.dialogueAction == 153) {
				player.getDialogueHandler().sendDialogues(3126, player.npcType);
			}
			if (player.dialogueAction == 160) {
				player.getDialogueHandler().sendDialogues(3166, player.npcType);
			}
			if (player.dialogueAction == 142) {
				player.getDialogueHandler().sendDialogues(1231, player.npcType);
			}
			if (player.dialogueAction == 485) {
				player.getPacketSender().closeAllWindows();
			}
			if (player.dialogueAction == 700) {
				player.getDialogueHandler().sendDialogues(28, player.npcType);
			}
			break;
		}
	}

}
