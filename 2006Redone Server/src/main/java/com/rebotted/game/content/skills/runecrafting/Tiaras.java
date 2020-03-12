package com.rebotted.game.content.skills.runecrafting;

import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.players.Player;

public class Tiaras {

	public static boolean bindTiara(Player player, int itemId, int objectId) {
		for (int[] ruin : Runecrafting.RC_DATA) {
			if (itemId == ruin[0] && objectId == ruin[2]) {
				if (!SkillHandler.RUNECRAFTING) {
					player.getPacketSender().sendMessage("This skill is currently disabled.");
					return false;
				}
				if (player.getItemAssistant().playerHasItem(5525)) {
					player.getItemAssistant().deleteItem(5525, 1);
					player.getItemAssistant().addItem(ruin[1], 1);
					player.getPacketSender().sendMessage("You bind the power of the talisman into the tiara.");
				}
				return true;
			}
		}
		return false;
	}

	public static void handleTiara(Player player, int id) {
		int[][] tiaras = { { 5527, 1 }, { 5529, 2 }, { 5531, 4 }, { 5535, 8 },
				{ 5537, 16 }, { 5533, 31 }, { 5539, 64 }, { 5543, 128 },
				{ 5541, 256 }, { 5545, 512 }, { 5547, 1024 } };
		for (int[] t : tiaras) {
			if (t[0] == id) {
				player.getPacketSender().sendConfig(491, t[1]);
				return;
			}
		}
		player.getPacketSender().sendConfig(491, 0);
	}

}
