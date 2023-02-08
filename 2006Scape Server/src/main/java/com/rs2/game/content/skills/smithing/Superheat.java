package com.rs2.game.content.skills.smithing;

import com.rs2.Constants;
import com.rs2.game.content.combat.magic.CastRequirements;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

/**
 * @author Andrew (Mr Extremez)
 */

public class Superheat {

	// ore1, ore1amount, ore2, ore2amount, item, xp, smith lvl req
	private static final int[][] SMELT = { { 436, 1, 438, 1, 2349, 6, 1, 6 }, // TIN
			{ 438, 1, 436, 1, 2349, 6, 1, 6 }, // COPPER
			{ 440, 1, 453, 2, 2353, 18, 30, 17 }, // STEEL ORE
			{ 440, 1, -1, -1, 2351, 13, 15, 12 }, // IRON ORE
			{ 442, 1, -1, -1, 2355, 14, 20, 13 }, // SILVER ORE
			{ 444, 1, -1, -1, 2357, 23, 40, 22 }, // GOLD BAR
			{ 447, 1, 453, 4, 2359, 30, 50, 30 }, // MITHRIL ORE
			{ 449, 1, 453, 6, 2361, 38, 70, 37 }, // ADDY ORE
			{ 451, 1, 453, 8, 2363, 50, 85, 50 }, // RUNE ORE
	};

	public static boolean superHeatItem(Player player, int itemID) {
		for (int smelt[] : SMELT) {
			if (itemID == smelt[0]) {
				if (!player.getItemAssistant().playerHasItem(smelt[2], smelt[3])) {
					if (itemID == 440 && smelt[2] == 453) {
						continue;
					} else {
						player.getPacketSender().sendMessage("You haven't got enough " + DeprecatedItems.getItemName(smelt[2]).toLowerCase() + " to cast this spell!");
						return false;
					}
				}
				if (!CastRequirements.hasRunes(player, new int[][]{{554, 4}, {561, 1}})) {
					player.getPacketSender().sendMessage("You don't have the correct runes to cast this spell.");
					return false;
				}
				if (itemID == 444 && player.playerEquipment[player.playerHands] == 776) {
					player.getPlayerAssistant().addSkillXP(56.2, Constants.SMITHING);
				} else {
					player.getPlayerAssistant().addSkillXP(smelt[7], Constants.SMITHING);
				}
				if (player.playerLevel[Constants.SMITHING] < smelt[6]) {
					player.getPacketSender().sendMessage("You need a smithing level of " + smelt[6] + " to superheat this ore.");
					return false;
				}
				if (player.playerLevel[Constants.MAGIC] < 43) {
					player.getPacketSender().sendMessage("You need a magic level of 43 to superheat this ore.");
					return false;
				}
				player.getItemAssistant().deleteItem(itemID, 1);
				player.getItemAssistant().deleteItem(smelt[2], smelt[3]);
				CastRequirements.deleteRunes(player, new int[][]{{554, 4}, {561, 1}});
				player.getItemAssistant().addItem(smelt[4], 1);
				player.getPlayerAssistant().addSkillXP(53, Constants.MAGIC);
				player.startAnimation(722);
				player.gfx0(148);
				player.getPacketSender().sendSound(SoundList.SUPERHEAT, 100, 0);
				if (itemID != 444) {
					player.getPlayerAssistant().addSkillXP(smelt[7], Constants.SMITHING);
				}
				player.getPacketSender().sendShowTab(6);
				return true;
			}
		}
		player.getPacketSender().sendMessage("You can only cast superheat item on ores!");
		player.getPacketSender().sendSound(SoundList.SUPERHEAT_FAIL, 100, 0);
		return false;
	}
}
