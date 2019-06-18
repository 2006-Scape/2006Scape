package redone.game.content.skills.smithing;

import redone.game.content.music.sound.SoundList;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Andrew
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

	public static boolean superHeatItem(Client c, int itemID) {
		for (int smelt[] : SMELT) {
			if (itemID == smelt[0]) {
				if (!c.getItemAssistant().playerHasItem(smelt[2], smelt[3])) {
					if (itemID == 440 && smelt[2] == 453) {
						continue;
					} else {
						c.getActionSender().sendMessage("You haven't got enough " + ItemAssistant.getItemName(smelt[2]).toLowerCase() + " to cast this spell!");
						return false;
					}
				}
				if (!c.getItemAssistant().playerHasItem(554, 4) || !c.getItemAssistant().playerHasItem(561, 1)) {
					c.getActionSender().sendMessage("You don't have the correct runes to cast this spell.");
					return false;
				}
				if (itemID == 444 && c.playerEquipment[c.playerHands] == 776) {
					c.getPlayerAssistant().addSkillXP(56.2, c.playerSmithing);
				} else {
					c.getPlayerAssistant().addSkillXP(smelt[7],
							c.playerSmithing);
				}
				if (c.playerLevel[c.playerSmithing] < smelt[6]) {
					c.getActionSender().sendMessage(
							"You need a smithing level of " + smelt[6]
									+ " to superheat this ore.");
					return false;
				}
				if (c.playerLevel[c.playerMagic] < 43) {
					c.getActionSender()
							.sendMessage(
									"You need a magic level of 43 to superheat this ore.");
					return false;
				}
				c.getItemAssistant().deleteItem(itemID, 1);
				c.getItemAssistant().deleteItem(smelt[2], smelt[3]);
				c.getItemAssistant().deleteItem2(554, 4);
				c.getItemAssistant().deleteItem2(561, 1);
				c.getItemAssistant().addItem(smelt[4], 1);
				c.getPlayerAssistant().addSkillXP(53, c.playerMagic);
				c.startAnimation(722);
				c.gfx0(148);
				c.getActionSender().sendSound(SoundList.SUPERHEAT, 100, 0);
				if (itemID != 444) {
					c.getPlayerAssistant().addSkillXP(smelt[7], c.playerSmithing);
				}
				c.getPlayerAssistant().sendFrame106(6);
				return true;
			}
		}
		c.getActionSender().sendMessage(
				"You can only cast superheat item on ores!");
		c.getActionSender().sendSound(SoundList.SUPERHEAT_FAIL, 100, 0);
		return false;
	}
}
